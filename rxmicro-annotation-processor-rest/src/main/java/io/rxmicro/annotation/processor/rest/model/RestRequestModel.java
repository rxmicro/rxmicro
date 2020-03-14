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

import io.rxmicro.annotation.processor.common.model.virtual.VirtualTypeElement;

import javax.lang.model.element.TypeElement;
import java.util.Optional;

import static io.rxmicro.annotation.processor.common.util.Errors.createInternalErrorSupplier;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public class RestRequestModel {

    public static final RestRequestModel VOID = new RestRequestModel();

    private final TypeElement requestType;

    private final String variableName;

    private RestRequestModel() {
        requestType = null;
        variableName = null;
    }

    public RestRequestModel(final TypeElement requestType,
                            final String variableName) {
        this.requestType = require(requestType);
        this.variableName = require(variableName);
    }

    public boolean isVirtual() {
        return requestType instanceof VirtualTypeElement;
    }

    public boolean requestModelNotExists() {
        return requestType == null;
    }

    public boolean requestModelExists() {
        return requestType != null;
    }

    public Optional<TypeElement> getRequestType() {
        return Optional.ofNullable(requestType);
    }

    public TypeElement getRequiredRequestType() {
        return getRequestType().orElseThrow(createInternalErrorSupplier("Expected request model type"));
    }

    public String getRequiredVariableName() {
        return require(variableName);
    }

    @Override
    public String toString() {
        return this == VOID ? "" : format("? ?", getRequiredRequestType().asType(), getRequiredVariableName());
    }
}
