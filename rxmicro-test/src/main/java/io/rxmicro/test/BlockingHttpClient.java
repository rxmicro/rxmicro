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

package io.rxmicro.test;

import io.rxmicro.http.HttpHeaders;
import io.rxmicro.http.QueryParams;
import io.rxmicro.rest.client.HttpClientTimeoutException;
import io.rxmicro.runtime.AutoRelease;

import static io.rxmicro.http.QueryParams.joinPath;

/**
 * The special <strong>blocking</strong> HTTP client to execute HTTP requests during testing.
 *
 * @author nedis
 * @see BlockingHttpClientSettings
 * @since 0.1
 */
public interface BlockingHttpClient extends AutoRelease {

    /**
     * HTTP GET method.
     */
    String GET = "GET";

    /**
     * HTTP HEAD method.
     */
    String HEAD = "HEAD";

    /**
     * HTTP DELETE method.
     */
    String DELETE = "DELETE";

    /**
     * HTTP OPTIONS method.
     */
    String OPTIONS = "OPTIONS";

    /**
     * HTTP POST method.
     */
    String POST = "POST";

    /**
     * HTTP PUT method.
     */
    String PUT = "PUT";

    /**
     * HTTP PATCH method.
     */
    String PATCH = "PATCH";

    // ------------------------------------------------- GET METHOD ----------------------------------------------------

    /**
     * Invokes the {@code GET} HTTP blocking request.
     *
     * @param path the relative URL path. If {@code path=null} then '/' URL value will be used.
     * @param headers the custom HTTP request headers
     * @return the received {@link ClientHttpResponse} from the HTTP server
     * @throws NullPointerException is any method parameter is {@code null}
     * @throws HttpClientTimeoutException if HTTP connect timed out occurs to the HTTP server
     * @throws IllegalArgumentException is any request parameter is invalid
     */
    default ClientHttpResponse get(final String path,
                                   final HttpHeaders headers) {
        return send(GET, path, headers);
    }

    /**
     * Invokes the {@code GET} HTTP blocking request.
     *
     * @param path the relative URL path. If {@code path=null} then '/' URL value will be used.
     * @return the received {@link ClientHttpResponse} from the HTTP server
     * @throws NullPointerException is any method parameter is {@code null}
     * @throws HttpClientTimeoutException if HTTP connect timed out occurs to the HTTP server
     * @throws IllegalArgumentException is any request parameter is invalid
     */
    default ClientHttpResponse get(final String path) {
        return send(GET, path, HttpHeaders.of());
    }

    /**
     * Invokes the {@code GET} HTTP blocking request.
     *
     * @param path the relative URL path. If {@code path=null} then '/' URL value will be used.
     * @param headers the custom HTTP request headers
     * @param parameters the custom HTTP query parameters
     * @return the received {@link ClientHttpResponse} from the HTTP server
     * @throws NullPointerException is any method parameter is {@code null}
     * @throws HttpClientTimeoutException if HTTP connect timed out occurs to the HTTP server
     * @throws IllegalArgumentException is any request parameter is invalid
     */
    default ClientHttpResponse get(final String path,
                                   final HttpHeaders headers,
                                   final QueryParams parameters) {
        return send(GET, joinPath(path, parameters), headers);
    }

    /**
     * Invokes the {@code GET} HTTP blocking request.
     *
     * @param path the relative URL path. If {@code path=null} then '/' URL value will be used.
     * @param parameters the custom HTTP query parameters
     * @return the received {@link ClientHttpResponse} from the HTTP server
     * @throws NullPointerException is any method parameter is {@code null}
     * @throws HttpClientTimeoutException if HTTP connect timed out occurs to the HTTP server
     * @throws IllegalArgumentException is any request parameter is invalid
     */
    default ClientHttpResponse get(final String path,
                                   final QueryParams parameters) {
        return send(GET, joinPath(path, parameters), HttpHeaders.of());
    }

    // ------------------------------------------------ HEAD METHOD ----------------------------------------------------

    /**
     * Invokes the {@code HEAD} HTTP blocking request.
     *
     * @param path the relative URL path. If {@code path=null} then '/' URL value will be used.
     * @param headers the custom HTTP request headers
     * @return the received {@link ClientHttpResponse} from the HTTP server
     * @throws NullPointerException is any method parameter is {@code null}
     * @throws HttpClientTimeoutException if HTTP connect timed out occurs to the HTTP server
     * @throws IllegalArgumentException is any request parameter is invalid
     */
    default ClientHttpResponse head(final String path,
                                    final HttpHeaders headers) {
        return send(HEAD, path, headers);
    }

