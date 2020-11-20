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

package io.rxmicro.annotation.processor.data.sql.r2dbc.postgresql.component.impl.method;

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.common.model.method.MethodResult;
import io.rxmicro.annotation.processor.data.model.DataGenerationContext;
import io.rxmicro.annotation.processor.data.model.DataMethodParams;
import io.rxmicro.annotation.processor.data.model.Variable;
import io.rxmicro.annotation.processor.data.sql.model.ParsedSQL;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataModelField;
import io.rxmicro.annotation.processor.data.sql.model.SQLMethodDescriptor;
import io.rxmicro.annotation.processor.data.sql.model.SQLStatement;
import io.rxmicro.annotation.processor.data.sql.r2dbc.component.impl.AbstractSQLModificationOperationReturningResultDataRepositoryMethodModelBuilder;
import io.rxmicro.annotation.processor.data.sql.r2dbc.postgresql.model.PostgreSQLDataObjectModelClass;
import io.rxmicro.data.sql.operation.Update;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.lang.model.element.ExecutableElement;

import static io.rxmicro.annotation.processor.data.sql.model.SQLKeywords.UPDATE;
import static io.rxmicro.annotation.processor.data.sql.r2dbc.postgresql.model.PostgreSQLKeywords.RETURNING;
import static io.rxmicro.data.sql.operation.Update.DEFAULT_UPDATE;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class PostgreSQLUpdateWithReturningSQLRepositoryMethodModelBuilder
        extends AbstractSQLModificationOperationReturningResultDataRepositoryMethodModelBuilder
        <Update, SQLDataModelField, PostgreSQLDataObjectModelClass> {

    @Override
    protected void validateMethod(final ParsedSQL<Update> parsedSQL,
                                  final MethodResult methodResult,
                                  final DataGenerationContext<SQLDataModelField, PostgreSQLDataObjectModelClass> dataGenerationContext,
                                  final ExecutableElement method,
                                  final DataMethodParams dataMethodParams) {
        super.validateMethod(parsedSQL, methodResult, dataGenerationContext, method, dataMethodParams);
        if (parsedSQL.doesNotContain(RETURNING)) {
            throw new InterruptProcessingException(method, "Missing '?' keyword in the '?' statement", RETURNING, UPDATE);
        }
        validateThatEntityContainsPrimaryKeyIfParamIsEntity(dataGenerationContext, method, dataMethodParams.getOtherParams());
    }

    @Override
    protected ParsedSQL<Update> parseSQL(final ExecutableElement method) {
        final Update annotation = method.getAnnotation(Update.class);
        final String originalSQL = Optional.of(annotation.value()).filter(v -> !v.isEmpty()).orElse(DEFAULT_UPDATE);
        return parseSQL(originalSQL, annotation);
    }

    @Override
    protected String getTemplateName() {
        return "data/sql/r2dbc/postgresql/method/$$PostgreSQLRepositoryUpdateWithReturningMethodBodyTemplate.javaftl";
    }

    @Override
    public Class<? extends Annotation> operationType() {
        return Update.class;
    }

    @Override
    protected void addEntityConverter(final MethodResult methodResult,
                                      final SQLMethodDescriptor<SQLDataModelField, PostgreSQLDataObjectModelClass> sqlMethodDescriptor,
                                      final DataGenerationContext<SQLDataModelField, PostgreSQLDataObjectModelClass> dataGenerationContext,
                                      final List<Variable> params,
                                      final SQLStatement sqlStatement,
                                      final Map<String, Object> templateArguments) {
        super.addEntityConverter(methodResult, sqlMethodDescriptor, dataGenerationContext, params, sqlStatement, templateArguments);
        sqlMethodDescriptor.getEntityParam().ifPresent(modelClass -> {
            modelClass.setUpdatable(true);
        });
    }
}
