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

package io.rxmicro.examples.data.r2dbc.postgresql.returntypes.insert;

import io.rxmicro.data.sql.model.EntityFieldList;
import io.rxmicro.data.sql.model.EntityFieldMap;
import io.rxmicro.data.sql.operation.Insert;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.Account;

import java.util.concurrent.CompletionStage;

@PostgreSQLRepository
public interface InsertOneEntityUsingRequiredCompletionStageRepository {

    @Insert
    CompletionStage<Void> insert01(Account account);

    @Insert
    CompletionStage<Long> insert02(Account account);

    @Insert
    CompletionStage<Boolean> insert03(Account account);

    @Insert
    CompletionStage<Account> insert04(Account account);

    @Insert
    CompletionStage<EntityFieldMap> insert05(Account account);

    @Insert
    CompletionStage<EntityFieldList> insert06(Account account);

    @Insert("INSERT INTO ${table}(${inserted-columns}) VALUES(${values}) RETURNING *")
    CompletionStage<Account> insert07(Account account);

    @Insert("INSERT INTO ${table}(${inserted-columns}) VALUES(${values}) RETURNING *")
    CompletionStage<EntityFieldMap> insert08(Account account);

    @Insert("INSERT INTO ${table}(${inserted-columns}) VALUES(${values}) RETURNING *")
    CompletionStage<EntityFieldList> insert09(Account account);
}
