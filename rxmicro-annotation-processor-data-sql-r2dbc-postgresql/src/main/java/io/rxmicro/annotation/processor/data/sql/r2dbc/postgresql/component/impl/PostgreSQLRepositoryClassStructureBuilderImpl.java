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

package io.rxmicro.annotation.processor.data.sql.r2dbc.postgresql.component.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.data.component.impl.AbstractDataClassStructureBuilder;
import io.rxmicro.annotation.processor.data.model.DataGenerationContext;
import io.rxmicro.annotation.processor.data.model.DataRepositoryClassStructure;
import io.rxmicro.annotation.processor.data.model.DataRepositoryInterfaceSignature;
import io.rxmicro.annotation.processor.data.sql.component.SQLRepositoryMethodModelBuilder;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataModelField;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataRepositoryMethod;
import io.rxmicro.annotation.processor.data.sql.r2dbc.postgresql.model.PostgreSQLDataObjectModelClass;
import io.rxmicro.annotation.processor.data.sql.r2dbc.postgresql.model.PostgreSQLRepositoryClassStructure;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLRepository;

import java.util.List;
import java.util.Set;

import static io.rxmicro.annotation.processor.common.model.ClassHeader.newClassHeaderBuilder;
import static io.rxmicro.annotation.processor.common.util.Annotations.getDefaultConfigValues;
import static io.rxmicro.annotation.processor.common.util.Names.getPackageName;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class PostgreSQLRepositoryClassStructureBuilderImpl extends AbstractDataClassStructureBuilder<SQLDataModelField, PostgreSQLDataObjectModelClass> {

    @Inject
    private Set<SQLRepositoryMethodModelBuilder<SQLDataModelField, PostgreSQLDataObjectModelClass>> postgreSQLRepositoryMethodModelBuilders;

    @Override
    public DataRepositoryClassStructure build(final EnvironmentContext environmentContext,
                                              final DataRepositoryInterfaceSignature signature,
                                              final DataGenerationContext<SQLDataModelField, PostgreSQLDataObjectModelClass> dataGenerationContext) {
        final ClassHeader.Builder classHeaderBuilder = newClassHeaderBuilder(getPackageName(signature.getRepositoryInterface()));
        final PostgreSQLRepository postgreSQLRepository =
                signature.getRepositoryInterface().getAnnotation(PostgreSQLRepository.class);
        final List<SQLDataRepositoryMethod> methods = buildMethods(
                postgreSQLRepositoryMethodModelBuilders,
                environmentContext,
                dataGenerationContext,
                signature,
                classHeaderBuilder
        );
        return new PostgreSQLRepositoryClassStructure(
                classHeaderBuilder,
                signature.getRepositoryInterface(),
                signature.getRepositoryAbstractClass(),
                postgreSQLRepository.configNameSpace(),
                methods,
                getDefaultConfigValues(postgreSQLRepository.configNameSpace(), signature.getRepositoryInterface())
        );
    }
}
