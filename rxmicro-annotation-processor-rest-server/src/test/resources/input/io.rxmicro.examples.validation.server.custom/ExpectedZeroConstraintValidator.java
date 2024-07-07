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

package io.rxmicro.examples.validation.server.custom;

import io.rxmicro.http.error.ValidationException;
import io.rxmicro.model.ModelType;
import io.rxmicro.validation.ConstraintValidator;

import java.math.BigDecimal;

// tag::content[]
public final class ExpectedZeroConstraintValidator
        implements ConstraintValidator<BigDecimal> { // <1>

    @Override
    public void validateNonNull(final BigDecimal value,
                                final ModelType modelType,
                                final String modelName) throws ValidationException {
        if (value.compareTo(BigDecimal.ZERO) != 0) { // <2>
            throw new ValidationException(
                "Invalid ? \"?\": Expected a zero value!",
                modelType, modelName
            );
        }
    }

}
// end::content[]
