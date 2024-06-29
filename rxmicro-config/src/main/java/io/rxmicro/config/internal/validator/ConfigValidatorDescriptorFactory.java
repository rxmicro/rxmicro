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

package io.rxmicro.config.internal.validator;

import io.rxmicro.common.ImpossibleException;
import io.rxmicro.config.Config;
import io.rxmicro.config.ConfigException;
import io.rxmicro.reflection.JavaBeans;
import io.rxmicro.reflection.Reflections;
import io.rxmicro.validation.ConstraintValidator;
import io.rxmicro.validation.base.ConstraintParametersOrder;
import io.rxmicro.validation.base.ConstraintRule;
import io.rxmicro.validation.constraint.AllowEmptyString;
import io.rxmicro.validation.constraint.Nullable;
import io.rxmicro.validation.validator.RequiredAndNotEmptyStringConstraintValidator;
import io.rxmicro.validation.validator.RequiredConstraintValidator;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.validation.detail.StatelessValidators.getStatelessValidator;

/**
 * @author nedis
 * @since 0.12
 */
public final class ConfigValidatorDescriptorFactory {

    private static final String RX_MICRO_VALIDATION_PACKAGE = Nullable.class.getPackageName();

    public static ConfigValidatorDescriptor create(final Config instance, final String propertyName) {
        final String propertyErrorDescriptor = format("?.?", instance.getClass().getName(), propertyName);

        final List<AnnotatedElement> annotatedElements = JavaBeans.getAnnotatedElements(instance.getClass(), propertyName);
        if (annotatedElements.isEmpty()) {
            throw new ImpossibleException("Property not defined: ?", propertyErrorDescriptor);
        }
        return ConfigValidatorDescriptor.builder()
                .setValidators(buildValidators(propertyErrorDescriptor, annotatedElements))
                .build();
    }

    private static Collection<ConstraintValidator<?>> buildValidators(final String propertyErrorDescriptor,
                                                                      final List<AnnotatedElement> annotatedElements) {
        final Map<String, ConstraintValidator<?>> validators = new LinkedHashMap<>();
        for (final AnnotatedElement annotatedElement : annotatedElements) {
            final Class<?> propertyType = annotatedElement instanceof Field ? ((Field) annotatedElement).getType() : ((Method) annotatedElement).getReturnType();
            addRequiredValidator(validators, propertyType, annotatedElement);
            for (final Annotation annotation : annotatedElement.getAnnotations()) {
                final Class<? extends Annotation> annotationType = annotation.annotationType();
                if (annotationType.getPackageName().equals(RX_MICRO_VALIDATION_PACKAGE)) {
                    final Class<? extends ConstraintValidator> validatorClass = getValidatorClass(propertyErrorDescriptor, propertyType, annotationType);
                    final ConstraintParametersOrder constraintParametersOrder = annotationType.getAnnotation(ConstraintParametersOrder.class);
                    if (constraintParametersOrder == null) {
                        validators.put(annotationType.getName(), getStatelessValidator(validatorClass));
                    } else {
                        final Object[] args = getAnnotationParameters(annotation, constraintParametersOrder.value());
                        final Class[] argTypes = getAnnotationParameterTypes(args);
                        validators.put(annotationType.getName(), Reflections.instantiate(validatorClass, false, argTypes, args));
                    }
                }
            }
        }
        return validators.values();
    }

    private static Class[] getAnnotationParameterTypes(final Object[] args) {
        final Class[] result = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            final Object arg = args[i];
            if (arg instanceof Long) {
                result[i] = Long.TYPE;
            } else if (arg instanceof Integer) {
                result[i] = Integer.TYPE;
            } else if (arg instanceof Short) {
                result[i] = Short.TYPE;
            } else if (arg instanceof Byte) {
                result[i] = Byte.TYPE;
            } else if (arg instanceof Double) {
                result[i] = Double.TYPE;
            } else if (arg instanceof Float) {
                result[i] = Float.TYPE;
            } else if (arg instanceof Character) {
                result[i] = Character.TYPE;
            } else if (arg instanceof Boolean) {
                result[i] = Boolean.TYPE;
            } else {
                result[i] = arg.getClass();
            }
        }
        return result;
    }

    private static Object[] getAnnotationParameters(final Annotation annotation, final String[] value) {
        return Arrays.stream(value)
                .filter(param -> !ConstraintRule.OFF.equals(param))
                .map(param -> Reflections.invokeMethod(annotation, param)).toArray();
    }

    private static Class<? extends ConstraintValidator> getValidatorClass(final String propertyErrorDescriptor,
                                                                          final Class<?> propertyType,
                                                                          final Class<? extends Annotation> annotationType) {
        final ConstraintRule constraintRule = require(annotationType.getAnnotation(ConstraintRule.class));
        final Class<?>[] supportedTypes = constraintRule.supportedTypes();
        for (int i = 0; i < supportedTypes.length; i++) {
            if (supportedTypes[i].equals(propertyType)) {
                return constraintRule.validatorClass()[i];
            }
        }
        throw new ConfigException(
                "'?' annotation can't be applied to '?', because there are no defined validator for this property type.",
                annotationType.getName(), propertyErrorDescriptor
        );
    }

    private static void addRequiredValidator(final Map<String, ConstraintValidator<?>> validators,
                                             final Class<?> propertyType,
                                             final AnnotatedElement annotatedElement) {

        if (String.class.equals(propertyType)) {
            final AllowEmptyString allowEmptyString = annotatedElement.getAnnotation(AllowEmptyString.class);
            if (allowEmptyString == null || allowEmptyString.off()) {
                validators.put(RX_MICRO_VALIDATION_PACKAGE, getStatelessValidator(RequiredAndNotEmptyStringConstraintValidator.class));
                return;
            }
        }
        final Nullable nullable = annotatedElement.getAnnotation(Nullable.class);
        if (nullable == null || nullable.off()) {
            validators.put(RX_MICRO_VALIDATION_PACKAGE, getStatelessValidator(RequiredConstraintValidator.class));
        }
    }

    private ConfigValidatorDescriptorFactory() {
    }
}
