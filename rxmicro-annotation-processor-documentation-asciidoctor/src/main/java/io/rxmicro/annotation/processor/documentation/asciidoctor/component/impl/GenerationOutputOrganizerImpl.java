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

package io.rxmicro.annotation.processor.documentation.asciidoctor.component.impl;

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.model.DocumentStructure;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.documentation.asciidoctor.component.GenerationOutputOrganizer;
import io.rxmicro.annotation.processor.documentation.asciidoctor.model.AsciiDoctorDocumentStructure;
import io.rxmicro.annotation.processor.documentation.asciidoctor.model.Resource;
import io.rxmicro.annotation.processor.documentation.asciidoctor.model.ResourceGroup;
import io.rxmicro.annotation.processor.documentation.asciidoctor.model.RestControllerBasicsDocumentStructure;
import io.rxmicro.annotation.processor.documentation.asciidoctor.model.RestControllerResourceDocumentStructure;
import io.rxmicro.annotation.processor.documentation.model.ProjectMetaData;
import io.rxmicro.documentation.DocumentationDefinition;
import io.rxmicro.documentation.asciidoctor.DocumentAttributes;

import javax.lang.model.element.ModuleElement;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static io.rxmicro.annotation.processor.common.util.Annotations.getPresentOrDefaultAnnotation;
import static io.rxmicro.documentation.DocumentationDefinition.GenerationOutput.BASICS_SECTION;
import static io.rxmicro.documentation.DocumentationDefinition.GenerationOutput.RESOURCES_SECTION;
import static io.rxmicro.documentation.DocumentationDefinition.GenerationOutput.SINGLE_DOCUMENT;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@Singleton
public final class GenerationOutputOrganizerImpl implements GenerationOutputOrganizer {

    @Override
    public Set<? extends DocumentStructure> organize(final EnvironmentContext environmentContext,
                                                     final ProjectMetaData projectMetaData,
                                                     final DocumentationDefinition documentationDefinition,
                                                     final List<ResourceGroup> resourceGroups,
                                                     final List<String> customSections) {
        final ModuleElement currentModule = environmentContext.getCurrentModule();
        validateGenerationOutput(currentModule, documentationDefinition.output());
        final Set<DocumentStructure> result = new HashSet<>();
        final Set<DocumentationDefinition.GenerationOutput> outputs = Set.of(documentationDefinition.output());
        if (outputs.contains(SINGLE_DOCUMENT)) {
            result.add(buildAsciiDoctorDocumentStructure(
                    currentModule, projectMetaData, documentationDefinition, resourceGroups, customSections
            ));
        }
        if (outputs.contains(BASICS_SECTION)) {
            resourceGroups.forEach(resourceGroup ->
                    result.add(buildRestControllerBasicsDocumentStructure(
                            currentModule, projectMetaData, documentationDefinition, resourceGroup
                    )));
        }
        if (outputs.contains(RESOURCES_SECTION)) {
            resourceGroups.forEach(resourceGroup -> resourceGroup.getResources().forEach(resource -> {
                result.add(buildRestControllerResourceDocumentStructure(
                        currentModule, projectMetaData, documentationDefinition, resourceGroup, resource
                ));
            }));
        }
        return result;
    }

    private void validateGenerationOutput(final ModuleElement currentModule,
                                          final DocumentationDefinition.GenerationOutput[] outputs) {
        if (outputs.length == 0) {
            throw new InterruptProcessingException(currentModule, "Expected at least one GenerationOutput item");
        }
        final Set<DocumentationDefinition.GenerationOutput> outputSet = new HashSet<>();
        for (final DocumentationDefinition.GenerationOutput output : outputs) {
            if (!outputSet.add(output)) {
                throw new InterruptProcessingException(currentModule, "GenerationOutput duplicates not allowed");
            }
        }
    }

    private DocumentStructure buildAsciiDoctorDocumentStructure(final ModuleElement currentModule,
                                                                final ProjectMetaData projectMetaData,
                                                                final DocumentationDefinition documentationDefinition,
                                                                final List<ResourceGroup> resourceGroups,
                                                                final List<String> customSections) {
        return new AsciiDoctorDocumentStructure(
                currentModule,
                projectMetaData,
                getPresentOrDefaultAnnotation(currentModule, DocumentAttributes.class),
                documentationDefinition,
                resourceGroups,
                customSections);
    }

    private DocumentStructure buildRestControllerBasicsDocumentStructure(final ModuleElement currentModule,
                                                                         final ProjectMetaData projectMetaData,
                                                                         final DocumentationDefinition documentationDefinition,
                                                                         final ResourceGroup resourceGroup) {
        return new RestControllerBasicsDocumentStructure(projectMetaData, documentationDefinition, currentModule, resourceGroup);
    }

    private DocumentStructure buildRestControllerResourceDocumentStructure(final ModuleElement currentModule,
                                                                           final ProjectMetaData projectMetaData,
                                                                           final DocumentationDefinition documentationDefinition,
                                                                           final ResourceGroup resourceGroup,
                                                                           final Resource resource) {
        return new RestControllerResourceDocumentStructure(projectMetaData, documentationDefinition, currentModule, resourceGroup, resource);
    }
}
