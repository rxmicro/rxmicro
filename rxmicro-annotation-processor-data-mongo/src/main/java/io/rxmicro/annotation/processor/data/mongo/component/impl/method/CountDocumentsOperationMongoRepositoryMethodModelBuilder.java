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
import com.mongodb.client.model.CountOptions;
import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.method.MethodResult;
import io.rxmicro.annotation.processor.data.model.DataGenerationContext;
import io.rxmicro.annotation.processor.data.mongo.component.BsonExpressionBuilder;
import io.rxmicro.annotation.processor.data.mongo.component.impl.AbstractMongoRepositoryMethodModelBuilder;
import io.rxmicro.annotation.processor.data.mongo.component.impl.MethodParameterReader;
import io.rxmicro.annotation.processor.data.mongo.model.MongoDataModelField;
import io.rxmicro.annotation.processor.data.mongo.model.MongoDataObjectModelClass;
import io.rxmicro.data.DataRepositoryGeneratorConfig;
import io.rxmicro.data.mongo.detail.MongoQueries;
import io.rxmicro.data.mongo.operation.CountDocuments;
import org.bson.Document;
import org.reactivestreams.Publisher;

import javax.lang.model.element.ExecutableElement;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class CountDocumentsOperationMongoRepositoryMethodModelBuilder extends AbstractMongoRepositoryMethodModelBuilder {

    @Inject
    private BsonExpressionBuilder bsonExpressionBuilder;

    @Override
    public Class<? extends Annotation> operationType() {
        return CountDocuments.class;
    }

    @Override
    protected List<String> buildBody(final ClassHeader.Builder classHeaderBuilder,
                                     final ExecutableElement method,
                                     final MethodResult methodResult,
                                     final MethodParameterReader methodParameterReader,
                                     final DataRepositoryGeneratorConfig dataRepositoryGeneratorConfig,
                                     final DataGenerationContext<MongoDataModelField, MongoDataObjectModelClass> dataGenerationContext) {
        classHeaderBuilder.addImports(
                Publisher.class,
                Document.class,
                CountOptions.class,
                MongoQueries.class
        );
        validateRequiredSingleReturnType(method, methodResult);
        validateReturnType(method, methodResult.getResultType(), Long.class);

        final Map<String, Object> templateArguments = new HashMap<>();
        putCommonArguments(dataRepositoryGeneratorConfig, templateArguments);
        templateArguments.put("RETURN", methodResult);
        final CountDocuments annotation = method.getAnnotation(CountDocuments.class);
        final String query = annotation.query();
        if (!query.isBlank()) {
            templateArguments.put("QUERY", bsonExpressionBuilder.build(method, classHeaderBuilder, query, methodParameterReader));
        }
        final String hint = annotation.hint();
        if (!hint.isBlank()) {
            templateArguments.put("HINT", bsonExpressionBuilder.build(method, classHeaderBuilder, hint, methodParameterReader));
        }

        final Class<? extends Annotation> annotationType = annotation.annotationType();
        final int limit = annotation.limit();
        final int skip = annotation.skip();
        setPageable(method, methodParameterReader, templateArguments, annotationType, limit, skip);

        return methodBodyGenerator.generate(
                "data/mongo/method/$$MongoRepositoryCountDocumentsMethodBodyTemplate.javaftl",
                templateArguments
        );
    }
}
