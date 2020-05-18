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

package io.rxmicro.annotation.processor.common.model.type;

import io.rxmicro.annotation.processor.common.model.ModelField;
import io.rxmicro.annotation.processor.common.util.UsedByFreemarker;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

import static io.rxmicro.annotation.processor.common.model.ModelAccessorType.REFLECTION;
import static io.rxmicro.annotation.processor.common.util.Names.getSimpleName;
import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @since 0.1
 */
public abstract class ObjectModelClass<T extends ModelField> extends ModelClass {

    private final TypeMirror modelTypeMirror;

    private final TypeElement modelTypeElement;

    private final Map<T, ModelClass> params;

    public ObjectModelClass(final TypeMirror modelTypeMirror,
                            final TypeElement modelTypeElement,
                            final Map<T, ModelClass> params) {
        this.modelTypeMirror = require(modelTypeMirror);
        this.modelTypeElement = require(modelTypeElement);
        this.params = require(params);
    }

    public TypeMirror getModelTypeMirror() {
        return modelTypeMirror;
    }

    public TypeElement getModelTypeElement() {
        return modelTypeElement;
    }

    @Override
    @UsedByFreemarker
    public String getJavaSimpleClassName() {
        return getSimpleName(modelTypeMirror);
    }

    @Override
    @UsedByFreemarker
    public String getJavaFullClassName() {
        return modelTypeMirror.toString();
    }

    @UsedByFreemarker
    public boolean isReadReflectionRequired() {
        return params.keySet().stream().anyMatch(m -> m.getModelReadAccessorType() == REFLECTION);
    }

    @UsedByFreemarker
    public boolean isWriteReflectionRequired() {
        return params.keySet().stream().anyMatch(m -> m.getModelWriteAccessorType() == REFLECTION);
    }

    @UsedByFreemarker
    public Set<Map.Entry<T, ModelClass>> getParamEntries() {
        return params.entrySet();
    }

    @UsedByFreemarker
    public boolean isParamEntriesPresent() {
        return !params.isEmpty();
    }

    public List<TypeMirror> getModelFieldTypes() {
        return params.keySet().stream().map(ModelField::getFieldClass).collect(Collectors.toList());
    }

    public boolean isParamsPresent() {
        return !getParamEntries().isEmpty();
    }

    public Set<ObjectModelClass<T>> getAllChildrenObjectModelClasses() {
        final Set<ObjectModelClass<T>> result = new HashSet<>();
        for (final ModelClass modelClass : params.values()) {
            if (modelClass.isObject()) {
                final ObjectModelClass<T> objectModelClass = modelClass.asObject();
                result.add(objectModelClass);
                result.addAll(objectModelClass.getAllChildrenObjectModelClasses());
            } else if (modelClass.isList() && modelClass.asList().isObjectList()) {
                final ObjectModelClass<T> objectModelClass = modelClass.asList().getElementModelClass().asObject();
                result.add(objectModelClass);
                result.addAll(objectModelClass.getAllChildrenObjectModelClasses());
            }
        }
        return result;
    }
}
