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
import io.rxmicro.annotation.processor.data.sql.model.EntitySetFieldsConverterMethod;
import io.rxmicro.annotation.processor.data.sql.model.ParsedSQL;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataModelField;
import io.rxmicro.annotation.processor.data.sql.model.SQLMethodDescriptor;
import io.rxmicro.annotation.processor.data.sql.model.SQLStatement;
import io.rxmicro.annotation.processor.data.sql.r2dbc.component.impl.AbstractSQLModificationOperationReturningResultDataRepositoryMethodModelBuilder;
import io.rxmicro.annotation.processor.data.sql.r2dbc.postgresql.model.PostgreSQLDataObjectModelClass;
import io.rxmicro.annotation.processor.data.sql.r2dbc.postgresql.model.PostgreSQLKeywords;
import io.rxmicro.data.sql.model.EntityFieldList;
import io.rxmicro.data.sql.model.EntityFieldMap;
import io.rxmicro.data.sql.operation.Delete;
import io.rxmicro.data.sql.r2dbc.detail.EntityFromR2DBCSQLDBConverter;
import io.rxmicro.data.sql.r2dbc.detail.EntityToR2DBCSQLDBConverter;

import javax.lang.model.element.ExecutableElement;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

import static io.rxmicro.annotation.processor.common.util.Errors.createInternalErrorSupplier;
import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getModelTransformerInstanceName;
import static io.rxmicro.annotation.processor.common.util.Names.getSimpleName;
import static io.rxmicro.annotation.processor.data.sql.model.SQLKeywords.INSERT;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@Singleton
public final class PostgreSQLDeleteWithReturningSQLRepositoryMethodModelBuilder
        extends AbstractSQLModificationOperationReturningResultDataRepositoryMethodModelBuilder<Delete, SQLDataModelField, PostgreSQLDataObjectModelClass> {

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
        validateThatEntityContainsPrimaryKeyIfCurrentParamIsEntity(dataGenerationContext, method, params);
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
    protected void addEntityConverter(final MethodResult methodResult,
                                      final SQLMethodDescriptor<SQLDataModelField, PostgreSQLDataObjectModelClass> sqlMethodDescriptor,
                                      final DataGenerationContext<SQLDataModelField, PostgreSQLDataObjectModelClass> dataGenerationContext,
                                      final List<Variable> params,
                                      final SQLStatement sqlStatement,
                                      final Map<String, Object> templateArguments) {
        sqlMethodDescriptor.getEntityParam().ifPresent(modelClass -> {
            modelClass.setDeletable(true);
            templateArguments.put("IS_PRIMARY_KEY_SIMPLE", modelClass.getPrimaryKeysParams().size() == 1);
        });
        final boolean isEntityParam = isEntityParam(params, dataGenerationContext);
        final boolean isEntityFieldMap = sqlMethodDescriptor.getResult().isResultType(EntityFieldMap.class);
        final boolean isEntityFieldList = sqlMethodDescriptor.getResult().isResultType(EntityFieldList.class);
        templateArguments.put("IS_ENTITY_PARAM", isEntityParam);
        templateArguments.put("RETURN_ENTITY_FIELD_MAP", isEntityFieldMap);
        templateArguments.put("RETURN_ENTITY_FIELD_LIST", isEntityFieldList);
        if (isEntityParam) {
            templateArguments.put("ENTITY", params.get(0).getGetter());
            templateArguments.put("ENTITY_CONVERTER", getModelTransformerInstanceName(
                    params.get(0).getType(),
                    EntityToR2DBCSQLDBConverter.class)
            );
        }
        if (!isEntityFieldList && !isEntityFieldMap) {
            final PostgreSQLDataObjectModelClass modelClass = sqlMethodDescriptor.getEntityResult()
                    .orElseThrow(createInternalErrorSupplier("PostgreSQLDataObjectModelClass method return result not found for DELETE operation"));
            final String entityClass = getSimpleName(modelClass.getJavaFullClassName());
            templateArguments.put("ENTITY_CLASS", entityClass);
            if (!isEntityParam) {
                templateArguments.put("ENTITY_CONVERTER", getModelTransformerInstanceName(
                        entityClass,
                        EntityFromR2DBCSQLDBConverter.class)
                );
            }
            final EntitySetFieldsConverterMethod converterMethod = new EntitySetFieldsConverterMethod(sqlStatement);
            modelClass.addEntitySetFieldsConverterMethod(converterMethod);
            templateArguments.put("ENTITY_CONVERTER_METHOD", converterMethod.getName());
        }
    }

    @Override
    protected String getTemplateName() {
        return "data/sql/r2dbc/postgresql/method/$$PostgreSQLRepositoryDeleteWithReturningMethodBodyTemplate.javaftl";
    }

    @Override
    public Class<? extends Annotation> operationType() {
        return Delete.class;
    }
}
