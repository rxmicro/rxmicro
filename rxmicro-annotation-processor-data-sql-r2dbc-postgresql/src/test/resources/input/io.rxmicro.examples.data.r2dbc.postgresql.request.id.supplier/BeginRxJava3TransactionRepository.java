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

import io.reactivex.rxjava3.core.Single;
import io.rxmicro.data.sql.model.IsolationLevel;
import io.rxmicro.data.sql.model.rxjava3.Transaction;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLRepository;
import io.rxmicro.logger.RequestIdSupplier;

@PostgreSQLRepository
public interface BeginRxJava3TransactionRepository {

    Single<Transaction> beginTransaction(RequestIdSupplier requestIdSupplier);

    Single<Transaction> beginTransaction(IsolationLevel isolationLevel, RequestIdSupplier requestIdSupplier);

    Single<Transaction> beginTransaction(RequestIdSupplier requestIdSupplier, IsolationLevel isolationLevel);
}
// end::content[]
