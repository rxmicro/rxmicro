package io.rxmicro.validation.validator;

import io.rxmicro.validation.ConstraintValidator;
import io.rxmicro.validation.ConstraintViolationException;
import io.rxmicro.validation.constraint.Port;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.rxmicro.common.util.Formats.format;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author nedis
 * @since 0.12
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PortConstraintValidatorTest extends AbstractNullableConstraintValidatorTest<Integer> {

    @Override
    ConstraintValidator<Integer> instantiate() {
        return new PortConstraintValidator();
    }

    @Test
    @Order(11)
    void Should_process_parameter_as_a_valid_one() {
        assertDoesNotThrow(() -> validator.validate(8080, PARAMETER, FIELD_NAME));
    }

    @ParameterizedTest
    @ValueSource(ints = {Integer.MIN_VALUE, Port.MIN_PORT_VALUE - 1})
    @Order(12)
    void Should_throw_ConstraintViolationException_if_value_less_then_min_allowed(final int value) {
        final ConstraintViolationException exception =
                assertThrows(ConstraintViolationException.class, () -> validator.validate(value, PARAMETER, FIELD_NAME));
        assertEquals(
                format("Invalid parameter \"fieldName\": Expected that value >= 0, but actual is ?!", value),
                exception.getMessage()
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {Port.MAX_PORT_VALUE + 1, Integer.MAX_VALUE})
    @Order(13)
    void Should_throw_ConstraintViolationException_if_value_greater_then_max_allowed(final int value) {
        final ConstraintViolationException exception =
                assertThrows(ConstraintViolationException.class, () -> validator.validate(value, PARAMETER, FIELD_NAME));
        assertEquals(
                format("Invalid parameter \"fieldName\": Expected that value <= 65535, but actual is ?!", value),
                exception.getMessage()
        );
    }
}