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

package io.rxmicro.annotation.processor.documentation.component.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.documentation.component.ExampleValueBuilder;
import io.rxmicro.annotation.processor.documentation.component.HttpResponseExampleBuilder;
import io.rxmicro.annotation.processor.documentation.component.JsonStructureExampleBuilder;
import io.rxmicro.annotation.processor.rest.model.RestObjectModelClass;
import io.rxmicro.annotation.processor.rest.server.model.RestControllerClassStructureStorage;
import io.rxmicro.annotation.processor.rest.server.model.RestControllerMethod;
import io.rxmicro.documentation.ResourceDefinition;
import io.rxmicro.json.JsonHelper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static io.rxmicro.annotation.processor.common.util.Errors.createInternalErrorSupplier;
import static io.rxmicro.annotation.processor.documentation.model.Constants.HTTP_VERSION;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.resource.PropertiesResources.loadProperties;
import static io.rxmicro.rest.RequestId.REQUEST_ID_EXAMPLE;
import static java.lang.System.lineSeparator;
import static java.util.Map.entry;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class HttpResponseExampleBuilderImpl implements HttpResponseExampleBuilder {

    private final Map<Integer, String> statusCodes;

    private final Map<Integer, String> statusCodeGroups = Map.of(
            1, "Information response",
            2, "Successful response",
            3, "Information response",
            4, "Client error response",
            5, "Server error response"
    );

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
        final List<Map.Entry<String, Object>> customHeaders =
                method.getToHttpDataType()
                        .stream()
                        .flatMap(t -> restControllerClassStructureStorage.getModelWriterClassStructure(t.asType().toString())
                                .stream().flatMap(cl -> cl.getModelClass().getAllDeclaredHeadersStream(false))
                                .map(e -> entry(e.getKey().getModelName(), exampleValueBuilder.getExample(e.getKey())))).collect(toList());
        final Optional<String> body = getJsonBodyExample(restControllerClassStructureStorage, method);
        return buildExample(
                resourceDefinition,
                method.getSuccessStatusCode(),
                customHeaders,
                body.orElse(null)
        );
    }

    @Override
    public String build(final ResourceDefinition resourceDefinition,
                        final int statusCode,
                        final RestObjectModelClass restObjectModelClass) {
        return buildExample(
                resourceDefinition,
                statusCode,
                List.of(),
                jsonStructureExampleBuilder.build(restObjectModelClass)
        );
    }

    @Override
    public String buildErrorExample(final ResourceDefinition resourceDefinition,
                                    final int statusCode,
                                    final String message) {
        final String body = JsonHelper.toJsonString(Map.of("message", message), true);
        return buildExample(
                resourceDefinition,
                statusCode,
                List.of(),
                body
        );
    }

    @Override
    public String buildErrorExample(final ResourceDefinition resourceDefinition,
                                    final int statusCode) {
        return buildExample(
                resourceDefinition,
                statusCode,
                List.of(),
                null
        );
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

    private String buildExample(final ResourceDefinition resourceDefinition,
                                final int statusCode,
                                final List<Map.Entry<String, Object>> customHeaders,
                                final String body) {
        final StringBuilder httpMessageBuilder = new StringBuilder();
        httpMessageBuilder.append(format("? ? ??", HTTP_VERSION, statusCode, getStatusMessage(statusCode), lineSeparator()));
        if (body != null) {
            httpMessageBuilder.append("Content-Type: application/json").append(lineSeparator());
            httpMessageBuilder.append(format("Content-Length: ?", getContentLength(body))).append(lineSeparator());
        } else {
            httpMessageBuilder.append("Content-Length: 0").append(lineSeparator());
        }
        if (resourceDefinition.withRequestIdResponseHeader()) {
            httpMessageBuilder.append(format("Request-Id: ?", REQUEST_ID_EXAMPLE)).append(lineSeparator());
        }
        customHeaders.forEach(e -> httpMessageBuilder.append(format("?: ?", e.getKey(), e.getValue())).append(lineSeparator()));
        if (body != null) {
            httpMessageBuilder.append(lineSeparator());
            httpMessageBuilder.append(body);
        }
        return httpMessageBuilder.toString();
    }

    private int getContentLength(final String body) {
        // Remove CL if detected
        return body.replace("\r", "").length();
    }

    private String getStatusMessage(final int statusCode) {
        return Optional.ofNullable(statusCodes.get(statusCode)).orElseGet(() -> {
            final int httpStatusGroupStep = 100;
            final int group = statusCode % httpStatusGroupStep;
            return Optional.ofNullable(statusCodeGroups.get(group)).orElse("");
        });
    }
}
