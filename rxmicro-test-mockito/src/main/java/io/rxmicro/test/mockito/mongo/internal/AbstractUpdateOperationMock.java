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

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public abstract class AbstractUpdateOperationMock {

    private final boolean anyUpdate;

    private final Document update;

    private final boolean anyFilter;

    private final Document filter;

    private final boolean upsert;

    public AbstractUpdateOperationMock(final boolean anyUpdate,
                                       final Document update,
                                       final boolean anyFilter,
                                       final Document filter,
                                       final boolean upsert) {
        this.anyUpdate = anyUpdate;
        this.update = update;
        this.anyFilter = anyFilter;
        this.filter = filter;
        this.upsert = upsert;
    }

    protected boolean isAnyUpdate() {
        return anyUpdate;
    }

    protected Document getUpdate() {
        return require(update, "'update' is required!");
    }

    protected boolean isAnyFilter() {
        return anyFilter;
    }

    protected Document getFilter() {
        return require(filter, "'filter' is required!");
    }

    protected boolean isUpsert() {
        return upsert;
    }
}
