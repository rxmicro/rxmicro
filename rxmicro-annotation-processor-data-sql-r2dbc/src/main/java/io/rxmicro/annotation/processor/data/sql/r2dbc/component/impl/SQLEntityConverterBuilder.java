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

package io.rxmicro.annotation.processor.data.sql.r2dbc.component.impl;

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.component.impl.AbstractProcessorComponent;
import io.rxmicro.annotation.processor.common.model.ClassStructure;
import io.rxmicro.annotation.processor.data.component.EntityConverterBuilder;
import io.rxmicro.annotation.processor.data.model.DataGenerationContext;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataModelField;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataObjectModelClass;
import io.rxmicro.annotation.processor.data.sql.r2dbc.model.EntityFromDBConverterClassStructure;
import io.rxmicro.annotation.processor.data.sql.r2dbc.model.EntityToDBConverterClassStructure;

import java.util.HashSet;
import java.util.Set;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@Singleton
public class SQLEntityConverterBuilder<DMF extends SQLDataModelField, DMC extends SQLDataObjectModelClass<DMF>>
        extends AbstractProcessorComponent
        implements EntityConverterBuilder<DMF, DMC> {

    @Override
    public Set<? extends ClassStructure> build(final DataGenerationContext<DMF, DMC> dataGenerationContext) {
        final Set<ClassStructure> result = new HashSet<>();
        dataGenerationContext.getEntityParamMap().values()
                .forEach(m -> result.add(new EntityToDBConverterClassStructure<>(m)));
        dataGenerationContext.getEntityReturnMap().values()
                .forEach(m -> result.add(new EntityFromDBConverterClassStructure<>(m)));
        return result;
    }
}