    /**
     * Invokes the {@code HEAD} HTTP blocking request.
     *
     * @param path the relative URL path. If {@code path=null} then '/' URL value will be used.
     * @return the received {@link ClientHttpResponse} from the HTTP server
     * @throws NullPointerException is any method parameter is {@code null}
     * @throws HttpClientTimeoutException if HTTP connect timed out occurs to the HTTP server
     * @throws IllegalArgumentException is any request parameter is invalid
     */
    default ClientHttpResponse head(final String path) {
        return send(HEAD, path, HttpHeaders.of());
    }

    /**
     * Invokes the {@code HEAD} HTTP blocking request.
     *
     * @param path the relative URL path. If {@code path=null} then '/' URL value will be used.
     * @param headers the custom HTTP request headers
     * @param parameters the custom HTTP query parameters
     * @return the received {@link ClientHttpResponse} from the HTTP server
     * @throws NullPointerException is any method parameter is {@code null}
     * @throws HttpClientTimeoutException if HTTP connect timed out occurs to the HTTP server
     * @throws IllegalArgumentException is any request parameter is invalid
     */
    default ClientHttpResponse head(final String path,
                                    final HttpHeaders headers,
                                    final QueryParams parameters) {
        return send(HEAD, joinPath(path, parameters), headers);
    }

    /**
     * Invokes the {@code HEAD} HTTP blocking request.
     *
     * @param path the relative URL path. If {@code path=null} then '/' URL value will be used.
     * @param parameters the custom HTTP query parameters
     * @return the received {@link ClientHttpResponse} from the HTTP server
     * @throws NullPointerException is any method parameter is {@code null}
     * @throws HttpClientTimeoutException if HTTP connect timed out occurs to the HTTP server
     * @throws IllegalArgumentException is any request parameter is invalid
     */
    default ClientHttpResponse head(final String path,
                                    final QueryParams parameters) {
        return send(HEAD, joinPath(path, parameters), HttpHeaders.of());
    }

    // ----------------------------------------------- DELETE METHOD ---------------------------------------------------

    /**
     * Invokes the {@code DELETE} HTTP blocking request.
     *
     * @param path the relative URL path. If {@code path=null} then '/' URL value will be used.
     * @param headers the custom HTTP request headers
     * @return the received {@link ClientHttpResponse} from the HTTP server
     * @throws NullPointerException is any method parameter is {@code null}
     * @throws HttpClientTimeoutException if HTTP connect timed out occurs to the HTTP server
     * @throws IllegalArgumentException is any request parameter is invalid
     */
    default ClientHttpResponse delete(final String path,
                                      final HttpHeaders headers) {
        return send(DELETE, path, headers);
    }

    /**
     * Invokes the {@code DELETE} HTTP blocking request.
     *
     * @param path the relative URL path. If {@code path=null} then '/' URL value will be used.
     * @return the received {@link ClientHttpResponse} from the HTTP server
     * @throws NullPointerException is any method parameter is {@code null}
     * @throws HttpClientTimeoutException if HTTP connect timed out occurs to the HTTP server
     * @throws IllegalArgumentException is any request parameter is invalid
     */
    default ClientHttpResponse delete(final String path) {
        return send(DELETE, path, HttpHeaders.of());
    }

    /**
     * Invokes the {@code DELETE} HTTP blocking request.
     *
     * @param path the relative URL path. If {@code path=null} then '/' URL value will be used.
     * @param headers the custom HTTP request headers
     * @param parameters the custom HTTP query parameters
     * @return the received {@link ClientHttpResponse} from the HTTP server
     * @throws NullPointerException is any method parameter is {@code null}
     * @throws HttpClientTimeoutException if HTTP connect timed out occurs to the HTTP server
     * @throws IllegalArgumentException is any request parameter is invalid
     */
    default ClientHttpResponse delete(final String path,
                                      final HttpHeaders headers,
                                      final QueryParams parameters) {
        return send(DELETE, joinPath(path, parameters), headers);
    }

