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

package io.rxmicro.annotation.processor.rest.server;

import com.google.inject.Inject;
import io.rxmicro.annotation.processor.common.CommonDependenciesModule;
import io.rxmicro.annotation.processor.common.FormatSourceCodeDependenciesModule;
import io.rxmicro.annotation.processor.common.component.ModuleGeneratorConfigBuilder;
import io.rxmicro.annotation.processor.common.component.WithParentClassStructureInitializer;
import io.rxmicro.annotation.processor.common.component.impl.AbstractModuleClassStructuresBuilder;
import io.rxmicro.annotation.processor.common.model.ClassStructure;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingBecauseAFewErrorsFoundException;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.common.util.Elements;
import io.rxmicro.annotation.processor.rest.RestCommonDependenciesModule;
import io.rxmicro.annotation.processor.rest.component.RestGenerationContextBuilder;
import io.rxmicro.annotation.processor.rest.component.RestModelFromJsonConverterBuilder;
import io.rxmicro.annotation.processor.rest.component.RestModelToJsonConverterBuilder;
import io.rxmicro.annotation.processor.rest.component.RestModelValidatorBuilder;
import io.rxmicro.annotation.processor.rest.model.HttpMethodMapping;
import io.rxmicro.annotation.processor.rest.model.MappedRestObjectModelClass;
import io.rxmicro.annotation.processor.rest.model.RestClassSignature;
import io.rxmicro.annotation.processor.rest.model.RestGenerationContext;
import io.rxmicro.annotation.processor.rest.model.VirtualTypeClassStructure;
import io.rxmicro.annotation.processor.rest.server.component.CrossOriginResourceSharingResourceBuilder;
import io.rxmicro.annotation.processor.rest.server.component.CustomExceptionMappedRestObjectModelClassBuilder;
import io.rxmicro.annotation.processor.rest.server.component.DeclaredStaticResourcesResolver;
import io.rxmicro.annotation.processor.rest.server.component.HttpHealthCheckBuilder;
import io.rxmicro.annotation.processor.rest.server.component.ModelReaderBuilder;
import io.rxmicro.annotation.processor.rest.server.component.ModelWriterBuilder;
import io.rxmicro.annotation.processor.rest.server.component.ParentModelVirtualClassSignatureBuilder;
import io.rxmicro.annotation.processor.rest.server.component.RestControllerClassSignatureBuilder;
import io.rxmicro.annotation.processor.rest.server.component.RestControllerClassStructureBuilder;
import io.rxmicro.annotation.processor.rest.server.component.RestDocumentationGenerator;
import io.rxmicro.annotation.processor.rest.server.model.CustomExceptionServerModelWritersCustomizerClassStructure;
import io.rxmicro.annotation.processor.rest.server.model.DeclaredStaticResources;
import io.rxmicro.annotation.processor.rest.server.model.RestControllerAggregatorClassStructure;
import io.rxmicro.annotation.processor.rest.server.model.RestControllerClassSignature;
import io.rxmicro.annotation.processor.rest.server.model.RestControllerClassStructure;
import io.rxmicro.annotation.processor.rest.server.model.RestControllerClassStructureStorage;
import io.rxmicro.annotation.processor.rest.server.model.RestControllerMethodSignature;
import io.rxmicro.annotation.processor.rest.server.model.RestServerModuleGeneratorConfig;
import io.rxmicro.rest.model.ExchangeFormat;
import io.rxmicro.rest.server.ServerHttpError;
import io.rxmicro.rest.server.ServerRequest;
import io.rxmicro.rest.server.ServerResponse;
import io.rxmicro.rest.server.StaticResources;
import io.rxmicro.validation.DisableValidation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;

import static io.rxmicro.annotation.processor.common.util.Injects.injectDependencies;
import static io.rxmicro.annotation.processor.common.util.LoggerMessages.DEFAULT_OFFSET;
import static io.rxmicro.annotation.processor.common.util.LoggerMessages.getLoggableMethodName;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.rest.method.HttpMethods.HTTP_METHOD_ANNOTATIONS;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 * @author nedis
 * @since 0.1
 */
public final class RestServerModuleClassStructuresBuilder extends AbstractModuleClassStructuresBuilder {

    private static final Set<String> PARENT_MODEL_ANNOTATION_NAMES = Set.of(
            ServerRequest.class.getName(),
            ServerResponse.class.getName()
    );

