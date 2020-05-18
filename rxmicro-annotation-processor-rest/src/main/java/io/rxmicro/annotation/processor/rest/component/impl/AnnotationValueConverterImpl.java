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

package io.rxmicro.annotation.processor.rest.component.impl;

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.rest.component.AnnotationValueConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;

import static io.rxmicro.annotation.processor.common.util.Names.getSimpleName;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Strings.escapeString;

/**
 * @author nedis
 * @since 0.2
 */
@Singleton
public final class AnnotationValueConverterImpl implements AnnotationValueConverter {

    private final Map<Class<?>, Function<Object, String>> primitiveToStringMap = Map.of(
            Byte.class, v -> "(byte)" + v,
            Short.class, v -> "(short)" + v,
            Long.class, v -> v + "L",
            Float.class, v -> "(float)" + v,
            Character.class, v -> format("'?'", v)
    );

    /*
     * https://docs.oracle.com/javase/specs/jls/se8/html/jls-9.html#jls-9.6.1
     *
     * A primitive type:
     * - boolean, byte, short, int, long, float, double, char -> java.lang.*
     * - String -> java.lang.String
     * - Class or an invocation of Class -> com.sun.tools.javac.code.Type$ClassType
     * - An enum value -> com.sun.tools.javac.code.Symbol$VarSymbol
     * - An annotation type --> com.sun.tools.javac.code.Attribute$Compound
     * - An array type whose component type is one of the preceding types -> com.sun.tools.javac.util.List
     */
    @Override
    public String convert(final Element owner,
                          final ClassHeader.Builder classHeaderBuilder,
                          final Object value) {
        return convert(owner, classHeaderBuilder, value, true);
    }

    @SuppressWarnings("unchecked")
    private String convert(final Element owner,
                           final ClassHeader.Builder classHeaderBuilder,
                           final Object value,
                           final boolean supportArrays) {
        if (isPrimitiveParameter(value)) {
            return primitiveToString(value);
        } else if (isStringParameter(value)) {
            return format("\"?\"", escapeString(value.toString()));
        } else if (isClassParameter(value)) {
            final String fullClassName = value.toString();
            classHeaderBuilder.addImports(fullClassName);
            return getSimpleName(fullClassName) + ".class";
        } else if (isEnumConstantParameter(value)) {
            final VariableElement var = (VariableElement) value;
            final String fullClassName = var.asType().toString();
            classHeaderBuilder.addImports(fullClassName);
            return format("?.?", getSimpleName(fullClassName), var.getSimpleName().toString());
        } else if (isArrayParameter(value) && supportArrays) {
            return arrayToString(owner, classHeaderBuilder, (List<AnnotationValue>) value);
        } else {
            throw new InterruptProcessingException(
                    owner,
                    "Annotation value: ? (type=?) not supported!", value, value.getClass().getName()
            );
        }
    }

    private boolean isPrimitiveParameter(final Object value) {
        return value instanceof Number || value instanceof Boolean || value instanceof Character;
    }

    private boolean isStringParameter(final Object value) {
        return value instanceof String;
    }

    private boolean isClassParameter(final Object value) {
        return value instanceof DeclaredType;
    }

    private boolean isEnumConstantParameter(final Object value) {
        return value instanceof VariableElement;
    }

    private boolean isArrayParameter(final Object value) {
        return value instanceof List;
    }

    private String primitiveToString(final Object value) {
        return Optional.ofNullable(primitiveToStringMap.get(value.getClass()))
                .orElse(Object::toString)
                .apply(value);
    }

    /*
     * https://docs.oracle.com/javase/specs/jls/se8/html/jls-9.html#jls-9.6.1
     *
     * Array Element:
     * - primitive + String -> com.sun.tools.javac.code.Attribute$Constant
     * - Class or an invocation of Class -> com.sun.tools.javac.code.Attribute$Class
     * - An enum value -> com.sun.tools.javac.code.Attribute$Enum
     * - An annotation type --> com.sun.tools.javac.code.Attribute$Compound
     * - An array type whose component type is one of the preceding types -> com.sun.tools.javac.util.List
     */
    private String arrayToString(final Element owner,
                                 final ClassHeader.Builder classHeaderBuilder,
                                 final List<AnnotationValue> list) {
        classHeaderBuilder.addImports(List.class);
        final List<String> values = new ArrayList<>();
        for (final AnnotationValue item : list) {
            values.add(convert(owner, classHeaderBuilder, item.getValue(), false));
        }
        return format("List.of(?)", String.join(", ", values));
    }
}
