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

package io.rxmicro.annotation.processor.data.mongo.component.impl.method;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mongodb.reactivestreams.client.AggregatePublisher;
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
import io.rxmicro.data.mongo.operation.Aggregate;
import org.bson.Document;
import reactor.core.publisher.Flux;

import javax.lang.model.element.ExecutableElement;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getModelTransformerInstanceName;
import static java.util.stream.Collectors.toList;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @link https://docs.mongodb.com/manual/reference/bson-types/
 * @link https://docs.mongodb.com/manual/reference/method/db.collection.aggregate/
 * @since 0.1
 */
@Singleton
public final class AggregateOperationMongoRepositoryMethodModelBuilder extends AbstractMongoRepositoryMethodModelBuilder {

    @Inject
    private BsonExpressionBuilder bsonExpressionBuilder;

    @Override
    public Class<? extends Annotation> operationType() {
        return Aggregate.class;
    }

    @Override
    protected List<String> buildBody(final ClassHeader.Builder classHeaderBuilder,
                                     final ExecutableElement method,
                                     final MethodResult methodResult,
                                     final MethodParameterReader methodParameterReader,
                                     final DataRepositoryGeneratorConfig dataRepositoryGeneratorConfig,
                                     final DataGenerationContext<MongoDataModelField, MongoDataObjectModelClass> dataGenerationContext) {
        addImports(classHeaderBuilder);
        validateReturnType(
                method,
                methodResult.getResultType(),
                dataGenerationContext.getEntityResultTypes()
        );
        final Map<String, Object> templateArguments = new HashMap<>();
        putCommonArguments(dataRepositoryGeneratorConfig, templateArguments);
        templateArguments.put("RETURN", methodResult);
        templateArguments.put(
                "ENTITY_CONVERTER",
                getModelTransformerInstanceName(methodResult.getResultType(), EntityFromMongoDBConverter.class)
        );
        final Aggregate annotation = method.getAnnotation(Aggregate.class);
        final String[] pipeline = annotation.pipeline();
        templateArguments.put("PIPELINE", Arrays.stream(pipeline)
                .map(p -> bsonExpressionBuilder.build(method, classHeaderBuilder, p, methodParameterReader))
                .collect(toList()));
        final String hint = annotation.hint();
        if (!hint.trim().isEmpty()) {
            templateArguments.put("HINT", bsonExpressionBuilder.build(method, classHeaderBuilder, hint, methodParameterReader));
        }
        templateArguments.put("ALLOW_DISK_USE", annotation.allowDiskUse());
        return methodBodyGenerator.generate(
                "data/mongo/method/$$MongoRepositoryAggregateMethodBodyTemplate.javaftl",
                templateArguments
        );
    }

    private void addImports(final ClassHeader.Builder classHeaderBuilder) {
        classHeaderBuilder
                .addImports(
                        Document.class,
                        AggregatePublisher.class,
                        List.class,
                        Arrays.class,
                        Flux.class,
                        Flowable.class,
                        ArrayList.class
                );
    }
}
