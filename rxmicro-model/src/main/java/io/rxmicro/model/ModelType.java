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

package io.rxmicro.model;

/**
 * Represents a model type.
 * <p>Model type is used by other {@code RxMicro} modules to generate user-friendly messages.
 * <p>For example:
 * <ul>
 *     <li>{@code rxmicro.validation} module uses the {@link ModelType} for generation of error messages</li>
 *     <li>etc.</li>
 * </ul>
 *
 * @author nedis
 * @since 0.12
 */
public interface ModelType {

    /**
     * String representation of a model type.
     *
     * @return String representation
     * @implSpec Should be a lowercase string!
     */
    @Override
    String toString();
}