    @Inject
    private ModuleGeneratorConfigBuilder<RestServerModuleGeneratorConfig> restServerModuleGeneratorConfigBuilder;

    @Inject
    private RestControllerClassSignatureBuilder restControllerClassSignatureBuilder;

    @Inject
    private RestGenerationContextBuilder restGenerationContextBuilder;

    @Inject
    private RestControllerClassStructureBuilder restControllerClassStructureBuilder;

    @Inject
    private ModelReaderBuilder modelReaderBuilder;

    @Inject
    private ModelWriterBuilder modelWriterBuilder;

    @Inject
    private CustomExceptionMappedRestObjectModelClassBuilder customExceptionMappedRestObjectModelClassBuilder;

    @Inject
    private RestModelValidatorBuilder restModelValidatorBuilder;

    @Inject
    private RestModelToJsonConverterBuilder restModelToJsonConverterBuilder;

    @Inject
    private RestModelFromJsonConverterBuilder restModelFromJsonConverterBuilder;

    @Inject
    private CrossOriginResourceSharingResourceBuilder crossOriginResourceSharingResourceBuilder;

    @Inject
    private HttpHealthCheckBuilder httpHealthCheckBuilder;

    @Inject
    private RestDocumentationGenerator restDocumentationGenerator;

    @Inject
    private WithParentClassStructureInitializer withParentClassStructureInitializer;

    @Inject
    private DeclaredStaticResourcesResolver declaredStaticResourcesResolver;

    @Inject
    private ParentModelVirtualClassSignatureBuilder parentModelVirtualClassSignatureBuilder;

    public static RestServerModuleClassStructuresBuilder create() {
        final RestServerModuleClassStructuresBuilder builder = new RestServerModuleClassStructuresBuilder();
        injectDependencies(
                builder,
                new FormatSourceCodeDependenciesModule(),
                new CommonDependenciesModule(),
                new RestCommonDependenciesModule(),
                new RestServerDependenciesModule()
        );
        return builder;
    }

    private RestServerModuleClassStructuresBuilder() {
    }

    @Override
    public String getBuilderName() {
        return "rest-server-annotation-processor-module";
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Stream
                .of(
                        HTTP_METHOD_ANNOTATIONS.stream().map(Class::getName),
                        Stream.of(
                                io.rxmicro.rest.method.GET.List.class,
                                io.rxmicro.rest.method.POST.List.class,
                                io.rxmicro.rest.method.PUT.List.class,
                                io.rxmicro.rest.method.DELETE.List.class,
                                io.rxmicro.rest.method.PATCH.List.class,
                                io.rxmicro.rest.method.OPTIONS.List.class,
                                io.rxmicro.rest.method.HEAD.List.class
                        ).map(cl -> cl.getName().replace("$", ".")),
                        Stream.of(
                                StaticResources.class.getName(),
                                StaticResources.List.class.getName().replace("$", ".")
                        ),
                        PARENT_MODEL_ANNOTATION_NAMES.stream(),
                        Stream.of(ServerHttpError.class.getName())
                )
                .flatMap(identity())
                .collect(Collectors.toSet());
    }

    @Override
    public Set<? extends ClassStructure> buildClassStructures(final EnvironmentContext environmentContext,
                                                              final Set<? extends TypeElement> annotations,
                                                              final RoundEnvironment roundEnv) {
        try {
            final List<TypeElement> httpMethodAnnotations = new ArrayList<>();
            final List<TypeElement> staticResourceAnnotations = new ArrayList<>();
            final List<TypeElement> parentModelsAnnotations = new ArrayList<>();
            boolean hasServerHttpErrors = false;
            for (final TypeElement annotation : annotations) {
                final String annotationFullClassName = annotation.getQualifiedName().toString();
                if (ServerHttpError.class.getName().equals(annotationFullClassName)) {
                    hasServerHttpErrors = true;
                } else if (PARENT_MODEL_ANNOTATION_NAMES.contains(annotationFullClassName)) {
                    parentModelsAnnotations.add(annotation);
                } else if (annotationFullClassName.startsWith(StaticResources.class.getName())) {
                    staticResourceAnnotations.add(annotation);
                } else {
                    httpMethodAnnotations.add(annotation);
                }
            }

            final Set<RestClassSignature> classSignatures = new HashSet<>();
            classSignatures.addAll(
                    restControllerClassSignatureBuilder.build(environmentContext, httpMethodAnnotations, roundEnv)
            );
            classSignatures.addAll(
                    parentModelVirtualClassSignatureBuilder.buildVirtualSignatures(environmentContext, parentModelsAnnotations, roundEnv)
            );
            final DeclaredStaticResources declaredStaticResources =
                    declaredStaticResourcesResolver.resolve(environmentContext, staticResourceAnnotations, roundEnv);

            return buildClassStructures(environmentContext, classSignatures, declaredStaticResources, hasServerHttpErrors);
        } catch (final InterruptProcessingException ex) {
            error(ex);
            return Set.of();
        } catch (final InterruptProcessingBecauseAFewErrorsFoundException ignored) {
            // do nothing, because all errors already printed
            return Set.of();
        }
    }

