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

/**
 * Used by generated code that created by the {@code RxMicro Annotation Processor}.
 *
 * <p>
 * Examples:
 * <ul>
 *     <li>{@code /static/**}</li>
 *     <li>{@code /parent/child/**}</li>
 *     <li>{@code /static**}</li>
 *     <li>{@code /parent/child**}</li>
 * </ul>
 *
 * @author nedis
 * @hidden
 * @since 0.8
 */
public final class StartsWithAndOneOrMorePathFragmentUrlPathMatchTemplate extends UrlPathMatchTemplate {

    private static final int ORDER = 3;

    public StartsWithAndOneOrMorePathFragmentUrlPathMatchTemplate(final String urlTemplate) {
        super(urlTemplate);
    }

    @Override
    public boolean match(final HttpRequest request) {
        final String uri = request.getUri();
        final int length = uri.length();
        if (length >= urlTemplateLength - 2) {
            for (int i = 0; i < length && i < urlTemplateLength; i++) {
                final char expected = urlTemplate.charAt(i);
                if (expected == '*') {
                    return true;
                } else {
                    final char actual = uri.charAt(i);
                    if (actual != expected) {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public int order() {
        return ORDER;
    }
}
