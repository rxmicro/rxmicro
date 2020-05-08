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

package io.rxmicro.annotation.processor.rest.model.converter;

import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.rest.model.AbstractModelJsonConverterClassStructure;
import io.rxmicro.annotation.processor.rest.model.RestObjectModelClass;
import io.rxmicro.exchange.json.detail.ModelFromJsonConverter;
import io.rxmicro.rest.model.ExchangeFormat;
import io.rxmicro.rest.model.HttpModelType;

import java.util.List;
import java.util.Map;

import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.REFLECTIONS_FULL_CLASS_NAME;
import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @since 0.1
 */
public final class ModelFromJsonConverterClassStructure extends AbstractModelJsonConverterClassStructure {

    private final ReaderType readerType;

    public ModelFromJsonConverterClassStructure(final ReaderType readerType,
                                                final RestObjectModelClass modelClass,
                                                final ExchangeFormat exchangeFormat) {
        super(modelClass, exchangeFormat);
        this.readerType = require(readerType);
    }

    @Override
    public boolean isRequiredReflectionSetter() {
        return modelClass.isParamsWriteReflectionRequired();
    }

    @Override
    protected void addRequiredImports(final ClassHeader.Builder classHeaderBuilder) {
        classHeaderBuilder
                .addImports(
                        ModelFromJsonConverter.class,
                        HttpModelType.class,
                        List.class
                );
        if (isRequiredReflectionSetter()) {
            classHeaderBuilder.addStaticImport(REFLECTIONS_FULL_CLASS_NAME, "setFieldValue");
        }
    }

    @Override
    protected Class<?> getBaseTransformerClass() {
        return ModelFromJsonConverter.class;
    }

    @Override
    protected void customize(final Map<String, Object> map) {
        map.put("HTTP_READER_TYPE", readerType);
    }
}
