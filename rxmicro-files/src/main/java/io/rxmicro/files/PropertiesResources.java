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
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import static io.rxmicro.common.util.ExCollections.unmodifiableOrderedMap;
import static io.rxmicro.common.util.Strings.startsWith;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class PropertiesResources {

    public static Optional<Map<String, String>> loadProperties(final String classPathResource) {
        try {
            final InputStream in = PropertiesResources.class.getClassLoader().getResourceAsStream(classPathResource);
            if (in == null) {
                return Optional.empty();
            } else {
                return Optional.of(loadProperties(in));
            }
        } catch (final IOException e) {
            throw new ResourceException(e, "Can't read from classpath resource: ?", classPathResource);
        }
    }

    public static Optional<Map<String, String>> loadProperties(final Path filePath) {
        try {
            return Optional.of(loadProperties(Files.newInputStream(filePath)));
        } catch (final NoSuchFileException e) {
            return Optional.empty();
        } catch (final IOException e) {
            throw new ResourceException(e, "Can't read from file resource: ?", filePath.toAbsolutePath());
        }
    }

    private static Map<String, String> loadProperties(final InputStream in) throws IOException {
        try (final BufferedReader br = new BufferedReader(new InputStreamReader(in, UTF_8))) {
            final Map<String, String> map = new LinkedHashMap<>();
            String line;
            while ((line = br.readLine()) != null) {
                if(line.isBlank() || startsWith(line, '#')) {
                    continue;
                }
                addProperty(map, line);
            }
            return unmodifiableOrderedMap(map);
        }
    }

    private static void addProperty(final Map<String, String> map,
                                    final String line) {
        final String[] data = line.split("=");
        if (data.length != 2) {
            throw new ResourceException("Can't load property from resource, " +
                    "because it is not a properties resource: ?", line);
        }
        map.put(data[0], data[1]);
    }

    private PropertiesResources() {
    }
}
