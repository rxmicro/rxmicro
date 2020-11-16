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

package io.rxmicro.test.dbunit.internal;

import io.rxmicro.config.ConfigException;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITableIterator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.rxmicro.common.util.Formats.format;

/**
 * @author nedis
 * @since 0.7
 */
public class OrderByColumnExtractor {

    public Map<String, Set<String>> getOrderByColumns(final IDataSet expectedDataSet,
                                                      final String[] orderBy) throws DataSetException {
        if (orderBy.length > 0) {
            final Map<String, Set<String>> map = new LinkedHashMap<>();
            for (final String orderByColumn : orderBy) {
                // IF order column does not contain table name:
                if (orderByColumn.indexOf('.') == -1) {
                    final List<String> names = new ArrayList<>();
                    final ITableIterator iterator = expectedDataSet.iterator();
                    while (iterator.next()) {
                        final String tableName = iterator.getTableMetaData().getTableName();
                        final Set<String> columns = new LinkedHashSet<>();
                        for (final Column column : iterator.getTableMetaData().getColumns()) {
                            if (column.getColumnName().equals(orderByColumn)) {
                                names.add(format("?.?", tableName, orderByColumn));
                                columns.add(orderByColumn);
                            }
                        }
                        map.computeIfAbsent(tableName, t -> new LinkedHashSet<>()).addAll(columns);
                    }
                    validateOrderByColumns(orderByColumn, names);
                } else {
                    final String[] data = orderByColumn.split("\\.");
                    if (data.length != 2) {
                        throw new ConfigException("Redundant '.' for order by column name: '?'! Remove redundant symbol!", orderByColumn);
                    }
                    map.computeIfAbsent(data[0], t -> new LinkedHashSet<>()).add(data[1]);
                }
            }
            validateOrderByMap(map, expectedDataSet);
            return map;
        } else {
            return Map.of();
        }
    }

    private void validateOrderByColumns(final String orderByColumn,
                                        final List<String> names) {
        if (names.isEmpty()) {
            throw new ConfigException(
                    "Expected dataset does not contain '?' column that used at the order by clause!",
                    orderByColumn
            );
        } else if (names.size() > 1) {
            throw new ConfigException(
                    "'?' column that used at the order by clause is ambiguous! Specify which exactly column it is necessary to use: ?!",
                    orderByColumn, names
            );
        }
    }

    private void validateOrderByMap(final Map<String, Set<String>> map,
                                    final IDataSet expectedDataSet) throws DataSetException {
        final Set<String> expectedTableNames = Set.of(expectedDataSet.getTableNames());
        for (final String tableName : map.keySet()) {
            if (!expectedTableNames.contains(tableName)) {
                throw new ConfigException("Order by annotation parameter contains not found table: '?'! Remove invalid table!", tableName);
            }
        }
    }
}
