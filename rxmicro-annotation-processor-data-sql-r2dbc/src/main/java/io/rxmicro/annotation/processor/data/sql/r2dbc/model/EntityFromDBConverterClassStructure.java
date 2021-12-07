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
import io.rxmicro.data.sql.r2dbc.detail.EntityFromR2DBCSQLDBConverter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.lang.model.element.ModuleElement;

import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getModelTransformerFullClassName;
import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getReflectionsFullClassName;
import static java.util.Map.entry;
import static java.util.stream.Collectors.toList;

/**
 * @author nedis
 * @since 0.1
 */
public final class EntityFromDBConverterClassStructure<DMF extends SQLDataModelField, DMC extends SQLDataObjectModelClass<DMF>>
        extends AbstractEntityConverterClassStructure<DMF, DMC> {

    private final ModuleElement moduleElement;

    private final List<Map.Entry<String, List<Map.Entry<DMF, ModelClass>>>> fromDBConverterMethods;

    public EntityFromDBConverterClassStructure(final ModuleElement moduleElement,
                                               final DMC modelClass) {
        super(modelClass);
        this.moduleElement = moduleElement;
        fromDBConverterMethods = getFromDBConverterMethods();
    }

    @Override
    public String getTargetFullClassName() {
        return getModelTransformerFullClassName(
                modelClass.getModelTypeElement(),
                EntityFromR2DBCSQLDBConverter.class);
    }

    @Override
    public String getTemplateName() {
        return "data/sql/r2dbc/$$SQLEntityFromSQLDBConverterTemplate.javaftl";
    }

    @Override
    public Map<String, Object> getTemplateVariables() {
        final Map<String, Object> map = new HashMap<>();
        map.put("JAVA_MODEL_CLASS", modelClass);
        map.put("FROM_DB_CONVERTER_METHODS", fromDBConverterMethods);
        map.put("SET_ENTITY_FIELDS_CONVERTER_METHODS", setEntityFieldsConverterMethods);
        return map;
    }

    @Override
    public ClassHeader getClassHeader() {
        final ClassHeader.Builder classHeaderBuilder = ClassHeader.newClassHeaderBuilder(modelClass)
                .addImports(
                        EntityFromR2DBCSQLDBConverter.class,
                        Row.class,
                        RowMetadata.class
                );
        if (isRequiredReflectionSetter()) {
            classHeaderBuilder.addStaticImport(getReflectionsFullClassName(moduleElement), "setFieldValue");
        }
        fromDBConverterMethods.stream()
                .flatMap(converterMethod -> converterMethod.getValue().stream())
                .forEach(entry -> classHeaderBuilder.addImports(entry.getKey().getFieldClass()));
        setEntityFieldsConverterMethods.stream()
                .flatMap(converterMethod -> converterMethod.getValue().stream())
                .forEach(entry -> classHeaderBuilder.addImports(entry.getKey().getFieldClass()));
        return classHeaderBuilder.build();
    }

    @Override
    public boolean isRequiredReflectionSetter() {
        return modelClass.isWriteReflectionRequired();
    }

    private List<Map.Entry<String, List<Map.Entry<DMF, ModelClass>>>> getFromDBConverterMethods() {
        return modelClass.getEntityFromDBConverterMethods().stream()
                .map(m -> entry(m.getName(), toModelParams(m.getSelectedColumns())))
                .collect(toList());
    }
}
