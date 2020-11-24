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

package io.rxmicro.annotation.processor.data.sql.r2dbc.component.impl;

import com.google.inject.Inject;
import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.common.model.method.MethodBody;
import io.rxmicro.annotation.processor.common.model.method.MethodResult;
import io.rxmicro.annotation.processor.data.component.DataMethodParamsResolver;
import io.rxmicro.annotation.processor.data.model.DataGenerationContext;
import io.rxmicro.annotation.processor.data.model.DataMethodParams;
import io.rxmicro.annotation.processor.data.model.Variable;
import io.rxmicro.annotation.processor.data.sql.component.SQLBuilder;
import io.rxmicro.annotation.processor.data.sql.component.impl.AbstractSQLDataRepositoryMethodModelBuilder;
import io.rxmicro.annotation.processor.data.sql.model.ParsedSQL;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataModelField;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataObjectModelClass;
import io.rxmicro.annotation.processor.data.sql.model.SQLMethodBody;
import io.rxmicro.annotation.processor.data.sql.model.SQLMethodDescriptor;
import io.rxmicro.annotation.processor.data.sql.model.SQLStatement;
import io.rxmicro.data.DataRepositoryGeneratorConfig;
import io.rxmicro.data.sql.model.EntityFieldList;
import io.rxmicro.data.sql.model.EntityFieldMap;
import io.rxmicro.data.sql.model.reactor.Transaction;
import io.rxmicro.logger.RequestIdSupplier;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;

import static io.rxmicro.annotation.processor.data.model.CommonDataGroupRules.REQUEST_ID_SUPPLIER_GROUP;
import static io.rxmicro.annotation.processor.data.model.CommonDataGroupRules.REQUEST_ID_SUPPLIER_PREDICATE;
import static io.rxmicro.annotation.processor.data.sql.model.CommonSQLGroupRules.CUSTOM_SELECT_GROUP;
import static io.rxmicro.annotation.processor.data.sql.model.CommonSQLGroupRules.CUSTOM_SELECT_PREDICATE;
import static io.rxmicro.annotation.processor.data.sql.model.CommonSQLGroupRules.TRANSACTION_GROUP;
import static io.rxmicro.annotation.processor.data.sql.model.CommonSQLGroupRules.TRANSACTION_PREDICATE;

/**
 * @author nedis
 * @since 0.1
 */
