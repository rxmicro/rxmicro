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

package io.rxmicro.annotation.processor.common;

import io.rxmicro.annotation.processor.common.component.impl.AbstractProcessorComponent;
import io.rxmicro.annotation.processor.common.model.DocumentationType;

/**
 * Supported options by {@code RxMicro Annotation Processor}.
 *
 * @author nedis
 * @since 0.1
 */
public final class SupportedOptions {

    /**
     * Maximum stack size for recursive invocations when analyzing models containing JSON nested objects.
     */
    public static final String RX_MICRO_MAX_JSON_NESTED_DEPTH = "RX_MICRO_MAX_JSON_NESTED_DEPTH";

    /**
     * Default stack size.
     */
    public static final int RX_MICRO_MAX_JSON_NESTED_DEPTH_DEFAULT_VALUE = 20;

    /**
     * {@code RxMicro Annotation Processor} logging level.
     *
     * OFF
     * INFO
     * DEBUG
     * other == OFF
     *
     * @see io.rxmicro.annotation.processor.common.component.impl.AbstractProcessorComponent.Level
     */
    public static final String RX_MICRO_LOG_LEVEL = "RX_MICRO_LOG_LEVEL";

    /**
     * Default logging level.
     */
    public static final String RX_MICRO_LOG_LEVEL_DEFAULT_VALUE = AbstractProcessorComponent.Level.INFO.name();

    /**
     * The resulting directory for generated documentation.
     *
     * Default value is defined at {@link DocumentationType} enum
     *
     * @see DocumentationType
     */
    public static final String RX_MICRO_DOC_DESTINATION_DIR = "RX_MICRO_DOC_DESTINATION_DIR";

    /**
     * The unnamed module support for a microservice project.
     */
    public static final String RX_MICRO_BUILD_UNNAMED_MODULE = "RX_MICRO_BUILD_UNNAMED_MODULE";

    /**
     * The unnamed module support is disabled by default.
     */
    public static final boolean RX_MICRO_BUILD_UNNAMED_MODULE_DEFAULT_VALUE = false;

    /**
     * Strict mode option.
     *
     * <p>
     * This option allows validating methods body.
     * Validation of method body is not public API, so sometimes RxMicro Annotation Processor can analyze your code incorrectly.
     * For such cases it is recommended to disable strict mode.
     */
    public static final String RX_MICRO_STRICT_MODE = "RX_MICRO_STRICT_MODE";

    /**
     * String mode is available by default
     */
    public static final boolean RX_MICRO_STRICT_MODE_DEFAULT_VALUE = true;

    private SupportedOptions() {
    }
}
