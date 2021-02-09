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

package io.rxmicro.model;

import io.rxmicro.common.CheckedWrapperException;

import java.lang.reflect.Field;
import java.lang.reflect.InaccessibleObjectException;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.reflection.Reflections.allFields;
import static io.rxmicro.reflection.Reflections.getFieldValue;
import static java.lang.reflect.Modifier.isStatic;

/**
 * Defines a basic model class that overrides {@link Object#toString()} method that reads all model field values using {@code reflection}.
 *
 * @author nedis
 * @since 0.7
 */
public class BaseModel {

    /**
     * This is basic class designed for extension only.
     */
    protected BaseModel() {
        // This is basic class designed for extension only.
    }

    @Override
    public String toString() {
        final Predicate<Field> modelFieldsPredicate = field -> !isStatic(field.getModifiers());
        try {
            return getClass().getSimpleName() + '{' +
                    allFields(getClass(), modelFieldsPredicate).stream()
                            .map(field -> new Pair(field.getName(), getFieldValue(this, field)))
                            .map(e -> format(
                                    "?=?",
                                    e.fieldName,
                                    e.fieldValue instanceof String ? '\'' + e.fieldValue.toString() + '\'' : e.fieldValue
                            ))
                            .collect(Collectors.joining(", ")) +
                    '}';
        } catch (final CheckedWrapperException |
                InaccessibleObjectException |
                SecurityException |
                IllegalArgumentException |
                ExceptionInInitializerError ex) {
            return getClass().getSimpleName() + "{Can't read field data: " + ex.getMessage() + '}';
        }
    }

    /**
     * This is analog of {@link java.util.Map.Entry} that supports {@code null} values.
     *
     * @author nedis
     * @since 0.7.2
     */
    private static final class Pair {

        private final String fieldName;

        private final Object fieldValue;

        private Pair(final String fieldName,
                     final Object fieldValue) {
            this.fieldName = fieldName;
            this.fieldValue = fieldValue;
        }
    }
}
