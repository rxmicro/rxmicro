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

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import static io.rxmicro.common.util.ExCollections.unmodifiableOrderedMap;

/**
 * Utility class to get properties represented by a {@link Map} from external resource.
 *
 * @author nedis
 * @since 0.1
 */
public final class PropertiesResources {

    /**
     * Returns the {@link Optional} of the {@link Map} that contains properties from the specified classpath resource.
     *
     * <p>
     * This method returns {@link Map} instead of {@link java.util.Properties} because
     * all methods from {@link java.util.Properties} class are {@code synchronized} that is not effective during reactive approach usage
     *
     * @param classPathResource the specified classpath resource
     * @return the {@link Optional} of the {@link Map} that contains properties from the specified classpath resource or
     *         the empty {@link Optional} if classpath resource not found
     * @throws ResourceException if IO error occurs
     */
    public static Optional<Map<String, String>> loadProperties(final String classPathResource) {
        try {
            try (InputStream in = PropertiesResources.class.getClassLoader().getResourceAsStream(classPathResource)) {
                if (in == null) {
                    return Optional.empty();
                } else {
                    return Optional.of(loadProperties(in));
                }
            }
        } catch (final IOException ex) {
            throw new ResourceException(ex, "Can't read from classpath resource: ?", classPathResource);
        }
    }

    /**
     * Returns the {@link Optional} of the {@link Map} that contains properties from the specified file resource.
     *
     * <p>
     * This method returns {@link Map} instead of {@link java.util.Properties} because
     * all methods from {@link java.util.Properties} class are {@code synchronized} that is not effective during reactive approach usage
     *
     * @param filePath the specified file path
     * @return the {@link Optional} of the {@link Map} that contains properties from the specified classpath resource or
     *         the empty {@link Optional} if file not found
     * @throws ResourceException if IO error occurs
     */
    public static Optional<Map<String, String>> loadProperties(final Path filePath) {
        try {
            return Optional.of(loadProperties(Files.newInputStream(filePath)));
        } catch (final NoSuchFileException ignore) {
            return Optional.empty();
        } catch (final IOException ex) {
            throw new ResourceException(ex, "Can't read from file resource: ?", filePath.toAbsolutePath());
        }
    }

    private static Map<String, String> loadProperties(final InputStream in) throws IOException {
        final Properties properties = new Properties();
        properties.load(in);
        final Map<String, String> map = new LinkedHashMap<>();
        for (final Map.Entry<Object, Object> entry : properties.entrySet()) {
            final Object key = entry.getKey();
            final Object value = entry.getValue();
            map.put(key != null ? key.toString() : null, value != null ? value.toString() : null);
        }
        return unmodifiableOrderedMap(map);
    }

    private PropertiesResources() {
    }
}
