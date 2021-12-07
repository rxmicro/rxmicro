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

package io.rxmicro.annotation.processor.data.sql.r2dbc.postgresql.component.impl;

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.model.ClassStructure;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.data.component.DataRepositoryConfigAutoCustomizerBuilder;
import io.rxmicro.annotation.processor.data.model.DataRepositoryClassStructure;
import io.rxmicro.annotation.processor.data.sql.r2dbc.postgresql.model.PostgreSQLConfigAutoCustomizerClassStructure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.lang.model.element.TypeElement;

import static io.rxmicro.common.util.ExCollections.unmodifiableList;

/**
 * @author nedis
 * @since 0.7
 */
@Singleton
public final class PostgreSQLDataRepositoryConfigAutoCustomizerBuilder implements DataRepositoryConfigAutoCustomizerBuilder {

    @Override
    public Optional<ClassStructure> build(final EnvironmentContext environmentContext,
                                          final Set<DataRepositoryClassStructure> dataRepositoryClassStructures) {
        final List<Map.Entry<TypeElement, String>> postgresEnumMapping =
                validateAndReturnPostgresEnumMappingWithoutDuplicates(
                        dataRepositoryClassStructures.stream().flatMap(r -> r.getEnumMapping().stream()).collect(Collectors.toList())
                );
        return Optional.of(postgresEnumMapping)
                .filter(l -> !l.isEmpty())
                .map(postgresEnumMapping1 ->
                        new PostgreSQLConfigAutoCustomizerClassStructure(environmentContext.getCurrentModule(), postgresEnumMapping1));
    }

    private List<Map.Entry<TypeElement, String>> validateAndReturnPostgresEnumMappingWithoutDuplicates(
            final List<Map.Entry<TypeElement, String>> postgresEnumMapping) {
        final Map<String, Map.Entry<TypeElement, String>> classNames = new HashMap<>();
        final Map<String, Map.Entry<TypeElement, String>> dbTypeNames = new HashMap<>();
        for (final Map.Entry<TypeElement, String> entry : postgresEnumMapping) {
            final String fullClassName = entry.getKey().getQualifiedName().toString();
            validateEnumClassNames(classNames, entry, fullClassName);
            validateDbTypeNames(dbTypeNames, entry, fullClassName);
        }
        return unmodifiableList(classNames.values());
    }

    private void validateEnumClassNames(final Map<String, Map.Entry<TypeElement, String>> classNames,
                                        final Map.Entry<TypeElement, String> entry,
                                        final String fullClassName) {
        final Map.Entry<TypeElement, String> processedEntry = classNames.get(fullClassName);
        if (processedEntry != null) {
            if (!entry.getValue().equals(processedEntry.getValue())) {
                throw new InterruptProcessingException(
                        entry.getKey(),
                        "Detected two different db type names for the same enum class: ?, " +
                                "first db type name = ? and second db type name = ?!" +
                                "Set unique db type name per enum class!",
                        fullClassName, entry.getValue(), processedEntry.getValue()
                );
            }
        } else {
            classNames.put(fullClassName, entry);
        }
    }

    private void validateDbTypeNames(final Map<String, Map.Entry<TypeElement, String>> dbTypeNames,
                                     final Map.Entry<TypeElement, String> entry,
                                     final String fullClassName) {
        final Map.Entry<TypeElement, String> processedEntry = dbTypeNames.get(entry.getValue());
        if (processedEntry != null) {
            if (!fullClassName.equals(processedEntry.getKey().getQualifiedName().toString())) {
                throw new InterruptProcessingException(
                        entry.getKey(),
                        "Detected two different enum classes for the same db type name: ?, " +
                                "first enum class = ? and second enum class = ?!" +
                                "Set unique db type name per enum class!",
                        fullClassName, entry.getValue(), processedEntry.getValue()
                );
            }
        } else {
            dbTypeNames.put(fullClassName, entry);
        }
    }
}
