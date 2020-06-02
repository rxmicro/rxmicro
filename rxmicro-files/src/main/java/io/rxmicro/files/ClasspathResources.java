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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Utility class to get data from classpath resources.
 *
 * @author nedis
 * @since 0.1
 */
public final class ClasspathResources {

    /**
     * Reads bytes from the classpath resource and converts it to the {@code UTF-8} {@link String}.
     *
     * @param classPathResource the classpath resource
     * @return the {@link List} of {@code UTF-8} strings read from the classpath resource or
     *          the empty list if the classpath resource not found
     * @throws ResourceException if IO error occurs
     */
    public static Optional<String> readString(final String classPathResource) {
        final List<String> lines = readLines(classPathResource);
        return lines.isEmpty() ? Optional.empty() : Optional.of(String.join(System.lineSeparator(), lines));
    }

    /**
     * Reads bytes from the classpath resource and converts it to the {@link List} of {@code UTF-8} strings.
     *
     * @param classPathResource the classpath resource
     * @return the {@link List} of {@code UTF-8} strings read from the classpath resource or
     *          the empty list if the classpath resource not found
     * @throws ResourceException if IO error occurs
     */
    public static List<String> readLines(final String classPathResource) {
        try {
            try (InputStream in = ClasspathResources.class.getClassLoader().getResourceAsStream(classPathResource)) {
                if (in == null) {
                    return List.of();
                } else {
                    return readLines(in);
                }
            }
        } catch (final IOException ex) {
            throw new ResourceException(ex, "Can't read from classpath resource: ?", classPathResource);
        }
    }

    private static List<String> readLines(final InputStream in) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(in, UTF_8))) {
            final List<String> lines = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        }
    }

    private ClasspathResources() {
    }
}
