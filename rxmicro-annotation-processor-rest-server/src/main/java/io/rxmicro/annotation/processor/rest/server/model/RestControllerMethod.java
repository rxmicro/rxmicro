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

package io.rxmicro.annotation.processor.rest.server.model;

import io.rxmicro.annotation.processor.common.model.method.MethodBody;
import io.rxmicro.annotation.processor.common.model.method.MethodName;
import io.rxmicro.annotation.processor.common.util.UsedByFreemarker;
import io.rxmicro.annotation.processor.rest.model.HttpMethodMapping;
import io.rxmicro.annotation.processor.rest.model.RestRequestModel;
import io.rxmicro.annotation.processor.rest.model.RestResponseModel;
import io.rxmicro.rest.server.NotFoundMessage;

import java.util.List;
import java.util.Optional;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

import static io.rxmicro.common.util.Formats.format;
import static java.util.stream.Collectors.joining;

/**
 * @author nedis
 * @since 0.1
 */
public final class RestControllerMethod {

    private final List<HttpMethodMapping> httpMethodMappings;

    private final ExecutableElement method;

    private final MethodName methodName;

    private final RestRequestModel requestModel;

    private final MethodBody body;

    private final RestResponseModel responseModel;

    private final int successStatusCode;

    private final boolean notFoundPossible;

    public RestControllerMethod(final List<HttpMethodMapping> httpMethodMappings,
                                final ExecutableElement method,
                                final MethodName methodName,
                                final RestRequestModel requestModel,
                                final MethodBody body,
                                final RestResponseModel responseModel,
                                final int successStatusCode,
                                final boolean notFoundPossible) {
        this.httpMethodMappings = httpMethodMappings;
        this.method = method;
        this.methodName = methodName;
        this.requestModel = requestModel;
        this.body = body;
        this.responseModel = responseModel;
        this.successStatusCode = successStatusCode;
        this.notFoundPossible = notFoundPossible;
    }

    public ExecutableElement getMethod() {
        return method;
    }

    public Optional<TypeElement> getFromHttpDataType() {
        return requestModel.getRequestType();
    }

    public Optional<TypeElement> getToHttpDataType() {
        return responseModel.getResultType();
    }

    public List<HttpMethodMapping> getHttpMethodMappings() {
        return httpMethodMappings;
    }

    @UsedByFreemarker
    public MethodName getName() {
        return methodName;
    }

    @UsedByFreemarker
    public MethodBody getBody() {
        return body;
    }

    public int getSuccessStatusCode() {
        return successStatusCode;
    }

    boolean isNotFoundPossible() {
        return notFoundPossible;
    }

    String getNotFoundMessage() {
        return Optional.ofNullable(method.getAnnotation(NotFoundMessage.class))
                .map(NotFoundMessage::value)
                .orElse("Not Found");
    }

    boolean isVoidReturn() {
        return responseModel.isVoidReturn() || responseModel.isReactiveVoid();
    }

    @Override
    public String toString() {
        return format(
                "?.?(?)",
                method.getEnclosingElement().asType().toString(),
                method.getSimpleName(),
                method.getParameters().stream().map(v -> v.asType().toString()).collect(joining(", "))
        );
    }
}
