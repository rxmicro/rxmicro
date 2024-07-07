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

package io.rxmicro.test.dbunit.internal.component;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static io.rxmicro.resource.InputStreamResources.getInputStreamResource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * @author nedis
 * @since 0.7
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class SQLScriptReaderTest {

    private final SQLScriptReader sqlScriptReader = new SQLScriptReader();

    static Stream<Arguments> argumentProvider() {
        return Stream.of(
                arguments(
                        "classpath:script01.sql",
                        List.of(
                                "DELETE FROM public.order"
                        )
                ),
                arguments(
                        "classpath:script02.sql",
                        List.of(
                                "DELETE FROM public.\"order\"",
                                "DELETE FROM public.account"
                        )
                ),
                arguments(
                        "classpath:script03.sql",
                        List.of(
                                "DELETE FROM public.\"order\"",
                                "DELETE FROM public.account"
                        )
                ),
                arguments(
                        "classpath:script04.sql",
                        List.of(
                                "DELETE FROM public.\"order\"",
                                "DELETE FROM public.account"
                        )
                ),
                arguments(
                        "classpath:script05.sql",
                        List.of(
                                "CREATE TYPE public.role AS ENUM ( 'CEO', 'Lead_Engineer', 'Systems_Architect' )",
                                "ALTER TYPE public.role OWNER TO rxmicro",
                                "CREATE TABLE public.account ( id int8 NOT NULL, email varchar(50) NOT NULL, " +
                                        "CONSTRAINT account_email_uniq UNIQUE (email), CONSTRAINT account_id_pk PRIMARY KEY (id) )",
                                "ALTER TABLE public.account OWNER TO rxmicro",
                                "GRANT ALL ON TABLE public.account TO rxmicro",
                                "CREATE SEQUENCE public.account_seq INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 NO CYCLE",
                                "ALTER SEQUENCE public.account_seq OWNER TO rxmicro",
                                "GRANT ALL ON SEQUENCE public.account_seq TO rxmicro"
                        )
                ),
                arguments(
                        "classpath:script06.sql",
                        List.of(
                                "INSERT INTO public.account (id, email) VALUES (nextval('account_seq'), 'richard.hendricks@piedpiper.com'), " +
                                        "(nextval('account_seq'), 'bertram.gilfoyle@piedpiper.com'), " +
                                        "(nextval('account_seq'), 'dinesh.chugtai@piedpiper.com')",
                                "INSERT INTO public.account (id, email) VALUES (nextval('account_seq'), 'richard;hendricks;@piedpiper.com')",
                                "DELETE FROM public.order"
                        )
                )
        );
    }

    @ParameterizedTest
    @MethodSource("argumentProvider")
    void Should_read_SQL_script(final String resource,
                                final List<String> expected) {
        final List<String> actual = sqlScriptReader.readJdbcStatements(getInputStreamResource(resource).orElseThrow());

        assertEquals(expected, actual);
    }
}
