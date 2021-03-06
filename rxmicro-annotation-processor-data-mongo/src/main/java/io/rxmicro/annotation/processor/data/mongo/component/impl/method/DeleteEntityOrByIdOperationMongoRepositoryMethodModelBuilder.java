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

import com.google.inject.Singleton;
import com.mongodb.client.result.DeleteResult;
import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.common.model.method.MethodResult;
import io.rxmicro.annotation.processor.data.model.DataGenerationContext;
import io.rxmicro.annotation.processor.data.model.DataRepositoryMethodSignature;
import io.rxmicro.annotation.processor.data.model.Variable;
import io.rxmicro.annotation.processor.data.mongo.component.impl.AbstractMongoRepositoryMethodModelBuilder;
import io.rxmicro.annotation.processor.data.mongo.component.impl.MethodParameterReader;
import io.rxmicro.annotation.processor.data.mongo.model.MongoDataModelField;
import io.rxmicro.annotation.processor.data.mongo.model.MongoDataObjectModelClass;
import io.rxmicro.common.util.Formats;
import io.rxmicro.data.DataRepositoryGeneratorConfig;
import io.rxmicro.data.mongo.detail.EntityToMongoDBConverter;
import io.rxmicro.data.mongo.operation.Delete;
import org.bson.Document;
import org.reactivestreams.Publisher;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.lang.model.element.ExecutableElement;

import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getModelTransformerInstanceName;
import static io.rxmicro.annotation.processor.common.util.Names.getSimpleName;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class DeleteEntityOrByIdOperationMongoRepositoryMethodModelBuilder extends AbstractMongoRepositoryMethodModelBuilder {

    @Override
    public boolean isSupported(final DataRepositoryMethodSignature dataRepositoryMethodSignature,
                               final DataGenerationContext<MongoDataModelField, MongoDataObjectModelClass> dataGenerationContext) {
        return super.isSupported(dataRepositoryMethodSignature, dataGenerationContext) &&
                dataRepositoryMethodSignature.getMethod().getAnnotation(Delete.class).filter().isBlank();
    }

    @Override
    public Class<? extends Annotation> operationType() {
        return Delete.class;
    }

    @Override
    protected List<String> buildBody(final ClassHeader.Builder classHeaderBuilder,
                                     final ExecutableElement method,
                                     final MethodResult methodResult,
                                     final MethodParameterReader methodParameterReader,
                                     final DataRepositoryGeneratorConfig dataRepositoryGeneratorConfig,
                                     final DataGenerationContext<MongoDataModelField, MongoDataObjectModelClass> dataGenerationContext) {
        classHeaderBuilder
                .addImports(
                        Publisher.class,
                        DeleteResult.class,
                        Document.class
                )
                .addStaticImport(Formats.class, "format");
        validateRequiredSingleReturnType(method, methodResult);
        validateReturnType(method, methodResult.getResultType(),
                Void.class, Long.class, Boolean.class, DeleteResult.class
        );
        final Map<String, Object> templateArguments = new HashMap<>();
        putCommonArguments(dataRepositoryGeneratorConfig, templateArguments);
        templateArguments.put("RETURN", methodResult);
        final Variable arg = getEntityOrDocumentId(method, methodParameterReader, dataGenerationContext);
        if (dataGenerationContext.isEntityParamType(arg.getType())) {
            templateArguments.put("ENTITY", arg.getName());
            templateArguments.put("ENTITY_CONVERTER", getModelTransformerInstanceName(
                    getSimpleName(arg.getType()),
                    EntityToMongoDBConverter.class)
            );
        } else {
            templateArguments.put("ID_VAR", arg.getName());
        }
        return methodBodyGenerator.generate(
                "data/mongo/method/$$MongoRepositoryDeleteEntityOrByIdMethodBodyTemplate.javaftl",
                templateArguments
        );
    }

    private Variable getEntityOrDocumentId(
            final ExecutableElement method,
            final MethodParameterReader methodParameterReader,
            final DataGenerationContext<MongoDataModelField, MongoDataObjectModelClass> dataGenerationContext) {
        final Variable mongoVar = methodParameterReader.nextVar().orElseThrow(() -> {
            throw new InterruptProcessingException(method,
                    "Method must have only one parameter. It must be a document id or a document with document id field!."
            );
        });
        if (dataGenerationContext.isEntityParamType(mongoVar.getType())) {
            return mongoVar;
        } else {
            if (!allowedPrimitives().contains(mongoVar.getType())) {
                throw new InterruptProcessingException(method,
                        "Method parameter must be a document id of primitive type. (FYI: Allowed primitive types are: ?)",
                        allowedPrimitives().getTypeDefinitions());
            }
            return mongoVar;
        }
    }
}
