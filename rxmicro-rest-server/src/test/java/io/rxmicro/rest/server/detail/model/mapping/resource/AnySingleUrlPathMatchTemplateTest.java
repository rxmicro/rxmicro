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

package io.rxmicro.rest.server.detail.model.mapping.resource;

import io.rxmicro.rest.server.detail.model.HttpRequest;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

/**
 * @author nedis
 * @since 0.8
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
final class AnySingleUrlPathMatchTemplateTest {

    @Mock
    private HttpRequest request;

    @ParameterizedTest
    @Order(1)
    @CsvSource({
            "/static-image.jpg",
            "/static-style.css",
            "/static-code.js",
            "/static-static-static-static-static-static-static"
    })
    void Should_return_true(final String url) {
        lenient().when(request.getUri()).thenReturn(url);
        final UrlPathMatchTemplate template = AnySingleUrlPathMatchTemplate.INSTANCE;
        assertTrue(template.match(request));
    }

    @ParameterizedTest
    @Order(2)
    @CsvSource({
            "/static/image.jpg",
            "/static/image/image.jpg",
            "/static/image/nested/image.jpg",
            "/static/a/b/c/d/e/f/g/h/i/j/k/l/m/n/o/p/q/r/s/t/u/v/w/x/y/z/image.jpg",
            "/static/folder1",
            "/static/image.jpg",
            "/static/image/image.jpg",
            "/static/image/nested/image.jpg",
            "/static/a/b/c/d/e/f/g/h/i/j/k/l/m/n/o/p/q/r/s/t/u/v/w/x/y/z/image.jpg",
            "/static/folder1"
    })
    void Should_return_false(final String url) {
        when(request.getUri()).thenReturn(url);
        final UrlPathMatchTemplate template = AnySingleUrlPathMatchTemplate.INSTANCE;
        assertFalse(template.match(request));
    }
}
