/*
 * Copyright 2019 https://rxmicro.io
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

package io.rxmicro.http.internal;

import io.rxmicro.config.ConfigException;
import io.rxmicro.http.HttpConfig;
import io.rxmicro.http.ProtocolSchema;

import java.util.Arrays;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class HttpConfigExtractor {

    public void extract(final String connectionString,
                        final HttpConfig config) {
        final StringBuilder connectionStringSource = new StringBuilder(connectionString);
        final ProtocolSchema schema = getSchema(connectionStringSource);
        config.setSchema(schema)
                .setHost(getHost(connectionStringSource))
                .setPort(getPort(schema, connectionStringSource));

    }

    private ProtocolSchema getSchema(final StringBuilder connectionStringSource) {
        final int index = connectionStringSource.indexOf("://");
        if (index == -1) {
            return ProtocolSchema.http;
        } else {
            final String schema = connectionStringSource.substring(0, index);
            connectionStringSource.delete(0, index + 3);
            try {
                return ProtocolSchema.valueOf(schema);
            } catch (final IllegalArgumentException e) {
                throw new ConfigException("Only following schemas are supported: ?",
                        Arrays.toString(ProtocolSchema.values()));
            }
        }
    }

    private String getHost(final StringBuilder connectionStringSource) {
        final int index = connectionStringSource.indexOf(":");
        if (index == -1) {
            try {
                return connectionStringSource.toString();
            } finally {
                connectionStringSource.delete(0, connectionStringSource.length());
            }
        } else {
            try {
                return connectionStringSource.substring(0, index);
            } finally {
                connectionStringSource.delete(0, index + 1);
            }
        }
    }

    private int getPort(final ProtocolSchema schema,
                        final StringBuilder connectionStringSource) {
        if (connectionStringSource.length() == 0) {
            return schema.getPort();
        } else {
            try {
                return Integer.parseInt(connectionStringSource.toString());
            } catch (final NumberFormatException e) {
                throw new ConfigException("Port must be a number: ?",
                        connectionStringSource.toString());
            }
        }
    }
}
