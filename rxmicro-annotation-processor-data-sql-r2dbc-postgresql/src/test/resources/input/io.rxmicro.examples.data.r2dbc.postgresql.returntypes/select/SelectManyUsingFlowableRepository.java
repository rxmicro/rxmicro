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

package io.rxmicro.examples.data.r2dbc.postgresql.returntypes.select;

import io.reactivex.rxjava3.core.Flowable;
import io.rxmicro.data.sql.VariableValues;
import io.rxmicro.data.sql.model.EntityFieldList;
import io.rxmicro.data.sql.model.EntityFieldMap;
import io.rxmicro.data.sql.operation.Select;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.Account;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.Role;

import java.math.BigDecimal;

@PostgreSQLRepository
@VariableValues({
        "${table}", "account"
})
public interface SelectManyUsingFlowableRepository {

    @Select("SELECT first_name, last_name FROM ${table} ORDER BY id")
    Flowable<Account> findAll01();

    @Select("SELECT first_name, last_name FROM ${table} ORDER BY id")
    Flowable<EntityFieldMap> findAll02();

    @Select("SELECT first_name, last_name FROM ${table} ORDER BY id")
    Flowable<EntityFieldList> findAll03();

    @Select("SELECT email FROM ${table} ORDER BY id")
    Flowable<String> findAll04();

    @Select("SELECT DISTINCT role::text FROM ${table} ORDER BY role")
    Flowable<Role> findAll05();

    @Select("SELECT DISTINCT balance FROM ${table}")
    Flowable<BigDecimal> findAll06();
}
