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
 * The common constants for the RxMicro framework
 *
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class Constants {

    /**
     * The RxMicro framework name
     */
    public static final String RX_MICRO_FRAMEWORK_NAME = "RxMicro";

    private Constants() {
    }

    /**
     * This class describes the constants for virtual descriptor.
     * <p>
     * The RxMicro framework supports the {@code unnamed module} mode for interaction
     * with external frameworks and libs that don't support {@code JPMS}.
     * <p>
     * But some RxMicro modules require the common configuration with RxMicro Annotations,
     * which annotate the {@code module-info.java} module descriptor.
     * But for the {@code unnamed module}, the {@code module-info.java} module descriptor does not exist!
     * <p>
     * In such cases a virtual descriptor must be used.
     *
     * @author nedis
     * @link https://rxmicro.io
     * @since 0.2
     */
    public static final class VirtualModuleInfoConstants {

        /**
         * The virtual descriptor annotation name
         */
        public static final String RX_MICRO_VIRTUAL_MODULE_INFO_ANNOTATION_NAME = "ModuleInfo";

        /**
         * The static final field that describes the module name for the virtual descriptor
         */
        public static final String RX_MICRO_VIRTUAL_MODULE_INFO_NAME = "NAME";

        /**
         * The default module name for the virtual descriptor
         */
        public static final String RX_MICRO_VIRTUAL_MODULE_INFO_DEFAULT_NAME = "virtual.module";

        private VirtualModuleInfoConstants() {
        }
    }
}
