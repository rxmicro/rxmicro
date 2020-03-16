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

package io.rxmicro.annotation.processor.rest.server.model;

import io.rxmicro.annotation.processor.rest.model.HttpMethodMapping;
import io.rxmicro.annotation.processor.rest.model.ParentUrl;
import io.rxmicro.annotation.processor.rest.model.RestMethodSignature;
import io.rxmicro.annotation.processor.rest.model.RestRequestModel;
import io.rxmicro.annotation.processor.rest.model.RestResponseModel;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.util.List;
import java.util.Optional;

import static io.rxmicro.common.util.Formats.format;
import static java.util.stream.Collectors.joining;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class RestControllerMethodSignature implements RestMethodSignature {

    private final ParentUrl parentUrl;

    private final ExecutableElement executableElement;

    private final String simpleName;

    private final RestRequestModel requestModel;

    private final RestResponseModel responseModel;

    private final List<HttpMethodMapping> httpMethodMappings;

    public RestControllerMethodSignature(final ParentUrl parentUrl,
                                         final ExecutableElement executableElement,
                                         final RestRequestModel requestModel,
                                         final RestResponseModel responseModel,
                                         final List<HttpMethodMapping> httpMethodMappings) {
        this.parentUrl = parentUrl;
        this.executableElement = executableElement;
        this.simpleName = executableElement.getSimpleName().toString();
        this.requestModel = requestModel;
        this.responseModel = responseModel;
        this.httpMethodMappings = httpMethodMappings;
    }

    public ParentUrl getParentUrl() {
        return parentUrl;
    }

    public ExecutableElement getExecutableElement() {
        return executableElement;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public RestRequestModel getRequestModel() {
        return requestModel;
    }

    public RestResponseModel getResponseModel() {
        return responseModel;
    }

    @Override
    public List<HttpMethodMapping> getHttpMethodMappings() {
        return httpMethodMappings;
    }

    @Override
    public Optional<TypeElement> getFromHttpDataType() {
        return requestModel.getRequestType();
    }

    @Override
    public Optional<TypeElement> getToHttpDataType() {
        return responseModel.getResultType();
    }

    @Override
    public String toString() {
        return format("?.?",
                executableElement.getEnclosingElement().asType(),
                format("?(?)",
                        simpleName,
                        executableElement.getParameters().stream()
                                .map(p -> p.asType().toString())
                                .collect(joining(", "))
                )
        );
    }
}
