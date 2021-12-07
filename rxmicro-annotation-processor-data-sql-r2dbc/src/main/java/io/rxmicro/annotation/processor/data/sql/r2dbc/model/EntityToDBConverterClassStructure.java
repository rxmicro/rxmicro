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

package io.rxmicro.annotation.processor.data.sql.r2dbc.model;

import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.type.ModelClass;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataModelField;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataObjectModelClass;
import io.rxmicro.common.util.Requires;
import io.rxmicro.data.sql.r2dbc.detail.EntityToR2DBCSQLDBConverter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.lang.model.element.ModuleElement;

import static io.rxmicro.annotation.processor.common.model.ClassHeader.newClassHeaderBuilder;
import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getModelTransformerFullClassName;
import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getReflectionsFullClassName;

/**
 * @author nedis
 * @since 0.1
 */
public final class EntityToDBConverterClassStructure<DMF extends SQLDataModelField, DMC extends SQLDataObjectModelClass<DMF>>
        extends AbstractEntityConverterClassStructure<DMF, DMC> {

    private final ModuleElement moduleElement;

    public EntityToDBConverterClassStructure(final ModuleElement moduleElement,
                                             final DMC modelClass) {
        super(modelClass);
        this.moduleElement = moduleElement;
    }

    @Override
    public String getTargetFullClassName() {
        return getModelTransformerFullClassName(
                modelClass.getModelTypeElement(),
                EntityToR2DBCSQLDBConverter.class);
    }

    @Override
    public String getTemplateName() {
        return "data/sql/r2dbc/$$SQLEntityToSQLDBConverterTemplate.javaftl";
    }

    @Override
    public Map<String, Object> getTemplateVariables() {
        final Map<String, Object> map = new HashMap<>();
        map.put("JAVA_MODEL_CLASS", modelClass);
        if (modelClass.isInsertable()) {
            map.put("INSERTABLE_PARAMS", modelClass.getInsertableParams());
        }
        if (modelClass.isUpdatable()) {
            map.put("UPDATABLE_PARAMS", modelClass.getUpdatableParams());
            map.put("PRIMARY_KEY_PARAMS", modelClass.getPrimaryKeysParams());
        }
        map.put("IS_DELETABLE", modelClass.isDeletable());
        if (modelClass.isDeletable()) {
            final Set<Map.Entry<DMF, ModelClass>> primaryKeysParams = modelClass.getPrimaryKeysParams();
            if (primaryKeysParams.size() == 1) {
                map.put("PRIMARY_KEY_PARAM", primaryKeysParams.iterator().next());
            } else {
                map.put("PRIMARY_KEY_PARAMS", primaryKeysParams);
            }
        }
        map.put("SET_ENTITY_FIELDS_CONVERTER_METHODS", setEntityFieldsConverterMethods);
        return map;
    }

    @Override
    public ClassHeader getClassHeader() {
        final ClassHeader.Builder classHeaderBuilder = newClassHeaderBuilder(modelClass)
                .addImports(
                        EntityToR2DBCSQLDBConverter.class,
                        Row.class,
                        RowMetadata.class
                );
        if (isRequiredReflectionGetter()) {
            classHeaderBuilder.addStaticImport(getReflectionsFullClassName(moduleElement), "getFieldValue");
        }
        setEntityFieldsConverterMethods.stream()
                .flatMap(converterMethod -> converterMethod.getValue().stream())
                .forEach(entry -> classHeaderBuilder.addImports(entry.getKey().getFieldClass()));
        if (modelClass.isInsertable()) {
            modelClass.getInsertableParams().stream()
                    .filter(p -> p.getKey().isInsertValuePlaceholder())
                    .forEach(e -> classHeaderBuilder.addImports(e.getKey().getFieldClass()));
        }
        if (modelClass.isUpdatable()) {
            modelClass.getUpdatableParams().forEach(e -> classHeaderBuilder.addImports(e.getKey().getFieldClass()));
        }
        if (modelClass.isPrimaryKeysPresent()) {
            classHeaderBuilder.addStaticImport(Requires.class, "require");
        }
        return classHeaderBuilder.build();
    }

    @Override
    public boolean isRequiredReflectionGetter() {
        return modelClass.isReadReflectionRequired();
    }
}
