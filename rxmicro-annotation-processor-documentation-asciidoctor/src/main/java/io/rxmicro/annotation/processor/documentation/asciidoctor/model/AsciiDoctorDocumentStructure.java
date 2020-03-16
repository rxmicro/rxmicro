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

package io.rxmicro.annotation.processor.documentation.asciidoctor.model;

import io.rxmicro.annotation.processor.common.model.DocumentStructure;
import io.rxmicro.annotation.processor.common.model.DocumentationType;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.documentation.model.ProjectMetaData;
import io.rxmicro.documentation.DocumentationDefinition;
import io.rxmicro.documentation.IntroductionDefinition;
import io.rxmicro.documentation.asciidoctor.DocumentAttributes;

import javax.lang.model.element.ModuleElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;
import static java.util.Map.entry;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class AsciiDoctorDocumentStructure extends DocumentStructure {

    private final ModuleElement currentModule;

    private final ProjectMetaData projectMetaData;

    private final DocumentAttributes documentAttributes;

    private final DocumentationDefinition documentationDefinition;

    private final List<ResourceGroup> resourceGroups;

    private final List<String> customSections;

    public AsciiDoctorDocumentStructure(final ModuleElement currentModule,
                                        final ProjectMetaData projectMetaData,
                                        final DocumentAttributes documentAttributes,
                                        final DocumentationDefinition documentationDefinition,
                                        final List<ResourceGroup> resourceGroups,
                                        final List<String> customSections) {
        this.currentModule = require(currentModule);
        this.documentAttributes = require(documentAttributes);
        this.documentationDefinition = require(documentationDefinition);
        this.projectMetaData = require(projectMetaData);
        this.resourceGroups = require(resourceGroups);
        this.customSections = require(customSections);
    }

    @Override
    public ModuleElement getCurrentModule() {
        return currentModule;
    }

    @Override
    public DocumentationType getDocumentationType() {
        return DocumentationType.Ascii_Doctor;
    }

    @Override
    public String getProjectDirectory() {
        return projectMetaData.getProjectDirectory();
    }

    @Override
    public Optional<String> getCustomDestinationDirectory() {
        return Optional.of(documentationDefinition.destinationDirectory()).filter(v -> !v.isEmpty());
    }

    @Override
    public String getName() {
        return format("?Documentation.adoc", projectMetaData.getName().replace(" ", ""));
    }

    @Override
    public String getTemplateName() {
        return "documentation/asciidoctor/asciidoctor-document-template.adocftl";
    }

    @Override
    public Map<String, Object> getTemplateVariables() {
        final Map<String, Object> map = new HashMap<>();
        map.put("PROJECT", projectMetaData);
        map.put("WITH_TIPS", documentationDefinition.withTips());
        map.put("GENERATE_DATE", documentationDefinition.withGeneratedDate());
        map.put("DOCUMENT_ATTRIBUTES", getDocumentAttributes(currentModule, documentAttributes));
        final IntroductionDefinition introductionDefinition = documentationDefinition.introduction();
        map.put("SECTIONS", getSections(introductionDefinition));
        map.put("RESOURCE_GROUPS", resourceGroups);
        return map;
    }

    private List<Map.Entry<String, String>> getDocumentAttributes(final ModuleElement currentModule,
                                                                  final DocumentAttributes documentAttributes) {
        final List<Map.Entry<String, String>> list = new ArrayList<>();
        final String[] attrs = documentAttributes.value();
        if (attrs.length % 2 == 1) {
            throw new InterruptProcessingException(currentModule, "Missing value for '?' attribute", attrs[attrs.length - 1]);
        }
        for (int i = 0; i < attrs.length; i += 2) {
            list.add(entry(attrs[i], attrs[i + 1]));
        }
        return list;
    }

    private List<Section> getSections(final IntroductionDefinition introductionDefinition) {
        final List<Section> sections = new ArrayList<>();
        int index = 0;
        for (final IntroductionDefinition.Section section : introductionDefinition.sectionOrder()) {
            if (section == IntroductionDefinition.Section.Base_Endpoint) {
                if (projectMetaData.isBaseEndpointPresent()) {
                    sections.add(toSection(section, index));
                }
            } else {
                sections.add(toSection(section, index));
            }
            if (section == IntroductionDefinition.Section.Custom_section) {
                index++;
            }
        }
        return sections;
    }

    private Section toSection(final IntroductionDefinition.Section section,
                              final int index) {
        if (section.isCustomSection()) {
            return new Section(SectionType.CUSTOM_TEXT, customSections.get(index));
        } else {
            return new Section(SectionType.INCLUDE_TEMPLATE,
                    format("introduction/?.adocftl", section.name().toLowerCase().replace("_", "-")));
        }
    }
}
