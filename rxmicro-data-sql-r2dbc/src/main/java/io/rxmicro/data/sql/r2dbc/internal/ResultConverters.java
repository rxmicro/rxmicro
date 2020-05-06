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

package io.rxmicro.data.sql.r2dbc.internal;

import io.r2dbc.spi.ColumnMetadata;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import io.rxmicro.data.sql.local.impl.EntityFieldListImpl;
import io.rxmicro.data.sql.local.impl.EntityFieldMapImpl;
import io.rxmicro.data.sql.model.EntityFieldList;
import io.rxmicro.data.sql.model.EntityFieldMap;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class ResultConverters {

    public static final BiFunction<Row, RowMetadata, EntityFieldMap> TO_ENTITY_FIELD_MAP_BI_FUNCTION =
            (row, metadata) -> {
                final Map<String, Object> result = new LinkedHashMap<>();
                int index = 0;
                for (final ColumnMetadata columnMetadata : metadata.getColumnMetadatas()) {
                    final Class<?> javaType = columnMetadata.getJavaType();
                    result.put(columnMetadata.getName(), javaType == null ? row.get(index++) : row.get(index++, javaType));
                }
                return new EntityFieldMapImpl(result);
            };

    public static final BiFunction<Row, RowMetadata, EntityFieldList> TO_ENTITY_FIELD_LIST_BI_FUNCTION =
            (row, metadata) -> {
                final List<Object> result = new ArrayList<>();
                int index = 0;
                for (final ColumnMetadata columnMetadata : metadata.getColumnMetadatas()) {
                    final Class<?> javaType = columnMetadata.getJavaType();
                    result.add(javaType == null ? row.get(index++) : row.get(index++, javaType));
                }
                return new EntityFieldListImpl(result);
            };

    private ResultConverters() {
    }
}
