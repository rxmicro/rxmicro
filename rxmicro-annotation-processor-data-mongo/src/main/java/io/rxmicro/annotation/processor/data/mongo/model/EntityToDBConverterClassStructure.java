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

package io.rxmicro.annotation.processor.data.mongo.model;

import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.ClassStructure;
import io.rxmicro.annotation.processor.common.model.ModelAccessorType;
import io.rxmicro.annotation.processor.common.model.type.ModelClass;
import io.rxmicro.annotation.processor.common.model.type.ObjectModelClass;
import io.rxmicro.common.util.Requires;
import io.rxmicro.data.mongo.detail.EntityToMongoDBConverter;
import org.bson.Document;

import java.util.HashMap;
import java.util.List;
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
public final class EntityToDBConverterClassStructure extends ClassStructure {

    private final Map.Entry<MongoDataModelField, ModelClass> id;

    private final MongoDataObjectModelClass modelClass;

    private final Set<ObjectModelClass<MongoDataModelField>> allChildrenObjectModelClasses;

    public EntityToDBConverterClassStructure(final Map.Entry<MongoDataModelField, ModelClass> id,
                                             final MongoDataObjectModelClass modelClass) {
        this.id = require(id);
        this.modelClass = require(modelClass);
        this.allChildrenObjectModelClasses = modelClass.getAllChildrenObjectModelClasses();
    }

    public EntityToDBConverterClassStructure(final MongoDataObjectModelClass modelClass) {
        this.id = null;
        this.modelClass = require(modelClass);
        this.allChildrenObjectModelClasses = modelClass.getAllChildrenObjectModelClasses();
    }

    @Override
    public String getTargetFullClassName() {
        return getModelTransformerFullClassName(
                modelClass.getModelTypeElement(),
                EntityToMongoDBConverter.class);
    }

    @Override
    public String getTemplateName() {
        return "data/mongo/$$MongoEntityToDBConverterTemplate.javaftl";
    }

    @Override
    public Map<String, Object> getTemplateVariables() {
        final Map<String, Object> map = new HashMap<>();
        map.put("JAVA_MODEL_CLASS", modelClass);
        map.put("JAVA_MODEL_CONVERTER_CHILDREN", allChildrenObjectModelClasses);
        // Only not final fields could be set
        if (hasId()) {
            map.put("ID", id);
            map.put("ID_NOT_FINAL", id.getKey().isNotFinal());
        }
        return map;
    }

    @Override
    public ClassHeader getClassHeader() {
        final ClassHeader.Builder classHeaderBuilder = newClassHeaderBuilder(modelClass.getModelTypeElement());
        classHeaderBuilder
                .addImports(
                        EntityToMongoDBConverter.class,
                        Document.class,
                        List.class
                )
                .addImports(allChildrenObjectModelClasses.stream()
                        .map(v -> getModelTransformerFullClassName(v.getModelTypeElement(), EntityToMongoDBConverter.class))
                        .toArray(String[]::new))
                .addImports(modelClass.getModelFieldTypes());
        if (isRequiredReflectionGetter()) {
            classHeaderBuilder.addStaticImport(REFLECTIONS_FULL_CLASS_NAME, "getFieldValue");
        }
        if (isRequiredReflectionSetter()) {
            classHeaderBuilder.addStaticImport(REFLECTIONS_FULL_CLASS_NAME, "setFieldValue");
        }
        classHeaderBuilder.addStaticImport(Requires.class, "require");
        return classHeaderBuilder.build();
    }

    @Override
    public boolean isRequiredReflectionGetter() {
        return modelClass.isReadReflectionRequired();
    }

    @Override
    public boolean isRequiredReflectionSetter() {
        return hasId() && id.getKey().isNotFinal() && id.getKey().getModelWriteAccessorType() == ModelAccessorType.REFLECTION;
    }

    private boolean hasId() {
        return id != null;
    }
}
