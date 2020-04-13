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
import io.rxmicro.rest.server.detail.component.HttpResponseBuilder;
import io.rxmicro.rest.server.local.component.HttpErrorResponseBodyBuilder;
import io.rxmicro.rest.server.local.component.ServerFactory;
import io.rxmicro.rest.server.local.test.ServerUnNamedModuleFixer;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
module rxmicro.rest.server {
    requires transitive rxmicro.rest;
    requires transitive rxmicro.logger;
    requires transitive rxmicro.config;

    exports io.rxmicro.rest.server;
    exports io.rxmicro.rest.server.feature;

    exports io.rxmicro.rest.server.detail.model;
    exports io.rxmicro.rest.server.detail.model.mapping;
    exports io.rxmicro.rest.server.detail.component;

    exports io.rxmicro.rest.server.local.component to
            rxmicro.rest.server.netty,
            rxmicro.validation,
            rxmicro.test,
            rxmicro.test.junit,
            rxmicro.rest.server.exchange.json,
            rxmicro.annotation.processor.rest.server;
    exports io.rxmicro.rest.server.local.component.impl to
            rxmicro.rest.server.netty,
            rxmicro.benchmarks;
    exports io.rxmicro.rest.server.local.model to
            rxmicro.test,
            rxmicro.test.junit;
    exports io.rxmicro.rest.server.local.util to
            rxmicro.rest.server.netty;

    opens io.rxmicro.rest.server to
            rxmicro.config;

    uses HttpResponseBuilder;
    uses ServerFactory;
    uses HttpErrorResponseBodyBuilder;

    provides UnNamedModuleFixer with ServerUnNamedModuleFixer;
}