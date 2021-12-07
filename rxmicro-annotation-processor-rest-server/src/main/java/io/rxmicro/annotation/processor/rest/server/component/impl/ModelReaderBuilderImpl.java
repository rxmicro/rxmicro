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

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.rest.model.MappedRestObjectModelClass;
import io.rxmicro.annotation.processor.rest.model.RestObjectModelClass;
import io.rxmicro.annotation.processor.rest.model.converter.ReaderType;
import io.rxmicro.annotation.processor.rest.server.component.ModelReaderBuilder;
import io.rxmicro.annotation.processor.rest.server.model.ServerModelReaderClassStructure;
import io.rxmicro.rest.model.ExchangeFormat;

import javax.lang.model.element.ModuleElement;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class ModelReaderBuilderImpl
        extends AbstractRestControllerModelBuilder<ServerModelReaderClassStructure>
        implements ModelReaderBuilder {

    @Override
    protected ServerModelReaderClassStructure newInstance(final ModuleElement moduleElement,
                                                          final ReaderType readerType,
                                                          final RestObjectModelClass modelClass,
                                                          final ExchangeFormat exchangeFormat) {
        return new ServerModelReaderClassStructure(moduleElement, readerType, modelClass, exchangeFormat);
    }

    @Override
    protected boolean shouldModelClassBeProcessed(final MappedRestObjectModelClass mappedRestObjectModelClass,
                                                  final RestObjectModelClass modelClass) {
        return modelClass.isModelClassReturnedByRestMethod() ||
                modelClass.isHeadersOrPathVariablesOrInternalsPresent() ||
                mappedRestObjectModelClass.getReaderType().isQueryPresent() && modelClass.isParamEntriesPresent();
    }
}
