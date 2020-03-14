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

package io.rxmicro.test.mockito.mongo.internal;

import org.bson.Document;

import java.util.Optional;
import java.util.OptionalInt;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public abstract class AbstractCountDocumentsOperationMock {

    private final boolean anyQuery;

    private final Document query;

    private final Document hint;

    private final int limit;

    private final int skip;

    protected AbstractCountDocumentsOperationMock(final boolean anyQuery,
                                                  final Document query,
                                                  final Document hint,
                                                  final int limit,
                                                  final int skip) {
        this.anyQuery = anyQuery;
        this.query = query;
        this.hint = hint;
        this.limit = limit;
        this.skip = skip;
    }

    protected boolean isAnyQuery() {
        return anyQuery;
    }

    protected Optional<Document> getQuery() {
        return Optional.ofNullable(query);
    }

    protected Optional<Document> getHint() {
        return Optional.ofNullable(hint);
    }

    protected OptionalInt getLimit() {
        return limit == -1 ? OptionalInt.empty() : OptionalInt.of(limit);
    }

    protected OptionalInt getSkip() {
        return skip == -1 ? OptionalInt.empty() : OptionalInt.of(skip);
    }

    protected boolean hasOptions() {
        return hint != null || limit != -1 || skip != -1;
    }
}
