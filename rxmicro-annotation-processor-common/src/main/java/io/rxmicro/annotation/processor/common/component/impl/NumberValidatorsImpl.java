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

package io.rxmicro.annotation.processor.common.component.impl;

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.component.NumberValidators;
import io.rxmicro.annotation.processor.common.model.ModelField;

import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.math.BigInteger;

import static io.rxmicro.annotation.processor.common.util.Numbers.removeUnderscoresIfPresent;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@Singleton
public final class NumberValidatorsImpl extends AbstractProcessorComponent implements NumberValidators {

    private static final String ERROR_MESSAGE_PREFIX = "Annotation '@?' has invalid parameter: ";

    private static final String VALUE_OUT_OF_RANGE_EXPECTED_TEMPLATE = "Value out of range: Expected ? < ? < ?";

    @Override
    public void validateFloat(final ModelField modelField,
                              final double value,
                              final Class<? extends Annotation> annotationClass) {
        if (value < Float.MIN_VALUE || value > Float.MAX_VALUE) {
            error(modelField.getElementAnnotatedBy(annotationClass),
                    ERROR_MESSAGE_PREFIX +
                            VALUE_OUT_OF_RANGE_EXPECTED_TEMPLATE,
                    annotationClass.getSimpleName(), Float.MIN_VALUE, value, Float.MAX_VALUE);
        }
    }

    @Override
    public void validateByte(final ModelField modelField,
                             final long value,
                             final Class<? extends Annotation> annotationClass) {
        if (value < Byte.MIN_VALUE || value > Byte.MAX_VALUE) {
            error(modelField.getElementAnnotatedBy(annotationClass),
                    ERROR_MESSAGE_PREFIX +
                            VALUE_OUT_OF_RANGE_EXPECTED_TEMPLATE,
                    annotationClass.getSimpleName(), Byte.MIN_VALUE, value, Byte.MAX_VALUE);
        }
    }

    @Override
    public void validateShort(final ModelField modelField,
                              final long value,
                              final Class<? extends Annotation> annotationClass) {
        if (value < Short.MIN_VALUE || value > Short.MAX_VALUE) {
            error(modelField.getElementAnnotatedBy(annotationClass),
                    ERROR_MESSAGE_PREFIX +
                            VALUE_OUT_OF_RANGE_EXPECTED_TEMPLATE,
                    annotationClass.getSimpleName(), Short.MIN_VALUE, value, Short.MAX_VALUE);
        }
    }

    @Override
    public void validateInteger(final ModelField modelField,
                                final long value,
                                final Class<? extends Annotation> annotationClass) {
        if (value < Integer.MIN_VALUE || value > Integer.MAX_VALUE) {
            error(modelField.getElementAnnotatedBy(annotationClass),
                    ERROR_MESSAGE_PREFIX +
                            VALUE_OUT_OF_RANGE_EXPECTED_TEMPLATE,
                    annotationClass.getSimpleName(), Integer.MIN_VALUE, value, Integer.MAX_VALUE);
        }
    }

    @Override
    public boolean validateBigInteger(final ModelField modelField,
                                      final String value,
                                      final Class<? extends Annotation> annotationClass) {
        return toBigIntegerOrNull(modelField, value, annotationClass) != null;
    }

    @Override
    public boolean validateBigDecimal(final ModelField modelField,
                                      final String value,
                                      final Class<? extends Annotation> annotationClass) {
        return toBigDecimalOrNull(modelField, value, annotationClass) != null;
    }

