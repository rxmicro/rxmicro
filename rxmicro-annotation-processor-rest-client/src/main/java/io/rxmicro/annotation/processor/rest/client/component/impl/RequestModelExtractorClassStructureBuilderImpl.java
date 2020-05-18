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

package io.rxmicro.annotation.processor.rest.client.component.impl;

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.rest.client.component.RequestModelExtractorClassStructureBuilder;
import io.rxmicro.annotation.processor.rest.client.model.RequestModelExtractorClassStructure;
import io.rxmicro.annotation.processor.rest.model.MappedRestObjectModelClass;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class RequestModelExtractorClassStructureBuilderImpl
        implements RequestModelExtractorClassStructureBuilder {

    @Override
    public Set<RequestModelExtractorClassStructure> build(
            final List<MappedRestObjectModelClass> mappedRestObjectModelClasses) {
        return mappedRestObjectModelClasses.stream()
                .peek(restModelClass -> {
                    if (!restModelClass.getModelClass().getAllChildrenObjectModelClasses().isEmpty()) {
                        throw new InterruptProcessingException(
                                restModelClass.getModelClass().getModelTypeElement(),
                                "Nested model classes couldn't be used for building HTTP query string. " +
                                        "Remove nested model classes or change HTTP method. " +
                                        "(FYI: It is necessary to use HTTP method, which uses HTTP body" +
                                        " to send parameters instead of HTTP query string, " +
                                        "for example POST, PUT, etc)");
                    }
                })
                .map(restModelClass -> new RequestModelExtractorClassStructure(restModelClass.getModelClass()))
                .collect(Collectors.toSet());
    }
}
