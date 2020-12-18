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
 * @author nedis
 * @since 0.8
 */
public final class Paths {

    public static final String HOME_DIRECTORY = "~";

    public static final String CURRENT_DIRECTORY = ".";

    public static Path createPath(final String path) {
        if (HOME_DIRECTORY.equals(path)) {
            return Path.of(System.getProperty("user.home"));
        } else if (CURRENT_DIRECTORY.equals(path)) {
            return Path.of(System.getProperty("user.dir"));
        } else {
            return Path.of(path);
        }
    }

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
