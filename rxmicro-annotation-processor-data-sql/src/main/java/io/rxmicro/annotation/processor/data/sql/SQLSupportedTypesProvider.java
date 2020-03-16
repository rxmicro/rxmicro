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

package io.rxmicro.annotation.processor.data.sql;

import io.rxmicro.annotation.processor.common.model.definition.TypeDefinition;
import io.rxmicro.annotation.processor.common.model.definition.TypeDefinitions;
import io.rxmicro.annotation.processor.common.model.definition.impl.ByNameTypeDefinition;
import io.rxmicro.annotation.processor.common.model.definition.impl.TypeDefinitionsImpl;
import io.rxmicro.annotation.processor.data.model.DataSupportedTypesProvider;
import io.rxmicro.data.sql.model.EntityFieldList;
import io.rxmicro.data.sql.model.EntityFieldMap;

import java.util.List;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public abstract class SQLSupportedTypesProvider extends DataSupportedTypesProvider {

    @Override
    protected TypeDefinitions<TypeDefinition> createStandardMethodParameterTypes() {
        return new TypeDefinitionsImpl<>(List.of(
                super.createStandardMethodParameterTypes(),
                new TypeDefinitionsImpl<>(
                        new ByNameTypeDefinition(io.rxmicro.data.sql.model.reactor.Transaction.class),
                        new ByNameTypeDefinition(io.rxmicro.data.sql.model.rxjava3.Transaction.class),
                        new ByNameTypeDefinition(io.rxmicro.data.sql.model.completablefuture.Transaction.class)
                )
        ));
    }

    @Override
    protected TypeDefinitions<TypeDefinition> createResultReturnPrimitiveTypes() {
        return new TypeDefinitionsImpl<>(List.of(
                super.createResultReturnPrimitiveTypes(),
                new TypeDefinitionsImpl<>(
                        new ByNameTypeDefinition(io.rxmicro.data.sql.model.reactor.Transaction.class),
                        new ByNameTypeDefinition(io.rxmicro.data.sql.model.rxjava3.Transaction.class),
                        new ByNameTypeDefinition(io.rxmicro.data.sql.model.completablefuture.Transaction.class),
                        new ByNameTypeDefinition(EntityFieldList.class),
                        new ByNameTypeDefinition(EntityFieldMap.class)
                )
        ));
    }
}
