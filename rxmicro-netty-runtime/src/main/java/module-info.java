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
import io.rxmicro.netty.runtime.local.NettyRuntimeUnNamedModuleFixer;

/**
 * The module that defines netty runtime components.
 *
 * <p>
 * This module follows the next package structure rules:
 * <ul>
 *     <li>
 *         {@code io.rxmicro.netty.runtime} - is root module package that contains:
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
 * @since 0.8
 */
@SuppressWarnings("JavaRequiresAutoModule")
module rxmicro.netty.runtime {
    requires transitive rxmicro.runtime;

    requires transitive io.netty.transport;
    requires transitive io.netty.buffer;
    requires transitive io.netty.common;

    requires static io.netty.transport.classes.epoll;
    requires static io.netty.transport.classes.kqueue;
    requires rxmicro.validation;

    exports io.rxmicro.netty.runtime;

    exports io.rxmicro.netty.runtime.local to
            rxmicro.data.mongo,
            rxmicro.data.sql.r2dbc.postgresql,
            rxmicro.rest.server.netty,
            rxmicro.rest.client.netty,
            rxmicro.test.junit;

    provides UnNamedModuleFixer with NettyRuntimeUnNamedModuleFixer;
}
