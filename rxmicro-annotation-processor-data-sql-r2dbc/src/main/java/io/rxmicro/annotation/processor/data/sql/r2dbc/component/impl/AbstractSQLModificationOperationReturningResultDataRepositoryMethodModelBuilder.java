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

import io.reactivex.rxjava3.core.Flowable;
import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.common.model.method.MethodResult;
import io.rxmicro.annotation.processor.data.model.DataGenerationContext;
import io.rxmicro.annotation.processor.data.model.DataMethodParams;
import io.rxmicro.annotation.processor.data.model.DataRepositoryMethodSignature;
import io.rxmicro.annotation.processor.data.model.Variable;
import io.rxmicro.annotation.processor.data.sql.model.EntitySetFieldsConverterMethod;
import io.rxmicro.annotation.processor.data.sql.model.ParsedSQL;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataModelField;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataObjectModelClass;
import io.rxmicro.annotation.processor.data.sql.model.SQLMethodDescriptor;
import io.rxmicro.annotation.processor.data.sql.model.SQLStatement;
import io.rxmicro.data.sql.ExpectedUpdatedRowsCount;
import io.rxmicro.data.sql.model.EntityFieldList;
import io.rxmicro.data.sql.model.EntityFieldMap;
import io.rxmicro.data.sql.operation.CustomSelect;
import io.rxmicro.data.sql.r2dbc.detail.EntityFromR2DBCSQLDBConverter;
import io.rxmicro.data.sql.r2dbc.detail.EntityToR2DBCSQLDBConverter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import javax.lang.model.element.ExecutableElement;

import static io.rxmicro.annotation.processor.common.util.Errors.createInternalErrorSupplier;
import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getModelTransformerInstanceName;
import static io.rxmicro.annotation.processor.common.util.Names.getSimpleName;
import static io.rxmicro.annotation.processor.data.sql.model.CommonSQLGroupRules.CUSTOM_SELECT_GROUP;

/**
 * @author nedis
 * @since 0.1
 */
public abstract class AbstractSQLModificationOperationReturningResultDataRepositoryMethodModelBuilder
        <A extends Annotation, DMF extends SQLDataModelField, DMC extends SQLDataObjectModelClass<DMF>>
        extends AbstractSQLOperationDataRepositoryMethodModelBuilder<A, DMF, DMC> {

    @Override
    public final boolean isSupported(final DataRepositoryMethodSignature dataRepositoryMethodSignature,
                                     final DataGenerationContext<DMF, DMC> dataGenerationContext) {
        return super.isSupported(dataRepositoryMethodSignature, dataGenerationContext) &&
                isEntityResultReturn(dataGenerationContext, dataRepositoryMethodSignature.getMethodResult());
    }

    @Override
    protected void validateMethod(final ParsedSQL<A> parsedSQL,
                                  final MethodResult methodResult,
                                  final DataGenerationContext<DMF, DMC> dataGenerationContext,
                                  final ExecutableElement method,
                                  final DataMethodParams dataMethodParams) {
        final List<Variable> customSelectParams = dataMethodParams.getParamsOfGroup(CUSTOM_SELECT_GROUP);
        if (!customSelectParams.isEmpty()) {
            throw new InterruptProcessingException(
                    customSelectParams.get(0).getElement(),
                    "Parameter(s) annotated by '@?' annotation is(are) not supported for '?' operation. " +
                            "Remove this(these) parameter(s)!",
                    CustomSelect.class, operationType().getSimpleName().toUpperCase(Locale.ENGLISH)
            );
        }
        Optional.ofNullable(method.getAnnotation(ExpectedUpdatedRowsCount.class)).ifPresent(expectedUpdatedRowsCount -> {
            final long expectedValue = expectedUpdatedRowsCount.value();
            if (methodResult.isOneItem()) {
                if (expectedValue > 1) {
                    throw new InterruptProcessingException(
                            method,
                            "Expected updated rows count value conflict with repository method result: expectedValue is ?, " +
                                    "but the repository method returns the single result! " +
                                    "Set valid expected rows count value or change the repository method result!",
                            expectedValue
                    );
                }
            } else {
                throw new InterruptProcessingException(
                        method,
                        "'@?' annotation is not supported for repository methods that return not single result! " +
                                "Remove unsupported annotation!"
                );
            }
        });
    }

    @Override
    protected void customizeClassHeaderBuilder(final ClassHeader.Builder classHeaderBuilder,
                                               final MethodResult methodResult,
                                               final DataGenerationContext<DMF, DMC> dataGenerationContext,
                                               final ExecutableElement method,
                                               final SQLStatement sqlStatement) {
        classHeaderBuilder.addImports(
                Mono.class,
                Flux.class,
                Flowable.class,
                ArrayList.class
        );
        sqlStatement.getBindParams().forEach(p -> classHeaderBuilder.addImports(p.getType()));
    }

    @Override
    protected void addEntityConverter(final MethodResult methodResult,
                                      final SQLMethodDescriptor<DMF, DMC> sqlMethodDescriptor,
                                      final DataGenerationContext<DMF, DMC> dataGenerationContext,
                                      final List<Variable> params,
                                      final SQLStatement sqlStatement,
                                      final Map<String, Object> templateArguments) {
        final boolean isEntityParam = isEntityParam(params, dataGenerationContext);
        final boolean isEntityFieldMap = sqlMethodDescriptor.getResult().isResultType(EntityFieldMap.class);
        final boolean isEntityFieldList = sqlMethodDescriptor.getResult().isResultType(EntityFieldList.class);
        templateArguments.put("RETURN_ENTITY_FIELD_MAP", isEntityFieldMap);
        templateArguments.put("RETURN_ENTITY_FIELD_LIST", isEntityFieldList);

        if (isEntityParam) {
            templateArguments.put("ENTITY", params.get(0).getGetter());
            templateArguments.put("ENTITY_TO_DB_CONVERTER", getModelTransformerInstanceName(
                    params.get(0).getType(),
                    EntityToR2DBCSQLDBConverter.class)
            );
        }
        if (!isEntityFieldList && !isEntityFieldMap) {
            final DMC modelClass = sqlMethodDescriptor.getEntityResult().orElseThrow(
                    createInternalErrorSupplier(
                            "Method return result not found for '?' operation",
                            operationType().getSimpleName().toUpperCase(Locale.ENGLISH)
                    )
            );
            final String entityClass = getSimpleName(modelClass.getJavaFullClassName());
            templateArguments.put("ENTITY_CLASS", entityClass);
            templateArguments.put("ENTITY_FROM_DB_CONVERTER", getModelTransformerInstanceName(
                    entityClass,
                    EntityFromR2DBCSQLDBConverter.class)
            );
            templateArguments.put(
                    "ENTITY_RESULT_DIFFERS_FROM_ENTITY_PARAM",
                    !isEntityParam || !params.get(0).is(modelClass.getJavaFullClassName())
            );
            final EntitySetFieldsConverterMethod converterMethod = new EntitySetFieldsConverterMethod(sqlStatement);
            modelClass.addEntitySetFieldsConverterMethod(converterMethod);
            templateArguments.put("ENTITY_CONVERTER_METHOD", converterMethod.getName());
        }
    }
}
