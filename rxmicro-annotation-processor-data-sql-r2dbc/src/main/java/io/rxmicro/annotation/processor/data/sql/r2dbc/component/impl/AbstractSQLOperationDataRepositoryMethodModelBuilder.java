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
import io.rxmicro.annotation.processor.common.model.method.MethodBody;
import io.rxmicro.annotation.processor.common.model.method.MethodResult;
import io.rxmicro.annotation.processor.data.model.DataGenerationContext;
import io.rxmicro.annotation.processor.data.model.Variable;
import io.rxmicro.annotation.processor.data.sql.component.SQLBuilder;
import io.rxmicro.annotation.processor.data.sql.component.impl.AbstractSQLDataRepositoryMethodModelBuilder;
import io.rxmicro.annotation.processor.data.sql.component.impl.MethodParamResolver;
import io.rxmicro.annotation.processor.data.sql.model.ParsedSQL;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataModelField;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataObjectModelClass;
import io.rxmicro.annotation.processor.data.sql.model.SQLMethodBody;
import io.rxmicro.annotation.processor.data.sql.model.SQLMethodDescriptor;
import io.rxmicro.annotation.processor.data.sql.model.SQLStatement;
import io.rxmicro.data.DataRepositoryGeneratorConfig;
import io.rxmicro.data.sql.model.EntityFieldList;
import io.rxmicro.data.sql.model.EntityFieldMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.lang.model.element.ExecutableElement;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public abstract class AbstractSQLOperationDataRepositoryMethodModelBuilder<A extends Annotation, DMF extends SQLDataModelField, DMC extends SQLDataObjectModelClass<DMF>>
        extends AbstractSQLDataRepositoryMethodModelBuilder<DMF, DMC> {

    @Inject
    private MethodParamResolver methodParamResolver;

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
    protected MethodBody buildBody(final ClassHeader.Builder classHeaderBuilder,
                                   final ExecutableElement method, final MethodResult methodResult,
                                   final DataRepositoryGeneratorConfig dataRepositoryGeneratorConfig,
                                   final DataGenerationContext<DMF, DMC> dataGenerationContext) {
        customizeClassHeaderBuilder(classHeaderBuilder, methodResult, dataGenerationContext, method);

        final List<Variable> params = methodParamResolver.getMethodParams(method.getParameters());
        final SQLMethodDescriptor<DMF, DMC> sqlMethodDescriptor =
                buildSQLMethodDescriptor(method, params, methodResult, dataGenerationContext);

        final ParsedSQL<A> parsedSQL = parseSQL(method);
        validateMethod(parsedSQL, methodResult, dataGenerationContext, method, params);

        final Map<String, Object> templateArguments = new HashMap<>();
        putCommonArguments(dataRepositoryGeneratorConfig, templateArguments);
        templateArguments.put("RETURN", methodResult);
        templateArguments.put("RETURN_ENTITY_FIELD_MAP", methodResult.isResultType(EntityFieldMap.class));
        templateArguments.put("RETURN_ENTITY_FIELD_LIST", methodResult.isResultType(EntityFieldList.class));
        final SQLStatement sqlStatement = sqlBuilder.build(classHeaderBuilder, parsedSQL, method, sqlMethodDescriptor);
        templateArguments.put("SQL", sqlStatement);
        addEntityConverter(methodResult, sqlMethodDescriptor, dataGenerationContext, params, sqlStatement, templateArguments);
        getTransactionMethodParameter(method).ifPresent(t -> templateArguments.put("TRANSACTION", t));

        return new SQLMethodBody(
                methodBodyGenerator.generate(
                        getTemplateName(),
                        templateArguments)
        );
    }

    protected void customizeClassHeaderBuilder(final ClassHeader.Builder classHeaderBuilder,
                                               final MethodResult methodResult,
                                               final DataGenerationContext<DMF, DMC> dataGenerationContext,
                                               final ExecutableElement method) {
        classHeaderBuilder
                .addImports(
                        Mono.class,
                        Flux.class
                );
    }

    protected abstract void validateMethod(final ParsedSQL<A> parsedSQL,
                                           final MethodResult methodResult,
                                           final DataGenerationContext<DMF, DMC> dataGenerationContext,
                                           final ExecutableElement method,
                                           final List<Variable> params);

    protected abstract ParsedSQL<A> parseSQL(ExecutableElement method);

    protected abstract void addEntityConverter(MethodResult methodResult,
                                               SQLMethodDescriptor<DMF, DMC> sqlMethodDescriptor,
                                               DataGenerationContext<DMF, DMC> dataGenerationContext,
                                               List<Variable> params,
                                               SQLStatement sqlStatement,
                                               Map<String, Object> templateArguments);

    protected abstract String getTemplateName();
}
