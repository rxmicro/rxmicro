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

package io.rxmicro.http.local;

import java.util.Collection;
import java.util.Map;

import static java.net.URLEncoder.encode;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author nedis
 * @since 0.1
 */
public final class QueryParamUtils {

    private static final int DEFAULT_PATH_BUILDER_CAPACITY = 50;

    public static String joinPath(final String path,
                                  final Collection<Map.Entry<String, String>> parameters) {
        if (parameters == null || parameters.isEmpty()) {
            return path;
        } else {
            char delimiter = path.contains("?") ? '&' : '?';
            final StringBuilder stringBuilder = new StringBuilder(DEFAULT_PATH_BUILDER_CAPACITY)
                    .append(path);
            for (final Map.Entry<String, String> entry : parameters) {
                stringBuilder.append(delimiter)
                        .append(entry.getKey()).append('=').append(encode(entry.getValue(), UTF_8));
                if (delimiter == '?') {
                    delimiter = '&';
                }
            }
            return stringBuilder.toString();
        }
    }

    private QueryParamUtils() {
    }
}
