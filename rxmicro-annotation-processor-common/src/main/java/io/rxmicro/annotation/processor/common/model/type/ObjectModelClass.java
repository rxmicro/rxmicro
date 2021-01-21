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

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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

    private final ObjectModelClass<T> parent;

    private final boolean modelClassReturnedByRestMethod;

    protected ObjectModelClass(final TypeMirror modelTypeMirror,
                               final TypeElement modelTypeElement,
                               final Map<T, ModelClass> params,
                               final ObjectModelClass<T> parent,
                               final boolean modelClassReturnedByRestMethod) {
        this.modelTypeMirror = require(modelTypeMirror);
        this.modelTypeElement = require(modelTypeElement);
        this.params = require(params);
        this.parent = parent;
        this.modelClassReturnedByRestMethod = modelClassReturnedByRestMethod;
    }

    public Optional<? extends ObjectModelClass<T>> getParent() {
        return Optional.ofNullable(parent);
    }

    public List<ObjectModelClass<T>> getAllParents() {
        if (parent == null) {
            return List.of();
        } else {
            return Stream.concat(
                    Stream.of(parent),
                    parent.getAllParents().stream()
            ).collect(Collectors.toList());
        }
    }

    @Override
    public String getLoggableFullClassName() {
        if (modelClassReturnedByRestMethod) {
            return super.getLoggableFullClassName();
        } else {
            return "abstract " + super.getLoggableFullClassName();
        }
    }

    public boolean isModelClassReturnedByRestMethod() {
        return modelClassReturnedByRestMethod;
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

    @UsedByFreemarker("$$RestJsonModelWriterTemplate.javaftl")
    public boolean isParamEntriesPresentAtThisOrAnyParent() {
        return isParamEntriesPresent() || (parent != null && parent.isParamEntriesPresentAtThisOrAnyParent());
    }

    public List<TypeMirror> getModelFieldTypes() {
        return params.keySet().stream().map(ModelField::getFieldClass).collect(Collectors.toList());
    }

    public Set<ObjectModelClass<T>> getAllChildrenObjectModelClasses() {
        final Set<ObjectModelClass<T>> result = new HashSet<>();
        for (final ModelClass modelClass : params.values()) {
            if (modelClass.isObject()) {
                final ObjectModelClass<T> objectModelClass = modelClass.asObject();
                result.add(objectModelClass);
                result.addAll(objectModelClass.getAllChildrenObjectModelClasses());
            } else if (modelClass.isIterable() && modelClass.asIterable().isObjectIterable()) {
                final ObjectModelClass<T> objectModelClass = modelClass.asIterable().getElementModelClass().asObject();
                result.add(objectModelClass);
                result.addAll(objectModelClass.getAllChildrenObjectModelClasses());
            }
        }
        return result;
    }

    /**
     * Declared fields must be ordered: fields that declared at super class must be at the beginning of collection
     *
     * @see io.rxmicro.annotation.processor.common.util.Elements#allFields(TypeElement, boolean, Predicate)
     */
    public Collection<Map.Entry<T, ModelClass>> getAllOrderedDeclaredFields() {
        return params.entrySet();
    }

    /**
     * Returns path variable entries from current class and all parents.
     * Parent path variables returned first.
     */
    public final Stream<Map.Entry<T, ModelClass>> getAllDeclaredParametersStream() {
        return Stream.concat(
                getAllParents().stream().flatMap(p -> p.getParamEntries().stream()),
                getParamEntries().stream()
        );
    }
}
