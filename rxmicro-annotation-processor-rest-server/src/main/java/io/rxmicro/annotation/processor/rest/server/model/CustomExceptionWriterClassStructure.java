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
import io.rxmicro.annotation.processor.common.util.UsedByFreemarker;
import io.rxmicro.annotation.processor.rest.model.RestObjectModelClass;
import io.rxmicro.rest.server.detail.component.CustomExceptionWriter;
import io.rxmicro.validation.ConstraintValidator;
import io.rxmicro.validation.detail.ResponseValidators;

import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import static io.rxmicro.annotation.processor.common.model.ClassHeader.newClassHeaderBuilder;
import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getModelTransformerFullClassName;
import static io.rxmicro.common.util.GeneratedClassRules.GENERATED_CLASS_NAME_PREFIX;

/**
 * @author nedis
 * @since 0.1
 */
public final class CustomExceptionWriterClassStructure extends ClassStructure {

    private final RestObjectModelClass modelClass;

    private final boolean withValidator;

    public CustomExceptionWriterClassStructure(final RestObjectModelClass modelClass,
                                               final boolean withValidator) {
        this.modelClass = modelClass;
        this.withValidator = withValidator;
    }

    @Override
    public String getTargetFullClassName() {
        return getModelTransformerFullClassName(
                modelClass.getModelTypeElement(),
                CustomExceptionWriter.class
        );
    }

    @Override
    public String getTemplateName() {
        return "rest/server/$$RestCustomExceptionModelWriterTemplate.javaftl";
    }

    @Override
    public Map<String, Object> getTemplateVariables() {
        final Map<String, Object> map = new HashMap<>();
        map.put("PREFIX", GENERATED_CLASS_NAME_PREFIX);
        map.put("JAVA_MODEL_CLASS", modelClass);
        map.put("WITH_VALIDATOR", withValidator);
        return map;
    }

    @Override
    public ClassHeader getClassHeader() {
        final ClassHeader.Builder builder = newClassHeaderBuilder(modelClass.getModelTypeElement())
                .addImports(modelClass.getModelFieldTypes())
                .addImports(CustomExceptionWriter.class)
                .addImports(getModelTransformerFullClassName(modelClass.getModelTypeElement(), Writer.class));
        if (withValidator) {
            builder
                    .addStaticImport(ResponseValidators.class, "validateResponse")
                    .addImports(getModelTransformerFullClassName(modelClass.getModelTypeElement(), ConstraintValidator.class));
        }
        return builder.build();
    }

    @UsedByFreemarker("$$RestControllerAggregatorTemplate.javaftl")
    public String getModelSimpleClassName() {
        return modelClass.getJavaSimpleClassName();
    }

    public String getModelFullClassName() {
        return modelClass.getJavaFullClassName();
    }
}
