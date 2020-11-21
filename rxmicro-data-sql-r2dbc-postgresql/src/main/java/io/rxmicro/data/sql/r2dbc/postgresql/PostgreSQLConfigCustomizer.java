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

package io.rxmicro.data.sql.r2dbc.postgresql;

import io.r2dbc.postgresql.extension.CodecRegistrar;
import io.r2dbc.spi.Connection;
import io.rxmicro.data.sql.r2dbc.postgresql.internal.PostgreSQLConnectionPoolBuilder;

import java.util.function.Function;

/**
 * Allows configuring the application specific configs for PostgreSQL database driver (R2DBC driver).
 *
 * <ul>
 *     <li>{@link PostgreSQLConfig} must be used for environment specific configs.</li>
 *     <li>{@link PostgreSQLConfigCustomizer} must be used for application specific configs.</li>
 * </ul>
 *
 * @author nedis
 * @see PostgreSQLConfig
 * @since 0.7
 */
public final class PostgreSQLConfigCustomizer {

    /**
     * Allows registering custom codecs.
     *
     * @param codecRegistrars the custom codecs
     */
    public static void registerPostgreSQLCodecs(final CodecRegistrar... codecRegistrars) {
        for (final CodecRegistrar codecRegistrar : codecRegistrars) {
            PostgreSQLConnectionPoolBuilder.getInstance().addCodecRegistrar(codecRegistrar);
        }
    }

    /**
     * Sets the connection decorator function.
     *
     * <p>
     * <i>This features is useful for testing purposes</i>.
     *
     * @param connectionDecorator the connection decorator function
     */
    public static void setConnectionDecorator(final Function<Connection, Connection> connectionDecorator) {
        PostgreSQLConnectionPoolBuilder.getInstance().setConnectionDecorator(connectionDecorator);
    }

    private PostgreSQLConfigCustomizer() {
    }
}
