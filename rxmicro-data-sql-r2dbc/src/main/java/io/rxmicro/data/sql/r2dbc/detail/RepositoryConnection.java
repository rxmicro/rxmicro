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

package io.rxmicro.data.sql.r2dbc.detail;

import io.r2dbc.spi.Connection;
import io.r2dbc.spi.IsolationLevel;
import io.r2dbc.spi.ValidationDepth;
import io.rxmicro.logger.RequestIdSupplier;
import reactor.core.publisher.Mono;

/**
 * @author nedis
 * @since 0.7
 */
public interface RepositoryConnection extends Connection, RequestIdSupplier {

    @Override
    Mono<Void> beginTransaction();

    @Override
    Mono<Void> close();

    @Override
    Mono<Void> commitTransaction();

    @Override
    Mono<Void> createSavepoint(String name);

    @Override
    Mono<Void> releaseSavepoint(String name);

    @Override
    Mono<Void> rollbackTransaction();

    @Override
    Mono<Void> rollbackTransactionToSavepoint(String name);

    @Override
    Mono<Void> setAutoCommit(boolean autoCommit);

    @Override
    Mono<Void> setTransactionIsolationLevel(IsolationLevel isolationLevel);

    @Override
    Mono<Boolean> validate(ValidationDepth depth);

    String getConnectionId();

    String getConnectionClassName();
}
