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

package io.rxmicro.http.internal;

import io.rxmicro.http.QueryParams;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
@SuppressWarnings("unchecked")
public final class EmptyQueryParams implements QueryParams {

    @SuppressWarnings("rawtypes")
    private static final List EMPTY_LIST = List.of();

    @Override
    public String getValue(final String name) {
        return null;
    }

    @Override
    public List<String> getValues(final String name) {
        return EMPTY_LIST;
    }

    @Override
    public Collection<Map.Entry<String, String>> getEntries() {
        return EMPTY_LIST;
    }

    @Override
    public String toString() {
        return "";
    }
}
