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

import io.rxmicro.annotation.processor.common.component.ModelClassHierarchyBuilder;
import io.rxmicro.annotation.processor.common.model.ModelField;
import io.rxmicro.annotation.processor.common.model.type.ModelClass;
import io.rxmicro.annotation.processor.common.model.type.ObjectModelClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

import static io.rxmicro.annotation.processor.common.util.Elements.allSuperTypes;
import static io.rxmicro.annotation.processor.common.util.LoggerMessages.getLoggableParentChildRelationFragment;
import static io.rxmicro.common.util.ExCollections.unmodifiableList;
import static io.rxmicro.common.util.Requires.require;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

/**
 * @author nedis
 * @since 0.7.2
 */
public abstract class AbstractModelClassHierarchyBuilder<MF extends ModelField, MC extends ObjectModelClass<MF>>
        extends AbstractProcessorComponent
        implements ModelClassHierarchyBuilder<MF, MC> {

    @Override
    public Optional<List<MC>> build(final MC returnedByRestMethodModelClass,
                                    final Set<String> returnedByRestMethodModelClassNames) {
        final Map<String, List<Map.Entry<MF, ModelClass>>> map = new LinkedHashMap<>();
        final Map<String, TypeElement> typeElementMap = new HashMap<>();
        String currentClass = returnedByRestMethodModelClass.getJavaFullClassName();
        typeElementMap.put(currentClass, returnedByRestMethodModelClass.getModelTypeElement());
        for (final Map.Entry<MF, ModelClass> field : returnedByRestMethodModelClass.getAllOrderedDeclaredFields()) {
            final TypeElement declaredClass = (TypeElement) field.getKey().getEnclosingElement();
            if (!declaredClass.getQualifiedName().toString().equals(currentClass)) {
                currentClass = declaredClass.getQualifiedName().toString();
                typeElementMap.put(currentClass, declaredClass);
            }
            map.computeIfAbsent(currentClass, c -> new ArrayList<>()).add(field);
        }
        // If model class does not contain any fields (i.e. all fields are defined at super class(es))
        // then add empty model class to class hierarchy
        if (!map.containsKey(returnedByRestMethodModelClass.getJavaFullClassName())) {
            map.put(returnedByRestMethodModelClass.getJavaFullClassName(), List.of());
        }
        if (map.size() == 1) {
            return Optional.empty();
        } else {
            final List<MC> list = build(returnedByRestMethodModelClassNames, map, typeElementMap);
            debug(
                    "Resolved the following class hierarchy for '?' model class:\n?",
                    returnedByRestMethodModelClass::getJavaSimpleClassName,
                    () -> classHierarchyToString(list)
            );
            return Optional.of(list);
        }
    }

    private List<MC> build(final Set<String> notAbstractModelClassNames,
                           final Map<String, List<Map.Entry<MF, ModelClass>>> map,
                           final Map<String, TypeElement> typeElementMap) {
        final Map<String, MC> modelClassMap = new LinkedHashMap<>();
        for (final Map.Entry<String, List<Map.Entry<MF, ModelClass>>> entry : map.entrySet()) {
            final TypeElement modelTypeElement = require(typeElementMap.get(entry.getKey()));
            final boolean modelClassReturnedByRestMethod = notAbstractModelClassNames.contains(entry.getKey());
            final Map<MF, ModelClass> params = entry.getValue().stream().collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
            final MC parentModelClass = getNullableParentModelClass(modelTypeElement, modelClassMap);
            if (parentModelClass != null) {
                validateParentModelClass(parentModelClass);
            }
            final MC modelClass = createObjectModelClass(
                    modelTypeElement.asType(), modelTypeElement, params, parentModelClass, modelClassReturnedByRestMethod
            );
            modelClassMap.put(modelClass.getJavaFullClassName(), modelClass);
        }
        return unmodifiableList(modelClassMap.values());
    }

    private MC getNullableParentModelClass(final TypeElement modelTypeElement,
                                           final Map<String, MC> modelClassMap) {
        for (final String superType :
                allSuperTypes(modelTypeElement).stream().map(t -> t.getQualifiedName().toString()).collect(toList())) {
            final MC mc = modelClassMap.get(superType);
            if (mc != null) {
                return mc;
            }
        }
        return null;
    }

    protected abstract MC createObjectModelClass(TypeMirror modelTypeMirror,
                                                 TypeElement modelTypeElement,
                                                 Map<MF, ModelClass> params,
                                                 MC parent,
                                                 boolean modelClassReturnedByRestMethod);

    protected abstract void validateParentModelClass(MC parentModelClass);

    private String classHierarchyToString(final List<MC> list) {
        final StringBuilder stringBuilder = new StringBuilder();
        final ListIterator<MC> iterator = list.listIterator();
        int index = 0;
        while (iterator.hasNext()) {
            final MC parent = iterator.next();
            if (iterator.hasNext()) {
                final MC child = iterator.next();
                stringBuilder.append(getLoggableParentChildRelationFragment(index, index == 0, parent, child)).append('\n');
                iterator.previous();
                index++;
            } else {
                break;
            }
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }
}
