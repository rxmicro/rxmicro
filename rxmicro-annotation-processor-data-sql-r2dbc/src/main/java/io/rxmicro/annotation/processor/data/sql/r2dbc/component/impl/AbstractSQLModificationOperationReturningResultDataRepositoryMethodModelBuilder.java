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
import io.rxmicro.annotation.processor.common.model.method.MethodResult;
import io.rxmicro.annotation.processor.data.model.DataGenerationContext;
import io.rxmicro.annotation.processor.data.model.DataRepositoryMethodSignature;
import io.rxmicro.annotation.processor.data.model.Variable;
import io.rxmicro.annotation.processor.data.sql.model.EntitySetFieldsConverterMethod;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataModelField;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataObjectModelClass;
import io.rxmicro.annotation.processor.data.sql.model.SQLMethodDescriptor;
import io.rxmicro.annotation.processor.data.sql.model.SQLStatement;
import io.rxmicro.data.sql.model.EntityFieldList;
import io.rxmicro.data.sql.model.EntityFieldMap;
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
    protected void customizeClassHeaderBuilder(final ClassHeader.Builder classHeaderBuilder,
                                               final MethodResult methodResult,
                                               final DataGenerationContext<DMF, DMC> dataGenerationContext,
                                               final ExecutableElement method) {
        classHeaderBuilder.addImports(
                Mono.class,
                Flux.class,
                Flowable.class,
                ArrayList.class
        );
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
        templateArguments.put("IS_ENTITY_PARAM", isEntityParam);
        templateArguments.put("RETURN_ENTITY_FIELD_MAP", isEntityFieldMap);
        templateArguments.put("RETURN_ENTITY_FIELD_LIST", isEntityFieldList);

        final DMC modelClass = getResultModelClassOrNull(sqlMethodDescriptor, isEntityParam, isEntityFieldMap, isEntityFieldList);

        if (isEntityParam) {
            templateArguments.put("ENTITY", params.get(0).getGetter());
            templateArguments.put("ENTITY_CONVERTER", getModelTransformerInstanceName(
                    params.get(0).getType(),
                    EntityToR2DBCSQLDBConverter.class)
            );
        } else if (modelClass != null) {
            final String entityClass = getSimpleName(modelClass.getJavaFullClassName());
            templateArguments.put("ENTITY_CLASS", entityClass);
            templateArguments.put("ENTITY_CONVERTER", getModelTransformerInstanceName(
                    entityClass,
                    EntityFromR2DBCSQLDBConverter.class)
            );
        }
        // repository method can read entity parameter and return field list or map
        if (modelClass != null) {
            final EntitySetFieldsConverterMethod converterMethod = new EntitySetFieldsConverterMethod(sqlStatement);
            modelClass.addEntitySetFieldsConverterMethod(converterMethod);
            templateArguments.put("ENTITY_CONVERTER_METHOD", converterMethod.getName());
        }
    }

    private DMC getResultModelClassOrNull(final SQLMethodDescriptor<DMF, DMC> sqlMethodDescriptor,
                                          final boolean isEntityParam,
                                          final boolean isEntityFieldMap,
                                          final boolean isEntityFieldList) {
        if (!isEntityFieldList && !isEntityFieldMap) {
            final Optional<DMC> modelClassOptional;
            final String errorMessageTemplate;
            if (isEntityParam) {
                modelClassOptional = sqlMethodDescriptor.getEntityParam().or(sqlMethodDescriptor::getEntityResult);
                errorMessageTemplate = "Method entity param or return result not found for '?' operation";
            } else {
                modelClassOptional = sqlMethodDescriptor.getEntityResult();
                errorMessageTemplate = "Method return result not found for '?' operation";
            }
            return modelClassOptional.orElseThrow(
                    createInternalErrorSupplier(errorMessageTemplate, operationType().getSimpleName().toUpperCase(Locale.ENGLISH))
            );
        } else {
            return null;
        }
    }
}
