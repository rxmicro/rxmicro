/*
 * Copyright 2019 http://rxmicro.io
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

package io.rxmicro.annotation.processor.rest.client;

import com.google.inject.Inject;
import io.rxmicro.annotation.processor.common.CommonDependenciesModule;
import io.rxmicro.annotation.processor.common.FormatSourceCodeDependenciesModule;
import io.rxmicro.annotation.processor.common.component.ModuleGeneratorConfigBuilder;
import io.rxmicro.annotation.processor.common.component.ModuleInfoCustomizer;
import io.rxmicro.annotation.processor.common.component.impl.AbstractModuleClassStructuresBuilder;
import io.rxmicro.annotation.processor.common.model.ClassStructure;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.common.model.EnvironmentCustomizerClassStructure;
import io.rxmicro.annotation.processor.common.model.ModuleInfoItem;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.common.model.virtual.VirtualTypeElement;
import io.rxmicro.annotation.processor.rest.RestCommonDependenciesModule;
import io.rxmicro.annotation.processor.rest.client.component.PathBuilderClassStructureBuilder;
import io.rxmicro.annotation.processor.rest.client.component.RequestModelExtractorClassStructureBuilder;
import io.rxmicro.annotation.processor.rest.client.component.RestClientClassSignatureBuilder;
import io.rxmicro.annotation.processor.rest.client.component.RestClientClassStructureBuilder;
import io.rxmicro.annotation.processor.rest.client.component.RestClientModelReaderBuilder;
import io.rxmicro.annotation.processor.rest.client.model.RestClientClassSignature;
import io.rxmicro.annotation.processor.rest.client.model.RestClientClassStructure;
import io.rxmicro.annotation.processor.rest.client.model.RestClientClassStructureStorage;
import io.rxmicro.annotation.processor.rest.client.model.RestClientFactoryClassStructure;
import io.rxmicro.annotation.processor.rest.client.model.RestClientMethodSignature;
import io.rxmicro.annotation.processor.rest.client.model.RestClientModuleGeneratorConfig;
import io.rxmicro.annotation.processor.rest.client.model.RestClientObjectModelClass;
import io.rxmicro.annotation.processor.rest.component.RestGenerationContextBuilder;
import io.rxmicro.annotation.processor.rest.component.RestModelFromJsonConverterBuilder;
import io.rxmicro.annotation.processor.rest.component.RestModelToJsonConverterBuilder;
import io.rxmicro.annotation.processor.rest.component.RestModelValidatorBuilder;
import io.rxmicro.annotation.processor.rest.model.MappedRestObjectModelClass;
import io.rxmicro.annotation.processor.rest.model.RestGenerationContext;
import io.rxmicro.annotation.processor.rest.model.VirtualTypeClassStructure;
import io.rxmicro.annotation.processor.rest.model.converter.ReaderType;
import io.rxmicro.rest.client.RestClient;
import io.rxmicro.rest.model.ExchangeFormat;
import io.rxmicro.validation.DisableValidation;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static io.rxmicro.annotation.processor.common.util.Injects.injectDependencies;
import static io.rxmicro.annotation.processor.common.util.Names.getPackageName;
import static io.rxmicro.common.RxMicroModule.RX_MICRO_CONFIG_MODULE;
import static io.rxmicro.common.RxMicroModule.RX_MICRO_RUNTIME_MODULE;
import static io.rxmicro.common.util.Formats.format;
import static java.util.Map.entry;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class RestClientModuleClassStructuresBuilder extends AbstractModuleClassStructuresBuilder {

    @Inject
    private ModuleGeneratorConfigBuilder<RestClientModuleGeneratorConfig> restClientModuleGeneratorConfigBuilder;

    @Inject
    private RestClientClassSignatureBuilder restClientClassSignatureBuilder;

    @Inject
    private RestClientClassStructureBuilder restClientClassStructureBuilder;

    @Inject
    private RestGenerationContextBuilder restGenerationContextBuilder;

    @Inject
    private RestClientModelReaderBuilder restClientModelReaderBuilder;

    @Inject
    private RestModelToJsonConverterBuilder restModelToJsonConverterBuilder;

    @Inject
    private RestModelFromJsonConverterBuilder restModelFromJsonConverterBuilder;

    @Inject
    private RequestModelExtractorClassStructureBuilder requestModelExtractorClassStructureBuilder;

    @Inject
    private PathBuilderClassStructureBuilder pathBuilderClassStructureBuilder;

    @Inject
    private RestModelValidatorBuilder restModelValidatorBuilder;

    @Inject
    private ModuleInfoCustomizer moduleInfoCustomizer;

    public static RestClientModuleClassStructuresBuilder create() {
        final RestClientModuleClassStructuresBuilder builder = new RestClientModuleClassStructuresBuilder();
        injectDependencies(
                builder,
                new FormatSourceCodeDependenciesModule(),
                new CommonDependenciesModule(),
                new RestCommonDependenciesModule(),
                new RestClientDependenciesModule()
        );
        return builder;
    }

    private RestClientModuleClassStructuresBuilder() {
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of(
                RestClient.class.getName()
        );
    }

    @Override
    public Set<? extends ClassStructure> buildClassStructures(final EnvironmentContext environmentContext,
                                                              final Set<? extends TypeElement> annotations,
                                                              final RoundEnvironment roundEnv) {
        try {
            final Set<RestClientClassSignature> classSignatures =
                    restClientClassSignatureBuilder.build(environmentContext, annotations, roundEnv);
            if (!classSignatures.isEmpty()) {
                environmentContext.put(restClientModuleGeneratorConfigBuilder.build(environmentContext));
                logFoundRestClients(classSignatures);
                final RestGenerationContext restGenerationContext =
                        restGenerationContextBuilder.build(environmentContext, RestClientModuleGeneratorConfig.class, classSignatures);
                final RestClientClassStructureStorage restClientClassStructureStorage =
                        buildRestClientClassStructureStorage(environmentContext, classSignatures, restGenerationContext);
                final Set<ClassStructure> classStructures = new HashSet<>(restClientClassStructureStorage.getAll());
                final Set<RestClientClassStructure> restClientClassStructures =
                        restClientClassStructureBuilder.build(environmentContext, restClientClassStructureStorage, classSignatures);
                classStructures.addAll(restClientClassStructures);
                final List<ModuleInfoItem> moduleInfoItems =
                        buildModuleInfoItems(environmentContext, restClientClassStructures);
                classStructures.add(new RestClientFactoryClassStructure(
                        restClientClassStructures,
                        restClientClassStructures.stream()
                                .flatMap(c -> c.getDefaultConfigValues().stream())
                                .collect(toList()),
                        moduleInfoItems
                ));
                addAllVirtualRequestClassStructures(classStructures, classSignatures, restClientClassStructureStorage);
                classStructures.add(new EnvironmentCustomizerClassStructure(environmentContext.getDefaultConfigValues()));
                return classStructures;
            }
            return Set.of();
        } catch (final InterruptProcessingException e) {
            error(e);
            return Set.of();
        }
    }

    private void addAllVirtualRequestClassStructures(final Set<ClassStructure> classStructures,
                                                     final Set<RestClientClassSignature> classSignatures,
                                                     final RestClientClassStructureStorage restClientClassStructureStorage) {
        for (final RestClientClassSignature classSignature : classSignatures) {
            for (final RestClientMethodSignature methodSignature : classSignature.getMethodSignatures()) {
                methodSignature.getRequestModel().getRequestType()
                        .filter(t -> t instanceof VirtualTypeElement)
                        .flatMap(t -> restClientClassStructureStorage.getModelClass(t.getQualifiedName().toString()))
                        .ifPresent(modelClass -> classStructures.add(new VirtualTypeClassStructure(modelClass, true)));
            }
        }
    }

    private void logFoundRestClients(final Set<RestClientClassSignature> set) {
        info("Found the following rest clients:\n?", () -> set.stream()
                .map(s -> s.getMethodSignatures().stream()
                        .map(e -> format("  ? ? -> ?",
                                e.getHttpMethodMapping().getMethod(),
                                e.getHttpMethodMapping().getExactOrTemplateUri(),
                                e.toString()))
                        .collect(joining("\n")))
                .collect(joining("\n"))
        );
    }

    private RestClientClassStructureStorage buildRestClientClassStructureStorage(final EnvironmentContext environmentContext,
                                                                                 final Set<RestClientClassSignature> classSignatures,
                                                                                 final RestGenerationContext context) {
        final ExchangeFormat clientExchangeFormat =
                environmentContext.get(RestClientModuleGeneratorConfig.class).getExchangeFormatModule().getExchangeFormat();
        final List<MappedRestObjectModelClass> toHttpQueryModelClasses = new ArrayList<>();
        final List<MappedRestObjectModelClass> toHttpBodyModelClasses = new ArrayList<>();
        final List<MappedRestObjectModelClass> toHttpPathModelClasses = new ArrayList<>();
        separateModelClasses(
                context.getToHttpDataModelClasses(),
                toHttpQueryModelClasses,
                toHttpBodyModelClasses,
                toHttpPathModelClasses
        );
        final RestClientClassStructureStorage.Builder builder = new RestClientClassStructureStorage.Builder()
                .addModelReaders(
                        restClientModelReaderBuilder.build(
                                context.getFromHttpDataModelClasses(), classSignatures, clientExchangeFormat
                        )
                )
                .addModelFromJsonConverters(
                        restModelFromJsonConverterBuilder.buildFromJson(
                                context.getFromHttpDataModelClasses(), clientExchangeFormat, true
                        )
                )
                .addRequestModelExtractors(
                        requestModelExtractorClassStructureBuilder.build(toHttpQueryModelClasses)
                )
                .addPathBuilders(
                        pathBuilderClassStructureBuilder.build(toHttpPathModelClasses)
                )
                .addModelToJsonConverters(
                        restModelToJsonConverterBuilder.buildToJson(toHttpBodyModelClasses, clientExchangeFormat, true)
                );
        addValidators(environmentContext, context, builder);
        builder.addRestObjectModelClasses(context.getFromHttpDataModelClasses().stream().map(MappedRestObjectModelClass::getModelClass).collect(Collectors.toSet()));
        builder.addRestObjectModelClasses(context.getToHttpDataModelClasses().stream().map(MappedRestObjectModelClass::getModelClass).collect(Collectors.toSet()));
        return builder.build();
    }

    private void separateModelClasses(final List<MappedRestObjectModelClass> toHttpDataModelClasses,
                                      final List<MappedRestObjectModelClass> toHttpQueryModelClasses,
                                      final List<MappedRestObjectModelClass> toHttpBodyModelClasses,
                                      final List<MappedRestObjectModelClass> toHttpPathModelClasses) {
        for (final MappedRestObjectModelClass modelClass : toHttpDataModelClasses) {
            if (modelClass.getReaderType() == ReaderType.QUERY_STRING) {
                if (modelClass.getModelClass().isParamsPresent() ||
                        modelClass.getModelClass().isHeadersPresent()) {
                    toHttpQueryModelClasses.add(modelClass);
                }
            } else if (modelClass.getReaderType() == ReaderType.HTTP_BODY) {
                if (modelClass.getModelClass().isHeadersPresent()) {
                    toHttpQueryModelClasses.add(
                            modelClass.cloneUsingNewModelClass(
                                    ((RestClientObjectModelClass) modelClass.getModelClass()).cloneWithHeadersOnly()));
                }
                if (modelClass.getModelClass().isParamsPresent()) {
                    toHttpBodyModelClasses.add(modelClass);
                }
            } else if (modelClass.getModelClass().isParamsPresent() ||
                    modelClass.getModelClass().isHeadersPresent()) {
                toHttpQueryModelClasses.add(modelClass);
                toHttpBodyModelClasses.add(modelClass);
            }
            // path variables
            if (modelClass.getModelClass().isPathVariablesPresent()) {
                toHttpPathModelClasses.add(modelClass.cloneUsingNewModelClass(
                        ((RestClientObjectModelClass) modelClass.getModelClass()).cloneWithPathVariablesOnly()));
            }
        }
    }

    private void addValidators(final EnvironmentContext environmentContext,
                               final RestGenerationContext context,
                               final RestClientClassStructureStorage.Builder builder) {
        if (environmentContext.get(RestClientModuleGeneratorConfig.class).isGenerateRequestValidators()) {
            builder.addRequestValidators(
                    restModelValidatorBuilder.build(context.getToHttpDataModelClasses().stream()
                            .map(MappedRestObjectModelClass::getModelClass)
                            .filter(m -> isAnnotationPerPackageHierarchyAbsent(
                                    m.getModelTypeElement(), DisableValidation.class))
                            .collect(toList()))
            );
        }
        if (environmentContext.get(RestClientModuleGeneratorConfig.class).isGenerateResponseValidators()) {
            builder.addResponseValidators(
                    restModelValidatorBuilder.build(context.getFromHttpDataModelClasses().stream()
                            .map(MappedRestObjectModelClass::getModelClass)
                            .filter(m -> isAnnotationPerPackageHierarchyAbsent(
                                    m.getModelTypeElement(), DisableValidation.class))
                            .collect(toList()))
            );
        }
    }

    private List<ModuleInfoItem> buildModuleInfoItems(final EnvironmentContext environmentContext,
                                                      final Set<RestClientClassStructure> restClientClassStructures) {
        if (environmentContext.get(RestClientModuleGeneratorConfig.class).isGenerateRequiredModuleDirectives()) {
            final Set<String> allModulePackages = environmentContext.get(RestClientModuleGeneratorConfig.class).getAllModulePackages();
            final Set<String> packages = restClientClassStructures.stream()
                    .map(r -> getPackageName(r.getHttpClientConfigFullClassName().asType()))
                    .filter(allModulePackages::contains)
                    .collect(Collectors.toSet());
            return moduleInfoCustomizer.build(
                    environmentContext.getCurrentModule(),
                    packages.stream().map(p -> entry(p, RX_MICRO_RUNTIME_MODULE)).collect(toList()),
                    packages.stream().map(p -> entry(p, RX_MICRO_CONFIG_MODULE)).collect(toList())
            );
        } else {
            return List.of();
        }
    }
}
