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

import java.util.List;
import java.util.Optional;

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public abstract class AbstractAggregateOperationMock {

    private final List<Document> pipeline;

    private final Document hint;

    private final boolean allowDiskUse;

    public AbstractAggregateOperationMock(final List<Document> pipeline,
                                          final Document hint,
                                          final boolean allowDiskUse) {
        this.pipeline = require(pipeline);
        this.hint = hint;
        this.allowDiskUse = allowDiskUse;
    }

    protected List<Document> getPipeline() {
        return pipeline;
    }

    protected Optional<Document> getHint() {
        return Optional.ofNullable(hint);
    }

    protected boolean isAllowDiskUse() {
        return allowDiskUse;
    }
}
