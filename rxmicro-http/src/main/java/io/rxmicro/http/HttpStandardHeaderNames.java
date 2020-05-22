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

package io.rxmicro.http;

/**
 * Standard HTTP header names.
 *
 * @author nedis
 * @see HttpHeaders
 * @since 0.4
 */
public final class HttpStandardHeaderNames {

    /**
     * `{@code Accept}` standard HTTP header.
     */
    public static final String ACCEPT = "Accept";

    /**
     * `{@code Accept-Charset}` standard HTTP header.
     */
    public static final String ACCEPT_CHARSET = "Accept-Charset";

    /**
     * `{@code Accept-Encoding"}` standard HTTP header.
     */
    public static final String ACCEPT_ENCODING = "Accept-Encoding";

    /**
     * `{@code Accept-Language}` standard HTTP header.
     */
    public static final String ACCEPT_LANGUAGE = "Accept-Language";

    /**
     * `{@code Accept-Ranges}` standard HTTP header.
     */
    public static final String ACCEPT_RANGES = "Accept-Ranges";

    /**
     * `{@code Accept-Patch}` standard HTTP header.
     */
    public static final String ACCEPT_PATCH = "Accept-Patch";

    /**
     * `{@code Access-Control-Allow-Credentials}` standard HTTP header.
     */
    public static final String ACCESS_CONTROL_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";

    /**
     * `{@code Access-Control-Allow-Headers}` standard HTTP header.
     */
    public static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";

    /**
     * `{@code Access-Control-Allow-Methods}` standard HTTP header.
     */
    public static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";

    /**
     * `{@code Access-Control-Allow-Origin}` standard HTTP header.
     */
    public static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";

    /**
     * `{@code Access-Control-Expose-Headers}` standard HTTP header.
     */
    public static final String ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers";

    /**
     * `{@code Access-Control-Max-Age}` standard HTTP header.
     */
    public static final String ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age";

    /**
     * `{@code Access-Control-Request-Headers}` standard HTTP header.
     */
    public static final String ACCESS_CONTROL_REQUEST_HEADERS = "Access-Control-Request-Headers";

    /**
     * `{@code Access-Control-Request-Method}` standard HTTP header.
     */
    public static final String ACCESS_CONTROL_REQUEST_METHOD = "Access-Control-Request-Method";

    /**
     * `{@code Age}` standard HTTP header.
     */
    public static final String AGE = "Age";

    /**
     * `{@code Allow}` standard HTTP header.
     */
    public static final String ALLOW = "Allow";

    /**
     * `{@code Authorization}` standard HTTP header.
     */
    public static final String AUTHORIZATION = "Authorization";

    /**
     * `{@code Cache-Control}` standard HTTP header.
     */
    public static final String CACHE_CONTROL = "Cache-Control";

    /**
     * `{@code Connection}` standard HTTP header.
     */
    public static final String CONNECTION = "Connection";

    /**
     * `{@code Content-Base}` standard HTTP header.
     */
    public static final String CONTENT_BASE = "Content-Base";

    /**
     * `{@code Content-Encoding}` standard HTTP header.
     */
    public static final String CONTENT_ENCODING = "Content-Encoding";

    /**
     * `{@code Content-Language}` standard HTTP header.
     */
    public static final String CONTENT_LANGUAGE = "Content-Language";

    /**
     * `{@code Content-Length}` standard HTTP header.
     */
    public static final String CONTENT_LENGTH = "Content-Length";

    /**
     * `{@code Content-Location}` standard HTTP header.
     */
    public static final String CONTENT_LOCATION = "Content-Location";

    /**
     * `{@code Content-Transfer-Encoding}` standard HTTP header.
     */
    public static final String CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding";

    /**
     * `{@code Content-MD5}` standard HTTP header.
     */
    public static final String CONTENT_MD5 = "Content-MD5";

    /**
     * `{@code Content-Range}` standard HTTP header.
     */
    public static final String CONTENT_RANGE = "Content-Range";

    /**
     * `{@code Content-Type}` standard HTTP header.
     */
    public static final String CONTENT_TYPE = "Content-Type";

    /**
     * `{@code Cookie}` standard HTTP header.
     */
    public static final String COOKIE = "Cookie";

    /**
     * `{@code Date}` standard HTTP header.
     */
    public static final String DATE = "Date";

    /**
     * `{@code ETag}` standard HTTP header.
     */
    public static final String ETAG = "ETag";

    /**
     * `{@code Expect}` standard HTTP header.
     */
    public static final String EXPECT = "Expect";

    /**
     * `{@code Expires}` standard HTTP header.
     */
    public static final String EXPIRES = "Expires";

    /**
     * `{@code From}` standard HTTP header.
     */
    public static final String FROM = "From";

    /**
     * `{@code Host}` standard HTTP header.
     */
    public static final String HOST = "Host";

    /**
     * `{@code If-Match}` standard HTTP header.
     */
    public static final String IF_MATCH = "If-Match";

    /**
     * `{@code If-Modified-Since}` standard HTTP header.
     */
    public static final String IF_MODIFIED_SINCE = "If-Modified-Since";

    /**
     * `{@code If-None-Match}` standard HTTP header.
     */
    public static final String IF_NONE_MATCH = "If-None-Match";

    /**
     * `{@code If-Range}` standard HTTP header.
     */
    public static final String IF_RANGE = "If-Range";

    /**
     * `{@code If-Unmodified-Since}` standard HTTP header.
     */
    public static final String IF_UNMODIFIED_SINCE = "If-Unmodified-Since";

