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

package io.rxmicro.annotation.processor.rest.model;

import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.ClassStructure;
import io.rxmicro.annotation.processor.common.model.error.InternalErrorException;
import io.rxmicro.annotation.processor.common.model.type.ObjectModelClass;
import io.rxmicro.rest.model.ExchangeFormat;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.lang.model.element.ModuleElement;

import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getModelTransformerFullClassName;
import static io.rxmicro.annotation.processor.common.util.Names.getPackageName;
import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @since 0.1
 */
public abstract class AbstractModelJsonConverterClassStructure extends ClassStructure {

    protected final ModuleElement moduleElement;

    protected final RestObjectModelClass modelClass;

    private final Set<ObjectModelClass<RestModelField>> allChildrenObjectModelClasses;

    private final ExchangeFormat exchangeFormat;

    protected AbstractModelJsonConverterClassStructure(final ModuleElement moduleElement,
                                                       final RestObjectModelClass modelClass,
                                                       final ExchangeFormat exchangeFormat) {
        this.moduleElement = moduleElement;
        this.modelClass = require(modelClass);
        this.exchangeFormat = require(exchangeFormat);
        this.allChildrenObjectModelClasses = modelClass.getAllChildrenObjectModelClasses();
    }

    public final RestObjectModelClass getModelClass() {
        return modelClass;
    }

    public final String getModelFullClassName() {
        return modelClass.getJavaFullClassName();
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
            return "rest/$$Rest" + getBaseTransformerClass().getSimpleName() + "Template.javaftl";
        } else {
            throw new InternalErrorException("Not impl yet");
        }
    }

    @Override
    public final Map<String, Object> getTemplateVariables() {
        final Map<String, Object> map = new HashMap<>();
        map.put("JAVA_MODEL_CLASS", modelClass);
        map.put("JAVA_MODEL_CONVERTER_CHILDREN", allChildrenObjectModelClasses);
        customize(map);
        return map;
    }

    @Override
    public ClassHeader getClassHeader() {
        final ClassHeader.Builder builder =
                ClassHeader.newClassHeaderBuilder(getPackageName(modelClass.getModelTypeElement()))
                        .addImports(allChildrenObjectModelClasses.stream()
                                .map(v -> getModelTransformerFullClassName(
                                        v.getModelTypeElement(),
                                        getBaseTransformerClass()
                                ))
                                .toArray(String[]::new))
                        .addImports(Map.class)
                        .addImports(modelClass.getModelFieldTypes());
        addRequiredImports(builder);
        return builder.build();
    }

    protected abstract void addRequiredImports(ClassHeader.Builder builder);

    protected abstract Class<?> getBaseTransformerClass();

    protected void customize(final Map<String, Object> map) {
        // Sub classes can add additional attributes to template map
    }
}
