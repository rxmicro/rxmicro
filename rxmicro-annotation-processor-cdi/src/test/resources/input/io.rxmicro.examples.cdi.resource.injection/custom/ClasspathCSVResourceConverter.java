/*
 * Copyright (c) 2020. http://rxmicro.io
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

package io.rxmicro.examples.cdi.resource.injection.custom;

import io.rxmicro.cdi.resource.ResourceConverter;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static io.rxmicro.cdi.resource.ResourceSchemes.CLASSPATH_SCHEME;
import static io.rxmicro.files.ClasspathResources.readString;
import static java.util.stream.Collectors.toList;

public final class ClasspathCSVResourceConverter implements ResourceConverter<List<Integer>> {

    @Override
    public Optional<List<Integer>> convert(final String source) {
        final String classpathUri = source.substring(CLASSPATH_SCHEME.length());
        return readString(classpathUri)
                .map(s -> Arrays.stream(s.split(","))
                        .map(Integer::parseInt)
                        .collect(toList()));
    }
}
