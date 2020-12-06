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

package io.rxmicro.annotation.processor.data.model;

import io.rxmicro.annotation.processor.common.model.definition.SupportedTypesProvider;
import io.rxmicro.annotation.processor.common.model.definition.TypeDefinition;
import io.rxmicro.annotation.processor.common.model.definition.TypeDefinitions;
import io.rxmicro.annotation.processor.common.model.method.MethodParameter;
import io.rxmicro.annotation.processor.common.model.method.MethodResult;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @since 0.1
 */
public final class DataRepositoryMethodSignature {

    private final SupportedTypesProvider supportedTypesProvider;

    private final ExecutableElement method;

    private final boolean operationReturnVoid;

    private final MethodResult methodResult;

    private final List<MethodParameter> params;

    private DataRepositoryInterfaceSignature dataRepositoryInterfaceSignature;

    public DataRepositoryMethodSignature(final SupportedTypesProvider supportedTypesProvider,
                                         final ExecutableElement method,
                                         final boolean operationReturnVoid,
                                         final MethodResult methodResult,
                                         final List<MethodParameter> params) {
        this.supportedTypesProvider = require(supportedTypesProvider);
        this.method = require(method);
        this.operationReturnVoid = operationReturnVoid;
        this.methodResult = require(methodResult);
        this.params = require(params);
    }

    public DataRepositoryInterfaceSignature getDataRepositoryInterfaceSignature() {
        return dataRepositoryInterfaceSignature;
    }

    public void setDataRepositoryInterfaceSignature(final DataRepositoryInterfaceSignature dataRepositoryInterfaceSignature) {
        this.dataRepositoryInterfaceSignature = dataRepositoryInterfaceSignature;
    }

    public ExecutableElement getMethod() {
        return method;
    }

    public MethodResult getMethodResult() {
        return methodResult;
    }

    public List<MethodParameter> getParams() {
        return params;
    }

    public Set<TypeMirror> getParamEntityClasses() {
        final TypeDefinitions<TypeDefinition> notEntityMethodParameters = supportedTypesProvider.getNotEntityMethodParameters();
        return params.stream()
                .map(MethodParameter::getType)
                .filter(p -> !notEntityMethodParameters.contains(p) &&
                        !p.getKind().isPrimitive())
                .collect(Collectors.toSet());
    }

    public Set<TypeMirror> getReturnEntityClasses() {
        final TypeDefinitions<TypeDefinition> primitives = supportedTypesProvider.getResultReturnPrimitiveTypes();
        if (methodResult.isVoid() ||
                primitives.contains(methodResult.getResultType()) ||
                operationReturnVoid) {
            return Set.of();
        } else {
            return Set.of(methodResult.getResultType());
        }
    }
}
