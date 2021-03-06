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

package io.rxmicro.test.internal.validator;

import io.rxmicro.test.local.InvalidTestConfigException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.rxmicro.common.local.DeniedPackages.isDeniedPackage;
import static io.rxmicro.common.util.Formats.format;
import static java.lang.reflect.Modifier.isStatic;

/**
 * @author nedis
 * @since 0.1
 */
public abstract class FieldValidator {

    public void validate(final List<Field> fields) {
        for (final Field field : fields) {
            validate(field);
        }
    }

    protected abstract void validate(Field field);

    protected final String fieldNamePrefix(final Field field) {
        return format(
                "'?.?' field",
                field.getDeclaringClass().getSimpleName(),
                field.getName()
        );
    }

    protected final void validateThatFieldIsNotFinal(final Field field) {
        if (Modifier.isFinal(field.getModifiers())) {
            throw new InvalidTestConfigException(
                    "? could not be a final. Remove 'final' modifier!",
                    fieldNamePrefix(field)
            );
        }
    }

    protected final void validateThatFieldIsNotStatic(final Field field) {
        if (isStatic(field.getModifiers())) {
            throw new InvalidTestConfigException(
                    "? could not be a static! Remove 'static' modifier!",
                    fieldNamePrefix(field)
            );
        }
    }

    protected final void validateThatFieldWithGivenTypeIsSinglePerClass(final List<Field> fields,
                                                                        final Class<?> verifiedType) {
        if (fields.size() > 1) {
            throw new InvalidTestConfigException(
                    "? is redundant, " +
                            "because it is expected only one field with '?' type per test class. " +
                            "Remove this field!",
                    fieldNamePrefix(fields.get(fields.size() - 1)),
                    verifiedType.getName()
            );
        }
    }

    protected final void validateThatFieldWithGivenTypeIsSinglePerClass(final List<Field> fields) {
        final Map<Class<?>, List<Field>> map = new HashMap<>();
        for (final Field field : fields) {
            map.computeIfAbsent(field.getType(), v -> new ArrayList<>()).add(field);
        }
        for (final Map.Entry<Class<?>, List<Field>> entry : map.entrySet()) {
            if (entry.getValue().size() > 1) {
                throw new InvalidTestConfigException(
                        "? is redundant, " +
                                "because it is expected only one field with '?' type per test class. " +
                                "Remove this field!",
                        fieldNamePrefix(entry.getValue().get(entry.getValue().size() - 1)),
                        entry.getKey().getName()
                );
            }
        }
    }

    @SafeVarargs
    protected final void validateThatFieldIsAnnotatedOnlyBySupportedOnes(final Field field,
                                                                         final Class<? extends Annotation>... supportedAnnotations) {
        final Set<Class<? extends Annotation>> supportedAnnotationSet = Set.of(supportedAnnotations);
        for (final Annotation annotation : field.getAnnotations()) {
            final Class<? extends Annotation> annotationClass = annotation.annotationType();
            if (isDeniedPackage(annotationClass.getPackageName()) &&
                    !annotationClass.getPackageName().startsWith("org.mockito") &&
                    !supportedAnnotationSet.contains(annotationClass)) {
                throw new InvalidTestConfigException(
                        "? is annotated by unsupported '@?' annotation. " +
                                "Remove the '@?' annotation!",
                        fieldNamePrefix(field),
                        annotationClass.getName(),
                        annotationClass.getName()
                );
            }
        }
    }

    protected final void validateThatFieldAnnotatedByRequiredAnnotation(final Field field,
                                                                        final Class<? extends Annotation> annotationClass) {
        if (!field.isAnnotationPresent(annotationClass)) {
            throw new InvalidTestConfigException(
                    "? is not annotated by required '@?' annotation. " +
                            "Add the missing annotation!",
                    fieldNamePrefix(field),
                    annotationClass.getName()
            );
        }
    }
}
