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

import static io.rxmicro.common.CommonConstants.RX_MICRO_RUNTIME_STRICT_MODE;

/**
 * @author nedis
 * @since 0.7.2
 */
public final class RxMicroEnvironment {

    private static final boolean STRICT_MODE_ENABLED = Boolean.parseBoolean(System.getenv(RX_MICRO_RUNTIME_STRICT_MODE)) ||
            Boolean.parseBoolean(System.getProperty(RX_MICRO_RUNTIME_STRICT_MODE));

    public static boolean isRuntimeStrictModeEnabled() {
        return STRICT_MODE_ENABLED;
    }

    private RxMicroEnvironment() {
    }
}
