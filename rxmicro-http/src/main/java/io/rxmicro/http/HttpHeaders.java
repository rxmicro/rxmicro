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

package io.rxmicro.http;

import io.rxmicro.http.internal.HttpHeadersImpl;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

/**
 * Represents a HTTP headers instance
 *
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public interface HttpHeaders {

    /**
     * `{code Accept}` standard HTTP header
     */
    String ACCEPT = "Accept";

    /**
     * `{code Accept-Charset}` standard HTTP header
     */
    String ACCEPT_CHARSET = "Accept-Charset";

    /**
     * `{code Accept-Encoding"}` standard HTTP header
     */
    String ACCEPT_ENCODING = "Accept-Encoding";

    /**
     * `{code Accept-Language}` standard HTTP header
     */
    String ACCEPT_LANGUAGE = "Accept-Language";

    /**
     * `{code Accept-Ranges}` standard HTTP header
     */
    String ACCEPT_RANGES = "Accept-Ranges";

    /**
     * `{code Accept-Patch}` standard HTTP header
     */
    String ACCEPT_PATCH = "Accept-Patch";

    /**
     * `{code Access-Control-Allow-Credentials}` standard HTTP header
     */
    String ACCESS_CONTROL_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";

    /**
     * `{code Access-Control-Allow-Headers}` standard HTTP header
     */
    String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";

    /**
     * `{code Access-Control-Allow-Methods}` standard HTTP header
     */
    String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";

    /**
     * `{code Access-Control-Allow-Origin}` standard HTTP header
     */
    String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";

    /**
     * `{code Access-Control-Expose-Headers}` standard HTTP header
     */
    String ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers";

    /**
     * `{code Access-Control-Max-Age}` standard HTTP header
     */
    String ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age";

    /**
     * `{code Access-Control-Request-Headers}` standard HTTP header
     */
    String ACCESS_CONTROL_REQUEST_HEADERS = "Access-Control-Request-Headers";

    /**
     * `{code Access-Control-Request-Method}` standard HTTP header
     */
    String ACCESS_CONTROL_REQUEST_METHOD = "Access-Control-Request-Method";

    /**
     * `{code Age}` standard HTTP header
     */
    String AGE = "Age";

    /**
     * `{code Allow}` standard HTTP header
     */
    String ALLOW = "Allow";

    /**
     * `{code Authorization}` standard HTTP header
     */
    String AUTHORIZATION = "Authorization";

    /**
     * `{code Cache-Control}` standard HTTP header
     */
    String CACHE_CONTROL = "Cache-Control";

    /**
     * `{code Connection}` standard HTTP header
     */
    String CONNECTION = "Connection";

    /**
     * `{code Content-Base}` standard HTTP header
     */
    String CONTENT_BASE = "Content-Base";

    /**
     * `{code Content-Encoding}` standard HTTP header
     */
    String CONTENT_ENCODING = "Content-Encoding";

    /**
     * `{code Content-Language}` standard HTTP header
     */
    String CONTENT_LANGUAGE = "Content-Language";

    /**
     * `{code Content-Length}` standard HTTP header
     */
    String CONTENT_LENGTH = "Content-Length";

    /**
     * `{code Content-Location}` standard HTTP header
     */
    String CONTENT_LOCATION = "Content-Location";

    /**
     * `{code Content-Transfer-Encoding}` standard HTTP header
     */
    String CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding";

    /**
     * `{code Content-MD5}` standard HTTP header
     */
    String CONTENT_MD5 = "Content-MD5";

    /**
     * `{code Content-Range}` standard HTTP header
     */
    String CONTENT_RANGE = "Content-Range";

    /**
     * `{code Content-Type}` standard HTTP header
     */
    String CONTENT_TYPE = "Content-Type";

    /**
     * `{code Cookie}` standard HTTP header
     */
    String COOKIE = "Cookie";

    /**
     * `{code Date}` standard HTTP header
     */
    String DATE = "Date";

    /**
     * `{code ETag}` standard HTTP header
     */
    String ETAG = "ETag";

    /**
     * `{code Expect}` standard HTTP header
     */
    String EXPECT = "Expect";

    /**
     * `{code Expires}` standard HTTP header
     */
    String EXPIRES = "Expires";

    /**
     * `{code From}` standard HTTP header
     */
    String FROM = "From";

    /**
     * `{code Host}` standard HTTP header
     */
    String HOST = "Host";

    /**
     * `{code If-Match}` standard HTTP header
     */
    String IF_MATCH = "If-Match";

    /**
     * `{code If-Modified-Since}` standard HTTP header
     */
    String IF_MODIFIED_SINCE = "If-Modified-Since";

    /**
     * `{code If-None-Match}` standard HTTP header
     */
    String IF_NONE_MATCH = "If-None-Match";

    /**
     * `{code If-Range}` standard HTTP header
     */
    String IF_RANGE = "If-Range";

    /**
     * `{code If-Unmodified-Since}` standard HTTP header
     */
    String IF_UNMODIFIED_SINCE = "If-Unmodified-Since";

    /**
     * `{code Last-Modified}` standard HTTP header
     */
    String LAST_MODIFIED = "Last-Modified";

    /**
     * `{code Location}` standard HTTP header
     */
    String LOCATION = "Location";

    /**
     * `{code Max-Forwards}` standard HTTP header
     */
    String MAX_FORWARDS = "Max-Forwards";

    /**
     * `{code Origin}` standard HTTP header
     */
    String ORIGIN = "Origin";

    /**
     * `{code Pragma}` standard HTTP header
     */
    String PRAGMA = "Pragma";

    /**
     * `{code Proxy-Authenticate}` standard HTTP header
     */
    String PROXY_AUTHENTICATE = "Proxy-Authenticate";

    /**
     * `{code Proxy-Authorization}` standard HTTP header
     */
    String PROXY_AUTHORIZATION = "Proxy-Authorization";

    /**
     * `{code Range}` standard HTTP header
     */
    String RANGE = "Range";

    /**
     * `{code Referer}` standard HTTP header
     */
    String REFERER = "Referer";

    /**
     * `{code Retry-After}` standard HTTP header
     */
    String RETRY_AFTER = "Retry-After";

    /**
     * `{code Sec-WebSocket-Key1}` standard HTTP header
     */
    String SEC_WEBSOCKET_KEY1 = "Sec-WebSocket-Key1";

    /**
     * `{code Sec-WebSocket-Key2}` standard HTTP header
     */
    String SEC_WEBSOCKET_KEY2 = "Sec-WebSocket-Key2";

    /**
     * `{code Sec-WebSocket-Location}` standard HTTP header
     */
    String SEC_WEBSOCKET_LOCATION = "Sec-WebSocket-Location";

    /**
     * `{code Sec-WebSocket-Origin}` standard HTTP header
     */
    String SEC_WEBSOCKET_ORIGIN = "Sec-WebSocket-Origin";

    /**
     * `{code Sec-WebSocket-Protocol}` standard HTTP header
     */
    String SEC_WEBSOCKET_PROTOCOL = "Sec-WebSocket-Protocol";

    /**
     * `{code Sec-WebSocket-Version}` standard HTTP header
     */
    String SEC_WEBSOCKET_VERSION = "Sec-WebSocket-Version";

    /**
     * `{code Sec-WebSocket-Key}` standard HTTP header
     */
    String SEC_WEBSOCKET_KEY = "Sec-WebSocket-Key";

    /**
     * `{code Sec-WebSocket-Accept}` standard HTTP header
     */
    String SEC_WEBSOCKET_ACCEPT = "Sec-WebSocket-Accept";

    /**
     * `{code Server}` standard HTTP header
     */
    String SERVER = "Server";

    /**
     * `{code Set-Cookie}` standard HTTP header
     */
    String SET_COOKIE = "Set-Cookie";

    /**
     * `{code Set-Cookie2}` standard HTTP header
     */
    String SET_COOKIE2 = "Set-Cookie2";

    /**
     * `{code TE}` standard HTTP header
     */
    String TE = "TE";

    /**
     * `{code Trailer}` standard HTTP header
     */
    String TRAILER = "Trailer";

    /**
     * `{code Transfer-Encoding}` standard HTTP header
     */
    String TRANSFER_ENCODING = "Transfer-Encoding";

    /**
     * `{code Upgrade}` standard HTTP header
     */
    String UPGRADE = "Upgrade";

    /**
     * `{code User-Agent}` standard HTTP header
     */
    String USER_AGENT = "User-Agent";

    /**
     * `{code Vary}` standard HTTP header
     */
    String VARY = "Vary";

    /**
     * `{code Via}` standard HTTP header
     */
    String VIA = "Via";

    /**
     * `{code Warning}` standard HTTP header
     */
    String WARNING = "Warning";

    /**
     * `{code WebSocket-Location}` standard HTTP header
     */
    String WEBSOCKET_LOCATION = "WebSocket-Location";

    /**
     * `{code WebSocket-Origin}` standard HTTP header
     */
    String WEBSOCKET_ORIGIN = "WebSocket-Origin";

    /**
     * `{code WebSocket-Protocol}` standard HTTP header
     */
    String WEBSOCKET_PROTOCOL = "WebSocket-Protocol";

    /**
     * `{code WWW-Authenticate}` standard HTTP header
     */
    String WWW_AUTHENTICATE = "WWW-Authenticate";

    // -------------------- Custom --------------------

    /**
     * Pseudo HTTP header.
     * If HTTP request contains `{@coe Request-Id}`, then all methods return header value,
     * otherwise all methods return request id, which was generated by the RxMicro framework automatically.
     */
    String REQUEST_ID = "Request-Id";

    /**
     * Default HTTP header name (`{@code Api-Version}`), which is used to detect API version
     * using {@code io.rxmicro.rest.Version.Strategy.HEADER} strategy.
     */
    String API_VERSION = "Api-Version";

    /**
     * Empty HTTP headers instance
     */
    HttpHeaders EMPTY_HEADERS = new HttpHeadersImpl(List.of());

    /**
     * Returns the empty HTTP headers instance
     *
     * @return the empty HTTP headers instance
     */
    static HttpHeaders of() {
        return EMPTY_HEADERS;
    }

    /**
     * Returns HTTP headers instance containing one header.
     *
     * @param name the header name
     * @param value the header value
     * @return the {@link HttpHeaders} containing the provided headers.
     * @throws NullPointerException if any header name or value is {@code null}
     */
    static HttpHeaders of(final String name, final Object value) {
        return of(
                entry(name, value)
        );
    }

    /**
     * Returns HTTP headers instance containing two headers.
     *
     * @param name1 the first header name
     * @param value1 the first header value
     * @param name2 the second header name
     * @param value2 the second header value
     * @return the {@link HttpHeaders} containing the provided headers.
     * @throws NullPointerException if any header name or value is {@code null}
     */
    static HttpHeaders of(final String name1, final Object value1, final String name2, final Object value2) {
        return of(
                entry(name1, value1), entry(name2, value2)
        );
    }

    /**
     * Returns HTTP headers instance containing three headers.
     *
     * @param name1 the first header name
     * @param value1 the first header value
     * @param name2 the second header name
     * @param value2 the second header value
     * @param name3 the third header name
     * @param value3 the third header value
     * @return the {@link HttpHeaders} containing the provided headers.
     * @throws NullPointerException if any header name or value is {@code null}
     */
    static HttpHeaders of(final String name1, final Object value1, final String name2, final Object value2,
                          final String name3, final Object value3) {
        return of(
                entry(name1, value1), entry(name2, value2), entry(name3, value3)
        );
    }

    /**
     * Returns HTTP headers instance containing four headers.
     *
     * @param name1 the first header name
     * @param value1 the first header value
     * @param name2 the second header name
     * @param value2 the second header value
     * @param name3 the third header name
     * @param value3 the third header value
     * @param name4 the fourth header name
     * @param value4 the fourth header value
     * @return the {@link HttpHeaders} containing the provided headers.
     * @throws NullPointerException if any header name or value is {@code null}
     */
    static HttpHeaders of(final String name1, final Object value1, final String name2, final Object value2,
                          final String name3, final Object value3, final String name4, final Object value4) {
        return of(
                entry(name1, value1), entry(name2, value2), entry(name3, value3), entry(name4, value4)
        );
    }

    /**
     * Returns HTTP headers instance containing five headers.
     *
     * @param name1 the first header name
     * @param value1 the first header value
     * @param name2 the second header name
     * @param value2 the second header value
     * @param name3 the third header name
     * @param value3 the third header value
     * @param name4 the fourth header name
     * @param value4 the fourth header value
     * @param name5 the fifth header name
     * @param value5 the fifth header value
     * @return the {@link HttpHeaders} containing the provided headers.
     * @throws NullPointerException if any header name or value is {@code null}
     */
    static HttpHeaders of(final String name1, final Object value1, final String name2, final Object value2,
                          final String name3, final Object value3, final String name4, final Object value4,
                          final String name5, final Object value5) {
        return of(
                entry(name1, value1), entry(name2, value2), entry(name3, value3), entry(name4, value4), entry(name5, value5)
        );
    }

    /**
     * Returns HTTP headers instance containing six headers.
     *
     * @param name1 the first header name
     * @param value1 the first header value
     * @param name2 the second header name
     * @param value2 the second header value
     * @param name3 the third header name
     * @param value3 the third header value
     * @param name4 the fourth header name
     * @param value4 the fourth header value
     * @param name5 the fifth header name
     * @param value5 the fifth header value
     * @param name6 the sixth header name
     * @param value6 the sixth header value
     * @return the {@link HttpHeaders} containing the provided headers.
     * @throws NullPointerException if any header name or value is {@code null}
     */
    static HttpHeaders of(final String name1, final Object value1, final String name2, final Object value2,
                          final String name3, final Object value3, final String name4, final Object value4,
                          final String name5, final Object value5, final String name6, final Object value6) {
        return of(
                entry(name1, value1), entry(name2, value2), entry(name3, value3),
                entry(name4, value4), entry(name5, value5), entry(name6, value6)
        );
    }

    /**
     * Returns HTTP headers instance containing seven headers.
     *
     * @param name1 the first header name
     * @param value1 the first header value
     * @param name2 the second header name
     * @param value2 the second header value
     * @param name3 the third header name
     * @param value3 the third header value
     * @param name4 the fourth header name
     * @param value4 the fourth header value
     * @param name5 the fifth header name
     * @param value5 the fifth header value
     * @param name6 the sixth header name
     * @param value6 the sixth header value
     * @param name7 the seventh header name
     * @param value7 the seventh header value
     * @return the {@link HttpHeaders} containing the provided headers.
     * @throws NullPointerException if any header name or value is {@code null}
     */
    static HttpHeaders of(final String name1, final Object value1, final String name2, final Object value2,
                          final String name3, final Object value3, final String name4, final Object value4,
                          final String name5, final Object value5, final String name6, final Object value6,
                          final String name7, final Object value7) {
        return of(
                entry(name1, value1), entry(name2, value2), entry(name3, value3), entry(name4, value4),
                entry(name5, value5), entry(name6, value6), entry(name7, value7)
        );
    }

    /**
     * Returns HTTP headers instance containing eight headers.
     *
     * @param name1 the first header name
     * @param value1 the first header value
     * @param name2 the second header name
     * @param value2 the second header value
     * @param name3 the third header name
     * @param value3 the third header value
     * @param name4 the fourth header name
     * @param value4 the fourth header value
     * @param name5 the fifth header name
     * @param value5 the fifth header value
     * @param name6 the sixth header name
     * @param value6 the sixth header value
     * @param name7 the seventh header name
     * @param value7 the seventh header value
     * @param name8 the eighth header name
     * @param value8 the eighth header value
     * @return the {@link HttpHeaders} containing the provided headers.
     * @throws NullPointerException if any header name or value is {@code null}
     */
    static HttpHeaders of(final String name1, final Object value1, final String name2, final Object value2,
                          final String name3, final Object value3, final String name4, final Object value4,
                          final String name5, final Object value5, final String name6, final Object value6,
                          final String name7, final Object value7, final String name8, final Object value8) {
        return of(
                entry(name1, value1), entry(name2, value2), entry(name3, value3), entry(name4, value4),
                entry(name5, value5), entry(name6, value6), entry(name7, value7), entry(name8, value8)
        );
    }

    /**
     * Returns HTTP headers instance containing nine headers.
     *
     * @param name1 the first header name
     * @param value1 the first header value
     * @param name2 the second header name
     * @param value2 the second header value
     * @param name3 the third header name
     * @param value3 the third header value
     * @param name4 the fourth header name
     * @param value4 the fourth header value
     * @param name5 the fifth header name
     * @param value5 the fifth header value
     * @param name6 the sixth header name
     * @param value6 the sixth header value
     * @param name7 the seventh header name
     * @param value7 the seventh header value
     * @param name8 the eighth header name
     * @param value8 the eighth header value
     * @param name9 the ninth header name
     * @param value9 the ninth header value
     * @return the {@link HttpHeaders} containing the provided headers.
     * @throws NullPointerException if any header name or value is {@code null}
     */
    static HttpHeaders of(final String name1, final Object value1, final String name2, final Object value2,
                          final String name3, final Object value3, final String name4, final Object value4,
                          final String name5, final Object value5, final String name6, final Object value6,
                          final String name7, final Object value7, final String name8, final Object value8,
                          final String name9, final Object value9) {
        return of(
                entry(name1, value1), entry(name2, value2), entry(name3, value3), entry(name4, value4), entry(name5, value5),
                entry(name6, value6), entry(name7, value7), entry(name8, value8), entry(name9, value9)
        );
    }

    /**
     * Returns HTTP headers instance containing ten headers.
     *
     * @param name1 the first header name
     * @param value1 the first header value
     * @param name2 the second header name
     * @param value2 the second header value
     * @param name3 the third header name
     * @param value3 the third header value
     * @param name4 the fourth header name
     * @param value4 the fourth header value
     * @param name5 the fifth header name
     * @param value5 the fifth header value
     * @param name6 the sixth header name
     * @param value6 the sixth header value
     * @param name7 the seventh header name
     * @param value7 the seventh header value
     * @param name8 the eighth header name
     * @param value8 the eighth header value
     * @param name9 the ninth header name
     * @param value9 the ninth header value
     * @param name10 the tenth header name
     * @param value10 the tenth header value
     * @return the {@link HttpHeaders} containing the provided headers.
     * @throws NullPointerException if any header name or value is {@code null}
     */
    static HttpHeaders of(final String name1, final Object value1, final String name2, final Object value2,
                          final String name3, final Object value3, final String name4, final Object value4,
                          final String name5, final Object value5, final String name6, final Object value6,
                          final String name7, final Object value7, final String name8, final Object value8,
                          final String name9, final Object value9, final String name10, final Object value10) {
        return of(
                entry(name1, value1), entry(name2, value2), entry(name3, value3), entry(name4, value4), entry(name5, value5),
                entry(name6, value6), entry(name7, value7), entry(name8, value8), entry(name9, value9), entry(name10, value10)
        );
    }

    /**
     * Returns HTTP headers instance containing header names and values extracted from the given entries.
     *
     * @param entries the {@link Map.Entry}s containing the header names and values from which the HTTP headers is populated
     * @return the {@link HttpHeaders} containing the provided headers.
     */
    @SafeVarargs
    static HttpHeaders of(final Map.Entry<String, Object>... entries) {
        return new HttpHeadersImpl(Arrays.asList(entries));
    }

    /**
     * Returns HTTP headers instance containing header names and values extracted from the given entries.
     *
     * @param entries the collection of {@link Map.Entry}s containing the header names and values from which the HTTP headers is populated
     * @return the {@link HttpHeaders} containing the provided headers.
     */
    static HttpHeaders of(final Collection<Map.Entry<String, Object>> entries) {
        return new HttpHeadersImpl(entries);
    }

    /**
     * Returns the HTTP header value of the given named header.
     * If current header is multi-valued one that the first value is returned.
     *
     * @param name the HTTP header name
     * @return the HTTP header value of the given named header or {@code null} if HTTP header not defined.
     */
    String getValue(String name);

    /**
     * Returns all defined HTTP header values of the given named header.
     *
     * @param name the HTTP header name
     * @return the list of all defined values or empty list if HTTP header not defined.
     */
    List<String> getValues(String name);

    /**
     * Returns {@code true} if HTTP header is defined, {@code false} otherwise
     *
     * @param name the HTTP header name
     * @return {@code true} if HTTP header is defined
     */
    boolean contains(String name);

    /**
     * Returns the entries of HTTP header names and values
     *
     * @return the entries of HTTP header names and values
     */
    List<Map.Entry<String, String>> getEntries();

    /**
     * Returns the count of HTTP headers
     *
     * @return the count of HTTP headers
     */
    int size();

    /**
     * Returns {@code true} if HTTP headers contains any HTTP headers, {@code false} otherwise
     *
     * @return {@code true} if HTTP headers contains any HTTP headers,
     */
    boolean isNotEmpty();
}