    /**
     * `{@code Last-Modified}` standard HTTP header.
     */
    public static final String LAST_MODIFIED = "Last-Modified";

    /**
     * `{@code Location}` standard HTTP header.
     */
    public static final String LOCATION = "Location";

    /**
     * `{@code Max-Forwards}` standard HTTP header.
     */
    public static final String MAX_FORWARDS = "Max-Forwards";

    /**
     * `{@code Origin}` standard HTTP header.
     */
    public static final String ORIGIN = "Origin";

    /**
     * `{@code Pragma}` standard HTTP header.
     */
    public static final String PRAGMA = "Pragma";

    /**
     * `{@code Proxy-Authenticate}` standard HTTP header.
     */
    public static final String PROXY_AUTHENTICATE = "Proxy-Authenticate";

    /**
     * `{@code Proxy-Authorization}` standard HTTP header.
     */
    public static final String PROXY_AUTHORIZATION = "Proxy-Authorization";

    /**
     * `{@code Range}` standard HTTP header.
     */
    public static final String RANGE = "Range";

    /**
     * `{@code Referer}` standard HTTP header.
     */
    public static final String REFERER = "Referer";

    /**
     * `{@code Retry-After}` standard HTTP header.
     */
    public static final String RETRY_AFTER = "Retry-After";

    /**
     * `{@code Sec-WebSocket-Key1}` standard HTTP header.
     */
    public static final String SEC_WEBSOCKET_KEY1 = "Sec-WebSocket-Key1";

    /**
     * `{@code Sec-WebSocket-Key2}` standard HTTP header.
     */
    public static final String SEC_WEBSOCKET_KEY2 = "Sec-WebSocket-Key2";

    /**
     * `{@code Sec-WebSocket-Location}` standard HTTP header.
     */
    public static final String SEC_WEBSOCKET_LOCATION = "Sec-WebSocket-Location";

    /**
     * `{@code Sec-WebSocket-Origin}` standard HTTP header.
     */
    public static final String SEC_WEBSOCKET_ORIGIN = "Sec-WebSocket-Origin";

    /**
     * `{@code Sec-WebSocket-Protocol}` standard HTTP header.
     */
    public static final String SEC_WEBSOCKET_PROTOCOL = "Sec-WebSocket-Protocol";

    /**
     * `{@code Sec-WebSocket-Version}` standard HTTP header.
     */
    public static final String SEC_WEBSOCKET_VERSION = "Sec-WebSocket-Version";

    /**
     * `{@code Sec-WebSocket-Key}` standard HTTP header.
     */
    public static final String SEC_WEBSOCKET_KEY = "Sec-WebSocket-Key";

    /**
     * `{@code Sec-WebSocket-Accept}` standard HTTP header.
     */
    public static final String SEC_WEBSOCKET_ACCEPT = "Sec-WebSocket-Accept";

    /**
     * `{@code Server}` standard HTTP header.
     */
    public static final String SERVER = "Server";

    /**
     * `{@code Set-Cookie}` standard HTTP header.
     */
    public static final String SET_COOKIE = "Set-Cookie";

    /**
     * `{@code Set-Cookie2}` standard HTTP header.
     */
    public static final String SET_COOKIE2 = "Set-Cookie2";

    /**
     * `{@code TE}` standard HTTP header.
     */
    public static final String TE = "TE";

    /**
     * `{@code Trailer}` standard HTTP header.
     */
    public static final String TRAILER = "Trailer";

    /**
     * `{@code Transfer-Encoding}` standard HTTP header.
     */
    public static final String TRANSFER_ENCODING = "Transfer-Encoding";

    /**
     * `{@code Upgrade}` standard HTTP header.
     */
    public static final String UPGRADE = "Upgrade";

    /**
     * `{@code User-Agent}` standard HTTP header.
     */
    public static final String USER_AGENT = "User-Agent";

    /**
     * `{@code Vary}` standard HTTP header.
     */
    public static final String VARY = "Vary";

    /**
     * `{@code Via}` standard HTTP header.
     */
    public static final String VIA = "Via";

    /**
     * `{@code Warning}` standard HTTP header.
     */
    public static final String WARNING = "Warning";

    /**
     * `{@code WebSocket-Location}` standard HTTP header.
     */
    public static final String WEBSOCKET_LOCATION = "WebSocket-Location";

    /**
     * `{@code WebSocket-Origin}` standard HTTP header.
     */
    public static final String WEBSOCKET_ORIGIN = "WebSocket-Origin";

    /**
     * `{@code WebSocket-Protocol}` standard HTTP header.
     */
    public static final String WEBSOCKET_PROTOCOL = "WebSocket-Protocol";

    /**
     * `{@code WWW-Authenticate}` standard HTTP header.
     */
    public static final String WWW_AUTHENTICATE = "WWW-Authenticate";

    // -------------------- Custom --------------------

    /**
     * Pseudo HTTP header.
     * If HTTP request contains `{@code Request-Id}`, then all methods return header value,
     * otherwise all methods return request id, which was generated by the RxMicro framework automatically.
     */
    public static final String REQUEST_ID = "Request-Id";

    /**
     * Default HTTP header name (`{@code Api-Version}`), which is used to detect API version
     * using {@code io.rxmicro.rest.Version.Strategy.HEADER} strategy.
     */
    public static final String API_VERSION = "Api-Version";

    private HttpStandardHeaderNames() {
    }
}
