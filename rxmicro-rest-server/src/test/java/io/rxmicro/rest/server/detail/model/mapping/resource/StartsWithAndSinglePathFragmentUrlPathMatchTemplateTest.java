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

import io.rxmicro.common.ImpossibleException;
import io.rxmicro.rest.server.detail.model.HttpRequest;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

/**
 * @author nedis
 * @since 0.8
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
final class StartsWithAndSinglePathFragmentUrlPathMatchTemplateTest {

    @Mock
    private HttpRequest request;

    @ParameterizedTest
    @Order(1)
    @CsvSource(delimiter = ';', value = {
            "/static/*;            /static/image.jpg",
            "/static/*;            /static/folder1",

            "/static*;             /static-image.jpg",
            "/static*;             /static-folder1",
            "/static*;             /static-static-static-static-static-static-static",

            "/parent/child/*;      /parent/child/image.jpg",
            "/parent/child/*;      /parent/child/folder1",

            "/parent/child*;       /parent/child_image.jpg",
            "/parent/child*;       /parent/child_folder1"
    })
    void Should_return_true(final String urlTemplate,
                            final String url){
        when(request.getUri()).thenReturn(url);
        final UrlPathMatchTemplate template = new StartsWithAndSinglePathFragmentUrlPathMatchTemplate(urlTemplate);
        assertTrue(template.match(request));
    }

    @ParameterizedTest
    @Order(2)
    @CsvSource(delimiter = ';', value = {
            "/static/*;            /image.jpg",
            "/static/*;            /static",
            "/static/*;            /static/",
            "/static/*;            /folder1",

            "/static*;             /static/image.jpg",
            "/static*;             /static/folder1",
            "/static*;             /static/",
            "/static*;             /folder1",
            "/static*;             /static/a/b/c/d/e/f/g/h/i/j/k/l/m/n/o/p/q/r/s/t/u/v/w/x/y/z/image.jpg",

            "/parent/child/*;      /image.jpg",
            "/parent/child/*;      /parent/child",
            "/parent/child/*;      /parent/child/",
            "/parent/child/*;      /folder1",

            "/parent/child*;       /parent/child/image.jpg",
            "/parent/child*;       /parent/child/folder1",
            "/parent/child*;       /folder1"
    })
    void Should_return_false(final String urlTemplate,
                            final String url){
        when(request.getUri()).thenReturn(url);
        final UrlPathMatchTemplate template = new StartsWithAndSinglePathFragmentUrlPathMatchTemplate(urlTemplate);
        assertFalse(template.match(request));
    }

    @Test
    @Order(3)
    void Should_throw_ImpossibleException_if_provided_urlTemplate_does_not_contain_asterisk(){
        when(request.getUri()).thenReturn("/static/image.jpg");
        final UrlPathMatchTemplate template = new StartsWithAndSinglePathFragmentUrlPathMatchTemplate("/static");
        final ImpossibleException exception = assertThrows(ImpossibleException.class, () -> template.match(request));
        assertEquals(
                "At least one return must be invoked for 'StartsWithAndSinglePathFragmentUrlPathMatchTemplate' class",
                exception.getMessage()
        );
    }
}
