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

package io.rxmicro.annotation.processor.rest.client.model;

import io.rxmicro.annotation.processor.rest.model.HttpMethodMapping;
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
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class RestClientMethodSignature implements RestMethodSignature {

    private final ExecutableElement method;

    private final String simpleName;

    private final RestRequestModel requestModel;

    private final RestResponseModel responseModel;

    private final HttpMethodMapping httpMethodMapping;

    private RestClientClassSignature restClientClassSignature;

    public RestClientMethodSignature(final ExecutableElement method,
                                     final RestRequestModel requestModel,
                                     final RestResponseModel responseModel,
                                     final HttpMethodMapping httpMethodMapping) {
        this.method = method;
        this.simpleName = method.getSimpleName().toString();
        this.requestModel = requestModel;
        this.responseModel = responseModel;
        this.httpMethodMapping = httpMethodMapping;
    }

    public RestClientClassSignature getRestClientClassSignature() {
        return restClientClassSignature;
    }

    void setRestClientClassSignature(final RestClientClassSignature restClientClassSignature) {
        this.restClientClassSignature = restClientClassSignature;
    }

    public ExecutableElement getMethod() {
        return method;
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
        return List.of(httpMethodMapping);
    }

    @Override
    public Optional<TypeElement> getFromHttpDataType() {
        return responseModel.getResultType();
    }

    @Override
    public Optional<TypeElement> getToHttpDataType() {
        return requestModel.getRequestType();
    }

    public HttpMethodMapping getHttpMethodMapping() {
        return httpMethodMapping;
    }

    @Override
    public String toString() {
        return format("?.?",
                method.getEnclosingElement().asType(),
                format("?(?)",
                        simpleName,
                        method.getParameters().stream()
                                .map(p -> p.asType().toString())
                                .collect(joining(", "))
                )
        );
    }
}
