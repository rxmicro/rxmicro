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
 * @author nedis
 * @hidden
 * @since 0.8
 */
public abstract class UrlPathMatchTemplate implements Comparable<Object> {

    protected static final int LOWEST_PRIORITY = 1;

    protected static final int HIGHEST_PRIORITY = Integer.MAX_VALUE;

    protected final String urlTemplate;

    protected final int urlTemplateLength;

    protected UrlPathMatchTemplate(final String urlTemplate) {
        this.urlTemplateLength = urlTemplate.length();
        this.urlTemplate = urlTemplate;
    }

    public String getUrlTemplate() {
        return urlTemplate;
    }

    public abstract boolean match(HttpRequest request);

    public abstract int priority();

    public boolean isStateless() {
        return false;
    }

    @Override
    public final boolean equals(final Object other) {
        if (this == other) {
            return true;
        } else if (!(other instanceof UrlPathMatchTemplate)) {
            return false;
        } else {
            return urlTemplate.equals(((UrlPathMatchTemplate) other).urlTemplate);
        }
    }

    @Override
    public final int hashCode() {
        return urlTemplate.hashCode();
    }

    @Override
    @SuppressWarnings("NullableProblems")
    public final int compareTo(final Object other) {
        if (!(other instanceof UrlPathMatchTemplate)) {
            return 1;
        } else {
            final UrlPathMatchTemplate otherUrlPathMatchTemplate = (UrlPathMatchTemplate) other;
            final int res = otherUrlPathMatchTemplate.priority() - priority();
            return res == 0 ? urlTemplate.compareTo(otherUrlPathMatchTemplate.urlTemplate) : res;
        }
    }
}
