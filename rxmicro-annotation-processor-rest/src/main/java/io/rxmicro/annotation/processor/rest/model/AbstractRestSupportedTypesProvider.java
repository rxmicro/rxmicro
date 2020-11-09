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

package io.rxmicro.annotation.processor.rest.model;

import io.rxmicro.annotation.processor.common.model.definition.SupportedTypesProvider;
import io.rxmicro.annotation.processor.common.model.definition.TypeDefinition;
import io.rxmicro.annotation.processor.common.model.definition.TypeDefinitions;
import io.rxmicro.annotation.processor.common.model.definition.impl.ByNameTypeDefinition;
import io.rxmicro.annotation.processor.common.model.definition.impl.ContainerTypeDefinition;
import io.rxmicro.annotation.processor.common.model.definition.impl.EnumTypeDefinition;
import io.rxmicro.annotation.processor.common.model.definition.impl.TypeDefinitionsImpl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.List;
import java.util.Set;

/**
 * @author nedis
 * @since 0.1
 */
public abstract class AbstractRestSupportedTypesProvider extends SupportedTypesProvider {

    @Override
    protected final TypeDefinitions<TypeDefinition> createPrimitives() {
        // Add new supported primitive here:
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
                new ByNameTypeDefinition(Instant.class)
        );
    }

    @Override
    protected TypeDefinitions<ContainerTypeDefinition> createCollectionContainers() {
        return new TypeDefinitionsImpl<>(
                new ContainerTypeDefinition(List.class),
                new ContainerTypeDefinition(Set.class)
        );
    }
}
