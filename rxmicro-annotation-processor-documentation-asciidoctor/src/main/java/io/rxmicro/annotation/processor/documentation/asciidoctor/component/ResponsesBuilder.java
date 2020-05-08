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

import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.documentation.asciidoctor.model.Response;
import io.rxmicro.annotation.processor.documentation.model.ProjectMetaData;
import io.rxmicro.annotation.processor.rest.server.model.RestControllerClassStructure;
import io.rxmicro.annotation.processor.rest.server.model.RestControllerClassStructureStorage;
import io.rxmicro.annotation.processor.rest.server.model.RestControllerMethod;
import io.rxmicro.documentation.DocumentationDefinition;
import io.rxmicro.documentation.ResourceDefinition;

import java.util.Set;

/**
 * @author nedis
 * @since 0.1
 */
public interface ResponsesBuilder {

    Response buildSuccessResponse(ResourceDefinition resourceDefinition,
                                  ProjectMetaData projectMetaData,
                                  EnvironmentContext environmentContext,
                                  RestControllerMethod method,
                                  RestControllerClassStructureStorage restControllerClassStructureStorage);

    Set<Response> buildErrorResponses(EnvironmentContext environmentContext,
                                      ProjectMetaData projectMetaData,
                                      DocumentationDefinition documentationDefinition,
                                      ResourceDefinition resourceDefinition,
                                      RestControllerClassStructure classStructure,
                                      RestControllerMethod method);
}
