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

package io.rxmicro.annotation.processor.rest.model;

import io.rxmicro.annotation.processor.common.model.ModelField;
import io.rxmicro.annotation.processor.common.model.type.ModelClass;
import io.rxmicro.annotation.processor.common.model.type.ObjectModelClass;
import io.rxmicro.annotation.processor.common.util.UsedByFreemarker;
import io.rxmicro.exchange.json.detail.ModelFromJsonConverter;
import io.rxmicro.exchange.json.detail.ModelToJsonConverter;
import io.rxmicro.validation.ConstraintValidator;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

import static io.rxmicro.annotation.processor.common.model.ModelAccessorType.REFLECTION;
import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getModelTransformerFullClassName;
import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getModelTransformerInstanceName;
import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getModelTransformerSimpleClassName;
import static io.rxmicro.common.util.ExCollections.join;
import static io.rxmicro.common.util.ExCollectors.toUnmodifiableOrderedMap;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

/**
 * @author nedis
 * @since 0.1
 */
public abstract class RestObjectModelClass extends ObjectModelClass<RestModelField> {

    /**
     * Declared fields must be ordered: fields that declared at super class must be at the beginning of collection
     *
     * @see io.rxmicro.annotation.processor.common.util.Elements#allFields(TypeElement, boolean, Predicate)
     */
    private final Map<RestModelField, ModelClass> allFieldsInDeclaredOrder;

    protected final Map<RestModelField, ModelClass> pathVariables;

    protected final Map<RestModelField, ModelClass> headers;

    protected final Map<RestModelField, ModelClass> internals;

