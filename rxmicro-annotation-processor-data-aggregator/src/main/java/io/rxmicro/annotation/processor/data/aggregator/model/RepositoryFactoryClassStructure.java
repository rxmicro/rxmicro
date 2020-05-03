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

package io.rxmicro.annotation.processor.data.aggregator.model;

import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.ClassStructure;
import io.rxmicro.annotation.processor.common.model.error.InternalErrorException;
import io.rxmicro.annotation.processor.data.model.DataRepositoryClassStructure;
import io.rxmicro.annotation.processor.data.mongo.model.MongoRepositoryClassStructure;
import io.rxmicro.annotation.processor.data.sql.r2dbc.postgresql.model.PostgreSQLRepositoryClassStructure;
import io.rxmicro.config.detail.DefaultConfigValueBuilder;
import io.rxmicro.data.RepositoryFactory;
import io.rxmicro.data.mongo.detail.MongoRepositoryFactory;
import io.rxmicro.data.sql.r2dbc.postgresql.detail.PostgreSQLRepositoryFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.rxmicro.annotation.processor.common.model.ClassHeader.newClassHeaderBuilder;
import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.ENVIRONMENT_CUSTOMIZER_SIMPLE_CLASS_NAME;
import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getEntryPointFullClassName;
import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.data.RepositoryFactory.REPOSITORY_FACTORY_IMPL_CLASS_NAME;
import static io.rxmicro.runtime.detail.Runtimes.ENTRY_POINT_PACKAGE;
import static java.util.stream.Collectors.toList;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class RepositoryFactoryClassStructure extends ClassStructure {

    private final Set<DataRepositoryClassStructure> classStructures;

    private final List<MongoRepositoryClassStructure> mongoRepositories;

    private final List<PostgreSQLRepositoryClassStructure> postgreSQLRepositories;

    public RepositoryFactoryClassStructure(final Set<DataRepositoryClassStructure> classStructures) {
        this.classStructures = require(classStructures);
        this.mongoRepositories = new ArrayList<>();
        this.postgreSQLRepositories = new ArrayList<>();
        for (final DataRepositoryClassStructure classStructure : classStructures) {
            if (classStructure instanceof MongoRepositoryClassStructure) {
                mongoRepositories.add((MongoRepositoryClassStructure) classStructure);
            } else if (classStructure instanceof PostgreSQLRepositoryClassStructure) {
                postgreSQLRepositories.add((PostgreSQLRepositoryClassStructure) classStructure);
            } else {
                throw new InternalErrorException("Unsupported data repository: " + classStructure);
            }
        }
    }

    @Override
    public String getTargetFullClassName() {
        return getEntryPointFullClassName(REPOSITORY_FACTORY_IMPL_CLASS_NAME);
    }

    @Override
    public String getTemplateName() {
        return "data/aggregator/$$RepositoryFactoryImplTemplate.javaftl";
    }

    @Override
    public Map<String, Object> getTemplateVariables() {
        final Map<String, Object> map = new HashMap<>();
        map.put("IMPL_CLASS_NAME", REPOSITORY_FACTORY_IMPL_CLASS_NAME);
        map.put("MONGO_REPOSITORIES", mongoRepositories);
        map.put("POSTGRE_SQL_REPOSITORIES", postgreSQLRepositories);
        map.put("ENVIRONMENT_CUSTOMIZER_CLASS", ENVIRONMENT_CUSTOMIZER_SIMPLE_CLASS_NAME);
        map.put("DEFAULT_CONFIG_VALUES", classStructures.stream()
                .flatMap(s -> s.getDefaultConfigValues().stream())
                .collect(toList()));
        return map;
    }

    @Override
    public ClassHeader getClassHeader() {
        final ClassHeader.Builder builder = newClassHeaderBuilder(ENTRY_POINT_PACKAGE)
                .addImports(RepositoryFactory.class)
                .addStaticImport(DefaultConfigValueBuilder.class, "putDefaultConfigValue");
        addRepositoryImports(
                builder, mongoRepositories, MongoRepositoryFactory.class, "createMongoRepository");
        addRepositoryImports(
                builder, postgreSQLRepositories, PostgreSQLRepositoryFactory.class, "createPostgreSQLRepository");
        return builder.build();
    }

    private void addRepositoryImports(final ClassHeader.Builder classHeaderBuilder,
                                      final List<? extends DataRepositoryClassStructure> dataRepositories,
                                      final Class<?> factoryClass,
                                      final String factoryMethod) {
        if (!dataRepositories.isEmpty()) {
            classHeaderBuilder.addStaticImport(factoryClass, factoryMethod);
            for (final DataRepositoryClassStructure r : dataRepositories) {
                classHeaderBuilder.addImports(r.getFullInterfaceName(), r.getTargetFullClassName());
            }
        }
    }
}
