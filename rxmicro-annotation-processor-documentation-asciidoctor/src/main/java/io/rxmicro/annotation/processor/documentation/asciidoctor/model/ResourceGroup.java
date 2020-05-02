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

import io.rxmicro.annotation.processor.common.util.UsedByFreemarker;
import io.rxmicro.annotation.processor.rest.server.model.RestControllerClassStructure;
import io.rxmicro.documentation.ResourceGroupDefinition;
import io.rxmicro.rest.server.feature.EnableCrossOriginResourceSharing;

import java.util.ArrayList;
import java.util.List;

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class ResourceGroup {

    private final ResourceGroupDefinition resourceGroupDefinition;

    private final String name;

    private final String description;

    private final RestControllerClassStructure restControllerClassStructure;

    private final List<Resource> resources;

    private final List<String> customSections;

    public ResourceGroup(
            final ResourceGroupDefinition resourceGroupDefinition,
            final String name,
            final String description,
            final RestControllerClassStructure restControllerClassStructure,
            final List<Resource> resources,
            final List<String> customSections) {
        this.resourceGroupDefinition = require(resourceGroupDefinition);
        this.name = require(name);
        this.description = description;
        this.restControllerClassStructure = require(restControllerClassStructure);
        this.resources = require(resources);
        this.customSections = require(customSections);
    }

    public String getRestControllerClass() {
        return restControllerClassStructure.getOwnerClass().getSimpleName().toString();
    }

    @UsedByFreemarker("asciidoctor-document-template.adocftl")
    public String getName() {
        return name;
    }

    @UsedByFreemarker("asciidoctor-document-template.adocftl")
    public boolean isDescriptionPresent() {
        return description != null;
    }

    @UsedByFreemarker("asciidoctor-document-template.adocftl")
    public String getDescription() {
        return description;
    }

    @UsedByFreemarker({
            "asciidoctor-document-template.adocftl",
            "micro-service-basics-document-template.adocftl"
    })
    public List<Section> getSections() {
        final List<Section> sections = new ArrayList<>();
        int index = 0;
        for (final ResourceGroupDefinition.Section section : resourceGroupDefinition.sectionOrder()) {
            if (section == ResourceGroupDefinition.Section.VERSIONING) {
                if (restControllerClassStructure.getParentUrl().isVersionPresent()) {
                    sections.add(new Section(SectionType.INVOKE_MACROS, "versioning"));
                }

            } else if (section == ResourceGroupDefinition.Section.CORS) {
                if (restControllerClassStructure.getOwnerClass()
                        .getAnnotation(EnableCrossOriginResourceSharing.class) != null) {
                    sections.add(new Section(SectionType.INVOKE_MACROS, "cors"));
                }
            } else {
                sections.add(new Section(SectionType.CUSTOM_TEXT, customSections.get(index)));
            }
        }
        return sections;
    }

    @UsedByFreemarker("resource-group-macro.adocftl")
    public String getVersionStrategyType() {
        return restControllerClassStructure.getParentUrl().isUrlPathVersionStrategy() ? "URL" : "Header";
    }

    @UsedByFreemarker("resource-group-macro.adocftl")
    public String getApiVersionHeader() {
        return restControllerClassStructure.getParentUrl().getVersionHeaderName();
    }

    @UsedByFreemarker("resource-group-macro.adocftl")
    public String getCurrentApiVersion() {
        return restControllerClassStructure.getParentUrl().getVersionValue();
    }

    @UsedByFreemarker("asciidoctor-document-template.adocftl")
    public List<Resource> getResources() {
        return resources;
    }
}
