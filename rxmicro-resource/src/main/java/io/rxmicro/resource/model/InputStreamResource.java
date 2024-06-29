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

package io.rxmicro.resource.model;

import java.io.BufferedInputStream;
import java.io.InputStream;

import static io.rxmicro.common.util.Requires.require;

/**
 * The input stream resource model.
 *
 * @author nedis
 * @since 0.7
 */
public final class InputStreamResource {

    private final String resourcePath;

    private final BufferedInputStream bufferedInputStream;

    /**
     * Creates an instance of the {@link InputStreamResource} using provided normalized {@code resourcePath} and {@code inputStream}.
     *
     * @param resourcePath the provided normalized resource path.
     * @param inputStream  the provided input stream.
     */
    public InputStreamResource(final String resourcePath,
                               final InputStream inputStream) {
        this.resourcePath = require(resourcePath);
        this.bufferedInputStream = new BufferedInputStream(require(inputStream));
    }

    /**
     * Returns the normalized resource path.
     *
     * @return the normalized resource path.
     */
    public String getResourcePath() {
        return resourcePath;
    }

    /**
     * Returns the {@link BufferedInputStream} to the provided resource path.
     *
     * @return the {@link BufferedInputStream} to the provided resource path.
     */
    public BufferedInputStream getBufferedInputStream() {
        return bufferedInputStream;
    }
}
