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
import io.reactivex.rxjava3.core.Flowable;
import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.method.MethodResult;
import io.rxmicro.annotation.processor.data.model.DataGenerationContext;
import io.rxmicro.annotation.processor.data.model.Variable;
import io.rxmicro.annotation.processor.data.sql.model.EntityFromDBConverterMethod;
import io.rxmicro.annotation.processor.data.sql.model.ParsedSQL;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataModelField;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataObjectModelClass;
import io.rxmicro.annotation.processor.data.sql.model.SQLMethodDescriptor;
import io.rxmicro.annotation.processor.data.sql.model.SQLStatement;
import io.rxmicro.annotation.processor.data.sql.r2dbc.component.impl.AbstractSQLOperationDataRepositoryMethodModelBuilder;
import io.rxmicro.data.sql.model.EntityFieldList;
import io.rxmicro.data.sql.model.EntityFieldMap;
import io.rxmicro.data.sql.operation.Select;
import io.rxmicro.data.sql.r2dbc.detail.EntityFromR2DBCSQLDBConverter;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import javax.lang.model.element.ExecutableElement;

import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getModelTransformerInstanceName;
import static io.rxmicro.annotation.processor.common.util.Names.getSimpleName;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public class SelectSQLRepositoryMethodModelBuilder<DMF extends SQLDataModelField, DMC extends SQLDataObjectModelClass<DMF>>
        extends AbstractSQLOperationDataRepositoryMethodModelBuilder<Select, DMF, DMC> {

    @Override
    protected void customizeClassHeaderBuilder(final ClassHeader.Builder classHeaderBuilder,
                                               final MethodResult methodResult,
                                               final DataGenerationContext<DMF, DMC> dataGenerationContext,
                                               final ExecutableElement method) {
        super.customizeClassHeaderBuilder(classHeaderBuilder, methodResult, dataGenerationContext, method);
        classHeaderBuilder.addImports(
                Flowable.class,
                ArrayList.class
        );
    }

    @Override
    protected void validateMethod(final ParsedSQL<Select> parsedSQL, final MethodResult methodResult,
                                  final DataGenerationContext<DMF, DMC> dataGenerationContext,
                                  final ExecutableElement method, final List<Variable> params) {
        validateReturnType(
                method,
                methodResult.getResultType(),
                List.of(
                        supportedTypesProvider.getPrimitives(),
                        supportedTypesProvider.getPrimitiveContainers()
                ),
                Stream.concat(
                        dataGenerationContext.getEntityResultTypes().stream(),
                        Stream.of(
                                EntityFieldMap.class,
                                EntityFieldList.class
                        ).map(Class::getName)
                ).toArray(String[]::new)
        );
    }

    @Override
    protected ParsedSQL<Select> parseSQL(final ExecutableElement method) {
        final Select annotation = method.getAnnotation(Select.class);
        return parseSQL(annotation.value(), annotation);
    }

    @Override
    protected void addEntityConverter(final MethodResult methodResult,
                                      final SQLMethodDescriptor<DMF, DMC> sqlMethodDescriptor,
                                      final DataGenerationContext<DMF, DMC> dataGenerationContext,
                                      final List<Variable> params,
                                      final SQLStatement sqlStatement,
                                      final Map<String, Object> templateArguments) {
        sqlMethodDescriptor.getEntityResult().ifPresent(modelClass -> {
            final EntityFromDBConverterMethod method = new EntityFromDBConverterMethod(sqlStatement);
            modelClass.addEntityFromDBConverterMethod(method);
            templateArguments.put("ENTITY_CONVERTER", getModelTransformerInstanceName(
                    getSimpleName(methodResult.getResultType()),
                    EntityFromR2DBCSQLDBConverter.class)
            );
            templateArguments.put("ENTITY_CONVERTER_METHOD", method.getName());
        });
    }

    @Override
    protected String getTemplateName() {
        return "data/sql/r2dbc/method/$$SQLRepositorySelectMethodBodyTemplate.javaftl";
    }

    @Override
    public Class<? extends Annotation> operationType() {
        return Select.class;
    }
}