    /**
     * Invokes the {@code DELETE} HTTP blocking request.
     *
     * @param path the relative URL path. If {@code path=null} then '/' URL value will be used.
     * @param parameters the custom HTTP query parameters
     * @return the received {@link ClientHttpResponse} from the HTTP server
     * @throws NullPointerException is any method parameter is {@code null}
     * @throws HttpClientTimeoutException if HTTP connect timed out occurs to the HTTP server
     * @throws IllegalArgumentException is any request parameter is invalid
     */
    default ClientHttpResponse delete(final String path,
                                      final QueryParams parameters) {
        return send(DELETE, joinPath(path, parameters), HttpHeaders.of());
    }

    // ---------------------------------------------- OPTIONS METHOD ---------------------------------------------------

    /**
     * Invokes the {@code OPTIONS} HTTP blocking request.
     *
     * @param path the relative URL path. If {@code path=null} then '/' URL value will be used.
     * @param headers the custom HTTP request headers
     * @return the received {@link ClientHttpResponse} from the HTTP server
     * @throws NullPointerException is any method parameter is {@code null}
     * @throws HttpClientTimeoutException if HTTP connect timed out occurs to the HTTP server
     * @throws IllegalArgumentException is any request parameter is invalid
     */
    default ClientHttpResponse options(final String path,
                                       final HttpHeaders headers) {
        return send(OPTIONS, path, headers);
    }

    /**
     * Invokes the {@code OPTIONS} HTTP blocking request.
     *
     * @param path the relative URL path. If {@code path=null} then '/' URL value will be used.
     * @return the received {@link ClientHttpResponse} from the HTTP server
     * @throws NullPointerException is any method parameter is {@code null}
     * @throws HttpClientTimeoutException if HTTP connect timed out occurs to the HTTP server
     * @throws IllegalArgumentException is any request parameter is invalid
     */
    default ClientHttpResponse options(final String path) {
        return send(OPTIONS, path, HttpHeaders.of());
    }

    /**
     * Invokes the {@code OPTIONS} HTTP blocking request.
     *
     * @param path the relative URL path. If {@code path=null} then '/' URL value will be used.
     * @param headers the custom HTTP request headers
     * @param parameters the custom HTTP query parameters
     * @return the received {@link ClientHttpResponse} from the HTTP server
     * @throws NullPointerException is any method parameter is {@code null}
     * @throws HttpClientTimeoutException if HTTP connect timed out occurs to the HTTP server
     * @throws IllegalArgumentException is any request parameter is invalid
     */
    default ClientHttpResponse options(final String path,
                                       final HttpHeaders headers,
                                       final QueryParams parameters) {
        return send(OPTIONS, joinPath(path, parameters), headers);
    }

    /**
     * Invokes the {@code OPTIONS} HTTP blocking request.
     *
     * @param path the relative URL path. If {@code path=null} then '/' URL value will be used.
     * @param parameters the custom HTTP query parameters
     * @return the received {@link ClientHttpResponse} from the HTTP server
     * @throws NullPointerException is any method parameter is {@code null}
     * @throws HttpClientTimeoutException if HTTP connect timed out occurs to the HTTP server
     * @throws IllegalArgumentException is any request parameter is invalid
     */
    default ClientHttpResponse options(final String path,
                                       final QueryParams parameters) {
        return send(OPTIONS, joinPath(path, parameters), HttpHeaders.of());
    }

    // ------------------------------------------------ POST METHOD ----------------------------------------------------

    /**
     * Invokes the {@code POST} HTTP blocking request.
     *
     * @param path the relative URL path. If {@code path=null} then '/' URL value will be used.
     * @param headers the custom HTTP request headers
     * @return the received {@link ClientHttpResponse} from the HTTP server
     * @throws NullPointerException is any method parameter is {@code null}
     * @throws HttpClientTimeoutException if HTTP connect timed out occurs to the HTTP server
     * @throws IllegalArgumentException is any request parameter is invalid
     */
    default ClientHttpResponse post(final String path,
                                    final HttpHeaders headers) {
        return send(POST, path, headers);
    }

    /**
     * Invokes the {@code POST} HTTP blocking request.
     *
     * @param path the relative URL path. If {@code path=null} then '/' URL value will be used.
     * @return the received {@link ClientHttpResponse} from the HTTP server
     * @throws NullPointerException is any method parameter is {@code null}
     * @throws HttpClientTimeoutException if HTTP connect timed out occurs to the HTTP server
     * @throws IllegalArgumentException is any request parameter is invalid
     */
    default ClientHttpResponse post(final String path) {
        return send(POST, path, HttpHeaders.of());
    }

