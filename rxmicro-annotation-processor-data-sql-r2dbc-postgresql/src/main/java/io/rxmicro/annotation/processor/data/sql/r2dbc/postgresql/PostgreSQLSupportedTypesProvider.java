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

package io.rxmicro.annotation.processor.data.sql.r2dbc.postgresql;

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.model.definition.TypeDefinition;
import io.rxmicro.annotation.processor.common.model.definition.TypeDefinitions;
import io.rxmicro.annotation.processor.common.model.definition.impl.ByNameTypeDefinition;
import io.rxmicro.annotation.processor.common.model.definition.impl.EnumTypeDefinition;
import io.rxmicro.annotation.processor.common.model.definition.impl.TypeDefinitionsImpl;
import io.rxmicro.annotation.processor.data.sql.SQLSupportedTypesProvider;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@Singleton
public final class PostgreSQLSupportedTypesProvider extends SQLSupportedTypesProvider {

    @Override
    protected TypeDefinitions<TypeDefinition> createPrimitives() {
        // https://github.com/r2dbc/r2dbc-postgresql#data-type-mapping
        return new TypeDefinitionsImpl<>(
                new EnumTypeDefinition(),
                new ByNameTypeDefinition(Boolean.class),

                new ByNameTypeDefinition(Byte.class),
                new ByNameTypeDefinition(Short.class),
                new ByNameTypeDefinition(Integer.class),
                new ByNameTypeDefinition(Long.class),
                new ByNameTypeDefinition(BigInteger.class),

                new ByNameTypeDefinition(Float.class),
                new ByNameTypeDefinition(Double.class),
                new ByNameTypeDefinition(BigDecimal.class),

                new ByNameTypeDefinition(Character.class),
                new ByNameTypeDefinition(String.class),

                new ByNameTypeDefinition(Instant.class),
                new ByNameTypeDefinition(LocalTime.class),
                new ByNameTypeDefinition(LocalDate.class),
                new ByNameTypeDefinition(LocalDateTime.class),
                new ByNameTypeDefinition(OffsetDateTime.class),
                new ByNameTypeDefinition(ZonedDateTime.class),

                new ByNameTypeDefinition(InetAddress.class),
                new ByNameTypeDefinition(UUID.class)
                // Add new supported primitive here
        );
    }
}
