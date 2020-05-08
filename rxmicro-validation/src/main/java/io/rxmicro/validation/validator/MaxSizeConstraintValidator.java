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

package io.rxmicro.validation.validator;

import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.validation.ConstraintValidator;
import io.rxmicro.validation.base.AbstractMaxConstraintValidator;

import java.util.List;

/**
 * Validator for the {@link io.rxmicro.validation.constraint.MaxSize} constraint
 *
 * @author nedis
 * @since 0.1
 * @see io.rxmicro.validation.constraint.MaxSize
 */
public class MaxSizeConstraintValidator extends AbstractMaxConstraintValidator<Integer>
        implements ConstraintValidator<List<?>> {

    public MaxSizeConstraintValidator(final int maxValue,
                                      final boolean inclusive) {
        super(maxValue, inclusive);
    }

    @Override
    public void validate(final List<?> value,
                         final HttpModelType httpModelType,
                         final String modelName) {
        if (value != null) {
            final int actual = value.size();
            validate(actual, httpModelType, modelName,
                    "Invalid ? \"?\": Expected that 'list size' <= ?, where 'list size' is '?'!",
                    "Invalid ? \"?\": Expected that 'list size' < ?, where 'list size' is '?'!");
        }
    }

    @Override
    public void validateList(final List<List<?>> list,
                             final HttpModelType httpModelType,
                             final String modelName) {
        throw new AbstractMethodError("Use 'validate' instead!");
    }

    @Override
    public void validateList(final List<List<?>> models) {
        throw new AbstractMethodError("Use 'validate' instead!");
    }
}
