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

package io.rxmicro.test.local;

import io.rxmicro.common.CheckedWrapperException;
import io.rxmicro.common.model.UnNamedModuleFixer;
import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerFactory;

import java.util.ServiceLoader;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.runtime.detail.RxMicroRuntime.ENTRY_POINT_PACKAGE;
import static io.rxmicro.runtime.local.Instances.instantiate;
import static io.rxmicro.tool.common.TestFixers.COMPONENT_TEST_FIXER;
import static io.rxmicro.tool.common.TestFixers.INTEGRATION_TEST_FIXER;
import static io.rxmicro.tool.common.TestFixers.REST_BASED_MICRO_SERVICE_TEST_FIXER;

/**
 * @author nedis
 * @since 0.1
 */
public final class UnNamedModuleFixers {

    private static final Logger LOGGER = LoggerFactory.getLogger(UnNamedModuleFixers.class);

    public static void componentTestsFix() {
        testFix(COMPONENT_TEST_FIXER);
    }

    public static void restBasedMicroServiceTestsFix() {
        testFix(REST_BASED_MICRO_SERVICE_TEST_FIXER);
    }

    public static void integrationTestsFix() {
        testFix(INTEGRATION_TEST_FIXER);
    }

    private static void testFix(final String testFixSimpleClassName) {
        commonFix();
        try {
            instantiate(format("?.?", ENTRY_POINT_PACKAGE, testFixSimpleClassName));
        } catch (final CheckedWrapperException ex) {
            if (!ex.isCause(ClassNotFoundException.class)) {
                LOGGER.error(ex, "Can't instantiate a `?.?` class: ?", ENTRY_POINT_PACKAGE, testFixSimpleClassName, ex.getMessage());
            }
        }
    }

    private static void commonFix() {
        final Module unNamedModule = UnNamedModuleFixers.class.getClassLoader().getUnnamedModule();
        final ServiceLoader<UnNamedModuleFixer> loader = ServiceLoader.load(UnNamedModuleFixer.class);
        for (final UnNamedModuleFixer namedModule : loader) {
            namedModule.fix(unNamedModule);
        }
    }

    private UnNamedModuleFixers() {
    }
}
