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

package io.rxmicro.annotation.processor.rest.client.model;

import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.ClassStructure;
import io.rxmicro.annotation.processor.common.model.error.InternalErrorException;
import io.rxmicro.annotation.processor.rest.model.RestObjectModelClass;
import io.rxmicro.http.HttpHeaders;
import io.rxmicro.rest.client.detail.ClientModelReader;
import io.rxmicro.rest.client.detail.HttpResponse;
import io.rxmicro.rest.model.ExchangeFormat;
import io.rxmicro.rest.model.HttpModelType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.rxmicro.annotation.processor.common.model.ClassHeader.newClassHeaderBuilder;
import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.REFLECTIONS_FULL_CLASS_NAME;
import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getModelTransformerFullClassName;
import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @since 0.1
 */
public final class ClientModelReaderClassStructure extends ClassStructure {

    private final RestObjectModelClass modelClass;

    private final ExchangeFormat exchangeFormat;

    private final ClientModelReaderType modelReaderType;

    public ClientModelReaderClassStructure(final RestObjectModelClass modelClass,
                                           final ExchangeFormat exchangeFormat,
                                           final ClientModelReaderType modelReaderType) {
        this.modelClass = require(modelClass);
        this.exchangeFormat = require(exchangeFormat);
        this.modelReaderType = modelReaderType;
    }

    public String getModelFullClassName() {
        return modelClass.getJavaFullClassName();
    }

    @Override
    public String getTargetFullClassName() {
        return getModelTransformerFullClassName(
                modelClass.getModelTypeElement(),
                ClientModelReader.class
        );
    }

    @Override
    public String getTemplateName() {
        if (exchangeFormat == ExchangeFormat.JSON_EXCHANGE_FORMAT) {
            return "rest/client/$$RestJsonClientModelReaderTemplate.javaftl";
        } else {
            throw new InternalErrorException("Not impl yet");
        }
    }

    @Override
    public Map<String, Object> getTemplateVariables() {
        final Map<String, Object> map = new HashMap<>();
        map.put("JAVA_MODEL_CLASS", modelClass);
        map.put("PARENT_CLASS", ClientModelReader.class.getSimpleName());
        map.put("READER_TYPE", modelReaderType);
        map.put("WITH_BODY", modelClass.isParamEntriesPresent());
        return map;
    }

    @Override
    public ClassHeader getClassHeader() {
        final ClassHeader.Builder classHeaderBuilder = newClassHeaderBuilder(modelClass.getModelTypeElement())
                .addImports(
                        HttpResponse.class,
                        ClientModelReader.class,
                        List.class,
                        HttpHeaders.class,
                        HttpModelType.class
                )
                .addImports(modelClass.getModelFieldTypes());
        if (isRequiredReflectionSetter()) {
            classHeaderBuilder.addStaticImport(REFLECTIONS_FULL_CLASS_NAME, "setFieldValue");
        }
        return classHeaderBuilder.build();
    }

    @Override
    public boolean isRequiredReflectionSetter() {
        return modelClass.isInternalsReadReflectionRequired() ||
                modelClass.isHeaderReadReflectionRequired();
    }
}
