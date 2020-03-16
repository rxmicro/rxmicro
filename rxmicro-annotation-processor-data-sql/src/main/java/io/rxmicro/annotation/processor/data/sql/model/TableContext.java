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

package io.rxmicro.annotation.processor.data.sql.model;

import java.util.Optional;

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class TableContext {

    private final String schema;

    private final String table;

    public TableContext(final String schema,
                        final String table) {
        this.schema = require(schema);
        this.table = require(table);
    }

    public TableContext(final String table) {
        this.schema = null;
        this.table = require(table);
    }

    public Optional<String> getSchema() {
        return Optional.ofNullable(schema);
    }

    public String getTableSimpleName() {
        return table;
    }
}
