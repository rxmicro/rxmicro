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

package io.rxmicro.examples.data.r2dbc.postgresql.primary.keys;

import io.rxmicro.data.sql.operation.Update;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.primary.keys.model.Account;
import io.rxmicro.examples.data.r2dbc.postgresql.primary.keys.model.CompositePrimaryKey;
import io.rxmicro.examples.data.r2dbc.postgresql.primary.keys.model.Order;
import io.rxmicro.examples.data.r2dbc.postgresql.primary.keys.model.Product;

import java.util.concurrent.CompletableFuture;

// tag::content[]
@PostgreSQLRepository
public interface UpdateDataRepository {

    @Update
    CompletableFuture<Void> update(Account account);

    @Update
    CompletableFuture<Void> update(Order order);

    @Update
    CompletableFuture<Void> update(Product product);

    @Update
    CompletableFuture<Void> update(CompositePrimaryKey entity);
}
// end::content[]
