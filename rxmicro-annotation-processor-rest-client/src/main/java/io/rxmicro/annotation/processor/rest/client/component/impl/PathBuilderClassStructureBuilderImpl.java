/*
 * Copyright 2019 http://rxmicro.io
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
import io.rxmicro.annotation.processor.rest.client.component.PathBuilderClassStructureBuilder;
import io.rxmicro.annotation.processor.rest.client.model.PathBuilderClassStructure;
import io.rxmicro.annotation.processor.rest.model.MappedRestObjectModelClass;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
@Singleton
public final class PathBuilderClassStructureBuilderImpl implements PathBuilderClassStructureBuilder {

    @Override
    public Set<PathBuilderClassStructure> build(final List<MappedRestObjectModelClass> mappedRestObjectModelClasses) {
        return mappedRestObjectModelClasses.stream()
                .peek(restModelClass -> {
                    if (!restModelClass.getModelClass().getAllChildrenObjectModelClasses().isEmpty()) {
                        throw new InterruptProcessingException(
                                restModelClass.getModelClass().getModelTypeElement(),
                                "Nested model classes couldn't be used for HTTP path building. " +
                                        "Remove nested model classes. ");
                    }
                })
                .map(restModelClass -> new PathBuilderClassStructure(
                        restModelClass.getModelClass(),
                        restModelClass.getHttpMethodMappings()
                ))
                .collect(Collectors.toSet());
    }
}
