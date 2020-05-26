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

package io.rxmicro.annotation.processor.data.sql.component.impl;

import com.google.inject.Inject;
import io.rxmicro.annotation.processor.common.component.TokenParser;
import io.rxmicro.annotation.processor.common.model.TokenParserRule;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.common.model.method.MethodBody;
import io.rxmicro.annotation.processor.common.model.method.MethodResult;
import io.rxmicro.annotation.processor.data.component.impl.AbstractDataRepositoryMethodModelBuilder;
import io.rxmicro.annotation.processor.data.model.DataGenerationContext;
import io.rxmicro.annotation.processor.data.model.DataRepositoryMethodSignature;
import io.rxmicro.annotation.processor.data.model.Variable;
import io.rxmicro.annotation.processor.data.sql.component.SQLRepositoryMethodModelBuilder;
import io.rxmicro.annotation.processor.data.sql.model.ParsedSQL;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataModelField;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataObjectModelClass;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataRepositoryMethod;
import io.rxmicro.annotation.processor.data.sql.model.SQLMethodDescriptor;
import io.rxmicro.annotation.processor.data.sql.model.SQLTokenParserRule;
import io.rxmicro.data.sql.model.EntityFieldList;
import io.rxmicro.data.sql.model.EntityFieldMap;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;
import javax.lang.model.element.ExecutableElement;

import static io.rxmicro.annotation.processor.common.util.Elements.asTypeElement;

/**
 * @author nedis
 * @since 0.1
 */
public abstract class AbstractSQLDataRepositoryMethodModelBuilder<DMF extends SQLDataModelField, DMC extends SQLDataObjectModelClass<DMF>>
        extends AbstractDataRepositoryMethodModelBuilder<DMF, SQLDataRepositoryMethod, DMC>
        implements SQLRepositoryMethodModelBuilder<DMF, DMC> {

    @Inject
    private TransactionResolver transactionResolver;

    @Inject
    private TokenParser tokenParser;

    @Inject
    @SQLTokenParserRule
    private TokenParserRule tokenParserRule;

    @Override
    protected final SQLDataRepositoryMethod build(final DataRepositoryMethodSignature dataRepositoryMethodSignature,
                                                  final MethodBody body) {
        return new SQLDataRepositoryMethod(
                dataRepositoryMethodSignature,
                body
        );
    }

    protected final SQLMethodDescriptor<DMF, DMC> buildSQLMethodDescriptor(final ExecutableElement method,
                                                                           final List<Variable> methodParams,
                                                                           final MethodResult methodResult,
                                                                           final DataGenerationContext<DMF, DMC> dataGenerationContext) {
        final SQLMethodDescriptor.Builder<DMF, DMC> builder = new SQLMethodDescriptor.Builder<>(
                dataGenerationContext.getCurrentModule(),
                methodParams,
                methodResult
        );
        methodParams.stream()
                .flatMap(v -> asTypeElement(v.getType())
                        .flatMap(t -> Optional.ofNullable(dataGenerationContext.getEntityParamMap().get(t))).stream())
                .forEach(modelClass -> {
                    if (builder.isEntityParamSet()) {
                        throw new InterruptProcessingException(
                                method,
                                "Repository method does not support multi entity params. Remove param of type: ?",
                                modelClass.getModelTypeElement().getQualifiedName()
                        );
                    }
                    builder.setEntityParam(modelClass);
                });
        asTypeElement(methodResult.getResultType())
                .flatMap(t -> Optional.ofNullable(dataGenerationContext.getEntityReturnMap().get(t)))
                .ifPresent(builder::setEntityResult);
        final SQLMethodDescriptor<DMF, DMC> sqlMethodDescriptor = builder.build();
        validate(method, sqlMethodDescriptor);
        return sqlMethodDescriptor;
    }

    protected final <A extends Annotation> ParsedSQL<A> parseSQL(final String sql, final A annotation) {
        final List<String> sqlTokens;
        if (sql.isEmpty()) {
            sqlTokens = List.of();
        } else {
            sqlTokens = tokenParser.parse(sql, tokenParserRule, false).getTokens();
        }
        return new ParsedSQL<>(annotation, sqlTokens);
    }

    protected final boolean isEntityParam(final List<Variable> params,
                                          final DataGenerationContext<DMF, DMC> dataGenerationContext) {
        if (params.size() != 1) {
            return false;
        } else {
            return dataGenerationContext.isEntityParamType(params.get(0).getType());
        }
    }

    protected final void validateThatEntityContainsPrimaryKeyIfParamIsEntity(final DataGenerationContext<DMF, DMC> dataGenerationContext,
                                                                             final ExecutableElement method,
                                                                             final List<Variable> params) {
        final boolean isEntityParam = isEntityParam(params, dataGenerationContext);
        if (isEntityParam) {
            final DMC modelClass = dataGenerationContext.getEntityParamMap().get(asTypeElement(params.get(0).getType()).orElseThrow());
            if (modelClass.getPrimaryKeysParams().isEmpty()) {
                throw new InterruptProcessingException(
                        method,
                        "Can't generate method body, because '?' entity class does not contain primary keys!",
                        params.get(0).getType()
                );
            }
        }
    }

    protected boolean isEntityResultReturn(final DataGenerationContext<DMF, DMC> dataGenerationContext,
                                           final MethodResult methodResult) {
        return dataGenerationContext.isEntityResultType(methodResult.getResultType()) ||
                dataGenerationContext.isEntityParamType(methodResult.getResultType()) ||
                methodResult.isResultType(EntityFieldList.class) ||
                methodResult.isResultType(EntityFieldMap.class);
    }

    protected final Optional<String> getTransactionMethodParameter(final ExecutableElement method) {
        return transactionResolver.getTransactionParameter(method).map(Variable::getGetter);
    }

    private void validate(final ExecutableElement method,
                          final SQLMethodDescriptor<DMF, DMC> sqlMethodDescriptor) {
        if (sqlMethodDescriptor.getEntityParam().isPresent() && sqlMethodDescriptor.getParams().size() > 1) {
            throw new InterruptProcessingException(method, "Repository method couldn't contain any parameters with entity parameter");
        }
        if (sqlMethodDescriptor.getEntityParam().isPresent() &&
                sqlMethodDescriptor.getEntityResult().isPresent() &&
                !sqlMethodDescriptor.getEntityParam().get().getJavaFullClassName().equals(
                        sqlMethodDescriptor.getEntityResult().get().getJavaFullClassName())) {
            throw new InterruptProcessingException(method, "An entity parameter class must be equals to the entity result class");
        }
    }
}
