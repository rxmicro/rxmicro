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

package io.rxmicro.model;

import io.rxmicro.common.ImpossibleException;
import io.rxmicro.common.util.Strings;

import java.util.List;
import java.util.Locale;

import static io.rxmicro.common.CommonConstants.EMPTY_STRING;
import static io.rxmicro.common.util.Strings.capitalize;
import static io.rxmicro.common.util.Strings.splitByCamelCase;
import static io.rxmicro.model.ModelConstants.HYPHEN_DELIMITER;
import static io.rxmicro.model.ModelConstants.UNDERSCORED_DELIMITER;
import static java.util.stream.Collectors.joining;

/**
 * Declares predefined mapping strategies for different usage: ORM mapping, HTTP mapping, etc.
 *
 * @author nedis
 * @since 0.1
 */
public enum MappingStrategy {

    /**
     * Example: {@code hello_world = helloWorld}.
     */
    LOWERCASE_WITH_UNDERSCORED(UNDERSCORED_DELIMITER),

    /**
     * Example: {@code hello-world = helloWorld}.
     */
    LOWERCASE_WITH_HYPHEN(HYPHEN_DELIMITER),

    /**
     * Example: {@code hello world = helloWorld}.
     */
    LOWERCASE_WITH_SPACE_CHARACTER(" "),

    /**
     * Example: {@code HELLO_WORLD = helloWorld}.
     */
    UPPERCASE_WITH_UNDERSCORED(UNDERSCORED_DELIMITER),

    /**
     * Example: {@code HELLO-WORLD = helloWorld}.
     */
    UPPERCASE_WITH_HYPHEN(HYPHEN_DELIMITER),

    /**
     * Example: {@code HELLO WORLD = helloWorld}.
     */
    UPPERCASE_WITH_SPACE_CHARACTER(" "),

    /**
     * Example: {@code Hello_World = helloWorld}.
     */
    CAPITALIZE_WITH_UNDERSCORED(UNDERSCORED_DELIMITER),

    /**
     * Example: {@code Hello-World = helloWorld}.
     */
    CAPITALIZE_WITH_HYPHEN(HYPHEN_DELIMITER),

    /**
     * Example: {@code HelloWorld = helloWorld}.
     */
    CAPITALIZE_CAMEL_CASE(EMPTY_STRING);

    private final String delimiter;

    MappingStrategy(final String delimiter) {
        this.delimiter = delimiter;
    }

    /**
     * Returns the model name based on defined mapping strategy and word list.
     *
     * @param words the word list
     * @return the model name based on defined mapping strategy and word list.
     */
    public String getModelName(final List<String> words) {
        return words.stream()
                .map(this::convert)
                .filter(Strings::isNotBlank)
                .collect(joining(delimiter));
    }

    /**
     * Returns the model name based on defined mapping strategy and word list that created using the camel case rule.
     *
     * @param camelCaseName the name that follows the camel case rule.
     * @return the model name based on defined mapping strategy and word list that created using the camel case rule.
     * @see io.rxmicro.common.util.Strings#splitByCamelCase(String)
     */
    public String getModelName(final String camelCaseName) {
        return getModelName(splitByCamelCase(camelCaseName));
    }

    private String convert(final String word) {
        if (name().startsWith("LOWERCASE")) {
            return word.toLowerCase(Locale.ENGLISH);
        } else if (name().startsWith("UPPERCASE")) {
            return word.toUpperCase(Locale.ENGLISH);
        } else if (name().startsWith("CAPITALIZE")) {
            return capitalize(word);
        } else {
            throw new ImpossibleException("Undefined mapping strategy group: '?'!", name());
        }
    }
}
