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
import io.rxmicro.test.local.component.RxMicroTestExtension;

/**
 * The basic module designed for test writing using any modern testing framework.
 *
 * <p>
 * This module follows the next package structure rules:
 * <ul>
 *     <li>
 *         {@code io.rxmicro.test} - is root module package that contains:
 *         <ul>
 *             <li>
 *                 {@code internal} - is sub package with classes for current module use only.
 *             </li>
 *             <li>
 *                 {@code local} - is shared sub package, which can be used by other {@code rxmicro} modules only.
 *             </li>
 *             <li>
 *                 any other sub packages and root package - are public API that available for usage.
 *             </li>
 *         </ul>
 *     </li>
 * </ul>
 *
 * @author nedis
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
    requires org.mongodb.driver.reactivestreams;

    exports io.rxmicro.test;
    exports io.rxmicro.test.json;

    exports io.rxmicro.test.local to
            rxmicro.test.junit,
            rxmicro.test.mockito,
            rxmicro.test.mockito.junit,
            rxmicro.test.dbunit,
            rxmicro.test.dbunit.junit;
    exports io.rxmicro.test.local.component to
            rxmicro.test.junit,
            rxmicro.test.mockito.junit,
            rxmicro.test.dbunit.junit;
    exports io.rxmicro.test.local.component.builder to
            rxmicro.test.junit,
            rxmicro.test.dbunit.junit;
    exports io.rxmicro.test.local.component.injector to
            rxmicro.test.junit;
    exports io.rxmicro.test.local.component.validator to
            rxmicro.test.junit,
            rxmicro.test.dbunit,
            rxmicro.test.dbunit.junit;
    exports io.rxmicro.test.local.util to
            rxmicro.test.junit,
            rxmicro.test.dbunit.junit;
    exports io.rxmicro.test.local.model to
            rxmicro.test.junit,
            rxmicro.test.mockito.junit,
            rxmicro.test.dbunit,
            rxmicro.test.dbunit.junit;

    uses UnNamedModuleFixer;
    uses RxMicroTestExtension;
}