    private Set<ClassStructure> buildClassStructures(final EnvironmentContext environmentContext,
                                                     final Set<RestClassSignature> classSignatures,
                                                     final DeclaredStaticResources declaredStaticResources,
                                                     final boolean hasServerHttpErrors) {

        logFoundRestControllers(classSignatures);
        logFoundStaticResources(declaredStaticResources);
        final Set<ClassStructure> classStructures = new HashSet<>();
        if (!classSignatures.isEmpty()) {
            addAllClassStructures(classStructures, environmentContext, classSignatures, declaredStaticResources);
        } else if (declaredStaticResources.exist()) {
            classStructures.add(
                    new RestControllerAggregatorClassStructure.Builder()
                            .setEnvironmentContext(environmentContext)
                            .setDeclaredStaticResources(declaredStaticResources)
                            .setHttpHealthChecks(httpHealthCheckBuilder.build(environmentContext, Set.of()))
                            .build()
            );
        } else if (hasServerHttpErrors) {
            addCustomErrorsOnly(classStructures, environmentContext, classSignatures);
        }
        return classStructures;
    }

    private void addAllClassStructures(final Set<ClassStructure> classStructures,
                                       final EnvironmentContext environmentContext,
                                       final Set<RestClassSignature> classSignatures,
                                       final DeclaredStaticResources declaredStaticResources) {
        environmentContext.put(restServerModuleGeneratorConfigBuilder.build(environmentContext));

        final RestGenerationContext restGenerationContext =
                restGenerationContextBuilder.build(environmentContext, RestServerModuleGeneratorConfig.class, classSignatures);
        final RestControllerClassStructureStorage restControllerClassStructureStorage =
                buildRestClassStructureStorage(environmentContext, restGenerationContext);
        classStructures.addAll(restControllerClassStructureStorage.getAll());
        // Exclude virtual signatures: VirtualRestControllerClassSignature
        final Set<RestControllerClassSignature> restControllerClassSignatures = classSignatures.stream()
                .filter(s -> s instanceof RestControllerClassSignature)
                .map(s -> (RestControllerClassSignature) s)
                .collect(Collectors.toSet());

        final Set<RestControllerClassStructure> restControllerClassStructures = restControllerClassStructureBuilder.build(
                environmentContext, restControllerClassStructureStorage, restControllerClassSignatures
        );
        classStructures.addAll(restControllerClassStructures);
        if (!environmentContext.get(RestServerModuleGeneratorConfig.class).getDocumentationTypes().isEmpty()) {
            restDocumentationGenerator.generate(environmentContext, restControllerClassStructureStorage, restControllerClassStructures);
        }
        addAllVirtualRequestClassStructures(classStructures, restControllerClassSignatures, restControllerClassStructureStorage);

        classStructures.add(
                new CustomExceptionServerModelWritersCustomizerClassStructure(
                        environmentContext.getCurrentModule(),
                        restControllerClassStructureStorage.getCustomExceptionModelWriters()
                )
        );
        classStructures.add(
                new RestControllerAggregatorClassStructure.Builder()
                        .setEnvironmentContext(environmentContext)
                        .setClassStructures(restControllerClassStructures)
                        .setCrossOriginResourceSharingResources(
                                crossOriginResourceSharingResourceBuilder.build(restControllerClassStructures, restGenerationContext)
                        )
                        .setHttpHealthChecks(httpHealthCheckBuilder.build(environmentContext, restControllerClassStructures))
                        .setDeclaredStaticResources(declaredStaticResources)
                        .build()
        );
    }

