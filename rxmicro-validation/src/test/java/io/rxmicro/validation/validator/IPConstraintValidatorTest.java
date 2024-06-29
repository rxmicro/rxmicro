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

import io.rxmicro.validation.ConstraintValidator;
import io.rxmicro.validation.ConstraintViolationException;
import io.rxmicro.validation.constraint.IP;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;

import static io.rxmicro.common.util.Formats.format;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author nedis
 * @since 0.7
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SuppressWarnings("PMD.AvoidUsingHardCodedIP")
final class IPConstraintValidatorTest extends AbstractNullableConstraintValidatorTest<String> {

    @Override
    ConstraintValidator<String> instantiate() {
        return new IPConstraintValidator(Set.of(IP.Version.IP_V4, IP.Version.IP_V6));
    }

    @Test
    @Order(10)
    void Should_ignore_validation_for_empty_string() {
        assertDoesNotThrow(() -> validator.validate("", PARAMETER, FIELD_NAME));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "8.8.8.8",
            "255.255.255.255",
            "0.0.0.0",
            "127.0.0.1",
            "2001:0db8:11a3:09d7:1f34:8a2e:07a0:765d",
            "2001:0db8:85a3:0000:0000:8a2e:0370:7334",
            "2001:db8::ae21:ad12",
            "::ae21:ad12"
    })
    @Order(11)
    void Should_process_parameter_as_a_valid_one(final String ip) {
        assertDoesNotThrow(() -> validator.validate(ip, PARAMETER, "ip"));
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "IP_V4;     8.8.8.8",
            "IP_V4;     255.255.255.255",
            "IP_V4;     0.0.0.0",
            "IP_V4;     127.0.0.1",
            "IP_V6;     2001:0db8:11a3:09d7:1f34:8a2e:07a0:765d",
            "IP_V6;     2001:0db8:85a3:0000:0000:8a2e:0370:7334"
    })
    @Order(12)
    void Should_process_parameter_as_a_valid_one(final IP.Version version,
                                                 final String ip) {
        final IPConstraintValidator validator = new IPConstraintValidator(Set.of(version));
        assertDoesNotThrow(() -> validator.validate(ip, PARAMETER, "ip"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "0000",
            "ff"
    })
    @Order(13)
    void Should_throw_ConstraintViolationException_if_parameter_if_not_ip_address(final String ip) {
        final ConstraintViolationException exception =
                assertThrows(ConstraintViolationException.class, () -> validator.validate(ip, PARAMETER, "ip"));
        assertEquals(
                format("Invalid parameter \"ip\": Expected IPv4 or IPv6, but actual is '?'!", ip),
                exception.getMessage()
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "0.0",
            "0.0.0",
            "0.0.0.0.0"
    })
    @Order(14)
    void Should_throw_ConstraintViolationException_if_IP_V4_does_not_contain_3_dots(final String ip) {
        final ConstraintViolationException exception =
                assertThrows(ConstraintViolationException.class, () -> validator.validate(ip, PARAMETER, "ip"));
        assertEquals(
                format("Invalid parameter \"ip\": Expected 4 numbers divided by '.', but actual is '?'!", ip),
                exception.getMessage()
        );
    }

    @ParameterizedTest
    @CsvSource({
            "256.0.0.0,     256",
            "12.12.500.12,  500",
            "-1.2.3.4,      -1",
            "gg.2.3.4,      gg",
    })
    @Order(15)
    void Should_throw_ConstraintViolationException_if_IP_V4_decimal_number_out_of_range(final String ip,
                                                                                        final String invalidNumber) {
        final ConstraintViolationException exception =
                assertThrows(ConstraintViolationException.class, () -> validator.validate(ip, PARAMETER, "ip"));
        assertEquals(
                format("Invalid parameter \"ip\": Expected a number between 0 and 255, but actual is '?' (IP = '?')!", invalidNumber, ip),
                exception.getMessage()
        );
    }

    @ParameterizedTest
    @CsvSource({
            "2001:0db8:11a3:-1:1f34:8a2e:07a0:765d,     -1",
            "2001:0db8:11a3:1ffff:1f34:8a2e:07a0:765d,  1ffff",
            "2001:0db8:11a3:tttt:1f34:8a2e:07a0:765d,   tttt"
    })
    @Order(16)
    void Should_throw_ConstraintViolationException_if_IP_V6_decimal_number_out_of_range(final String ip,
                                                                                        final String invalidNumber) {
        final ConstraintViolationException exception =
                assertThrows(ConstraintViolationException.class, () -> validator.validate(ip, PARAMETER, "ip"));
        assertEquals(
                format("Invalid parameter \"ip\": Expected a number between 0 and ffff, but actual is '?' (IP = '?')!", invalidNumber, ip),
                exception.getMessage()
        );
    }
}
