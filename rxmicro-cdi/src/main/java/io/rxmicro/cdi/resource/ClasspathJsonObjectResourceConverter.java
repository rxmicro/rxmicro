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

import java.util.Map;
import java.util.Optional;

import static io.rxmicro.common.util.InputStreamResources.CLASSPATH_SCHEME;
import static io.rxmicro.files.ClasspathResources.readString;

/**
 * Defines the resource converter that convert text classpath resource to the json object format.
 *
 * @author nedis
 * @see JsonHelper#readJsonObject(String)
 * @since 0.6
 */
public final class ClasspathJsonObjectResourceConverter implements ResourceConverter<Map<String, Object>> {

    @Override
    public Optional<Map<String, Object>> convert(final String resourcePath) {
        final String classpathUri = resourcePath.substring(CLASSPATH_SCHEME.length());
        return readString(classpathUri).map(JsonHelper::readJsonObject);
    }
}
