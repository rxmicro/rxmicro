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

package io.rxmicro.annotation.processor.data.sql.r2dbc.postgresql.model;

import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.DefaultConfigProxyValue;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataRepositoryMethod;
import io.rxmicro.annotation.processor.data.sql.r2dbc.model.AbstractSQLDataRepositoryClassStructure;
import io.rxmicro.data.local.EntityFromDBConverter;
import io.rxmicro.data.local.EntityToDBConverter;
import io.rxmicro.data.sql.r2dbc.detail.EntityFromR2DBCSQLDBConverter;
import io.rxmicro.data.sql.r2dbc.detail.EntityToR2DBCSQLDBConverter;

import javax.lang.model.element.TypeElement;
import java.util.List;
import java.util.Map;

/**
 * @author nedis
 * @since 0.1
 */
public final class PostgreSQLRepositoryClassStructure extends AbstractSQLDataRepositoryClassStructure {

    public PostgreSQLRepositoryClassStructure(final ClassHeader.Builder classHeaderBuilder,
                                              final TypeElement repositoryInterface,
                                              final TypeElement abstractClass,
                                              final String configNameSpace,
                                              final List<SQLDataRepositoryMethod> methods,
                                              final List<Map.Entry<String, DefaultConfigProxyValue>> defaultConfigValues) {
        super(classHeaderBuilder, repositoryInterface, abstractClass, configNameSpace, methods, defaultConfigValues);
    }

    @Override
    protected String getRepositoryTypePrefix() {
        return "PostgreSQL";
    }

    @Override
    protected Class<? extends EntityToDBConverter> getEntityToDBConverterClass() {
        return EntityToR2DBCSQLDBConverter.class;
    }

    @Override
    protected Class<? extends EntityFromDBConverter> getEntityFromDBConverterClass() {
        return EntityFromR2DBCSQLDBConverter.class;
    }
}
