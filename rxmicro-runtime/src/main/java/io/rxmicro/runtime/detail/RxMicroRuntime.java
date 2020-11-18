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

package io.rxmicro.runtime.detail;

import io.rxmicro.runtime.internal.RuntimeVersion;

import static io.rxmicro.common.CommonConstants.RX_MICRO_RUNTIME_STRICT_MODE;
import static io.rxmicro.logger.LoggerFactory.getLogger;
import static io.rxmicro.runtime.internal.RuntimeVersion.setRxMicroVersion;

/**
 * Used by generated code that created by the {@code RxMicro Annotation Processor}.
 *
 * @author nedis
 * @hidden
 * @since 0.1
 */
public final class RxMicroRuntime {

    public static final String ENTRY_POINT_PACKAGE = "rxmicro";

    static {
        setRxMicroVersion();
        if (Boolean.parseBoolean(System.getenv(RX_MICRO_RUNTIME_STRICT_MODE))) {
            getLogger(RxMicroRuntime.class).info("!!! RxMicro Runtime Strict Mode is activated !!!");
        }
    }

    public static String getRxMicroVersion() {
        return RuntimeVersion.getRxMicroVersion();
    }

    private RxMicroRuntime() {
    }
}
