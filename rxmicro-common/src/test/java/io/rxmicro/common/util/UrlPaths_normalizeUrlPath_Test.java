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

package io.rxmicro.common.util;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.rxmicro.common.util.UrlPaths.normalizeUrlPath;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author nedis
 *
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class UrlPaths_normalizeUrlPath_Test {

    @Test
    @Order(1)
    void Should_throw_NPE_if_path_is_null() {
        assertThrows(NullPointerException.class, () -> normalizeUrlPath(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            " ",
            "\t",
            "   "
    })
    @Order(2)
    void Should_return_splash_if_path_is_blank_or_empty(final String path) {
        assertEquals("/", normalizeUrlPath(path));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "category",
            " category",
            "\tcategory",
            "  category "
    })
    @Order(3)
    void Should_add_splash_before_path_if_path_does_not_start_with_splash(final String path) {
        assertEquals("/category", normalizeUrlPath(path));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "/category/",
            " /category/ ",
            "\t/category/\t",
            "  \t/category/ "
    })
    @Order(4)
    void Should_remove_splash_after_path_if_path_ends_with_splash(final String path) {
        assertEquals("/category", normalizeUrlPath(path));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "//category/type",
            "///category//type",
            "/////category////type",
            "///////////category/////////////////////////type"
    })
    @Order(5)
    void Should_remove_redundant_splashes(final String path) {
        assertEquals("/category/type", normalizeUrlPath(path));
    }
}
