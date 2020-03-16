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
import io.rxmicro.annotation.processor.documentation.model.ProjectMetaData;
import io.rxmicro.documentation.DocumentationDefinition;

import javax.lang.model.element.ModuleElement;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class RestControllerBasicsDocumentStructure extends DocumentStructure {

    private final ProjectMetaData projectMetaData;

    private final DocumentationDefinition documentationDefinition;

    private final ModuleElement currentModule;

    private final ResourceGroup resourceGroup;

    public RestControllerBasicsDocumentStructure(final ProjectMetaData projectMetaData,
                                                 final DocumentationDefinition documentationDefinition,
                                                 final ModuleElement currentModule,
                                                 final ResourceGroup resourceGroup) {
        this.projectMetaData = projectMetaData;
        this.documentationDefinition = documentationDefinition;
        this.currentModule = require(currentModule);
        this.resourceGroup = require(resourceGroup);
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
        final String destinationDirectory = documentationDefinition.destinationDirectory();
        return destinationDirectory.isEmpty() ? Optional.empty() : Optional.of(destinationDirectory);
    }

    @Override
    public String getName() {
        return format("?-Basics-fragment.adoc", resourceGroup.getRestControllerClass());
    }

    @Override
    public String getTemplateName() {
        return "documentation/asciidoctor/micro-service-basics-document-template.adocftl";
    }

    @Override
    public Map<String, Object> getTemplateVariables() {
        final Map<String, Object> map = new HashMap<>();
        map.put("GROUP", resourceGroup);
        return map;
    }
}
