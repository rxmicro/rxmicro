/*
 * Copyright 2019 https://rxmicro.io
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

import io.rxmicro.test.SystemOut;
import io.rxmicro.test.local.InvalidTestConfigException;
import io.rxmicro.test.local.SystemOutImpl;

import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.List;

import static io.rxmicro.tool.common.Reflections.getFieldValue;
import static io.rxmicro.tool.common.Reflections.setFieldValue;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class SystemOutInjector {

    private static final PrintStream ORIGINAL_OUT = System.out;

    private final Field systemOutField;

    SystemOutInjector(final Field systemOutField) {
        this.systemOutField = systemOutField;
    }

    public void injectIfFound(final List<Object> testInstances) {
        if (systemOutField != null) {
            final SystemOutImpl systemOutImpl = new SystemOutImpl();
            if (getFieldValue(testInstances, this.systemOutField) != null) {
                throw new InvalidTestConfigException(
                        "Field with type '?' could not be initialized. Remove initialize statement!",
                        SystemOut.class.getSimpleName()
                );
            }
            setFieldValue(testInstances, this.systemOutField, systemOutImpl);
            System.setOut(systemOutImpl.getPrintStream());
        }
    }

    public void resetIfNecessary() {
        if (systemOutField != null) {
            System.setOut(ORIGINAL_OUT);
        }
    }
}
