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
 * Contains all RxMicro modules, that can be used to create microservices using the RxMicro framework.
 *
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public enum RxMicroModule {

    /**
     * The module that is an implementation of the <a href="https://en.wikipedia.org/wiki/Dependency_injection">Dependency Injection</a>
     * design pattern, that is integrated to the RxMicro framework.
     */
    RX_MICRO_CDI_MODULE("rxmicro.cdi"),

    /**
     * The common module with base models and useful utils
     */
    RX_MICRO_COMMON_MODULE("rxmicro.common"),

    /**
     * The module for flexible configuration of microservice projects to any environment.
     * <p>
     * This module provides the following features:
     * <ul>
     *     <li>Support for different types of configuration sources:
     *          files, classpath resources, environment variables, command line arguments, etc.</li>
     *     <li>Inheritance and redefinition of settings from different configuration sources</li>
     *     <li>Changing the order in which the configuration sources are read</li>
     *     <li>Configuration using annotations and Java classes</li>
     * </ul>
     */
    RX_MICRO_CONFIG_MODULE("rxmicro.config"),

    /**
     * The basic module to work with dynamic repositories for interaction with SQL and NoSQL databases.
     */
    RX_MICRO_DATA_MODULE("rxmicro.data"),

    /**
     * The module to work with dynamic repositories for interaction with <a href="https://www.mongodb.com/">Mongo DB</a>.
     */
    RX_MICRO_DATA_MONGO_MODULE("rxmicro.data.mongo"),

    /**
     * The basic module to work with dynamic repositories for interaction with relational SQL databases.
     */
    RX_MICRO_DATA_SQL_MODULE("rxmicro.data.sql"),

    /**
     * The basic module to work with dynamic repositories for interaction with relational SQL databases using
     * the Reactive Relational Database Connectivity (R2DBC).
     * <p>
     * Read more: <a href="https://r2dbc.io/">https://r2dbc.io</a>
     */
    RX_MICRO_DATA_SQL_R2DBC_MODULE("rxmicro.data.sql.r2dbc"),

    /**
     * The module to work with dynamic repositories for interaction with <a href="https://www.postgresql.org/">PostgreSQL DB</a> using
     * the Reactive Relational Database Connectivity (R2DBC).
     * <p>
     * Read more: <a href="https://github.com/r2dbc/r2dbc-postgresql">https://github.com/r2dbc/r2dbc-postgresql</a>
     */
    RX_MICRO_DATA_SQL_R2DBC_POSTGRESQL_MODULE("rxmicro.data.sql.r2dbc.postgresql"),

    /**
     * The basic module for creation a REST-based microservice documentation.
     */
    RX_MICRO_DOCUMENTATION_MODULE("rxmicro.documentation"),

    /**
     * The module for creation a REST-based microservice documentation, based on widely used and multifunctional
     * <a href="http://asciidoc.org/">AsciiDoc</a> documenting format.
     */
    RX_MICRO_DOCUMENTATION_ASCIIDOCTOR_MODULE("rxmicro.documentation.asciidoctor"),

    /**
     * The module for converting a Java models to JSON format and vice versa.
     */
    RX_MICRO_EXCHANGE_JSON_MODULE("rxmicro.exchange.json"),

    /**
     * The module to work with file system using sync and async modes.
     */
    RX_MICRO_FILES_MODULE("rxmicro.files"),

    /**
     * The basic module to work with HTTP protocol.
     */
    RX_MICRO_HTTP_MODULE("rxmicro.http"),

    /**
     * The module that provides a low-level reactive HTTP client.
     */
    RX_MICRO_HTTP_CLIENT_MODULE("rxmicro.http.client"),

    /**
     * The module that is a low-level HTTP client implementation module based on
     * <a href="https://openjdk.java.net/groups/net/httpclient/intro.html">Java HTTP Client</a>;
     * <p>
     * This module can be used for REST based microservice testing.
     */
    RX_MICRO_HTTP_CLIENT_JDK_MODULE("rxmicro.http.client.jdk"),

    /**
     * The module for low-level and efficient work with <a href="https://json.org/">JSON</a> format.
     */
    RX_MICRO_JSON_MODULE("rxmicro.json"),

    /**
     * The module for logging important events during the work of microservices that is integrated to the RxMicro framework.
     */
    RX_MICRO_LOGGER_MODULE("rxmicro.logger"),

    /**
     * The basic module that provides modelling utils.
     */
    RX_MICRO_MODEL_MODULE("rxmicro.model"),

    /**
     * The module for monitoring of microservices work.
     */
    RX_MICRO_MONITORING_MODULE("rxmicro.monitoring"),

    /**
     * The basic module that defines basic RxMicro annotations, required when using the
     * <a href="https://en.wikipedia.org/wiki/Representational_state_transfer">REST architecture</a> of building program systems.
     */
    RX_MICRO_REST_MODULE("rxmicro.rest"),

    /**
     * The basic module used to create and run REST clients.
     */
    RX_MICRO_REST_CLIENT_MODULE("rxmicro.rest.client"),

    /**
     * The module for converting Java models to JSON format and vice versa for REST clients.
     */
    RX_MICRO_REST_CLIENT_EXCHANGE_JSON_MODULE("rxmicro.rest.client.exchange.json"),

    /**
     * The module that is a REST client implementation module based on
     * <a href="https://openjdk.java.net/groups/net/httpclient/intro.html">Java HTTP Client</a>;
     * <p>
     * This module requires {@code rxmicro.http.client.jdk} module.
     */
    RX_MICRO_REST_CLIENT_JDK_MODULE("rxmicro.rest.client.jdk"),

    /**
     * The basic HTTP server module used to create REST controllers and run REST-based microservices.
     */
    RX_MICRO_REST_SERVER_MODULE("rxmicro.rest.server"),

    /**
     * The module for converting Java models to JSON format and vice versa for REST controllers.
     */
    RX_MICRO_REST_SERVER_EXCHANGE_JSON_MODULE("rxmicro.rest.server.exchange.json"),

    /**
     * The module that defines HTTP server implementation based on <a href="https://netty.io/">Netty</a>.
     */
    RX_MICRO_REST_SERVER_NETTY_MODULE("rxmicro.rest.server.netty"),

    /**
     * The module that defines common runtime components.
     */
    RX_MICRO_RUNTIME_MODULE("rxmicro.runtime"),

    //rxmicro-slf4j-proxy is a workaround module, so it was excluded from this list

    /**
     * TODO
     */
    RX_MICRO_TEST_MODULE("rxmicro.test"),

    /**
     * TODO
     */
    RX_MICRO_TEST_JUNIT_MODULE("rxmicro.test.junit"),

    /**
     * TODO
     */
    RX_MICRO_TEST_MOCKITO_MODULE("rxmicro.test.mockito"),

    /**
     * TODO
     */
    RX_MICRO_TEST_MOCKITO_JUNIT_MODULE("rxmicro.test.mockito.junit"),

    /**
     * The basic module that provides common tools for other modules
     */
    RX_MICRO_TOOL_COMMON_MODULE("rxmicro.tool.common"),

    /**
     * The basic module that contains all supported constraints and validators.
     */
    RX_MICRO_VALIDATION_MODULE("rxmicro.validation");

    private final String name;

    private final String rootPackage;

    /**
     * Returns {@code true} if provided module name is R2DBC module.
     *
     * @param moduleFullName module full name
     * @return {@code true} if provided module name is R2DBC module
     */
    public static boolean isSqlR2DBCModule(final String moduleFullName) {
        return moduleFullName.contains("rxmicro.data.sql.r2dbc.");
    }

    /**
     * Returns the enum constant of the {@link RxMicroModule} type with the specified module full name.
     *
     * @param moduleFullName the specified module full name
     * @return the enum constant of the {@link RxMicroModule} type
     */
    public static Optional<RxMicroModule> of(final String moduleFullName) {
        for (final RxMicroModule rxMicroModule : RxMicroModule.values()) {
            if (rxMicroModule.name.equals(moduleFullName)) {
                return Optional.of(rxMicroModule);
            }
        }
        return Optional.empty();
    }

    /**
     * Returns {@code true} if provided package name is RxMicro package.
     *
     * @param packageName tested package
     * @return {@code true} if provided package name is RxMicro package
     */
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

    /**
     * Returns the RxMicro module name
     *
     * @return the RxMicro module name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the root package of the current RxMicro module name
     *
     * @return the root package of the current RxMicro module name
     */
    public String getRootPackage() {
        return rootPackage;
    }

    @Override
    public String toString() {
        return name;
    }
}
