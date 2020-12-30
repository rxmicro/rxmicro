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

package io.rxmicro.resource.internal;

import java.io.InputStream;

import static io.rxmicro.resource.InputStreamResources.CLASSPATH_SCHEME;

/**
 * @author nedis
 * @since 0.8
 */
public final class ClassPaths {

    private static final int CLASSPATH_SCHEME_SHIFT = CLASSPATH_SCHEME.length();

    public static String getNormalizedClassPathResource(final String classPathResource) {
        return classPathResource.startsWith(CLASSPATH_SCHEME) ? classPathResource.substring(CLASSPATH_SCHEME_SHIFT) : classPathResource;
    }

    public static InputStream getNullableClassPathResourceStream(final String classPathResource) {
        return ClassPaths.class.getClassLoader().getResourceAsStream(getNormalizedClassPathResource(classPathResource));
    }

    private ClassPaths() {
    }
}
