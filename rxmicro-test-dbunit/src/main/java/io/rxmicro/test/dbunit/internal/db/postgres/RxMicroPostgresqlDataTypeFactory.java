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

package io.rxmicro.test.dbunit.internal.db.postgres;

import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.datatype.DataTypeException;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import java.util.Set;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.data.RepositoryFactory.REPOSITORY_FACTORY_IMPL_CLASS_NAME;
import static io.rxmicro.runtime.detail.RxMicroRuntime.ENTRY_POINT_PACKAGE;

/**
 * @author nedis
 * @since 0.7
 */
public class RxMicroPostgresqlDataTypeFactory extends PostgresqlDataTypeFactory {

    private static final int TYPES_OTHER = java.sql.Types.OTHER;

    private final Set<String> enumNames;

    public RxMicroPostgresqlDataTypeFactory() {
        enumNames = findEnumNames();
    }

    @SuppressWarnings("unchecked")
    private static Set<String> findEnumNames() {
        final String repositoryClassName = format("?.?", ENTRY_POINT_PACKAGE, REPOSITORY_FACTORY_IMPL_CLASS_NAME);
        try {
            final Class<?> repositoryClass = Class.forName(repositoryClassName);
            return (Set<String>) repositoryClass.getMethod("getPostgresEnumNames").invoke(repositoryClass);
        } catch (final ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException ignore) {
            return Set.of();
        }
    }

    @Override
    public DataType createDataType(final int sqlType,
                                   final String sqlTypeName) throws DataTypeException {
        if (isEnumType(sqlTypeName)) {
            return super.createDataType(TYPES_OTHER, sqlTypeName);
        } else {
            return super.createDataType(sqlType, sqlTypeName);
        }
    }

    @Override
    public boolean isEnumType(final String sqlTypeName) {
        return enumNames.contains(sqlTypeName.toLowerCase(Locale.ENGLISH));
    }
}
