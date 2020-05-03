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

package io.rxmicro.annotation.processor.documentation.component.impl.example;

import io.rxmicro.annotation.processor.common.component.impl.AbstractProcessorComponent;
import io.rxmicro.annotation.processor.rest.model.RestModelField;
import io.rxmicro.documentation.Example;

import java.util.Locale;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public abstract class ExampleValueConverter extends AbstractProcessorComponent {

    protected static final Object ERROR_DETECTED = "error";

    public abstract boolean isSupported(RestModelField restModelField);

    public abstract Object convert(RestModelField restModelField,
                                   String value);

    protected final void showInvalidExampleTypeError(final RestModelField restModelField,
                                                     final Class<?> expectedType,
                                                     final String actualValue) {
        error(
                restModelField.getElementAnnotatedBy(Example.class),
                "Invalid example value type! Expected: ? type, Actual: '?'",
                expectedType.getSimpleName().toLowerCase(Locale.ENGLISH),
                actualValue
        );
    }

    protected final void showInvalidExampleValueError(final RestModelField restModelField,
                                                      final String expectedValue,
                                                      final String actualValue) {
        error(
                restModelField.getElementAnnotatedBy(Example.class),
                "Invalid example value! Expected: '?', Actual: '?'",
                expectedValue,
                actualValue
        );
    }
}
