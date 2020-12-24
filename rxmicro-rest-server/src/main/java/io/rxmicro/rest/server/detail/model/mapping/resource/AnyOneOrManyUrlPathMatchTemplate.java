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
 *     <li>{@code /**}</li>
 * </ul>
 *
 * @author nedis
 * @hidden
 * @since 0.8
 */
public final class AnyOneOrManyUrlPathMatchTemplate extends UrlPathMatchTemplate {

    public static final AnyOneOrManyUrlPathMatchTemplate INSTANCE = new AnyOneOrManyUrlPathMatchTemplate();

    private AnyOneOrManyUrlPathMatchTemplate() {
        super("/**");
    }

    @Override
    public boolean match(final HttpRequest request) {
        return true;
    }

    @Override
    public int priority() {
        return HIGHEST_PRIORITY;
    }

    @Override
    public boolean isStateless() {
        return true;
    }
}
