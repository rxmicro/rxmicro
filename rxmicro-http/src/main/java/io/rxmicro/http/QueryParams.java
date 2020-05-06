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

import io.rxmicro.http.internal.EmptyQueryParams;
import io.rxmicro.http.internal.QueryParamsImpl;
import io.rxmicro.http.local.QueryParamUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

/**
 * Represents a HTTP query parameters instance
 *
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public interface QueryParams {

    /**
     * Empty HTTP query parameters instance
     */
    QueryParams EMPTY_QUERY_PARAMS = new EmptyQueryParams();

    /**
     * Returns the empty HTTP query parameters instance
     *
     * @return the empty HTTP query parameters instance
     */
    static QueryParams of() {
        return EMPTY_QUERY_PARAMS;
    }

    /**
     * Returns HTTP query parameters instance containing one query parameter.
     *
     * @param name query parameter name
     * @param value query parameter value
     * @return the {@link QueryParams} containing the provided query parameters.
     * @throws NullPointerException if any query parameter name or value is {@code null}
     */
    static QueryParams of(final String name, final Object value) {
        return of(
                entry(name, value)
        );
    }

    /**
     * Returns HTTP query parameters instance containing two query parameters.
     *
     * @param name1 the first query parameter name
     * @param value1 the first query parameter value
     * @param name2 the second query parameter name
     * @param value2 the second query parameter value
     * @return the {@link QueryParams} containing the provided query parameters.
     * @throws NullPointerException if any query parameter name or value is {@code null}
     */
    static QueryParams of(final String name1, final Object value1, final String name2, final Object value2) {
        return of(
                entry(name1, value1), entry(name2, value2)
        );
    }

    /**
     * Returns HTTP query parameters instance containing three query parameters.
     *
     * @param name1 the first query parameter name
     * @param value1 the first query parameter value
     * @param name2 the second query parameter name
     * @param value2 the second query parameter value
     * @param name3 the third query parameter name
     * @param value3 the third query parameter value
     * @return the {@link QueryParams} containing the provided query parameters.
     * @throws NullPointerException if any query parameter name or value is {@code null}
     */
    static QueryParams of(final String name1, final Object value1, final String name2, final Object value2,
                          final String name3, final Object value3) {
        return of(
                entry(name1, value1), entry(name2, value2), entry(name3, value3)
        );
    }

    /**
     * Returns HTTP query parameters instance containing four query parameters.
     *
     * @param name1 the first query parameter name
     * @param value1 the first query parameter value
     * @param name2 the second query parameter name
     * @param value2 the second query parameter value
     * @param name3 the third query parameter name
     * @param value3 the third query parameter value
     * @param name4 the fourth query parameter name
     * @param value4 the fourth query parameter value
     * @return the {@link QueryParams} containing the provided query parameters.
     * @throws NullPointerException if any query parameter name or value is {@code null}
     */
    static QueryParams of(final String name1, final Object value1, final String name2, final Object value2,
                          final String name3, final Object value3, final String name4, final Object value4) {
        return of(
                entry(name1, value1), entry(name2, value2), entry(name3, value3), entry(name4, value4)
        );
    }

    /**
     * Returns HTTP query parameters instance containing five query parameters.
     *
     * @param name1 the first query parameter name
     * @param value1 the first query parameter value
     * @param name2 the second query parameter name
     * @param value2 the second query parameter value
     * @param name3 the third query parameter name
     * @param value3 the third query parameter value
     * @param name4 the fourth query parameter name
     * @param value4 the fourth query parameter value
     * @param name5 the fifth query parameter name
     * @param value5 the fifth query parameter value
     * @return the {@link QueryParams} containing the provided query parameters.
     * @throws NullPointerException if any query parameter name or value is {@code null}
     */
    static QueryParams of(final String name1, final Object value1, final String name2, final Object value2,
                          final String name3, final Object value3, final String name4, final Object value4,
                          final String name5, final Object value5) {
        return of(
                entry(name1, value1), entry(name2, value2), entry(name3, value3), entry(name4, value4), entry(name5, value5)
        );
    }

    /**
     * Returns HTTP query parameters instance containing six query parameters.
     *
     * @param name1 the first query parameter name
     * @param value1 the first query parameter value
     * @param name2 the second query parameter name
     * @param value2 the second query parameter value
     * @param name3 the third query parameter name
     * @param value3 the third query parameter value
     * @param name4 the fourth query parameter name
     * @param value4 the fourth query parameter value
     * @param name5 the fifth query parameter name
     * @param value5 the fifth query parameter value
     * @param name6 the sixth query parameter name
     * @param value6 the sixth query parameter value
     * @return the {@link QueryParams} containing the provided query parameters.
     * @throws NullPointerException if any query parameter name or value is {@code null}
     */
    static QueryParams of(final String name1, final Object value1, final String name2, final Object value2,
                          final String name3, final Object value3, final String name4, final Object value4,
                          final String name5, final Object value5, final String name6, final Object value6) {
        return of(
                entry(name1, value1), entry(name2, value2), entry(name3, value3),
                entry(name4, value4), entry(name5, value5), entry(name6, value6)
        );
    }

    /**
     * Returns HTTP query parameters instance containing seven query parameters.
     *
     * @param name1 the first query parameter name
     * @param value1 the first query parameter value
     * @param name2 the second query parameter name
     * @param value2 the second query parameter value
     * @param name3 the third query parameter name
     * @param value3 the third query parameter value
     * @param name4 the fourth query parameter name
     * @param value4 the fourth query parameter value
     * @param name5 the fifth query parameter name
     * @param value5 the fifth query parameter value
     * @param name6 the sixth query parameter name
     * @param value6 the sixth query parameter value
     * @param name7 the seventh query parameter name
     * @param value7 the seventh query parameter value
     * @return the {@link QueryParams} containing the provided query parameters.
     * @throws NullPointerException if any query parameter name or value is {@code null}
     */
    static QueryParams of(final String name1, final Object value1, final String name2, final Object value2,
                          final String name3, final Object value3, final String name4, final Object value4,
                          final String name5, final Object value5, final String name6, final Object value6,
                          final String name7, final Object value7) {
        return of(
                entry(name1, value1), entry(name2, value2), entry(name3, value3), entry(name4, value4),
                entry(name5, value5), entry(name6, value6), entry(name7, value7)
        );
    }

    /**
     * Returns HTTP query parameters instance containing eight query parameters.
     *
     * @param name1 the first query parameter name
     * @param value1 the first query parameter value
     * @param name2 the second query parameter name
     * @param value2 the second query parameter value
     * @param name3 the third query parameter name
     * @param value3 the third query parameter value
     * @param name4 the fourth query parameter name
     * @param value4 the fourth query parameter value
     * @param name5 the fifth query parameter name
     * @param value5 the fifth query parameter value
     * @param name6 the sixth query parameter name
     * @param value6 the sixth query parameter value
     * @param name7 the seventh query parameter name
     * @param value7 the seventh query parameter value
     * @param name8 the eighth query parameter name
     * @param value8 the eighth query parameter value
     * @return the {@link QueryParams} containing the provided query parameters.
     * @throws NullPointerException if any query parameter name or value is {@code null}
     */
    static QueryParams of(final String name1, final Object value1, final String name2, final Object value2,
                          final String name3, final Object value3, final String name4, final Object value4,
                          final String name5, final Object value5, final String name6, final Object value6,
                          final String name7, final Object value7, final String name8, final Object value8) {
        return of(
                entry(name1, value1), entry(name2, value2), entry(name3, value3), entry(name4, value4),
                entry(name5, value5), entry(name6, value6), entry(name7, value7), entry(name8, value8)
        );
    }

    /**
     * Returns HTTP query parameters instance containing nine query parameters.
     *
     * @param name1 the first query parameter name
     * @param value1 the first query parameter value
     * @param name2 the second query parameter name
     * @param value2 the second query parameter value
     * @param name3 the third query parameter name
     * @param value3 the third query parameter value
     * @param name4 the fourth query parameter name
     * @param value4 the fourth query parameter value
     * @param name5 the fifth query parameter name
     * @param value5 the fifth query parameter value
     * @param name6 the sixth query parameter name
     * @param value6 the sixth query parameter value
     * @param name7 the seventh query parameter name
     * @param value7 the seventh query parameter value
     * @param name8 the eighth query parameter name
     * @param value8 the eighth query parameter value
     * @param name9 the ninth query parameter name
     * @param value9 the ninth query parameter value
     * @return the {@link QueryParams} containing the provided query parameters.
     * @throws NullPointerException if any query parameter name or value is {@code null}
     */
    static QueryParams of(final String name1, final Object value1, final String name2, final Object value2,
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
     * Returns HTTP query parameters instance containing ten query parameters.
     *
     * @param name1 the first query parameter name
     * @param value1 the first query parameter value
     * @param name2 the second query parameter name
     * @param value2 the second query parameter value
     * @param name3 the third query parameter name
     * @param value3 the third query parameter value
     * @param name4 the fourth query parameter name
     * @param value4 the fourth query parameter value
     * @param name5 the fifth query parameter name
     * @param value5 the fifth query parameter value
     * @param name6 the sixth query parameter name
     * @param value6 the sixth query parameter value
     * @param name7 the seventh query parameter name
     * @param value7 the seventh query parameter value
     * @param name8 the eighth query parameter name
     * @param value8 the eighth query parameter value
     * @param name9 the ninth query parameter name
     * @param value9 the ninth query parameter value
     * @param name10 the tenth query parameter name
     * @param value10 the tenth query parameter value
     * @return the {@link QueryParams} containing the provided query parameters.
     * @throws NullPointerException if any query parameter name or value is {@code null}
     */
    static QueryParams of(final String name1, final Object value1, final String name2, final Object value2,
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
     * Returns HTTP query parameters instance containing query parameter names and values extracted from the given entries.
     *
     * @param entries the {@link Map.Entry}s containing the query parameter names and values from which the query parameters is populated
     * @return the {@link QueryParams} containing the provided query parameters.
     */
    @SafeVarargs
    static QueryParams of(final Map.Entry<String, Object>... entries) {
        return new QueryParamsImpl(entries);

    }

    /**
     * Joins the URL path and HTTP query parameters
     *
     * @param path the URL path
     * @param parameters the query parameters
     * @return the URL path with joined query parameters
     */
    static String joinPath(final String path,
                           final QueryParams parameters) {
        return QueryParamUtils.joinPath(path, parameters.getEntries());
    }

    /**
     * Returns the HTTP query parameter value of the given named query parameter.
     * If current query parameter is multi-valued one that the first value is returned.
     *
     * @param name the HTTP query parameter name
     * @return the HTTP query parameter value of the given named query parameter or {@code null} if HTTP query parameter not defined.
     */
    String getValue(String name);

    /**
     * Returns all defined HTTP query parameter values of the given named query parameter.
     *
     * @param name the HTTP query parameter name
     * @return the list of all defined values or empty list if HTTP query parameter not defined.
     */
    List<String> getValues(String name);

    /**
     * Returns the entries of HTTP query parameter names and values
     *
     * @return the entries of HTTP query parameter names and values
     */
    Collection<Map.Entry<String, String>> getEntries();

    @Override
    String toString();
}
