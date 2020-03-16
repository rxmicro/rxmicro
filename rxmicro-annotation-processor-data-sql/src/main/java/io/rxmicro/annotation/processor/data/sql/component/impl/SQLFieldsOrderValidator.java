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

package io.rxmicro.annotation.processor.data.sql.component.impl;

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataObjectModelClass;
import io.rxmicro.annotation.processor.data.sql.model.SelectedColumn;

import javax.lang.model.element.ExecutableElement;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@Singleton
public final class SQLFieldsOrderValidator {

    public void validateSelectedColumn(final ExecutableElement method,
                                       final SQLDataObjectModelClass<?> modelClass,
                                       final List<String> defaultColumns,
                                       final List<SelectedColumn> actualColumns) {
        final Set<String> uniqueColumns = new HashSet<>();
        for (int i = 0; i < actualColumns.size(); i++) {
            final SelectedColumn column = actualColumns.get(i);
            final Optional<String> optionalCaption = column.getCaption();
            if (optionalCaption.isEmpty()) {
                throw missingAliasException(method, i, column);
            } else {
                final String actualCaption = optionalCaption.get();
                if (!defaultColumns.contains(actualCaption)) {
                    throw unmappingColumnError(method, modelClass, actualCaption);
                }
                validateUniqueColumnName(method, uniqueColumns, actualCaption, column.toString());
            }
        }
    }

    public void validateStringColumns(final ExecutableElement method,
                                      final SQLDataObjectModelClass<?> modelClass,
                                      final List<String> defaultColumns,
                                      final List<String> actualColumns) {
        final Set<String> uniqueColumns = new HashSet<>();
        for (final String column : actualColumns) {
            if (!defaultColumns.contains(column)) {
                throw unmappingColumnError(method, modelClass, column);
            }
            validateUniqueColumnName(method, uniqueColumns, column, column);
        }
    }

    public void validateSelectedColumn(final ExecutableElement method,
                                       final List<SelectedColumn> actualColumns) {
        final Set<String> uniqueColumns = new HashSet<>();
        for (int i = 0; i < actualColumns.size(); i++) {
            final SelectedColumn column = actualColumns.get(i);
            final Optional<String> optionalCaption = column.getCaption();
            if (optionalCaption.isEmpty()) {
                throw missingAliasException(method, i, column);
            } else {
                final String actualCaption = optionalCaption.get();
                validateUniqueColumnName(method, uniqueColumns, actualCaption, column.toString());
            }

        }
    }

    private InterruptProcessingException missingAliasException(final ExecutableElement method,
                                                               final int index,
                                                               final SelectedColumn column) {
        return new InterruptProcessingException(
                method,
                "Missing alias for expression: Index=? (Zero based), Expression='?'. Add column alias!",
                index, column.getExpression());
    }

    private InterruptProcessingException unmappingColumnError(final ExecutableElement method,
                                                              final SQLDataObjectModelClass<?> modelClass,
                                                              final String actualCaption) {
        return new InterruptProcessingException(
                method,
                "Selected column does not map to any entity field. Column='?', Entity class='?'. " +
                        "Remove the column from select fields or add appropriate entity field!",
                actualCaption,
                modelClass.getModelTypeElement().getQualifiedName()
        );
    }

    private void validateUniqueColumnName(final ExecutableElement method,
                                          final Set<String> uniqueColumns,
                                          final String actualCaption,
                                          final String columnExpression) {
        if (!uniqueColumns.add(actualCaption)) {
            throw new InterruptProcessingException(
                    method,
                    "Detected duplicate of column or alias in select field list: '?'. " +
                            "Remove the duplicate!",
                    columnExpression
            );
        }
    }
}