    @Override
    public boolean validateByte(final ModelField modelField,
                                final String value,
                                final Class<? extends Annotation> annotationClass) {
        final BigInteger result = toBigIntegerOrNull(modelField, value, annotationClass);
        if (result == null) {
            return false;
        } else if (result.compareTo(BigInteger.valueOf(Byte.MIN_VALUE)) < 0 ||
                result.compareTo(BigInteger.valueOf(Byte.MAX_VALUE)) > 0) {
            error(modelField.getElementAnnotatedBy(annotationClass),
                    ERROR_MESSAGE_PREFIX +
                            VALUE_OUT_OF_RANGE_EXPECTED_TEMPLATE,
                    annotationClass.getSimpleName(), Byte.MIN_VALUE, value, Byte.MAX_VALUE);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean validateShort(final ModelField modelField,
                                 final String value,
                                 final Class<? extends Annotation> annotationClass) {
        final BigInteger result = toBigIntegerOrNull(modelField, value, annotationClass);
        if (result == null) {
            return false;
        } else if (result.compareTo(BigInteger.valueOf(Short.MIN_VALUE)) < 0 ||
                result.compareTo(BigInteger.valueOf(Short.MAX_VALUE)) > 0) {
            error(modelField.getElementAnnotatedBy(annotationClass),
                    ERROR_MESSAGE_PREFIX +
                            VALUE_OUT_OF_RANGE_EXPECTED_TEMPLATE,
                    annotationClass.getSimpleName(), Short.MIN_VALUE, value, Short.MAX_VALUE);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean validateInteger(final ModelField modelField,
                                   final String value,
                                   final Class<? extends Annotation> annotationClass) {
        final BigInteger result = toBigIntegerOrNull(modelField, value, annotationClass);
        if (result == null) {
            return false;
        } else if (result.compareTo(BigInteger.valueOf(Integer.MIN_VALUE)) < 0 ||
                result.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) > 0) {
            error(modelField.getElementAnnotatedBy(annotationClass),
                    ERROR_MESSAGE_PREFIX +
                            VALUE_OUT_OF_RANGE_EXPECTED_TEMPLATE,
                    annotationClass.getSimpleName(), Integer.MIN_VALUE, value, Integer.MAX_VALUE);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean validateLong(final ModelField modelField,
                                final String value,
                                final Class<? extends Annotation> annotationClass) {
        final BigInteger result = toBigIntegerOrNull(modelField, value, annotationClass);
        if (result == null) {
            return false;
        } else if ((result.compareTo(BigInteger.valueOf(Long.MIN_VALUE)) < 0 ||
                result.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0)) {
            error(modelField.getElementAnnotatedBy(annotationClass),
                    ERROR_MESSAGE_PREFIX +
                            VALUE_OUT_OF_RANGE_EXPECTED_TEMPLATE,
                    annotationClass.getSimpleName(), Long.MIN_VALUE, value, Long.MAX_VALUE);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean validateFloat(final ModelField modelField,
                                 final String value,
                                 final Class<? extends Annotation> annotationClass) {
        final BigDecimal result = toBigDecimalOrNull(modelField, value, annotationClass);
        if (result == null) {
            return false;
        } else if (result.compareTo(BigDecimal.valueOf(Float.MIN_VALUE)) < 0 ||
                result.compareTo(BigDecimal.valueOf(Float.MAX_VALUE)) > 0) {
            error(modelField.getElementAnnotatedBy(annotationClass),
                    ERROR_MESSAGE_PREFIX +
                            VALUE_OUT_OF_RANGE_EXPECTED_TEMPLATE,
                    annotationClass.getSimpleName(), Float.MIN_VALUE, value, Float.MAX_VALUE);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean validateDouble(final ModelField modelField,
                                  final String value,
                                  final Class<? extends Annotation> annotationClass) {
        final BigDecimal result = toBigDecimalOrNull(modelField, value, annotationClass);
        if (result == null) {
            return false;
        } else if (result.compareTo(BigDecimal.valueOf(Double.MIN_VALUE)) < 0 ||
                result.compareTo(BigDecimal.valueOf(Double.MAX_VALUE)) > 0) {
            error(modelField.getElementAnnotatedBy(annotationClass),
                    ERROR_MESSAGE_PREFIX +
                            VALUE_OUT_OF_RANGE_EXPECTED_TEMPLATE,
                    annotationClass.getSimpleName(), Double.MIN_VALUE, value, Double.MAX_VALUE);
            return false;
        } else {
            return true;
        }
    }

    private BigInteger toBigIntegerOrNull(final ModelField modelField,
                                          final String value,
                                          final Class<? extends Annotation> annotationClass) {
        try {
            return new BigInteger(removeUnderscoresIfPresent(value));
        } catch (final NumberFormatException e) {
            error(modelField.getElementAnnotatedBy(annotationClass), ERROR_MESSAGE_PREFIX +
                    "Expected an integer number", annotationClass.getSimpleName());
            return null;
        }
    }

    private BigDecimal toBigDecimalOrNull(final ModelField modelField,
                                          final String value,
                                          final Class<? extends Annotation> annotationClass) {
        try {
            return new BigDecimal(removeUnderscoresIfPresent(value));
        } catch (final NumberFormatException e) {
            error(modelField.getElementAnnotatedBy(annotationClass), ERROR_MESSAGE_PREFIX +
                    "Expected a number", annotationClass.getSimpleName());
            return null;
        }
    }
}
