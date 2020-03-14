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

package io.rxmicro.annotation.processor.data.mongo.component.impl;

import com.mongodb.BasicDBObject;
import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.common.model.method.MethodBody;
import io.rxmicro.annotation.processor.common.model.method.MethodResult;
import io.rxmicro.annotation.processor.data.component.impl.AbstractDataRepositoryMethodModelBuilder;
import io.rxmicro.annotation.processor.data.model.DataGenerationContext;
import io.rxmicro.annotation.processor.data.model.DataRepositoryMethodSignature;
import io.rxmicro.annotation.processor.data.model.Var;
import io.rxmicro.annotation.processor.data.mongo.component.MongoRepositoryMethodModelBuilder;
import io.rxmicro.annotation.processor.data.mongo.model.MongoDataModelField;
import io.rxmicro.annotation.processor.data.mongo.model.MongoDataObjectModelClass;
import io.rxmicro.annotation.processor.data.mongo.model.MongoMethodBody;
import io.rxmicro.annotation.processor.data.mongo.model.MongoRepositoryMethod;
import io.rxmicro.annotation.processor.data.mongo.model.MongoVar;
import io.rxmicro.data.DataRepositoryGeneratorConfig;
import io.rxmicro.data.detail.adapter.PublisherToFluxFutureAdapter;
import io.rxmicro.data.detail.adapter.PublisherToOptionalMonoFutureAdapter;
import io.rxmicro.data.detail.adapter.PublisherToRequiredMonoFutureAdapter;
import org.bson.conversions.Bson;

import javax.lang.model.element.ExecutableElement;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public abstract class AbstractMongoRepositoryMethodModelBuilder
        extends AbstractDataRepositoryMethodModelBuilder<MongoDataModelField, MongoRepositoryMethod, MongoDataObjectModelClass>
        implements MongoRepositoryMethodModelBuilder {

    @Override
    protected final MongoRepositoryMethod build(final DataRepositoryMethodSignature dataRepositoryMethodSignature,
                                                final MethodBody body) {
        return new MongoRepositoryMethod(dataRepositoryMethodSignature, body);
    }

    @Override
    protected final MethodBody buildBody(final ClassHeader.Builder classHeaderBuilder,
                                         final ExecutableElement repositoryMethod,
                                         final MethodResult methodResult,
                                         final DataRepositoryGeneratorConfig dataRepositoryGeneratorConfig,
                                         final DataGenerationContext<MongoDataModelField, MongoDataObjectModelClass> dataGenerationContext) {
        final MethodParameterReader methodParameterReader =
                new MethodParameterReader(repositoryMethod.getParameters(), supportedTypesProvider);
        final List<String> content = buildBody(
                classHeaderBuilder,
                repositoryMethod, methodResult,
                methodParameterReader,
                dataRepositoryGeneratorConfig,
                dataGenerationContext
        );
        validateUnusedMethodParameters(repositoryMethod, methodParameterReader);
        return new MongoMethodBody(content);
    }

    @Override
    protected final void addCommonImports(final ClassHeader.Builder classHeaderBuilder,
                                          final MethodResult methodResult) {
        classHeaderBuilder.addImports(
                Bson.class,
                BasicDBObject.class
        );
        if (methodResult.isFuture()) {
            if (methodResult.isOneItem()) {
                if (methodResult.isOptional()) {
                    classHeaderBuilder.addImports(PublisherToOptionalMonoFutureAdapter.class);
                } else {
                    classHeaderBuilder.addImports(PublisherToRequiredMonoFutureAdapter.class);
                }
            } else {
                classHeaderBuilder.addImports(
                        PublisherToFluxFutureAdapter.class,
                        Collectors.class
                );
            }
        }
    }

    protected abstract List<String> buildBody(final ClassHeader.Builder classHeaderBuilder,
                                              final ExecutableElement method,
                                              final MethodResult methodResult,
                                              final MethodParameterReader methodParameterReader,
                                              final DataRepositoryGeneratorConfig dataRepositoryGeneratorConfig,
                                              final DataGenerationContext<MongoDataModelField, MongoDataObjectModelClass> dataGenerationContext);

    private void validateUnusedMethodParameters(final ExecutableElement repositoryMethod,
                                                final MethodParameterReader methodParameterReader) {
        final List<Var> unusedParameters = methodParameterReader.getUnusedVars();
        if (!unusedParameters.isEmpty()) {
            throw new InterruptProcessingException(repositoryMethod,
                    "The following parameter(s) unused: [?]. Remove it or add missing placeholder(s)!",
                    unusedParameters.stream()
                            .map(Var::toString)
                            .collect(joining(", ")));
        }
    }

    protected final void setPageable(final ExecutableElement repositoryMethod,
                                     final MethodParameterReader methodParameterReader,
                                     final Map<String, Object> templateArguments,
                                     final Class<? extends Annotation> annotationClass,
                                     final int limit,
                                     final int skip) {
        if (limit > -1) {
            templateArguments.put("LIMIT", limit);
        }
        if (skip > -1) {
            templateArguments.put("SKIP", skip);
        }
        Optional<MongoVar> limitVar = methodParameterReader.nextIfLimit();
        final Optional<MongoVar> skipVar = methodParameterReader.nextIfSkip();
        if (limitVar.isEmpty()) {
            limitVar = methodParameterReader.nextIfLimit();
        }
        limitVar.ifPresent(var -> {
            templateArguments.put("LIMIT", var.getGetter());
            if (limit > -1) {
                warn(repositoryMethod,
                        "Method parameter '?' overrides annotation parameter: '?.limit()'",
                        var.getName(), annotationClass.getName());
            }
        });
        skipVar.ifPresent(var -> {
            templateArguments.put("SKIP", var.getGetter());
            if (skip > -1) {
                warn(repositoryMethod,
                        "Method parameter '?' overrides annotation parameter: '?.skip()'",
                        var.getName(), annotationClass.getName());
            }
        });
    }
}
