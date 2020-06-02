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

package io.rxmicro.cdi.detail;

import io.rxmicro.cdi.resource.ResourceConverter;
import io.rxmicro.files.ResourceException;

import java.util.Optional;

import static io.rxmicro.common.util.Environments.resolveEnvironmentVariables;
import static io.rxmicro.runtime.local.Instances.instantiate;

/**
 * Used by generated code that created by the {@code RxMicro Annotation Processor}.
 *
 * @author nedis
 * @hidden
 * @since 0.6
 */
public final class ResourceLoaderFactory {

    public static <R> Optional<R> loadOptionalResource(final String resourcePath,
                                                       final Class<? extends ResourceConverter<R>> converterClass) {
        final ResourceConverter<R> converter = instantiate(converterClass);
        return converter.convert(resolveEnvironmentVariables(resourcePath));
    }

    public static <R> R loadResource(final String resourcePath,
                                     final Class<? extends ResourceConverter<R>> converterClass) {
        return loadOptionalResource(resourcePath, converterClass).orElseThrow(() -> {
            throw new ResourceException("Resource '?' not found", resourcePath);
        });
    }

    private ResourceLoaderFactory() {
    }
}
