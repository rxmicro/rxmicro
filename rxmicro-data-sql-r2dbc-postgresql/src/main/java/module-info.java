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
 * The module to work with dynamic repositories for interaction with <a href="https://www.postgresql.org/">PostgreSQL DB</a> using
 * the Reactive Relational Database Connectivity (R2DBC).
 *
 * <p>
 * Read more: <a href="https://github.com/r2dbc/r2dbc-postgresql">https://github.com/r2dbc/r2dbc-postgresql</a>.
 *
 * <p>
 * This module follows the next package structure rules:
 * <ul>
 *     <li>
 *         {@code io.rxmicro.data.sql.r2dbc.postgresql} - is root module package that contains:
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
module rxmicro.data.sql.r2dbc.postgresql {
    requires transitive rxmicro.data.sql.r2dbc;
    requires transitive rxmicro.netty.runtime;
    requires reactor.netty;

    requires transitive r2dbc.postgresql;
    requires transitive r2dbc.pool;

    exports io.rxmicro.data.sql.r2dbc.postgresql;

    exports io.rxmicro.data.sql.r2dbc.postgresql.detail;

    exports io.rxmicro.data.sql.r2dbc.postgresql.local to
            rxmicro.annotation.processor.data.sql.r2dbc.postgresql;
}
