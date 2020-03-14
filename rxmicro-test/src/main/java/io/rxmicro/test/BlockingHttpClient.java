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

package io.rxmicro.test;

import io.rxmicro.http.HttpHeaders;
import io.rxmicro.http.QueryParams;
import io.rxmicro.http.client.ClientHttpResponse;
import io.rxmicro.runtime.AutoRelease;

import static io.rxmicro.http.HttpHeaders.EMPTY_HEADERS;
import static io.rxmicro.http.QueryParams.joinPath;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public interface BlockingHttpClient extends AutoRelease {

    // ------------------------------------------------- GET METHOD ----------------------------------------------------

    default ClientHttpResponse get(final String path,
                                   final HttpHeaders headers) {
        return send("GET", path, headers);
    }

    default ClientHttpResponse get(final String path) {
        return send("GET", path, EMPTY_HEADERS);
    }

    default ClientHttpResponse get(final String path,
                                   final HttpHeaders headers,
                                   final QueryParams parameters) {
        return send("GET", joinPath(path, parameters), headers);
    }

    default ClientHttpResponse get(final String path,
                                   final QueryParams parameters) {
        return send("GET", joinPath(path, parameters), EMPTY_HEADERS);
    }

    // ------------------------------------------------ HEAD METHOD ----------------------------------------------------

    default ClientHttpResponse head(final String path,
                                    final HttpHeaders headers) {
        return send("HEAD", path, headers);
    }

    default ClientHttpResponse head(final String path) {
        return send("HEAD", path, EMPTY_HEADERS);
    }

    default ClientHttpResponse head(final String path,
                                    final HttpHeaders headers,
                                    final QueryParams parameters) {
        return send("HEAD", joinPath(path, parameters), headers);
    }

    default ClientHttpResponse head(final String path,
                                    final QueryParams parameters) {
        return send("HEAD", joinPath(path, parameters), EMPTY_HEADERS);
    }

    // ----------------------------------------------- DELETE METHOD ---------------------------------------------------

    default ClientHttpResponse delete(final String path,
                                      final HttpHeaders headers) {
        return send("DELETE", path, headers);
    }

    default ClientHttpResponse delete(final String path) {
        return send("DELETE", path, EMPTY_HEADERS);
    }

    default ClientHttpResponse delete(final String path,
                                      final HttpHeaders headers,
                                      final QueryParams parameters) {
        return send("DELETE", joinPath(path, parameters), headers);
    }

    default ClientHttpResponse delete(final String path,
                                      final QueryParams parameters) {
        return send("DELETE", joinPath(path, parameters), EMPTY_HEADERS);
    }

    // ---------------------------------------------- OPTIONS METHOD ---------------------------------------------------

    default ClientHttpResponse options(final String path,
                                       final HttpHeaders headers) {
        return send("OPTIONS", path, headers);
    }

    default ClientHttpResponse options(final String path) {
        return send("OPTIONS", path, EMPTY_HEADERS);
    }

    default ClientHttpResponse options(final String path,
                                       final HttpHeaders headers,
                                       final QueryParams parameters) {
        return send("OPTIONS", joinPath(path, parameters), headers);
    }

    default ClientHttpResponse options(final String path,
                                       final QueryParams parameters) {
        return send("OPTIONS", joinPath(path, parameters), EMPTY_HEADERS);
    }

    // ------------------------------------------------ POST METHOD ----------------------------------------------------

    default ClientHttpResponse post(final String path,
                                    final HttpHeaders headers) {
        return send("POST", path, headers);
    }

    default ClientHttpResponse post(final String path) {
        return send("POST", path, EMPTY_HEADERS);
    }

    default ClientHttpResponse post(final String path,
                                    final HttpHeaders headers,
                                    final QueryParams parameters) {
        return send("POST", joinPath(path, parameters), headers);
    }

    default ClientHttpResponse post(final String path,
                                    final QueryParams parameters) {
        return send("POST", joinPath(path, parameters), EMPTY_HEADERS);
    }

    default ClientHttpResponse post(final String path,
                                    final HttpHeaders headers,
                                    final Object body) {
        return send("POST", path, headers, body);
    }

    default ClientHttpResponse post(final String path,
                                    final Object body) {
        return send("POST", path, EMPTY_HEADERS, body);
    }

    // ------------------------------------------------- PUT METHOD ----------------------------------------------------

    default ClientHttpResponse put(final String path,
                                   final HttpHeaders headers) {
        return send("PUT", path, headers);
    }

    default ClientHttpResponse put(final String path) {
        return send("PUT", path, EMPTY_HEADERS);
    }

    default ClientHttpResponse put(final String path,
                                   final HttpHeaders headers,
                                   final QueryParams parameters) {
        return send("PUT", joinPath(path, parameters), headers);
    }

    default ClientHttpResponse put(final String path,
                                   final QueryParams parameters) {
        return send("PUT", joinPath(path, parameters), EMPTY_HEADERS);
    }

    default ClientHttpResponse put(final String path,
                                   final HttpHeaders headers,
                                   final Object body) {
        return send("PUT", path, headers, body);
    }

    default ClientHttpResponse put(final String path,
                                   final Object body) {
        return send("PUT", path, EMPTY_HEADERS, body);
    }

    // ------------------------------------------------ PATCH METHOD ---------------------------------------------------

    default ClientHttpResponse patch(final String path,
                                     final HttpHeaders headers) {
        return send("PATCH", path, headers);
    }

    default ClientHttpResponse patch(final String path) {
        return send("PATCH", path, EMPTY_HEADERS);
    }

    default ClientHttpResponse patch(final String path,
                                     final HttpHeaders headers,
                                     final QueryParams parameters) {
        return send("PATCH", joinPath(path, parameters), headers);
    }

    default ClientHttpResponse patch(final String path,
                                     final QueryParams parameters) {
        return send("PATCH", joinPath(path, parameters), EMPTY_HEADERS);
    }

    default ClientHttpResponse patch(final String path,
                                     final HttpHeaders headers,
                                     final Object body) {
        return send("PATCH", path, headers, body);
    }

    default ClientHttpResponse patch(final String path,
                                     final Object body) {
        return send("PATCH", path, EMPTY_HEADERS, body);
    }

    // ------------------------------------------------ ANY METHODS ----------------------------------------------------

    ClientHttpResponse send(String method,
                            String path,
                            HttpHeaders headers);

    default ClientHttpResponse send(String method,
                                    String path) {
        return send(method, path, EMPTY_HEADERS);
    }

    ClientHttpResponse send(String method,
                            String path,
                            HttpHeaders headers,
                            Object body);

    default ClientHttpResponse send(String method,
                                    String path,
                                    Object body) {
        return send(method, path, EMPTY_HEADERS, body);
    }
}
