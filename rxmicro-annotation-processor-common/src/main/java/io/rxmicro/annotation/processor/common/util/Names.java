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

package io.rxmicro.annotation.processor.common.util;

import io.rxmicro.annotation.processor.common.model.error.InternalErrorException;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import java.util.List;

import static io.rxmicro.annotation.processor.common.util.ProcessingEnvironmentHelper.getTypes;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class Names {

    private static final String PRIMITIVE_NOT_ALLOWED_HERE = "Primitive not allowed here";

    public static String getPackageName(final TypeMirror type) {
        if (type.getKind().isPrimitive()) {
            throw new InternalErrorException(PRIMITIVE_NOT_ALLOWED_HERE);
        }
        return getPackageName(getTypeWithoutGeneric(type));
    }

    public static String getPackageName(final TypeElement typeElement) {
        return getPackageName(typeElement.getQualifiedName().toString());
    }

    public static String getPackageName(final String className) {
        final int lastDot = className.lastIndexOf('.');
        if (lastDot == -1) {
            throw new InternalErrorException("Package <default> not allowed");
        }
        return className.substring(0, lastDot);
    }

    public static String getSimpleName(final TypeMirror type) {
        if (type.getKind().isPrimitive()) {
            throw new InternalErrorException(PRIMITIVE_NOT_ALLOWED_HERE);
        }
        final StringBuilder nameBuilder = new StringBuilder();
        populate(nameBuilder, type);
        return nameBuilder.toString();
    }

    private static void populate(final StringBuilder nameBuilder,
                                 final TypeMirror type) {
        nameBuilder.append(getSimpleName(getTypes().erasure(type).toString()));
        if (type instanceof DeclaredType) {
            final List<? extends TypeMirror> typeArguments = ((DeclaredType) type).getTypeArguments();
            if (!typeArguments.isEmpty()) {
                nameBuilder.append('<');
            }
            for (int i = 0; i < typeArguments.size(); i++) {
                final TypeMirror typeArgument = typeArguments.get(i);
                populate(nameBuilder, typeArgument);
                if (i != typeArguments.size() - 1) {
                    nameBuilder.append(", ");
                }
            }
            if (!typeArguments.isEmpty()) {
                nameBuilder.append('>');
            }
        }
    }

    public static String getSimpleName(final TypeElement typeElement) {
        return getSimpleName(typeElement.getSimpleName().toString());
    }

    public static String getSimpleName(final String fullClassName) {
        final int index = fullClassName.lastIndexOf('.');
        return index > 0 ? fullClassName.substring(index + 1) : fullClassName;
    }

    public static String getDefaultVarName(final String fullClassName) {
        final String simpleName = getSimpleName(fullClassName);
        if (simpleName.length() > 1) {
            return Character.toLowerCase(simpleName.charAt(0)) + simpleName.substring(1);
        } else {
            return String.valueOf(Character.toLowerCase(simpleName.charAt(0)));
        }
    }

    public static String getGenericType(final TypeMirror type) {
        if (type.getKind().isPrimitive()) {
            throw new InternalErrorException(PRIMITIVE_NOT_ALLOWED_HERE);
        }
        final String string = type.toString();
        return string.substring(string.indexOf('<') + 1, string.lastIndexOf('>'));
    }

    public static String getTypeWithoutGeneric(final TypeMirror type) {
        if (type.getKind().isPrimitive()) {
            throw new InternalErrorException(PRIMITIVE_NOT_ALLOWED_HERE);
        }
        final String string = type.toString();
        final int index = string.indexOf('<');
        return index > 0 ? string.substring(0, index) : string;
    }

    public static String getTypeWithoutGeneric(final String type) {
        final int index = type.indexOf('<');
        return index > 0 ? type.substring(0, index) : type;
    }

    private Names() {
    }
}
