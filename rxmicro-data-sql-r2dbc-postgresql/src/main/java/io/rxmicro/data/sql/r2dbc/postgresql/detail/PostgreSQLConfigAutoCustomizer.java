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

package io.rxmicro.data.sql.r2dbc.postgresql.detail;

import io.r2dbc.postgresql.extension.CodecRegistrar;

import java.util.Map;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.GeneratedClassRules.GENERATED_CLASS_NAME_PREFIX;
import static io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLConfigCustomizer.registerPostgreSQLCodecs;

/**
 * @author nedis
 * @since 0.7
 */
public class PostgreSQLConfigAutoCustomizer {

    public static final String POSTGRES_SQL_CONFIG_AUTO_CUSTOMIZER_CLASS_NAME =
            format("??", GENERATED_CLASS_NAME_PREFIX, PostgreSQLConfigAutoCustomizer.class.getSimpleName());

    protected static void registerAllPostgreSQLCodecs(final Map<String, Class<? extends Enum<?>>> enumMapping) {
        registerPostgreSQLCodecs(
                enumMapping.entrySet().stream()
                        .map(e -> io.r2dbc.postgresql.codec.EnumCodec.builder()
                                .withEnum(e.getKey(), e.getValue())
                                .build())
                        .toArray(CodecRegistrar[]::new)
        );
    }

    protected PostgreSQLConfigAutoCustomizer() {
        // This is basic class designed for extension only.
    }
}
