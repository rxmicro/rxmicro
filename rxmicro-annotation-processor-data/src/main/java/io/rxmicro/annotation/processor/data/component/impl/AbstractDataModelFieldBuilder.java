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

package io.rxmicro.annotation.processor.data.component.impl;

import com.google.inject.Inject;
import io.rxmicro.annotation.processor.common.component.impl.AbstractModelFieldBuilder;
import io.rxmicro.annotation.processor.common.model.AnnotatedModelElement;
import io.rxmicro.annotation.processor.common.model.ModelFieldBuilderOptions;
import io.rxmicro.annotation.processor.common.model.ModelFieldType;
import io.rxmicro.annotation.processor.common.model.definition.SupportedTypesProvider;
import io.rxmicro.annotation.processor.common.model.type.PrimitiveModelClass;
import io.rxmicro.annotation.processor.data.model.DataModelField;
import io.rxmicro.annotation.processor.data.model.DataObjectModelClass;
import io.rxmicro.annotation.processor.data.model.DataPrimitiveModelClass;
import io.rxmicro.data.Column;
import io.rxmicro.data.ColumnMappingStrategy;

import java.util.Optional;
import java.util.Set;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

import static io.rxmicro.common.CommonConstants.EMPTY_STRING;

/**
 * @author nedis
 * @since 0.1
 */
public abstract class AbstractDataModelFieldBuilder<DMF extends DataModelField, DMC extends DataObjectModelClass<DMF>>
        extends AbstractModelFieldBuilder<DMF, DMC> {

    @Inject
    private SupportedTypesProvider supportedTypesProvider;

    @Override
    protected final SupportedTypesProvider getSupportedTypesProvider() {
        return supportedTypesProvider;
    }

    @Override
    protected PrimitiveModelClass createPrimitiveModelClass(final TypeMirror type) {
        return new DataPrimitiveModelClass(type);
    }

    @Override
    protected DMF build(final ModelFieldType modelFieldType,
                        final VariableElement field,
                        final TypeElement typeElement,
                        final ModelNames modelNames,
                        final Set<String> fieldNames,
                        final int nestedLevel,
                        final ModelFieldBuilderOptions options) {
        final String fieldName = field.getSimpleName().toString();
        if (!fieldNames.add(fieldName)) {
            error(field, "Detected duplicate of class field name: ?", fieldName);
        }
        final String modelName;
        final AnnotatedModelElement annotated = build(typeElement, field);
        final boolean id;
        if (isColumnId(annotated)) {
            id = true;
            modelName = getColumnIdFixedModelName().orElseGet(() ->
                    getColumnName(typeElement, fieldName, annotated));
        } else {
            id = false;
            modelName = getColumnName(typeElement, fieldName, annotated);
        }
        if (!modelNames.modelNames("columns").add(modelName)) {
            error(annotated.getElementAnnotatedBy(Column.class).orElse(field), "Detected duplicate of column name: ?", modelName);
        }
        final Column column = annotated.getAnnotation(Column.class);
        final int length = column != null ? column.length() : Column.NOT_SPECIFIED_LENGTH;
        final boolean nullable = column != null && column.nullable();
        return validateAndReturn(options, build(annotated, modelName, length, nullable, id), typeElement);
    }

    protected abstract DMF build(AnnotatedModelElement annotated,
                                 String modelName,
                                 int length,
                                 boolean nullable,
                                 boolean isId);

    private String getColumnName(final TypeElement typeElement,
                                 final String fieldName,
                                 final AnnotatedModelElement annotated) {
        final Column column = annotated.getAnnotation(Column.class);
        final ColumnMappingStrategy strategy = typeElement.getAnnotation(ColumnMappingStrategy.class);
        return getModelName(column != null ? column.value() : EMPTY_STRING, strategy, fieldName, strategy::value);
    }

    protected abstract boolean isColumnId(AnnotatedModelElement annotated);

    protected Optional<String> getColumnIdFixedModelName() {
        return Optional.empty();
    }
}
