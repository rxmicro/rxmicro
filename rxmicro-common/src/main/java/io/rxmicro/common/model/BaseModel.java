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

package io.rxmicro.common.model;

import io.rxmicro.common.CheckedWrapperException;

import java.lang.reflect.InaccessibleObjectException;
import java.util.stream.Collectors;

import static io.rxmicro.common.util.Reflections.allFields;
import static io.rxmicro.common.util.Reflections.getFieldValue;
import static java.util.Map.entry;

/**
 * Defines a basic model class that overrides {@link Object#toString()} method that read all model field values using reflection.
 *
 * @author nedis
 * @since 0.7
 */
public class BaseModel {

    /**
     * This is basic class designed for extension only.
     */
    protected BaseModel() {
    }

    @Override
    public String toString() {
        try {
            return getClass().getSimpleName() + '{' +
                    allFields(getClass(), field -> true).stream()
                            .map(field -> entry(field.getName(), getFieldValue(this, field)))
                            .map(e -> e.getKey() + '=' + e.getValue())
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
}
