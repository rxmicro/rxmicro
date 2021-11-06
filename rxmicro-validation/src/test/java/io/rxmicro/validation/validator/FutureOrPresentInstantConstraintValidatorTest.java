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
import io.rxmicro.validation.ConstraintValidator;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.time.Duration;
import java.time.Instant;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.rest.model.HttpModelType.PARAMETER;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author nedis
 * @since 0.7
 */
// CPD-OFF Read more: https://pmd.github.io/pmd-6.13.0/pmd_userdocs_cpd.html#suppression
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class FutureOrPresentInstantConstraintValidatorTest extends AbstractConstraintValidatorTest<Instant> {

    @Override
    ConstraintValidator<Instant> instantiate() {
        return new FutureOrPresentInstantConstraintValidator();
    }

    @Test
    @Order(11)
    void Should_process_parameter_as_a_valid_one() {
        assertDoesNotThrow(() -> validator.validate(Instant.now().plus(Duration.ofMinutes(1)), PARAMETER, "value"));
    }

    @Test
    @Order(12)
    void Should_throw_ValidationException() {
        final ValidationException exception = assertThrows(ValidationException.class, () ->
                validator.validate(Instant.now().minus(Duration.ofMinutes(1)), PARAMETER, "value"));
        {
            final String requiredFragment = "Invalid parameter \"value\": Expected a future or present instant, but actual is ";
            assertTrue(
                    exception.getMessage().contains(requiredFragment),
                    format("Exception message {?} does not contain required fragment: {?}", exception.getMessage(), requiredFragment)
            );
        }
        {
            final String requiredFragment = " (now is ";
            assertTrue(
                    exception.getMessage().contains(requiredFragment),
                    format("Exception message {?} does not contain required fragment: {?}", exception.getMessage(), requiredFragment)
            );
        }
    }
}
// CPD-ON Read more: https://pmd.github.io/pmd-6.13.0/pmd_userdocs_cpd.html#suppression
