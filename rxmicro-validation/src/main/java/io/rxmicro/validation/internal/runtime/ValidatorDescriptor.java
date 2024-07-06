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

package io.rxmicro.validation.internal.runtime;

import io.rxmicro.common.meta.BuilderMethod;
import io.rxmicro.validation.ConstraintValidator;

import java.util.Collection;
import java.util.List;

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @since 0.12
 */
public final class ValidatorDescriptor {

    private final Collection<ConstraintValidator<?>> validators;

    public static Builder builder() {
        return new Builder();
    }

    private ValidatorDescriptor(final Builder builder) {
        validators = List.copyOf(builder.validators);
    }

    public Collection<ConstraintValidator<?>> getValidators() {
        return validators;
    }

    /**
     * @author nedis
     * @since 0.12
     */
    public static final class Builder {
        private Collection<ConstraintValidator<?>> validators;

        private Builder() {
            // Use static builder() method
        }

        @BuilderMethod
        public Builder setValidators(final Collection<ConstraintValidator<?>> validators) {
            this.validators = require(validators);
            return this;
        }

        public ValidatorDescriptor build() {
            return new ValidatorDescriptor(this);
        }
    }
}
