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
import io.rxmicro.exchange.json.detail.JsonExchangeDataFormatConverter;
import io.rxmicro.http.HttpStandardHeaderNames;
import io.rxmicro.rest.detail.ExchangeDataFormatConverter;
import io.rxmicro.rest.model.ExchangeFormat;
import io.rxmicro.rest.server.detail.component.ModelWriter;
import io.rxmicro.rest.server.detail.model.HttpResponse;

import java.util.Map;

import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.REFLECTIONS_FULL_CLASS_NAME;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class ModelWriterClassStructure extends AbstractRestControllerModelClassStructure {

    public ModelWriterClassStructure(final RestObjectModelClass modelClass,
                                     final ExchangeFormat exchangeFormat) {
        super(modelClass, exchangeFormat);
    }

    @Override
    public boolean isRequiredReflectionGetter() {
        return modelClass.isReadReflectionRequired();
    }

    @Override
    protected Class<?> getBaseTransformerClass() {
        return ModelWriter.class;
    }

    @Override
    protected void addRequiredImports(final ClassHeader.Builder classHeaderBuilder) {
        classHeaderBuilder
                .addImports(
                        ModelWriter.class,
                        HttpResponse.class,
                        Map.class,
                        ExchangeDataFormatConverter.class,
                        JsonExchangeDataFormatConverter.class,
                        HttpStandardHeaderNames.class
                );
        if (modelClass.isReadReflectionRequired()) {
            classHeaderBuilder.addStaticImport(REFLECTIONS_FULL_CLASS_NAME, "getFieldValue");
        }
    }
}
