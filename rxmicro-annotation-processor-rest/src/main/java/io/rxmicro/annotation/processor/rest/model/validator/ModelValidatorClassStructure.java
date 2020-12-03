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

package io.rxmicro.annotation.processor.rest.model.validator;

import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.ClassStructure;
import io.rxmicro.annotation.processor.common.model.WithParentClassStructure;
import io.rxmicro.annotation.processor.rest.model.RestModelField;
import io.rxmicro.annotation.processor.rest.model.RestObjectModelClass;
import io.rxmicro.http.error.ValidationException;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.validation.ConstraintValidator;
import io.rxmicro.validation.detail.StatelessValidators;
import io.rxmicro.validation.validator.RequiredConstraintValidator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.REFLECTIONS_FULL_CLASS_NAME;
import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getModelTransformerFullClassName;
import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getModelTransformerInstanceName;
import static io.rxmicro.annotation.processor.common.util.Names.getDefaultVarName;
import static io.rxmicro.annotation.processor.common.util.Names.getPackageName;
import static io.rxmicro.annotation.processor.common.util.Names.getSimpleName;
import static io.rxmicro.annotation.processor.common.util.Names.getTypeWithoutGeneric;
import static io.rxmicro.annotation.processor.common.util.ProcessingEnvironmentHelper.getTypes;
import static io.rxmicro.common.util.ExCollections.EMPTY_STRING_ARRAY;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;
import static java.util.stream.Collectors.toList;

/**
 * @author nedis
 * @since 0.1
 */
