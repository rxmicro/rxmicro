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

package io.rxmicro.http.client;

import io.rxmicro.runtime.AutoRelease;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * A low-level reactive HTTP client
 *
 * @author nedis
 * @since 0.1
 * @see HttpClientFactory
 */
public interface HttpClient extends AutoRelease {

    /**
     * Sends the HTTP request
     *
     * @param method the HTTP method
     * @param path the HTTP path
     * @param headers the HTTP headers
     * @return the {@link CompletableFuture} that contains async result of sending the HTTP request
     */
    CompletableFuture<ClientHttpResponse> sendAsync(String method,
                                                    String path,
                                                    List<Map.Entry<String, String>> headers);

    /**
     * Sends the HTTP request
     *
     * @param method the HTTP method
     * @param path the HTTP path
     * @param headers the HTTP headers
     * @param body the HTTP body
     * @return the {@link CompletableFuture} that contains async result of sending the HTTP request
     */
    CompletableFuture<ClientHttpResponse> sendAsync(String method,
                                                    String path,
                                                    List<Map.Entry<String, String>> headers,
                                                    Object body);
}
