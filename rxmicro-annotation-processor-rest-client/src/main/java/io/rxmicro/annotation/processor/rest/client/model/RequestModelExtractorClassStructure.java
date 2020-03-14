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

package io.rxmicro.annotation.processor.rest.client.model;

import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.ClassStructure;
import io.rxmicro.annotation.processor.rest.model.RestObjectModelClass;
import io.rxmicro.rest.client.detail.HeaderBuilder;
import io.rxmicro.rest.client.detail.QueryBuilder;
import io.rxmicro.rest.client.detail.RequestModelExtractor;

import java.util.HashMap;
import java.util.Map;

import static io.rxmicro.annotation.processor.common.model.ClassHeader.newClassHeaderBuilder;
import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.$$_REFLECTIONS_FULL_CLASS_NAME;
import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getModelTransformerFullClassName;
import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class RequestModelExtractorClassStructure extends ClassStructure {

    private final RestObjectModelClass modelClass;

    public RequestModelExtractorClassStructure(final RestObjectModelClass modelClass) {
        this.modelClass = require(modelClass);
    }

    public String getModelFullClassName() {
        return modelClass.getJavaFullClassName();
    }

    @Override
    public String getTargetFullClassName() {
        return getModelTransformerFullClassName(
                modelClass.getModelTypeElement(),
                RequestModelExtractor.class);
    }

    @Override
    public String getTemplateName() {
        return "rest/client/$$RestClientRequestModelExtractorTemplate.javaftl";
    }

    @Override
    public Map<String, Object> getTemplateVariables() {
        final Map<String, Object> map = new HashMap<>();
        map.put("JAVA_MODEL_CLASS", modelClass);
        map.put("HAS_HEADERS", modelClass.isHeadersPresent());
        map.put("HAS_QUERY", !modelClass.getParamEntries().isEmpty());
        return map;
    }

    @Override
    public ClassHeader getClassHeader() {
        final ClassHeader.Builder classHeaderBuilder = newClassHeaderBuilder(modelClass)
                .addImports(
                        RequestModelExtractor.class,
                        HeaderBuilder.class,
                        QueryBuilder.class
                )
                .addImports(modelClass.getModelFieldTypes());
        if (isRequiredReflectionGetter()) {
            classHeaderBuilder.addStaticImport($$_REFLECTIONS_FULL_CLASS_NAME, "getFieldValue");
        }
        return classHeaderBuilder.build();
    }

    @Override
    public boolean isRequiredReflectionGetter() {
        return modelClass.isReadReflectionRequired();
    }
}