public final class ModelValidatorClassStructure extends ClassStructure
        implements WithParentClassStructure<ModelValidatorClassStructure, RestModelField, RestObjectModelClass> {

    private final boolean optional;

    private final ClassHeader.Builder classHeaderBuilder;

    private final RestObjectModelClass modelClass;

    private final List<ModelValidatorCreator> modelValidatorCreators;

    private final List<ModelFieldValidatorsInvoker> modelFieldValidatorsInvokers;

    private final Set<String> stdValidatorClassImports;

    private final Set<ModelValidatorClassStructure> childrenValidators;

    private ModelValidatorClassStructure parent;

    private ModelValidatorClassStructure(final boolean optional,
                                         final ClassHeader.Builder classHeaderBuilder,
                                         final RestObjectModelClass modelClass,
                                         final List<ModelValidatorCreator> modelValidatorCreators,
                                         final List<ModelFieldValidatorsInvoker> modelFieldValidatorsInvokers,
                                         final Set<String> stdValidatorClassImports,
                                         final Set<ModelValidatorClassStructure> childrenValidators) {
        this.optional = optional;
        this.classHeaderBuilder = classHeaderBuilder;
        this.modelClass = require(modelClass);
        this.modelValidatorCreators = require(modelValidatorCreators);
        this.modelFieldValidatorsInvokers = require(modelFieldValidatorsInvokers);
        this.stdValidatorClassImports = new TreeSet<>(require(stdValidatorClassImports));
        this.childrenValidators = require(childrenValidators);
    }

    @Override
    public RestObjectModelClass getModelClass() {
        return modelClass;
    }

    public String getModelFullClassName() {
        return modelClass.getJavaFullClassName();
    }

    @Override
    public boolean setParent(final ModelValidatorClassStructure parent) {
        this.parent = parent;
        return true;
    }

    @Override
    public String getTargetFullClassName() {
        return getModelTransformerFullClassName(modelClass.getModelTypeElement(), ConstraintValidator.class);
    }

    @Override
    public String getTemplateName() {
        return "rest/$$RestModelValidatorTemplate.javaftl";
    }

    @Override
    public Map<String, Object> getTemplateVariables() {
        final Map<String, Object> map = new LinkedHashMap<>();
        map.put("JAVA_MODEL_CLASS", modelClass);
        map.put("OPTIONAL", optional);
        map.put("JAVA_MODEL_VALIDATOR_CREATORS", modelValidatorCreators);
        map.put("JAVA_MODEL_VALIDATOR_INVOKERS", modelFieldValidatorsInvokers);
        map.put("JAVA_MODEL_VALIDATOR_CHILDREN", childrenValidators);
        if (parent != null) {
            map.put("PARENT", parent.getTargetSimpleClassName());
            map.put("HAS_PARENT", true);
        } else {
            map.put("HAS_PARENT", false);
        }
        return map;
    }

    @Override
    public ClassHeader getClassHeader() {
        classHeaderBuilder.addImports(stdValidatorClassImports.toArray(EMPTY_STRING_ARRAY))
                .addImports(
                        HttpModelType.class,
                        ValidationException.class,
                        ConstraintValidator.class
                )
                .addImports(
                        childrenValidators.stream()
                                .map(v -> getModelTransformerFullClassName(
                                        v.getModelClass().getModelTypeElement(),
                                        ConstraintValidator.class)
                                )
                                .toArray(String[]::new)
                )
                .addStaticImport(StatelessValidators.class, "getStatelessValidator");
        if (parent != null) {
            classHeaderBuilder.addImports(parent.getTargetFullClassName());
        }
        if (isRequiredReflectionGetter()) {
            classHeaderBuilder.addStaticImport(REFLECTIONS_FULL_CLASS_NAME, "getFieldValue");
        }
        return classHeaderBuilder.build();
    }

    @Override
    public boolean isRequiredReflectionGetter() {
        return modelClass.isReadReflectionRequired();
    }

    /**
     * @author nedis
     * @since 0.1
     */
    @SuppressWarnings("UnusedReturnValue")
    public static final class Builder {

        private static final String STANDARD_VALIDATOR_PACKAGE =
                getPackageName(RequiredConstraintValidator.class.getName());

        private final ClassHeader.Builder classHeaderBuilder;

        private final RestObjectModelClass modelClass;

        private final List<ModelValidatorCreator> modelValidatorCreators = new ArrayList<>();

        private final Map<String, ModelValidatorCreator> stateLessValidatorMap = new LinkedHashMap<>();

        private final Map<RestModelField, List<ModelValidator>> modelFieldValidatorsMap = new LinkedHashMap<>();

        private final Set<String> stdValidatorClassImports = new HashSet<>();

        private final Set<ModelValidatorClassStructure> childrenValidators = new LinkedHashSet<>();

        public Builder(final RestObjectModelClass modelClass) {
            this.modelClass = require(modelClass);
            classHeaderBuilder = ClassHeader.newClassHeaderBuilder(getPackageName(modelClass.getModelTypeElement()));
        }

        public boolean isValidatorsNotFound() {
            return modelValidatorCreators.isEmpty() &&
                    stateLessValidatorMap.isEmpty() &&
                    childrenValidators.isEmpty();
        }

        public ClassHeader.Builder getClassHeaderBuilder() {
            return classHeaderBuilder;
        }

        public Set<ModelValidatorClassStructure> getChildrenValidators() {
            return childrenValidators;
        }

        public void add(final RestModelField restModelField,
                        final String typeSimpleClassName,
                        final boolean validateIterable) {
            final String instanceName =
                    getModelTransformerInstanceName(typeSimpleClassName, ConstraintValidator.class);
            modelFieldValidatorsMap.computeIfAbsent(restModelField, m -> new ArrayList<>())
                    .add(new ModelValidator(instanceName, getValidationMethodName(restModelField, validateIterable)));
        }

        public void add(final RestModelField restModelField,
                        final String constraintAnnotationSimpleName,
                        final String validatorClassOriginal,
                        final String constructorArgs,
                        final boolean validateIterable) {
            final boolean isStateless = (constructorArgs == null || constructorArgs.isEmpty()) &&
                    getPackageName(validatorClassOriginal).equals(STANDARD_VALIDATOR_PACKAGE);
            final String validatorClass = getValidatorClassName(validatorClassOriginal);
            final String instanceName;
            if (isStateless) {
                instanceName = getDefaultVarName(getSimpleName(validatorClass));
                stateLessValidatorMap.putIfAbsent(validatorClass,
                        new ModelValidatorCreator(validatorClass, instanceName));
            } else {
                instanceName = format("???",
                        restModelField.getFieldName(),
                        constraintAnnotationSimpleName,
                        getSimpleName(validatorClass));
                modelValidatorCreators.add(new ModelValidatorCreator(validatorClass, instanceName, constructorArgs));
            }
            modelFieldValidatorsMap.computeIfAbsent(restModelField, m -> new ArrayList<>())
                    .add(new ModelValidator(instanceName, getValidationMethodName(restModelField, validateIterable)));
        }

        private String getValidationMethodName(final RestModelField restModelField,
                                               final boolean validateIterable) {
            if (validateIterable) {
                if (Map.class.getName().equals(getTypes().erasure(restModelField.getFieldClass()).toString())) {
                    return "validateMapValues";
                } else {
                    return "validateIterable";
                }
            } else {
                return "validate";
            }
        }

        private String getValidatorClassName(final String validatorClass) {
            if (validatorClass.startsWith(STANDARD_VALIDATOR_PACKAGE)) {
                stdValidatorClassImports.add(getTypeWithoutGeneric(validatorClass));
                return getSimpleName(getTypeWithoutGeneric(validatorClass));
            } else {
                return validatorClass;
            }
        }

        public ModelValidatorClassStructure build(final boolean optional) {
            return new ModelValidatorClassStructure(
                    optional,
                    classHeaderBuilder, modelClass,
                    Stream.concat(
                            stateLessValidatorMap.values().stream(),
                            modelValidatorCreators.stream()
                    ).collect(toList()),
                    modelFieldValidatorsMap.entrySet().stream()
                            .map(e -> new ModelFieldValidatorsInvoker(e.getKey(), e.getValue()))
                            .collect(toList()),
                    stdValidatorClassImports,
                    childrenValidators);
        }
    }
}
