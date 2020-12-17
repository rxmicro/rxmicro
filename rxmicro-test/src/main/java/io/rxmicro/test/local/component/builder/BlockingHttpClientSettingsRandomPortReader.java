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

package io.rxmicro.test.local.component.builder;

import io.rxmicro.test.local.InvalidTestConfigException;

import java.lang.reflect.Field;
import java.util.Collections;

import static io.rxmicro.config.Networks.validatePort;
import static io.rxmicro.reflection.Reflections.getDeclaredField;
import static io.rxmicro.reflection.Reflections.getFieldValue;
import static java.lang.reflect.Modifier.isFinal;
import static java.lang.reflect.Modifier.isStatic;

/**
 * @author nedis
 * @since 0.1
 */
public final class BlockingHttpClientSettingsRandomPortReader {

    public int getPort(final Class<?> testClass,
                       final String portProvider) {
        final Field field = getDeclaredField(testClass, portProvider);
        validate(field);
        final int value = (int) getFieldValue(Collections.singletonList(null), field);
        return validatePort(value);
    }

    private void validate(final Field field) {
        if (!isStatic(field.getModifiers())) {
            throw new InvalidTestConfigException(
                    "?.? must be a static!",
                    field.getDeclaringClass().getSimpleName(), field.getName()
            );
        }
        if (!isFinal(field.getModifiers())) {
            throw new InvalidTestConfigException(
                    "?.? must be a final!",
                    field.getDeclaringClass().getSimpleName(), field.getName()
            );
        }
        if (field.getType() != Integer.TYPE) {
            throw new InvalidTestConfigException(
                    "?.? must have `int` type!",
                    field.getDeclaringClass().getSimpleName(), field.getName()
            );
        }
    }
}
