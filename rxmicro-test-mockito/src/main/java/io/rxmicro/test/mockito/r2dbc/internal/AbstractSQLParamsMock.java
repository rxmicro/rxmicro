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

package io.rxmicro.test.mockito.r2dbc.internal;

import java.util.List;
import java.util.Optional;

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @since 0.1
 */
public abstract class AbstractSQLParamsMock {

    private final boolean transactional;

    private final String sql;

    private final List<Object> bindParams;

    public AbstractSQLParamsMock(final boolean transactional,
                                 final String sql,
                                 final List<Object> bindParams) {
        this.transactional = transactional;
        this.sql = sql;
        this.bindParams = require(bindParams);
    }

    protected boolean isTransactional() {
        return transactional;
    }

    protected Optional<String> getSql() {
        return Optional.ofNullable(sql);
    }

    protected List<Object> getBindParams() {
        return bindParams;
    }
}
