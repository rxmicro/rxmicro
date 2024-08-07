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

import io.rxmicro.common.local.ConfigurationResetController;
import io.rxmicro.rest.server.detail.component.HttpResponseBuilder;
import io.rxmicro.rest.server.local.component.HttpErrorResponseBodyBuilder;
import io.rxmicro.rest.server.local.component.ServerFactory;
import io.rxmicro.rest.server.netty.internal.component.NettyHttpResponseBuilder;
import io.rxmicro.rest.server.netty.internal.component.NettyServerConfigurationResetController;
import io.rxmicro.rest.server.netty.internal.component.NettyServerFactory;

/**
 * The module that defines HTTP server implementation based on <a href="https://netty.io/">Netty</a>.
 *
 * <p>
 * This module follows the next package structure rules:
 * <ul>
 *     <li>
 *         {@code io.rxmicro.rest.server.netty} - is root module package that contains:
 *         <ul>
 *             <li>
 *                 {@code internal} - is sub package with classes for current module use only.
 *             </li>
 *             <li>
 *                 {@code local} - is shared sub package, which can be used by other {@code rxmicro} modules only.
 *             </li>
 *             <li>
 *                 {@code detail} - is sub package for generated code by {@code RxMicro Annotation Processor} use preferably.<br>
 *                 <i>Developer must not use classes from this sub package!</i><br>
 *                 <i>(Except documented abilities: HTTP internal types, partial implementations, etc.)</i>
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
@SuppressWarnings("JavaRequiresAutoModule")
module rxmicro.rest.server.netty {
    requires transitive rxmicro.rest.server;
    requires transitive rxmicro.netty.runtime;

    requires transitive io.netty.transport;
    requires transitive io.netty.buffer;
    requires transitive io.netty.codec;
    requires transitive io.netty.codec.http;
    requires transitive io.netty.common;
    requires transitive io.netty.handler;
    // For sun.misc.Unsafe
    requires jdk.unsupported;
    requires rxmicro.validation;

    exports io.rxmicro.rest.server.netty;

    uses HttpErrorResponseBodyBuilder;

    provides ServerFactory with NettyServerFactory;
    provides ConfigurationResetController with NettyServerConfigurationResetController;
    provides HttpResponseBuilder with NettyHttpResponseBuilder;
}
