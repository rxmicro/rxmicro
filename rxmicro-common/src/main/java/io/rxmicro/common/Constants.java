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
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class Constants {

    public static final String RX_MICRO_FRAMEWORK_NAME = "RxMicro";

    private Constants() {
    }

    /**
     * @author nedis
     * @link https://rxmicro.io
     * @since 0.2
     */
    public static final class VirtualModuleInfo {

        public static final String RX_MICRO_VIRTUAL_MODULE_INFO_ANNOTATION_NAME = "ModuleInfo";

        public static final String RX_MICRO_VIRTUAL_MODULE_INFO_NAME = "NAME";

        public static final String RX_MICRO_VIRTUAL_MODULE_INFO_DEFAULT_NAME = "virtual.module";

        public static final String RX_MICRO_VIRTUAL_MODULE_INFO_ROOT_PACKAGE = "ROOT_PACKAGE";

        private VirtualModuleInfo () {
        }
    }
}
