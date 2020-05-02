/*
 * Copyright 2019 https://rxmicro.io
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

package io.rxmicro.model;

import java.util.List;
import java.util.Locale;

import static io.rxmicro.common.util.Strings.capitalize;
import static java.util.stream.Collectors.joining;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public enum MappingStrategy {

    /**
     * Example: hello_world == helloWorld
     */
    LOWERCASE_WITH_UNDERSCORED("_"),

    /**
     * Example: hello-world == helloWorld
     */
    LOWERCASE_WITH_HYPHEN("-"),

    /**
     * Example: HELLO_WORLD == helloWorld
     */
    UPPERCASE_WITH_UNDERSCORED("_"),

    /**
     * Example: HELLO-WORLD == helloWorld
     */
    UPPERCASE_WITH_HYPHEN("-"),

    /**
     * Example: Hello_World == helloWorld
     */
    CAPITALIZE_WITH_UNDERSCORED("_"),

    /**
     * Example: Hello-World == helloWorld
     */
    CAPITALIZE_WITH_HYPHEN("-"),

    /**
     * Example: HelloWorld == helloWorld
     */
    CAPITALIZE_CAMEL_CASE("");

    private final String delimiter;

    MappingStrategy(final String delimiter) {
        this.delimiter = delimiter;
    }

    public String getModelName(final List<String> words) {
        return words.stream()
                .map(this::convert)
                .filter(v -> !v.trim().isEmpty())
                .collect(joining(delimiter));
    }

    private String convert(final String word) {
        if (name().startsWith("LOWERCASE")) {
            return word.toLowerCase(Locale.ENGLISH);
        } else if (name().startsWith("UPPERCASE")) {
            return word.toUpperCase(Locale.ENGLISH);
        } else if (name().startsWith("CAPITALIZE")) {
            return capitalize(word);
        } else {
            throw new UnsupportedOperationException();
        }
    }
}
