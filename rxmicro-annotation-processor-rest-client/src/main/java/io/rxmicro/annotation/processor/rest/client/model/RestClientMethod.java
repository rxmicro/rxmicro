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

package io.rxmicro.annotation.processor.rest.client.model;

import io.rxmicro.annotation.processor.common.model.method.MethodBody;
import io.rxmicro.annotation.processor.common.util.UsedByFreemarker;
import io.rxmicro.annotation.processor.rest.model.RestRequestModel;
import io.rxmicro.annotation.processor.rest.model.RestResponseModel;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.util.List;
import java.util.Optional;

import static io.rxmicro.annotation.processor.common.util.Names.getSimpleName;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;
import static java.util.stream.Collectors.joining;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class RestClientMethod {

    private final ExecutableElement method;

    private final String methodName;

    private final RestRequestModel requestModel;

    private final MethodBody body;

    private final RestResponseModel responseModel;

    public RestClientMethod(final ExecutableElement method,
                            final String methodName,
                            final RestRequestModel requestModel,
                            final MethodBody body,
                            final RestResponseModel responseModel) {
        this.method = require(method);
        this.methodName = require(methodName);
        this.requestModel = require(requestModel);
        this.body = require(body);
        this.responseModel = require(responseModel);
    }

    Optional<TypeElement> getFromHttpDataType() {
        return responseModel.getResultType();
    }

    boolean shouldGenerateModelExtractor(final RestClientClassStructureStorage classStructureStorage) {
        return requestModel.getRequestType()
                .map(t -> classStructureStorage.isRequestModelExtractorPresent(t.asType().toString()))
                .orElse(false);
    }

    boolean shouldGeneratePathBuilder(final RestClientClassStructureStorage classStructureStorage) {
        return requestModel.getRequestType()
                .map(t -> classStructureStorage.isPathBuilderPresent(t.asType().toString()))
                .orElse(false);
    }

    boolean shouldGenerateModelToHttpBodyConverter(final RestClientClassStructureStorage classStructureStorage) {
        return requestModel.getRequestType()
                .map(t -> classStructureStorage.isModelToJsonConverterPresent(t.asType().toString()))
                .orElse(false);
    }

    boolean shouldGenerateModelReader(final RestClientClassStructureStorage classStructureStorage) {
        return responseModel.getResultType()
                .map(t -> classStructureStorage.isModelReaderPresent(t.asType().toString()))
                .orElse(false);
    }

    boolean shouldGenerateRequestValidator(final RestClientClassStructureStorage classStructureStorage) {
        return requestModel.getRequestType()
                .map(t -> classStructureStorage.isRequestValidatorPresent(t.asType().toString()))
                .orElse(false);
    }

    boolean shouldGenerateResponseValidator(final RestClientClassStructureStorage classStructureStorage) {
        return responseModel.getResultType()
                .map(t -> classStructureStorage.isResponseValidatorPresent(t.asType().toString()))
                .orElse(false);
    }

    Optional<TypeElement> getToHttpDataType() {
        return requestModel.getRequestType();
    }

    @UsedByFreemarker("$$RestClientTemplate.javaftl")
    public String getReturnType() {
        return responseModel.getHumanReadableReturnType();
    }

    @UsedByFreemarker("$$RestClientTemplate.javaftl")
    public String getName() {
        return methodName;
    }

    @UsedByFreemarker("$$RestClientTemplate.javaftl")
    public String getParams() {
        return method.getParameters().stream()
                .map(e -> format("final ? ?", getSimpleName(e.asType()), e.getSimpleName().toString()))
                .collect(joining(", "));
    }

    @UsedByFreemarker("$$RestClientTemplate.javaftl")
    public List<String> getBodyLines() {
        return body.getLines();
    }
}
