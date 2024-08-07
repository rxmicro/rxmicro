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

package io.rxmicro.validation.detail;

import io.rxmicro.validation.ConstraintValidator;

import java.util.HashMap;
import java.util.Map;

import static io.rxmicro.reflection.Reflections.instantiate;

/**
 * Used by generated code that created by the {@code RxMicro Annotation Processor}.
 *
 * @author nedis
 * @hidden
 * @since 0.1
 */
public final class StatelessValidators {

    private static final Map<Class<? extends ConstraintValidator<?>>, ConstraintValidator<?>> VALIDATOR_MAP = new HashMap<>();

    private StatelessValidators() {
    }

    /**
     * @implNote If the provided class is not a class of stateless validator(i.e. validator without any constructor arguments), then
     * the {@link io.rxmicro.common.CheckedWrapperException} will be thrown.
     */
    @SuppressWarnings("unchecked")
    public static <T extends ConstraintValidator<?>> T getStatelessValidator(final Class<T> constraintValidatorClass) {
        T constraintValidator = (T) VALIDATOR_MAP.get(constraintValidatorClass);
        if (constraintValidator == null) {
            constraintValidator = instantiate(constraintValidatorClass);
            VALIDATOR_MAP.put(constraintValidatorClass, constraintValidator);
        }
        return constraintValidator;
    }
}
