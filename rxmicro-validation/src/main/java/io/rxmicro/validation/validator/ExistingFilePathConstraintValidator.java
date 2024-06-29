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
import io.rxmicro.validation.constraint.ExistingFile;

import java.nio.file.Files;
import java.nio.file.Path;

import static io.rxmicro.validation.ConstraintViolations.reportViolation;

/**
 * Validator for the {@link ExistingFile} constraint.
 *
 * @author nedis
 * @see ExistingFile
 * @since 0.1
 */
public final class ExistingFilePathConstraintValidator implements ConstraintValidator<Path> {

    @Override
    public void validateNonNull(final Path actual,
                                final ModelType modelType,
                                final String modelName) {
        if (!Files.exists(actual)) {
            reportViolation("Invalid ? \"?\": File not found: '?'!", modelType, modelName, actual.toAbsolutePath());
        } else if (!Files.isRegularFile(actual)) {
            reportViolation("Invalid ? \"?\": Not a file: '?'!", modelType, modelName, actual.toAbsolutePath());
        }
    }
}
