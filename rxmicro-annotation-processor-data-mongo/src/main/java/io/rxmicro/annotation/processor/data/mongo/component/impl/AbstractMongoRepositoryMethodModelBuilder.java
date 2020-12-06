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

package io.rxmicro.annotation.processor.data.mongo.component.impl;

import com.google.inject.Inject;
import com.mongodb.BasicDBObject;
import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.common.model.method.MethodBody;
import io.rxmicro.annotation.processor.common.model.method.MethodResult;
import io.rxmicro.annotation.processor.data.component.DataMethodParamsResolver;
import io.rxmicro.annotation.processor.data.component.impl.AbstractDataRepositoryMethodModelBuilder;
import io.rxmicro.annotation.processor.data.model.DataGenerationContext;
import io.rxmicro.annotation.processor.data.model.DataMethodParams;
import io.rxmicro.annotation.processor.data.model.DataRepositoryMethodSignature;
import io.rxmicro.annotation.processor.data.model.Variable;
import io.rxmicro.annotation.processor.data.mongo.component.MongoRepositoryMethodModelBuilder;
import io.rxmicro.annotation.processor.data.mongo.model.MongoDataModelField;
import io.rxmicro.annotation.processor.data.mongo.model.MongoDataObjectModelClass;
import io.rxmicro.annotation.processor.data.mongo.model.MongoMethodBody;
import io.rxmicro.annotation.processor.data.mongo.model.MongoRepositoryMethod;
import io.rxmicro.data.DataRepositoryGeneratorConfig;
import io.rxmicro.data.detail.adapter.PublisherToFluxFutureAdapter;
import io.rxmicro.data.detail.adapter.PublisherToOptionalMonoFutureAdapter;
import io.rxmicro.data.detail.adapter.PublisherToRequiredMonoFutureAdapter;
import io.rxmicro.data.mongo.MongoRepository;
import io.rxmicro.data.mongo.operation.Delete;
import io.rxmicro.data.mongo.operation.Insert;
import io.rxmicro.data.mongo.operation.Update;
import io.rxmicro.logger.RequestIdSupplier;
import org.bson.conversions.Bson;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;

import static io.rxmicro.annotation.processor.data.model.CommonDataGroupRules.REQUEST_ID_SUPPLIER_GROUP;
import static io.rxmicro.annotation.processor.data.model.CommonDataGroupRules.REQUEST_ID_SUPPLIER_PREDICATE;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Strings.unCapitalize;
import static java.util.stream.Collectors.joining;

/**
 * @author nedis
 * @since 0.1
 */
public abstract class AbstractMongoRepositoryMethodModelBuilder
        extends AbstractDataRepositoryMethodModelBuilder<MongoDataModelField, MongoRepositoryMethod, MongoDataObjectModelClass>
        implements MongoRepositoryMethodModelBuilder {

    private static final Map<Class<? extends Annotation>, String> MONGO_DB_NOT_STD_OPERATION_NAMES = Map.of(
            Insert.class, "insertOne",
            Update.class, "updateMany",
            Delete.class, "deleteMany"
    );

    private final Map<String, Predicate<VariableElement>> groupRules = Map.of(
            REQUEST_ID_SUPPLIER_GROUP, REQUEST_ID_SUPPLIER_PREDICATE
    );

    @Inject
    private DataMethodParamsResolver dataMethodParamsResolver;

    @Override
    protected final MongoRepositoryMethod build(final DataRepositoryMethodSignature dataRepositoryMethodSignature,
                                                final MethodBody body) {
        final String collection =
                dataRepositoryMethodSignature.getDataRepositoryInterfaceSignature().getRepositoryInterface()
                        .getAnnotation(MongoRepository.class).collection();
        final String operation = Optional.ofNullable(MONGO_DB_NOT_STD_OPERATION_NAMES.get(operationType()))
                .orElseGet(() -> unCapitalize(operationType().getSimpleName()));
        return new MongoRepositoryMethod(
                format("db.?.?()", collection, operation),
                dataRepositoryMethodSignature,
                body
        );
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

    @Override
    protected final MethodBody buildBody(final ClassHeader.Builder classHeaderBuilder,
                                         final ExecutableElement method,
                                         final MethodResult methodResult,
                                         final DataRepositoryGeneratorConfig dataRepositoryGeneratorConfig,
                                         final DataGenerationContext<MongoDataModelField, MongoDataObjectModelClass> generationContext) {
        final DataMethodParams dataMethodParams = dataMethodParamsResolver.resolve(method, groupRules);
        validateCommonDataMethodParams(dataMethodParams);
        final MethodParameterReader methodParameterReader = new MethodParameterReader(dataMethodParams);
        final List<String> content = buildBody(
                classHeaderBuilder,
                method, methodResult,
                methodParameterReader,
                dataRepositoryGeneratorConfig,
                generationContext
        );
        validateUnusedMethodParameters(method, methodParameterReader);
        return new MongoMethodBody(content);
    }

    protected abstract List<String> buildBody(ClassHeader.Builder classHeaderBuilder,
                                              ExecutableElement method,
                                              MethodResult methodResult,
                                              MethodParameterReader methodParameterReader,
                                              DataRepositoryGeneratorConfig dataRepositoryGeneratorConfig,
                                              DataGenerationContext<MongoDataModelField, MongoDataObjectModelClass> dataGenerationContext);

    protected void validateCommonDataMethodParams(final DataMethodParams dataMethodParams) {
        final List<Variable> requestIdSupplierParams = dataMethodParams.getParamsOfGroup(REQUEST_ID_SUPPLIER_GROUP);
        if (!requestIdSupplierParams.isEmpty()) {
            final Variable variable = requestIdSupplierParams.get(0);
            throw new InterruptProcessingException(
                    variable.getElement(),
                    "Unfortunately, the RxMicro framework does not support the '?' parameter for Mongo data repositories yet! " +
                            "Remove unsupported parameter!",
                    RequestIdSupplier.class.getName()
            );
        }
        validatePageableParameter(dataMethodParams);
    }

    private void validateUnusedMethodParameters(final ExecutableElement repositoryMethod,
                                                final MethodParameterReader methodParameterReader) {
        final List<Variable> unusedParameters = methodParameterReader.getUnusedVars();
        if (!unusedParameters.isEmpty()) {
            throw new InterruptProcessingException(repositoryMethod,
                    "The following parameter(s) unused: [?]. Remove it or add missing placeholder(s)!",
                    unusedParameters.stream()
                            .map(Variable::toString)
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
        Optional<Variable> limitVar = methodParameterReader.nextIfLimit();
        final Optional<Variable> skipVar = methodParameterReader.nextIfSkip();
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
