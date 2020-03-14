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

package io.rxmicro.annotation.processor.integration.test;

import static io.rxmicro.annotation.processor.integration.test.internal.MavenUtils.getMavenProperty;
import static io.rxmicro.common.util.Formats.format;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public enum ExternalModule {

    EXTERNAL_NETTY_COMMON_MODULE(
            "io.netty.common",
            "?/.m2/repository/io/netty/netty-common/?/netty-common-?.jar",
            getMavenProperty("netty.version")
    ),

    EXTERNAL_NETTY_CODEC_MODULE(
            "io.netty.codec",
            "?/.m2/repository/io/netty/netty-codec/?/netty-codec-?.jar",
            getMavenProperty("netty.version")
    ),

    EXTERNAL_NETTY_CODEC_HTTP_MODULE(
            "io.netty.codec.http",
            "?/.m2/repository/io/netty/netty-codec-http/?/netty-codec-http-?.jar",
            getMavenProperty("netty.version")
    ),

    EXTERNAL_NETTY_BUFFER_MODULE(
            "io.netty.buffer",
            "?/.m2/repository/io/netty/netty-buffer/?/netty-buffer-?.jar",
            getMavenProperty("netty.version")
    ),

    EXTERNAL_NETTY_TRANSPORT_MODULE(
            "io.netty.transport",
            "?/.m2/repository/io/netty/netty-transport/?/netty-transport-?.jar",
            getMavenProperty("netty.version")
    ),

    EXTERNAL_REACTOR_CORE_MODULE(
            "reactor.core",
            "?/.m2/repository/io/projectreactor/reactor-core/?/reactor-core-?.jar",
            getMavenProperty("external.lib.projectreactor.version")
    ),

    EXTERNAL_REACTIVE_STREAMS_MODULE(
            "org.reactivestreams",
            "?/.m2/repository/org/reactivestreams/reactive-streams/?/reactive-streams-?.jar",
            getMavenProperty("external.lib.reactivestreams.version")
    ),

    EXTERNAL_RX_JAVA_3_MODULE(
            "io.reactivex.rxjava3",
            "?/.m2/repository/io/reactivex/rxjava3/rxjava/?/rxjava-?.jar",
            getMavenProperty("external.lib.rxjava.version")
    ),

    EXTERNAL_MONGO_DB_DRIVER_CORE_MODULE(
            "org.mongodb.driver.core",
            "?/.m2/repository/org/mongodb/mongodb-driver-core/?/mongodb-driver-core-?.jar",
            getMavenProperty("external.lib.mongodb-driver-core.version")
    ),

    EXTERNAL_MONGO_DB_REACTIVE_DRIVER_MODULE(
            "mongodb.driver.reactivestreams",
            "?/.m2/repository/org/mongodb/mongodb-driver-reactivestreams/?/mongodb-driver-reactivestreams-?.jar",
            getMavenProperty("external.lib.mongodb-driver-reactivestreams.version")
    ),

    EXTERNAL_MONGO_DB_BSON_MODULE(
            "org.mongodb.bson",
            "?/.m2/repository/org/mongodb/bson/?/bson-?.jar",
            getMavenProperty("external.lib.bson.version")
    ),

    EXTERNAL_R2DBC_SPI_MODULE(
            "r2dbc.spi",
            "?/.m2/repository/io/r2dbc/r2dbc-spi/?/r2dbc-spi-?.jar",
            getMavenProperty("external.lib.r2dbc-spi.version")
    ),

    EXTERNAL_R2DBC_POSTGRESQL_MODULE(
            "r2dbc.postgresql",
            "?/.m2/repository/io/r2dbc/r2dbc-postgresql/?/r2dbc-postgresql-?.jar",
            getMavenProperty("external.lib.r2dbc-postgresql.version")
    ),

    EXTERNAL_R2DBC_POOL_MODULE(
            "r2dbc.pool",
            "?/.m2/repository/io/r2dbc/r2dbc-pool/?/r2dbc-pool-?.jar",
            getMavenProperty("external.lib.r2dbc-pool.version")
    ),

    EXTERNAL_JUNIT_JUPITER_API_MODULE(
            "org.junit.jupiter.api",
            "?/.m2/repository/org/junit/jupiter/junit-jupiter-api/?/junit-jupiter-api-?.jar",
            getMavenProperty("junit.version")
    ),

    EXTERNAL_JUNIT_JUPITER_PARAMS_MODULE(
            "org.junit.jupiter.params",
            "?/.m2/repository/org/junit/jupiter/junit-jupiter-params/?/junit-jupiter-params-?.jar",
            getMavenProperty("junit.version")
    ),

    EXTERNAL_JUNIT_PLATFORM_COMMONS_MODULE(
            "org.junit-platform-commons",
            "?/.m2/repository/org/junit/platform/junit-platform-commons/?/junit-platform-commons-?.jar",
            getMavenProperty("external.lib.junit-platform-commons.version")
    ),

    EXTERNAL_APIGUARDIAN_API_MODULE(
            "org.apiguardian-api",
            "?/.m2/repository/org/apiguardian/apiguardian-api/?/apiguardian-api-?.jar",
            getMavenProperty("external.lib.apiguardian-api.version")
    ),

    EXTERNAL_OPENTEST4J_MODULE(
            "org.opentest4j",
            "?/.m2/repository/org/opentest4j/opentest4j/?/opentest4j-?.jar",
            getMavenProperty("external.lib.opentest4j.version")
    ),

    EXTERNAL_MOCKITO_JUNIT_JUPITER_MODULE(
            "mockito.junit.jupiter",
            "?/.m2/repository/org/mockito/mockito-junit-jupiter/?/mockito-junit-jupiter-?.jar",
            getMavenProperty("mockito.version")
    ),

    EXTERNAL_MOCKITO_MODULE(
            "org.mockito",
            "?/.m2/repository/org/mockito/mockito-core/?/mockito-core-?.jar",
            getMavenProperty("mockito.version")
    );

    private static final String USER_HOME = System.getProperty("user.home");

    private final String moduleName;

    private final String jarPath;

    private final String version;

    ExternalModule(final String moduleName,
                   final String jarPath,
                   final String version) {
        this.moduleName = moduleName;
        this.jarPath = jarPath;
        this.version = version;
    }

    public String getModuleName() {
        return moduleName;
    }

    public String getJarPath() {
        return format(jarPath, USER_HOME, version, version);
    }
}
