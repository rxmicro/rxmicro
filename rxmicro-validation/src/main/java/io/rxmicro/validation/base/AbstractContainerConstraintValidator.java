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

package io.rxmicro.validation.base;

import io.rxmicro.model.ModelType;
import io.rxmicro.validation.ConstraintValidator;

/**
 * Base validator class for container constraints.
 *
 * @param <T> the type to validate
 * @author nedis
 * @since 0.1
 */
public abstract class AbstractContainerConstraintValidator<T> implements ConstraintValidator<T> {

    @Override
    public final void validateIterable(final Iterable<T> iterable,
                                       final ModelType modelType,
                                       final String modelName) {
        throw new UnsupportedOperationException("Use 'validate' instead!");
    }

    @Override
    public final void validateIterable(final Iterable<T> iterable) {
        throw new UnsupportedOperationException("Use 'validate' instead!");
    }
}
