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

package io.rxmicro.examples.data.r2dbc.postgresql.returntypes.update;

import io.rxmicro.data.sql.VariableValues;
import io.rxmicro.data.sql.model.EntityFieldList;
import io.rxmicro.data.sql.model.EntityFieldMap;
import io.rxmicro.data.sql.operation.Update;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.Account;

import java.util.concurrent.CompletableFuture;

@PostgreSQLRepository
@VariableValues({
        "${table}", "account"
})
public interface UpdateOneEntityFieldsUsingRequiredCompletableFutureRepository {

    @Update("UPDATE ${table} SET first_name=?, last_name=? WHERE id=?")
    CompletableFuture<Void> update01(String firstName, String lastName, Long id);

    @Update("UPDATE ${table} SET first_name=?, last_name=? WHERE id=?")
    CompletableFuture<Long> update02(String firstName, String lastName, Long id);

    @Update("UPDATE ${table} SET first_name=?, last_name=? WHERE id=?")
    CompletableFuture<Boolean> update03(String firstName, String lastName, Long id);

    @Update("UPDATE ${table} SET first_name=?, last_name=? WHERE id=? RETURNING *")
    CompletableFuture<Account> update04(String firstName, String lastName, Long id);

    @Update("UPDATE ${table} SET first_name=?, last_name=? WHERE id=? RETURNING first_name, last_name")
    CompletableFuture<EntityFieldMap> update05(String firstName, String lastName, Long id);

    @Update("UPDATE ${table} SET first_name=?, last_name=? WHERE id=? RETURNING first_name, last_name")
    CompletableFuture<EntityFieldList> update06(String firstName, String lastName, Long id);
}
