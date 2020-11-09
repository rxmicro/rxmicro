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


import io.rxmicro.annotation.processor.common.component.ModelFieldBuilder;
import io.rxmicro.annotation.processor.common.model.AnnotatedModelElement;
import io.rxmicro.annotation.processor.common.model.ModelAccessorType;
import io.rxmicro.annotation.processor.common.model.ModelField;
import io.rxmicro.annotation.processor.common.model.ModelFieldBuilderOptions;
import io.rxmicro.annotation.processor.common.model.ModelFieldType;
import io.rxmicro.annotation.processor.common.model.definition.SupportedTypesProvider;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.common.model.type.EnumModelClass;
import io.rxmicro.annotation.processor.common.model.type.InternalModelClass;
import io.rxmicro.annotation.processor.common.model.type.IterableModelClass;
import io.rxmicro.annotation.processor.common.model.type.ModelClass;
import io.rxmicro.annotation.processor.common.model.type.ObjectModelClass;
import io.rxmicro.annotation.processor.common.model.type.PrimitiveModelClass;
import io.rxmicro.model.MappingStrategy;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.lang.model.element.Element;
import javax.lang.model.element.ModuleElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

import static io.rxmicro.annotation.processor.common.SupportedOptions.RX_MICRO_MAX_JSON_NESTED_DEPTH;
import static io.rxmicro.annotation.processor.common.SupportedOptions.RX_MICRO_MAX_JSON_NESTED_DEPTH_DEFAULT_VALUE;
import static io.rxmicro.annotation.processor.common.util.Elements.allModelFields;
import static io.rxmicro.annotation.processor.common.util.Elements.asEnumElement;
import static io.rxmicro.annotation.processor.common.util.Elements.findGetters;
import static io.rxmicro.annotation.processor.common.util.Elements.findSetters;
import static io.rxmicro.annotation.processor.common.util.ModelTypeElements.asValidatedModelTypeElement;
import static io.rxmicro.annotation.processor.common.util.Names.getPackageName;
import static io.rxmicro.annotation.processor.common.util.Types.JAVA_PRIMITIVE_REPLACEMENT;
import static io.rxmicro.annotation.processor.common.util.validators.TypeValidators.validateGenericType;
import static io.rxmicro.common.util.ExCollectors.toOrderedMap;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Strings.splitByCamelCase;
import static java.util.Collections.unmodifiableMap;

/**
 * @author nedis
 * @since 0.1
 */
