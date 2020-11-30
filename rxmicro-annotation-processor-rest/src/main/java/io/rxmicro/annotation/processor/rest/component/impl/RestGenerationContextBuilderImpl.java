/*
 * Copyright (c) 2020. https://rxmicro.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.rxmicro.annotation.processor.rest.component.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.component.ModelClassHierarchyBuilder;
import io.rxmicro.annotation.processor.common.component.ModelFieldBuilder;
import io.rxmicro.annotation.processor.common.component.impl.AbstractProcessorComponent;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.common.model.ModelFieldBuilderOptions;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.rest.component.RestGenerationContextBuilder;
import io.rxmicro.annotation.processor.rest.model.HttpMethodMapping;
import io.rxmicro.annotation.processor.rest.model.MappedRestObjectModelClass;
import io.rxmicro.annotation.processor.rest.model.RestClassSignature;
import io.rxmicro.annotation.processor.rest.model.RestGenerationContext;
import io.rxmicro.annotation.processor.rest.model.RestModelField;
import io.rxmicro.annotation.processor.rest.model.RestModuleGeneratorConfig;
import io.rxmicro.annotation.processor.rest.model.RestObjectModelClass;
import io.rxmicro.rest.model.ExchangeFormatModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.lang.model.element.ModuleElement;
import javax.lang.model.element.TypeElement;

import static io.rxmicro.annotation.processor.common.util.Elements.UNIQUE_TYPES_COMPARATOR;
import static io.rxmicro.common.util.ExCollectors.toTreeSet;
import static java.util.stream.Collectors.toList;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class RestGenerationContextBuilderImpl extends AbstractProcessorComponent
        implements RestGenerationContextBuilder {

    @Inject
    private ModelFieldBuilder<RestModelField, RestObjectModelClass> modelFieldBuilder;

    @Inject
    private ModelClassHierarchyBuilder<RestModelField, RestObjectModelClass> modelClassHierarchyBuilder;

    @Override
    public RestGenerationContext build(final EnvironmentContext environmentContext,
                                       final Class<? extends RestModuleGeneratorConfig> restModuleGeneratorConfigClass,
                                       final Set<? extends RestClassSignature> restClassSignatures) {
        final ModuleElement currentModule = environmentContext.getCurrentModule();
        final RestModuleGeneratorConfig restModuleGeneratorConfig = environmentContext.get(restModuleGeneratorConfigClass);
        final ExchangeFormatModule exchangeFormatModule = restModuleGeneratorConfig.getExchangeFormatModule();
        validateModuleDependencies(environmentContext, exchangeFormatModule);

        final List<MappedRestObjectModelClass> fromHttpDataModelClasses =
                getFromHttpDataModelClasses(restClassSignatures, restModuleGeneratorConfig, currentModule);
        final List<MappedRestObjectModelClass> toHttpDataModelClasses =
                getToHttpDataModelClasses(restClassSignatures, restModuleGeneratorConfig, currentModule);
        addModelClassHierarchy(fromHttpDataModelClasses, toHttpDataModelClasses);
        return new RestGenerationContext(fromHttpDataModelClasses, toHttpDataModelClasses);
    }

    private void validateModuleDependencies(final EnvironmentContext environmentContext,
                                            final ExchangeFormatModule exchangeFormatModule) {
        if (!environmentContext.isRxMicroModuleEnabled(exchangeFormatModule.getRxMicroModule())) {
            throw new InterruptProcessingException(environmentContext.getCurrentModule(),
                    "Missing module dependency. Add \"requires ?;\" to module-info.java",
                    exchangeFormatModule.getRxMicroModule().getName());
        }
    }

    private List<MappedRestObjectModelClass> getFromHttpDataModelClasses(final Set<? extends RestClassSignature> restClassStructures,
                                                                         final RestModuleGeneratorConfig restModuleGeneratorConfig,
                                                                         final ModuleElement currentModule) {
        final Map<TypeElement, RestObjectModelClass> fromHttpDataMap = modelFieldBuilder.build(
                restModuleGeneratorConfig.getFromHttpDataModelFieldType(),
                currentModule,
                restClassStructures.stream()
                        .flatMap(m -> m.getFromHttpDataModelTypes().stream())
                        .collect(toTreeSet(UNIQUE_TYPES_COMPARATOR)),
                new ModelFieldBuilderOptions()
                        .setRequireDefConstructor(true));
        return group(fromHttpDataMap, restClassStructures.stream()
                .flatMap(cl -> cl.getMethodSignatures().stream()
                        .flatMap(m -> m.getFromHttpDataType()
                                .map(r -> Map.entry(r.toString(), m.getHttpMethodMappings()))
                                .stream()))
                .collect(toList()));
    }

    private List<MappedRestObjectModelClass> getToHttpDataModelClasses(final Set<? extends RestClassSignature> restClassStructures,
                                                                       final RestModuleGeneratorConfig restModuleGeneratorConfig,
                                                                       final ModuleElement currentModule) {
        final Map<TypeElement, RestObjectModelClass> toHttpDataMap = modelFieldBuilder.build(
                restModuleGeneratorConfig.getToHttpDataModelFieldType(),
                currentModule,
                restClassStructures.stream()
                        .flatMap(m -> m.getToHttpDataModelTypes().stream())
                        .collect(toTreeSet(UNIQUE_TYPES_COMPARATOR)),
                new ModelFieldBuilderOptions());
        return group(toHttpDataMap, restClassStructures.stream()
                .flatMap(cl -> cl.getMethodSignatures().stream()
                        .flatMap(m -> m.getToHttpDataType()
                                .map(r -> Map.entry(r.toString(), m.getHttpMethodMappings()))
                                .stream()))
                .collect(toList()));
    }

    private List<MappedRestObjectModelClass> group(final Map<TypeElement, RestObjectModelClass> dataMap,
                                                   final List<Map.Entry<String, List<HttpMethodMapping>>> restModels) {
        final Map<RestObjectModelClass, List<HttpMethodMapping>> map = new HashMap<>();
        for (final Map.Entry<TypeElement, RestObjectModelClass> entry : dataMap.entrySet()) {
            map.computeIfAbsent(entry.getValue(), v -> new ArrayList<>())
                    .addAll(restModels.stream()
                            .filter(e -> e.getKey().equals(entry.getKey().asType().toString()))
                            .flatMap(e -> e.getValue().stream())
                            .collect(toList()));
        }
        return map.entrySet().stream()
                .map(e -> new MappedRestObjectModelClass(e.getKey(), e.getValue()))
                .collect(toList());
    }

    private void addModelClassHierarchy(final List<MappedRestObjectModelClass> fromHttpDataModelClasses,
                                        final List<MappedRestObjectModelClass> toHttpDataModelClasses) {
        final Set<String> notAbstractModelClassNames = Stream.concat(
                fromHttpDataModelClasses.stream().map(m -> m.getModelClass().getJavaFullClassName()),
                toHttpDataModelClasses.stream().map(m -> m.getModelClass().getJavaFullClassName())
        ).collect(Collectors.toSet());
        addModelClassHierarchy(fromHttpDataModelClasses, notAbstractModelClassNames);
        addModelClassHierarchy(toHttpDataModelClasses, notAbstractModelClassNames);
    }

    private void addModelClassHierarchy(final List<MappedRestObjectModelClass> mappedRestObjectModelClasses,
                                        final Set<String> notAbstractModelClassNames) {
        for (int i = 0; i < mappedRestObjectModelClasses.size(); i++) {
            final MappedRestObjectModelClass mappedRestObjectModelClass = mappedRestObjectModelClasses.get(i);
            final Optional<List<RestObjectModelClass>> modelClassListOptional =
                    modelClassHierarchyBuilder.build(mappedRestObjectModelClass.getModelClass(), notAbstractModelClassNames);
            if (modelClassListOptional.isPresent()) {
                final List<RestObjectModelClass> list = modelClassListOptional.get();
                mappedRestObjectModelClasses.set(
                        i,
                        new MappedRestObjectModelClass(
                                // The last item is a child object model class with set parent(s)
                                list.get(list.size() - 1),
                                mappedRestObjectModelClass.getHttpMethodMappings()
                        ));
            }
        }
    }
}
