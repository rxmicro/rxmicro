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

package io.rxmicro.annotation.processor.rest.component.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.rest.component.HttpMethodMappingBuilder;
import io.rxmicro.annotation.processor.rest.component.PathVariableExtractor;
import io.rxmicro.annotation.processor.rest.model.HttpMethodMapping;
import io.rxmicro.annotation.processor.rest.model.ParentUrl;
import io.rxmicro.rest.method.DELETE;
import io.rxmicro.rest.method.GET;
import io.rxmicro.rest.method.HEAD;
import io.rxmicro.rest.method.OPTIONS;
import io.rxmicro.rest.method.PATCH;
import io.rxmicro.rest.method.POST;
import io.rxmicro.rest.method.PUT;
import io.rxmicro.rest.model.UrlSegments;

import javax.lang.model.element.ExecutableElement;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static io.rxmicro.rest.method.HttpMethods.HTTP_METHOD_ANNOTATIONS;
import static java.util.stream.Collectors.toList;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@Singleton
public final class HttpMethodMappingBuilderImpl extends AbstractUrlBuilder implements HttpMethodMappingBuilder {

    @Inject
    private PathVariableExtractor pathVariableExtractor;

    @Override
    public List<HttpMethodMapping> buildList(final ParentUrl parentUrl,
                                             final ExecutableElement element) {
        return Stream.of(
                Arrays.stream(element.getAnnotationsByType(DELETE.class))
                        .map(a -> create(element, a.annotationType(), parentUrl, a.value(), false)),
                Arrays.stream(element.getAnnotationsByType(GET.class))
                        .map(a -> create(element, a.annotationType(), parentUrl, a.value(), false)),
                Arrays.stream(element.getAnnotationsByType(HEAD.class))
                        .map(a -> create(element, a.annotationType(), parentUrl, a.value(), false)),
                Arrays.stream(element.getAnnotationsByType(OPTIONS.class))
                        .map(a -> create(element, a.annotationType(), parentUrl, a.value(), false)),
                Arrays.stream(element.getAnnotationsByType(POST.class))
                        .map(a -> create(element, a.annotationType(), parentUrl, a.value(), a.httpBody())),
                Arrays.stream(element.getAnnotationsByType(PATCH.class))
                        .map(a -> create(element, a.annotationType(), parentUrl, a.value(), a.httpBody())),
                Arrays.stream(element.getAnnotationsByType(PUT.class))
                        .map(a -> create(element, a.annotationType(), parentUrl, a.value(), a.httpBody()))
        ).flatMap(Function.identity()).collect(toList());
    }

    @Override
    public HttpMethodMapping buildSingle(final ParentUrl parentUrl,
                                         final ExecutableElement executableElement) {
        final List<HttpMethodMapping> mappings = buildList(parentUrl, executableElement);
        if (mappings.isEmpty()) {
            throw new InterruptProcessingException(executableElement,
                    "Expected one of the following http method annotations: ?",
                    HTTP_METHOD_ANNOTATIONS);
        } else if (mappings.size() > 1) {
            throw new InterruptProcessingException(executableElement,
                    "Expected ONLY one of the following http method annotations: ?",
                    HTTP_METHOD_ANNOTATIONS);
        } else {
            return mappings.get(0);
        }
    }

    private HttpMethodMapping create(final ExecutableElement executableElement,
                                     final Class<? extends Annotation> annotationClass,
                                     final ParentUrl parentUrl,
                                     final String path,
                                     final boolean httpBody) {
        final HttpMethodMapping.Builder builder = new HttpMethodMapping.Builder()
                .setMethod(annotationClass)
                .setHttpBody(httpBody);
        validateNotNull(executableElement, path, "Invalid URL path mapping");
        validateThatPathIsTrimmedValue(executableElement, path, "Invalid URL path mapping");
        final String fullPath = parentUrl.getFullUrlPath(path);
        if (path.contains("${")) {
            final UrlSegments urlSegments = pathVariableExtractor.extractPathSegments(executableElement, fullPath);
            builder.setUrlSegments(urlSegments);
        } else {
            builder.setUri(fullPath);
        }
        if (parentUrl.isHeaderVersionStrategy()) {
            builder.setVersionHeaderValue(parentUrl.getVersionValue());
        }
        return builder.build();
    }
}
