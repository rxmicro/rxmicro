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

package io.rxmicro.annotation.processor.data.sql.component.impl;

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.data.sql.model.TableContext;
import io.rxmicro.data.sql.Schema;
import io.rxmicro.data.sql.Table;

import javax.lang.model.element.TypeElement;
import java.util.List;
import java.util.Optional;

import static io.rxmicro.annotation.processor.common.util.Annotations.defaultAnnotationInstance;
import static io.rxmicro.common.util.Strings.splitByCamelCase;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class TableContextBuilder {

    public TableContext createTableContext(final TypeElement modelTypeElement) {
        final Table table = Optional.ofNullable(modelTypeElement.getAnnotation(Table.class))
                .orElseGet(() -> defaultAnnotationInstance(Table.class));
        final String className = modelTypeElement.getSimpleName().toString();
        final List<String> classNameWords = splitByCamelCase(className);
        final String tableName = Optional.of(table.name()).filter(v -> !v.isEmpty())
                .orElseGet(() -> table.mappingStrategy().getModelName(classNameWords));
        final Optional<String> schemaOptional = getSchema(table.schema(), modelTypeElement);
        if (schemaOptional.isPresent()) {
            if (tableName.contains(".")) {
                throw new InterruptProcessingException(
                        modelTypeElement, "Table name already contains a schema name. Remove the schema definition!"
                );
            }
            return new TableContext(schemaOptional.get(), tableName);
        } else {
            if (tableName.contains(".")) {
                final String[] names = tableName.split("\\.");
                if (names.length != 2) {
                    throw new InterruptProcessingException(
                            modelTypeElement, "Invalid table name: '?'. Set a valid name!", tableName
                    );
                }
                return new TableContext(names[0], names[1]);
            } else {
                return new TableContext(tableName);
            }
        }
    }

    private Optional<String> getSchema(final String tableSchema,
                                       final TypeElement modelTypeElement) {
        final Schema schemaAnnotation = modelTypeElement.getAnnotation(Schema.class);
        if (schemaAnnotation != null) {
            if (schemaAnnotation.value().isEmpty()) {
                throw new InterruptProcessingException(
                        modelTypeElement, "Redundant annotation: '?'. Remote it", Schema.class.getName()
                );
            }
            if (!tableSchema.isEmpty()) {
                throw new InterruptProcessingException(
                        modelTypeElement, "Redundant annotation: '?'. Remote it", Schema.class.getName()
                );
            }
            return Optional.of(schemaAnnotation.value());
        } else {
            return Optional.of(tableSchema).filter(v -> !v.isEmpty());
        }
    }
}
