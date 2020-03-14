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

package io.rxmicro.annotation.processor.rest.server.model;

import io.rxmicro.annotation.processor.common.model.TypeSignature;
import io.rxmicro.annotation.processor.rest.model.ParentUrl;
import io.rxmicro.annotation.processor.rest.model.RestClassSignature;

import javax.lang.model.element.TypeElement;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static io.rxmicro.common.util.ExCollections.unmodifiableList;
import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class RestControllerClassSignature extends TypeSignature implements RestClassSignature {

    private final ParentUrl parentUrl;

    private final TypeElement typeElement;

    private final List<RestControllerMethodSignature> methodSignatures;

    public RestControllerClassSignature(final ParentUrl parentUrl,
                                        final TypeElement typeElement,
                                        final List<RestControllerMethodSignature> methodSignatures) {
        this.parentUrl = require(parentUrl);
        this.typeElement = require(typeElement);
        this.methodSignatures = unmodifiableList(methodSignatures);
    }

    public ParentUrl getParentUrl() {
        return parentUrl;
    }

    public TypeElement getTypeElement() {
        return typeElement;
    }

    @Override
    public Set<TypeElement> getFromHttpDataModelTypes() {
        return methodSignatures.stream()
                .flatMap(m -> m.getRequestModel().getRequestType().stream())
                .collect(Collectors.toSet());
    }

    @Override
    public Set<TypeElement> getToHttpDataModelTypes() {
        return methodSignatures.stream()
                .flatMap(m -> m.getResponseModel().getResultType().stream())
                .collect(Collectors.toSet());
    }

    @Override
    public List<RestControllerMethodSignature> getMethodSignatures() {
        return methodSignatures;
    }

    @Override
    protected String getTypeFullName() {
        return typeElement.getQualifiedName().toString();
    }
}
