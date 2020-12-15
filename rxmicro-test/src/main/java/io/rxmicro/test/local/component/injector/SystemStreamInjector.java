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

package io.rxmicro.test.local.component.injector;

import io.rxmicro.test.SystemErr;
import io.rxmicro.test.SystemOut;
import io.rxmicro.test.internal.SystemStream;
import io.rxmicro.test.internal.SystemStreamImpl;
import io.rxmicro.test.local.InvalidTestConfigException;

import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.List;

import static io.rxmicro.common.util.Reflections.getFieldValue;
import static io.rxmicro.common.util.Reflections.setFieldValue;

/**
 * @author nedis
 * @since 0.1
 */
public final class SystemStreamInjector {

    private static final PrintStream ORIGINAL_SYSTEM_OUT = System.out;

    private static final PrintStream ORIGINAL_SYSTEM_ERR = System.err;

    private final Field systemOutField;

    private final Field systemErrField;

    SystemStreamInjector(final Field systemOutField,
                         final Field systemErrField) {
        this.systemOutField = systemOutField;
        this.systemErrField = systemErrField;
    }

    public void injectIfFound(final List<Object> testInstances) {
        if (systemOutField != null) {
            System.setOut(createAndInject(testInstances, SystemOut.class, systemOutField, ORIGINAL_SYSTEM_OUT).getPrintStream());
        }
        if (systemErrField != null) {
            System.setErr(createAndInject(testInstances, SystemErr.class, systemErrField, ORIGINAL_SYSTEM_ERR).getPrintStream());
        }
    }

    private SystemStreamImpl createAndInject(final List<Object> testInstances,
                                             final Class<? extends SystemStream> systemStreamClass,
                                             final Field field,
                                             final PrintStream originalStream) {
        if (getFieldValue(testInstances, field) != null) {
            throw new InvalidTestConfigException(
                    "Field with type '?' could not be initialized. Remove initialize statement!",
                    systemStreamClass.getSimpleName()
            );
        }
        final SystemStreamImpl systemStreamImpl = new SystemStreamImpl(originalStream);
        setFieldValue(testInstances, field, systemStreamImpl);
        return systemStreamImpl;
    }

    public void resetIfNecessary() {
        if (systemOutField != null) {
            System.setOut(ORIGINAL_SYSTEM_OUT);
        }
        if (systemErrField != null) {
            System.setErr(ORIGINAL_SYSTEM_ERR);
        }
    }
}
