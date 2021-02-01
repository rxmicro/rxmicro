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
import io.rxmicro.annotation.processor.rest.model.RestGenerationContext;
import io.rxmicro.annotation.processor.rest.model.VirtualTypeClassStructure;
import io.rxmicro.annotation.processor.rest.server.component.CrossOriginResourceSharingResourceBuilder;
import io.rxmicro.annotation.processor.rest.server.component.CustomExceptionMappedRestObjectModelClassBuilder;
import io.rxmicro.annotation.processor.rest.server.component.DeclaredStaticResourcesResolver;
import io.rxmicro.annotation.processor.rest.server.component.HttpHealthCheckBuilder;
import io.rxmicro.annotation.processor.rest.server.component.ModelReaderBuilder;
import io.rxmicro.annotation.processor.rest.server.component.ModelWriterBuilder;
import io.rxmicro.annotation.processor.rest.server.component.RestControllerClassSignatureBuilder;
import io.rxmicro.annotation.processor.rest.server.component.RestControllerClassStructureBuilder;
import io.rxmicro.annotation.processor.rest.server.component.RestDocumentationGenerator;
import io.rxmicro.annotation.processor.rest.server.model.DeclaredStaticResources;
import io.rxmicro.annotation.processor.rest.server.model.RestControllerAggregatorClassStructure;
import io.rxmicro.annotation.processor.rest.server.model.RestControllerClassSignature;
import io.rxmicro.annotation.processor.rest.server.model.RestControllerClassStructure;
import io.rxmicro.annotation.processor.rest.server.model.RestControllerClassStructureStorage;
import io.rxmicro.annotation.processor.rest.server.model.RestControllerMethodSignature;
import io.rxmicro.annotation.processor.rest.server.model.RestServerModuleGeneratorConfig;
import io.rxmicro.rest.model.ExchangeFormat;
import io.rxmicro.rest.server.StaticResources;
import io.rxmicro.validation.DisableValidation;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 * @author nedis
 * @since 0.1
 */
