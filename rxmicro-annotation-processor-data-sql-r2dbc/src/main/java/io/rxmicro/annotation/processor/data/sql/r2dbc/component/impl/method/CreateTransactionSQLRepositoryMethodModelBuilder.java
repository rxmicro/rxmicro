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

package io.rxmicro.annotation.processor.data.sql.r2dbc.component.impl.method;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.common.model.method.MethodBody;
import io.rxmicro.annotation.processor.common.model.method.MethodResult;
import io.rxmicro.annotation.processor.data.component.DataMethodParamsResolver;
import io.rxmicro.annotation.processor.data.model.DataGenerationContext;
import io.rxmicro.annotation.processor.data.model.DataMethodParams;
import io.rxmicro.annotation.processor.data.model.DataRepositoryMethodSignature;
import io.rxmicro.annotation.processor.data.model.Variable;
import io.rxmicro.annotation.processor.data.sql.component.impl.AbstractSQLDataRepositoryMethodModelBuilder;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataModelField;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataObjectModelClass;
import io.rxmicro.annotation.processor.data.sql.model.SQLMethodBody;
import io.rxmicro.data.DataRepositoryGeneratorConfig;
import io.rxmicro.data.sql.model.IsolationLevel;
import io.rxmicro.data.sql.model.TransactionType;
import io.rxmicro.logger.RequestIdSupplier;
import reactor.core.publisher.Mono;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;

