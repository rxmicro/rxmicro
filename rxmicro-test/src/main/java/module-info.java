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

import io.rxmicro.common.model.UnNamedModuleFixer;
import io.rxmicro.test.local.component.TestExtension;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
module rxmicro.test {
    requires transitive rxmicro.tool.common;
    requires transitive rxmicro.data.mongo;
    requires transitive rxmicro.data.sql.r2dbc.postgresql;
    requires transitive rxmicro.rest.server;
    requires transitive rxmicro.rest.client;
    requires transitive rxmicro.json;
    requires transitive rxmicro.cdi;
    requires static rxmicro.rest.server.exchange.json;

    requires java.net.http;
    requires mongodb.driver.reactivestreams;

    exports io.rxmicro.test;
    exports io.rxmicro.test.json;

    exports io.rxmicro.test.local to
            rxmicro.test.junit,
            rxmicro.test.mockito, rxmicro.test.mockito.junit;
    exports io.rxmicro.test.local.component to
            rxmicro.test.junit, rxmicro.test.mockito.junit;
    exports io.rxmicro.test.local.component.builder to
            rxmicro.test.junit;
    exports io.rxmicro.test.local.component.injector to
            rxmicro.test.junit;
    exports io.rxmicro.test.local.component.validator to
            rxmicro.test.junit;
    exports io.rxmicro.test.local.util to
            rxmicro.test.junit;
    exports io.rxmicro.test.local.model to
            rxmicro.test.junit, rxmicro.test.mockito.junit;

    uses UnNamedModuleFixer;
    uses TestExtension;
}