    private void addCustomErrorsOnly(final Set<ClassStructure> classStructures,
                                     final EnvironmentContext environmentContext,
                                     final Set<RestClassSignature> classSignatures) {
        environmentContext.put(restServerModuleGeneratorConfigBuilder.build(environmentContext));

        final RestGenerationContext restGenerationContext =
                restGenerationContextBuilder.build(environmentContext, RestServerModuleGeneratorConfig.class, classSignatures);
        final RestControllerClassStructureStorage restControllerClassStructureStorage =
                buildRestClassStructureStorage(environmentContext, restGenerationContext);
        classStructures.addAll(restControllerClassStructureStorage.getAll());
        classStructures.add(
                new CustomExceptionServerModelWritersCustomizerClassStructure(
                        environmentContext.getCurrentModule(),
                        restControllerClassStructureStorage.getCustomExceptionModelWriters()
                )
        );
    }

    private void logFoundStaticResources(final DeclaredStaticResources declaredStaticResources) {
        if (isInfoEnabled() && declaredStaticResources.exist()) {
            info(
                    "Found the following static resources:\n?",
                    declaredStaticResources.getStaticUrls().stream()
                            .map(url -> format("?'GET ?'", DEFAULT_OFFSET, url))
                            .collect(joining("\n"))
            );
        }
    }

    private void logFoundRestControllers(final Set<RestClassSignature> set) {
        if (isInfoEnabled() && !set.isEmpty()) {
            final Set<RestControllerClassSignature> restControllerClassSignatures = set.stream()
                    .filter(s -> s instanceof RestControllerClassSignature)
                    .map(s -> (RestControllerClassSignature) s)
                    .collect(Collectors.toSet());
            if (!restControllerClassSignatures.isEmpty()) {
                final StringBuilder stringBuilder = new StringBuilder("Found the following REST controllers:\n");
                for (final RestControllerClassSignature signature : restControllerClassSignatures) {
                    stringBuilder.append(format("??:\n", DEFAULT_OFFSET, signature.getTypeElement().getQualifiedName()));
                    for (final RestControllerMethodSignature methodSignature : signature.getMethodSignatures()) {
                        for (final HttpMethodMapping httpMethodMapping : methodSignature.getHttpMethodMappings()) {
                            stringBuilder.append(format(
                                    "??'? ?' -> ?;\n",
                                    DEFAULT_OFFSET,
                                    DEFAULT_OFFSET,
                                    httpMethodMapping.getMethod(),
                                    httpMethodMapping.getExactOrTemplateUri(),
                                    getLoggableMethodName(methodSignature.getExecutableElement())
                            ));
                        }
                    }
                }
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                info(stringBuilder.toString());
            }
        }
    }

    private RestControllerClassStructureStorage buildRestClassStructureStorage(final EnvironmentContext environmentContext,
                                                                               final RestGenerationContext generationContext) {
        final ExchangeFormat exchangeFormat =
                environmentContext.get(RestServerModuleGeneratorConfig.class).getExchangeFormatModule().getExchangeFormat();
        final List<MappedRestObjectModelClass> customExceptionModels =
                customExceptionMappedRestObjectModelClassBuilder.build(environmentContext);
        final RestControllerClassStructureStorage.Builder builder = new RestControllerClassStructureStorage.Builder();
        addValidators(environmentContext, generationContext, customExceptionModels, builder);
        addModelReadersAndWriters(environmentContext, exchangeFormat, customExceptionModels, generationContext, builder);
        addModelJsonConverters(environmentContext, exchangeFormat, customExceptionModels, generationContext, builder);

        setParentsForExistingChildren(builder);
        logRestControllerClassStructureStorage(builder);
        return builder.build();
    }

    private void addModelReadersAndWriters(final EnvironmentContext environmentContext,
                                           final ExchangeFormat exchangeFormat,
                                           final List<MappedRestObjectModelClass> customExceptionModels,
                                           final RestGenerationContext generationContext,
                                           final RestControllerClassStructureStorage.Builder builder) {
        builder
                .addModelReaders(
                        modelReaderBuilder.build(environmentContext, generationContext.getFromHttpDataModelClasses(), exchangeFormat)
                )
                .addModelWriters(
                        modelWriterBuilder.build(environmentContext, generationContext.getToHttpDataModelClasses(), exchangeFormat)
                )
                .addCustomExceptionModelWriters(
                        modelWriterBuilder.build(environmentContext, customExceptionModels, exchangeFormat)
                );
    }

