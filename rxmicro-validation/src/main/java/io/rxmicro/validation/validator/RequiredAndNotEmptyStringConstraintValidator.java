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

import io.rxmicro.model.ModelType;

import static io.rxmicro.validation.ConstraintViolations.reportViolation;

/**
 * Validator for the required and not empty string values.
 *
 * @author nedis
 * @see io.rxmicro.validation.constraint.AllowEmptyString
 * @see io.rxmicro.validation.constraint.Nullable
 * @see io.rxmicro.validation.constraint.NullableArrayItem
 * @since 0.7
 */
public class RequiredAndNotEmptyStringConstraintValidator extends RequiredConstraintValidator {

    @Override
    public void validate(final Object actual,
                         final ModelType modelType,
                         final String modelName) {
        super.validate(actual, modelType, modelName);
        if (((String) actual).isEmpty()) {
            reportViolation("Invalid ? \"?\": Empty string is not valid value!", modelType, modelName);
        }
    }
}
