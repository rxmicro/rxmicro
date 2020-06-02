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

import java.util.Optional;

/**
 * Base interface that defines resource converter.
 *
 * <p>
 * Developer can define his(her) own converter implementation and use with @{@link io.rxmicro.cdi.Resource} annotation.
 *
 * @author nedis
 * @param <D> destination type
 * @see io.rxmicro.cdi.Resource
 * @since 0.6
 */
public interface ResourceConverter<D> {

    /**
     * Returns the converted resource specified by the resourcePath.
     *
     * <p>
     * If resource not found converter must return {@link Optional#empty()}
     *
     * @param resourcePath the resource path
     * @return the converted resource or {@link Optional#empty()} if resource not found
     * @see ResourceSchemes#CLASSPATH_SCHEME
     * @see ResourceSchemes#FILE_SCHEME
     */
    Optional<D> convert(String resourcePath);
}
