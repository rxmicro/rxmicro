/*
 * Copyright 2019 http://rxmicro.io
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

package io.rxmicro.annotation.processor.rest.model;

import io.rxmicro.annotation.processor.common.model.method.MethodResult;
import io.rxmicro.annotation.processor.common.util.UsedByFreemarker;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.util.Optional;
import java.util.Set;

import static io.rxmicro.annotation.processor.common.util.Elements.asTypeElement;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class RestResponseModel {

    public static final RestResponseModel VOID = new RestResponseModel(null);

    private final MethodResult methodResult;

    public RestResponseModel(final MethodResult methodResult) {
        this.methodResult = methodResult;
    }

    public Set<String> getRequiredImports() {
        return methodResult != null ? methodResult.getRequiredImports() : Set.of();
    }

    public String getHumanReadableReturnType() {
        return methodResult != null ? methodResult.getHumanReadableReturnType() : "void";
    }

    public boolean isVoidReturn() {
        return methodResult == null;
    }

    public boolean isReactiveVoid() {
        return methodResult != null && methodResult.isVoid();
    }

    public Optional<MethodResult> getMethodResult() {
        return Optional.ofNullable(methodResult);
    }

    @UsedByFreemarker
    public boolean isFuture() {
        return methodResult != null && methodResult.isFuture();
    }

    @UsedByFreemarker
    public boolean isMono() {
        return methodResult != null && methodResult.isMono();
    }

    @UsedByFreemarker
    public boolean isMaybe() {
        return methodResult != null && methodResult.isMaybe();
    }

    @UsedByFreemarker
    public boolean isSingle() {
        return methodResult != null && methodResult.isSingle();
    }

    @UsedByFreemarker
    public boolean isCompletable() {
        return methodResult != null && methodResult.isCompletable();
    }

    public Optional<TypeMirror> getReactiveType() {
        return Optional.ofNullable(methodResult).map(MethodResult::getReactiveType);
    }

    public Optional<TypeElement> getResultType() {
        return Optional.ofNullable(methodResult)
                .filter(r -> !r.isVoid())
                .flatMap(r -> asTypeElement(r.getResultType()));
    }

    @UsedByFreemarker
    public boolean isOptional() {
        return methodResult != null && methodResult.isOneItem() && methodResult.isOptional();
    }
}
