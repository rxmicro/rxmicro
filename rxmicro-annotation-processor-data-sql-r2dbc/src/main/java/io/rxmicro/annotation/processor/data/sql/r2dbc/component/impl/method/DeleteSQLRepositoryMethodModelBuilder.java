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
import io.rxmicro.annotation.processor.common.model.method.MethodResult;
import io.rxmicro.annotation.processor.data.model.DataGenerationContext;
import io.rxmicro.annotation.processor.data.model.Variable;
import io.rxmicro.annotation.processor.data.sql.model.ParsedSQL;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataModelField;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataObjectModelClass;
import io.rxmicro.annotation.processor.data.sql.model.SQLMethodDescriptor;
import io.rxmicro.annotation.processor.data.sql.model.SQLStatement;
import io.rxmicro.annotation.processor.data.sql.r2dbc.component.impl.AbstractSQLModificationOperationDataRepositoryMethodModelBuilder;
import io.rxmicro.data.sql.operation.Delete;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.lang.model.element.ExecutableElement;

import static io.rxmicro.data.sql.operation.Delete.DEFAULT_DELETE;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public class DeleteSQLRepositoryMethodModelBuilder<DMF extends SQLDataModelField, DMC extends SQLDataObjectModelClass<DMF>>
        extends AbstractSQLModificationOperationDataRepositoryMethodModelBuilder<Delete, DMF, DMC> {

    @Override
    protected ParsedSQL<Delete> parseSQL(final ExecutableElement method) {
        final Delete annotation = method.getAnnotation(Delete.class);
        final String originalSQL = Optional.of(annotation.value()).filter(v -> !v.isEmpty()).orElse(DEFAULT_DELETE);
        return parseSQL(originalSQL, annotation);
    }

    @Override
    protected String getTemplateName() {
        return "data/sql/r2dbc/method/$$SQLRepositoryDeleteMethodBodyTemplate.javaftl";
    }

    @Override
    protected void validateMethod(final ParsedSQL<Delete> parsedSQL,
                                  final MethodResult methodResult,
                                  final DataGenerationContext<DMF, DMC> dataGenerationContext,
                                  final ExecutableElement method,
                                  final List<Variable> params) {
        super.validateMethod(parsedSQL, methodResult, dataGenerationContext, method, params);
        validateThatEntityContainsPrimaryKeyIfParamIsEntity(dataGenerationContext, method, params);
    }

    @Override
    protected void addEntityConverter(final MethodResult methodResult,
                                      final SQLMethodDescriptor<DMF, DMC> sqlMethodDescriptor,
                                      final DataGenerationContext<DMF, DMC> dataGenerationContext,
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
