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

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.rest.model.RestObjectModelClass;
import io.rxmicro.annotation.processor.rest.model.converter.ReaderType;
import io.rxmicro.annotation.processor.rest.server.component.ModelReaderBuilder;
import io.rxmicro.annotation.processor.rest.server.model.ModelReaderClassStructure;
import io.rxmicro.rest.model.ExchangeFormat;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class ModelReaderBuilderImpl
        extends AbstractRestControllerModelBuilder<ModelReaderClassStructure>
        implements ModelReaderBuilder {

    @Override
    protected ModelReaderClassStructure newInstance(final ReaderType readerType,
                                                    final RestObjectModelClass modelClass,
                                                    final ExchangeFormat exchangeFormat) {
        return new ModelReaderClassStructure(readerType, modelClass, exchangeFormat);
    }
}
