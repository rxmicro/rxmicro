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

import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;
import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.method.MethodResult;
import io.rxmicro.annotation.processor.data.model.DataGenerationContext;
import io.rxmicro.annotation.processor.data.mongo.component.impl.AbstractMongoRepositoryMethodModelBuilder;
import io.rxmicro.annotation.processor.data.mongo.component.impl.MethodParameterReader;
import io.rxmicro.annotation.processor.data.mongo.model.MongoDataModelField;
import io.rxmicro.annotation.processor.data.mongo.model.MongoDataObjectModelClass;
import io.rxmicro.data.DataRepositoryGeneratorConfig;
import io.rxmicro.data.mongo.operation.Update;
import org.bson.Document;
import org.reactivestreams.Publisher;

import javax.lang.model.element.ExecutableElement;
import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author nedis
 * @since 0.4
 */
public abstract class AbstractUpdateOperationMongoRepositoryMethodModelBuilder extends AbstractMongoRepositoryMethodModelBuilder {

    @Override
    public final Class<? extends Annotation> operationType() {
        return Update.class;
    }

    @Override
    protected final List<String> buildBody(final ClassHeader.Builder classHeaderBuilder,
                                           final ExecutableElement method,
                                           final MethodResult methodResult,
                                           final MethodParameterReader methodParameterReader,
                                           final DataRepositoryGeneratorConfig dataRepositoryGeneratorConfig,
                                           final DataGenerationContext<MongoDataModelField, MongoDataObjectModelClass> generationContext) {
        classHeaderBuilder.addImports(
                Document.class,
                Publisher.class,
                UpdateResult.class,
                UpdateOptions.class
        );

        validateRequiredSingleReturnType(method, methodResult);
        validateReturnType(method, methodResult.getResultType(),
                Void.class, Long.class, Boolean.class, UpdateResult.class
        );
        return buildUpdateBody(
                classHeaderBuilder, method, methodResult, methodParameterReader, dataRepositoryGeneratorConfig, generationContext
        );
    }

    abstract List<String> buildUpdateBody(ClassHeader.Builder classHeaderBuilder,
                                          ExecutableElement method,
                                          MethodResult methodResult,
                                          MethodParameterReader methodParameterReader,
                                          DataRepositoryGeneratorConfig dataRepositoryGeneratorConfig,
                                          DataGenerationContext<MongoDataModelField, MongoDataObjectModelClass> dataGenerationContext);


}
