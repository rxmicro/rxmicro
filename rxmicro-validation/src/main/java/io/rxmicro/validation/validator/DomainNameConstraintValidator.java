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

/**
 * Validator for the {@link io.rxmicro.validation.constraint.DomainName} constraint.
 *
 * @author nedis
 * @see io.rxmicro.validation.constraint.DomainName
 * @since 0.1
 */
public final class DomainNameConstraintValidator extends AbstractDomainOrHostNameConstraintValidator {

    /**
     * Domain rule message.
     */
    public static final String DOMAIN_NAME_RULE =
            "Domain name must contains letters [a-z] or [A-Z], digits [0-9], underscores, periods, and dashes only!";

    private static final String NAME = "domain name";

    /**
     * Creates the default instance of {@link DomainNameConstraintValidator} type.
     *
     * @param errorWithDetails validation error with detail message or not
     */
    public DomainNameConstraintValidator(final boolean errorWithDetails) {
        super(errorWithDetails);
    }

    @Override
    protected String getRule() {
        return DOMAIN_NAME_RULE;
    }

    @Override
    protected String getName() {
        return NAME;
    }
}
