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

package io.rxmicro.annotation.processor.data.sql.model;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class SQLVariableValue {

    private static final String VALUE_VAR = "${VALUE-VAR}";

    private final List<String> columns;

    private final List<String> itemTemplate;

    private final String delimiter;

    public static SQLVariableValue createColumnList(final List<String> columns) {
        return new SQLVariableValue(columns, List.of(VALUE_VAR), ",");
    }

    public static SQLVariableValue createValues(final List<String> values) {
        return new SQLVariableValue(values, List.of(VALUE_VAR), ",");
    }

    public static SQLVariableValue createSetColumnList(final List<String> columns) {
        return new SQLVariableValue(columns, List.of(VALUE_VAR, "=", "?"), ",");
    }

    public static SQLVariableValue createByIdFilter(final List<String> columns) {
        return new SQLVariableValue(columns, List.of(VALUE_VAR, "=", "?"), "AND");
    }

    private SQLVariableValue(final List<String> columns,
                             final List<String> itemTemplate,
                             final String delimiter) {
        this.columns = require(columns);
        this.itemTemplate = require(itemTemplate);
        this.delimiter = require(delimiter);
    }

    public List<String> getColumns() {
        return columns;
    }

    public List<String> getSQLTokens() {
        return columns.stream()
                .flatMap(column -> Stream.concat(
                        Stream.of(delimiter),
                        itemTemplate.stream().map(v -> VALUE_VAR.equals(v) ? column : v))
                )
                .skip(1)
                .collect(Collectors.toList());
    }
}
