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

package io.rxmicro.annotation.processor.data.mongo;

import io.rxmicro.annotation.processor.common.CommonDependenciesModule;
import io.rxmicro.annotation.processor.common.FormatSourceCodeDependenciesModule;
import io.rxmicro.annotation.processor.data.AbstractDataModuleClassStructuresBuilder;
import io.rxmicro.annotation.processor.data.CommonDataDependenciesModule;
import io.rxmicro.annotation.processor.data.mongo.model.MongoDataModelField;
import io.rxmicro.annotation.processor.data.mongo.model.MongoDataObjectModelClass;
import io.rxmicro.data.mongo.MongoRepository;

import java.util.Set;

import static io.rxmicro.annotation.processor.common.util.Injects.injectDependencies;

/**
 * @author nedis
 * @since 0.1
 */
public final class MongoModuleClassStructuresBuilder
        extends AbstractDataModuleClassStructuresBuilder<MongoDataModelField, MongoDataObjectModelClass> {

    public static MongoModuleClassStructuresBuilder create() {
        final MongoModuleClassStructuresBuilder builder = new MongoModuleClassStructuresBuilder();
        injectDependencies(
                builder,
                new FormatSourceCodeDependenciesModule(),
                new CommonDependenciesModule(),
                new CommonDataDependenciesModule(),
                new MongoDependenciesModule()
        );
        return builder;
    }

    private MongoModuleClassStructuresBuilder() {
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of(MongoRepository.class.getName());
    }
}
