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

package io.rxmicro.http;

import io.rxmicro.http.internal.EmptyQueryParams;
import io.rxmicro.http.internal.QueryParamsImpl;
import io.rxmicro.http.local.QueryParamUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public interface QueryParams {

    QueryParams EMPTY_QUERY_PARAMS = new EmptyQueryParams();

    static QueryParams of() {
        return EMPTY_QUERY_PARAMS;
    }

    static QueryParams of(final String name, final Object value) {
        return of(
                entry(name, value)
        );
    }

    static QueryParams of(final String name1, final Object value1, final String name2, final Object value2) {
        return of(
                entry(name1, value1), entry(name2, value2)
        );
    }

    static QueryParams of(final String name1, final Object value1, final String name2, final Object value2,
                          final String name3, final Object value3) {
        return of(
                entry(name1, value1), entry(name2, value2), entry(name3, value3)
        );
    }

    static QueryParams of(final String name1, final Object value1, final String name2, final Object value2,
                          final String name3, final Object value3, final String name4, final Object value4) {
        return of(
                entry(name1, value1), entry(name2, value2), entry(name3, value3), entry(name4, value4)
        );
    }

    static QueryParams of(final String name1, final Object value1, final String name2, final Object value2,
                          final String name3, final Object value3, final String name4, final Object value4,
                          final String name5, final Object value5) {
        return of(
                entry(name1, value1), entry(name2, value2), entry(name3, value3), entry(name4, value4), entry(name5, value5)
        );
    }

    static QueryParams of(final String name1, final Object value1, final String name2, final Object value2,
                          final String name3, final Object value3, final String name4, final Object value4,
                          final String name5, final Object value5, final String name6, final Object value6) {
        return of(
                entry(name1, value1), entry(name2, value2), entry(name3, value3),
                entry(name4, value4), entry(name5, value5), entry(name6, value6)
        );
    }

    static QueryParams of(final String name1, final Object value1, final String name2, final Object value2,
                          final String name3, final Object value3, final String name4, final Object value4,
                          final String name5, final Object value5, final String name6, final Object value6,
                          final String name7, final Object value7) {
        return of(
                entry(name1, value1), entry(name2, value2), entry(name3, value3), entry(name4, value4),
                entry(name5, value5), entry(name6, value6), entry(name7, value7)
        );
    }

    static QueryParams of(final String name1, final Object value1, final String name2, final Object value2,
                          final String name3, final Object value3, final String name4, final Object value4,
                          final String name5, final Object value5, final String name6, final Object value6,
                          final String name7, final Object value7, final String name8, final Object value8) {
        return of(
                entry(name1, value1), entry(name2, value2), entry(name3, value3), entry(name4, value4),
                entry(name5, value5), entry(name6, value6), entry(name7, value7), entry(name8, value8)
        );
    }

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

    @SafeVarargs
    static QueryParams of(final Map.Entry<String, Object>... entries) {
        return new QueryParamsImpl(entries);

    }

    static String joinPath(final String path,
                           final QueryParams parameters) {
        return QueryParamUtils.joinPath(path, parameters.getEntries());
    }

    String getValue(String name);

    List<String> getValues(String name);

    Collection<Map.Entry<String, String>> getEntries();

    @Override
    String toString();
}
