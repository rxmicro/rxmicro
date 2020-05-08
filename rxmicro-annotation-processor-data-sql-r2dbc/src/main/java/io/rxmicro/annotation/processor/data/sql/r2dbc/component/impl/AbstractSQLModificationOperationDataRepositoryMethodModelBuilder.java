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

import io.rxmicro.annotation.processor.common.model.method.MethodResult;
import io.rxmicro.annotation.processor.data.model.DataGenerationContext;
import io.rxmicro.annotation.processor.data.model.DataRepositoryMethodSignature;
import io.rxmicro.annotation.processor.data.model.Variable;
import io.rxmicro.annotation.processor.data.sql.model.ParsedSQL;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataModelField;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataObjectModelClass;
import io.rxmicro.annotation.processor.data.sql.model.SQLMethodDescriptor;
import io.rxmicro.annotation.processor.data.sql.model.SQLStatement;
import io.rxmicro.data.sql.r2dbc.detail.EntityToR2DBCSQLDBConverter;

import javax.lang.model.element.ExecutableElement;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getModelTransformerInstanceName;
import static io.rxmicro.annotation.processor.common.util.Names.getSimpleName;

/**
 * @author nedis
 * @since 0.1
 */
public abstract class AbstractSQLModificationOperationDataRepositoryMethodModelBuilder<A extends Annotation, DMF extends SQLDataModelField, DMC extends SQLDataObjectModelClass<DMF>>
        extends AbstractSQLOperationDataRepositoryMethodModelBuilder<A, DMF, DMC> {

    @Override
    public boolean isSupported(final DataRepositoryMethodSignature dataRepositoryMethodSignature,
                               final DataGenerationContext<DMF, DMC> dataGenerationContext) {
        return super.isSupported(dataRepositoryMethodSignature, dataGenerationContext) &&
                !isEntityResultReturn(dataGenerationContext, dataRepositoryMethodSignature.getMethodResult());
    }

    @Override
    protected void validateMethod(final ParsedSQL<A> parsedSQL, final MethodResult methodResult,
                                  final DataGenerationContext<DMF, DMC> dataGenerationContext,
                                  final ExecutableElement method,
                                  final List<Variable> params) {
        validateRequiredSingleReturnType(method, methodResult);
        validateReturnType(method, methodResult.getResultType(), Void.class, Integer.class, Boolean.class);
    }

    @Override
    protected void addEntityConverter(final MethodResult methodResult,
                                      final SQLMethodDescriptor<DMF, DMC> sqlMethodDescriptor,
                                      final DataGenerationContext<DMF, DMC> dataGenerationContext,
                                      final List<Variable> params,
                                      final SQLStatement sqlStatement,
                                      final Map<String, Object> templateArguments) {
        final boolean isEntityParam = isEntityParam(params, dataGenerationContext);
        templateArguments.put("IS_ENTITY_PARAM", isEntityParam);
        if (isEntityParam) {
            final Variable entityVariable = params.get(0);
            templateArguments.put("ENTITY", entityVariable.getGetter());
            templateArguments.put("ENTITY_CONVERTER", getModelTransformerInstanceName(
                    getSimpleName(entityVariable.getType()),
                    EntityToR2DBCSQLDBConverter.class)
            );
        }
    }
}
