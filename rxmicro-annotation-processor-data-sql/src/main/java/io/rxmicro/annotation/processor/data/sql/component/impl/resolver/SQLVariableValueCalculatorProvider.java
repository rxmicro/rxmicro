/*
 * Copyright (c) 2020. http://rxmicro.io
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

import io.rxmicro.annotation.processor.data.sql.model.SQLDataModelField;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataObjectModelClass;
import io.rxmicro.annotation.processor.data.sql.model.SQLVariableValue;
import io.rxmicro.annotation.processor.data.sql.model.VariableContext;
import io.rxmicro.data.sql.PrimaryKey;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static io.rxmicro.annotation.processor.data.sql.model.SQLVariableValue.createByIdFilter;
import static io.rxmicro.annotation.processor.data.sql.model.SQLVariableValue.createColumnList;
import static io.rxmicro.annotation.processor.data.sql.model.SQLVariableValue.createSetColumnList;
import static io.rxmicro.annotation.processor.data.sql.model.SQLVariableValue.createValues;
import static io.rxmicro.data.sql.SupportedVariables.ALL_COLUMNS;
import static io.rxmicro.data.sql.SupportedVariables.BY_ID_FILTER;
import static io.rxmicro.data.sql.SupportedVariables.ID_COLUMNS;
import static io.rxmicro.data.sql.SupportedVariables.INSERTED_COLUMNS;
import static io.rxmicro.data.sql.SupportedVariables.TABLE;
import static io.rxmicro.data.sql.SupportedVariables.UPDATED_COLUMNS;
import static io.rxmicro.data.sql.SupportedVariables.VALUES;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
final class SQLVariableValueCalculatorProvider {

    final static Map<String, BiFunction<SQLDataObjectModelClass<? extends SQLDataModelField>, VariableContext, Object>> VARIABLE_RESOLVER_PROVIDER = Map.of(
            ALL_COLUMNS,
            (modelClass, context) -> nullIfEmpty(
                    createColumnList(
                            modelClass.getParamEntries().stream()
                                    .map(e -> e.getKey().getSelectedColumnNameOrCastExpression())
                                    .collect(Collectors.toList())
                    )
            ),
            //-------------------------------------------------------------------------------------------
            "*",
            (modelClass, context) -> nullIfEmpty(
                    createColumnList(
                            modelClass.getParamEntries().stream()
                                    .map(e -> e.getKey().getSelectedColumnNameOrCastExpression())
                                    .collect(Collectors.toList())
                    )
            ),
            //-------------------------------------------------------------------------------------------
            ID_COLUMNS,
            (modelClass, context) -> nullIfEmpty(
                    createColumnList(
                            modelClass.getParamEntries().stream()
                                    .filter(e -> e.getKey().getAnnotation(PrimaryKey.class) != null)
                                    .map(e -> e.getKey().getModelName())
                                    .collect(Collectors.toList())
                    )
            ),
            //-------------------------------------------------------------------------------------------
            BY_ID_FILTER,
            (modelClass, context) -> nullIfEmpty(
                    createByIdFilter(
                            modelClass.getParamEntries().stream()
                                    .filter(e -> e.getKey().getAnnotation(PrimaryKey.class) != null)
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
            (modelClass, context) -> context.getCurrentTable().getSchema()
                    .map(s -> s + "." + context.getCurrentTable().getTableSimpleName())
                    .orElse(context.getCurrentTable().getTableSimpleName())
            //-------------------------------------------------------------------------------------------
    );

    private static SQLVariableValue nullIfEmpty(final SQLVariableValue sqlVariableValue) {
        return sqlVariableValue.getColumns().isEmpty() ? null : sqlVariableValue;
    }
}
