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

/**
 * The basic module to work with dynamic repositories for interaction with relational SQL databases.
 *
 * <p>
 * This module follows the next package structure rules:
 * <ul>
 *     <li>
 *         {@code io.rxmicro.data.sql} - is root module package that contains:
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
module rxmicro.data.sql {
    requires transitive rxmicro.data;
    requires transitive rxmicro.logger;

    requires transitive org.reactivestreams;
    requires static reactor.core;
    requires static io.reactivex.rxjava3;
    requires rxmicro.validation;

    exports io.rxmicro.data.sql;
    exports io.rxmicro.data.sql.operation;
    exports io.rxmicro.data.sql.model;
    exports io.rxmicro.data.sql.model.reactor;
    exports io.rxmicro.data.sql.model.rxjava3;
    exports io.rxmicro.data.sql.model.completablefuture;

    exports io.rxmicro.data.sql.detail;

    exports io.rxmicro.data.sql.local to
            rxmicro.data.sql.r2dbc.postgresql;
    exports io.rxmicro.data.sql.local.impl to
            rxmicro.data.sql.r2dbc;
}
