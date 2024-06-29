package io.rxmicro.validation.validator;

import io.rxmicro.validation.ConstraintValidator;
import io.rxmicro.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author nedis
 * @since 0.12
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ExistingPathPathConstraintValidatorTest extends AbstractNullableConstraintValidatorTest<Path> {

    @TempDir
    static Path tempDir;

    @Override
    ConstraintValidator<Path> instantiate() {
        return new ExistingPathPathConstraintValidator();
    }

    @Test
    @Order(11)
    void Should_process_parameter_as_a_valid_one() {
        assertDoesNotThrow(() -> validator.validate(tempDir, PARAMETER, "value"));
    }

    @Test
    @Order(12)
    void Should_throw_ConstraintViolationException() {
        final ConstraintViolationException exception =
                assertThrows(ConstraintViolationException.class, () -> validator.validate(tempDir.resolve("/not-found"), PARAMETER, "value"));
        assertEquals(
                "Invalid parameter \"value\": Path not found: '/not-found'!",
                exception.getMessage()
        );
    }
}