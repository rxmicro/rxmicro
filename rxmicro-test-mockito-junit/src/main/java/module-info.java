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

import io.rxmicro.test.local.component.RxMicroTestExtension;
import io.rxmicro.test.mockito.junit.internal.MockitoRxMicroTestExtension;

/**
 * The module designed for test writing using the <a href="https://junit.org/junit5/">JUnit 5</a> and
 * <a href="https://site.mockito.org/">Mockito</a> frameworks.
 *
 * <p>
 * This module follows the next package structure rules:
 * <ul>
 *     <li>
 *         {@code io.rxmicro.test.mockito.junit} - is root module package that contains:
 *         <ul>
 *             <li>
 *                 {@code internal} - is sub package with classes for current module use only.
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
module rxmicro.test.mockito.junit {
    requires transitive rxmicro.test.junit;
    requires transitive rxmicro.test.mockito;

    requires transitive org.mockito.junit.jupiter;

    exports io.rxmicro.test.mockito.junit;

    provides RxMicroTestExtension with MockitoRxMicroTestExtension;
}
