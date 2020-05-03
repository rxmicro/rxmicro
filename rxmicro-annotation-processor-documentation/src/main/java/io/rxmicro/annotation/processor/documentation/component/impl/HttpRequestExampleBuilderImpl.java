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
import io.rxmicro.annotation.processor.common.model.type.ModelClass;
import io.rxmicro.annotation.processor.documentation.component.ExampleValueBuilder;
import io.rxmicro.annotation.processor.documentation.component.HttpRequestExampleBuilder;
import io.rxmicro.annotation.processor.documentation.component.JsonStructureExampleBuilder;
import io.rxmicro.annotation.processor.documentation.model.ProjectMetaData;
import io.rxmicro.annotation.processor.rest.model.HttpMethodMapping;
import io.rxmicro.annotation.processor.rest.model.ParentUrl;
import io.rxmicro.annotation.processor.rest.model.RestModelField;
import io.rxmicro.annotation.processor.rest.server.model.ModelReaderClassStructure;
import io.rxmicro.annotation.processor.rest.server.model.RestControllerClassStructureStorage;
import io.rxmicro.annotation.processor.rest.server.model.RestControllerMethod;
import io.rxmicro.rest.model.HttpMethod;

import javax.lang.model.element.TypeElement;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static io.rxmicro.annotation.processor.common.util.Errors.createInternalErrorSupplier;
import static io.rxmicro.annotation.processor.documentation.model.Constants.HTTP_VERSION;
import static io.rxmicro.common.util.Formats.format;
import static java.lang.System.lineSeparator;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@Singleton
public final class HttpRequestExampleBuilderImpl implements HttpRequestExampleBuilder {

    private static final Set<String> SUPPORTED_HTTP_BODY_METHODS = Set.of(
            HttpMethod.POST.name(),
            HttpMethod.PUT.name(),
            HttpMethod.PATCH.name()
    );

    @Inject
    private JsonStructureExampleBuilder jsonStructureExampleBuilder;

    @Inject
    private ExampleValueBuilder exampleValueBuilder;

    @Override
    public String build(final ProjectMetaData projectMetaData,
                        final ParentUrl parentUrl,
                        final HttpMethodMapping httpMethodMapping,
                        final RestControllerClassStructureStorage restControllerClassStructureStorage,
                        final RestControllerMethod method) {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(format("? ?? ??",
                httpMethodMapping.getMethod(),
                httpMethodMapping.getExactOrTemplateUri(),
                getQueryParameters(httpMethodMapping, method, restControllerClassStructureStorage),
                HTTP_VERSION,
                lineSeparator()));
        if (projectMetaData.isBaseEndpointPresent()) {
            stringBuilder.append(format("Host: ??", getHost(projectMetaData.getBaseEndpoint()), lineSeparator()));
        }
        stringBuilder.append(format("Accept: application/json?", lineSeparator()));
        if (parentUrl.isHeaderVersionStrategy()) {
            stringBuilder.append(format("?: ??", parentUrl.getVersionHeaderName(), parentUrl.getVersionValue(), lineSeparator()));
        }
        final String body = getRequestHttpBody(httpMethodMapping, method, restControllerClassStructureStorage);
        if (body != null) {
            stringBuilder.append(format("Content-Type: application/json?", lineSeparator()));
            stringBuilder.append(format("Content-Length: ??", getContentLength(body), lineSeparator()));
        } else if (httpMethodMapping.isHttpBody() || SUPPORTED_HTTP_BODY_METHODS.contains(httpMethodMapping.getMethod())) {
            stringBuilder.append(format("Content-Length: 0?", lineSeparator()));
        }
        addCustomHeaders(stringBuilder, method, restControllerClassStructureStorage);
        if (body != null) {
            stringBuilder.append(lineSeparator());
            stringBuilder.append(body);
        }
        return stringBuilder.toString();
    }

    private int getContentLength(final String body) {
        // Remove CL if detected
        return body.replace("\r", "").length();
    }

    private String getHost(final String baseEndpoint) {
        if (baseEndpoint.startsWith("http://")) {
            return baseEndpoint.substring(7);
        }
        if (baseEndpoint.startsWith("https://")) {
            return baseEndpoint.substring(8);
        }
        return baseEndpoint;
    }

    private String getQueryParameters(final HttpMethodMapping httpMethodMapping,
                                      final RestControllerMethod method,
                                      final RestControllerClassStructureStorage restControllerClassStructureStorage) {
        final Optional<TypeElement> requestModel = method.getFromHttpDataType();
        if (!httpMethodMapping.isHttpBody() && requestModel.isPresent()) {
            final ModelReaderClassStructure modelReaderClassStructure =
                    restControllerClassStructureStorage.getModelReaderClassStructure(
                            requestModel.get().asType().toString())
                            .orElseThrow(createInternalErrorSupplier("ModelReaderClassStructure not found for type: ?", requestModel.get().asType()));
            final StringBuilder queryBuilder = new StringBuilder();
            for (final Map.Entry<RestModelField, ModelClass> entry : modelReaderClassStructure.getModelClass().getParamEntries()) {
                if (queryBuilder.length() > 0) {
                    queryBuilder.append('&');
                }
                queryBuilder.append(entry.getKey().getModelName()).append('=').append(exampleValueBuilder.getExample(entry.getKey()));
            }
            return queryBuilder.length() > 0 ? "?" + queryBuilder.toString() : "";
        }
        return "";
    }

    private String getRequestHttpBody(final HttpMethodMapping httpMethodMapping,
                                      final RestControllerMethod method,
                                      final RestControllerClassStructureStorage restControllerClassStructureStorage) {
        final Optional<TypeElement> requestModel = method.getFromHttpDataType();
        if (httpMethodMapping.isHttpBody() && requestModel.isPresent()) {
            return jsonStructureExampleBuilder.build(
                    restControllerClassStructureStorage.getModelReaderClassStructure(
                            requestModel.get().asType().toString())
                            .orElseThrow(createInternalErrorSupplier(
                                    "ModelReaderClassStructure not found for type: ?",
                                    requestModel.get().asType()
                            ))
                            .getModelClass()
            );
        } else {
            return null;
        }
    }

    private void addCustomHeaders(final StringBuilder stringBuilder,
                                  final RestControllerMethod method,
                                  final RestControllerClassStructureStorage restControllerClassStructureStorage) {

        method.getFromHttpDataType().flatMap(t ->
                restControllerClassStructureStorage.getModelReaderClassStructure(t.asType().toString())).ifPresent(cl ->
                cl.getModelClass().getHeaderEntries().forEach(e ->
                        stringBuilder.append(
                                format("?: ??",
                                        e.getKey().getModelName(),
                                        exampleValueBuilder.getExample(e.getKey()),
                                        lineSeparator()))));
    }
}
