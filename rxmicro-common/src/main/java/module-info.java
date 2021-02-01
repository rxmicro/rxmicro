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

import io.rxmicro.common.internal.CommonUnNamedModuleFixer;
import io.rxmicro.common.model.UnNamedModuleFixer;

/**
 * The common module with base models and useful utils.
 *
 * <p>
 * This module follows the next package structure rules:
 * <ul>
 *     <li>
 *         {@code io.rxmicro.common} - is root module package that contains:
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
module rxmicro.common {
    exports io.rxmicro.common;
    exports io.rxmicro.common.model;
    exports io.rxmicro.common.meta;
    exports io.rxmicro.common.util;

    exports io.rxmicro.common.local to
            rxmicro.logger,
            rxmicro.runtime,
            rxmicro.config,
            rxmicro.rest,
            rxmicro.rest.client.netty,
            rxmicro.rest.server,
            rxmicro.rest.server.netty,
            rxmicro.json,
            rxmicro.exchange.json,
            rxmicro.test,
            rxmicro.test.junit,
            rxmicro.test.mockito,
            rxmicro.annotation.processor,
            rxmicro.annotation.processor.common,
            rxmicro.annotation.processor.cdi,
            rxmicro.annotation.processor.rest.server,
            rxmicro.annotation.processor.documentation,
            rxmicro.annotation.processor.documentation.asciidoctor;

    provides UnNamedModuleFixer with CommonUnNamedModuleFixer;
}
