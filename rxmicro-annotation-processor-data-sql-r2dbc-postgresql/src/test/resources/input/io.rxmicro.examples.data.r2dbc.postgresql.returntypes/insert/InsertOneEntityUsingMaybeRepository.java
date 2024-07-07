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

import io.reactivex.rxjava3.core.Maybe;
import io.rxmicro.data.sql.model.EntityFieldList;
import io.rxmicro.data.sql.model.EntityFieldMap;
import io.rxmicro.data.sql.operation.Insert;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.Account;

@PostgreSQLRepository
public interface InsertOneEntityUsingMaybeRepository {

    @Insert("INSERT INTO ${table}(${inserted-columns}) VALUES(${values}) ON CONFLICT DO NOTHING RETURNING ${id-columns}")
    Maybe<Account> insert01(Account account);

    @Insert("INSERT INTO ${table}(${inserted-columns}) VALUES(${values}) ON CONFLICT DO NOTHING RETURNING ${id-columns}")
    Maybe<EntityFieldMap> insert02(Account account);

    @Insert("INSERT INTO ${table}(${inserted-columns}) VALUES(${values}) ON CONFLICT DO NOTHING RETURNING ${id-columns}")
    Maybe<EntityFieldList> insert03(Account account);

    @Insert("INSERT INTO ${table}(${inserted-columns}) VALUES(${values}) ON CONFLICT DO NOTHING RETURNING *")
    Maybe<Account> insert04(Account account);

    @Insert("INSERT INTO ${table}(${inserted-columns}) VALUES(${values}) ON CONFLICT DO NOTHING RETURNING *")
    Maybe<EntityFieldMap> insert05(Account account);

    @Insert("INSERT INTO ${table}(${inserted-columns}) VALUES(${values}) ON CONFLICT DO NOTHING RETURNING *")
    Maybe<EntityFieldList> insert06(Account account);
}
