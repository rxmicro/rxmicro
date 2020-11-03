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
import io.rxmicro.annotation.processor.data.sql.model.DbObjectName;
import io.rxmicro.data.sql.Schema;
import io.rxmicro.data.sql.Table;
import io.rxmicro.data.sql.EnumType;
import io.rxmicro.model.MappingStrategy;

import java.util.List;
import java.util.Optional;
import javax.lang.model.element.TypeElement;

import static io.rxmicro.annotation.processor.common.util.Annotations.defaultAnnotationInstance;
import static io.rxmicro.common.util.Strings.capitalize;
import static io.rxmicro.common.util.Strings.splitByCamelCase;

/**
 * @author nedis
 * @since 0.7
 */
@Singleton
public final class DbObjectNameBuilder {

    public DbObjectName buildTableName(final TypeElement modelTypeElement) {
        final Table table = Optional.ofNullable(modelTypeElement.getAnnotation(Table.class))
                .orElseGet(() -> defaultAnnotationInstance(Table.class));
        return build(modelTypeElement, new NameConfigurator() {
            @Override
            public String schema() {
                return table.schema();
            }

            @Override
            public String name() {
                return table.name();
            }

            @Override
            public MappingStrategy mappingStrategy() {
                return table.mappingStrategy();
            }

            @Override
            public String type() {
                return "table";
            }
        });
    }

    public DbObjectName buildEnumName(final TypeElement modelTypeElement) {
        final EnumType enumType = Optional.ofNullable(modelTypeElement.getAnnotation(EnumType.class))
                .orElseGet(() -> defaultAnnotationInstance(EnumType.class));
        return build(modelTypeElement, new NameConfigurator() {
            @Override
            public String schema() {
                return enumType.schema();
            }

            @Override
            public String name() {
                return enumType.name();
            }

            @Override
            public MappingStrategy mappingStrategy() {
                return enumType.mappingStrategy();
            }

            @Override
            public String type() {
                return "enum";
            }
        });
    }

    private DbObjectName build(final TypeElement modelTypeElement,
                               final NameConfigurator nameConfigurator){
        final String className = modelTypeElement.getSimpleName().toString();
        final List<String> classNameWords = splitByCamelCase(className);
        final String dbObjectName = Optional.of(nameConfigurator.name()).filter(v -> !v.isEmpty())
                .orElseGet(() -> nameConfigurator.mappingStrategy().getModelName(classNameWords));
        final Optional<String> schemaOptional = getSchema(nameConfigurator.schema(), modelTypeElement);
        if (schemaOptional.isPresent()) {
            if (dbObjectName.contains(".")) {
                throw new InterruptProcessingException(
                        modelTypeElement,
                        "? name already contains a schema name. Remove the schema definition!",
                        capitalize(nameConfigurator.type())
                );
            }
            return new DbObjectName(schemaOptional.get(), dbObjectName);
        } else {
            if (dbObjectName.contains(".")) {
                final String[] names = dbObjectName.split("\\.");
                if (names.length != 2) {
                    throw new InterruptProcessingException(
                            modelTypeElement,
                            "Invalid ? name: '?'. Set a valid name!",
                            nameConfigurator.type(),
                            dbObjectName
                    );
                }
                return new DbObjectName(names[0], names[1]);
            } else {
                return new DbObjectName(dbObjectName);
            }
        }
    }

    private Optional<String> getSchema(final String dbObjectSchema,
                                       final TypeElement modelTypeElement) {
        final Schema schemaAnnotation = modelTypeElement.getAnnotation(Schema.class);
        if (schemaAnnotation != null) {
            if (schemaAnnotation.value().isEmpty()) {
                throw new InterruptProcessingException(
                        modelTypeElement, "Redundant annotation: '?'. Remote it", Schema.class.getName()
                );
            }
            if (!dbObjectSchema.isEmpty()) {
                throw new InterruptProcessingException(
                        modelTypeElement, "Redundant annotation: '?'. Remote it", Schema.class.getName()
                );
            }
            return Optional.of(schemaAnnotation.value());
        } else {
            return Optional.of(dbObjectSchema).filter(v -> !v.isEmpty());
        }
    }

    /**
     * @author nedis
     * @since 0.7
     */
    private interface NameConfigurator {

        String schema();

        String name();

        MappingStrategy mappingStrategy();

        String type();
    }
}
