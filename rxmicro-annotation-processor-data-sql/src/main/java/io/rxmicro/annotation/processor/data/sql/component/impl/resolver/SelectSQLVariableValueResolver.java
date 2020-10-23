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

package io.rxmicro.annotation.processor.data.sql.component.impl.resolver;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.data.sql.model.ParsedSQL;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataModelField;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataObjectModelClass;
import io.rxmicro.annotation.processor.data.sql.model.inject.SupportedSelectResultsVariables;
import io.rxmicro.data.sql.operation.Select;

import java.util.Set;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public class SelectSQLVariableValueResolver<DMF extends SQLDataModelField, DMC extends SQLDataObjectModelClass<DMF>>
        extends AbstractSQLVariableValueResolver<Select, DMF, DMC> {

    @Inject
    @SupportedSelectResultsVariables
    private Set<String> supportedResultsVariables;

    @Override
    protected Set<String> getSupportedParamsVariables() {
        return Set.of();
    }

    @Override
    protected Set<String> getSupportedResultsVariables() {
        return supportedResultsVariables;
    }

    @Override
    protected boolean isSupportEntityParam() {
        return false;
    }

    @Override
    protected Class<?> getEntityClass(final ParsedSQL<Select> parsedSQL) {
        return parsedSQL.getAnnotation().entityClass();
    }
}
