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
import io.rxmicro.config.internal.test.ConfigUnNamedModuleFixer;

/**
 * The module for flexible configuration of microservice projects to any environment.
 *
 * <p>
 * This module provides the following features:
 * <ul>
 *     <li>Support for different types of configuration sources:
 *          files, classpath resources, environment variables, command line arguments, etc.</li>
 *     <li>Inheritance and redefinition of settings from different configuration sources</li>
 *     <li>Changing the order in which the configuration sources are read</li>
 *     <li>Configuration using annotations and Java classes</li>
 * </ul>
 *
 * <p>
 * This module follows the next package structure rules:
 * <ul>
 *     <li>
 *         {@code io.rxmicro.config} - is root module package that contains:
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
module rxmicro.config {
    requires transitive rxmicro.runtime;
    requires rxmicro.files;

    exports io.rxmicro.config;
    exports io.rxmicro.config.detail;

    exports io.rxmicro.config.local to
            rxmicro.test.junit;

    provides UnNamedModuleFixer with ConfigUnNamedModuleFixer;
}