public abstract class AbstractModelFieldBuilder<MF extends ModelField, MC extends ObjectModelClass<MF>>
        extends AbstractProcessorComponent implements ModelFieldBuilder<MF, MC> {

    private int maxNestedLevel = -1;

    protected abstract SupportedTypesProvider getSupportedTypesProvider();

    @Override
    @SuppressWarnings("unchecked")
    public final Map<TypeElement, MC> build(final ModelFieldType modelFieldType,
                                            final ModuleElement currentModule,
                                            final Set<TypeElement> modelClasses,
                                            final ModelFieldBuilderOptions options) {
        final Map<TypeElement, MC> result = new LinkedHashMap<>();
        for (final TypeElement typeElement : modelClasses) {
            validateModelClass(typeElement);
            final ModelClass modelClass = extract(
                    currentModule, modelFieldType, typeElement, typeElement.asType(), 0, options
            );
            if (modelClass.isObject()) {
                final MC objectModelClass = (MC) modelClass;
                result.put(typeElement, objectModelClass);
                debugIfEnabled(objectModelClass);
            } else {
                error(typeElement,
                        "Invalid model class: ?. Model class could be an json object only",
                        typeElement.getQualifiedName());
            }
        }
        return unmodifiableMap(result);
    }

    protected final AnnotatedModelElement build(final TypeElement typeElement,
                                                final VariableElement variableElement) {
        return new AnnotatedModelElement(
                getPackageName(typeElement),
                variableElement,
                findGetters(typeElement, variableElement),
                findSetters(typeElement, variableElement).stream().findFirst().orElse(null));
    }

    protected abstract MF build(ModelFieldType modelFieldType,
                                VariableElement field,
                                TypeElement typeElement,
                                ModelNames modelNames,
                                Set<String> fieldNames,
                                int nestedLevel,
                                ModelFieldBuilderOptions options);

    protected void validateModelClass(final TypeElement typeElement) {
        // Sub classes can add additional validators for this type element
    }

    protected abstract PrimitiveModelClass createPrimitiveModelClass(TypeMirror type);

    protected abstract MC createObjectModelClass(ModuleElement currentModule,
                                                 ModelFieldType modelFieldType,
                                                 TypeMirror type,
                                                 TypeElement typeElement,
                                                 int nestedLevel,
                                                 ModelFieldBuilderOptions options);

    protected Map<MF, ModelClass> getFieldMap(final ModuleElement currentModule,
                                              final ModelFieldType modelFieldType,
                                              final TypeElement typeElement,
                                              final int nestedLevel,
                                              final ModelFieldBuilderOptions options) {
        final ModelNames modelNames = new ModelNames();
        final Set<String> fieldNames = new HashSet<>();
        return allModelFields(typeElement, options.isWithFieldsFromParentClasses()).stream()
                .collect(toOrderedMap(
                        el -> build(modelFieldType, el, typeElement, modelNames, fieldNames, nestedLevel, options),
                        el -> extract(currentModule, modelFieldType, el, el.asType(), nestedLevel, options)
                ));
    }

    protected final <M extends ModelField> M validateAndReturn(final ModelFieldBuilderOptions options,
                                                               final M modelField,
                                                               final TypeElement typeElement) {
        if (options.isAccessViaReflectionMustBeDetected()) {
            if (modelField.getModelReadAccessorType() == ModelAccessorType.REFLECTION) {
                warn(modelField.getFieldElement(),
                        "PERFORMANCE WARNING: To read a value from ?.? the RxMicro framework will use the reflection. " +
                                "It is recommended to add a getter or change the field modifier: " +
                                "from private to default, protected or public",
                        typeElement.getQualifiedName(),
                        modelField.getFieldName()
                );
            }
            if (modelField.getModelWriteAccessorType() == ModelAccessorType.REFLECTION) {
                warn(modelField.getFieldElement(),
                        "PERFORMANCE WARNING: To write a value to ?.? the RxMicro framework will use the reflection. " +
                                "It is recommended to add a setter or change the field modifier: " +
                                "from private to default, protected or public",
                        typeElement.getQualifiedName(),
                        modelField.getFieldName()
                );
            }
        }
        return modelField;
    }

    protected final String getModelName(final String value,
                                        final Annotation annotation,
                                        final String fieldName,
                                        final Supplier<MappingStrategy> mappingStrategySupplier) {
        if (!value.isEmpty()) {
            return value;
        } else if (annotation != null) {
            return mappingStrategySupplier.get().getModelName(splitByCamelCase(fieldName));
        } else {
            return fieldName;
        }
    }

    protected void debugIfEnabled(final ObjectModelClass<MF> objectModelClass) {
        debug(() -> format("All children object model classes of ? class:\n?",
                objectModelClass.getJavaFullClassName(), objectModelClass.getAllChildrenObjectModelClasses()));
    }

    private ModelClass extract(final ModuleElement currentModule,
                               final ModelFieldType modelFieldType,
                               final Element owner,
                               final TypeMirror type,
                               final int nestedLevel,
                               final ModelFieldBuilderOptions options) {
        if (nestedLevel > getMaxNestedLevel()) {
            throw new InterruptProcessingException(owner, "Cycle dependencies detected");
        }
        if (JAVA_PRIMITIVE_REPLACEMENT.containsKey(type.getKind())) {
            throw new InterruptProcessingException(
                    owner,
                    "Use \"?\" type instead of primitive",
                    JAVA_PRIMITIVE_REPLACEMENT.get(type.getKind()).getName());
        }
        if (type.getKind().isPrimitive()) {
            throw new InterruptProcessingException(owner, "Primitive type for model field not allowed");
        }
        return extractModelClasses(currentModule, modelFieldType, owner, type, nestedLevel, options);
    }

    private ModelClass extractModelClasses(final ModuleElement currentModule,
                                           final ModelFieldType modelFieldType,
                                           final Element owner,
                                           final TypeMirror type,
                                           final int nestedLevel,
                                           final ModelFieldBuilderOptions options) {
        if (getSupportedTypesProvider().isModelInternalType(owner)) {
            return InternalModelClass.create();
        } else if (getSupportedTypesProvider().isModelPrimitive(type)) {
            return asEnumElement(type)
                    .map(e -> (ModelClass) new EnumModelClass(type))
                    .orElseGet(() -> createPrimitiveModelClass(type));
        } else if (getSupportedTypesProvider().getCollectionContainers().contains(type)) {
            validateGenericType(owner, type, "Invalid container");
            return buildListModelClass(currentModule, modelFieldType, owner, (DeclaredType) type, nestedLevel + 1, options);
        } else {
            getSupportedTypesProvider().getReplacePrimitiveSuggestions(type.toString()).ifPresent(replace -> {
                throw new InterruptProcessingException(owner, "Use '?' type instead of '?' one!", replace.getName(), type.toString());
            });
            final TypeElement typeElement = asValidatedModelTypeElement(currentModule, owner, type, "Invalid model class", options);
            return createObjectModelClass(currentModule, modelFieldType, type, typeElement, nestedLevel + 1, options);
        }
    }

    private ModelClass buildListModelClass(final ModuleElement currentModule,
                                           final ModelFieldType modelFieldType,
                                           final Element owner,
                                           final DeclaredType type,
                                           final int nestedLevel,
                                           final ModelFieldBuilderOptions options) {
        final TypeMirror itemType = type.getTypeArguments().get(0);
        if (getSupportedTypesProvider().isModelPrimitive(itemType)) {
            return asEnumElement(itemType)
                    .map(e -> new IterableModelClass(new EnumModelClass(itemType), type))
                    .orElseGet(() -> new IterableModelClass(createPrimitiveModelClass(itemType), type));
        } else {
            final ModelClass elementModelClass = extract(
                    currentModule, modelFieldType, owner, itemType, nestedLevel + 1, options
            );
            if (elementModelClass.isIterable()) {
                throw new InterruptProcessingException(owner, "Multi array does not supported yet");
            }
            return new IterableModelClass(elementModelClass, type);
        }
    }

    private int getMaxNestedLevel() {
        if (maxNestedLevel == -1) {
            maxNestedLevel = getIntOption(
                    RX_MICRO_MAX_JSON_NESTED_DEPTH,
                    RX_MICRO_MAX_JSON_NESTED_DEPTH_DEFAULT_VALUE
            );
        }
        return maxNestedLevel;
    }

    /**
     * @author nedis
     * @since 0.1
     */
    public static final class ModelNames {

        private static final Function<String, Set<String>> NEW_SET_CREATOR = n -> new HashSet<>();

        private final Map<String, Set<String>> models = new HashMap<>();

        public Set<String> modelNames(final String name) {
            return models.computeIfAbsent(name, NEW_SET_CREATOR);
        }
    }
}
