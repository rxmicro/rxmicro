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

package io.rxmicro.rest.server.detail.component;

import io.rxmicro.http.error.HttpErrorException;
import io.rxmicro.rest.server.detail.model.HttpResponse;

/**
 * @author nedis
 * @since 0.9
 */
public abstract class CustomExceptionServerModelWriter<T extends HttpErrorException> extends ServerModelWriter<T> {

    private final ServerModelWriter<T> serverModelWriter;

    public CustomExceptionServerModelWriter(final ServerModelWriter<T> serverModelWriter) {
        this.serverModelWriter = serverModelWriter;
    }

    @Override
    public final void write(final T model,
                            final HttpResponse response) {
        serverModelWriter.write(model, response);
    }

    @SuppressWarnings("unused")
    public void validate(final T exception) {
        // do nothing: subclasses should override this method.
    }
}
