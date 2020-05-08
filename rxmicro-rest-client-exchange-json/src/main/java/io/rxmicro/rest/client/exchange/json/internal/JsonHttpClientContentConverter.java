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

package io.rxmicro.rest.client.exchange.json.internal;

import io.rxmicro.http.client.HttpClientContentConverter;
import io.rxmicro.json.JsonHelper;

import java.util.function.Function;

import static io.rxmicro.exchange.json.Constants.CONTENT_TYPE_APPLICATION_JSON;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author nedis
 * @since 0.1
 */
public final class JsonHttpClientContentConverter implements HttpClientContentConverter {

    @Override
    public Function<Object, byte[]> getRequestContentConverter() {
        return o -> {
            if (o == null) {
                return new byte[0];
            } else {
                return JsonHelper.toJsonString(o).getBytes(UTF_8);
            }
        };
    }

    @Override
    public Function<byte[], Object> getResponseContentConverter() {
        return bytes -> {
            if (bytes.length == 0) {
                return null;
            } else {
                return JsonHelper.readJson(new String(bytes, UTF_8));
            }
        };
    }

    @Override
    public String getContentType() {
        return CONTENT_TYPE_APPLICATION_JSON;
    }
}
