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

import io.rxmicro.validation.base.AbstractCompositionConstraintValidator;
import io.rxmicro.validation.constraint.Port;

import java.util.List;

/**
 * Validator for the {@link io.rxmicro.validation.constraint.Port} constraint.
 *
 * @author nedis
 * @see io.rxmicro.validation.constraint.Port
 * @since 0.12
 */
public final class PortConstraintValidator extends AbstractCompositionConstraintValidator<Integer> {

    /**
     * Creates the default instance of {@link PortConstraintValidator}.
     */
    public PortConstraintValidator() {
        super(List.of(
                new MinIntConstraintValidator(Port.MIN_PORT_VALUE, true),
                new MaxIntConstraintValidator(Port.MAX_PORT_VALUE, true)
        ));
    }
}
