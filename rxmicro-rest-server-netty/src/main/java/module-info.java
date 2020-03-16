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

import io.rxmicro.rest.server.detail.component.HttpResponseBuilder;
import io.rxmicro.rest.server.local.component.HttpErrorResponseBodyBuilder;
import io.rxmicro.rest.server.local.component.ServerFactory;
import io.rxmicro.rest.server.netty.internal.component.NettyHttpResponseBuilder;
import io.rxmicro.rest.server.netty.internal.component.NettyServerFactory;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@SuppressWarnings("JavaRequiresAutoModule")
module rxmicro.rest.server.netty {
    requires transitive rxmicro.rest.server;
    requires transitive io.netty.transport;
    requires transitive io.netty.buffer;
    requires transitive io.netty.codec;
    requires transitive io.netty.codec.http;
    requires transitive io.netty.common;

    // For sun.misc.Unsafe
    requires jdk.unsupported;

    exports io.rxmicro.rest.server.netty;

    opens io.rxmicro.rest.server.netty to
            rxmicro.config;

    uses HttpErrorResponseBodyBuilder;

    provides ServerFactory with NettyServerFactory;
    provides HttpResponseBuilder with NettyHttpResponseBuilder;
}