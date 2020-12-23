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

package io.rxmicro.files;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Path utils.
 *
 * @author nedis
 * @see Path
 * @since 0.8
 */
public final class Paths {

    /**
     * Defines platform independent constant that must be interpret as {@code user.home} directory.
     */
    public static final String HOME_DIRECTORY = "~";

    /**
     * Defines platform independent constant that must be interpret as {@code user.dir} directory.
     */
    public static final String CURRENT_DIRECTORY = ".";

    /**
     * Creates a {@link Path} instance using the provided path string.
     *
     * <p>
     * This method supports the {@link #HOME_DIRECTORY} and {@link #CURRENT_DIRECTORY} constants!
     *
     * @param path the provided path string.
     * @return a {@link Path} instance
     * @throws java.nio.file.InvalidPathException if the path string cannot be converted to a {@link Path} instance
     */
    public static Path createPath(final String path) {
        if (HOME_DIRECTORY.equals(path)) {
            return Path.of(System.getProperty("user.home"));
        } else if (CURRENT_DIRECTORY.equals(path)) {
            return Path.of(System.getProperty("user.dir"));
        } else {
            return Path.of(path);
        }
    }

    /**
     * Validates the provided {@link Path} instance.
     *
     * @param path the provided {@link Path} instance.
     * @throws ResourceException if the provided {@link Path} instance not found or is not directory.
     */
    public static void validateDirectory(final Path path) {
        if (!Files.exists(path)) {
            throw new ResourceException("Directory not found: '?'!", path.toAbsolutePath());
        }
        if (!Files.isDirectory(path)) {
            throw new ResourceException("'?' path is not a directory!", path.toAbsolutePath());
        }
    }

    private Paths() {
    }
}
