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
import io.rxmicro.data.mongo.operation.Distinct;
import org.bson.Document;
import reactor.core.publisher.Flux;

import javax.lang.model.element.ExecutableElement;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class DistinctOperationMongoRepositoryMethodModelBuilder extends AbstractMongoRepositoryMethodModelBuilder {

    @Inject
    private BsonExpressionBuilder bsonExpressionBuilder;

    @Override
    public Class<? extends Annotation> operationType() {
        return Distinct.class;
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
                List.of(allowedPrimitives())
        );

        final Map<String, Object> templateArguments = new HashMap<>();
        putCommonArguments(dataRepositoryGeneratorConfig, templateArguments);
        templateArguments.put("RETURN", methodResult);
        final Distinct annotation = method.getAnnotation(Distinct.class);
        final String query = annotation.query();
        if (!query.isBlank()) {
            templateArguments.put("QUERY", bsonExpressionBuilder.build(method, classHeaderBuilder, query, methodParameterReader));
        }
        templateArguments.put("FIELD", annotation.field());
        return methodBodyGenerator.generate(
                "data/mongo/method/$$MongoRepositoryDistinctMethodBodyTemplate.javaftl",
                templateArguments
        );
    }

    private void addImports(final ClassHeader.Builder classHeaderBuilder) {
        classHeaderBuilder
                .addImports(
                        Document.class,
                        Flux.class,
                        Flowable.class,
                        ArrayList.class
                );
    }
}