    /**
     * Invokes the {@code POST} HTTP blocking request.
     *
     * @param path the relative URL path. If {@code path=null} then '/' URL value will be used.
     * @param headers the custom HTTP request headers
     * @param parameters the custom HTTP query parameters
     * @return the received {@link ClientHttpResponse} from the HTTP server
     * @throws NullPointerException is any method parameter is {@code null}
     * @throws HttpClientTimeoutException if HTTP connect timed out occurs to the HTTP server
     * @throws IllegalArgumentException is any request parameter is invalid
     */
    default ClientHttpResponse post(final String path,
                                    final HttpHeaders headers,
                                    final QueryParams parameters) {
        return send(POST, joinPath(path, parameters), headers);
    }

    /**
     * Invokes the {@code POST} HTTP blocking request.
     *
     * @param path the relative URL path. If {@code path=null} then '/' URL value will be used.
     * @param parameters the custom HTTP query parameters
     * @return the received {@link ClientHttpResponse} from the HTTP server
     * @throws NullPointerException is any method parameter is {@code null}
     * @throws HttpClientTimeoutException if HTTP connect timed out occurs to the HTTP server
     * @throws IllegalArgumentException is any request parameter is invalid
     */
    default ClientHttpResponse post(final String path,
                                    final QueryParams parameters) {
        return send(POST, joinPath(path, parameters), HttpHeaders.of());
    }

    /**
     * Invokes the {@code POST} HTTP blocking request.
     *
     * @param path the relative URL path. If {@code path=null} then '/' URL value will be used.
     * @param headers the custom HTTP request headers
     * @param body the HTTP request body
     * @return the received {@link ClientHttpResponse} from the HTTP server
     * @throws NullPointerException is any method parameter is {@code null}
     * @throws HttpClientTimeoutException if HTTP connect timed out occurs to the HTTP server
     * @throws IllegalArgumentException is any request parameter is invalid
     */
    default ClientHttpResponse post(final String path,
                                    final HttpHeaders headers,
                                    final Object body) {
        return send(POST, path, headers, body);
    }

    /**
     * Invokes the {@code POST} HTTP blocking request.
     *
     * @param path the relative URL path. If {@code path=null} then '/' URL value will be used.
     * @param body the HTTP request body
     * @return the received {@link ClientHttpResponse} from the HTTP server
     * @throws NullPointerException is any method parameter is {@code null}
     * @throws HttpClientTimeoutException if HTTP connect timed out occurs to the HTTP server
     * @throws IllegalArgumentException is any request parameter is invalid
     */
    default ClientHttpResponse post(final String path,
                                    final Object body) {
        return send(POST, path, HttpHeaders.of(), body);
    }

    // ------------------------------------------------- PUT METHOD ----------------------------------------------------

    /**
     * Invokes the {@code PUT} HTTP blocking request.
     *
     * @param path the relative URL path. If {@code path=null} then '/' URL value will be used.
     * @param headers the custom HTTP request headers
     * @return the received {@link ClientHttpResponse} from the HTTP server
     * @throws NullPointerException is any method parameter is {@code null}
     * @throws HttpClientTimeoutException if HTTP connect timed out occurs to the HTTP server
     * @throws IllegalArgumentException is any request parameter is invalid
     */
    default ClientHttpResponse put(final String path,
                                   final HttpHeaders headers) {
        return send(PUT, path, headers);
    }

    /**
     * Invokes the {@code PUT} HTTP blocking request.
     *
     * @param path the relative URL path. If {@code path=null} then '/' URL value will be used.
     * @return the received {@link ClientHttpResponse} from the HTTP server
     * @throws NullPointerException is any method parameter is {@code null}
     * @throws HttpClientTimeoutException if HTTP connect timed out occurs to the HTTP server
     * @throws IllegalArgumentException is any request parameter is invalid
     */
    default ClientHttpResponse put(final String path) {
        return send(PUT, path, HttpHeaders.of());
    }

    /**
     * Invokes the {@code PUT} HTTP blocking request.
     *
     * @param path the relative URL path. If {@code path=null} then '/' URL value will be used.
     * @param headers the custom HTTP request headers
     * @param parameters the custom HTTP query parameters
     * @return the received {@link ClientHttpResponse} from the HTTP server
     * @throws NullPointerException is any method parameter is {@code null}
     * @throws HttpClientTimeoutException if HTTP connect timed out occurs to the HTTP server
     * @throws IllegalArgumentException is any request parameter is invalid
     */
    default ClientHttpResponse put(final String path,
                                   final HttpHeaders headers,
                                   final QueryParams parameters) {
        return send(PUT, joinPath(path, parameters), headers);
    }

