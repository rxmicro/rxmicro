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
import io.rxmicro.http.HttpVersion;

import java.nio.charset.Charset;

import static io.rxmicro.common.CommonConstants.EMPTY_STRING;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Low-level interface that contains the received HTTP response data from HTTP server.
 *
 * @author nedis
 * @since 0.8
 */
public interface ClientHttpResponse {

    /**
     * Returns the received status code.
     *
     * @return the received status code
     */
    int getStatusCode();

    /**
     * Returns the {@link HttpVersion} of the received HTTP response.
     *
     * @return the {@link HttpVersion} of the received HTTP response
     */
    HttpVersion getVersion();

    /**
     * Returns the {@link HttpHeaders} of the received HTTP response.
     *
     * @return the {@link HttpHeaders} of the received HTTP response
     */
    HttpHeaders getHeaders();

    /**
     * Returns {@code true} if the received HTTP response does not contain HTTP body.
     *
     * @return {@code true} if the received HTTP response does not contain HTTP body
     */
    boolean isBodyEmpty();

    /**
     * Returns low-level object that represents received HTTP body.
     *
     * <p>
     * Type of returned value is depended on message exchange format used for HTTP client.
     *
     * @return low-level object that represents received HTTP body
     * or empty object (empty map or empty list or empty array, etc) if body is not present
     */
    Object getBody();

    /**
     * Returns byte array of the received HTTP body.
     *
     * <p>
     * If body is not present, this method returns empty array.
     *
     * @return byte array of the received HTTP body or empty array if body is not present
     */
    byte[] getBodyAsBytes();

    /**
     * Returns the received HTTP body as UTF8 string.
     *
     * @return the received HTTP body as UTF8 string or empty string if body is not present
     */
    default String getBodyAsUTF8String() {
        return getBodyAsString(UTF_8);
    }

    /**
     * Returns the received HTTP body as string using the specified charset.
     *
     * @param charset the specified charset
     * @return the received HTTP body as UTF8 string or empty string if body is not present
     */
    default String getBodyAsString(final Charset charset) {
        if (isBodyEmpty()) {
            return EMPTY_STRING;
        } else {
            return new String(getBodyAsBytes(), charset);
        }
    }
}
