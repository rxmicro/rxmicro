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

import io.rxmicro.annotation.processor.common.CommonDependenciesModule;
import io.rxmicro.annotation.processor.common.FormatSourceCodeDependenciesModule;
import io.rxmicro.annotation.processor.data.AbstractDataModuleClassStructuresBuilder;
import io.rxmicro.annotation.processor.data.CommonDataDependenciesModule;
import io.rxmicro.annotation.processor.data.sql.SQLDependenciesModule;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataModelField;
import io.rxmicro.annotation.processor.data.sql.r2dbc.R2DBCSQLDependenciesModule;
import io.rxmicro.annotation.processor.data.sql.r2dbc.postgresql.model.PostgreSQLDataObjectModelClass;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLRepository;

import java.util.Set;

import static io.rxmicro.annotation.processor.common.util.Injects.injectDependencies;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class PostgreSQLModuleClassStructuresBuilder
        extends AbstractDataModuleClassStructuresBuilder<SQLDataModelField, PostgreSQLDataObjectModelClass> {

    public static PostgreSQLModuleClassStructuresBuilder create() {
        final PostgreSQLModuleClassStructuresBuilder builder = new PostgreSQLModuleClassStructuresBuilder();
        injectDependencies(
                builder,
                new FormatSourceCodeDependenciesModule(),
                new CommonDependenciesModule(),
                new CommonDataDependenciesModule(),
                new SQLDependenciesModule(),
                new R2DBCSQLDependenciesModule(),
                new PostgreSQLDependenciesModule()
        );
        return builder;
    }

    private PostgreSQLModuleClassStructuresBuilder() {
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of(PostgreSQLRepository.class.getName());
    }
}
