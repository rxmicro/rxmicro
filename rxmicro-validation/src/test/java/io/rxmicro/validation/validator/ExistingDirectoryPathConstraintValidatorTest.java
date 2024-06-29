package io.rxmicro.validation.validator;

import io.rxmicro.validation.ConstraintValidator;
import io.rxmicro.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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
class ExistingDirectoryPathConstraintValidatorTest extends AbstractNullableConstraintValidatorTest<Path> {

    @TempDir
    static Path tempDir;

    static Path testDir;

    static Path testFile;

    @BeforeAll
    static void beforeAll() throws IOException {
        testDir = tempDir.resolve("directory");
        Files.createDirectory(testDir);

        testFile = tempDir.resolve("file.txt");
        Files.createFile(testFile);
    }

    @Override
    ConstraintValidator<Path> instantiate() {
        return new ExistingDirectoryPathConstraintValidator();
    }

    @Test
    @Order(11)
    void Should_process_parameter_as_a_valid_one() {
        assertDoesNotThrow(() -> validator.validate(testDir, PARAMETER, "value"));
    }

    @Test
    @Order(12)
    void Should_throw_ConstraintViolationException_if_directory_not_found() {
        final ConstraintViolationException exception =
                assertThrows(ConstraintViolationException.class, () -> validator.validate(tempDir.resolve("/notFound"), PARAMETER, "value"));
        assertEquals(
                "Invalid parameter \"value\": Directory not found: '/notFound'!",
                exception.getMessage()
        );
    }

    @Test
    @Order(13)
    void Should_throw_ConstraintViolationException_if_not_a_directory() {
        final ConstraintViolationException exception =
                assertThrows(ConstraintViolationException.class, () -> validator.validate(tempDir.resolve(testFile), PARAMETER, "value"));
        assertEquals(
                format("Invalid parameter \"value\": Not a directory: '?'!", testFile.toAbsolutePath()),
                exception.getMessage()
        );
    }
}