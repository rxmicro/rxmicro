/*
 * Copyright (c) 2020. http://rxmicro.io
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
import io.rxmicro.annotation.processor.data.model.Var;
import io.rxmicro.annotation.processor.data.sql.model.ParsedSQL;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataModelField;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataObjectModelClass;
import io.rxmicro.annotation.processor.data.sql.r2dbc.component.impl.AbstractSQLModificationOperationDataRepositoryMethodModelBuilder;
import io.rxmicro.data.sql.operation.Update;

import javax.lang.model.element.ExecutableElement;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;

import static io.rxmicro.data.sql.operation.Update.DEFAULT_UPDATE;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
@Singleton
public class UpdateSQLRepositoryMethodModelBuilder<DMF extends SQLDataModelField, DMC extends SQLDataObjectModelClass<DMF>>
        extends AbstractSQLModificationOperationDataRepositoryMethodModelBuilder<Update, DMF, DMC> {

    @Override
    protected ParsedSQL<Update> parseSQL(final ExecutableElement method) {
        final Update annotation = method.getAnnotation(Update.class);
        final String originalSQL = Optional.of(annotation.value()).filter(v -> !v.isEmpty()).orElse(DEFAULT_UPDATE);
        return parseSQL(originalSQL, annotation);
    }

    @Override
    protected String getTemplateName() {
        return "data/sql/r2dbc/method/$$SQLRepositoryUpdateMethodBodyTemplate.javaftl";
    }

    @Override
    protected void validateMethod(final ParsedSQL<Update> parsedSQL,
                                  final MethodResult methodResult,
                                  final DataGenerationContext<DMF, DMC> dataGenerationContext,
                                  final ExecutableElement method,
                                  final List<Var> params) {
        super.validateMethod(parsedSQL, methodResult, dataGenerationContext, method, params);
        validateThatEntityContainsPrimaryKeyIfCurrentParamIsEntity(dataGenerationContext, method, params);
    }

    @Override
    public Class<? extends Annotation> operationType() {
        return Update.class;
    }
}
