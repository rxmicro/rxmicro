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

package io.rxmicro.annotation.processor.common.model.method;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static io.rxmicro.annotation.processor.common.util.Elements.isGenericType;
import static io.rxmicro.annotation.processor.common.util.Names.getSimpleName;
import static io.rxmicro.annotation.processor.common.util.ProcessingEnvironmentHelper.getTypes;
import static io.rxmicro.annotation.processor.common.util.validators.TypeValidators.validateGenericType;
import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class MethodParameter {

    private final VariableElement variableElement;

    private final String simpleType;

    private final Set<String> imports;

    public MethodParameter(final ExecutableElement method,
                           final VariableElement variableElement) {
        this.variableElement = require(variableElement);
        final StringBuilder simpleTypeBuilder = new StringBuilder();
        this.imports = extractTypes(simpleTypeBuilder, require(method), variableElement.asType());
        this.simpleType = simpleTypeBuilder.toString();
    }

    private Set<String> extractTypes(final StringBuilder simpleTypeBuilder,
                                     final ExecutableElement method,
                                     final TypeMirror type) {
        final Set<String> set = new HashSet<>();
        if (isGenericType(type)) {
            validateGenericType(method, type, "Invalid method parameter type");
            extractGenerics(set, simpleTypeBuilder, type);
        } else {
            if (!type.getKind().isPrimitive()) {
                set.add(type.toString());
                simpleTypeBuilder.append(getSimpleName(type));
            } else {
                simpleTypeBuilder.append(type);
            }
        }
        return set;
    }

    private void extractGenerics(final Set<String> set,
                                 final StringBuilder simpleTypeBuilder,
                                 final TypeMirror type) {
        final TypeMirror containerType = getTypes().erasure(type);
        if (!containerType.getKind().isPrimitive()) {
            set.add(containerType.toString());
        }
        simpleTypeBuilder.append(getSimpleName(containerType)).append('<');
        final List<? extends TypeMirror> typeArguments = ((DeclaredType) type).getTypeArguments();
        for (int i = 0; i < typeArguments.size(); i++) {
            final TypeMirror item = typeArguments.get(i);
            if (isGenericType(item)) {
                extractGenerics(set, simpleTypeBuilder, item);
            } else {
                set.add(item.toString());
                simpleTypeBuilder.append(getSimpleName(item));
            }
            if (i != typeArguments.size() - 1) {
                simpleTypeBuilder.append(',');
            }
        }
        simpleTypeBuilder.append('>');
    }

    public String getName() {
        return variableElement.getSimpleName().toString();
    }

    public String getSimpleType() {
        return simpleType;
    }

    public TypeMirror getType() {
        return variableElement.asType();
    }

    public Set<String> getRequiredImports() {
        return imports;
    }

    @Override
    public String toString() {
        return getSimpleType() + " " + getName();
    }
}
