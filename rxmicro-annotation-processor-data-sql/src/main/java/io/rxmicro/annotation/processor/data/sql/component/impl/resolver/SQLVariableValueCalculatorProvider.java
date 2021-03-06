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

package io.rxmicro.annotation.processor.data.sql.component.impl.resolver;

import io.rxmicro.annotation.processor.data.sql.model.SQLDataObjectModelClass;
import io.rxmicro.annotation.processor.data.sql.model.SQLVariableValue;
import io.rxmicro.annotation.processor.data.sql.model.VariableContext;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static io.rxmicro.annotation.processor.data.sql.model.SQLVariableValue.createByIdFilter;
import static io.rxmicro.annotation.processor.data.sql.model.SQLVariableValue.createColumnList;
import static io.rxmicro.annotation.processor.data.sql.model.SQLVariableValue.createSetColumnList;
import static io.rxmicro.annotation.processor.data.sql.model.SQLVariableValue.createSetColumnListUsingPseudoTable;
import static io.rxmicro.annotation.processor.data.sql.model.SQLVariableValue.createValues;
import static io.rxmicro.data.sql.SupportedVariables.ALL_COLUMNS;
import static io.rxmicro.data.sql.SupportedVariables.BY_ID_FILTER;
import static io.rxmicro.data.sql.SupportedVariables.ID_COLUMNS;
import static io.rxmicro.data.sql.SupportedVariables.INSERTED_COLUMNS;
import static io.rxmicro.data.sql.SupportedVariables.ON_CONFLICT_UPDATE_INSERTED_COLUMNS;
import static io.rxmicro.data.sql.SupportedVariables.RETURNING_COLUMNS;
import static io.rxmicro.data.sql.SupportedVariables.TABLE;
import static io.rxmicro.data.sql.SupportedVariables.UPDATED_COLUMNS;
import static io.rxmicro.data.sql.SupportedVariables.VALUES;

/**
 * @author nedis
 * @since 0.1
 */
final class SQLVariableValueCalculatorProvider {

    private static final BiFunction<SQLDataObjectModelClass<?>, VariableContext, Object> COLUMN_LIST_FUNCTION =
            (modelClass, context) -> nullIfEmpty(
                    createColumnList(
                            modelClass.getParamEntries().stream()
                                    .map(e -> e.getKey().getColumnName())
                                    .collect(Collectors.toList())
                    )
            );

    static final Map<String, BiFunction<SQLDataObjectModelClass<?>, VariableContext, Object>> VARIABLE_RESOLVER_PROVIDER =
            Map.of(
                    ALL_COLUMNS,
                    COLUMN_LIST_FUNCTION,
                    //-------------------------------------------------------------------------------------------
                    "*",
                    COLUMN_LIST_FUNCTION,
                    //-------------------------------------------------------------------------------------------
                    ID_COLUMNS,
                    (modelClass, context) -> nullIfEmpty(
                            createColumnList(
                                    modelClass.getPrimaryKeysParams().stream()
                                            .map(e -> e.getKey().getModelName())
                                            .collect(Collectors.toList())
                            )
                    ),
                    //-------------------------------------------------------------------------------------------
                    BY_ID_FILTER,
                    (modelClass, context) -> nullIfEmpty(
                            createByIdFilter(
                                    modelClass.getPrimaryKeysParams().stream()
                                            .map(e -> e.getKey().getModelName())
                                            .collect(Collectors.toList())
                            )
                    ),
                    //-------------------------------------------------------------------------------------------
                    INSERTED_COLUMNS,
                    (modelClass, context) -> nullIfEmpty(
                            createColumnList(
                                    modelClass.getInsertableParams().stream()
                                            .filter(e -> e.getKey().getInsertValue(context).isPresent())
                                            .map(e -> e.getKey().getModelName())
                                            .collect(Collectors.toList())
                            )
                    ),
                    //-------------------------------------------------------------------------------------------
                    VALUES,
                    (modelClass, context) -> nullIfEmpty(
                            createValues(
                                    modelClass.getInsertableParams().stream()
                                            .flatMap(e -> e.getKey().getInsertValue(context).stream())
                                            .collect(Collectors.toList())
                            )
                    ),
                    //-------------------------------------------------------------------------------------------
                    UPDATED_COLUMNS,
                    (modelClass, context) -> nullIfEmpty(
                            createSetColumnList(
                                    modelClass.getUpdatableParams().stream()
                                            .map(e -> e.getKey().getModelName())
                                            .collect(Collectors.toList())
                            )
                    ),
                    //-------------------------------------------------------------------------------------------
                    TABLE,
                    (modelClass, context) -> context.getCurrentTableName().getSchema()
                            .map(s -> s + "." + context.getCurrentTableName().getSimpleName())
                            .orElse(context.getCurrentTableName().getSimpleName()),
                    //-------------------------------------------------------------------------------------------
                    ON_CONFLICT_UPDATE_INSERTED_COLUMNS,
                    (modelClass, context) -> nullIfEmpty(
                            createSetColumnListUsingPseudoTable(
                                    modelClass.getUpdatableParams().stream()
                                            .map(e -> e.getKey().getModelName())
                                            .collect(Collectors.toList()),
                                    context.getPseudoTableNameToReadOriginalValuesForModification()
                            )
                    ),
                    //-------------------------------------------------------------------------------------------
                    RETURNING_COLUMNS,
                    COLUMN_LIST_FUNCTION
            );

    private static SQLVariableValue nullIfEmpty(final SQLVariableValue sqlVariableValue) {
        return sqlVariableValue.getColumns().isEmpty() ? null : sqlVariableValue;
    }

    private SQLVariableValueCalculatorProvider() {
    }
}
