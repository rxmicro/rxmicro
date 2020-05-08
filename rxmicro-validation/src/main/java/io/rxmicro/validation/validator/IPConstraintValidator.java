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
        if (tokenizer.countTokens() != 4) {
            throw new ValidationException(
                    "Invalid ? \"?\": Expected 4 numbers divided by '.', but actual is '?'!",
                    httpModelType, fieldName, actual);
        }
        while (tokenizer.hasMoreTokens()) {
            final String value = tokenizer.nextToken();
            try {
                final int ip4AddressPart = Integer.parseInt(value);
                if (ip4AddressPart < 0 || ip4AddressPart > 255) {
                    throw new ValidationException(
                            "Invalid ? \"?\": Expected a number between 0 and 255, but actual is '?' (IP = '?')!",
                            httpModelType, fieldName, ip4AddressPart, actual);
                }
            } catch (final NumberFormatException e) {
                throw new ValidationException(
                        "Invalid ? \"?\": Expected a number between 0 and 255, but actual is '?' (IP = '?')!",
                        httpModelType, fieldName, value, actual);
            }
        }
    }

    private void validateIPv6(final String actual,
                              final HttpModelType httpModelType,
                              final String fieldName) {
        final StringTokenizer tokenizer = new StringTokenizer(actual, ":");
        if (tokenizer.countTokens() != 8) {
            throw new ValidationException(
                    "Invalid ? \"?\": Expected 8 numbers divided by ':', but actual is '?'!",
                    httpModelType, fieldName, actual);
        }
        while (tokenizer.hasMoreTokens()) {
            final String value = tokenizer.nextToken();
            if (!value.isEmpty()) {
                try {
                    final int ip6AddressPart = Integer.decode("0x" + value);
                    if (ip6AddressPart < 0 || ip6AddressPart > 0xFFFF) {
                        throw new ValidationException(
                                "Invalid ? \"?\": Expected a number between 0 and FFFF, but actual is '?' (IP = '?')!",
                                httpModelType, fieldName, ip6AddressPart, actual);
                    }
                } catch (final NumberFormatException e) {
                    throw new ValidationException(
                            "Invalid ? \"?\": Expected a number between 0 and FFFF, but actual is '?' (IP = '?')!",
                            httpModelType, fieldName, value, actual);
                }
            }
        }
    }
}
