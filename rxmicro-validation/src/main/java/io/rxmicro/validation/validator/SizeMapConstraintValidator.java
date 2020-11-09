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
import io.rxmicro.validation.base.AbstractContainerConstraintValidator;

import java.util.List;
import java.util.Map;

/**
 * Validator for the {@link io.rxmicro.validation.constraint.Size} constraint.
 *
 * @author nedis
 * @see io.rxmicro.validation.constraint.Size
 * @since 0.1
 */
public class SizeMapConstraintValidator extends AbstractContainerConstraintValidator<Map<?, ?>> {

    private final int expectedSize;

    /**
     * Creates the default instance of {@link SizeMapConstraintValidator} with the specified map size.
     *
     * @param expectedSize the specified map size
     */
    public SizeMapConstraintValidator(final int expectedSize) {
        this.expectedSize = expectedSize;
    }

    @Override
    public void validate(final Map<?, ?> map,
                         final HttpModelType httpModelType,
                         final String modelName) {
        if (map != null) {
            final int actual = map.size();
            if (actual != expectedSize) {
                throw new ValidationException(
                        "Invalid ? \"?\": Expected ? object property(ies), but actual is ?. (object: ?)!",
                        httpModelType, modelName, expectedSize, actual, map
                );
            }
        }
    }
}
