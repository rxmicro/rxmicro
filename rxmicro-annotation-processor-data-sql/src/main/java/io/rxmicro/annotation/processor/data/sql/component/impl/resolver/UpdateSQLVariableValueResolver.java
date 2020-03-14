/*
 * Copyright (c) 2020. http://rxmicro.io
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
import io.rxmicro.annotation.processor.data.sql.model.inject.SupportedUpdateVariables;
import io.rxmicro.data.sql.operation.Update;

import java.util.Set;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
@Singleton
public class UpdateSQLVariableValueResolver<DMF extends SQLDataModelField, DMC extends SQLDataObjectModelClass<DMF>>
        extends AbstractSQLVariableValueResolver<Update, DMF, DMC> {

    @Inject
    @SupportedUpdateVariables
    private Set<String> supportedVariables;

    @Override
    protected Set<String> getSupportedVariables() {
        return supportedVariables;
    }

    @Override
    protected Class<?> getEntityClass(final ParsedSQL<Update> parsedSQL) {
        return parsedSQL.getAnnotation().entityClass();
    }
}
