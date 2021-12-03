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
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.rest.model.MappedRestObjectModelClass;
import io.rxmicro.annotation.processor.rest.model.RestModelField;
import io.rxmicro.annotation.processor.rest.model.RestObjectModelClass;
import io.rxmicro.annotation.processor.rest.model.converter.ReaderType;
import io.rxmicro.annotation.processor.rest.server.component.ModelWriterBuilder;
import io.rxmicro.annotation.processor.rest.server.model.ServerModelWriterClassStructure;
import io.rxmicro.rest.ResponseBody;
import io.rxmicro.rest.model.ExchangeFormat;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class ModelWriterBuilderImpl
        extends AbstractRestControllerModelBuilder<ServerModelWriterClassStructure>
        implements ModelWriterBuilder {

    @Override
    protected ServerModelWriterClassStructure newInstance(final ReaderType readerType,
                                                          final RestObjectModelClass modelClass,
                                                          final ExchangeFormat exchangeFormat) {
        validate(modelClass);
        return new ServerModelWriterClassStructure(modelClass, exchangeFormat);
    }

    @Override
    protected boolean shouldModelClassBeProcessed(final MappedRestObjectModelClass mappedRestObjectModelClass,
                                                  final RestObjectModelClass modelClass) {
        return modelClass.isModelClassReturnedByRestMethod() ||
                modelClass.isHeadersOrPathVariablesOrInternalsPresent();
    }

    private void validate(final RestObjectModelClass modelClass) {
        if (modelClass.getInternals().stream().anyMatch(RestModelField::isResponseBody) &&
                !modelClass.getParamEntries().isEmpty()) {
            throw new InterruptProcessingException(
                    modelClass.getModelTypeElement(),
                    "Annotation '@?' not supported if it is at least one response parameter. " +
                            "Remove this annotation or remove all response parameters",
                    ResponseBody.class
            );
        }
    }
}
