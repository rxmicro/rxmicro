/*
 * Copyright 2019 https://rxmicro.io
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

package io.rxmicro.annotation.processor.rest.component.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.component.NumberValidators;
import io.rxmicro.annotation.processor.common.component.impl.AbstractProcessorComponent;
import io.rxmicro.annotation.processor.common.util.Elements;
import io.rxmicro.annotation.processor.rest.component.AnnotationValueValidator;
import io.rxmicro.annotation.processor.rest.model.RestModelField;
import io.rxmicro.annotation.processor.rest.model.validator.ModelConstraintAnnotation;
import io.rxmicro.validation.constraint.Enumeration;
import io.rxmicro.validation.constraint.MaxDouble;
import io.rxmicro.validation.constraint.MaxInt;
import io.rxmicro.validation.constraint.MaxNumber;
import io.rxmicro.validation.constraint.MinDouble;
import io.rxmicro.validation.constraint.MinInt;
import io.rxmicro.validation.constraint.MinNumber;
import io.rxmicro.validation.constraint.Pattern;
import io.rxmicro.validation.constraint.SubEnum;

import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@Singleton
public final class AnnotationValueValidatorImpl extends AbstractProcessorComponent
        implements AnnotationValueValidator {

    private static final String ANNOTATION_ERROR_PREFFIX = "Annotation '@?' has invalid parameter: ";

    @Inject
    private NumberValidators numberValidators;

    @Override
    public void validate(final ModelConstraintAnnotation modelConstraintAnnotation,
                         final RestModelField restModelField) {
        if (Enumeration.class.getName().equals(modelConstraintAnnotation.getAnnotationFullName())) {
            validateEnumeration(restModelField);
        } else if (MaxDouble.class.getName().equals(modelConstraintAnnotation.getAnnotationFullName())) {
            if (Float.class.getName().equals(restModelField.getFieldClass().toString())) {
                final double value = restModelField.getAnnotation(MaxDouble.class).value();
                numberValidators.validateFloat(restModelField, value, MaxDouble.class);
            }
        } else if (MinDouble.class.getName().equals(modelConstraintAnnotation.getAnnotationFullName())) {
            if (Float.class.getName().equals(restModelField.getFieldClass().toString())) {
                final double value = restModelField.getAnnotation(MinDouble.class).value();
                numberValidators.validateFloat(restModelField, value, MinDouble.class);
            }
        } else if (MaxInt.class.getName().equals(modelConstraintAnnotation.getAnnotationFullName()) ||
                MinInt.class.getName().equals(modelConstraintAnnotation.getAnnotationFullName())) {
            validateMinMaxInt(modelConstraintAnnotation, restModelField);
        } else if (MaxNumber.class.getName().equals(modelConstraintAnnotation.getAnnotationFullName()) ||
                MinNumber.class.getName().equals(modelConstraintAnnotation.getAnnotationFullName())) {
            validateMinMaxNumber(modelConstraintAnnotation, restModelField);
        } else if (Pattern.class.getName().equals(modelConstraintAnnotation.getAnnotationFullName())) {
            validateRegExpSyntax(restModelField);
        } else if (SubEnum.class.getName().equals(modelConstraintAnnotation.getAnnotationFullName())) {
            validateEnumConstants(restModelField);
        }
        // Add new validation here
    }

    private void validateMinMaxInt(final ModelConstraintAnnotation modelConstraintAnnotation,
                                   final RestModelField restModelField) {
        final long value;
        final Class<? extends Annotation> annotation;
        if (MaxInt.class.getName().equals(modelConstraintAnnotation.getAnnotationFullName())) {
            value = restModelField.getAnnotation(MaxInt.class).value();
            annotation = MaxInt.class;
        } else {
            value = restModelField.getAnnotation(MinInt.class).value();
            annotation = MinInt.class;
        }
        if (Byte.class.getName().equals(restModelField.getFieldClass().toString())) {
            numberValidators.validateByte(restModelField, value, annotation);
        } else if (Short.class.getName().equals(restModelField.getFieldClass().toString())) {
            numberValidators.validateShort(restModelField, value, annotation);
        } else if (Integer.class.getName().equals(restModelField.getFieldClass().toString())) {
            numberValidators.validateInteger(restModelField, value, annotation);
        }
    }

    private void validateMinMaxNumber(final ModelConstraintAnnotation modelConstraintAnnotation,
                                      final RestModelField restModelField) {
        final String value;
        final Class<? extends Annotation> annotation;
        if (MaxNumber.class.getName().equals(modelConstraintAnnotation.getAnnotationFullName())) {
            value = restModelField.getAnnotation(MaxNumber.class).value();
            annotation = MaxNumber.class;
        } else {
            value = restModelField.getAnnotation(MinNumber.class).value();
            annotation = MinNumber.class;
        }
        if (Byte.class.getName().equals(restModelField.getFieldClass().toString())) {
            numberValidators.validateByte(restModelField, value, annotation);
        } else if (Short.class.getName().equals(restModelField.getFieldClass().toString())) {
            numberValidators.validateShort(restModelField, value, annotation);
        } else if (Integer.class.getName().equals(restModelField.getFieldClass().toString())) {
            numberValidators.validateInteger(restModelField, value, annotation);
        } else if (Long.class.getName().equals(restModelField.getFieldClass().toString())) {
            numberValidators.validateLong(restModelField, value, annotation);
        } else if (BigInteger.class.getName().equals(restModelField.getFieldClass().toString())) {
            numberValidators.validateBigInteger(restModelField, value, annotation);
        } else if (BigDecimal.class.getName().equals(restModelField.getFieldClass().toString())) {
            numberValidators.validateBigDecimal(restModelField, value, annotation);
        } else if (Float.class.getName().equals(restModelField.getFieldClass().toString())) {
            numberValidators.validateFloat(restModelField, value, annotation);
        }
    }

    private void validateEnumeration(final RestModelField restModelField) {
        final Enumeration enumeration = restModelField.getAnnotation(Enumeration.class);
        final String[] enums = enumeration.value();
        if (enums.length == 0) {
            error(restModelField.getElementAnnotatedBy(Enumeration.class),
                    ANNOTATION_ERROR_PREFFIX +
                            "Value couldn't be empty", Enumeration.class.getSimpleName());
        }
        if (Character.class.getName().equals(restModelField.getFieldClass().toString())) {
            for (final String item : enums) {
                if (item.length() != 1) {
                    error(restModelField.getElementAnnotatedBy(Enumeration.class),
                            ANNOTATION_ERROR_PREFFIX +
                                    "Expected character enum constant, but actual is '?'", Enumeration.class.getSimpleName(), item);
                }
            }
        }
    }

    @SuppressWarnings("MagicConstant")
    private void validateRegExpSyntax(final RestModelField restModelField) {
        final Pattern pattern = restModelField.getAnnotation(Pattern.class);
        try {
            java.util.regex.Pattern.compile(
                    pattern.regexp(),
                    Arrays.stream(pattern.flags()).map(Pattern.Flag::getValue).reduce((f1, f2) -> f1 | f2).orElse(0)
            );
        } catch (final PatternSyntaxException e) {
            error(restModelField.getElementAnnotatedBy(Pattern.class),
                    ANNOTATION_ERROR_PREFFIX +
                            "Invalid regular expression: ?", Pattern.class.getSimpleName(), e.getMessage());
        }
    }

    private void validateEnumConstants(final RestModelField restModelField) {
        final SubEnum subEnum = restModelField.getAnnotation(SubEnum.class);
        final List<String> names = Stream.concat(
                Arrays.stream(subEnum.exclude()),
                Arrays.stream(subEnum.include())
        ).collect(Collectors.toList());
        if (names.isEmpty()) {
            error(restModelField.getElementAnnotatedBy(SubEnum.class),
                    ANNOTATION_ERROR_PREFFIX +
                            "Expected include or exclude values", SubEnum.class.getSimpleName());
        }
        final Set<String> allowedEnumConstants = getAllowedEnumConstants(restModelField);
        for (final String name : names) {
            if (!allowedEnumConstants.contains(name)) {
                error(restModelField.getElementAnnotatedBy(SubEnum.class),
                        ANNOTATION_ERROR_PREFFIX +
                                "Value '?' is invalid enum constant. Allowed values: ?",
                        SubEnum.class.getSimpleName(), name, allowedEnumConstants);
            }
        }
        final Set<String> excludes = new HashSet<>(Arrays.asList(subEnum.exclude()));
        for (final String include : subEnum.include()) {
            if (excludes.contains(include)) {
                error(restModelField.getElementAnnotatedBy(SubEnum.class),
                        ANNOTATION_ERROR_PREFFIX +
                                "Value '?' couldn't be included and excluded at the same time",
                        SubEnum.class.getSimpleName(), include);
            }
        }
    }

    private Set<String> getAllowedEnumConstants(final RestModelField restModelField) {
        final TypeMirror typeMirror = restModelField.getFieldClass();
        if (typeMirror.toString().startsWith(List.class.getName())) {
            return Elements.getAllowedEnumConstants(((DeclaredType) typeMirror).getTypeArguments().get(0));
        } else {
            return Elements.getAllowedEnumConstants(typeMirror);
        }
    }
}
