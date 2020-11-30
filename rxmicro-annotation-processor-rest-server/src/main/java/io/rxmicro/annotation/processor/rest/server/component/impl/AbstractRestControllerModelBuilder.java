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

import io.rxmicro.annotation.processor.common.component.impl.AbstractProcessorComponent;
import io.rxmicro.annotation.processor.rest.model.MappedRestObjectModelClass;
import io.rxmicro.annotation.processor.rest.model.RestObjectModelClass;
import io.rxmicro.annotation.processor.rest.model.converter.ReaderType;
import io.rxmicro.annotation.processor.rest.server.model.AbstractRestControllerModelClassStructure;
import io.rxmicro.rest.model.ExchangeFormat;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static io.rxmicro.common.util.ExCollections.unmodifiableOrderedSet;
import static io.rxmicro.common.util.ExCollectors.toOrderedSet;

/**
 * @author nedis
 * @since 0.1
 */
public abstract class AbstractRestControllerModelBuilder<T extends AbstractRestControllerModelClassStructure>
        extends AbstractProcessorComponent {

    protected abstract T newInstance(ReaderType readerType,
                                     RestObjectModelClass modelClass,
                                     ExchangeFormat exchangeFormat);

    public final Set<T> build(final List<MappedRestObjectModelClass> mappedRestObjectModelClasses,
                              final ExchangeFormat exchangeFormat) {
        final Set<T> structures = new HashSet<>();
        for (final MappedRestObjectModelClass mappedRestObjectModelClass : mappedRestObjectModelClasses) {
            final RestObjectModelClass modelClass = mappedRestObjectModelClass.getModelClass();
            structures.addAll(
                    Stream.concat(Stream.of(modelClass), modelClass.getAllParents().stream())
                            .map(mc -> (RestObjectModelClass)mc)
                            .filter(mc -> mc.isHeadersOrPathVariablesOrInternalsPresent() || mc.isModelClassReturnedByRestMethod())
                            .map(mc -> newInstance(mappedRestObjectModelClass.getReaderType(), mc, exchangeFormat))
                            .collect(toOrderedSet())
            );
        }
        return unmodifiableOrderedSet(structures);
    }
}
