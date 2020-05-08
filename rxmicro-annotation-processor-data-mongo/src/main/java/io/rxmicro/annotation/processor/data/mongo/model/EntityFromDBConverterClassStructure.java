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

package io.rxmicro.annotation.processor.data.mongo.model;

import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.ClassStructure;
import io.rxmicro.annotation.processor.common.model.type.ObjectModelClass;
import io.rxmicro.data.mongo.detail.EntityFromMongoDBConverter;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static io.rxmicro.annotation.processor.common.model.ClassHeader.newClassHeaderBuilder;
import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.REFLECTIONS_FULL_CLASS_NAME;
import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getModelTransformerFullClassName;
import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @since 0.1
 */
public final class EntityFromDBConverterClassStructure extends ClassStructure {

    private final MongoDataObjectModelClass modelClass;

    private final Set<ObjectModelClass<MongoDataModelField>> allChildrenObjectModelClasses;

    public EntityFromDBConverterClassStructure(final MongoDataObjectModelClass modelClass) {
        this.modelClass = require(modelClass);
        this.allChildrenObjectModelClasses = modelClass.getAllChildrenObjectModelClasses();
    }

    @Override
    public String getTargetFullClassName() {
        return getModelTransformerFullClassName(
                modelClass.getModelTypeElement(),
                EntityFromMongoDBConverter.class);
    }

    @Override
    public String getTemplateName() {
        return "data/mongo/$$MongoEntityFromDBConverterTemplate.javaftl";
    }

    @Override
    public Map<String, Object> getTemplateVariables() {
        final Map<String, Object> map = new HashMap<>();
        map.put("JAVA_MODEL_CLASS", modelClass);
        map.put("JAVA_MODEL_CONVERTER_CHILDREN", allChildrenObjectModelClasses);
        return map;
    }

    @Override
    public ClassHeader getClassHeader() {
        final ClassHeader.Builder classHeaderBuilder = newClassHeaderBuilder(modelClass)
                .addImports(
                        EntityFromMongoDBConverter.class,
                        Document.class
                )
                .addImports(allChildrenObjectModelClasses.stream()
                        .map(v -> getModelTransformerFullClassName(v.getModelTypeElement(), EntityFromMongoDBConverter.class))
                        .toArray(String[]::new))
                .addImports(modelClass.getModelFieldTypes());
        if (isRequiredReflectionSetter()) {
            classHeaderBuilder.addStaticImport(REFLECTIONS_FULL_CLASS_NAME, "setFieldValue");
        }
        return classHeaderBuilder.build();
    }

    @Override
    public boolean isRequiredReflectionSetter() {
        return modelClass.isWriteReflectionRequired();
    }
}
