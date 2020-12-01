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

package io.rxmicro.annotation.processor.rest.client;

import com.google.inject.Inject;
import io.rxmicro.annotation.processor.common.CommonDependenciesModule;
import io.rxmicro.annotation.processor.common.FormatSourceCodeDependenciesModule;
import io.rxmicro.annotation.processor.common.component.ModuleGeneratorConfigBuilder;
import io.rxmicro.annotation.processor.common.component.impl.AbstractModuleClassStructuresBuilder;
import io.rxmicro.annotation.processor.common.model.ClassStructure;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.common.util.Elements;
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
import io.rxmicro.annotation.processor.rest.model.HttpMethodMapping;
import io.rxmicro.annotation.processor.rest.model.MappedRestObjectModelClass;
import io.rxmicro.annotation.processor.rest.model.RestGenerationContext;
import io.rxmicro.annotation.processor.rest.model.VirtualTypeClassStructure;
import io.rxmicro.annotation.processor.rest.model.converter.ReaderType;
import io.rxmicro.rest.client.RestClient;
import io.rxmicro.rest.model.ExchangeFormat;
import io.rxmicro.validation.DisableValidation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;

import static io.rxmicro.annotation.processor.common.util.Injects.injectDependencies;
import static io.rxmicro.annotation.processor.common.util.LoggerMessages.DEFAULT_OFFSET;
import static io.rxmicro.annotation.processor.common.util.LoggerMessages.getLoggableMethodName;
import static io.rxmicro.common.util.Formats.format;
import static java.util.stream.Collectors.toList;

/**
 * @author nedis
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
    public String getBuilderName() {
        return "rest-client-annotation-processor-module";
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
                classStructures.add(new RestClientFactoryClassStructure(
                        restClientClassStructures,
                        restClientClassStructures.stream()
                                .flatMap(c -> c.getDefaultConfigValues().stream())
                                .collect(toList())
                ));
                addAllVirtualRequestClassStructures(classStructures, classSignatures, restClientClassStructureStorage);
                return classStructures;
            }
            return Set.of();
        } catch (final InterruptProcessingException ex) {
            error(ex);
            return Set.of();
        }
    }

    private void addAllVirtualRequestClassStructures(final Set<ClassStructure> classStructures,
                                                     final Set<RestClientClassSignature> classSignatures,
                                                     final RestClientClassStructureStorage restClientClassStructureStorage) {
        for (final RestClientClassSignature classSignature : classSignatures) {
            for (final RestClientMethodSignature methodSignature : classSignature.getMethodSignatures()) {
                methodSignature.getRequestModel().getRequestType()
                        .filter(Elements::isVirtualTypeElement)
                        .flatMap(t -> restClientClassStructureStorage.getModelClass(t.getQualifiedName().toString()))
                        .ifPresent(modelClass -> classStructures.add(new VirtualTypeClassStructure(modelClass, true)));
            }
        }
    }

    private void logFoundRestClients(final Set<RestClientClassSignature> set) {
        if (isInfoEnabled()) {
            final StringBuilder stringBuilder = new StringBuilder("Found the following REST clients:\n");
            for (final RestClientClassSignature signature : set) {
                stringBuilder.append(format("??:\n", DEFAULT_OFFSET, signature.getRestClientInterface().getQualifiedName()));
                for (final RestClientMethodSignature methodSignature : signature.getMethodSignatures()) {
                    for (final HttpMethodMapping httpMethodMapping : methodSignature.getHttpMethodMappings()) {
                        stringBuilder.append(format(
                                "??'? ?' -> ?;\n",
                                DEFAULT_OFFSET,
                                DEFAULT_OFFSET,
                                httpMethodMapping.getMethod(),
                                httpMethodMapping.getExactOrTemplateUri(),
                                getLoggableMethodName(methodSignature.getMethod())
                        ));
                    }
                }
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            info(stringBuilder.toString());
        }
    }

    private RestClientClassStructureStorage buildRestClientClassStructureStorage(final EnvironmentContext environmentContext,
                                                                                 final Set<RestClientClassSignature> classSignatures,
                                                                                 final RestGenerationContext context) {
        final ExchangeFormat clientExchangeFormat =
                environmentContext.get(RestClientModuleGeneratorConfig.class).getExchangeFormatModule().getExchangeFormat();
        final List<MappedRestObjectModelClass> toHttpQueryModelClasses = new ArrayList<>();
        final List<MappedRestObjectModelClass> toHttpBodyModelClasses = new ArrayList<>();
        final List<MappedRestObjectModelClass> toHttpPathModelClasses = new ArrayList<>();
        separateModelClasses(context.getToHttpDataModelClasses(), toHttpQueryModelClasses, toHttpBodyModelClasses, toHttpPathModelClasses);

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
        builder.addRestObjectModelClasses(
                context.getFromHttpDataModelClasses().stream().map(MappedRestObjectModelClass::getModelClass).collect(Collectors.toSet())
        );
        builder.addRestObjectModelClasses(
                context.getToHttpDataModelClasses().stream().map(MappedRestObjectModelClass::getModelClass).collect(Collectors.toSet())
        );
        logRestClientClassStructureStorage(builder);
        return builder.build();
    }

    private void separateModelClasses(final List<MappedRestObjectModelClass> toHttpDataModelClasses,
                                      final List<MappedRestObjectModelClass> toHttpQueryModelClasses,
                                      final List<MappedRestObjectModelClass> toHttpBodyModelClasses,
                                      final List<MappedRestObjectModelClass> toHttpPathModelClasses) {
        for (final MappedRestObjectModelClass modelClass : toHttpDataModelClasses) {
            if (modelClass.getReaderType() == ReaderType.QUERY_STRING) {
                if (modelClass.getModelClass().isParamEntriesPresent() ||
                        modelClass.getModelClass().isHeadersPresent()) {
                    toHttpQueryModelClasses.add(modelClass);
                }
            } else if (modelClass.getReaderType() == ReaderType.HTTP_BODY) {
                if (modelClass.getModelClass().isHeadersPresent()) {
                    toHttpQueryModelClasses.add(
                            modelClass.cloneUsingNewModelClass(
                                    ((RestClientObjectModelClass) modelClass.getModelClass()).cloneWithHeadersOnly()));
                }
                if (modelClass.getModelClass().isParamEntriesPresent()) {
                    toHttpBodyModelClasses.add(modelClass);
                }
            } else if (modelClass.getModelClass().isParamEntriesPresent() ||
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

    private void logRestClientClassStructureStorage(final RestClientClassStructureStorage.Builder builder) {
        if (isDebugEnabled()) {
            logClassStructureStorageItem("path variable builder(s)", builder.getPathBuilders());
            logClassStructureStorageItem("request model extractor(s)", builder.getRequestModelExtractors());
            logClassStructureStorageItem("request model converter(s)", builder.getModelToJsonConverters());
            logClassStructureStorageItem("request validator(s)", builder.getRequestValidators());

            logClassStructureStorageItem("response model reader", builder.getModelReaders());
            logClassStructureStorageItem("response model converter(s)", builder.getModelFromJsonConverters());
            logClassStructureStorageItem("response validator(s)", builder.getResponseValidators());
        }
    }
}
