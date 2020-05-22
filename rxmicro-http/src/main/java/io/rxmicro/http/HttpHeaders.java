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

import io.rxmicro.http.internal.HttpHeadersImpl;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

/**
 * Represents a HTTP headers instance.
 *
 * @author nedis
 * @see QueryParams
 * @see HttpStandardHeaderNames
 * @see HttpValues
 * @since 0.1
 */
public interface HttpHeaders {

    /**
     * Empty HTTP headers instance.
     */
    HttpHeaders EMPTY_HEADERS = new HttpHeadersImpl(List.of());

    /**
     * Returns the empty HTTP headers instance.
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
     * Returns {@code true} if HTTP header is defined, {@code false} otherwise.
     *
     * @param name the HTTP header name
     * @return {@code true} if HTTP header is defined
     */
    boolean contains(String name);

    /**
     * Returns the entries of HTTP header names and values.
     *
     * @return the entries of HTTP header names and values
     */
    List<Map.Entry<String, String>> getEntries();

    /**
     * Returns the count of HTTP headers.
     *
     * @return the count of HTTP headers
     */
    int size();

    /**
     * Returns {@code true} if HTTP headers contains any HTTP headers, {@code false} otherwise.
     *
     * @return {@code true} if HTTP headers contains any HTTP headers,
     */
    boolean isNotEmpty();
}
