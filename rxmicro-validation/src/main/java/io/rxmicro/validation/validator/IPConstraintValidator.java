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
import io.rxmicro.validation.constraint.IP;

import java.util.Collection;
import java.util.Set;
import java.util.StringTokenizer;

import static io.rxmicro.validation.constraint.IP.Version.IP_V4;

/**
 * Validator for the {@link io.rxmicro.validation.constraint.IP} constraint
 *
 * @author nedis
 * @since 0.1
 * @see io.rxmicro.validation.constraint.IP
 */
public class IPConstraintValidator implements ConstraintValidator<String> {

    private static final int EXPECTED_IP_4_TOKENS_COUNT = 4;

    private static final int EXPECTED_IP_6_TOKENS_COUNT = 8;

    private static final int MIN_IP_VALUE = 0;

    private static final int MAX_IP_4_VALUE = 255;

    private static final int MAX_IP_6_VALUE = 0xFFFF;

    private static final String MAX_IP_6_VALUE_TO_STRING = Integer.toHexString(MAX_IP_6_VALUE);

    private static final String INVALID_IP_ERROR_MESSAGE_TEMPLATE =
            "Invalid ? \"?\": Expected a number between ? and ?, but actual is '?' (IP = '?')!";

    private final Set<IP.Version> versions;

    public IPConstraintValidator(final Collection<IP.Version> versions) {
        this.versions = Set.copyOf(versions);
    }

    @Override
    public void validate(final String actual,
                         final HttpModelType httpModelType,
                         final String modelName) {
        if (actual != null) {
            if (versions.size() == 1) {
                if (versions.contains(IP_V4)) {
                    validateIPv4(actual, httpModelType, modelName);
                } else {
                    validateIPv6(actual, httpModelType, modelName);
                }
            } else {
                if (actual.contains(".")) {
                    validateIPv4(actual, httpModelType, modelName);
                } else if (actual.contains(":")) {
                    validateIPv6(actual, httpModelType, modelName);
                } else {
                    throw new ValidationException(
                            "Invalid ? \"?\": Expected IPv4 or IPv6, but actual is '?'!",
                            httpModelType, modelName, actual);
                }
            }
        }
    }

    private void validateIPv4(final String actual,
                              final HttpModelType httpModelType,
                              final String fieldName) {
        final StringTokenizer tokenizer = new StringTokenizer(actual, ".");
        if (tokenizer.countTokens() != EXPECTED_IP_4_TOKENS_COUNT) {
            throw new ValidationException(
                    "Invalid ? \"?\": Expected ? numbers divided by '.', but actual is '?'!",
                    httpModelType, fieldName, EXPECTED_IP_4_TOKENS_COUNT, actual);
        }
        while (tokenizer.hasMoreTokens()) {
            final String value = tokenizer.nextToken();
            try {
                final int ip4AddressPart = Integer.parseInt(value);
                if (ip4AddressPart < MIN_IP_VALUE || ip4AddressPart > MAX_IP_4_VALUE) {
                    throw new ValidationException(
                            INVALID_IP_ERROR_MESSAGE_TEMPLATE,
                            httpModelType, fieldName, MIN_IP_VALUE, MAX_IP_4_VALUE, ip4AddressPart, actual);
                }
            } catch (final NumberFormatException ignore) {
                throw new ValidationException(
                        INVALID_IP_ERROR_MESSAGE_TEMPLATE,
                        httpModelType, fieldName, MIN_IP_VALUE, MAX_IP_4_VALUE, value, actual);
            }
        }
    }

    private void validateIPv6(final String actual,
                              final HttpModelType httpModelType,
                              final String fieldName) {
        final StringTokenizer tokenizer = new StringTokenizer(actual, ":");
        if (tokenizer.countTokens() != EXPECTED_IP_6_TOKENS_COUNT) {
            throw new ValidationException(
                    "Invalid ? \"?\": Expected ? numbers divided by ':', but actual is '?'!",
                    httpModelType, fieldName, EXPECTED_IP_6_TOKENS_COUNT, actual);
        }
        while (tokenizer.hasMoreTokens()) {
            final String value = tokenizer.nextToken();
            if (!value.isEmpty()) {
                try {
                    final int ip6AddressPart = Integer.decode("0x" + value);
                    if (ip6AddressPart < MIN_IP_VALUE || ip6AddressPart > MAX_IP_6_VALUE) {
                        throw new ValidationException(
                                INVALID_IP_ERROR_MESSAGE_TEMPLATE,
                                httpModelType, fieldName, MIN_IP_VALUE, MAX_IP_6_VALUE_TO_STRING, ip6AddressPart, actual);
                    }
                } catch (final NumberFormatException ignore) {
                    throw new ValidationException(
                            INVALID_IP_ERROR_MESSAGE_TEMPLATE,
                            httpModelType, fieldName, MIN_IP_VALUE, MAX_IP_6_VALUE_TO_STRING, value, actual);
                }
            }
        }
    }
}