    /**
     * Invokes the {@code PUT} HTTP blocking request.
     *
     * @param path the relative URL path. If {@code path=null} then '/' URL value will be used.
     * @param parameters the custom HTTP query parameters
     * @return the received {@link ClientHttpResponse} from the HTTP server
     * @throws NullPointerException is any method parameter is {@code null}
     * @throws HttpClientTimeoutException if HTTP connect timed out occurs to the HTTP server
     * @throws IllegalArgumentException is any request parameter is invalid
     */
    default ClientHttpResponse put(final String path,
                                   final QueryParams parameters) {
        return send(PUT, joinPath(path, parameters), HttpHeaders.of());
    }

    /**
     * Invokes the {@code PUT} HTTP blocking request.
     *
     * @param path the relative URL path. If {@code path=null} then '/' URL value will be used.
     * @param headers the custom HTTP request headers
     * @param body the HTTP request body
     * @return the received {@link ClientHttpResponse} from the HTTP server
     * @throws NullPointerException is any method parameter is {@code null}
     * @throws HttpClientTimeoutException if HTTP connect timed out occurs to the HTTP server
     * @throws IllegalArgumentException is any request parameter is invalid
     */
    default ClientHttpResponse put(final String path,
                                   final HttpHeaders headers,
                                   final Object body) {
        return send(PUT, path, headers, body);
    }

    /**
     * Invokes the {@code PUT} HTTP blocking request.
     *
     * @param path the relative URL path. If {@code path=null} then '/' URL value will be used.
     * @param body the HTTP request body
     * @return the received {@link ClientHttpResponse} from the HTTP server
     * @throws NullPointerException is any method parameter is {@code null}
     * @throws HttpClientTimeoutException if HTTP connect timed out occurs to the HTTP server
     * @throws IllegalArgumentException is any request parameter is invalid
     */
    default ClientHttpResponse put(final String path,
                                   final Object body) {
        return send(PUT, path, HttpHeaders.of(), body);
    }

    // ------------------------------------------------ PATCH METHOD ---------------------------------------------------

    /**
     * Invokes the {@code PATCH} HTTP blocking request.
     *
     * @param path the relative URL path. If {@code path=null} then '/' URL value will be used.
     * @param headers the custom HTTP request headers
     * @return the received {@link ClientHttpResponse} from the HTTP server
     * @throws NullPointerException is any method parameter is {@code null}
     * @throws HttpClientTimeoutException if HTTP connect timed out occurs to the HTTP server
     * @throws IllegalArgumentException is any request parameter is invalid
     */
    default ClientHttpResponse patch(final String path,
                                     final HttpHeaders headers) {
        return send(PATCH, path, headers);
    }

    /**
     * Invokes the {@code PATCH} HTTP blocking request.
     *
     * @param path the relative URL path. If {@code path=null} then '/' URL value will be used.
     * @return the received {@link ClientHttpResponse} from the HTTP server
     * @throws NullPointerException is any method parameter is {@code null}
     * @throws HttpClientTimeoutException if HTTP connect timed out occurs to the HTTP server
     * @throws IllegalArgumentException is any request parameter is invalid
     */
    default ClientHttpResponse patch(final String path) {
        return send(PATCH, path, HttpHeaders.of());
    }

    /**
     * Invokes the {@code PATCH} HTTP blocking request.
     *
     * @param path the relative URL path. If {@code path=null} then '/' URL value will be used.
     * @param headers the custom HTTP request headers
     * @param parameters the custom HTTP query parameters
     * @return the received {@link ClientHttpResponse} from the HTTP server
     * @throws NullPointerException is any method parameter is {@code null}
     * @throws HttpClientTimeoutException if HTTP connect timed out occurs to the HTTP server
     * @throws IllegalArgumentException is any request parameter is invalid
     */
    default ClientHttpResponse patch(final String path,
                                     final HttpHeaders headers,
                                     final QueryParams parameters) {
        return send(PATCH, joinPath(path, parameters), headers);
    }

