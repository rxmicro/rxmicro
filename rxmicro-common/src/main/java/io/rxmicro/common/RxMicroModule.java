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

package io.rxmicro.common;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public enum RxMicroModule {

    RX_MICRO_CDI_MODULE("rxmicro.cdi"),

    RX_MICRO_COMMON_MODULE("rxmicro.common"),

    RX_MICRO_CONFIG_MODULE("rxmicro.config"),

    RX_MICRO_DATA_MODULE("rxmicro.data"),

    RX_MICRO_DATA_MONGO_MODULE("rxmicro.data.mongo"),

    RX_MICRO_DATA_SQL_MODULE("rxmicro.data.sql"),

    RX_MICRO_DATA_SQL_R2DBC_MODULE("rxmicro.data.sql.r2dbc"),

    RX_MICRO_DATA_SQL_R2DBC_POSTGRESQL_MODULE("rxmicro.data.sql.r2dbc.postgresql"),

    RX_MICRO_TOOL_COMMON_MODULE("rxmicro.tool.common"),

    RX_MICRO_DOCUMENTATION_MODULE("rxmicro.documentation"),

    RX_MICRO_DOCUMENTATION_ASCIIDOCTOR_MODULE("rxmicro.documentation.asciidoctor"),

    RX_MICRO_EXCHANGE_JSON_MODULE("rxmicro.exchange.json"),

    RX_MICRO_FILES_MODULE("rxmicro.files"),

    RX_MICRO_HTTP_MODULE("rxmicro.http"),

    RX_MICRO_HTTP_CLIENT_MODULE("rxmicro.http.client"),

    RX_MICRO_HTTP_CLIENT_JDK_MODULE("rxmicro.http.client.jdk"),

    RX_MICRO_JSON_MODULE("rxmicro.json"),

    RX_MICRO_LOGGER_MODULE("rxmicro.logger"),

    RX_MICRO_MODEL_MODULE("rxmicro.model"),

    RX_MICRO_MONITORING_MODULE("rxmicro.monitoring"),

    RX_MICRO_REST_MODULE("rxmicro.rest"),

    RX_MICRO_REST_CLIENT_MODULE("rxmicro.rest.client"),

    RX_MICRO_REST_CLIENT_EXCHANGE_JSON_MODULE("rxmicro.rest.client.exchange.json"),

    RX_MICRO_REST_SERVER_MODULE("rxmicro.rest.server"),

    RX_MICRO_REST_SERVER_EXCHANGE_JSON_MODULE("rxmicro.rest.server.exchange.json"),

    RX_MICRO_REST_SERVER_NETTY_MODULE("rxmicro.rest.server.netty"),

    RX_MICRO_RUNTIME_MODULE("rxmicro.runtime"),

    RX_MICRO_TEST_MODULE("rxmicro.test"),

    RX_MICRO_TEST_JSON_MODULE("rxmicro.test.json"),

    RX_MICRO_TEST_JUNIT_MODULE("rxmicro.test.junit"),

    RX_MICRO_TEST_MOCKITO_MODULE("rxmicro.test.mockito"),

    RX_MICRO_TEST_MOCKITO_JUNIT_MODULE("rxmicro.test.mockito.junit"),

    RX_MICRO_VALIDATION_MODULE("rxmicro.validation");

    private final String name;

    private final String rootPackage;

    public static boolean isSqlR2DBCModule(final String moduleFullName) {
        return moduleFullName.contains("rxmicro.data.sql.r2dbc.");
    }

    public static Optional<RxMicroModule> of(final String moduleFullName) {
        for (final RxMicroModule rxMicroModule : RxMicroModule.values()) {
            if (rxMicroModule.name.equals(moduleFullName)) {
                return Optional.of(rxMicroModule);
            }
        }
        return Optional.empty();
    }

    public static boolean isRxMicroPackage(final String packageName) {
        return Arrays.stream(RxMicroModule.values()).anyMatch(module ->
                module.getRootPackage().equals(packageName) ||
                        packageName.startsWith(module.getRootPackage() + ".")
        );
    }

    RxMicroModule(final String name) {
        this.name = name;
        this.rootPackage = "io." + name;
    }

    public String getName() {
        return name;
    }

    public String getRootPackage() {
        return rootPackage;
    }

    @Override
    public String toString() {
        return name;
    }
}
