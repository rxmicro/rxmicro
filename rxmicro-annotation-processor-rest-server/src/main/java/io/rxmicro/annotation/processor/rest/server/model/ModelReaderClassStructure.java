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

package io.rxmicro.annotation.processor.rest.server.model;

import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.rest.model.RestObjectModelClass;
import io.rxmicro.annotation.processor.rest.model.converter.ReaderType;
import io.rxmicro.exchange.json.detail.JsonExchangeDataFormatConverter;
import io.rxmicro.http.HttpHeaders;
import io.rxmicro.http.QueryParams;
import io.rxmicro.rest.detail.ExchangeDataFormatConverter;
import io.rxmicro.rest.model.ExchangeFormat;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.component.ModelReader;
import io.rxmicro.rest.server.detail.model.HttpRequest;

import java.util.Map;

import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.REFLECTIONS_FULL_CLASS_NAME;
import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class ModelReaderClassStructure extends AbstractRestControllerModelClassStructure {

    private final ReaderType readerType;

    public ModelReaderClassStructure(final ReaderType readerType,
                                     final RestObjectModelClass modelClass,
                                     final ExchangeFormat exchangeFormat) {
        super(modelClass, exchangeFormat);
        this.readerType = require(readerType);
    }

    @Override
    protected Class<?> getBaseTransformerClass() {
        return ModelReader.class;
    }

    @Override
    protected void customize(final Map<String, Object> map) {
        map.put("HTTP_READER_TYPE", readerType);
        map.put("PARAMS_PRESENT", modelClass.isParamsPresent());
    }

    @Override
    protected void addRequiredImports(final ClassHeader.Builder classHeaderBuilder) {
        classHeaderBuilder
                .addImports(
                        ModelReader.class,
                        HttpRequest.class,
                        PathVariableMapping.class,
                        HttpHeaders.class,
                        HttpModelType.class,
                        QueryParams.class,
                        ExchangeDataFormatConverter.class,
                        JsonExchangeDataFormatConverter.class
                );
        if (isRequiredReflectionSetter()) {
            classHeaderBuilder.addStaticImport(REFLECTIONS_FULL_CLASS_NAME, "setFieldValue");
        }
    }

    @Override
    public boolean isRequiredReflectionSetter() {
        return modelClass.isInternalsReadReflectionRequired() ||
                modelClass.isHeaderReadReflectionRequired() ||
                modelClass.isPathVariablesReadReflectionRequired() ||
                modelClass.isParamsReadReflectionRequired();
    }
}
