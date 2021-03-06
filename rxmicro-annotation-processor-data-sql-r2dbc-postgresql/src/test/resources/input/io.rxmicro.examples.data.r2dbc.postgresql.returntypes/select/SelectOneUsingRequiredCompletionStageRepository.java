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

import io.rxmicro.data.sql.VariableValues;
import io.rxmicro.data.sql.model.EntityFieldList;
import io.rxmicro.data.sql.model.EntityFieldMap;
import io.rxmicro.data.sql.operation.Select;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.Account;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.Role;

import java.math.BigDecimal;
import java.util.concurrent.CompletionStage;

@PostgreSQLRepository
@VariableValues({
        "${table}", "account"
})
public interface SelectOneUsingRequiredCompletionStageRepository {

    @Select("SELECT first_name, last_name FROM ${table} WHERE email='richard.hendricks@piedpiper.com'")
    CompletionStage<Account> find01();

    @Select("SELECT first_name, last_name FROM ${table} WHERE email='richard.hendricks@piedpiper.com'")
    CompletionStage<EntityFieldMap> find02();

    @Select("SELECT first_name, last_name FROM ${table} WHERE email='richard.hendricks@piedpiper.com'")
    CompletionStage<EntityFieldList> find03();

    @Select("SELECT email FROM ${table} WHERE email='richard.hendricks@piedpiper.com'")
    CompletionStage<String> find04();

    @Select("SELECT role FROM ${table} WHERE email='richard.hendricks@piedpiper.com'")
    CompletionStage<Role> find05();

    @Select("SELECT balance FROM ${table} WHERE email='richard.hendricks@piedpiper.com'")
    CompletionStage<BigDecimal> find06();
}
