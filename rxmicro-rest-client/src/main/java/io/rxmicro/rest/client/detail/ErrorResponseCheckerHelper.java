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

package io.rxmicro.rest.client.detail;

import io.rxmicro.http.client.ClientHttpResponse;

import java.util.function.BiFunction;

import static io.rxmicro.common.util.Exceptions.reThrow;

/**
 * Used by generated code that created by the {@code RxMicro Annotation Processor}.
 *
 * @author nedis
 * @hidden
 * @since 0.1
 */
public final class ErrorResponseCheckerHelper {

    private static final int MIN_SUPPORTED_SUCCESS_CODE = 200;

    private static final int MAX_SUPPORTED_SUCCESS_CODE = 299;

    private static final BiFunction<ClientHttpResponse, Throwable, ClientHttpResponse> BI_FUNCTION =
            (clientHttpResponse, throwable) -> {
                if (throwable != null) {
                    return reThrow(throwable);
                }
                final int status = clientHttpResponse.getStatusCode();
                if (status >= MIN_SUPPORTED_SUCCESS_CODE && status <= MAX_SUPPORTED_SUCCESS_CODE) {
                    return clientHttpResponse;
                } else {
                    throw new HttpClientCallFailedException(clientHttpResponse);
                }
            };

    // Used by generated rest client code
    public static BiFunction<ClientHttpResponse, Throwable, ClientHttpResponse> throwExceptionIfNotSuccess() {
        return BI_FUNCTION;
    }

    private ErrorResponseCheckerHelper() {
    }
}
