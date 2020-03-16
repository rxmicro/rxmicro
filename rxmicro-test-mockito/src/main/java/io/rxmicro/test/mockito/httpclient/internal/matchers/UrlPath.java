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

package io.rxmicro.test.mockito.httpclient.internal.matchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;
import static java.util.Map.entry;
import static java.util.stream.Collectors.joining;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
final class UrlPath {

    private final String urlPath;

    private final List<Map.Entry<String, String>> queryParams;

    static UrlPath build(final String path) {
        final int index = path.indexOf('?');
        if (index == -1) {
            return new UrlPath(path, List.of());
        } else {
            final String urlPath = path.substring(0, index);
            final List<Map.Entry<String, String>> queryParams = new ArrayList<>();
            final StringTokenizer tokenizer = new StringTokenizer(path.substring(index + 1), "&=");
            while (tokenizer.hasMoreTokens()) {
                final String name = tokenizer.nextToken();
                final String value = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
                if (value != null) {
                    queryParams.add(entry(name, value));
                }
            }
            return new UrlPath(urlPath, queryParams);
        }
    }

    private UrlPath(final String urlPath,
                    final List<Map.Entry<String, String>> queryParams) {
        this.urlPath = require(urlPath);
        this.queryParams = require(queryParams);
    }

    public boolean isQueryParamsPresent() {
        return !queryParams.isEmpty();
    }

    public String getUrlPath() {
        return urlPath;
    }

    public List<Map.Entry<String, String>> getQueryParams() {
        return queryParams;
    }

    @Override
    public String toString() {
        if (isQueryParamsPresent()) {
            return urlPath + "?" + queryParams.stream()
                    .map(e -> format("?=?", e.getKey(), e.getValue()))
                    .collect(joining("&"));
        } else {
            return urlPath;
        }
    }
}
