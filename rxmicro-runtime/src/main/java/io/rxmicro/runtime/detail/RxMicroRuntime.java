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

import static io.rxmicro.common.CommonConstants.RX_MICRO_ANNOTATION_PROCESSOR_RUNTIME;
import static io.rxmicro.common.local.RxMicroEnvironment.isRuntimeStrictModeEnabled;
import static io.rxmicro.common.util.Formats.format;
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

    public static final String UNNAMED_MODULE_NAME = "unnamed";

    private static final String ENTRY_POINT_PACKAGE_PREFIX = "rxmicro";

    private static final String ENTRY_POINT_TEST_PACKAGE_PREFIX = ENTRY_POINT_PACKAGE_PREFIX + ".test";

    static {
        setRxMicroVersion();
        if (isRuntimeStrictModeEnabled() && !Boolean.getBoolean(RX_MICRO_ANNOTATION_PROCESSOR_RUNTIME)) {
            getLogger(RxMicroRuntime.class).info("!!! RxMicro Runtime Strict Mode is activated !!!");
        }
    }

    public static String getRxMicroVersion() {
        return RuntimeVersion.getRxMicroVersion();
    }

    public static String getEntryPointPackage(final String moduleName) {
        return ENTRY_POINT_PACKAGE_PREFIX + "." + moduleName;
    }

    public static String getUnnamedModuleEntryPointPackage() {
        return getEntryPointPackage(UNNAMED_MODULE_NAME);
    }

    public static String getEntryPointFullClassName(final Module module,
                                                    final String simpleClassName) {
        return format("?.?.?", ENTRY_POINT_PACKAGE_PREFIX, getModuleName(module), simpleClassName);
    }

    public static String getEntryPointTestFullClassName(final String simpleClassName) {
        return format("?.?", ENTRY_POINT_TEST_PACKAGE_PREFIX, simpleClassName);
    }

    public static String getEntryPointTestPackage() {
        return ENTRY_POINT_TEST_PACKAGE_PREFIX;
    }

    private static String getModuleName(final Module module) {
        return module.isNamed() ? module.getName() : UNNAMED_MODULE_NAME;
    }

    private RxMicroRuntime() {
    }
}
