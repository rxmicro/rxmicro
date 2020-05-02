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

package io.rxmicro.annotation.processor.common.model.definition;

import io.rxmicro.annotation.processor.common.model.definition.impl.ByNameTypeDefinition;
import io.rxmicro.annotation.processor.common.model.definition.impl.ContainerTypeDefinition;
import io.rxmicro.annotation.processor.common.model.definition.impl.TypeDefinitionsImpl;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static java.util.stream.Collectors.toMap;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public abstract class SupportedTypesProvider {

    private TypeDefinitions<TypeDefinition> primitiveTypes;

    private TypeDefinitions<ContainerTypeDefinition> collectionContainerTypes;

    private TypeDefinitions<ContainerTypeDefinition> primitiveContainerTypes;

    private TypeDefinitions<TypeDefinition> reactiveReturnTypes;

    private TypeDefinitions<TypeDefinition> resultReturnPrimitiveTypes;

    private TypeDefinitions<TypeDefinition> notEntityMethodParameterTypes;

    private TypeDefinitions<TypeDefinition> standardMethodParameterTypes;

    private TypeDefinitions<TypeDefinition> internalTypes;

    private Map<String, Class<?>> replacePrimitiveSuggestions;

    public final Optional<Class<?>> getReplacePrimitiveSuggestions(final String currentClass) {
        if (replacePrimitiveSuggestions == null) {
            replacePrimitiveSuggestions = createReplacePrimitiveSuggestions().stream()
                    .collect(toMap(e -> e.getKey().getName(), Map.Entry::getValue));
        }
        return Optional.ofNullable(replacePrimitiveSuggestions.get(currentClass));
    }

    public final TypeDefinitions<TypeDefinition> getPrimitives() {
        if (primitiveTypes == null) {
            primitiveTypes = createPrimitives();
        }
        return primitiveTypes;
    }

    public final TypeDefinitions<ContainerTypeDefinition> getCollectionContainers() {
        if (collectionContainerTypes == null) {
            collectionContainerTypes = createCollectionContainers();
        }
        return collectionContainerTypes;
    }

    public final TypeDefinitions<ContainerTypeDefinition> getPrimitiveContainers() {
        if (primitiveContainerTypes == null) {
            primitiveContainerTypes = createPrimitiveCollectionContainers();
        }
        return primitiveContainerTypes;
    }

    public final TypeDefinitions<TypeDefinition> getReactiveReturnTypes() {
        if (reactiveReturnTypes == null) {
            reactiveReturnTypes = createReactiveReturnTypes();
        }
        return reactiveReturnTypes;
    }

    public final TypeDefinitions<TypeDefinition> getResultReturnPrimitiveTypes() {
        if (resultReturnPrimitiveTypes == null) {
            resultReturnPrimitiveTypes = createResultReturnPrimitiveTypes();
        }
        return resultReturnPrimitiveTypes;
    }

    public final TypeDefinitions<TypeDefinition> getNotEntityMethodParameters() {
        if (notEntityMethodParameterTypes == null) {
            notEntityMethodParameterTypes = createNotEntityMethodParameters();
        }
        return notEntityMethodParameterTypes;
    }

    public final TypeDefinitions<TypeDefinition> getStandardMethodParameters() {
        if (standardMethodParameterTypes == null) {
            standardMethodParameterTypes = createStandardMethodParameterTypes();
        }
        return standardMethodParameterTypes;
    }

    public final TypeDefinitions<TypeDefinition> getInternalTypes() {
        if (internalTypes == null) {
            internalTypes = createInternalTypes();
        }
        return internalTypes;
    }

    protected abstract TypeDefinitions<TypeDefinition> createPrimitives();

    protected TypeDefinitions<ContainerTypeDefinition> createPrimitiveCollectionContainers() {
        final List<ContainerTypeDefinition> definitions = new ArrayList<>();
        for (final ContainerTypeDefinition typeDefinition : getCollectionContainers().typeDefinitions()) {
            final TypeDefinition container = typeDefinition.getContainerTypeDefinition();
            for (final TypeDefinition primitiveDefinition : getPrimitives().typeDefinitions()) {
                definitions.add(new ContainerTypeDefinition(container, primitiveDefinition));
            }
        }
        return new TypeDefinitionsImpl<>(definitions.toArray(new ContainerTypeDefinition[0]));
    }

    protected TypeDefinitions<ContainerTypeDefinition> createCollectionContainers() {
        return new TypeDefinitionsImpl<>(
                new ContainerTypeDefinition(List.class)
        );
    }

    protected TypeDefinitions<TypeDefinition> createReactiveReturnTypes() {
        return new TypeDefinitionsImpl<>(
                new ByNameTypeDefinition(CompletionStage.class),
                new ByNameTypeDefinition(CompletableFuture.class),
                new ByNameTypeDefinition(Mono.class)
        );
    }

    protected TypeDefinitions<TypeDefinition> createResultReturnPrimitiveTypes() {
        return new TypeDefinitionsImpl<>(List.of(
                getPrimitives(),
                new TypeDefinitionsImpl<>(
                        getPrimitiveContainers().typeDefinitions()
                                .stream()
                                .map(c -> (TypeDefinition) c)
                                .toArray(TypeDefinition[]::new))
        ));
    }

    protected TypeDefinitions<TypeDefinition> createNotEntityMethodParameters() {
        return new TypeDefinitionsImpl<>(List.of(
                getPrimitives(),
                getStandardMethodParameters(),
                new TypeDefinitionsImpl<>(
                        getPrimitiveContainers().typeDefinitions()
                                .stream()
                                .map(c -> (TypeDefinition) c)
                                .toArray(TypeDefinition[]::new))
        ));
    }

    protected TypeDefinitions<TypeDefinition> createStandardMethodParameterTypes() {
        return new TypeDefinitionsImpl<>();
    }

    protected TypeDefinitions<TypeDefinition> createInternalTypes() {
        return new TypeDefinitionsImpl<>();
    }

    protected List<Map.Entry<Class<?>, Class<?>>> createReplacePrimitiveSuggestions() {
        return List.of();
    }
}