    private void addModelJsonConverters(final EnvironmentContext environmentContext,
                                        final ExchangeFormat exchangeFormat,
                                        final List<MappedRestObjectModelClass> customExceptionModels,
                                        final RestGenerationContext generationContext,
                                        final RestControllerClassStructureStorage.Builder builder) {
        builder
                .addModelFromJsonConverters(
                        restModelFromJsonConverterBuilder.buildFromJson(
                                environmentContext.getCurrentModule(),
                                generationContext.getFromHttpDataModelClasses(), exchangeFormat, false
                        )
                )
                .addModelToJsonConverters(
                        restModelToJsonConverterBuilder.buildToJson(
                                environmentContext.getCurrentModule(),
                                generationContext.getToHttpDataModelClasses(), exchangeFormat, false
                        )
                )
                .addModelToJsonConverters(
                        restModelToJsonConverterBuilder.buildToJson(
                                environmentContext.getCurrentModule(),
                                customExceptionModels, exchangeFormat, false
                        )
                );
    }

    private void addValidators(final EnvironmentContext environmentContext,
                               final RestGenerationContext generationContext,
                               final List<MappedRestObjectModelClass> customExceptionModels,
                               final RestControllerClassStructureStorage.Builder builder) {
        if (environmentContext.get(RestServerModuleGeneratorConfig.class).isGenerateRequestValidators()) {
            builder.addRequestValidators(
                    restModelValidatorBuilder.build(environmentContext, generationContext.getFromHttpDataModelClasses().stream()
                            .map(MappedRestObjectModelClass::getModelClass)
                            .filter(m -> isAnnotationPerPackageHierarchyAbsent(m.getModelTypeElement(), DisableValidation.class))
                            .collect(toList())
                    )
            );
        }
        if (environmentContext.get(RestServerModuleGeneratorConfig.class).isGenerateResponseValidators()) {
            builder.addResponseValidators(
                    restModelValidatorBuilder.build(environmentContext, generationContext.getToHttpDataModelClasses().stream()
                            .map(MappedRestObjectModelClass::getModelClass)
                            .filter(m -> isAnnotationPerPackageHierarchyAbsent(m.getModelTypeElement(), DisableValidation.class))
                            .collect(toList())
                    )
            );
            builder.addCustomExceptionModelValidators(
                    restModelValidatorBuilder.build(environmentContext, customExceptionModels.stream()
                            .map(MappedRestObjectModelClass::getModelClass)
                            .filter(m -> isAnnotationPerPackageHierarchyAbsent(m.getModelTypeElement(), DisableValidation.class))
                            .collect(toList())
                    )
            );
        }
    }

    private void setParentsForExistingChildren(final RestControllerClassStructureStorage.Builder builder) {
        withParentClassStructureInitializer.setParentIfExists(builder.getRequestValidators());
        withParentClassStructureInitializer.setParentIfExists(builder.getResponseValidators());
        withParentClassStructureInitializer.setParentIfExists(builder.getModelReaders());
        withParentClassStructureInitializer.setParentIfExists(builder.getModelWriters());
        withParentClassStructureInitializer.setParentIfExists(builder.getModelFromJsonConverters());
        withParentClassStructureInitializer.setParentIfExists(builder.getModelToJsonConverters());
    }

    private void logRestControllerClassStructureStorage(final RestControllerClassStructureStorage.Builder builder) {
        if (isDebugEnabled()) {
            logClassStructureStorageItem("request model reader(s)", builder.getModelReaders());
            logClassStructureStorageItem("request model converter(s)", builder.getModelFromJsonConverters());
            logClassStructureStorageItem("request validator(s)", builder.getRequestValidators());

            logClassStructureStorageItem("response model writer(s)", builder.getModelWriters());
            logClassStructureStorageItem("response model converter(s)", builder.getModelToJsonConverters());
            logClassStructureStorageItem("response validator(s)", builder.getResponseValidators());
        }
    }

    private void addAllVirtualRequestClassStructures(final Set<ClassStructure> classStructures,
                                                     final Set<RestControllerClassSignature> classSignatures,
                                                     final RestControllerClassStructureStorage restControllerClassStructureStorage) {
        for (final RestControllerClassSignature classSignature : classSignatures) {
            for (final RestControllerMethodSignature methodSignature : classSignature.getMethodSignatures()) {
                methodSignature.getRequestModel().getRequestType()
                        .filter(Elements::isVirtualTypeElement)
                        .flatMap(t -> restControllerClassStructureStorage.getModelReaderClassStructure(t.getQualifiedName().toString()))
                        .ifPresent(mr -> classStructures.add(new VirtualTypeClassStructure(mr.getModelClass(), false)));
            }
        }
    }
}
