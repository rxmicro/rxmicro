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

package io.rxmicro.annotation.processor.rest.component.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.component.impl.BaseProcessorComponent;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.common.model.error.InternalErrorException;
import io.rxmicro.annotation.processor.common.model.type.ModelClass;
import io.rxmicro.annotation.processor.common.model.type.ObjectModelClass;
import io.rxmicro.annotation.processor.rest.component.AnnotationValueConverter;
import io.rxmicro.annotation.processor.rest.component.AnnotationValueValidator;
import io.rxmicro.annotation.processor.rest.component.ConstraintAnnotationExtractor;
import io.rxmicro.annotation.processor.rest.component.RestModelValidatorBuilder;
import io.rxmicro.annotation.processor.rest.model.RestModelField;
import io.rxmicro.annotation.processor.rest.model.RestObjectModelClass;
import io.rxmicro.annotation.processor.rest.model.validator.ModelConstraintAnnotation;
import io.rxmicro.annotation.processor.rest.model.validator.ModelValidatorClassStructure;
import io.rxmicro.validation.base.ConstraintParametersOrder;
import io.rxmicro.validation.base.ConstraintRule;
import io.rxmicro.validation.constraint.Max;
import io.rxmicro.validation.constraint.Min;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;

import static io.rxmicro.annotation.processor.common.util.Numbers.removeUnderscoresIfPresent;
import static io.rxmicro.common.CommonConstants.EMPTY_STRING;
import static io.rxmicro.common.util.Formats.format;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class RestModelValidatorBuilderImpl extends BaseProcessorComponent
        implements RestModelValidatorBuilder {

    @Inject
    private ConstraintAnnotationExtractor constraintAnnotationExtractor;

    @Inject
    private AnnotationValueValidator annotationValueValidator;

    @Inject
    private AnnotationValueConverter annotationValueConverter;

    @Inject
    private RestModelRequiredValidatorBuilder restModelRequiredValidatorBuilder;

    @Override
    public Set<ModelValidatorClassStructure> build(final EnvironmentContext environmentContext,
                                                   final List<RestObjectModelClass> objectModelClasses) {
        final Set<ModelValidatorClassStructure> result = new HashSet<>();
        for (final RestObjectModelClass objectModelClass : objectModelClasses) {
            final boolean isValidatorGenerated =
                    extractValidators(environmentContext, result, objectModelClass, new HashSet<>());
            boolean childrenAdded = false;
            for (final ObjectModelClass<RestModelField> p : objectModelClass.getAllParents()) {
                final RestObjectModelClass parent = (RestObjectModelClass) p;
                if (parent.isModelClassReturnedByRestMethod() ||
                        parent.isHeadersOrPathVariablesOrInternalsPresent() ||
                        parent.isParamEntriesPresent()) {
                    final boolean extracted = extractValidators(environmentContext, result, parent, new HashSet<>());
                    if (extracted) {
                        childrenAdded = true;
                    }
                }
            }
            if (!isValidatorGenerated && childrenAdded) {
                // Generate empty validator for child class that has parent validators!
                result.add(
                        new ModelValidatorClassStructure.Builder(environmentContext.getCurrentModule(), objectModelClass)
                                .build()
                );
            }
        }
        return result;
    }

    private boolean extractValidators(final EnvironmentContext environmentContext,
                                      final Set<ModelValidatorClassStructure> result,
                                      final RestObjectModelClass objectModelClass,
                                      final Set<ModelValidatorClassStructure> childrenValidators) {
        final ModelValidatorClassStructure.Builder builder =
                new ModelValidatorClassStructure.Builder(environmentContext.getCurrentModule(), objectModelClass);
        extractPrimitiveValidators(objectModelClass, builder);
        extractObjectChildValidators(environmentContext, result, objectModelClass, builder);
        extractObjectIterableChildValidators(environmentContext, result, objectModelClass, builder);
        if (builder.isValidatorsNotFound()) {
            return false;
        } else {
            final ModelValidatorClassStructure classStructure = builder.build();
            result.add(classStructure);
            childrenValidators.add(classStructure);
            return true;
        }
    }

    private void extractPrimitiveValidators(final RestObjectModelClass objectModelClass,
                                            final ModelValidatorClassStructure.Builder builder) {
        Stream.of(
                        objectModelClass.getPathVariableEntries().stream(),
                        objectModelClass.getHeaderEntries().stream(),
                        objectModelClass.getParamEntries().stream()
                )
                .flatMap(identity())
                .forEach(e -> extractFieldValidators(builder, e.getKey(), e.getValue()));
    }

    private void extractObjectChildValidators(final EnvironmentContext environmentContext,
                                              final Set<ModelValidatorClassStructure> result,
                                              final RestObjectModelClass objectModelClass,
                                              final ModelValidatorClassStructure.Builder builder) {
        objectModelClass.getParamEntries().stream()
                .filter(e -> e.getValue().isObject())
                .forEach(e -> extractValidators(
                        environmentContext,
                        result,
                        e.getValue().asObject(),
                        builder.getChildrenValidators()));
    }

    private void extractObjectIterableChildValidators(final EnvironmentContext environmentContext,
                                                      final Set<ModelValidatorClassStructure> result,
                                                      final RestObjectModelClass objectModelClass,
                                                      final ModelValidatorClassStructure.Builder builder) {
        objectModelClass.getParamEntries().stream()
                .filter(e -> e.getValue().isIterable() && e.getValue().asIterable().isObjectIterable())
                .forEach(e -> extractValidators(
                        environmentContext,
                        result,
                        (RestObjectModelClass) e.getValue().asIterable().getElementModelClass(),
                        builder.getChildrenValidators()));
    }

    private void extractFieldValidators(final ModelValidatorClassStructure.Builder builder,
                                        final RestModelField restModelField,
                                        final ModelClass modelFieldType) {
        restModelRequiredValidatorBuilder.addRequiredValidator(builder, restModelField, modelFieldType);
        constraintAnnotationExtractor.extract(restModelField, modelFieldType).forEach(m -> {
            annotationValueValidator.validate(m, restModelField);
            final String constraintConstructorArg = getConstraintConstructorArguments(builder, restModelField, m);
            final String constructorArg = getConstructorArgs(builder, modelFieldType, constraintConstructorArg, m.isIterableConstraint());
            final boolean validateIterable = !m.isIterableConstraint() && modelFieldType.isIterable();

            builder.add(restModelField, modelFieldType, m, constructorArg, validateIterable);
        });
        if (modelFieldType.isObject()) {
            builder.add(restModelField, modelFieldType.asObject().getJavaSimpleClassName(), false);
        } else if (modelFieldType.isIterable() && modelFieldType.asIterable().isObjectIterable()) {
            builder.add(restModelField, modelFieldType.asIterable().getElementModelClass().getJavaSimpleClassName(), true);
        }
    }

    private String getConstraintConstructorArguments(final ModelValidatorClassStructure.Builder builder,
                                                     final RestModelField restModelField,
                                                     final ModelConstraintAnnotation annotation) {
        final List<String> parameterOrder = annotation.getParameterOrder();
        final Map<String, String> map = annotation.getElementValues().entrySet().stream()
                .filter(e -> !ConstraintRule.OFF.equals(e.getKey().getSimpleName().toString()))
                .collect(toMap(
                        e -> e.getKey().getSimpleName().toString(),
                        e -> convertAnnotationValue(builder, restModelField, annotation, e)
                ));
        if (parameterOrder.isEmpty()) {
            if (map.isEmpty()) {
                return EMPTY_STRING;
            } else if (map.size() == 1) {
                return map.entrySet().iterator().next().getValue();
            } else {
                throw createInvalidConstraintParametersOrderError(annotation, "parameterOrder.isEmpty && map.size > 0");
            }
        } else if (parameterOrder.size() != map.size()) {
            throw createInvalidConstraintParametersOrderError(annotation, "parameterOrder.size != map.size");
        } else {
            final StringBuilder argsBuilder = new StringBuilder();
            for (final String parameter : parameterOrder) {
                if (argsBuilder.length() > 0) {
                    argsBuilder.append(", ");
                }
                argsBuilder.append(Optional.ofNullable(map.get(parameter)).orElseThrow(() ->
                        createInvalidConstraintParametersOrderError(annotation, format("'?' parameter not defined!", parameter))));
            }
            return argsBuilder.toString();
        }
    }

    private InternalErrorException createInvalidConstraintParametersOrderError(final ModelConstraintAnnotation annotation,
                                                                               final String details) {
        return new InternalErrorException(
                "The '@?' constraint annotation does not annotated by '@?' annotation, or '@?' annotation has invalid arguments: ?",
                annotation.getConstraintAnnotationFullName(),
                ConstraintParametersOrder.class.getName(),
                ConstraintParametersOrder.class.getName(),
                details
        );
    }

    private String convertAnnotationValue(final ModelValidatorClassStructure.Builder builder,
                                          final RestModelField restModelField,
                                          final ModelConstraintAnnotation modelConstraintAnnotation,
                                          final Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry) {
        final String result = annotationValueConverter.convert(
                restModelField.getFieldElement(), builder.getClassHeaderBuilder(),
                entry.getValue().getValue()
        );
        if (Min.class.getName().equals(modelConstraintAnnotation.getConstraintAnnotationFullName()) ||
                Max.class.getName().equals(modelConstraintAnnotation.getConstraintAnnotationFullName())) {
            return removeUnderscoresIfPresent(result);
        } else {
            return result;
        }
    }

    private String getConstructorArgs(final ModelValidatorClassStructure.Builder builder,
                                      final ModelClass modelFieldType,
                                      final String constraintConstructorArg,
                                      final boolean isListConstraint) {
        if (isListConstraint) {
            return constraintConstructorArg;
        } else if (modelFieldType.isEnum()) {
            //Add enum class to constructorArg
            builder.getClassHeaderBuilder().addImports(modelFieldType.getJavaFullClassName());
            return modelFieldType.getJavaSimpleClassName() + ".class, " + constraintConstructorArg;
        } else if (modelFieldType.isIterable()) {
            final ModelClass elementModelClass = modelFieldType.asIterable().getElementModelClass();
            //Add enum class to constructorArg
            if (elementModelClass.isEnum()) {
                builder.getClassHeaderBuilder().addImports(elementModelClass.getJavaFullClassName());
                return elementModelClass.getJavaSimpleClassName() + ".class, " + constraintConstructorArg;
            }
        }
        return constraintConstructorArg;
    }
}
