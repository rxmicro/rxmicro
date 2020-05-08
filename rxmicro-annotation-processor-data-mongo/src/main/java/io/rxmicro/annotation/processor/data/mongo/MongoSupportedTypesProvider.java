/*
 * Copyright 2019 https://rxmicro.io
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

package io.rxmicro.annotation.processor.data.mongo;

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.model.definition.TypeDefinition;
import io.rxmicro.annotation.processor.common.model.definition.TypeDefinitions;
import io.rxmicro.annotation.processor.common.model.definition.impl.ByNameTypeDefinition;
import io.rxmicro.annotation.processor.common.model.definition.impl.EnumTypeDefinition;
import io.rxmicro.annotation.processor.common.model.definition.impl.TypeDefinitionsImpl;
import io.rxmicro.annotation.processor.data.model.DataSupportedTypesProvider;
import org.bson.types.Binary;
import org.bson.types.Code;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class MongoSupportedTypesProvider extends DataSupportedTypesProvider {

    @Override
    protected TypeDefinitions<TypeDefinition> createPrimitives() {
        // https://docs.mongodb.com/manual/reference/bson-types/
        return new TypeDefinitionsImpl<>(
                new ByNameTypeDefinition(ObjectId.class),
                new EnumTypeDefinition(),
                new ByNameTypeDefinition(Boolean.class),

                new ByNameTypeDefinition(Byte.class),
                new ByNameTypeDefinition(Short.class),
                new ByNameTypeDefinition(Integer.class),
                new ByNameTypeDefinition(Long.class),
                new ByNameTypeDefinition(Float.class),
                new ByNameTypeDefinition(Double.class),
                new ByNameTypeDefinition(BigDecimal.class),

                new ByNameTypeDefinition(Character.class),
                new ByNameTypeDefinition(String.class),
                new ByNameTypeDefinition(Pattern.class),

                new ByNameTypeDefinition(Instant.class),
                new ByNameTypeDefinition(LocalDate.class),
                new ByNameTypeDefinition(LocalDateTime.class),
                new ByNameTypeDefinition(LocalTime.class),

                new ByNameTypeDefinition(UUID.class),
                new ByNameTypeDefinition(Code.class),
                new ByNameTypeDefinition(Binary.class)
                // Add new supported primitive here
        );
    }
}
