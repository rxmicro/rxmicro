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

package io.rxmicro.http.internal;

import io.rxmicro.http.QueryParams;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static io.rxmicro.common.CommonConstants.EMPTY_STRING;

/**
 * @author nedis
 * @since 0.1
 */
public final class EmptyQueryParams implements QueryParams {

    public static final EmptyQueryParams INSTANCE = new EmptyQueryParams();

    private EmptyQueryParams() {
    }

    @Override
    public String getValue(final String name) {
        return null;
    }

    @Override
    public List<String> getValues(final String name) {
        return List.of();
    }

    @Override
    public Collection<Map.Entry<String, String>> getEntries() {
        return List.of();
    }

    @Override
    public String toString() {
        return EMPTY_STRING;
    }
}
