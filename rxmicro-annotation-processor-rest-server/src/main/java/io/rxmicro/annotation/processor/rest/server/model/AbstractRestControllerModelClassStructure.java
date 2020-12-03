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

package io.rxmicro.annotation.processor.rest.server.model;

import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.ClassStructure;
import io.rxmicro.annotation.processor.common.model.error.InternalErrorException;
import io.rxmicro.annotation.processor.rest.model.RestObjectModelClass;
import io.rxmicro.rest.model.ExchangeFormat;

import java.util.HashMap;
import java.util.Map;

import static io.rxmicro.annotation.processor.common.model.ClassHeader.newClassHeaderBuilder;
import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getModelTransformerFullClassName;
import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @since 0.1
 */
public abstract class AbstractRestControllerModelClassStructure extends ClassStructure {

    final RestObjectModelClass modelClass;

    private final ExchangeFormat exchangeFormat;

    AbstractRestControllerModelClassStructure(final RestObjectModelClass modelClass,
                                              final ExchangeFormat exchangeFormat) {
        this.modelClass = require(modelClass);
        this.exchangeFormat = require(exchangeFormat);
    }

    public final RestObjectModelClass getModelClass() {
        return modelClass;
    }

    @Override
    public final String getTargetFullClassName() {
        return getModelTransformerFullClassName(
                modelClass.getModelTypeElement(),
                getBaseTransformerClass()
        );
    }

    @Override
    public final String getTemplateName() {
        if (exchangeFormat == ExchangeFormat.JSON_EXCHANGE_FORMAT) {
            return "rest/server/$$RestJson" + getBaseTransformerClass().getSimpleName() + "Template.javaftl";
        } else {
            throw new InternalErrorException("Not impl yet");
        }
    }

    @Override
    public Map<String, Object> getTemplateVariables() {
        final Map<String, Object> map = new HashMap<>();
        map.put("JAVA_MODEL_CLASS", modelClass);
        customize(map);
        return map;
    }

    @Override
    public ClassHeader getClassHeader() {
        final ClassHeader.Builder builder = newClassHeaderBuilder(modelClass.getModelTypeElement())
                .addImports(modelClass.getModelFieldTypes());
        addRequiredImports(builder);
        return builder.build();
    }

    public final String getModelFullClassName() {
        return modelClass.getJavaFullClassName();
    }

    protected abstract Class<?> getBaseTransformerClass();

    protected void customize(final Map<String, Object> map) {
        // Sub classes can add additional attributes to template map
    }

    protected abstract void addRequiredImports(ClassHeader.Builder classHeaderBuilder);
}
