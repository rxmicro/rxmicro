/*
 * Copyright (c) 2020. http://rxmicro.io
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
import io.rxmicro.annotation.processor.common.model.ModelFieldType;
import io.rxmicro.annotation.processor.common.model.definition.SupportedTypesProvider;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.common.model.type.EnumModelClass;
import io.rxmicro.annotation.processor.common.model.type.InternalModelClass;
import io.rxmicro.annotation.processor.common.model.type.ListModelClass;
import io.rxmicro.annotation.processor.common.model.type.ModelClass;
import io.rxmicro.annotation.processor.common.model.type.ObjectModelClass;
import io.rxmicro.annotation.processor.common.model.type.PrimitiveModelClass;
import io.rxmicro.model.MappingStrategy;

import javax.lang.model.element.Element;
import javax.lang.model.element.ModuleElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

import static io.rxmicro.annotation.processor.common.SupportedOptions.RX_MICRO_MAX_JSON_NESTED_DEPTH;
import static io.rxmicro.annotation.processor.common.SupportedOptions.RX_MICRO_MAX_JSON_NESTED_DEPTH_OPTION_DEFAULT_VALUE;
import static io.rxmicro.annotation.processor.common.util.Elements.allModelFields;
import static io.rxmicro.annotation.processor.common.util.Elements.asEnumElement;
import static io.rxmicro.annotation.processor.common.util.Elements.findGetters;
import static io.rxmicro.annotation.processor.common.util.Elements.findSetters;
import static io.rxmicro.annotation.processor.common.util.Names.getPackageName;
import static io.rxmicro.annotation.processor.common.util.TypeValidators.validateAndGetModelType;
import static io.rxmicro.annotation.processor.common.util.TypeValidators.validateGenericType;
import static io.rxmicro.common.util.ExCollectors.toOrderedMap;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Strings.splitByCamelCase;
import static java.util.Collections.unmodifiableMap;
import static javax.lang.model.type.TypeKind.BOOLEAN;
import static javax.lang.model.type.TypeKind.BYTE;
import static javax.lang.model.type.TypeKind.CHAR;
import static javax.lang.model.type.TypeKind.DOUBLE;
import static javax.lang.model.type.TypeKind.FLOAT;
import static javax.lang.model.type.TypeKind.INT;
import static javax.lang.model.type.TypeKind.LONG;
import static javax.lang.model.type.TypeKind.SHORT;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public abstract class AbstractModelFieldBuilder<MF extends ModelField, MC extends ObjectModelClass<MF>>
        extends AbstractProcessorComponent implements ModelFieldBuilder<MF, MC> {

    private final Map<TypeKind, Class<?>> JAVA_PRIMITIVE_REPLACEMENT = Map.of(
            BOOLEAN, Boolean.class,
            BYTE, Byte.class,
            SHORT, Short.class,
            INT, Integer.class,
            LONG, Long.class,
            CHAR, Character.class,
            FLOAT, Float.class,
            DOUBLE, Double.class
    );

    private int maxNestedLevel = -1;

    protected abstract SupportedTypesProvider supportedTypesProvider();

    @Override
    @SuppressWarnings("unchecked")
    public final Map<TypeElement, MC> build(final ModelFieldType modelFieldType,
                                            final ModuleElement currentModule,
                                            final Set<TypeElement> modelClasses,
                                            final boolean requireDefConstructor) {
        final Map<TypeElement, MC> result = new LinkedHashMap<>();
        for (final TypeElement typeElement : modelClasses) {
            validateModelClass(typeElement);
            final ModelClass modelClass = extract(
                    currentModule, modelFieldType, typeElement, typeElement.asType(), 0, requireDefConstructor
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

    protected void validateModelClass(final TypeElement typeElement) {

    }

    protected abstract PrimitiveModelClass createPrimitiveModelClass(TypeMirror type);

    protected abstract MC createObjectModelClass(ModuleElement currentModule,
                                                 ModelFieldType modelFieldType,
                                                 TypeMirror type,
                                                 TypeElement typeElement,
                                                 int nestedLevel,
                                                 boolean requireDefConstructor);

    protected Map<MF, ModelClass> getFieldMap(final ModuleElement currentModule,
                                              final ModelFieldType modelFieldType,
                                              final TypeElement typeElement,
                                              final int nestedLevel,
                                              final boolean requireDefConstructor) {
        final ModelNames modelNames = new ModelNames();
        final Set<String> fieldNames = new HashSet<>();
        return allModelFields(typeElement).stream()
                .collect(toOrderedMap(
                        el -> build(modelFieldType, el, typeElement, modelNames, fieldNames, nestedLevel),
                        el -> extract(currentModule, modelFieldType, el, el.asType(), nestedLevel, requireDefConstructor)
                ));
    }

    protected abstract MF build(ModelFieldType modelFieldType,
                                VariableElement field,
                                TypeElement typeElement,
                                ModelNames modelNames,
                                Set<String> fieldNames,
                                int nestedLevel);

    protected final boolean isModelPrimitive(final TypeMirror typeMirror) {
        return supportedTypesProvider().primitives().contains(typeMirror);
    }

    protected final boolean isModelInternalType(final Element owner) {
        return supportedTypesProvider().internalTypes().contains(owner);
    }

    protected final boolean isModelPrimitiveList(final TypeMirror typeMirror) {
        return supportedTypesProvider().primitiveContainers().contains(typeMirror);
    }

    protected final <M extends ModelField> M validate(final M modelField,
                                                      final TypeElement typeElement) {
        if (modelField.getModelReadAccessorType() == ModelAccessorType.REFLECTION) {
            warn(modelField.getFieldElement(),
                    "PERFORMANCE WARNING: To read a value from ?.? rxmicro will use the reflection. " +
                            "It is recommended to add a getter or change the field modifier: from private to default, protected or public",
                    typeElement.getQualifiedName(), modelField.getFieldName());
        }
        if (modelField.getModelWriteAccessorType() == ModelAccessorType.REFLECTION) {
            warn(modelField.getFieldElement(),
                    "PERFORMANCE WARNING: To write a value to ?.? rxmicro will use the reflection. " +
                            "It is recommended to add a setter or change the field modifier: from private to default, protected or public",
                    typeElement.getQualifiedName(), modelField.getFieldName());
        }
        return modelField;
    }

    protected final AnnotatedModelElement build(final TypeElement typeElement,
                                                final VariableElement variableElement) {
        return new AnnotatedModelElement(
                getPackageName(typeElement),
                variableElement,
                findGetters(typeElement, variableElement),
                findSetters(typeElement, variableElement).stream().findFirst().orElse(null));
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
                               final boolean requireDefConstructor) {
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
        return extractModelClasses(currentModule, modelFieldType, owner, type, nestedLevel, requireDefConstructor);
    }

    private ModelClass extractModelClasses(final ModuleElement currentModule,
                                           final ModelFieldType modelFieldType,
                                           final Element owner,
                                           final TypeMirror type,
                                           final int nestedLevel,
                                           final boolean requireDefConstructor) {
        if (isModelInternalType(owner)) {
            return InternalModelClass.create();
        } else if (isModelPrimitive(type)) {
            return asEnumElement(type)
                    .map(e -> (ModelClass) new EnumModelClass(type))
                    .orElseGet(() -> createPrimitiveModelClass(type));
        } else if (supportedTypesProvider().collectionContainers().contains(type)) {
            validateGenericType(owner, type, "Invalid container");
            return buildListModelClass(
                    currentModule, modelFieldType, owner, (DeclaredType) type, nestedLevel + 1, requireDefConstructor
            );
        } else {
            supportedTypesProvider().getReplacePrimitiveSuggestions(type.toString()).ifPresent(replace -> {
                throw new InterruptProcessingException(
                        owner,
                        "Use '?' type instead of '?' one!",
                        replace.getName(),
                        type.toString()
                );
            });
            final TypeElement typeElement =
                    validateAndGetModelType(currentModule, owner, type, "Invalid model class", requireDefConstructor);
            return createObjectModelClass(
                    currentModule,
                    modelFieldType,
                    type,
                    typeElement,
                    nestedLevel + 1,
                    requireDefConstructor);
        }
    }

    private ModelClass buildListModelClass(final ModuleElement currentModule,
                                           final ModelFieldType modelFieldType,
                                           final Element owner,
                                           final DeclaredType type,
                                           final int nestedLevel,
                                           final boolean requireDefConstructor) {
        final TypeMirror itemType = type.getTypeArguments().get(0);
        if (isModelPrimitive(itemType)) {
            return asEnumElement(itemType)
                    .map(e -> new ListModelClass(new EnumModelClass(itemType)))
                    .orElseGet(() -> new ListModelClass(createPrimitiveModelClass(itemType)));
        } else {
            final ModelClass elementModelClass = extract(
                    currentModule, modelFieldType, owner, itemType, nestedLevel + 1, requireDefConstructor
            );
            if (elementModelClass.isList()) {
                throw new InterruptProcessingException(owner, "Multi array does not supported yet");
            }
            return new ListModelClass(elementModelClass);
        }
    }

    private int getMaxNestedLevel() {
        if (maxNestedLevel == -1) {
            maxNestedLevel = getIntOption(
                    RX_MICRO_MAX_JSON_NESTED_DEPTH,
                    RX_MICRO_MAX_JSON_NESTED_DEPTH_OPTION_DEFAULT_VALUE
            );
        }
        return maxNestedLevel;
    }

    /**
     * @author nedis
     * @link http://rxmicro.io
     * @since 0.1
     */
    public static final class ModelNames {

        private static final Function<String, Set<String>> NEW_SET_CREATOR =
                n -> new HashSet<>();

        private final Map<String, Set<String>> models = new HashMap<>();

        public final Set<String> modelNames(final String name) {
            return models.computeIfAbsent(name, NEW_SET_CREATOR);
        }

    }
}
