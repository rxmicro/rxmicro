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
import io.rxmicro.annotation.processor.data.model.Variable;
import io.rxmicro.annotation.processor.data.sql.model.ParsedSQL;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataModelField;
import io.rxmicro.annotation.processor.data.sql.model.SQLMethodDescriptor;
import io.rxmicro.annotation.processor.data.sql.model.SQLStatement;
import io.rxmicro.annotation.processor.data.sql.r2dbc.component.impl.AbstractSQLModificationOperationReturningResultDataRepositoryMethodModelBuilder;
import io.rxmicro.annotation.processor.data.sql.r2dbc.postgresql.model.PostgreSQLDataObjectModelClass;
import io.rxmicro.annotation.processor.data.sql.r2dbc.postgresql.model.PostgreSQLKeywords;
import io.rxmicro.data.sql.operation.Delete;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import javax.lang.model.element.ExecutableElement;

import static io.rxmicro.annotation.processor.data.sql.model.SQLKeywords.INSERT;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class PostgreSQLDeleteWithReturningSQLRepositoryMethodModelBuilder
        extends AbstractSQLModificationOperationReturningResultDataRepositoryMethodModelBuilder
        <Delete, SQLDataModelField, PostgreSQLDataObjectModelClass> {

    @Override
    protected void validateMethod(final ParsedSQL<Delete> parsedSQL,
                                  final MethodResult methodResult,
                                  final DataGenerationContext<SQLDataModelField, PostgreSQLDataObjectModelClass> dataGenerationContext,
                                  final ExecutableElement method,
                                  final List<Variable> params) {
        if (parsedSQL.doesNotContain(PostgreSQLKeywords.RETURNING)) {
            throw new InterruptProcessingException(method,
                    "Missing '?' keyword in the '?' statement", PostgreSQLKeywords.RETURNING, INSERT);
        }
        validateThatEntityContainsPrimaryKeyIfParamIsEntity(dataGenerationContext, method, params);
    }

    @Override
    protected ParsedSQL<Delete> parseSQL(final ExecutableElement method) {
        final Delete annotation = method.getAnnotation(Delete.class);
        final ParsedSQL<Delete> parsedSQL = parseSQL(annotation.value(), annotation);
        if (parsedSQL.getSqlTokens().isEmpty()) {
            throw new InterruptProcessingException(method, "Missing a delete SQL statement. Add it!");
        }
        return parsedSQL;
    }

    @Override
    protected String getTemplateName() {
        return "data/sql/r2dbc/postgresql/method/$$PostgreSQLRepositoryDeleteWithReturningMethodBodyTemplate.javaftl";
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
            modelClass.setDeletable(true);
            templateArguments.put("IS_PRIMARY_KEY_SIMPLE", modelClass.getPrimaryKeysParams().size() == 1);
        });
    }

    @Override
    public Class<? extends Annotation> operationType() {
        return Delete.class;
    }
}
