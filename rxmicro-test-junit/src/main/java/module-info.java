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
import io.rxmicro.test.junit.internal.JUnitRxMicroTestExtension;
import io.rxmicro.test.local.component.RxMicroTestExtension;

/**
 * The module designed for test writing using the <a href="https://junit.org/junit5/">JUnit 5</a> framework.
 *
 * <p>
 * This module follows the next package structure rules:
 * <ul>
 *     <li>
 *         {@code io.rxmicro.test.junit} - is root module package that contains:
 *         <ul>
 *             <li>
 *                 {@code internal} - is sub package with classes for current module use only.
 *             </li>
 *             <li>
 *                 {@code local} - is shared sub package, which can be used by other {@code rxmicro} modules only.
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
module rxmicro.test.junit {
    requires transitive rxmicro.test;

    requires transitive org.junit.jupiter.api;
    requires transitive org.junit.jupiter.params;

    exports io.rxmicro.test.junit;

    exports io.rxmicro.test.junit.local to
            rxmicro.test.dbunit.junit;
    exports io.rxmicro.test.junit.internal to
            org.junit.jupiter.params,
            org.junit.jupiter.api;

    uses ConfigurationResetController;

    provides RxMicroTestExtension with JUnitRxMicroTestExtension;
}
