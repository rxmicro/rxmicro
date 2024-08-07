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
import io.rxmicro.validation.ConstraintValidator;
import io.rxmicro.validation.constraint.IP;

import java.util.Collection;
import java.util.Set;
import java.util.StringTokenizer;

import static io.rxmicro.validation.ConstraintViolations.reportViolation;
import static io.rxmicro.validation.constraint.IP.Version.IP_V4;
import static java.lang.Integer.toHexString;

/**
 * Validator for the {@link io.rxmicro.validation.constraint.IP} constraint.
 *
 * @author nedis
 * @see io.rxmicro.validation.constraint.IP
 * @since 0.1
 */
public class IPConstraintValidator implements ConstraintValidator<String> {

    private static final int EXPECTED_IP_4_TOKENS_COUNT = 4;

    private static final int MIN_IP_VALUE = 0;

    private static final int MAX_IP_4_VALUE = 255;

    private static final int MAX_IP_6_VALUE = 0xFFFF;

    private static final String MAX_IP_6_VALUE_TO_STRING = toHexString(MAX_IP_6_VALUE);

    private static final String INVALID_IP_ERROR_MESSAGE_TEMPLATE =
            "Invalid ? \"?\": Expected a number between ? and ?, but actual is '?' (IP = '?')!";

    private final Set<IP.Version> versions;

    /**
     * Creates the default instance of {@link IPConstraintValidator} with the supported IP versions.
     *
     * @param versions the supported IP versions
     */
    public IPConstraintValidator(final Collection<IP.Version> versions) {
        this.versions = Set.copyOf(versions);
    }

    @Override
    public void validateNonNull(final String actual,
                                final ModelType modelType,
                                final String modelName) {
        if (actual.length() > 0) {
            if (versions.size() == 1) {
                if (versions.contains(IP_V4)) {
                    validateIPv4(actual, modelType, modelName);
                } else {
                    validateIPv6(actual, modelType, modelName);
                }
            } else {
                if (actual.contains(".")) {
                    validateIPv4(actual, modelType, modelName);
                } else if (actual.contains(":")) {
                    validateIPv6(actual, modelType, modelName);
                } else {
                    reportViolation(
                            "Invalid ? \"?\": Expected IPv4 or IPv6, but actual is '?'!",
                            modelType, modelName, actual
                    );
                }
            }
        }
    }

    private void validateIPv4(final String actual,
                              final ModelType modelType,
                              final String fieldName) {
        final StringTokenizer tokenizer = new StringTokenizer(actual, ".");
        if (tokenizer.countTokens() != EXPECTED_IP_4_TOKENS_COUNT) {
            reportViolation(
                    "Invalid ? \"?\": Expected ? numbers divided by '.', but actual is '?'!",
                    modelType, fieldName, EXPECTED_IP_4_TOKENS_COUNT, actual);
        }
        while (tokenizer.hasMoreTokens()) {
            final String value = tokenizer.nextToken();
            try {
                final int ip4AddressPart = Integer.parseInt(value);
                if (ip4AddressPart < MIN_IP_VALUE || ip4AddressPart > MAX_IP_4_VALUE) {
                    reportViolation(
                            INVALID_IP_ERROR_MESSAGE_TEMPLATE,
                            modelType, fieldName, MIN_IP_VALUE, MAX_IP_4_VALUE, ip4AddressPart, actual);
                }
            } catch (final NumberFormatException ignored) {
                reportViolation(
                        INVALID_IP_ERROR_MESSAGE_TEMPLATE,
                        modelType, fieldName, MIN_IP_VALUE, MAX_IP_4_VALUE, value, actual);
            }
        }
    }

    private void validateIPv6(final String actual,
                              final ModelType modelType,
                              final String fieldName) {
        final StringTokenizer tokenizer = new StringTokenizer(actual, ":");
        while (tokenizer.hasMoreTokens()) {
            final String value = tokenizer.nextToken();
            if (!value.isEmpty()) {
                try {
                    final int ip6AddressPart = Integer.decode("0x" + value);
                    if (ip6AddressPart < MIN_IP_VALUE || ip6AddressPart > MAX_IP_6_VALUE) {
                        reportViolation(
                                INVALID_IP_ERROR_MESSAGE_TEMPLATE,
                                modelType, fieldName, MIN_IP_VALUE, MAX_IP_6_VALUE_TO_STRING, toHexString(ip6AddressPart), actual);
                    }
                } catch (final NumberFormatException ignored) {
                    reportViolation(
                            INVALID_IP_ERROR_MESSAGE_TEMPLATE,
                            modelType, fieldName, MIN_IP_VALUE, MAX_IP_6_VALUE_TO_STRING, value, actual);
                }
            }
        }
    }
}
