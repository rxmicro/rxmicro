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

package io.rxmicro.annotation.processor.config;

import io.rxmicro.reflection.Reflections;

import java.util.Arrays;
import java.util.Set;

import static java.util.stream.Collectors.toUnmodifiableSet;

/**
 * Supported options by the {@code RxMicro Annotation Processor}.
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
     * The {@code RxMicro Annotation Processor} logging level.
     *
     * @see LogLevel
     */
    public static final String RX_MICRO_LOG_LEVEL = "RX_MICRO_LOG_LEVEL";

    /**
     * Default logging level.
     */
    public static final LogLevel RX_MICRO_LOG_LEVEL_DEFAULT_VALUE = LogLevel.INFO;

    /**
     * The resulting directory for generated documentation.
     *
     * <p>
     * Default value is defined at {@link DocumentationType} enum
     *
     * @see DocumentationType
     */
    public static final String RX_MICRO_DOC_DESTINATION_DIR = "RX_MICRO_DOC_DESTINATION_DIR";

    /**
     * This option allows analyzing parent {@code pom.xml} if child {@code pom.xml} does not contain required property.
     */
    public static final String RX_MICRO_DOC_ANALYZE_PARENT_POM = "RX_MICRO_DOC_ANALYZE_PARENT_POM";

    /**
     * Analyzing of parent {@code pom.xml} is enabled by default.
     */
    public static final boolean RX_MICRO_DOC_ANALYZE_PARENT_POM_DEFAULT_VALUE = true;

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
     * The RxMicro Annotation Processor uses additional validation rules if strict mode is activated.
     */
    public static final String RX_MICRO_STRICT_MODE = "RX_MICRO_STRICT_MODE";

    /**
     * String mode is disabled by default.
     *
     * <p>
     * This behaviour allows starting to write code using the RxMicro framework as quick as possible.
     * But for production code it is strong recommended to enable strict mode.
     */
    public static final boolean RX_MICRO_STRICT_MODE_DEFAULT_VALUE = false;

    /**
     * Indicates that the current project is a library one.
     */
    public static final String RX_MICRO_LIBRARY_MODULE = "RX_MICRO_LIBRARY_MODULE";

    /**
     * Library module is disabled by default.
     */
    public static final boolean RX_MICRO_LIBRARY_MODULE_DEFAULT_VALUE = false;

    /**
     * Contains all supported options.
     */
    public static final Set<String> ALL_SUPPORTED_OPTIONS = Arrays.stream(SupportedOptions.class.getDeclaredFields())
            .filter(field -> field.getType() == String.class && !field.getName().endsWith("_DEFAULT_VALUE"))
            .map(field -> (String) Reflections.getFieldValue(SupportedOptions.class, field))
            .collect(toUnmodifiableSet());

    private SupportedOptions() {
    }
}
