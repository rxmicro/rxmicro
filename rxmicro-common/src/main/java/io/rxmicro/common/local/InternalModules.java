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

package io.rxmicro.common.local;

import static io.rxmicro.common.internal.InternalModuleConstants.EXACT_MODULE_NAMES;
import static io.rxmicro.common.internal.InternalModuleConstants.START_WITH_MODULE_NAMES;

/**
 * @author nedis
 * @since 0.10
 */
public final class InternalModules {

    public static boolean isInternalModule(final String moduleName) {
        return EXACT_MODULE_NAMES.contains(moduleName) ||
                START_WITH_MODULE_NAMES.stream().anyMatch(moduleName::startsWith);
    }

    private InternalModules() {
    }
}
