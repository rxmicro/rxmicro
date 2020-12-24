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

import io.rxmicro.http.HttpHeaders;

import java.util.List;
import java.util.Map;

/**
 * @author nedis
 * @since 0.8
 */
public final class EmptyHttpHeaders implements HttpHeaders {

    public static final EmptyHttpHeaders INSTANCE = new EmptyHttpHeaders();

    private EmptyHttpHeaders() {
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
    public boolean contains(final String name) {
        return false;
    }

    @Override
    public List<Map.Entry<String, String>> getEntries() {
        return List.of();
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isNotEmpty() {
        return false;
    }
}
