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

package io.rxmicro.annotation.processor.cdi.component.impl;

import io.rxmicro.annotation.processor.cdi.model.InjectionPointType;
import io.rxmicro.annotation.processor.common.model.error.InternalErrorException;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.common.RxMicroModule;
import io.rxmicro.data.sql.SQLPooledDatabaseConfig;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLConfig;

import javax.lang.model.element.Element;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static io.rxmicro.annotation.processor.cdi.component.impl.AbstractR2DBCConnectionProvider.R2DBCConnectionProvider.POSTGRE_SQL_CONNECTION_PROVIDER;
import static io.rxmicro.annotation.processor.cdi.model.InjectionPointType.POSTGRE_SQL_CONNECTION_FACTORY;
import static io.rxmicro.annotation.processor.cdi.model.InjectionPointType.POSTGRE_SQL_CONNECTION_POOL;
import static io.rxmicro.annotation.processor.common.util.AnnotationProcessorEnvironment.elements;
import static io.rxmicro.common.RxMicroModule.RX_MICRO_DATA_SQL_R2DBC_POSTGRESQL_MODULE;
import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public abstract class AbstractR2DBCConnectionProvider {

    private final Map<String, R2DBCConnectionProvider> map =
            Map.of(RX_MICRO_DATA_SQL_R2DBC_POSTGRESQL_MODULE.getName(), POSTGRE_SQL_CONNECTION_PROVIDER);

    protected final Set<R2DBCConnectionProvider> getR2DBCConnectionProviders(final Element element) {
        final Set<String> sqlModuleElementNames = elements().getAllModuleElements().stream()
                .map(me -> me.getQualifiedName().toString())
                .filter(RxMicroModule::isSqlR2DBCModule)
                .collect(Collectors.toSet());
        if (sqlModuleElementNames.size() == 1) {
            final String moduleName = sqlModuleElementNames.iterator().next();
            if (RX_MICRO_DATA_SQL_R2DBC_POSTGRESQL_MODULE.getName().equals(moduleName)) {
                return Set.of(POSTGRE_SQL_CONNECTION_PROVIDER);
            } else {
                throw new InternalErrorException("Unsupported SQL R2DBC module: '?'", moduleName);
            }
        } else if (sqlModuleElementNames.isEmpty()) {
            throw new InterruptProcessingException(
                    element,
                    "Missing sql r2dbc module. Add missing module to project!"
            );
        } else {
            return sqlModuleElementNames.stream()
                    .map(n -> require(map.get(n), "Missing R2DBCConnectionProvider enum constant: " + n))
                    .collect(Collectors.toSet());
        }
    }

    /**
     * @author nedis
     * @link http://rxmicro.io
     * @since 0.1
     */
    protected enum R2DBCConnectionProvider {

        POSTGRE_SQL_CONNECTION_PROVIDER(
                POSTGRE_SQL_CONNECTION_FACTORY,
                POSTGRE_SQL_CONNECTION_POOL,
                PostgreSQLConfig.class
        );

        private final InjectionPointType connectionFactory;

        private final InjectionPointType connectionPool;

        private final Class<? extends SQLPooledDatabaseConfig> dataBaseConfigClass;

        R2DBCConnectionProvider(final InjectionPointType connectionFactory,
                                final InjectionPointType connectionPool,
                                final Class<? extends SQLPooledDatabaseConfig> dataBaseConfigClass) {
            this.connectionFactory = connectionFactory;
            this.connectionPool = connectionPool;
            this.dataBaseConfigClass = dataBaseConfigClass;
        }

        public InjectionPointType getConnectionFactory() {
            return connectionFactory;
        }

        public InjectionPointType getConnectionPool() {
            return connectionPool;
        }

        public Class<? extends SQLPooledDatabaseConfig> getDataBaseConfigClass() {
            return dataBaseConfigClass;
        }
    }
}
