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

package io.rxmicro.common;

/**
 * The common constants for the RxMicro framework.
 *
 * @author nedis
 * @since 0.1
 */
@SuppressWarnings("JavaDoc")
public final class CommonConstants {

    /**
     * The empty string constant.
     */
    public static final String EMPTY_STRING = "";

    /**
     * Represents a hostname that refers to the current computer used to access it.
     * <p>Read more here: <a href="https://en.wikipedia.org/wiki/Localhost">What is localhost?</a>
     */
    public static final String LOCALHOST = "localhost";

    /**
     * The reference to the {@link Module} object of the {@code rxmicro.common} module.
     */
    public static final Module RX_MICRO_COMMON_MODULE = CommonConstants.class.getModule();

    /**
     * The RxMicro framework name: {@value #RX_MICRO_FRAMEWORK_NAME}.
     */
    public static final String RX_MICRO_FRAMEWORK_NAME = "rx-micro";

    /**
     * The {@value #RX_MICRO_HOME_VARIABLE_NAME} environment variable.
     */
    public static final String RX_MICRO_HOME_VARIABLE_NAME = "RX_MICRO_HOME";

    /**
     * Nanos in 1 millis.
     */
    public static final int NANOS_IN_1_MILLIS = 1_000_000;

    /**
     * Nanos in 1 second.
     */
    public static final int NANOS_IN_1_SECOND = 1_000_000_000;

    /**
     * The environment variable (or Java system property) that activates additional check in runtime.
     *
     * <p>
     * The RxMicro team recommends activating strict mode for runtime only for {@code dev} and {@code staging} environments,
     * because additional checks decrease total performance of your application.
     *
     * <p>
     * This variable has {@code boolean} type.
     *
     * <p>
     * By default, the strict mode is disabled.
     */
    public static final String RX_MICRO_RUNTIME_STRICT_MODE = "RX_MICRO_RUNTIME_STRICT_MODE";

    /**
     * The Java system property that indicates that the current runtime is an annotation processor runtime.
     */
    public static final String RX_MICRO_ANNOTATION_PROCESSOR_RUNTIME = "RX_MICRO_ANNOTATION_PROCESSOR_RUNTIME";

    private CommonConstants() {
    }

    /**
     * This class describes the constants for virtual descriptor.
     *
     * <p>
     * The RxMicro framework supports the {@code unnamed module} mode for interaction
     * with external frameworks and libs that don't support {@code JPMS}.
     *
     * <p>
     * But some RxMicro modules require the common configuration with RxMicro Annotations,
     * which annotate the {@code module-info.java} module descriptor.
     * But for the {@code unnamed module}, the {@code module-info.java} module descriptor does not exist!
     *
     * <p>
     * In such cases a virtual descriptor must be used.
     *
     * @author nedis
     * @since 0.2
     */
    public static final class VirtualModuleInfoConstants {

        /**
         * The virtual descriptor annotation name: {@value #RX_MICRO_VIRTUAL_MODULE_INFO_ANNOTATION_NAME}.
         */
        public static final String RX_MICRO_VIRTUAL_MODULE_INFO_ANNOTATION_NAME = "ModuleInfo";

        /**
         * The static final field that describes the module name for the virtual descriptor: {@value #RX_MICRO_VIRTUAL_MODULE_INFO_NAME}.
         */
        public static final String RX_MICRO_VIRTUAL_MODULE_INFO_NAME = "NAME";

        /**
         * The default module name for the virtual descriptor: {@value #RX_MICRO_VIRTUAL_MODULE_INFO_DEFAULT_NAME}.
         */
        public static final String RX_MICRO_VIRTUAL_MODULE_INFO_DEFAULT_NAME = "virtual.module";

        private VirtualModuleInfoConstants() {
        }
    }
}
