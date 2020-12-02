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

package io.rxmicro.annotation.processor.data.sql.component.impl;

import io.rxmicro.annotation.processor.common.component.TokenParser;
import io.rxmicro.annotation.processor.common.component.impl.TokenParserImpl;
import io.rxmicro.annotation.processor.common.model.TokenParserRule;
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

import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * @author nedis
 *
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class TokenParserImplTest {

    private final TokenParser tokenParser = new TokenParserImpl();

    private final TokenParserRule rule = new SQLTokenParserRuleProvider().get();

    @ParameterizedTest(name = "[{index}] -> {0}")
    @ArgumentsSource(SelectSQLQueriesProvider.class)
    @Order(8)
    void Should_split_line_using_all_supported_delimiters_correctly(final String sql,
                                                                    final List<String> expectedTokens) {
        final List<String> results = tokenParser.parse(sql, rule, false).getTokens();
        assertEquals(expectedTokens, results);
    }

    /**
     * @author nedis
     *
     */
    static final class SelectSQLQueriesProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
            return Stream.of(
                    arguments(
                            "   SELECT DISTINCT ON (location) location, time, report    FROM table1   ORDER BY location, time DESC",
                            of("SELECT DISTINCT ON ( location ) location , time , report FROM table1 ORDER BY location , time DESC".split(" "))
                    ),
                    arguments(
                            "   SELECT * FROM ${table} WHERE active=? AND state!=?     ORDER BY login ?,  name ? LIMIT ? OFFSET ?",
                            of("SELECT * FROM ${table} WHERE active = ? AND state != ? ORDER BY login ? , name ? LIMIT ? OFFSET ?".split(" "))
                    ),
                    arguments(
                            "   SELECT * FROM ${table} WHERE active=? AND text LIKE '_Hello?World%'",
                            of("SELECT * FROM ${table} WHERE active = ? AND text LIKE '_Hello?World%'".split(" "))
                    ),
                    arguments(
                            "   SELECT id, name, value as \"order\"   FROM ${table} WHERE active=?   AND text LIKE '_Hello?World%' AND content='${table}'",
                            of("SELECT id , name , value as \"order\" FROM ${table} WHERE active = ? AND text LIKE '_Hello?World%' AND content = '${table}'".split(" "))
                    ),
                    arguments(
                            "   SELECT * FROM test WHERE value=2+4*5       AND (name!=? OR name<>?)       ORDER BY ? ?, ? ?",
                            of("SELECT * FROM test WHERE value = 2 + 4 * 5 AND ( name != ? OR name <> ? ) ORDER BY ? ? , ? ?".split(" "))
                    ),
                    arguments(
                            "   SELECT field1, (SELECT value FROM dictionary) AS field2    FROM test WHERE id IN(SELECT * FROM data_values)",
                            of("SELECT field1 , ( SELECT value FROM dictionary ) AS field2 FROM test WHERE id IN ( SELECT * FROM data_values )".split(" "))
                    ),
                    arguments(
                            "   SELECT      DISTINCT role::text FROM account ORDER BY id",
                            of("SELECT DISTINCT role::text FROM account ORDER BY id".split(" "))
                    )
            );
        }
    }
}