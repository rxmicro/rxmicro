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

package io.rxmicro.rest.server.exchange.json.internal;

import io.rxmicro.http.error.HttpErrorException;
import io.rxmicro.json.JsonException;
import io.rxmicro.json.JsonHelper;
import io.rxmicro.rest.model.HttpCallFailedException;
import io.rxmicro.rest.server.RestServerConfig;
import io.rxmicro.rest.server.detail.component.HttpResponseBuilder;
import io.rxmicro.rest.server.detail.model.HttpResponse;
import io.rxmicro.rest.server.local.component.HttpErrorResponseBodyBuilder;

import java.util.Map;

import static io.rxmicro.common.CommonConstants.RX_MICRO_FRAMEWORK_NAME;
import static io.rxmicro.config.Configs.getConfig;
import static io.rxmicro.exchange.json.JsonExchangeConstants.CONTENT_TYPE_APPLICATION_JSON;
import static io.rxmicro.exchange.json.JsonExchangeConstants.MESSAGE;
import static io.rxmicro.http.HttpStandardHeaderNames.CONTENT_TYPE;
import static io.rxmicro.http.HttpStandardHeaderNames.SERVER;
import static io.rxmicro.json.JsonHelper.toJsonString;
import static io.rxmicro.json.JsonTypes.asJsonObject;
import static io.rxmicro.json.JsonTypes.isJsonObject;

/**
 * @author nedis
 * @since 0.1
 */
public final class JsonHttpErrorResponseBodyBuilder implements HttpErrorResponseBodyBuilder {

    private final RestServerConfig restServerConfig = getConfig(RestServerConfig.class);

    @Override
    public HttpResponse build(final HttpResponseBuilder httpResponseBuilder,
                              final int status,
                              final String message) {
        final HttpResponse response = httpResponseBuilder.build();
        response.setStatus(status);
        response.setHeader(CONTENT_TYPE, CONTENT_TYPE_APPLICATION_JSON);
        response.setContent(createErrorJson(String.valueOf(message)));
        return response;
    }

    @Override
    public HttpResponse build(final HttpResponseBuilder httpResponseBuilder,
                              final HttpErrorException exception) {
        final HttpResponse response = httpResponseBuilder.build();
        response.setStatus(exception.getStatusCode());
        if (exception.getMessage() != null) {
            response.setHeader(CONTENT_TYPE, CONTENT_TYPE_APPLICATION_JSON);
            response.setContent(createErrorJson(exception.getMessage()));
        }
        return response;
    }

    @Override
    public HttpResponse build(final HttpResponseBuilder httpResponseBuilder,
                              final HttpCallFailedException exception) {
        final HttpResponse response = httpResponseBuilder.build(false);
        response.setStatus(exception.getStatusCode());
        response.setVersion(exception.getVersion());
        response.setOrAddHeaders(exception.getHeaders());
        if (exception.isBodyPresent()) {
            response.setContent(exception.getBody());
        }
        return response;
    }

    @Override
    public boolean isRxMicroError(final HttpCallFailedException exception) {
        final String server = exception.getHeaders().getValue(SERVER);
        if (server != null && server.startsWith(RX_MICRO_FRAMEWORK_NAME)) {
            return true;
        }
        if (CONTENT_TYPE_APPLICATION_JSON.equals(exception.getHeaders().getValue(CONTENT_TYPE))) {
            try {
                final Object json = JsonHelper.readJson(exception.getBodyAsString());
                if (isJsonObject(json)) {
                    final Map<String, Object> jsonObject = asJsonObject(json);
                    return jsonObject.size() == 1 && jsonObject.containsKey(MESSAGE);
                }
            } catch (final JsonException | NumberFormatException ex) {
                //do nothing, i.e. return false
            }
        }
        return false;
    }

    private String createErrorJson(final String message) {
        return toJsonString(Map.of(MESSAGE, message), restServerConfig.isHumanReadableOutput());
    }
}
