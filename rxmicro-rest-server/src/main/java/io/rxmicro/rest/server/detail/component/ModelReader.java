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

package io.rxmicro.rest.server.detail.component;

import io.rxmicro.http.QueryParams;
import io.rxmicro.http.error.ValidationException;
import io.rxmicro.rest.local.FromStringValueConverter;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.model.HttpRequest;
import io.rxmicro.rest.server.internal.model.QueryParamsImpl;

import java.nio.charset.StandardCharsets;

import static io.rxmicro.http.local.HttpValidators.validateQueryParameterNameCharacter;
import static java.net.URLDecoder.decode;

/**
 * Used by generated code that created by the {@code RxMicro Annotation Processor}.
 *
 * @author nedis
 * @hidden
 * @since 0.1
 */
public abstract class ModelReader<T> extends FromStringValueConverter {

    public T read(final PathVariableMapping pathVariableMapping,
                  final HttpRequest request,
                  final boolean readParametersFromBody) {
        throw new AbstractMethodError("The RxMicro Annotation Processor did not generate an implementation of this method!");
    }

    protected final QueryParams extractParams(final String queryString) {
        try {
            final QueryParamsImpl queryParams = new QueryParamsImpl();
            int start = 0;
            String name = null;
            String value;
            boolean decode = false;
            for (int i = 0; i < queryString.length(); i++) {
                final char ch = queryString.charAt(i);
                if (ch == '=') {
                    name = queryString.substring(start, i);
                    start = i + 1;
                } else if (ch == '&') {
                    value = queryString.substring(start, i);
                    start = i + 1;
                    add(queryParams, name, value, decode);
                    name = null;
                    decode = false;
                } else {
                    if (name == null) {
                        validateQueryParameterNameCharacter(ch);
                    } else if (ch == '+' || ch == '%') {
                        decode = true;
                    }
                }
            }
            if (name != null) {
                value = queryString.substring(start);
                add(queryParams, name, value, decode);
            }
            return queryParams;
        } catch (final IllegalArgumentException ex) {
            throw new ValidationException("Query string is invalid: ?", ex.getMessage());
        }
    }

    private void add(final QueryParamsImpl queryParams,
                     final String name,
                     final String value,
                     final boolean decode) {
        if (!value.isEmpty()) {
            if (decode) {
                String decodedValue;
                try {
                    decodedValue = decode(value, StandardCharsets.UTF_8);
                } catch (final IllegalArgumentException ignored) {
                    decodedValue = value;
                }
                queryParams.setOrAdd(name, decodedValue);
            } else {
                queryParams.setOrAdd(name, value);
            }
        }
    }
}
