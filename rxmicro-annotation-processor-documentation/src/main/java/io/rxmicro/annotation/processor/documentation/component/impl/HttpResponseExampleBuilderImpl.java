/*
 * Copyright (c) 2020. http://rxmicro.io
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

package io.rxmicro.annotation.processor.documentation.component.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.documentation.component.ExampleValueBuilder;
import io.rxmicro.annotation.processor.documentation.component.HttpResponseExampleBuilder;
import io.rxmicro.annotation.processor.documentation.component.JsonStructureExampleBuilder;
import io.rxmicro.annotation.processor.rest.server.model.RestControllerClassStructureStorage;
import io.rxmicro.annotation.processor.rest.server.model.RestControllerMethod;
import io.rxmicro.documentation.ResourceDefinition;
import io.rxmicro.json.JsonHelper;

import java.util.Map;
import java.util.Optional;

import static io.rxmicro.annotation.processor.common.util.Errors.createInternalErrorSupplier;
import static io.rxmicro.annotation.processor.documentation.model.Constants.HTTP_VERSION;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.files.PropertiesResources.loadProperties;
import static io.rxmicro.rest.RequestId.REQUEST_ID_EXAMPLE;
import static java.lang.System.lineSeparator;
import static java.util.stream.Collectors.toMap;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
@Singleton
public final class HttpResponseExampleBuilderImpl implements HttpResponseExampleBuilder {

    private final Map<Integer, String> statusCodes;

    @Inject
    private JsonStructureExampleBuilder jsonStructureExampleBuilder;

    @Inject
    private ExampleValueBuilder exampleValueBuilder;

    public HttpResponseExampleBuilderImpl() {
        statusCodes = loadProperties("status-codes.properties")
                .map(m -> m.entrySet().stream().collect(toMap(e -> Integer.parseInt(e.getKey()), Map.Entry::getValue)))
                .orElse(Map.of());
    }

    @Override
    public String build(final ResourceDefinition resourceDefinition,
                        final RestControllerClassStructureStorage restControllerClassStructureStorage,
                        final RestControllerMethod method) {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(format("? ? ??",
                HTTP_VERSION,
                method.getSuccessStatusCode(),
                getStatusMessage(method.getSuccessStatusCode()),
                lineSeparator()));
        final Optional<String> body = getJsonBodyExample(restControllerClassStructureStorage, method);
        if (body.isPresent()) {
            stringBuilder.append(format("Content-Type: application/json?", lineSeparator()));
            stringBuilder.append(format("Content-Length: ??", getContentLength(body.get()), lineSeparator()));
        } else {
            stringBuilder.append(format("Content-Length: 0?", lineSeparator()));
        }
        if (resourceDefinition.withRequestIdResponseHeader()) {
            stringBuilder.append(format("Request-Id: ??", REQUEST_ID_EXAMPLE, lineSeparator()));
        }
        addCustomHeaders(stringBuilder, method, restControllerClassStructureStorage);
        if (body.isPresent()) {
            stringBuilder.append(lineSeparator());
            stringBuilder.append(body.get());
        }
        return stringBuilder.toString();
    }

    @Override
    public String buildErrorExample(final ResourceDefinition resourceDefinition,
                                    final int statusCode,
                                    final String message) {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(format("? ? ??",
                HTTP_VERSION,
                statusCode,
                getStatusMessage(statusCode),
                lineSeparator()));
        final String body = JsonHelper.toJsonString(Map.of("message", message), true);
        stringBuilder.append(format("Content-Type: application/json?", lineSeparator()));
        stringBuilder.append(format("Content-Length: ??", getContentLength(body), lineSeparator()));
        if (resourceDefinition.withRequestIdResponseHeader()) {
            stringBuilder.append(format("Request-Id: ??", REQUEST_ID_EXAMPLE, lineSeparator()));
        }
        stringBuilder.append(lineSeparator());
        stringBuilder.append(body);
        return stringBuilder.toString();
    }

    @Override
    public String buildErrorExample(final ResourceDefinition resourceDefinition,
                                    final int statusCode) {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(format("? ? ??",
                HTTP_VERSION,
                statusCode,
                getStatusMessage(statusCode),
                lineSeparator()));
        stringBuilder.append(format("Content-Length: 0?", lineSeparator()));
        if (resourceDefinition.withRequestIdResponseHeader()) {
            stringBuilder.append(format("Request-Id: ??", REQUEST_ID_EXAMPLE, lineSeparator()));
        }
        return stringBuilder.toString();
    }

    @Override
    public Optional<String> getJsonBodyExample(final RestControllerClassStructureStorage restControllerClassStructureStorage,
                                               final RestControllerMethod method) {
        return method.getToHttpDataType()
                .map(typeElement -> jsonStructureExampleBuilder.build(
                        restControllerClassStructureStorage.getModelWriterClassStructure(
                                typeElement.asType().toString())
                                .orElseThrow(createInternalErrorSupplier(
                                        "ModelWriterClassStructure not found for type: ?",
                                        typeElement.asType()
                                ))
                                .getModelClass()));
    }

    private int getContentLength(String body) {
        // Remove CL if detected
        return body.replace("\r", "").length();
    }

    private String getStatusMessage(final int statusCode) {
        return Optional.ofNullable(statusCodes.get(statusCode)).orElseGet(() -> {
            final int group = statusCode % 100;
            if (group == 1) {
                return "Information response";
            } else if (group == 2) {
                return "Successful response";
            } else if (group == 3) {
                return "Redirection response";
            } else if (group == 4) {
                return "Client error response";
            } else if (group == 5) {
                return "Server error response";
            } else {
                return "";
            }
        });
    }

    private void addCustomHeaders(final StringBuilder stringBuilder,
                                  final RestControllerMethod method,
                                  final RestControllerClassStructureStorage restControllerClassStructureStorage) {
        method.getToHttpDataType().flatMap(t ->
                restControllerClassStructureStorage.getModelWriterClassStructure(t.asType().toString())).ifPresent(cl ->
                cl.getModelClass().getHeaderEntries().forEach(e ->
                        stringBuilder.append(
                                format("?: ??",
                                        e.getKey().getModelName(),
                                        exampleValueBuilder.getExample(e.getKey()),
                                        lineSeparator()))));
    }
}
