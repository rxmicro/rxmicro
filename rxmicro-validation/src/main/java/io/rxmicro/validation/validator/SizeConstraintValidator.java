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

import io.rxmicro.http.error.ValidationException;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.validation.base.AbstractListConstraintValidator;

import java.util.List;

/**
 * Validator for the {@link io.rxmicro.validation.constraint.Size} constraint.
 *
 * @author nedis
 * @see io.rxmicro.validation.constraint.Size
 * @since 0.1
 */
public class SizeConstraintValidator extends AbstractListConstraintValidator {

    private final int expectedSize;

    /**
     * Creates the default instance of {@link SizeConstraintValidator} with the specified collection size.
     *
     * @param expectedSize the specified collection size
     */
    public SizeConstraintValidator(final int expectedSize) {
        this.expectedSize = expectedSize;
    }

    @Override
    public void validate(final List<?> value,
                         final HttpModelType httpModelType,
                         final String modelName) {
        if (value != null) {
            final int actual = value.size();
            if (actual != expectedSize) {
                throw new ValidationException(
                        "Invalid ? \"?\": Expected size = ?, but actual is ?!",
                        httpModelType, modelName, expectedSize, actual
                );
            }
        }
    }
}