import static io.rxmicro.annotation.processor.data.model.CommonDataGroupRules.REQUEST_ID_SUPPLIER_GROUP;
import static io.rxmicro.annotation.processor.data.model.CommonDataGroupRules.REQUEST_ID_SUPPLIER_PREDICATE;
import static io.rxmicro.annotation.processor.data.sql.model.CommonSQLGroupRules.ISOLATION_LEVEL_GROUP;
import static io.rxmicro.annotation.processor.data.sql.model.CommonSQLGroupRules.ISOLATION_LEVEL_PREDICATE;
import static io.rxmicro.data.sql.model.TransactionType.SUPPORTED_TRANSACTION_TYPES;
import static java.util.stream.Collectors.joining;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public class CreateTransactionSQLRepositoryMethodModelBuilder<DMF extends SQLDataModelField, DMC extends SQLDataObjectModelClass<DMF>>
        extends AbstractSQLDataRepositoryMethodModelBuilder<DMF, DMC> {

    private final Map<String, Predicate<VariableElement>> groupRules = Map.of(
            REQUEST_ID_SUPPLIER_GROUP, REQUEST_ID_SUPPLIER_PREDICATE,
            ISOLATION_LEVEL_GROUP, ISOLATION_LEVEL_PREDICATE
    );

    @Inject
    private DataMethodParamsResolver dataMethodParamsResolver;

    @Override
    public boolean isSupported(final DataRepositoryMethodSignature dataRepositoryMethodSignature,
                               final DataGenerationContext<DMF, DMC> dataGenerationContext) {
        return SUPPORTED_TRANSACTION_TYPES.stream().anyMatch(t -> dataRepositoryMethodSignature.getMethodResult().isResultType(t));
    }

    @Override
    public Class<? extends Annotation> operationType() {
        return CreateTransaction.class;
    }

    @Override
    protected MethodBody buildBody(final ClassHeader.Builder classHeaderBuilder,
                                   final ExecutableElement method,
                                   final MethodResult methodResult,
                                   final DataRepositoryGeneratorConfig dataRepositoryGeneratorConfig,
                                   final DataGenerationContext<DMF, DMC> dataGenerationContext) {
        classHeaderBuilder.addImports(Mono.class);
        final DataMethodParams dataMethodParams = dataMethodParamsResolver.resolve(method, groupRules);
        validateCommonDataMethodParams(method, dataMethodParams);

        final TransactionType transactionType = TransactionType.byClassName(methodResult.getResultType().toString());
        validateRequiredSingleReturnType(method, methodResult);
        validateTransactionType(method, methodResult, transactionType);

        final Map<String, Object> templateArguments = new HashMap<>();
        templateArguments.put("RETURN", methodResult);

        dataMethodParams.getSingleParamOfGroup(ISOLATION_LEVEL_GROUP).ifPresent(v ->
                templateArguments.put("ISOLATION_LEVEL", v));
        templateArguments.put(
                "CONNECTION_CREATE_PARAM",
                dataMethodParams.getSingleParamOfGroup(REQUEST_ID_SUPPLIER_GROUP).map(Variable::getName).orElse("")
        );
        return new SQLMethodBody(
                methodBodyGenerator.generate(
                        "data/sql/r2dbc/method/$$SQLRepositoryCreateTransactionMethodBodyTemplate.javaftl",
                        templateArguments));
    }

    private void validateCommonDataMethodParams(final ExecutableElement method,
                                                final DataMethodParams dataMethodParams) {
        final List<Variable> requestIdSupplierParams = dataMethodParams.getParamsOfGroup(REQUEST_ID_SUPPLIER_GROUP);
        if (requestIdSupplierParams.size() > 1) {
            throw new InterruptProcessingException(
                    requestIdSupplierParams.get(1).getElement(),
                    "Only one parameter of '?' type is allowed per method. Remove the redundant parameter(s): ?",
                    RequestIdSupplier.class.getName(),
                    requestIdSupplierParams.stream().skip(1).map(Variable::getName).collect(joining(", "))
            );
        }
        final List<Variable> isolationLevelParams = dataMethodParams.getParamsOfGroup(ISOLATION_LEVEL_GROUP);
        if (isolationLevelParams.size() > 1) {
            throw new InterruptProcessingException(
                    isolationLevelParams.get(1).getElement(),
                    "Only one parameter of '?' type is allowed per method. Remove the redundant parameter(s): ?",
                    IsolationLevel.class.getName(),
                    isolationLevelParams.stream().skip(1).map(Variable::getName).collect(joining(", "))
            );
        }
        for (final Variable param : dataMethodParams.getOtherParams()) {
            throw new InterruptProcessingException(
                    method,
                    "Unsupported method parameter: '?'. " +
                            "'?' method can contain only following types of parameters: ?! Remove unsupported parameter!",
                    param.getName(), method.getSimpleName(), Set.of(IsolationLevel.class.getName(), RequestIdSupplier.class.getName())
            );
        }
    }

    private void validateTransactionType(final ExecutableElement repositoryMethod,
                                         final MethodResult methodResult,
                                         final TransactionType transactionType) {
        if (methodResult.isMono()) {
            if (transactionType != TransactionType.REACTOR) {
                throw new InterruptProcessingException(
                        repositoryMethod,
                        "Invalid transaction type: '?'! Use '?' instead!",
                        methodResult.getResultType(),
                        io.rxmicro.data.sql.model.reactor.Transaction.class.getName()
                );
            }
        } else if (methodResult.isSingle()) {
            if (transactionType != TransactionType.RX_JAVA_3) {
                throw new InterruptProcessingException(
                        repositoryMethod,
                        "Invalid transaction type: '?'! Use '?' instead!",
                        methodResult.getResultType(),
                        io.rxmicro.data.sql.model.rxjava3.Transaction.class.getName()
                );
            }
        } else if (methodResult.isFuture()) {
            if (transactionType != TransactionType.COMPLETABLE_FUTURE) {
                throw new InterruptProcessingException(
                        repositoryMethod,
                        "Invalid transaction type: '?'! Use '?' instead!",
                        methodResult.getResultType(),
                        io.rxmicro.data.sql.model.completablefuture.Transaction.class.getName()
                );
            }
        } else {
            throw new InterruptProcessingException(repositoryMethod, "Unsupported method result: ?", methodResult);
        }
    }

    /**
     * @author nedis
     * @since 0.1
     */
    private @interface CreateTransaction {
    }
}
