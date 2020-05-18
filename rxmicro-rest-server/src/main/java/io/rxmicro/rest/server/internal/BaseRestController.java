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

package io.rxmicro.rest.server.internal;

import io.rxmicro.rest.server.RestServerConfig;
import io.rxmicro.rest.server.detail.component.HttpResponseBuilder;
import io.rxmicro.rest.server.detail.model.HttpResponse;
import io.rxmicro.rest.server.local.component.HttpErrorResponseBodyBuilder;

/**
 * @author nedis
 * @since 0.1
 */
public abstract class BaseRestController {

    private static final int DEFAULT_NOT_FOUND_STATUS_CODE = 404;

    protected HttpResponseBuilder httpResponseBuilder;

    protected RestServerConfig restServerConfig;

    protected HttpErrorResponseBodyBuilder httpErrorResponseBodyBuilder;

    @SuppressWarnings("EmptyMethod")
    protected void postConstruct() {
        // Sub class can override this method
    }

    protected final HttpResponse notFound(final String message) {
        return httpErrorResponseBodyBuilder.build(httpResponseBuilder.build(), DEFAULT_NOT_FOUND_STATUS_CODE, message);
    }

    public abstract Class<?> getRestControllerClass();

    @Override
    public final int hashCode() {
        return getRestControllerClass().hashCode();
    }

    @Override
    public final boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final BaseRestController that = (BaseRestController) other;
        return getRestControllerClass().equals(that.getRestControllerClass());
    }
}
