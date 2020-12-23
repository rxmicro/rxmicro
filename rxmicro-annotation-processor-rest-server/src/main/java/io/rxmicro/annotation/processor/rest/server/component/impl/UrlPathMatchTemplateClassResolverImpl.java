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

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.rest.server.component.UrlPathMatchTemplateClassResolver;
import io.rxmicro.rest.server.detail.model.mapping.resource.AnyOneOrManyUrlPathMatchTemplate;
import io.rxmicro.rest.server.detail.model.mapping.resource.AnySingleUrlPathMatchTemplate;
import io.rxmicro.rest.server.detail.model.mapping.resource.EndsWithAndOneOrMorePathFragmentUrlPathMatchTemplate;
import io.rxmicro.rest.server.detail.model.mapping.resource.EndsWithAndSinglePathFragmentUrlPathMatchTemplate;
import io.rxmicro.rest.server.detail.model.mapping.resource.StartsWithAndOneOrMorePathFragmentUrlPathMatchTemplate;
import io.rxmicro.rest.server.detail.model.mapping.resource.StartsWithAndSinglePathFragmentUrlPathMatchTemplate;
import io.rxmicro.rest.server.detail.model.mapping.resource.UrlPathMatchTemplate;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.lang.model.element.Element;

/**
 * @author nedis
 * @since 0.8
 */
@Singleton
public final class UrlPathMatchTemplateClassResolverImpl implements UrlPathMatchTemplateClassResolver {

    private final Set<String> invalidUrls = Set.of(
            "**",
            "*/"
    );

    @Override
    public Optional<UrlPathMatchTemplate> getIfExists(final Element element,
                                                      final String url) {
        if (invalidUrls.contains(url)) {
            throw createInterruptProcessingException(element, url);
        } else if ("/**".equals(url)) {
            return Optional.of(AnyOneOrManyUrlPathMatchTemplate.INSTANCE);
        } else if ("/*".equals(url)) {
            return Optional.of(AnySingleUrlPathMatchTemplate.INSTANCE);
        } else {
            final Optional<ParseResult> result = parseUrl(element, url);
            return result.flatMap(parseResult -> interpretParseResult(url, parseResult));
        }
    }

    private Optional<ParseResult> parseUrl(final Element element,
                                           final String url) {
        AsteriskPosition position = AsteriskPosition.UNDEFINED;
        PathType pathType = PathType.UNDEFINED;
        int i = 0;
        while (i < url.length()) {
            final char ch = url.charAt(i);
            if (ch == '*') {
                if (i == 0) {
                    position = AsteriskPosition.END;
                    if (url.length() > 1) {
                        pathType = getPathType(url.charAt(1));
                        i++;
                    } else {
                        throw createInterruptProcessingException(element, url);
                    }
                } else if (i == url.length() - 1) {
                    if (position == AsteriskPosition.UNDEFINED) {
                        position = AsteriskPosition.START;
                        pathType = getPathType(url.charAt(url.length() - 2));
                        i++;
                    } else {
                        throw createInterruptProcessingException(element, url);
                    }
                } else if (i != url.length() - 2) {
                    throw createInterruptProcessingException(element, url);
                }
            }
            i++;
        }
        return convert(position, pathType);
    }

    private PathType getPathType(final char ch) {
        if (ch == '*') {
            return PathType.ONE_OR_MANY;
        } else {
            return PathType.SINGLE;
        }
    }

    private Optional<ParseResult> convert(final AsteriskPosition position,
                                          final PathType pathType) {
        if (position == AsteriskPosition.UNDEFINED) {
            return Optional.empty();
        } else {
            return Optional.of(new ParseResult(
                    position == AsteriskPosition.START,
                    pathType == PathType.SINGLE
            ));
        }
    }

    private Optional<UrlPathMatchTemplate> interpretParseResult(final String url,
                                                                final ParseResult parseResult) {
        if (parseResult.start) {
            if (parseResult.single) {
                return Optional.of(new StartsWithAndSinglePathFragmentUrlPathMatchTemplate(url));
            } else {
                return Optional.of(new StartsWithAndOneOrMorePathFragmentUrlPathMatchTemplate(url));
            }
        } else {
            if (parseResult.single) {
                return Optional.of(new EndsWithAndSinglePathFragmentUrlPathMatchTemplate(url));
            } else {
                return Optional.of(new EndsWithAndOneOrMorePathFragmentUrlPathMatchTemplate(url));
            }
        }
    }

    private InterruptProcessingException createInterruptProcessingException(final Element element,
                                                                            final String url) {
        return new InterruptProcessingException(
                element,
                "Found unsupported resource url template: '?'! Example of supported url templates are: ?",
                url,
                List.of(
                        "/static/*",
                        "/static*",
                        "/static/**",
                        "/static**",
                        "*.jpg",
                        "*/static/image.jpg",
                        "**.jpg",
                        "**/static/image.jpg",
                        "/*",
                        "/**"
                )
        );
    }

    /**
     * @author nedis
     * @since 0.8
     */
    private enum AsteriskPosition {

        START,

        END,

        UNDEFINED
    }

    /**
     * @author nedis
     * @since 0.8
     */
    private enum PathType {

        SINGLE,

        ONE_OR_MANY,

        UNDEFINED
    }

    /**
     * @author nedis
     * @since 0.8
     */
    private static final class ParseResult {

        private final boolean start;

        private final boolean single;

        private ParseResult(final boolean start,
                            final boolean single) {
            this.start = start;
            this.single = single;
        }
    }
}
