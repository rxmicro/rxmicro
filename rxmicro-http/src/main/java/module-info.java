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
import io.rxmicro.http.local.test.HttpUnNamedModuleFixer;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
module rxmicro.http {
    requires transitive rxmicro.config;

    exports io.rxmicro.http;
    exports io.rxmicro.http.error;

    exports io.rxmicro.http.local to
            rxmicro.rest.client,
            rxmicro.rest.server,
            rxmicro.rest.server.netty,
            rxmicro.annotation.processor.rest;

    opens io.rxmicro.http to
            rxmicro.config;

    provides UnNamedModuleFixer with HttpUnNamedModuleFixer;
}
