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

package io.rxmicro.common.util;

import io.rxmicro.common.model.InputStreamResource;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

/**
 * @author nedis
 * @since 0.7
 */
public final class InputStreamResources {

    /**
     * File scheme.
     */
    public static final String FILE_SCHEME = "file://";

    /**
     * Classpath scheme.
     */
    public static final String CLASSPATH_SCHEME = "classpath:";

    public static Optional<InputStreamResource> getInputStreamResource(final String resourcePath) {
        return getClasspathInputStreamResource(resourcePath)
                .or(() -> getFileInputStreamResource(resourcePath))
                .or(() -> getUrlInputStreamResource(resourcePath));
    }

    public static List<String> getSupportedInputStreamResources() {
        return List.of("classpath", "file", "url");
    }

    private static Optional<InputStreamResource> getClasspathInputStreamResource(final String resourcePath) {
        final String normalizedResourcePath = resourcePath.startsWith(CLASSPATH_SCHEME) ?
                resourcePath.substring(CLASSPATH_SCHEME.length()) :
                resourcePath;
        return Optional.ofNullable(InputStreamResources.class.getClassLoader().getResourceAsStream(normalizedResourcePath))
                .map(in -> new InputStreamResource(CLASSPATH_SCHEME + normalizedResourcePath, in));
    }

    private static Optional<InputStreamResource> getFileInputStreamResource(final String resourcePath) {
        final String normalizedResourcePath = resourcePath.startsWith(FILE_SCHEME) ?
                resourcePath.substring(FILE_SCHEME.length()) :
                resourcePath;
        try {
            final Path normalizedPath = Paths.get(normalizedResourcePath).toAbsolutePath();
            return Optional.of(Files.newInputStream(normalizedPath))
                    .map(in -> new InputStreamResource(FILE_SCHEME + normalizedPath, in));
        } catch (final IOException ignore) {
            return Optional.empty();
        }
    }

    private static Optional<InputStreamResource> getUrlInputStreamResource(final String resourcePath) {
        try {
            return Optional.of(new InputStreamResource(resourcePath, new URL(resourcePath).openStream()));
        } catch (final IOException ignore) {
            return Optional.empty();
        }
    }
}
