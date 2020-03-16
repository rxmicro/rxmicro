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

package io.rxmicro.test.mockito.mongo.internal;

import org.bson.Document;

import java.util.Optional;

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public abstract class AbstractDistinctOperationMock<T> {

    private final boolean anyQuery;

    private final Document query;

    private final Class<T> resultClass;

    private final String field;

    protected AbstractDistinctOperationMock(final Class<T> resultClass,
                                            final String field,
                                            final Document query,
                                            final boolean anyQuery) {
        this.anyQuery = anyQuery;
        this.query = query;
        this.resultClass = require(resultClass, "'resultClass' is required!");
        this.field = require(field, "Distinct 'field' is required");
    }

    protected boolean isAnyQuery() {
        return anyQuery;
    }

    protected Optional<Document> getQuery() {
        return Optional.ofNullable(query);
    }

    protected Class<T> getResultClass() {
        return resultClass;
    }

    protected String getField() {
        return field;
    }
}
