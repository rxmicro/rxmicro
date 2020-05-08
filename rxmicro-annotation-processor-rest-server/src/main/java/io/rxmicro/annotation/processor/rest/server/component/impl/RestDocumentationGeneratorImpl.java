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

package io.rxmicro.annotation.processor.rest.server.component.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.component.DocumentationGenerator;
import io.rxmicro.annotation.processor.common.component.impl.AbstractProcessorComponent;
import io.rxmicro.annotation.processor.common.model.DocumentStructure;
import io.rxmicro.annotation.processor.common.model.DocumentationType;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.common.model.error.InternalErrorException;
import io.rxmicro.annotation.processor.rest.server.component.AbstractDocumentationModuleClassStructuresBuilder;
import io.rxmicro.annotation.processor.rest.server.component.RestDocumentationGenerator;
import io.rxmicro.annotation.processor.rest.server.model.RestControllerClassStructure;
import io.rxmicro.annotation.processor.rest.server.model.RestControllerClassStructureStorage;
import io.rxmicro.annotation.processor.rest.server.model.RestServerModuleGeneratorConfig;

import java.util.HashSet;
import java.util.List;
import java.util.ServiceLoader;
import java.util.Set;

import static io.rxmicro.annotation.processor.common.util.ServiceLoaderImplementations.getImplementations;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class RestDocumentationGeneratorImpl extends AbstractProcessorComponent
        implements RestDocumentationGenerator {

    @Inject
    private DocumentationGenerator documentationGenerator;

    @Override
    public void generate(final EnvironmentContext environmentContext,
                         final RestControllerClassStructureStorage restControllerClassStructureStorage,
                         final Set<RestControllerClassStructure> restControllerClassStructures) {
        final List<AbstractDocumentationModuleClassStructuresBuilder> documentationBuilders =
                getImplementations(AbstractDocumentationModuleClassStructuresBuilder.class, service ->
                        ServiceLoader.load(service, RestDocumentationGeneratorImpl.class.getClassLoader()));
        final Set<DocumentationType> toProcess = new HashSet<>(environmentContext.get(RestServerModuleGeneratorConfig.class).getDocumentationTypes());
        final Set<DocumentStructure> result = new HashSet<>();
        for (final AbstractDocumentationModuleClassStructuresBuilder documentationBuilder : documentationBuilders) {
            if (toProcess.remove(documentationBuilder.getDocumentationType())) {
                result.addAll(
                        documentationBuilder.build(
                                environmentContext,
                                restControllerClassStructureStorage,
                                restControllerClassStructures
                        )
                );
            }
        }
        if (!toProcess.isEmpty()) {
            throw new InternalErrorException("Documentation generator not found: ?", toProcess);
        }
        result.forEach(documentationGenerator::generate);
    }
}
