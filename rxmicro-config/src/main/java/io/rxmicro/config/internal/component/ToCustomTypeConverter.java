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

package io.rxmicro.config.internal.component;

import io.rxmicro.config.ConfigException;
import io.rxmicro.reflection.Reflections;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Optional;

import static io.rxmicro.common.util.Strings.startsWith;

/**
 * @author nedis
 * @since 0.7.2
 */
public final class ToCustomTypeConverter {

    public Optional<Object> convertToCustomType(final Class<?> type,
                                                final String value) {
        return convertToCustomType(type.getName(), value);
    }

    public Optional<Object> convertToCustomType(final String destinationType,
                                                final String value) {
        if (startsWith(value, '@')) {
            final int delimiter = value.indexOf(':');
            if (delimiter != -1) {
                final String fullClassName = value.substring(1, delimiter);
                try {
                    final Class<?> fullClass = Class.forName(value.substring(1, delimiter));
                    final String constName = value.substring(delimiter + 1);
                    if (fullClass.isEnum()) {
                        return useEnumConstant(destinationType, fullClass, constName);
                    } else {
                        return usePublicStaticFinalConstant(destinationType, fullClass, constName);
                    }
                } catch (final ClassNotFoundException ignored) {
                    throw new ConfigException(
                            "Can't convert '?' to '?', because '?' class not defined!",
                            value, destinationType, fullClassName
                    );
                }
            }
        }
        return Optional.empty();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Optional<Object> useEnumConstant(final String destinationType,
                                             final Class<?> fullClass,
                                             final String constName) {
        try {
            return Optional.of(Enum.valueOf((Class<? extends Enum>) fullClass, constName));
        } catch (final IllegalArgumentException ignored) {
            throw new ConfigException(
                    "Can't convert '@?.?' to '?', because '?' ? does not contain '?' enum constant!",
                    fullClass.getName(), constName, destinationType, fullClass.getName(), getKind(fullClass), constName
            );
        }
    }

    private Optional<Object> usePublicStaticFinalConstant(final String destinationType,
                                                          final Class<?> fullClass,
                                                          final String constName) {
        try {
            final Field declaredField = fullClass.getDeclaredField(constName);
            if (!Modifier.isStatic(declaredField.getModifiers())) {
                throw new ConfigException(
                        "Can't convert '@?.?' to '?', because '?' field declared at '?' ? not static!",
                        fullClass.getName(), constName, destinationType, constName, fullClass.getName(), getKind(fullClass)
                );
            }
            if (!Modifier.isPublic(declaredField.getModifiers())) {
                throw new ConfigException(
                        "Can't convert '@?.?' to '?', because '?' field declared at '?' ? not public!",
                        fullClass.getName(), constName, destinationType, constName, fullClass.getName(), getKind(fullClass)
                );
            }
            if (!Modifier.isFinal(declaredField.getModifiers())) {
                throw new ConfigException(
                        "Can't convert '@?.?' to '?', because '?' field declared at '?' ? not final!",
                        fullClass.getName(), constName, destinationType, constName, fullClass.getName(), getKind(fullClass)
                );
            }
            return Optional.of(Reflections.getFieldValue((Object) null, declaredField));
        } catch (final NoSuchFieldException ignored) {
            throw new ConfigException(
                    "Can't convert '@?.?' to '?', because '?' ? does not contain '?' public static final field!",
                    fullClass.getName(), constName, destinationType, fullClass.getName(), getKind(fullClass), constName
            );
        }
    }

    private String getKind(final Class<?> fullClass) {
        if (fullClass.isEnum()) {
            return "enum";
        } else if (fullClass.isAnnotation()) {
            return "annotation";
        } else if (fullClass.isInterface()) {
            return "interface";
        } else {
            return "class";
        }
    }
}
