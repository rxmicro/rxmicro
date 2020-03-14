/*
 * Copyright 2019 http://rxmicro.io
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

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import io.rxmicro.annotation.processor.common.component.ModelFieldBuilder;
import io.rxmicro.annotation.processor.common.model.definition.SupportedTypesProvider;
import io.rxmicro.annotation.processor.data.component.DataClassStructureBuilder;
import io.rxmicro.annotation.processor.data.component.DataGenerationContextBuilder;
import io.rxmicro.annotation.processor.data.component.DataRepositoryInterfaceSignatureBuilder;
import io.rxmicro.annotation.processor.data.component.DataRepositoryMethodSignatureBuilder;
import io.rxmicro.annotation.processor.data.component.EntityConverterBuilder;
import io.rxmicro.annotation.processor.data.component.impl.DataGenerationContextBuilderImpl;
import io.rxmicro.annotation.processor.data.mongo.component.BsonExpressionBuilder;
import io.rxmicro.annotation.processor.data.mongo.component.MongoRepositoryMethodModelBuilder;
import io.rxmicro.annotation.processor.data.mongo.component.impl.BsonExpressionBuilderImpl;
import io.rxmicro.annotation.processor.data.mongo.component.impl.MongoDataModelFieldBuilderImpl;
import io.rxmicro.annotation.processor.data.mongo.component.impl.MongoDataRepositoryInterfaceSignatureBuilder;
import io.rxmicro.annotation.processor.data.mongo.component.impl.MongoDataRepositoryMethodSignatureBuilder;
import io.rxmicro.annotation.processor.data.mongo.component.impl.MongoDocumentEntityConverterBuilder;
import io.rxmicro.annotation.processor.data.mongo.component.impl.MongoRepositoryClassStructureBuilderImpl;
import io.rxmicro.annotation.processor.data.mongo.component.impl.method.AggregateOperationMongoRepositoryMethodModelBuilder;
import io.rxmicro.annotation.processor.data.mongo.component.impl.method.CountDocumentsOperationMongoRepositoryMethodModelBuilder;
import io.rxmicro.annotation.processor.data.mongo.component.impl.method.DeleteByFilterOperationMongoRepositoryMethodModelBuilder;
import io.rxmicro.annotation.processor.data.mongo.component.impl.method.DeleteEntityOrByIdOperationMongoRepositoryMethodModelBuilder;
import io.rxmicro.annotation.processor.data.mongo.component.impl.method.DistinctOperationMongoRepositoryMethodModelBuilder;
import io.rxmicro.annotation.processor.data.mongo.component.impl.method.EstimateDocumentCountsOperationMongoRepositoryMethodModelBuilder;
import io.rxmicro.annotation.processor.data.mongo.component.impl.method.FindOperationMongoRepositoryMethodModelBuilder;
import io.rxmicro.annotation.processor.data.mongo.component.impl.method.InsertOperationMongoRepositoryMethodModelBuilder;
import io.rxmicro.annotation.processor.data.mongo.component.impl.method.UpdateDocumentOperationMongoRepositoryMethodModelBuilder;
import io.rxmicro.annotation.processor.data.mongo.component.impl.method.UpdateEntityOperationMongoRepositoryMethodModelBuilder;
import io.rxmicro.annotation.processor.data.mongo.component.impl.method.UpdateExpressionOperationMongoRepositoryMethodModelBuilder;
import io.rxmicro.annotation.processor.data.mongo.model.MongoDataModelField;
import io.rxmicro.annotation.processor.data.mongo.model.MongoDataObjectModelClass;

import static com.google.inject.multibindings.Multibinder.newSetBinder;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class MongoDependenciesModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(SupportedTypesProvider.class)
                .to(MongoSupportedTypesProvider.class);
        bind(DataRepositoryMethodSignatureBuilder.class)
                .to(MongoDataRepositoryMethodSignatureBuilder.class);
        bind(DataRepositoryInterfaceSignatureBuilder.class)
                .to(MongoDataRepositoryInterfaceSignatureBuilder.class);
        bind(BsonExpressionBuilder.class)
                .to(BsonExpressionBuilderImpl.class);

        bindParametrizedClasses();
        bindMethodBodyBuilders();
    }

    private void bindParametrizedClasses() {
        bind(new TypeLiteral<DataClassStructureBuilder<MongoDataModelField, MongoDataObjectModelClass>>() {
        })
                .to(MongoRepositoryClassStructureBuilderImpl.class);
        bind(new TypeLiteral<EntityConverterBuilder<MongoDataModelField, MongoDataObjectModelClass>>() {
        })
                .to(MongoDocumentEntityConverterBuilder.class);
        bind(new TypeLiteral<ModelFieldBuilder<MongoDataModelField, MongoDataObjectModelClass>>() {
        })
                .to(MongoDataModelFieldBuilderImpl.class);
        bind(new TypeLiteral<DataGenerationContextBuilder<MongoDataModelField, MongoDataObjectModelClass>>() {
        })
                .to(new TypeLiteral<DataGenerationContextBuilderImpl<MongoDataModelField, MongoDataObjectModelClass>>() {
                });
    }

    private void bindMethodBodyBuilders() {
        final Multibinder<MongoRepositoryMethodModelBuilder> binder =
                newSetBinder(binder(), MongoRepositoryMethodModelBuilder.class);
        // SELECT
        binder.addBinding().to(FindOperationMongoRepositoryMethodModelBuilder.class);
        binder.addBinding().to(AggregateOperationMongoRepositoryMethodModelBuilder.class);
        binder.addBinding().to(DistinctOperationMongoRepositoryMethodModelBuilder.class);
        binder.addBinding().to(CountDocumentsOperationMongoRepositoryMethodModelBuilder.class);
        binder.addBinding().to(EstimateDocumentCountsOperationMongoRepositoryMethodModelBuilder.class);
        // MODIFICATION
        binder.addBinding().to(InsertOperationMongoRepositoryMethodModelBuilder.class);
        binder.addBinding().to(UpdateExpressionOperationMongoRepositoryMethodModelBuilder.class);
        binder.addBinding().to(UpdateEntityOperationMongoRepositoryMethodModelBuilder.class);
        binder.addBinding().to(UpdateDocumentOperationMongoRepositoryMethodModelBuilder.class);
        binder.addBinding().to(DeleteEntityOrByIdOperationMongoRepositoryMethodModelBuilder.class);
        binder.addBinding().to(DeleteByFilterOperationMongoRepositoryMethodModelBuilder.class);
        // Add new operation method builders here
    }
}
