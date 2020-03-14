/*
 * Copyright 2019 http://rxmicro.io
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
import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.type.ModelClass;
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

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static io.rxmicro.annotation.processor.common.util.Names.getGenericType;
import static io.rxmicro.annotation.processor.common.util.Numbers.convertIfNecessary;
import static io.rxmicro.common.util.Formats.format;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.joining;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
@Singleton
public final class RestModelValidatorBuilderImpl extends AbstractProcessorComponent
        implements RestModelValidatorBuilder {

    @Inject
    private ConstraintAnnotationExtractor constraintAnnotationExtractor;

    @Inject
    private AnnotationValueValidator annotationValueValidator;

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
                    .map(e -> annotationValueToString(
                            builder.getClassHeaderBuilder(),
                            e.getValue().getValue(),
                            restModelField.getFieldClass(),
                            m)
                    )
                    .collect(joining(", "));
            final String constructorArg =
                    getConstructorArgs(modelFieldType, constraintConstructorArg, m.isListConstraint());
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

    private String getConstructorArgs(final ModelClass modelFieldType,
                                      final String constraintConstructorArg,
                                      final boolean isListConstraint) {
        if (isListConstraint) {
            return constraintConstructorArg;
        } else if (modelFieldType.isEnum()) {
            //Add enum class to constructorArg
            return modelFieldType.getJavaFullClassName() + ".class, " + constraintConstructorArg;
        } else if (modelFieldType.isList()) {
            final ModelClass elementModelClass = modelFieldType.asList().getElementModelClass();
            //Add enum class to constructorArg
            if (elementModelClass.isEnum()) {
                return elementModelClass.getJavaFullClassName() + ".class, " + constraintConstructorArg;
            }
        }
        return constraintConstructorArg;
    }

    /*
     * https://docs.oracle.com/javase/specs/jls/se8/html/jls-9.html#jls-9.6.1
     *
     * A primitive type:
     * - boolean, byte, short, int, long, float, double, char -> java.lang.*
     * - String -> java.lang.String
     * - Class or an invocation of Class -> com.sun.tools.javac.code.Type$ClassType
     * - An enum value -> com.sun.tools.javac.code.Symbol$VarSymbol
     * - An annotation type --> !!not supported by rxmicro.validation
     * - An array type whose component type is one of the preceding types -> com.sun.tools.javac.util.List
     *
     * Array Element:
     * - primitive + String -> com.sun.tools.javac.code.Attribute$Constant
     * - Class -> com.sun.tools.javac.code.Attribute$Class
     * - Enum value -> com.sun.tools.javac.code.Attribute$Enum
     */
    private String annotationValueToString(final ClassHeader.Builder classHeaderBuilder,
                                           final Object value,
                                           final TypeMirror expectedFieldType,
                                           final ModelConstraintAnnotation modelConstraintAnnotation) {
        if (value instanceof List) {
            classHeaderBuilder.addImports(Set.class);
            return format("Set.of(?)",
                    ((List<?>) value).stream()
                            .map(Object::toString)
                            .collect(joining(", ")));
        } else if (value instanceof VariableElement &&
                ((VariableElement) value).getKind() == ElementKind.ENUM_CONSTANT) {
            return format("?.?",
                    ((VariableElement) value).getEnclosingElement().toString(), value);
        } else if (value instanceof DeclaredType) {
            return value.toString() + ".class";
        } else if (value instanceof Character) {
            return format("'?'", escapeString(value));
        } else if (value instanceof String) {
            if (MinNumber.class.getSimpleName().equals(modelConstraintAnnotation.getAnnotationSimpleName()) ||
                    MaxNumber.class.getSimpleName().equals(modelConstraintAnnotation.getAnnotationSimpleName())) {
                return format("\"?\"", convertIfNecessary(value.toString()));
            } else {
                return format("\"?\"", escapeString(value));
            }
        } else if (value instanceof Boolean) {
            return value.toString();
        } else if (modelConstraintAnnotation.isListConstraint()) {
            return value.toString();
        } else {
            final String className = expectedFieldType.toString();
            if (className.startsWith(List.class.getName())) {
                final String genericType = getGenericType(expectedFieldType);
                return convertNumber(value, genericType);
            } else {
                return convertNumber(value, className);
            }
        }
    }

    private String escapeString(final Object value) {
        final String s = value.toString();
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            final char ch = s.charAt(i);
            if (ch == '\\' || ch == '"') {
                sb.append('\\').append(ch);
            } else if (ch == '\b') {
                sb.append('\\').append('b');
            } else if (ch == '\t') {
                sb.append('\\').append('t');
            } else if (ch == '\n') {
                sb.append('\\').append('n');
            } else if (ch == '\f') {
                sb.append('\\').append('f');
            } else if (ch == '\r') {
                sb.append('\\').append('r');
            } else if (ch < ' ' || (ch >= '\u0080' && ch < '\u00a0')
                    || (ch >= '\u2000' && ch < '\u2100')) {
                sb.append("\\u");
                final String hexCode = Integer.toHexString(ch);
                sb.append("0000", 0, 4 - hexCode.length());
                sb.append(hexCode);
            } else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }

    private String convertNumber(final Object value, final String className) {
        if (Byte.class.getName().equals(className)) {
            return "(byte) " + value;
        } else if (Short.class.getName().equals(className)) {
            return "(short) " + value;
        } else if (Integer.class.getName().equals(className)) {
            return value.toString();
        } else if (Long.class.getName().equals(className)) {
            return value + "L";
        } else if (Float.class.getName().equals(className)) {
            return "(float) " + value;
        } else if (Double.class.getName().equals(className)) {
            return value.toString();
        } else {
            return value.toString();
        }
    }
}
