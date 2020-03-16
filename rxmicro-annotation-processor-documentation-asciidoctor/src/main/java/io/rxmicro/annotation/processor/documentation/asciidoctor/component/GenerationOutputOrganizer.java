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

package io.rxmicro.annotation.processor.documentation.asciidoctor.component;

import io.rxmicro.annotation.processor.common.model.DocumentStructure;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.documentation.asciidoctor.model.ResourceGroup;
import io.rxmicro.annotation.processor.documentation.model.ProjectMetaData;
import io.rxmicro.documentation.DocumentationDefinition;

import java.util.List;
import java.util.Set;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public interface GenerationOutputOrganizer {

    Set<? extends DocumentStructure> organize(EnvironmentContext environmentContext,
                                              ProjectMetaData projectMetaData,
                                              DocumentationDefinition documentationDefinition,
                                              List<ResourceGroup> resourceGroups,
                                              List<String> customSections);
}
