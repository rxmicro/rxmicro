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

import io.rxmicro.common.model.UnNamedModuleFixer;
import io.rxmicro.runtime.local.test.RuntimeUnNamedModuleFixer;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
module rxmicro.runtime {
    requires transitive rxmicro.common;
    requires transitive rxmicro.logger;

    exports io.rxmicro.runtime;
    exports io.rxmicro.runtime.detail;

    exports io.rxmicro.runtime.local to
            rxmicro.test,
            rxmicro.test.junit,
            rxmicro.data,
            rxmicro.config,
            rxmicro.data.mongo,
            rxmicro.rest.client,
            rxmicro.rest.server,
            rxmicro.rest.server.netty,
            rxmicro.validation,
            rxmicro.annotation.processor,
            rxmicro.http.client,
            rxmicro.http.client.jdk,
            rxmicro.data.sql.r2dbc.postgresql,
            rxmicro.cdi;
    exports io.rxmicro.runtime.local.provider to
            rxmicro.data, rxmicro.rest.client,
            rxmicro.data.sql.r2dbc.postgresql,
            rxmicro.data.mongo,
            rxmicro.test;
    exports io.rxmicro.runtime.local.error to
            rxmicro.cdi;

    provides UnNamedModuleFixer with RuntimeUnNamedModuleFixer;
}