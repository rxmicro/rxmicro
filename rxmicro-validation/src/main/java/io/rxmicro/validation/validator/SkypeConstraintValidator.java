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
import io.rxmicro.validation.ConstraintValidator;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @see io.rxmicro.validation.constraint.Skype
 * @since 0.1
 */
public class SkypeConstraintValidator implements ConstraintValidator<String> {

    @Override
    public void validate(final String actual,
                         final HttpModelType httpModelType,
                         final String modelName) throws ValidationException {
        // TODO
    }
}
