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

package io.rxmicro.examples.data.r2dbc.postgresql.request.id.supplier;

import io.rxmicro.data.sql.model.IsolationLevel;
import io.rxmicro.data.sql.model.completablefuture.Transaction;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLRepository;
import io.rxmicro.logger.RequestIdSupplier;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@SuppressWarnings("unused")
@PostgreSQLRepository
public interface BeginCompletableFutureTransactionRepository {

    CompletionStage<Transaction> beginTransaction1(RequestIdSupplier requestIdSupplier);

    CompletionStage<Transaction> beginTransaction1(IsolationLevel isolationLevel, RequestIdSupplier requestIdSupplier);

    CompletionStage<Transaction> beginTransaction1(RequestIdSupplier requestIdSupplier, IsolationLevel isolationLevel);

    CompletableFuture<Transaction> beginTransaction2(RequestIdSupplier requestIdSupplier);

    CompletableFuture<Transaction> beginTransaction2(IsolationLevel isolationLevel, RequestIdSupplier requestIdSupplier);

    CompletableFuture<Transaction> beginTransaction2(RequestIdSupplier requestIdSupplier, IsolationLevel isolationLevel);
}
// end::content[]