    /**
     * Invokes the {@code PATCH} HTTP blocking request.
     *
     * @param path the relative URL path. If {@code path=null} then '/' URL value will be used.
     * @param parameters the custom HTTP query parameters
     * @return the received {@link ClientHttpResponse} from the HTTP server
     * @throws NullPointerException is any method parameter is {@code null}
     * @throws HttpClientTimeoutException if HTTP connect timed out occurs to the HTTP server
     * @throws IllegalArgumentException is any request parameter is invalid
     */
    default ClientHttpResponse patch(final String path,
                                     final QueryParams parameters) {
        return send(PATCH, joinPath(path, parameters), HttpHeaders.of());
    }

    /**
     * Invokes the {@code PATCH} HTTP blocking request.
     *
     * @param path the relative URL path. If {@code path=null} then '/' URL value will be used.
     * @param headers the custom HTTP request headers
     * @param body the HTTP request body
     * @return the received {@link ClientHttpResponse} from the HTTP server
     * @throws NullPointerException is any method parameter is {@code null}
     * @throws HttpClientTimeoutException if HTTP connect timed out occurs to the HTTP server
     * @throws IllegalArgumentException is any request parameter is invalid
     */
    default ClientHttpResponse patch(final String path,
                                     final HttpHeaders headers,
                                     final Object body) {
        return send(PATCH, path, headers, body);
    }

    /**
     * Invokes the {@code PATCH} HTTP blocking request.
     *
     * @param path the relative URL path. If {@code path=null} then '/' URL value will be used.
     * @param body the HTTP request body
     * @return the received {@link ClientHttpResponse} from the HTTP server
     * @throws NullPointerException is any method parameter is {@code null}
     * @throws HttpClientTimeoutException if HTTP connect timed out occurs to the HTTP server
     * @throws IllegalArgumentException is any request parameter is invalid
     */
    default ClientHttpResponse patch(final String path,
                                     final Object body) {
        return send(PATCH, path, HttpHeaders.of(), body);
    }

    // ------------------------------------------------ ANY METHODS ----------------------------------------------------

    /**
     * Invokes the HTTP blocking request with the custom HTTP method.
     *
     * @param method the custom HTTP method
     * @param path the relative URL path. If {@code path=null} then '/' URL value will be used.
     * @param headers the custom HTTP request headers
     * @return the received {@link ClientHttpResponse} from the HTTP server
     * @throws NullPointerException is any method parameter is {@code null}
     * @throws HttpClientTimeoutException if HTTP connect timed out occurs to the HTTP server
     * @throws IllegalArgumentException is any request parameter is invalid
     */
    ClientHttpResponse send(String method,
                            String path,
                            HttpHeaders headers);

    /**
     * Invokes the HTTP blocking request with the custom HTTP method.
     *
     * @param method the custom HTTP method
     * @param path the relative URL path. If {@code path=null} then '/' URL value will be used.
     * @return the received {@link ClientHttpResponse} from the HTTP server
     * @throws NullPointerException is any method parameter is {@code null}
     * @throws HttpClientTimeoutException if HTTP connect timed out occurs to the HTTP server
     * @throws IllegalArgumentException is any request parameter is invalid
     */
    default ClientHttpResponse send(final String method,
                                    final String path) {
        return send(method, path, HttpHeaders.of());
    }

    /**
     * Invokes the HTTP blocking request with the custom HTTP method.
     *
     * @param method the custom HTTP method
     * @param path the relative URL path. If {@code path=null} then '/' URL value will be used.
     * @param headers the custom HTTP request headers
     * @param body the HTTP request body
     * @return the received {@link ClientHttpResponse} from the HTTP server
     * @throws NullPointerException is any method parameter is {@code null}
     * @throws HttpClientTimeoutException if HTTP connect timed out occurs to the HTTP server
     * @throws IllegalArgumentException is any request parameter is invalid
     */
    ClientHttpResponse send(String method,
                            String path,
                            HttpHeaders headers,
                            Object body);

    /**
     * Invokes the HTTP blocking request with the custom HTTP method.
     *
     * @param method the custom HTTP method
     * @param path the relative URL path. If {@code path=null} then '/' URL value will be used.
     * @param body the HTTP request body
     * @return the received {@link ClientHttpResponse} from the HTTP server
     * @throws NullPointerException is any method parameter is {@code null}
     * @throws HttpClientTimeoutException if HTTP connect timed out occurs to the HTTP server
     * @throws IllegalArgumentException is any request parameter is invalid
     */
    default ClientHttpResponse send(final String method,
                                    final String path,
                                    final Object body) {
        return send(method, path, HttpHeaders.of(), body);
    }
}
