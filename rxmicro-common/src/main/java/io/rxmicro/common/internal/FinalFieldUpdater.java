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

package io.rxmicro.common.internal;

import io.rxmicro.common.CheckedWrapperException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static io.rxmicro.common.util.Formats.format;

/**
 * @author nedis
 * @since 0.3
 */
public final class FinalFieldUpdater {

    public static void setFinalFieldValue(final Object instance,
                                          final Field field,
                                          final Object value) {
        try {
            removeFinalModifier(field);
            field.set(instance, value);
        } catch (final IllegalAccessException ex) {
            throw new CheckedWrapperException(ex);
        } catch (final NoSuchFieldException ignore) {
            // Read more: https://bugs.openjdk.java.net/browse/JDK-8217225
            throw new IllegalArgumentException(format(
                    "Can't update final field: ?. Read more: https://bugs.openjdk.java.net/browse/JDK-8217225",
                    field
            ));
        }
    }

    private static void removeFinalModifier(final Field field) throws IllegalAccessException, NoSuchFieldException {
        final Field modifiersField = getFieldModifiers();
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
    }

    // Some workaround to remove final modifier
    private static Field getFieldModifiers() throws NoSuchFieldException, IllegalAccessException {
        try {
            return Field.class.getDeclaredField("modifiers");
        } catch (final NoSuchFieldException ex) {
            try {
                final Method getDeclaredFields0 = Class.class.getDeclaredMethod("getDeclaredFields0", boolean.class);
                getDeclaredFields0.setAccessible(true);
                final Field[] fields = (Field[]) getDeclaredFields0.invoke(Field.class, false);
                getDeclaredFields0.setAccessible(false);
                for (final Field field : fields) {
                    if ("modifiers".equals(field.getName())) {
                        return field;
                    }
                }
                throw ex;
            } catch (final NoSuchMethodException | InvocationTargetException suppressedEx) {
                ex.addSuppressed(suppressedEx);
                throw ex;
            }
        }
    }

    private FinalFieldUpdater() {
    }
}
