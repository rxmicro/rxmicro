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
import io.rxmicro.annotation.processor.rest.model.ParentUrl;
import io.rxmicro.annotation.processor.rest.model.StaticHeaders;
import io.rxmicro.annotation.processor.rest.server.component.ServerCommonOptionBuilder;
import io.rxmicro.rest.AddHeader;
import io.rxmicro.rest.SetHeader;

import javax.lang.model.element.Element;
import java.util.Arrays;

import static io.rxmicro.common.util.UrlPaths.normalizeUrlPath;
import static io.rxmicro.http.HttpStandardHeaderNames.LOCATION;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class ServerCommonOptionBuilderImpl implements ServerCommonOptionBuilder {

    @Override
    public StaticHeaders getStaticHeaders(final Element element,
                                          final ParentUrl parentUrl) {
        final StaticHeaders staticHeaders = new StaticHeaders();
        Arrays.stream(element.getAnnotationsByType(AddHeader.class)).forEach(a ->
                staticHeaders.add(a.name(), getValue(element, parentUrl, a.name(), a.value())));
        Arrays.stream(element.getAnnotationsByType(SetHeader.class)).forEach(a ->
                staticHeaders.set(a.name(), getValue(element, parentUrl, a.name(), a.value())));
        return staticHeaders;
    }

    private String getValue(final Element element,
                            final ParentUrl parentUrl,
                            final String name,
                            final String value) {
        if (value.contains("${")) {
            throw new InterruptProcessingException(element, "Expressions not supported here. Remove redundant expressions!");
        } else if (LOCATION.equalsIgnoreCase(name)) {
            final String parent = parentUrl.getFullUrlPath("");
            if (value.startsWith(parent)) {
                return value;
            } else {
                return normalizeUrlPath(parent + "/" + value);
            }
        } else {
            return value;
        }
    }
}
