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

package io.rxmicro.annotation.processor.data.sql.component.impl.builder;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.data.sql.component.SQLBuilder;
import io.rxmicro.annotation.processor.data.sql.component.impl.builder.select.CustomSelectSQLBuilder;
import io.rxmicro.annotation.processor.data.sql.component.impl.builder.select.PredefinedSelectSQLBuilder;
import io.rxmicro.annotation.processor.data.sql.model.ParsedSQL;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataModelField;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataObjectModelClass;
import io.rxmicro.annotation.processor.data.sql.model.SQLMethodDescriptor;
import io.rxmicro.annotation.processor.data.sql.model.SQLStatement;
import io.rxmicro.data.sql.operation.Select;

import javax.lang.model.element.ExecutableElement;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public class SelectSQLBuilder<DMF extends SQLDataModelField, DMC extends SQLDataObjectModelClass<DMF>>
        implements SQLBuilder<Select, DMF, DMC> {

    @Inject
    private CustomSelectSQLBuilder<DMF, DMC> customSelectSQLBuilder;

    @Inject
    private PredefinedSelectSQLBuilder<DMF, DMC> predefinedSelectSQLBuilder;

    @Override
    public SQLStatement build(final ClassHeader.Builder classHeaderBuilder,
                              final ParsedSQL<Select> parsedSQL,
                              final ExecutableElement method,
                              final SQLMethodDescriptor<DMF, DMC> sqlMethodDescriptor) {
        if (parsedSQL.getSqlTokens().isEmpty()) {
            return customSelectSQLBuilder.buildCustomSQL(
                    parsedSQL,
                    method,
                    sqlMethodDescriptor
            );
        } else {
            return predefinedSelectSQLBuilder.buildPredefinedSQL(
                    classHeaderBuilder,
                    parsedSQL,
                    method,
                    sqlMethodDescriptor
            );
        }
    }
}
