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

package io.rxmicro.rest.server.exchange.json.internal;

import io.rxmicro.json.JsonException;
import io.rxmicro.json.JsonHelper;
import io.rxmicro.rest.model.HttpCallFailedException;
import io.rxmicro.rest.server.detail.model.HttpResponse;
import io.rxmicro.rest.server.local.component.HttpErrorResponseBodyBuilder;

import java.util.Map;

import static io.rxmicro.common.Constants.RX_MICRO_FRAMEWORK_NAME;
import static io.rxmicro.exchange.json.Constants.CONTENT_TYPE_APPLICATION_JSON;
import static io.rxmicro.http.HttpHeaders.CONTENT_TYPE;
import static io.rxmicro.http.HttpHeaders.SERVER;
import static io.rxmicro.json.JsonHelper.toJsonString;
import static io.rxmicro.json.JsonTypes.asJsonObject;
import static io.rxmicro.json.JsonTypes.isJsonObject;
import static io.rxmicro.rest.server.exchange.json.local.Constants.MESSAGE;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class JsonHttpErrorResponseBodyBuilder implements HttpErrorResponseBodyBuilder {

    @Override
    public HttpResponse build(final HttpResponse emptyResponse,
                              final int status,
                              final String message) {
        emptyResponse.setStatus(status);
        emptyResponse.setHeader(CONTENT_TYPE, CONTENT_TYPE_APPLICATION_JSON);
        emptyResponse.setContent(createErrorJson(String.valueOf(message)));
        return emptyResponse;
    }

    @Override
    public boolean isRxMicroError(final HttpCallFailedException e) {
        final String server = e.getHeaders().getValue(SERVER);
        if (server != null && server.startsWith(RX_MICRO_FRAMEWORK_NAME)) {
            return true;
        }
        if (CONTENT_TYPE_APPLICATION_JSON.equals(e.getHeaders().getValue(CONTENT_TYPE))) {
            try {
                final Object json = JsonHelper.readJson(e.getBodyAsString());
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

    @Override
    public HttpResponse build(final HttpResponse emptyResponse,
                              final HttpCallFailedException e) {
        emptyResponse.setStatus(e.getStatusCode());
        emptyResponse.setVersion(e.getVersion());
        emptyResponse.setOrAddHeaders(e.getHeaders());
        if (e.isBodyPresent()) {
            emptyResponse.setContent(e.getBody());
        }
        return emptyResponse;
    }

    private String createErrorJson(final String message) {
        return toJsonString(Map.of(MESSAGE, message));
    }
}
