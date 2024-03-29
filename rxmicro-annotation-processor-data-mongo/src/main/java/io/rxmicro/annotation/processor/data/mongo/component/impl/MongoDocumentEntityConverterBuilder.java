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

package io.rxmicro.annotation.processor.data.mongo.component.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.component.impl.BaseProcessorComponent;
import io.rxmicro.annotation.processor.common.model.ClassStructure;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.common.model.definition.SupportedTypesProvider;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.common.model.type.ModelClass;
import io.rxmicro.annotation.processor.data.component.EntityConverterBuilder;
import io.rxmicro.annotation.processor.data.model.DataGenerationContext;
import io.rxmicro.annotation.processor.data.model.DataModelField;
import io.rxmicro.annotation.processor.data.mongo.model.EntityFromDBConverterClassStructure;
import io.rxmicro.annotation.processor.data.mongo.model.EntityToDBConverterClassStructure;
import io.rxmicro.annotation.processor.data.mongo.model.MongoDataModelField;
import io.rxmicro.annotation.processor.data.mongo.model.MongoDataObjectModelClass;
import io.rxmicro.data.mongo.DocumentId;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import javax.lang.model.element.ModuleElement;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class MongoDocumentEntityConverterBuilder extends BaseProcessorComponent
        implements EntityConverterBuilder<MongoDataModelField, MongoDataObjectModelClass> {

    @Inject
    private SupportedTypesProvider supportedTypesProvider;

    @Override
    public Set<? extends ClassStructure> build(
            final EnvironmentContext environmentContext,
            final DataGenerationContext<MongoDataModelField, MongoDataObjectModelClass> dataGenerationContext) {
        final ModuleElement moduleElement = environmentContext.getCurrentModule();
        final Set<ClassStructure> result = new HashSet<>();
        dataGenerationContext.getEntityParamMap().values().stream()
                .flatMap(m -> Stream.concat(
                        Stream.of(m),
                        m.getAllChildrenObjectModelClasses().stream())
                )
                .map(m -> m.asObject(MongoDataObjectModelClass.class))
                .collect(toSet())
                .forEach(m -> result.add(createEntityToDBConverterClassStructure(moduleElement, m)));

        dataGenerationContext.getEntityReturnMap().values().stream()
                .flatMap(m -> Stream.concat(
                        Stream.of(m),
                        m.getAllChildrenObjectModelClasses().stream())
                )
                .map(m -> m.asObject(MongoDataObjectModelClass.class))
                .collect(toSet())
                .forEach(m -> result.add(new EntityFromDBConverterClassStructure(moduleElement, m)));
        return result;
    }

    private EntityToDBConverterClassStructure createEntityToDBConverterClassStructure(final ModuleElement moduleElement,
                                                                                      final MongoDataObjectModelClass modelClass) {
        return getIdField(modelClass)
                .map(id -> {
                    validateIdFieldType(id.getKey());
                    return new EntityToDBConverterClassStructure(moduleElement, id, modelClass);
                })
                .orElse(new EntityToDBConverterClassStructure(moduleElement, modelClass));
    }

    private void validateIdFieldType(final DataModelField id) {
        if (!supportedTypesProvider.isModelPrimitive(id.getFieldClass())) {
            throw new InterruptProcessingException(id.getFieldElement(), "Document id must be a primitive type");
        }
    }

    private Optional<Map.Entry<MongoDataModelField, ModelClass>> getIdField(final MongoDataObjectModelClass modelClass) {
        final List<Map.Entry<MongoDataModelField, ModelClass>> ids = modelClass.getParamEntries().stream()
                .filter(e -> e.getKey().getAnnotation(DocumentId.class) != null)
                .collect(toList());
        if (ids.size() > 1) {
            throw new InterruptProcessingException(
                    ids.get(ids.size() - 1).getKey().getFieldElement(),
                    "Expected one field annotated by '?' per document", DocumentId.class.getName());
        }
        return ids.isEmpty() ? Optional.empty() : Optional.of(ids.get(0));
    }
}
