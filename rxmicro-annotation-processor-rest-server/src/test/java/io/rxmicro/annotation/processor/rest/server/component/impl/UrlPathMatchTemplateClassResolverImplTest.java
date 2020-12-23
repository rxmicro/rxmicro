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

package io.rxmicro.annotation.processor.rest.server.component.impl;

import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.rest.server.component.UrlPathMatchTemplateClassResolver;
import io.rxmicro.rest.server.detail.model.mapping.resource.AnyOneOrManyUrlPathMatchTemplate;
import io.rxmicro.rest.server.detail.model.mapping.resource.AnySingleUrlPathMatchTemplate;
import io.rxmicro.rest.server.detail.model.mapping.resource.EndsWithAndOneOrMorePathFragmentUrlPathMatchTemplate;
import io.rxmicro.rest.server.detail.model.mapping.resource.EndsWithAndSinglePathFragmentUrlPathMatchTemplate;
import io.rxmicro.rest.server.detail.model.mapping.resource.StartsWithAndOneOrMorePathFragmentUrlPathMatchTemplate;
import io.rxmicro.rest.server.detail.model.mapping.resource.StartsWithAndSinglePathFragmentUrlPathMatchTemplate;
import io.rxmicro.rest.server.detail.model.mapping.resource.UrlPathMatchTemplate;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.stream.Stream;
import javax.lang.model.element.Element;

import static io.rxmicro.common.util.Formats.format;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * @author nedis
 * @since 0.8
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
final class UrlPathMatchTemplateClassResolverImplTest {

    private final UrlPathMatchTemplateClassResolver resolver = new UrlPathMatchTemplateClassResolverImpl();

    @Mock
    private Element element;

    static Stream<Arguments> urlPathMatchTemplateInstanceProvider() {
        return Stream.of(
                arguments("/**", AnyOneOrManyUrlPathMatchTemplate.class),
                arguments("/*", AnySingleUrlPathMatchTemplate.class),

                arguments("/static/*", StartsWithAndSinglePathFragmentUrlPathMatchTemplate.class),
                arguments("/static*", StartsWithAndSinglePathFragmentUrlPathMatchTemplate.class),
                arguments("/parent/child/*", StartsWithAndSinglePathFragmentUrlPathMatchTemplate.class),
                arguments("/parent/child*", StartsWithAndSinglePathFragmentUrlPathMatchTemplate.class),

                arguments("/static/**", StartsWithAndOneOrMorePathFragmentUrlPathMatchTemplate.class),
                arguments("/static**", StartsWithAndOneOrMorePathFragmentUrlPathMatchTemplate.class),
                arguments("/parent/child/**", StartsWithAndOneOrMorePathFragmentUrlPathMatchTemplate.class),
                arguments("/parent/child**", StartsWithAndOneOrMorePathFragmentUrlPathMatchTemplate.class),

                arguments("*.jpg", EndsWithAndSinglePathFragmentUrlPathMatchTemplate.class),
                arguments("*/image/image.jpg", EndsWithAndSinglePathFragmentUrlPathMatchTemplate.class),

                arguments("**.jpg", EndsWithAndOneOrMorePathFragmentUrlPathMatchTemplate.class),
                arguments("**/image/image.jpg", EndsWithAndOneOrMorePathFragmentUrlPathMatchTemplate.class)
        );
    }

    @ParameterizedTest
    @Order(1)
    @MethodSource("urlPathMatchTemplateInstanceProvider")
    void Should_return_UrlPathMatchTemplate_instance(final String url,
                                                     final Class<? extends UrlPathMatchTemplate> urlPathMatchTemplateClass) {
        final Optional<UrlPathMatchTemplate> urlPathMatchTemplate = assertDoesNotThrow(() -> resolver.getIfExists(element, url));
        assertTrue(
                urlPathMatchTemplate.isPresent(),
                format("Expected an instance of UrlPathMatchTemplate, but actual is null for '?' url!")
        );
        assertEquals(urlPathMatchTemplateClass, urlPathMatchTemplate.get().getClass());
    }

    @Test
    @Order(2)
    void Should_return_empty_Optional() {
        final Optional<UrlPathMatchTemplate> urlPathMatchTemplate =
                assertDoesNotThrow(() -> resolver.getIfExists(element, "/static/image.jpg"));
        assertEquals(Optional.empty(), urlPathMatchTemplate);
    }

    @ParameterizedTest
    @Order(3)
    @CsvSource({
            "/***",
            "**",
            "*",
            "*/",
            "*/*",
            "*/**",
            "**/*",
            "**/**",
            "*/*/*",
            "**/**/**",
            "/static/*/image.txt",
            "/static/*/*/image.txt",
            "/static/*/**/image.txt",
            "/static/**/image.txt",
            "/static/***/image.txt",
            "*/image/*",
            "*/image/**",
            "**/image/*",
            "**/image/**"
    })
    void Should_throw_InterruptProcessingException(final String url) {
        final InterruptProcessingException exception =
                assertThrows(InterruptProcessingException.class, () -> resolver.getIfExists(element, url));
        assertEquals(
                format(
                        "Found unsupported resource url template: '?'! Example of supported url templates are: " +
                                "[/static/*, /static*, /static/**, /static**, " +
                                "*.jpg, */static/image.jpg, **.jpg, **/static/image.jpg, " +
                                "/*, /**]",
                        url
                ),
                exception.getMessage()
        );
    }
}