    protected RestObjectModelClass(final TypeMirror modelTypeMirror,
                                   final TypeElement modelTypeElement,
                                   final Map<RestModelField, ModelClass> fields,
                                   final ObjectModelClass<RestModelField> parent,
                                   final boolean modelClassReturnedByRestMethod) {
        super(
                modelTypeMirror,
                modelTypeElement,
                fields.entrySet().stream()
                        .filter(e -> e.getKey().isHttpParameter())
                        .collect(toUnmodifiableOrderedMap(Map.Entry::getKey, Map.Entry::getValue)),
                parent,
                modelClassReturnedByRestMethod
        );
        // Sorting is not necessary, because fields are returned by
        // {@link io.rxmicro.annotation.processor.common.util.Elements#allFields(TypeElement, boolean, Predicate)}
        this.allFieldsInDeclaredOrder = fields;
        this.pathVariables = fields.entrySet().stream()
                .filter(e -> e.getKey().isHttpPathVariable())
                .collect(toUnmodifiableOrderedMap(Map.Entry::getKey, Map.Entry::getValue));
        this.headers = fields.entrySet().stream()
                .filter(e -> e.getKey().isHttpHeader())
                .collect(toUnmodifiableOrderedMap(Map.Entry::getKey, Map.Entry::getValue));
        this.internals = fields.entrySet().stream()
                .filter(e -> e.getKey().isInternalType())
                .collect(toUnmodifiableOrderedMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public Optional<RestObjectModelClass> getParent() {
        return super.getParent().map(o -> (RestObjectModelClass)o);
    }

    @Override
    public boolean isReadReflectionRequired() {
        final Predicate<RestModelField> predicate = m ->
                m.getModelReadAccessorType() == REFLECTION;
        return super.isReadReflectionRequired() ||
                pathVariables.keySet().stream().anyMatch(predicate) ||
                headers.keySet().stream().anyMatch(predicate) ||
                internals.keySet().stream().anyMatch(predicate);
    }

    @Override
    public boolean isWriteReflectionRequired() {
        final Predicate<RestModelField> predicate = m ->
                m.getModelWriteAccessorType() == REFLECTION;
        return super.isWriteReflectionRequired() ||
                pathVariables.keySet().stream().anyMatch(predicate) ||
                headers.keySet().stream().anyMatch(predicate) ||
                internals.keySet().stream().anyMatch(predicate);
    }

    @Override
    public List<TypeMirror> getModelFieldTypes() {
        return join(
                super.getModelFieldTypes(),
                pathVariables.keySet().stream().map(ModelField::getFieldClass).collect(toList()),
                headers.keySet().stream().map(ModelField::getFieldClass).collect(toList()),
                internals.keySet().stream().map(ModelField::getFieldClass).collect(toList())
        );
    }

    @UsedByFreemarker(
            "$$RestJsonModelWriterTemplate.javaftl"
    )
    public boolean isHeaderReadReflectionRequired() {
        return headers.keySet().stream().anyMatch(m ->
                m.getModelReadAccessorType() == REFLECTION);
    }

    public boolean isInternalsReadReflectionRequired() {
        return internals.keySet().stream().anyMatch(m ->
                m.getModelReadAccessorType() == REFLECTION);
    }

    public boolean isPathVariablesReadReflectionRequired() {
        return pathVariables.keySet().stream().anyMatch(m ->
                m.getModelReadAccessorType() == REFLECTION);
    }

    public boolean isParamsWriteReflectionRequired() {
        return getParamEntries().stream().anyMatch(m -> m.getKey().getModelWriteAccessorType() == REFLECTION);
    }

    public boolean isParamsReadReflectionRequired() {
        return getParamEntries().stream().anyMatch(m -> m.getKey().getModelReadAccessorType() == REFLECTION);
    }

    public boolean isInternalsPresent() {
        return !internals.isEmpty();
    }

    @UsedByFreemarker
    public Collection<RestModelField> getInternals() {
        return internals.keySet();
    }

    public boolean isHeadersPresent() {
        return !getHeaderEntries().isEmpty();
    }

    public boolean isPathVariablesPresent() {
        return !pathVariables.isEmpty();
    }

    public boolean isHeadersOrPathVariablesOrInternalsPresent(){
        return isHeadersPresent() || isPathVariablesPresent() || isInternalsPresent();
    }

    @UsedByFreemarker
    public Set<Map.Entry<RestModelField, ModelClass>> getHeaderEntries() {
        return headers.entrySet();
    }

    public Set<Map.Entry<RestModelField, ModelClass>> getPathVariableEntries() {
        return pathVariables.entrySet();
    }

    public Map<String, RestModelField> getPathVariableMap() {
        return pathVariables.keySet().stream()
                .collect(toMap(ModelField::getModelName, identity()));
    }

    @UsedByFreemarker({
            "$$RestJsonModelReaderTemplate.javaftl",
            "$$RestModelFromJsonConverterTemplate.javaftl"
    })
    public String getFromJsonConverterInstanceName() {
        return getModelTransformerInstanceName(getJavaSimpleClassName(), ModelFromJsonConverter.class);
    }

    @UsedByFreemarker({
            "$$RestJsonModelReaderTemplate.javaftl",
            "$$RestModelFromJsonConverterTemplate.javaftl"
    })
    public String getModelFromJsonConverterImplSimpleClassName() {
        return getModelTransformerSimpleClassName(getModelTypeElement(), ModelFromJsonConverter.class);
    }

    public String getModelFromJsonConverterImplFullClassName() {
        return getModelTransformerFullClassName(getModelTypeElement(), ModelFromJsonConverter.class);
    }

    @UsedByFreemarker({
            "$$RestJsonModelWriterTemplate.javaftl",
            "$$RestModelToJsonConverterTemplate.javaftl"
    })
    public String getToJsonConverterInstanceName() {
        return getModelTransformerInstanceName(getJavaSimpleClassName(), ModelToJsonConverter.class);
    }

    @UsedByFreemarker({
            "$$RestJsonModelWriterTemplate.javaftl",
            "$$RestModelToJsonConverterTemplate.javaftl"
    })
    public String getModelToJsonConverterImplSimpleClassName() {
        return getModelTransformerSimpleClassName(getModelTypeElement(), ModelToJsonConverter.class);
    }

    public String getModelToJsonConverterImplFullClassName() {
        return getModelTransformerFullClassName(getModelTypeElement(), ModelToJsonConverter.class);
    }

    @UsedByFreemarker(
            "$$RestModelValidatorTemplate.javaftl"
    )
    public String getModelValidatorImplSimpleClassName() {
        return getModelTransformerSimpleClassName(getModelTypeElement(), ConstraintValidator.class);
    }

    @UsedByFreemarker(
            "$$RestModelValidatorTemplate.javaftl"
    )
    public String getModelValidatorInstanceName() {
        return getModelTransformerInstanceName(getJavaSimpleClassName(), ConstraintValidator.class);
    }

    /**
     * Declared fields must be ordered: fields that declared at super class must be at the beginning of collection
     *
     * @see io.rxmicro.annotation.processor.common.util.Elements#allFields(TypeElement, boolean, Predicate)
     */
    @Override
    public Collection<Map.Entry<RestModelField, ModelClass>> getAllOrderedDeclaredFields() {
        return allFieldsInDeclaredOrder.entrySet();
    }
}
