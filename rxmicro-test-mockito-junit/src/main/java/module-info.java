/*
 * Copyright (c) 2020. http://rxmicro.io
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

import io.rxmicro.test.local.component.TestExtension;
import io.rxmicro.test.mockito.junit.internal.MockitoTestExtension;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
@SuppressWarnings("JavaRequiresAutoModule")
module rxmicro.test.mockito.junit {
    requires transitive rxmicro.test.junit;
    requires transitive rxmicro.test.mockito;
    requires transitive mockito.junit.jupiter;

    exports io.rxmicro.test.mockito.junit;

    provides TestExtension with MockitoTestExtension;
}