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

package io.rxmicro.annotation.processor.data.mongo.component.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.data.component.impl.AbstractDataClassStructureBuilder;
import io.rxmicro.annotation.processor.data.model.DataGenerationContext;
import io.rxmicro.annotation.processor.data.model.DataRepositoryClassStructure;
import io.rxmicro.annotation.processor.data.model.DataRepositoryInterfaceSignature;
import io.rxmicro.annotation.processor.data.mongo.component.MongoRepositoryMethodModelBuilder;
import io.rxmicro.annotation.processor.data.mongo.model.MongoDataModelField;
import io.rxmicro.annotation.processor.data.mongo.model.MongoDataObjectModelClass;
import io.rxmicro.annotation.processor.data.mongo.model.MongoRepositoryClassStructure;
import io.rxmicro.annotation.processor.data.mongo.model.MongoRepositoryMethod;
import io.rxmicro.data.mongo.MongoRepository;

import java.util.List;
import java.util.Set;

import static io.rxmicro.annotation.processor.common.model.ClassHeader.newClassHeaderBuilder;
import static io.rxmicro.annotation.processor.common.util.Annotations.getDefaultConfigValues;
import static io.rxmicro.annotation.processor.common.util.Names.getPackageName;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class MongoRepositoryClassStructureBuilderImpl
        extends AbstractDataClassStructureBuilder<MongoDataModelField, MongoDataObjectModelClass> {

    @Inject
    private Set<MongoRepositoryMethodModelBuilder> mongoRepositoryMethodModelBuilders;

    @Override
    public DataRepositoryClassStructure build(final EnvironmentContext environmentContext,
                                              final DataRepositoryInterfaceSignature signature,
                                              final DataGenerationContext<MongoDataModelField, MongoDataObjectModelClass> dataGenerationContext) {
        final ClassHeader.Builder classHeaderBuilder = newClassHeaderBuilder(getPackageName(signature.getRepositoryInterface()));
        final MongoRepository mongoRepository =
                signature.getRepositoryInterface().getAnnotation(MongoRepository.class);
        final List<MongoRepositoryMethod> methods = buildMethods(
                mongoRepositoryMethodModelBuilders,
                environmentContext,
                dataGenerationContext,
                signature,
                classHeaderBuilder
        );
        return new MongoRepositoryClassStructure(
                classHeaderBuilder,
                signature.getRepositoryInterface(),
                signature.getRepositoryAbstractClass(),
                mongoRepository.collection(),
                mongoRepository.configNameSpace(),
                methods,
                getDefaultConfigValues(mongoRepository.configNameSpace(), signature.getRepositoryInterface())
        );
    }
}
