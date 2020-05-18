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

package io.rxmicro.annotation.processor.data.mongo.component.impl.method;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mongodb.reactivestreams.client.FindPublisher;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.method.MethodResult;
import io.rxmicro.annotation.processor.data.model.DataGenerationContext;
import io.rxmicro.annotation.processor.data.mongo.component.BsonExpressionBuilder;
import io.rxmicro.annotation.processor.data.mongo.component.impl.AbstractMongoRepositoryMethodModelBuilder;
import io.rxmicro.annotation.processor.data.mongo.component.impl.MethodParameterReader;
import io.rxmicro.annotation.processor.data.mongo.model.MongoDataModelField;
import io.rxmicro.annotation.processor.data.mongo.model.MongoDataObjectModelClass;
import io.rxmicro.data.DataRepositoryGeneratorConfig;
import io.rxmicro.data.mongo.detail.EntityFromMongoDBConverter;
import io.rxmicro.data.mongo.operation.Find;
import org.bson.Document;
import reactor.core.publisher.Flux;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.lang.model.element.ExecutableElement;

import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getModelTransformerInstanceName;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class FindOperationMongoRepositoryMethodModelBuilder extends AbstractMongoRepositoryMethodModelBuilder {

    @Inject
    private BsonExpressionBuilder bsonExpressionBuilder;

    @Override
    public Class<? extends Annotation> operationType() {
        return Find.class;
    }

    @Override
    protected List<String> buildBody(final ClassHeader.Builder classHeaderBuilder,
                                     final ExecutableElement method,
                                     final MethodResult methodResult,
                                     final MethodParameterReader methodParameterReader,
                                     final DataRepositoryGeneratorConfig dataRepositoryGeneratorConfig,
                                     final DataGenerationContext<MongoDataModelField, MongoDataObjectModelClass> dataGenerationContext) {
        addImports(classHeaderBuilder);
        validateReactiveTypeWithExcluded(method, methodResult, Completable.class);
        validateReturnType(method, methodResult.getResultType(), dataGenerationContext.getEntityResultTypes());

        final Map<String, Object> templateArguments = new HashMap<>();
        putCommonArguments(dataRepositoryGeneratorConfig, templateArguments);
        templateArguments.put("RETURN", methodResult);
        templateArguments.put("ENTITY_CONVERTER", getModelTransformerInstanceName(
                methodResult.getResultType(),
                EntityFromMongoDBConverter.class)
        );
        final Find annotation = method.getAnnotation(Find.class);
        final String query = annotation.query();
        if (!query.isEmpty()) {
            templateArguments.put("QUERY", bsonExpressionBuilder.build(method, classHeaderBuilder, query, methodParameterReader));
        }
        final String projection = annotation.projection();
        if (!projection.isEmpty()) {
            templateArguments.put("PROJECTION", bsonExpressionBuilder.build(method, classHeaderBuilder, projection, methodParameterReader));
        }
        final String hint = annotation.hint();
        if (!hint.isEmpty()) {
            templateArguments.put("HINT", bsonExpressionBuilder.build(method, classHeaderBuilder, hint, methodParameterReader));
        }
        final String sort = annotation.sort();
        if (!sort.isEmpty()) {
            templateArguments.put("SORT", bsonExpressionBuilder.build(method, classHeaderBuilder, sort, methodParameterReader));
        }

        final Class<? extends Annotation> annotationType = annotation.annotationType();
        final int limit = annotation.limit();
        final int skip = annotation.skip();
        setPageable(method, methodParameterReader, templateArguments, annotationType, limit, skip);

        return methodBodyGenerator.generate(
                "data/mongo/method/$$MongoRepositoryFindMethodBodyTemplate.javaftl",
                templateArguments
        );
    }

    private void addImports(final ClassHeader.Builder classHeaderBuilder) {
        classHeaderBuilder.addImports(
                Document.class,
                FindPublisher.class,
                Flux.class,
                Flowable.class,
                ArrayList.class
        );
    }
}
