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

package io.rxmicro.annotation.processor.data.sql.component.impl.builder.select;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.data.model.Var;
import io.rxmicro.annotation.processor.data.sql.component.SQLVariableValueResolver;
import io.rxmicro.annotation.processor.data.sql.component.impl.SQLFieldsOrderValidator;
import io.rxmicro.annotation.processor.data.sql.model.ParsedSQL;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataModelField;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataObjectModelClass;
import io.rxmicro.annotation.processor.data.sql.model.SQLMethodDescriptor;
import io.rxmicro.annotation.processor.data.sql.model.SQLStatement;
import io.rxmicro.annotation.processor.data.sql.model.VariableContext;
import io.rxmicro.annotation.processor.data.sql.model.VariableValuesMap;
import io.rxmicro.data.sql.operation.CustomSelect;
import io.rxmicro.data.sql.operation.Select;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.data.sql.SupportedVariables.ALL_COLUMNS;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @link https://www.postgresql.org/docs/12/sql-select.html
 * @since 0.1
 */
@Singleton
public class CustomSelectSQLBuilder<DMF extends SQLDataModelField, DMC extends SQLDataObjectModelClass<DMF>> {

    @Inject
    private SQLVariableValueResolver<Select, DMF, DMC> selectSQLVariableValueResolver;

    @Inject
    private SQLFieldsOrderValidator sqlFieldsOrderValidator;

    @Inject
    private VariableContext variableContext;

    public SQLStatement buildCustomSQL(final ParsedSQL<Select> parsedSQL,
                                       final ExecutableElement method,
                                       final SQLMethodDescriptor<DMF, DMC> sqlMethodDescriptor) {
        final VariableElement customSQL = getCustomSQL(method);
        final CustomSelect customSelect = customSQL.getAnnotation(CustomSelect.class);
        final SQLStatement.Builder builder = new SQLStatement.Builder()
                .setDefaultColumnOrder(true)
                .setBindParams(sqlMethodDescriptor.getParams().stream().map(Var::getGetter).collect(Collectors.toList()));
        if (customSelect.supportUniversalPlaceholder()) {
            // See AbstractPostgreSQLRepository.replaceUniversalPlaceholder
            builder.setSqlExpression(format("replaceUniversalPlaceholder(?)", customSQL.getSimpleName().toString()));
        } else {
            builder.setSqlExpression(customSQL.getSimpleName().toString());
        }
        setResultColumnsAndDefaultColumnOrder(
                builder,
                parsedSQL,
                method,
                sqlMethodDescriptor,
                customSelect
        );
        return builder.build();
    }

    private void setResultColumnsAndDefaultColumnOrder(final SQLStatement.Builder builder,
                                                       final ParsedSQL<Select> parsedSQL,
                                                       final ExecutableElement method,
                                                       final SQLMethodDescriptor<DMF, DMC> sqlMethodDescriptor,
                                                       final CustomSelect customSelect) {
        final Optional<DMC> entityResultOptional = sqlMethodDescriptor.getEntityResult();
        if (entityResultOptional.isPresent()) {
            final DMC modelClass = entityResultOptional.get();
            final VariableValuesMap variableValuesMap =
                    selectSQLVariableValueResolver.resolveVariableValues(variableContext, parsedSQL, method, sqlMethodDescriptor);
            final List<String> defaultColumns = variableValuesMap.getSqlVariableValue(ALL_COLUMNS).getColumns();
            final List<String> customColumns = List.of(customSelect.selectedColumns());
            if (customColumns.isEmpty()) {
                builder.setDefaultColumnOrder(true)
                        .setResultColumns(defaultColumns);
            } else {
                sqlFieldsOrderValidator.validateStringColumns(method, modelClass, defaultColumns, customColumns);
                builder.setDefaultColumnOrder(defaultColumns.equals(customColumns))
                        .setResultColumns(customColumns);
            }
        } else {
            if (customSelect.selectedColumns().length > 0) {
                throw new InterruptProcessingException(method, "Selected columns for custom select is redundant. Remote it!");
            }
        }
    }

    private VariableElement getCustomSQL(final ExecutableElement method) {
        final List<VariableElement> customSQLs = method.getParameters().stream()
                .filter(v -> v.getAnnotation(CustomSelect.class) != null)
                .collect(Collectors.toList());
        if (customSQLs.isEmpty()) {
            throw new InterruptProcessingException(
                    method,
                    "Missing method parameter, annotated by '@?' annotation or " +
                            "missing 'SELECT' query, which must be defined via '@?' annotation",
                    CustomSelect.class.getName(),
                    Select.class.getName()
            );
        } else if (customSQLs.size() > 1) {
            throw new InterruptProcessingException(
                    method,
                    "Expected only one method parameter, annotated by '@?' annotation",
                    CustomSelect.class.getName()
            );
        } else {
            return customSQLs.get(0);
        }
    }
}
