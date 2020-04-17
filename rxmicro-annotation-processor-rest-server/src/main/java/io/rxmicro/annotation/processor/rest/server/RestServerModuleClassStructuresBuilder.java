/*
 * Copyright 2019 https://rxmicro.io
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
import io.rxmicro.annotation.processor.common.component.impl.AbstractModuleClassStructuresBuilder;
import io.rxmicro.annotation.processor.common.model.ClassStructure;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.common.model.EnvironmentCustomizerClassStructure;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.common.model.virtual.VirtualTypeElement;
import io.rxmicro.annotation.processor.rest.RestCommonDependenciesModule;
import io.rxmicro.annotation.processor.rest.component.RestGenerationContextBuilder;
import io.rxmicro.annotation.processor.rest.component.RestModelFromJsonConverterBuilder;
import io.rxmicro.annotation.processor.rest.component.RestModelToJsonConverterBuilder;
import io.rxmicro.annotation.processor.rest.component.RestModelValidatorBuilder;
import io.rxmicro.annotation.processor.rest.model.MappedRestObjectModelClass;
import io.rxmicro.annotation.processor.rest.model.RestGenerationContext;
import io.rxmicro.annotation.processor.rest.model.VirtualTypeClassStructure;
import io.rxmicro.annotation.processor.rest.server.component.CrossOriginResourceSharingResourceBuilder;
import io.rxmicro.annotation.processor.rest.server.component.HttpHealthCheckBuilder;
import io.rxmicro.annotation.processor.rest.server.component.ModelReaderBuilder;
import io.rxmicro.annotation.processor.rest.server.component.ModelWriterBuilder;
import io.rxmicro.annotation.processor.rest.server.component.RestControllerClassSignatureBuilder;
import io.rxmicro.annotation.processor.rest.server.component.RestControllerClassStructureBuilder;
import io.rxmicro.annotation.processor.rest.server.component.RestDocumentationGenerator;
import io.rxmicro.annotation.processor.rest.server.model.RestControllerAggregatorClassStructure;
import io.rxmicro.annotation.processor.rest.server.model.RestControllerClassSignature;
import io.rxmicro.annotation.processor.rest.server.model.RestControllerClassStructure;
import io.rxmicro.annotation.processor.rest.server.model.RestControllerClassStructureStorage;
import io.rxmicro.annotation.processor.rest.server.model.RestControllerMethodSignature;
import io.rxmicro.annotation.processor.rest.server.model.RestServerModuleGeneratorConfig;
import io.rxmicro.rest.model.ExchangeFormat;
import io.rxmicro.validation.DisableValidation;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.rxmicro.annotation.processor.common.util.Injects.injectDependencies;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.rest.method.HttpMethods.HTTP_METHOD_ANNOTATIONS;
import static java.util.Map.entry;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 * @author nedis
 * @link https://rxmicro.io
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
    public Set<String> getSupportedAnnotationTypes() {
        return Stream
                .concat(
                        HTTP_METHOD_ANNOTATIONS.stream().map(Class::getName),
                        Stream.of(
                                io.rxmicro.rest.method.GET.List.class,
                                io.rxmicro.rest.method.POST.List.class,
                                io.rxmicro.rest.method.PUT.List.class,
                                io.rxmicro.rest.method.DELETE.List.class,
                                io.rxmicro.rest.method.PATCH.List.class,
                                io.rxmicro.rest.method.OPTIONS.List.class,
                                io.rxmicro.rest.method.HEAD.List.class
                        ).map(cl -> cl.getName().replace("$", "."))
                )
                .collect(Collectors.toSet());
    }

    @Override
    public Set<? extends ClassStructure> buildClassStructures(final EnvironmentContext environmentContext,
                                                              final Set<? extends TypeElement> annotations,
                                                              final RoundEnvironment roundEnv) {
        try {
            final Set<RestControllerClassSignature> classSignatures =
                    restControllerClassSignatureBuilder.build(environmentContext, annotations, roundEnv);
            if (!classSignatures.isEmpty()) {
                environmentContext.put(restServerModuleGeneratorConfigBuilder.build(environmentContext));
                logFoundRestControllers(classSignatures);
                final RestGenerationContext restGenerationContext =
                        restGenerationContextBuilder.build(environmentContext, RestServerModuleGeneratorConfig.class, classSignatures);
                final RestControllerClassStructureStorage restControllerClassStructureStorage =
                        buildRestClassStructureStorage(environmentContext, restGenerationContext);
                final Set<ClassStructure> classStructures = new HashSet<>(restControllerClassStructureStorage.getAll());
                final Set<RestControllerClassStructure> restControllerClassStructures =
                        restControllerClassStructureBuilder.build(
                                environmentContext,
                                restControllerClassStructureStorage,
                                classSignatures
                        );

                classStructures.addAll(restControllerClassStructures);
                classStructures.add(new RestControllerAggregatorClassStructure(
                        environmentContext,
                        restControllerClassStructures,
                        crossOriginResourceSharingResourceBuilder.build(
                                restControllerClassStructures,
                                restGenerationContext
                        ),
                        httpHealthCheckBuilder.build(
                                environmentContext,
                                restControllerClassStructures
                        )));
                classStructures.add(new EnvironmentCustomizerClassStructure(environmentContext));
                if (!environmentContext.get(RestServerModuleGeneratorConfig.class).getDocumentationTypes().isEmpty()) {
                    restDocumentationGenerator.generate(
                            environmentContext,
                            restControllerClassStructureStorage,
                            restControllerClassStructures
                    );
                }
                addAllVirtualRequestClassStructures(classStructures, classSignatures, restControllerClassStructureStorage);
                return classStructures;
            }
            return Set.of();
        } catch (final InterruptProcessingException e) {
            error(e);
            return Set.of();
        }
    }

    private void logFoundRestControllers(final Set<RestControllerClassSignature> set) {
        info("Found the following REST controllers:\n?", () -> set.stream()
                .map(s -> s.getMethodSignatures().stream()
                        .flatMap(m -> m.getHttpMethodMappings().stream().map(h -> entry(h, m)))
                        .map(e -> format("  ? ? -> ?",
                                e.getKey().getMethod(), e.getKey().getExactOrTemplateUri(),
                                e.getValue()))
                        .collect(joining("\n")))
                .collect(joining("\n"))
        );
    }

    private RestControllerClassStructureStorage buildRestClassStructureStorage(final EnvironmentContext environmentContext,
                                                                               final RestGenerationContext generationContext) {
        final ExchangeFormat exchangeFormat =
                environmentContext.get(RestServerModuleGeneratorConfig.class).getExchangeFormatModule().getExchangeFormat();
        final RestControllerClassStructureStorage.Builder builder = new RestControllerClassStructureStorage.Builder()
                .addModelReaders(modelReaderBuilder.build(
                        generationContext.getFromHttpDataModelClasses(), exchangeFormat
                ))
                .addModelWriters(modelWriterBuilder.build(
                        generationContext.getToHttpDataModelClasses(), exchangeFormat
                ))
                .addModelFromJsonConverters(restModelFromJsonConverterBuilder.buildFromJson(
                        generationContext.getFromHttpDataModelClasses(), exchangeFormat, false
                ))
                .addModelToJsonConverters(restModelToJsonConverterBuilder.buildToJson(
                        generationContext.getToHttpDataModelClasses(), exchangeFormat, false
                ));
        addValidators(environmentContext, generationContext, builder);
        return builder.build();
    }

    private void addValidators(final EnvironmentContext environmentContext,
                               final RestGenerationContext generationContext,
                               final RestControllerClassStructureStorage.Builder builder) {
        if (environmentContext.get(RestServerModuleGeneratorConfig.class).isGenerateRequestValidators()) {
            builder.addRequestValidators(
                    restModelValidatorBuilder.build(generationContext.getFromHttpDataModelClasses().stream()
                            .map(MappedRestObjectModelClass::getModelClass)
                            .filter(m -> isAnnotationPerPackageHierarchyAbsent(
                                    m.getModelTypeElement(), DisableValidation.class))
                            .collect(toList()))
            );
        }
        if (environmentContext.get(RestServerModuleGeneratorConfig.class).isGenerateResponseValidators()) {
            builder.addResponseValidators(
                    restModelValidatorBuilder.build(generationContext.getToHttpDataModelClasses().stream()
                            .map(MappedRestObjectModelClass::getModelClass)
                            .filter(m -> isAnnotationPerPackageHierarchyAbsent(
                                    m.getModelTypeElement(), DisableValidation.class))
                            .collect(toList()))
            );
        }
    }

    private void addAllVirtualRequestClassStructures(final Set<ClassStructure> classStructures,
                                                     final Set<RestControllerClassSignature> classSignatures,
                                                     final RestControllerClassStructureStorage restControllerClassStructureStorage) {
        for (final RestControllerClassSignature classSignature : classSignatures) {
            for (final RestControllerMethodSignature methodSignature : classSignature.getMethodSignatures()) {
                methodSignature.getRequestModel().getRequestType()
                        .filter(t -> t instanceof VirtualTypeElement)
                        .flatMap(t -> restControllerClassStructureStorage.getModelReaderClassStructure(t.getQualifiedName().toString()))
                        .ifPresent(mr -> classStructures.add(new VirtualTypeClassStructure(mr.getModelClass(), false)));
            }
        }
    }
}
