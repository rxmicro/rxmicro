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

package io.rxmicro.examples.data.r2dbc.postgresql.returntypes.delete;

import io.rxmicro.data.sql.model.EntityFieldList;
import io.rxmicro.data.sql.model.EntityFieldMap;
import io.rxmicro.data.sql.operation.Delete;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.Account;

import java.util.concurrent.CompletionStage;

@PostgreSQLRepository
public interface DeleteOneEntityUsingRequiredCompletionStageRepository {

    @Delete
    CompletionStage<Void> delete01(Account account);

    @Delete
    CompletionStage<Integer> delete02(Account account);

    @Delete
    CompletionStage<Boolean> delete03(Account account);

    @Delete("DELETE FROM ${table} WHERE ${by-id-filter} RETURNING *")
    CompletionStage<Account> delete04(Account account);

    @Delete("DELETE FROM ${table} WHERE ${by-id-filter} RETURNING *")
    CompletionStage<EntityFieldMap> delete05(Account account);

    @Delete("DELETE FROM ${table} WHERE ${by-id-filter} RETURNING *")
    CompletionStage<EntityFieldList> delete06(Account account);
}
