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

import io.rxmicro.json.JsonHelper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

/**
 * Defines the resource converter that convert text file to the json array format.
 * 
 * @author nedis
 * @see JsonHelper#readJsonArray(String)
 * @since 0.6
 */
public final class FileJsonArrayResourceConverter extends AbstractFileResourceConverter<List<Object>> {

    @Override
    protected Optional<List<Object>> convert(final Path path) throws IOException {
        return Optional.of(JsonHelper.readJsonArray(Files.readString(path)));
    }
}
