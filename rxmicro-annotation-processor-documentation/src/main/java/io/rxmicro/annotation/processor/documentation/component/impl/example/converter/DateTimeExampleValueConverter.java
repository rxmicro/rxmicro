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

package io.rxmicro.annotation.processor.documentation.component.impl.example.converter;

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.documentation.component.impl.example.ExampleValueConverter;
import io.rxmicro.annotation.processor.rest.model.RestModelField;

import java.time.Instant;
import java.time.format.DateTimeParseException;

import static io.rxmicro.annotation.processor.common.util.Types.SUPPORTED_DATE_TIME_CLASSES;
import static io.rxmicro.common.local.Examples.INSTANT_EXAMPLE;
import static io.rxmicro.common.util.Formats.format;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class DateTimeExampleValueConverter extends ExampleValueConverter {

    @Override
    public boolean isSupported(final RestModelField restModelField) {
        return SUPPORTED_DATE_TIME_CLASSES.contains(restModelField.getFieldClass().toString());
    }

    @Override
    public Object convert(final RestModelField restModelField, final String value) {
        if (restModelField.getFieldClass().toString().equals(Instant.class.getName())) {
            try {
                Instant.parse(value);
                return value;
            } catch (final DateTimeParseException ignore) {
                showInvalidExampleValueError(
                        restModelField,
                        format("ISO-8601 instant (Example: '?')", INSTANT_EXAMPLE),
                        value
                );
                return ERROR_DETECTED;
            }
        }
        throw new UnsupportedOperationException("Not impl yet");
    }
}
