/*
 * Copyright 2019 http://rxmicro.io
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

import java.util.ServiceLoader;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.runtime.detail.Runtimes.ENTRY_POINT_PACKAGE;
import static io.rxmicro.runtime.local.Instances.instantiate;
import static io.rxmicro.tool.common.TestFixers.COMPONENT_TEST_FIXER;
import static io.rxmicro.tool.common.TestFixers.INTEGRATION_TEST_FIXER;
import static io.rxmicro.tool.common.TestFixers.REST_BASED_MICRO_SERVICE_TEST_FIXER;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class UnNamedModuleFixer {

    public static void commonFix() {
        final Module unNamedModule = UnNamedModuleFixer.class.getClassLoader().getUnnamedModule();
        final ServiceLoader<io.rxmicro.common.model.UnNamedModuleFixer> loader = ServiceLoader.load(io.rxmicro.common.model.UnNamedModuleFixer.class);
        for (final io.rxmicro.common.model.UnNamedModuleFixer namedModule : loader) {
            namedModule.fix(unNamedModule);
        }
    }

    public static void componentTestsFix() {
        commonFix();
        try {
            instantiate(format("?.?", ENTRY_POINT_PACKAGE, COMPONENT_TEST_FIXER));
        } catch (final CheckedWrapperException e) {
            if (!e.isCause(ClassNotFoundException.class)) {
                e.printStackTrace();
            }
        }
    }

    public static void restBasedMicroServiceTestsFix() {
        commonFix();
        try {
            instantiate(format("?.?", ENTRY_POINT_PACKAGE, REST_BASED_MICRO_SERVICE_TEST_FIXER));
        } catch (final CheckedWrapperException e) {
            if (!e.isCause(ClassNotFoundException.class)) {
                e.printStackTrace();
            }
        }
    }

    public static void integrationTestsFix() {
        commonFix();
        try {
            instantiate(format("?.?", ENTRY_POINT_PACKAGE, INTEGRATION_TEST_FIXER));
        } catch (final CheckedWrapperException e) {
            if (!e.isCause(ClassNotFoundException.class)) {
                e.printStackTrace();
            }
        }
    }

    private UnNamedModuleFixer() {
    }
}
