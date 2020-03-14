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

package io.rxmicro.annotation.processor.rest.model.converter;

import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.rest.model.AbstractModelJsonConverterClassStructure;
import io.rxmicro.annotation.processor.rest.model.RestObjectModelClass;
import io.rxmicro.exchange.json.detail.ModelToJsonConverter;
import io.rxmicro.json.JsonObjectBuilder;
import io.rxmicro.rest.model.ExchangeFormat;

import java.util.Map;

import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.$$_REFLECTIONS_FULL_CLASS_NAME;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class ModelToJsonConverterClassStructure extends AbstractModelJsonConverterClassStructure {

    public ModelToJsonConverterClassStructure(final RestObjectModelClass modelClass,
                                              final ExchangeFormat exchangeFormat) {
        super(modelClass, exchangeFormat);
    }

    @Override
    protected void addRequiredImports(final ClassHeader.Builder classHeaderBuilder) {
        classHeaderBuilder
                .addImports(
                        JsonObjectBuilder.class,
                        ModelToJsonConverter.class,
                        Map.class
                );
        if (isRequiredReflectionGetter()) {
            classHeaderBuilder.addStaticImport($$_REFLECTIONS_FULL_CLASS_NAME, "getFieldValue");
        }
    }

    @Override
    protected Class<?> getBaseTransformerClass() {
        return ModelToJsonConverter.class;
    }

    @Override
    public boolean isRequiredReflectionGetter() {
        return modelClass.isReadReflectionRequired();
    }
}
