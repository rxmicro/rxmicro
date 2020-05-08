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

package io.rxmicro.annotation.processor.data.model;

import javax.lang.model.element.ModuleElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static io.rxmicro.annotation.processor.common.util.Elements.asTypeElement;
import static io.rxmicro.common.util.ExCollections.unmodifiableMap;
import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @since 0.1
 */
public final class DataGenerationContext<DMF extends DataModelField, DMC extends DataObjectModelClass<DMF>> {

    private final ModuleElement currentModule;

    private final Map<TypeElement, DMC> entityParamMap;

    private final Map<TypeElement, DMC> entityReturnMap;

    public DataGenerationContext(final ModuleElement currentModule,
                                 final Map<TypeElement, DMC> entityParamMap,
                                 final Map<TypeElement, DMC> entityReturnMap) {
        this.currentModule = require(currentModule);
        this.entityParamMap = unmodifiableMap(entityParamMap);
        this.entityReturnMap = unmodifiableMap(entityReturnMap);
    }

    public ModuleElement getCurrentModule() {
        return currentModule;
    }

    public Map<TypeElement, DMC> getEntityParamMap() {
        return entityParamMap;
    }

    public Map<TypeElement, DMC> getEntityReturnMap() {
        return entityReturnMap;
    }

    public Set<String> getEntityResultTypes() {
        return entityReturnMap.keySet().stream().map(t -> t.asType().toString()).collect(Collectors.toSet());
    }

    public Optional<DMC> getEntityParamModel(final TypeMirror type) {
        return asTypeElement(type)
                .flatMap(e -> Optional.ofNullable(entityParamMap.get(e)));
    }

    public boolean isEntityParamType(final TypeMirror type) {
        return asTypeElement(type)
                .map(entityParamMap::containsKey)
                .orElse(false);
    }

    public boolean isEntityResultType(final TypeMirror type) {
        return asTypeElement(type)
                .map(entityReturnMap::containsKey)
                .orElse(false);
    }
}
