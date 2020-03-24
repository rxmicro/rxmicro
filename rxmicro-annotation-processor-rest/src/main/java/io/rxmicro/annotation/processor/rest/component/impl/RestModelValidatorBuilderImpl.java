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
import io.rxmicro.annotation.processor.common.component.impl.AbstractProcessorComponent;
import io.rxmicro.annotation.processor.common.model.type.ModelClass;
import io.rxmicro.annotation.processor.rest.component.AnnotationValueConverter;
import io.rxmicro.annotation.processor.rest.component.AnnotationValueValidator;
import io.rxmicro.annotation.processor.rest.component.ConstraintAnnotationExtractor;
import io.rxmicro.annotation.processor.rest.component.RestModelValidatorBuilder;
import io.rxmicro.annotation.processor.rest.model.RestModelField;
import io.rxmicro.annotation.processor.rest.model.RestObjectModelClass;
import io.rxmicro.annotation.processor.rest.model.validator.ModelConstraintAnnotation;
import io.rxmicro.annotation.processor.rest.model.validator.ModelValidatorClassStructure;
import io.rxmicro.validation.constraint.MaxNumber;
import io.rxmicro.validation.constraint.MinNumber;
import io.rxmicro.validation.constraint.Nullable;
import io.rxmicro.validation.constraint.NullableArrayItem;
import io.rxmicro.validation.validator.RequiredConstraintValidator;
import io.rxmicro.validation.validator.RequiredListConstraintValidator;

import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static io.rxmicro.annotation.processor.common.util.Numbers.removeUnderscoresIfPresent;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.joining;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@Singleton
public final class RestModelValidatorBuilderImpl extends AbstractProcessorComponent
        implements RestModelValidatorBuilder {

    @Inject
    private ConstraintAnnotationExtractor constraintAnnotationExtractor;

    @Inject
    private AnnotationValueValidator annotationValueValidator;

    @Inject
    private AnnotationValueConverter annotationValueConverter;

    @Override
    public Set<ModelValidatorClassStructure> build(final List<RestObjectModelClass> objectModelClasses) {
        final Set<ModelValidatorClassStructure> result = new HashSet<>();
        for (final RestObjectModelClass objectModelClass : objectModelClasses) {
            extractValidators(result, objectModelClass, new HashSet<>(), false);
        }
        return result;
    }

    private void extractValidators(final Set<ModelValidatorClassStructure> result,
                                   final RestObjectModelClass objectModelClass,
                                   final Set<ModelValidatorClassStructure> childrenValidators,
                                   final boolean optional) {
        final ModelValidatorClassStructure.Builder builder =
                new ModelValidatorClassStructure.Builder(objectModelClass);
        Stream.of(
                objectModelClass.getPathVariableEntries().stream(),
                objectModelClass.getHeaderEntries().stream(),
                objectModelClass.getParamEntries().stream()
        )
                .flatMap(identity())
                .forEach(e -> extractFieldValidators(builder, e.getKey(), e.getValue()));

        objectModelClass.getParamEntries().stream()
                .filter(e -> e.getValue().isObject())
                .forEach(e -> extractValidators(
                        result,
                        e.getValue().asObject(),
                        builder.getChildrenValidators(),
                        e.getKey().hasAnnotation(Nullable.class)));
        if (builder.isValidatorsNotFound()) {
            return;
        }
        final ModelValidatorClassStructure classStructure = builder.build(optional);
        result.add(classStructure);
        childrenValidators.add(classStructure);
    }

    @SuppressWarnings("SimplifiableConditionalExpression")
    private void extractFieldValidators(final ModelValidatorClassStructure.Builder builder,
                                        final RestModelField restModelField,
                                        final ModelClass modelFieldType) {
        addRequiredValidator(builder, restModelField, modelFieldType);
        constraintAnnotationExtractor.extract(restModelField, modelFieldType).forEach(m -> {
            annotationValueValidator.validate(m, restModelField);
            final String constraintConstructorArg = m.getElementValues().entrySet().stream()
                    .filter(e -> !e.getKey().getSimpleName().toString().equals("off"))
                    .map(e -> convertAnnotationValue(builder, restModelField, m, e))
                    .collect(joining(", "));
            final String constructorArg = getConstructorArgs(builder, modelFieldType, constraintConstructorArg, m.isListConstraint());
            final boolean validateList = m.isListConstraint() ? false : modelFieldType.isList();

            builder.add(restModelField, m.getAnnotationSimpleName(),
                    m.getJavaFullName(), constructorArg, validateList);
        });
        if (modelFieldType.isObject()) {
            builder.add(restModelField, modelFieldType.asObject().getJavaSimpleClassName(), false);
        } else if (modelFieldType.isList() && modelFieldType.asList().isObjectList()) {
            builder.add(restModelField, modelFieldType.asList().getElementModelClass().getJavaSimpleClassName(), true);
        }
    }

    private String convertAnnotationValue(final ModelValidatorClassStructure.Builder builder,
                                          final RestModelField restModelField,
                                          final ModelConstraintAnnotation modelConstraintAnnotation,
                                          final Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> e) {
        final String result = annotationValueConverter.convert(
                restModelField.getFieldElement(), builder.getClassHeaderBuilder(),
                e.getValue().getValue()
        );
        if (MinNumber.class.getSimpleName().equals(modelConstraintAnnotation.getAnnotationSimpleName()) ||
                MaxNumber.class.getSimpleName().equals(modelConstraintAnnotation.getAnnotationSimpleName())) {
            return removeUnderscoresIfPresent(result);
        } else {
            return result;
        }
    }

    private void addRequiredValidator(final ModelValidatorClassStructure.Builder builder,
                                      final RestModelField restModelField,
                                      final ModelClass modelFieldType) {
        final Nullable nullable = restModelField.getAnnotation(Nullable.class);
        if ((nullable == null || nullable.off())) {
            if (modelFieldType.isList()) {
                builder.add(restModelField, Nullable.class.getSimpleName(),
                        RequiredListConstraintValidator.class.getName(), null, false);
                final NullableArrayItem nullableArrayItem = restModelField.getAnnotation(NullableArrayItem.class);
                if (nullableArrayItem == null || nullableArrayItem.off()) {
                    builder.add(restModelField, NullableArrayItem.class.getSimpleName(),
                            RequiredConstraintValidator.class.getName(), null, true);
                }
            } else {
                builder.add(restModelField, Nullable.class.getSimpleName(),
                        RequiredConstraintValidator.class.getName(), null, false);
            }
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
        } else if (modelFieldType.isList()) {
            final ModelClass elementModelClass = modelFieldType.asList().getElementModelClass();
            //Add enum class to constructorArg
            if (elementModelClass.isEnum()) {
                builder.getClassHeaderBuilder().addImports(elementModelClass.getJavaFullClassName());
                return elementModelClass.getJavaSimpleClassName() + ".class, " + constraintConstructorArg;
            }
        }
        return constraintConstructorArg;
    }
}
