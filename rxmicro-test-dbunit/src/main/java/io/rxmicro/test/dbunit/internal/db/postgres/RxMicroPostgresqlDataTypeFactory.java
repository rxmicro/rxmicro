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

import io.rxmicro.data.sql.r2dbc.postgresql.detail.PostgreSQLConfigAutoCustomizer;
import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.datatype.DataTypeException;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import static io.rxmicro.common.local.InternalModules.isInternalModule;
import static io.rxmicro.common.util.ExCollections.unmodifiableOrderedSet;
import static io.rxmicro.data.sql.r2dbc.postgresql.detail.PostgreSQLConfigAutoCustomizer.POSTGRES_SQL_CONFIG_AUTO_CUSTOMIZER_CLASS_NAME;
import static io.rxmicro.runtime.detail.RxMicroRuntime.getEntryPointFullClassName;
import static io.rxmicro.test.dbunit.internal.data.type.RxMicroDataTypes.CUSTOM_DATA_TYPES;
import static java.util.stream.Collectors.toSet;

/**
 * @author nedis
 * @since 0.7
 */
public final class RxMicroPostgresqlDataTypeFactory extends PostgresqlDataTypeFactory {

    private static final int TYPES_OTHER = java.sql.Types.OTHER;

    private final Set<String> enumNames;

    public RxMicroPostgresqlDataTypeFactory() {
        enumNames = findEnumNames();
    }

    private static Set<String> findEnumNames() {
        final Set<String> enums = new HashSet<>();
        for (final Module module : getAllNotInternalModules()) {
            enums.addAll(tryToGetEnumsForModule(module));
        }
        return unmodifiableOrderedSet(enums);
    }

    @SuppressWarnings("unchecked")
    private static Set<String> tryToGetEnumsForModule(final Module module) {
        final String configAutoCustomizerClassName = getEntryPointFullClassName(module, POSTGRES_SQL_CONFIG_AUTO_CUSTOMIZER_CLASS_NAME);
        try {
            final Class<PostgreSQLConfigAutoCustomizer> repositoryClass =
                    (Class<PostgreSQLConfigAutoCustomizer>) Class.forName(configAutoCustomizerClassName);
            final Constructor<PostgreSQLConfigAutoCustomizer> constructor = repositoryClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance().getEnumMapping().keySet();
        } catch (final ClassNotFoundException |
                IllegalAccessException |
                NoSuchMethodException |
                InstantiationException |
                InvocationTargetException ignored) {
            return Set.of();
        }
    }

    private static Set<Module> getAllNotInternalModules() {
        return ModuleLayer.boot().modules().stream().filter(m -> !m.isNamed() || !isInternalModule(m.getName())).collect(toSet());
    }

    @Override
    public DataType createDataType(final int sqlType,
                                   final String sqlTypeName) throws DataTypeException {
        if (isEnumType(sqlTypeName)) {
            return super.createDataType(TYPES_OTHER, sqlTypeName);
        } else {
            for (final DataType dataType : CUSTOM_DATA_TYPES) {
                if (dataType.getSqlType() == sqlType) {
                    return dataType;
                }
            }
            return super.createDataType(sqlType, sqlTypeName);
        }
    }

    @Override
    public boolean isEnumType(final String sqlTypeName) {
        return enumNames.contains(sqlTypeName.toLowerCase(Locale.ENGLISH));
    }
}