public abstract class AbstractSQLOperationDataRepositoryMethodModelBuilder
        <A extends Annotation, DMF extends SQLDataModelField, DMC extends SQLDataObjectModelClass<DMF>>
        extends AbstractSQLDataRepositoryMethodModelBuilder<DMF, DMC> {

    private final Map<String, Predicate<VariableElement>> groupRules = Map.of(
            REQUEST_ID_SUPPLIER_GROUP, REQUEST_ID_SUPPLIER_PREDICATE,
            TRANSACTION_GROUP, TRANSACTION_PREDICATE,
            CUSTOM_SELECT_GROUP, CUSTOM_SELECT_PREDICATE
    );

    @Inject
    private DataMethodParamsResolver dataMethodParamsResolver;

    @Inject
    private SQLBuilder<A, DMF, DMC> sqlBuilder;

    @Override
    protected boolean isEntityResultReturn(final DataGenerationContext<DMF, DMC> dataGenerationContext,
                                           final MethodResult methodResult) {
        return dataGenerationContext.isEntityResultType(methodResult.getResultType()) ||
                dataGenerationContext.isEntityParamType(methodResult.getResultType()) ||
                methodResult.isResultType(EntityFieldList.class) ||
                methodResult.isResultType(EntityFieldMap.class);
    }

    @Override
    protected final MethodBody buildBody(final ClassHeader.Builder classHeaderBuilder,
                                         final ExecutableElement method,
                                         final MethodResult methodResult,
                                         final DataRepositoryGeneratorConfig dataRepositoryGeneratorConfig,
                                         final DataGenerationContext<DMF, DMC> dataGenerationContext) {
        final DataMethodParams dataMethodParams = dataMethodParamsResolver.resolve(method, groupRules);
        validateCommonDataMethodParams(dataMethodParams);
        final List<Variable> params = dataMethodParams.getOtherParams();
        final SQLMethodDescriptor<DMF, DMC> sqlMethodDescriptor =
                buildSQLMethodDescriptor(method, params, methodResult, dataGenerationContext);

        final ParsedSQL<A> parsedSQL = parseSQL(method);
        validateMethod(parsedSQL, methodResult, dataGenerationContext, method, dataMethodParams);

        final Map<String, Object> templateArguments = new HashMap<>();
        putCommonArguments(dataRepositoryGeneratorConfig, templateArguments);
        templateArguments.put("RETURN", methodResult);
        templateArguments.put("RETURN_ENTITY_FIELD_MAP", methodResult.isResultType(EntityFieldMap.class));
        templateArguments.put("RETURN_ENTITY_FIELD_LIST", methodResult.isResultType(EntityFieldList.class));
        final SQLStatement sqlStatement = sqlBuilder.build(classHeaderBuilder, parsedSQL, method, sqlMethodDescriptor);
        customizeClassHeaderBuilder(classHeaderBuilder, methodResult, dataGenerationContext, method, sqlStatement);
        templateArguments.put("SQL", sqlStatement);
        addEntityConverter(methodResult, sqlMethodDescriptor, dataGenerationContext, params, sqlStatement, templateArguments);

        dataMethodParams.getSingleParamOfGroup(TRANSACTION_GROUP).ifPresent(t ->
                templateArguments.put("TRANSACTION", t.getName()));
        templateArguments.put(
                "CONNECTION_CREATE_PARAM",
                dataMethodParams.getSingleParamOfGroup(REQUEST_ID_SUPPLIER_GROUP).map(Variable::getName).orElse("")
        );
        return new SQLMethodBody(methodBodyGenerator.generate(getTemplateName(), templateArguments));
    }

    protected void validateCommonDataMethodParams(final DataMethodParams dataMethodParams) {
        final List<Variable> requestIdSupplierParams = dataMethodParams.getParamsOfGroup(REQUEST_ID_SUPPLIER_GROUP);
        if (requestIdSupplierParams.size() > 1) {
            throw createNotUniqueParameterException(requestIdSupplierParams, 1, RequestIdSupplier.class);
        }
        final List<Variable> transactionParams = dataMethodParams.getParamsOfGroup(TRANSACTION_GROUP);
        if (transactionParams.size() > 1) {
            throw createNotUniqueParameterException(transactionParams, 1, Transaction.class);
        }
        if (!transactionParams.isEmpty() && !requestIdSupplierParams.isEmpty()) {
            throw new InterruptProcessingException(
                    requestIdSupplierParams.get(0).getElement(),
                    "'?' parameter is redundant. The request id supplier must be bind to the transaction object. For example: " +
                            "'ReactiveType<Transaction> beginTransaction(RequestIdSupplier requestIdSupplier);' " +
                            "Remove this parameter!",
                    requestIdSupplierParams.get(0).getName()
            );
        }
        validatePageableParameter(dataMethodParams);
    }

    protected void customizeClassHeaderBuilder(final ClassHeader.Builder classHeaderBuilder,
                                               final MethodResult methodResult,
                                               final DataGenerationContext<DMF, DMC> dataGenerationContext,
                                               final ExecutableElement method,
                                               final SQLStatement sqlStatement) {
        classHeaderBuilder.addImports(Mono.class, Flux.class);
    }

    protected abstract void validateMethod(ParsedSQL<A> parsedSQL,
                                           MethodResult methodResult,
                                           DataGenerationContext<DMF, DMC> dataGenerationContext,
                                           ExecutableElement method,
                                           DataMethodParams dataMethodParams);

    protected abstract ParsedSQL<A> parseSQL(ExecutableElement method);

    protected abstract void addEntityConverter(MethodResult methodResult,
                                               SQLMethodDescriptor<DMF, DMC> sqlMethodDescriptor,
                                               DataGenerationContext<DMF, DMC> dataGenerationContext,
                                               List<Variable> params,
                                               SQLStatement sqlStatement,
                                               Map<String, Object> templateArguments);

    protected abstract String getTemplateName();
}
