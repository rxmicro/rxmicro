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

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class SupportedOptions {

    public static final String RX_MICRO_MAX_JSON_NESTED_DEPTH = "RX_MICRO_MAX_JSON_NESTED_DEPTH";

    public static final int RX_MICRO_MAX_JSON_NESTED_DEPTH_OPTION_DEFAULT_VALUE = 20;

    /**
     * OFF
     * INFO
     * DEBUG
     * other == OFF
     *
     * @see io.rxmicro.annotation.processor.common.component.impl.AbstractProcessorComponent.Level
     */
    public static final String RX_MICRO_LOG_LEVEL = "RX_MICRO_LOG_LEVEL";

    public static final String RX_MICRO_LOG_LEVEL_DEFAULT_VALUE = AbstractProcessorComponent.Level.INFO.name();

    /**
     * @see io.rxmicro.annotation.processor.common.model.DocumentationType
     */
    public static final String RX_MICRO_DOC_DESTINATION_DIR = "RX_MICRO_DOC_DESTINATION_DIR";

    public static final String RX_MICRO_BUILD_UNNAMED_MODULE = "RX_MICRO_BUILD_UNNAMED_MODULE";

    public static final boolean RX_MICRO_BUILD_UNNAMED_MODULE_DEFAULT_VALUE = false;

    private SupportedOptions() {
    }
}
