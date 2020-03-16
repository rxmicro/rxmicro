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

package io.rxmicro.data.sql.detail;

import java.util.Collection;

import static io.rxmicro.common.util.Formats.format;
import static java.util.stream.Collectors.joining;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class SQLParams {

    public static String joinParams(final Collection<?> collection) {
        return collection.stream().map(Object::toString).collect(joining(", "));
    }

    public static String joinStringParams(final Collection<String> collection) {
        return collection.stream().map(v -> format("'?'", v)).collect(joining(", "));
    }

    public static <T extends Enum<T>> String joinEnumParams(final Collection<T> collection) {
        return collection.stream().map(v -> format("'?'", v.name())).collect(joining(", "));
    }

    private SQLParams() {
    }
}
