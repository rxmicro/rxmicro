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

package io.rxmicro.annotation.processor.data.sql.util;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.List;
import java.util.stream.Stream;

import static io.rxmicro.annotation.processor.data.sql.util.SQLs.joinTokensToSQL;
import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * @author nedis
 * @link https://rxmicro.io
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class SQLs_UnitTest {

    @ParameterizedTest(name = "[{index}] -> {0}")
    @ArgumentsSource(SelectSQLQueriesProvider.class)
    @Order(8)
    void Should_join_SQL_correctly(final List<String> sqlTokens,
                                   final String expectedSQL) {
        final String joinedSQL = joinTokensToSQL(sqlTokens);
        assertEquals(expectedSQL, joinedSQL);
    }

    /**
     * @author nedis
     * @link https://rxmicro.io
     */
    static final class SelectSQLQueriesProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
            return Stream.of(
                    arguments(
                            of("SELECT id , login , password FROM account".split(" ")),
                            "SELECT id, login, password FROM account"
                    ),
                    arguments(
                            of("SELECT id , upper ( login ) as login , ( SELECT sum ( * ) FROM account_balance WHERE account_balance . id = account . id ) AS balance , now ( ) AS created FROM account".split(" ")),
                            "SELECT id, upper(login) as login, (SELECT sum(*) FROM account_balance WHERE account_balance.id = account.id) AS balance, now() AS created FROM account"
                    )
            );
        }
    }
}