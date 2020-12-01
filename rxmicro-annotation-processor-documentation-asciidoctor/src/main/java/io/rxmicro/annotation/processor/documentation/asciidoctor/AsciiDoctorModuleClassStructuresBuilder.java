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

package io.rxmicro.annotation.processor.documentation.asciidoctor;

import com.google.inject.Inject;
import io.rxmicro.annotation.processor.common.CommonDependenciesModule;
import io.rxmicro.annotation.processor.common.model.DocumentStructure;
import io.rxmicro.annotation.processor.config.DocumentationType;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.documentation.DocumentationDependenciesModule;
import io.rxmicro.annotation.processor.documentation.asciidoctor.component.GenerationOutputOrganizer;
import io.rxmicro.annotation.processor.documentation.asciidoctor.component.RequestBuilder;
import io.rxmicro.annotation.processor.documentation.asciidoctor.component.ResponsesBuilder;
import io.rxmicro.annotation.processor.documentation.asciidoctor.model.Resource;
import io.rxmicro.annotation.processor.documentation.asciidoctor.model.ResourceGroup;
import io.rxmicro.annotation.processor.documentation.component.CustomSectionsReader;
import io.rxmicro.annotation.processor.documentation.component.DescriptionReader;
import io.rxmicro.annotation.processor.documentation.component.IncludeReferenceSyntaxProvider;
import io.rxmicro.annotation.processor.documentation.component.ProjectMetaDataReader;
import io.rxmicro.annotation.processor.documentation.component.TitleReader;
import io.rxmicro.annotation.processor.documentation.model.ProjectMetaData;
import io.rxmicro.annotation.processor.rest.RestCommonDependenciesModule;
import io.rxmicro.annotation.processor.rest.model.HttpMethodMapping;
import io.rxmicro.annotation.processor.rest.server.RestServerDependenciesModule;
import io.rxmicro.annotation.processor.rest.server.component.AbstractDocumentationModuleClassStructuresBuilder;
import io.rxmicro.annotation.processor.rest.server.model.RestControllerClassStructure;
import io.rxmicro.annotation.processor.rest.server.model.RestControllerClassStructureStorage;
import io.rxmicro.annotation.processor.rest.server.model.RestControllerMethod;
import io.rxmicro.documentation.DocumentationDefinition;
import io.rxmicro.documentation.ResourceDefinition;
import io.rxmicro.documentation.ResourceGroupDefinition;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.lang.model.element.ModuleElement;

import static io.rxmicro.annotation.processor.config.DocumentationType.ASCII_DOCTOR;
import static io.rxmicro.annotation.processor.common.util.Annotations.getPresentOrDefaultAnnotation;
import static io.rxmicro.annotation.processor.common.util.Injects.injectDependencies;
import static java.util.Map.entry;
import static java.util.stream.Collectors.toList;

/**
 * @author nedis
 * @since 0.1
 */
public final class AsciiDoctorModuleClassStructuresBuilder extends AbstractDocumentationModuleClassStructuresBuilder {

    @Inject
    private ProjectMetaDataReader projectMetaDataReader;

    @Inject
    private TitleReader titleReader;

    @Inject
    private DescriptionReader descriptionReader;

    @Inject
    private CustomSectionsReader customSectionsReader;

    @Inject
    private IncludeReferenceSyntaxProvider includeReferenceSyntaxProvider;

    @Inject
    private RequestBuilder requestBuilder;

    @Inject
    private ResponsesBuilder responsesBuilder;

    @Inject
    private GenerationOutputOrganizer generationOutputOrganizer;

    public AsciiDoctorModuleClassStructuresBuilder() {
        injectDependencies(
                this,
                new CommonDependenciesModule(),
                new DocumentationDependenciesModule(),
                new AsciiDoctorDocumentationDependenciesModule(),
                new RestCommonDependenciesModule(),
                new RestServerDependenciesModule()
        );
    }

    @Override
    public Set<? extends DocumentStructure> build(final EnvironmentContext environmentContext,
                                                  final RestControllerClassStructureStorage restControllerClassStructureStorage,
                                                  final Set<RestControllerClassStructure> restControllerClassStructures) {
        final ModuleElement currentModule = environmentContext.getCurrentModule();
        final ProjectMetaData projectMetaData = projectMetaDataReader.read(currentModule);
        final DocumentationDefinition documentationDefinition =
                getPresentOrDefaultAnnotation(currentModule, DocumentationDefinition.class);
        final List<ResourceGroup> resourceGroups = buildResourceGroups(
                environmentContext,
                projectMetaData,
                documentationDefinition,
                restControllerClassStructureStorage,
                restControllerClassStructures
        );
        final List<String> customSections = customSectionsReader.read(
                currentModule,
                documentationDefinition.introduction(),
                projectMetaData,
                includeReferenceSyntaxProvider
        );
        return generationOutputOrganizer.organize(
                environmentContext,
                projectMetaData,
                documentationDefinition,
                resourceGroups,
                customSections
        );
    }