public final class RestServerModuleClassStructuresBuilder extends AbstractModuleClassStructuresBuilder {

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
                        )
                )
                .flatMap(identity())
                .collect(Collectors.toSet());
    }

    @Override
    public Set<? extends ClassStructure> buildClassStructures(final EnvironmentContext environmentContext,
                                                              final Set<? extends TypeElement> annotations,
                                                              final RoundEnvironment roundEnv) {
        try {
            final Map<Boolean, List<TypeElement>> groups = annotations.stream()
                    .collect(groupingBy(typeElement ->
                            typeElement.getQualifiedName().toString().startsWith(StaticResources.class.getName())
                    ));
            final List<? extends TypeElement> httpMethodAnnotations = Optional.ofNullable(groups.get(false)).orElse(List.of());
            final List<? extends TypeElement> staticResourceAnnotations = Optional.ofNullable(groups.get(true)).orElse(List.of());

            final Set<RestControllerClassSignature> classSignatures =
                    restControllerClassSignatureBuilder.build(environmentContext, httpMethodAnnotations, roundEnv);
            final DeclaredStaticResources declaredStaticResources =
                    declaredStaticResourcesResolver.resolve(environmentContext, staticResourceAnnotations, roundEnv);

            if (!classSignatures.isEmpty()) {
                return buildClassStructures(environmentContext, classSignatures, declaredStaticResources);
            } else if (declaredStaticResources.exist()) {
                logFoundStaticResources(declaredStaticResources);
                return Set.of(
                        new RestControllerAggregatorClassStructure(
                                environmentContext,
                                Set.of(),
                                Set.of(),
                                httpHealthCheckBuilder.build(environmentContext, Set.of()),
                                declaredStaticResources,
                                Set.of()
                        )
                );
            } else {
                return Set.of();
            }
        } catch (final InterruptProcessingException ex) {
            error(ex);
            return Set.of();
        } catch (final InterruptProcessingBecauseAFewErrorsFoundException ignore) {
            // do nothing, because all errors already printed
            return Set.of();
        }
    }

    private Set<ClassStructure> buildClassStructures(final EnvironmentContext environmentContext,
                                                     final Set<RestControllerClassSignature> classSignatures,
                                                     final DeclaredStaticResources declaredStaticResources) {
        environmentContext.put(restServerModuleGeneratorConfigBuilder.build(environmentContext));
        logFoundRestControllers(classSignatures);
        if (declaredStaticResources.exist()) {
            logFoundStaticResources(declaredStaticResources);
        }
        final RestGenerationContext restGenerationContext =
                restGenerationContextBuilder.build(environmentContext, RestServerModuleGeneratorConfig.class, classSignatures);
        final RestControllerClassStructureStorage restControllerClassStructureStorage =
                buildRestClassStructureStorage(environmentContext, restGenerationContext);
        final Set<ClassStructure> classStructures = new HashSet<>(restControllerClassStructureStorage.getAll());
        final Set<RestControllerClassStructure> restControllerClassStructures =
                restControllerClassStructureBuilder.build(environmentContext, restControllerClassStructureStorage, classSignatures);
        classStructures.addAll(restControllerClassStructures);
        classStructures.add(new RestControllerAggregatorClassStructure(
                environmentContext,
                restControllerClassStructures,
                crossOriginResourceSharingResourceBuilder.build(restControllerClassStructures, restGenerationContext),
                httpHealthCheckBuilder.build(environmentContext, restControllerClassStructures),
                declaredStaticResources,
                restControllerClassStructureStorage.getCustomExceptionModelWriters()
        ));
        if (!environmentContext.get(RestServerModuleGeneratorConfig.class).getDocumentationTypes().isEmpty()) {
            restDocumentationGenerator.generate(environmentContext, restControllerClassStructureStorage, restControllerClassStructures);
        }
        addAllVirtualRequestClassStructures(classStructures, classSignatures, restControllerClassStructureStorage);
        return classStructures;
    }

    private void logFoundStaticResources(final DeclaredStaticResources declaredStaticResources) {
        info(
                "Found the following static resources:\n?",
                declaredStaticResources.getStaticUrls().stream()
                        .map(url -> format("?'GET ?'", DEFAULT_OFFSET, url))
                        .collect(joining("\n"))
        );
    }

    private void logFoundRestControllers(final Set<RestControllerClassSignature> set) {
        if (isInfoEnabled()) {
            final StringBuilder stringBuilder = new StringBuilder("Found the following REST controllers:\n");
            for (final RestControllerClassSignature signature : set) {
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

    private RestControllerClassStructureStorage buildRestClassStructureStorage(final EnvironmentContext environmentContext,
                                                                               final RestGenerationContext generationContext) {
        final ExchangeFormat exchangeFormat =
                environmentContext.get(RestServerModuleGeneratorConfig.class).getExchangeFormatModule().getExchangeFormat();
        final List<MappedRestObjectModelClass> customExceptionModels =
                customExceptionMappedRestObjectModelClassBuilder.build(environmentContext);
        final RestControllerClassStructureStorage.Builder builder = new RestControllerClassStructureStorage.Builder();
        addValidators(environmentContext, generationContext, customExceptionModels, builder);
        addModelReadersAndWriters(exchangeFormat, customExceptionModels, generationContext, builder);
        addModelJsonConverters(exchangeFormat, customExceptionModels, generationContext, builder);

        setParentsForExistingChildren(builder);
        logRestControllerClassStructureStorage(builder);
        return builder.build();
    }

    private void addModelReadersAndWriters(final ExchangeFormat exchangeFormat,
                                           final List<MappedRestObjectModelClass> customExceptionModels,
                                           final RestGenerationContext generationContext,
                                           final RestControllerClassStructureStorage.Builder builder) {
        builder
                .addModelReaders(
                        modelReaderBuilder.build(generationContext.getFromHttpDataModelClasses(), exchangeFormat)
                )
                .addModelWriters(
                        modelWriterBuilder.build(generationContext.getToHttpDataModelClasses(), exchangeFormat)
                )
                .addCustomExceptionModelWriters(
                        modelWriterBuilder.build(customExceptionModels, exchangeFormat)
                );
    }

    private void addModelJsonConverters(final ExchangeFormat exchangeFormat,
                                        final List<MappedRestObjectModelClass> customExceptionModels,
                                        final RestGenerationContext generationContext,
                                        final RestControllerClassStructureStorage.Builder builder) {
        builder
                .addModelFromJsonConverters(
                        restModelFromJsonConverterBuilder.buildFromJson(
                                generationContext.getFromHttpDataModelClasses(), exchangeFormat, false
                        )
                )
                .addModelToJsonConverters(
                        restModelToJsonConverterBuilder.buildToJson(
                                generationContext.getToHttpDataModelClasses(), exchangeFormat, false
                        )
                )
                .addModelToJsonConverters(
                        restModelToJsonConverterBuilder.buildToJson(customExceptionModels, exchangeFormat, false)
                );
    }

    private void addValidators(final EnvironmentContext environmentContext,
                               final RestGenerationContext generationContext,
                               final List<MappedRestObjectModelClass> customExceptionModels,
                               final RestControllerClassStructureStorage.Builder builder) {
        if (environmentContext.get(RestServerModuleGeneratorConfig.class).isGenerateRequestValidators()) {
            builder.addRequestValidators(
                    restModelValidatorBuilder.build(generationContext.getFromHttpDataModelClasses().stream()
                            .map(MappedRestObjectModelClass::getModelClass)
                            .filter(m -> isAnnotationPerPackageHierarchyAbsent(m.getModelTypeElement(), DisableValidation.class))
                            .collect(toList())
                    )
            );
        }
        if (environmentContext.get(RestServerModuleGeneratorConfig.class).isGenerateResponseValidators()) {
            builder.addResponseValidators(
                    restModelValidatorBuilder.build(generationContext.getToHttpDataModelClasses().stream()
                            .map(MappedRestObjectModelClass::getModelClass)
                            .filter(m -> isAnnotationPerPackageHierarchyAbsent(m.getModelTypeElement(), DisableValidation.class))
                            .collect(toList())
                    )
            );
            builder.addCustomExceptionModelValidators(
                    restModelValidatorBuilder.build(customExceptionModels.stream()
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
