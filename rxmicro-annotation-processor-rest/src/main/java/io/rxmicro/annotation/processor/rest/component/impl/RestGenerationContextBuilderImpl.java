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
import java.util.Set;
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

    @Override
    public RestGenerationContext build(final EnvironmentContext environmentContext,
                                       final Class<? extends RestModuleGeneratorConfig> restModuleGeneratorConfigClass,
                                       final Set<? extends RestClassSignature> restClassSignatures) {
        final ModuleElement currentModule = environmentContext.getCurrentModule();
        final RestModuleGeneratorConfig restModuleGeneratorConfig = environmentContext.get(restModuleGeneratorConfigClass);
        final RestGenerationContext.Builder builder = new RestGenerationContext.Builder();
        setFromHttpDataModelClasses(restClassSignatures, restModuleGeneratorConfig, currentModule, builder);
        setToHttpDataModelClasses(restClassSignatures, restModuleGeneratorConfig, currentModule, builder);
        final ExchangeFormatModule exchangeFormatModule = restModuleGeneratorConfig.getExchangeFormatModule();
        validateModuleDependencies(environmentContext, exchangeFormatModule);
        return builder.build();
    }

    private void setFromHttpDataModelClasses(final Set<? extends RestClassSignature> restClassStructures,
                                             final RestModuleGeneratorConfig restModuleGeneratorConfig,
                                             final ModuleElement currentModule,
                                             final RestGenerationContext.Builder builder) {
        final Map<TypeElement, RestObjectModelClass> fromHttpDataMap = modelFieldBuilder.build(
                restModuleGeneratorConfig.getFromHttpDataModelFieldType(),
                currentModule,
                restClassStructures.stream()
                        .flatMap(m -> m.getFromHttpDataModelTypes().stream())
                        .collect(toTreeSet(UNIQUE_TYPES_COMPARATOR)),
                new ModelFieldBuilderOptions()
                        .setRequireDefConstructor(true));
        final List<MappedRestObjectModelClass> fromHttpDataModelClasses = group(fromHttpDataMap, restClassStructures.stream()
                .flatMap(cl -> cl.getMethodSignatures().stream()
                        .flatMap(m -> m.getFromHttpDataType()
                                .map(r -> Map.entry(r.toString(), m.getHttpMethodMappings()))
                                .stream()))
                .collect(toList()));
        builder.setFromHttpDataModelClasses(fromHttpDataModelClasses);
    }

    private void setToHttpDataModelClasses(final Set<? extends RestClassSignature> restClassStructures,
                                           final RestModuleGeneratorConfig restModuleGeneratorConfig,
                                           final ModuleElement currentModule,
                                           final RestGenerationContext.Builder builder) {
        final Map<TypeElement, RestObjectModelClass> toHttpDataMap = modelFieldBuilder.build(
                restModuleGeneratorConfig.getToHttpDataModelFieldType(),
                currentModule,
                restClassStructures.stream()
                        .flatMap(m -> m.getToHttpDataModelTypes().stream())
                        .collect(toTreeSet(UNIQUE_TYPES_COMPARATOR)),
                new ModelFieldBuilderOptions());
        final List<MappedRestObjectModelClass> toHttpDataModelClasses = group(toHttpDataMap, restClassStructures.stream()
                .flatMap(cl -> cl.getMethodSignatures().stream()
                        .flatMap(m -> m.getToHttpDataType()
                                .map(r -> Map.entry(r.toString(), m.getHttpMethodMappings()))
                                .stream()))
                .collect(toList()));
        builder.setToHttpDataModelClasses(toHttpDataModelClasses);
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

    private void validateModuleDependencies(final EnvironmentContext environmentContext,
                                            final ExchangeFormatModule exchangeFormatModule) {
        if (!environmentContext.isRxMicroModuleEnabled(exchangeFormatModule.getRxMicroModule())) {
            throw new InterruptProcessingException(environmentContext.getCurrentModule(),
                    "Missing module dependency. Add \"requires ?;\" to module-info.java",
                    exchangeFormatModule.getRxMicroModule().getName());
        }
    }
}
