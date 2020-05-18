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

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.common.model.method.MethodBody;
import io.rxmicro.annotation.processor.common.model.method.MethodResult;
import io.rxmicro.annotation.processor.data.model.DataGenerationContext;
import io.rxmicro.annotation.processor.data.model.DataRepositoryMethodSignature;
import io.rxmicro.annotation.processor.data.sql.component.impl.AbstractSQLDataRepositoryMethodModelBuilder;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataModelField;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataObjectModelClass;
import io.rxmicro.annotation.processor.data.sql.model.SQLMethodBody;
import io.rxmicro.data.DataRepositoryGeneratorConfig;
import io.rxmicro.data.sql.model.IsolationLevel;
import io.rxmicro.data.sql.model.TransactionType;
import reactor.core.publisher.Mono;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;

import static io.rxmicro.data.sql.model.TransactionType.SUPPORTED_TRANSACTION_TYPES;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public class CreateTransactionSQLRepositoryMethodModelBuilder<DMF extends SQLDataModelField, DMC extends SQLDataObjectModelClass<DMF>>
        extends AbstractSQLDataRepositoryMethodModelBuilder<DMF, DMC> {

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
                                   final ExecutableElement repositoryMethod, final MethodResult methodResult,
                                   final DataRepositoryGeneratorConfig dataRepositoryGeneratorConfig,
                                   final DataGenerationContext<DMF, DMC> dataGenerationContext) {
        classHeaderBuilder.addImports(Mono.class);
        final TransactionType transactionType = TransactionType.byClassName(methodResult.getResultType().toString());
        validateRequiredSingleReturnType(repositoryMethod, methodResult);
        validateTransactionType(repositoryMethod, methodResult, transactionType);

        final Map<String, Object> templateArguments = new HashMap<>();
        templateArguments.put("RETURN", methodResult);
        getIsolationLevelParameter(repositoryMethod).ifPresent(v ->
                templateArguments.put("ISOLATION_LEVEL", v.getSimpleName().toString()));

        return new SQLMethodBody(
                methodBodyGenerator.generate(
                        "data/sql/r2dbc/method/$$SQLRepositoryCreateTransactionMethodBodyTemplate.javaftl",
                        templateArguments));
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

    private Optional<VariableElement> getIsolationLevelParameter(final ExecutableElement repositoryMethod) {
        final List<? extends VariableElement> parameters = repositoryMethod.getParameters();
        if (parameters.isEmpty()) {
            return Optional.empty();
        } else if (parameters.size() == 1) {
            final VariableElement param = parameters.get(0);
            if (param.asType().toString().equals(IsolationLevel.class.getName())) {
                return Optional.of(param);
            }
        }
        throw new InterruptProcessingException(
                repositoryMethod,
                "Method can contains no parameters or one parameter of '?' type only",
                IsolationLevel.class.getName()
        );
    }

    /**
     * @author nedis
     * @since 0.1
     */
    private @interface CreateTransaction {
    }
}
