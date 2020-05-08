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

package io.rxmicro.test.mockito.mongo.internal.util;

import org.bson.Document;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @since 0.1
 */
public final class Validators {

    public static Document validateBson(final Document document,
                                        final String paramName) {
        return require(document, "'?' must be not null!", paramName);
    }

    public static Document validateBson(final String value,
                                        final String paramName) {
        return Document.parse(validateString(value, paramName));
    }

    public static String validateString(final String value,
                                        final String paramName) {
        require(value, "'?' must be not null!", paramName);
        if (value.isBlank()) {
            throw new IllegalArgumentException(format("'?' must be not blank!", paramName));
        }
        return value;
    }

    private Validators() {
    }
}
