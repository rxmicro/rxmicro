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

package io.rxmicro.cdi.resource;

import io.rxmicro.resource.model.ResourceException;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static io.rxmicro.resource.InputStreamResources.FILE_SCHEME;

/**
 * Base abstract class that converts resource path to the {@link Path} type.
 *
 * @author nedis
 * @param <D> destination type
 * @since 0.6
 */
public abstract class AbstractFileResourceConverter<D> implements ResourceConverter<D> {

    @Override
    public final Optional<D> convert(final String resourcePath) {
        final String pathName = resourcePath.startsWith(FILE_SCHEME) ?
                resourcePath.substring(FILE_SCHEME.length()) :
                resourcePath;
        final Path path = Paths.get(pathName);
        try {
            return convert(path);
        } catch (final NoSuchFileException ex) {
            return Optional.empty();
        } catch (final IOException ex) {
            throw new ResourceException(ex, "Can't read data from path: '?'", path.toAbsolutePath());
        }
    }

    /**
     * Converts the specified {@link Path} item to the {@code D} type.
     *
     * @param path the specified {@link Path} item
     * @return the converted resource or {@link Optional#empty()} if resource not found
     * @throws IOException if any IO error occurs.
     */
    protected abstract Optional<D> convert(Path path) throws IOException;
}
