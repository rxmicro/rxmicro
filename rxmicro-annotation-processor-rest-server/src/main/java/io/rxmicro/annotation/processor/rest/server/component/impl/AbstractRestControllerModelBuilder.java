/*
 * Copyright 2019 https://rxmicro.io
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

import io.rxmicro.annotation.processor.common.component.impl.AbstractProcessorComponent;
import io.rxmicro.annotation.processor.rest.model.MappedRestObjectModelClass;
import io.rxmicro.annotation.processor.rest.model.RestObjectModelClass;
import io.rxmicro.annotation.processor.rest.model.converter.ReaderType;
import io.rxmicro.annotation.processor.rest.server.model.AbstractRestControllerModelClassStructure;
import io.rxmicro.rest.model.ExchangeFormat;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public abstract class AbstractRestControllerModelBuilder<T extends AbstractRestControllerModelClassStructure>
        extends AbstractProcessorComponent {

    protected abstract T newInstance(final ReaderType readerType,
                                     final RestObjectModelClass modelClass,
                                     final ExchangeFormat exchangeFormat);

    public final Set<T> build(final List<MappedRestObjectModelClass> mappedRestObjectModelClasses,
                              final ExchangeFormat exchangeFormat) {
        return mappedRestObjectModelClasses.stream()
                .map(restModelClass ->
                        newInstance(restModelClass.getReaderType(), restModelClass.getModelClass(), exchangeFormat))
                .collect(Collectors.toSet());
    }
}