    @Override
    public DocumentationType getDocumentationType() {
        return ASCII_DOCTOR;
    }

    private List<ResourceGroup> buildResourceGroups(final EnvironmentContext environmentContext,
                                                    final ProjectMetaData projectMetaData,
                                                    final DocumentationDefinition documentationDefinition,
                                                    final RestControllerClassStructureStorage restControllerClassStructureStorage,
                                                    final Set<RestControllerClassStructure> restControllerClassStructures) {
        return restControllerClassStructures.stream()
                .map(cl -> buildResourceGroup(
                        environmentContext,
                        projectMetaData,
                        documentationDefinition,
                        restControllerClassStructureStorage,
                        cl
                ))
                .collect(toList());
    }

    private ResourceGroup buildResourceGroup(final EnvironmentContext environmentContext,
                                             final ProjectMetaData projectMetaData,
                                             final DocumentationDefinition documentationDefinition,
                                             final RestControllerClassStructureStorage restControllerClassStructureStorage,
                                             final RestControllerClassStructure classStructure) {
        final String name = titleReader.readTitleOrDefault(classStructure.getOwnerClass());
        final String description = descriptionReader.readDescription(
                classStructure.getOwnerClass(),
                projectMetaData.getProjectDirectory()
        ).orElse(null);
        final List<Resource> resources = buildResources(
                environmentContext,
                projectMetaData,
                documentationDefinition,
                restControllerClassStructureStorage,
                classStructure);
        final ResourceGroupDefinition resourceGroupDefinition =
                classStructure.getOwnerClass().getAnnotation(ResourceGroupDefinition.class);
        final List<String> customSections = customSectionsReader.read(
                resourceGroupDefinition != null ? classStructure.getOwnerClass() : environmentContext.getCurrentModule(),
                resourceGroupDefinition != null ? resourceGroupDefinition : documentationDefinition.resourceGroup(),
                projectMetaData,
                includeReferenceSyntaxProvider
        );
        return new ResourceGroup(
                resourceGroupDefinition != null ? resourceGroupDefinition : documentationDefinition.resourceGroup(),
                name,
                description,
                classStructure,
                resources,
                customSections);
    }

    private List<Resource> buildResources(final EnvironmentContext environmentContext,
                                          final ProjectMetaData projectMetaData,
                                          final DocumentationDefinition documentationDefinition,
                                          final RestControllerClassStructureStorage restControllerClassStructureStorage,
                                          final RestControllerClassStructure classStructure) {
        return classStructure.getMethods().stream()
                .flatMap(m -> m.getHttpMethodMappings().stream().map(h -> entry(h, m)))
                .map(e -> buildResource(
                        environmentContext,
                        projectMetaData,
                        documentationDefinition,
                        classStructure,
                        restControllerClassStructureStorage,
                        e.getKey(),
                        e.getValue()
                ))
                .collect(toList());
    }

    private Resource buildResource(final EnvironmentContext environmentContext,
                                   final ProjectMetaData projectMetaData,
                                   final DocumentationDefinition documentationDefinition,
                                   final RestControllerClassStructure classStructure,
                                   final RestControllerClassStructureStorage storage,
                                   final HttpMethodMapping httpMethodMapping,
                                   final RestControllerMethod method) {
        final ResourceDefinition resourceDefinition = Optional
                .ofNullable(method.getMethod().getAnnotation(ResourceDefinition.class))
                .orElseGet(() -> Optional
                        .ofNullable(classStructure.getOwnerClass().getAnnotation(ResourceDefinition.class))
                        .orElseGet(documentationDefinition::resource));
        final Resource.Builder builder = new Resource.Builder()
                .setRestControllerMethod(method)
                .setHttpMethodMapping(httpMethodMapping)
                .setName(titleReader.readTitleOrDefault(method.getMethod()))
                .setRequest(requestBuilder.buildRequest(
                        environmentContext,
                        projectMetaData,
                        resourceDefinition,
                        classStructure,
                        httpMethodMapping,
                        method,
                        storage
                ))
                .addResponse(responsesBuilder.buildSuccessResponse(
                        resourceDefinition,
                        projectMetaData,
                        environmentContext,
                        method,
                        storage
                ));
        responsesBuilder.buildErrorResponses(
                environmentContext,
                projectMetaData,
                documentationDefinition,
                resourceDefinition,
                classStructure,
                method).forEach(builder::addResponse);
        descriptionReader.readDescription(method.getMethod(), projectMetaData.getProjectDirectory())
                .ifPresent(builder::setDescription);
        return builder.build();
    }
}
