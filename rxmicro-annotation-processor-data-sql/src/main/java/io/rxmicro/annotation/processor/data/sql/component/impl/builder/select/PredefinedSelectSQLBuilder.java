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

package io.rxmicro.annotation.processor.data.sql.component.impl.builder.select;

import com.google.inject.Inject;
import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.data.model.Variable;
import io.rxmicro.annotation.processor.data.sql.component.SQLFieldsOrderExtractor;
import io.rxmicro.annotation.processor.data.sql.component.SQLVariableValueResolver;
import io.rxmicro.annotation.processor.data.sql.component.impl.SQLFieldsOrderValidator;
import io.rxmicro.annotation.processor.data.sql.component.impl.builder.AbstractSQLBuilder;
import io.rxmicro.annotation.processor.data.sql.model.ParsedSQL;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataModelField;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataObjectModelClass;
import io.rxmicro.annotation.processor.data.sql.model.SQLMethodDescriptor;
import io.rxmicro.annotation.processor.data.sql.model.SQLStatement;
import io.rxmicro.annotation.processor.data.sql.model.SQLVariableValue;
import io.rxmicro.annotation.processor.data.sql.model.SelectedColumn;
import io.rxmicro.annotation.processor.data.sql.model.SelectedColumnFilter;
import io.rxmicro.annotation.processor.data.sql.model.VariableContext;
import io.rxmicro.annotation.processor.data.sql.model.VariableValuesMap;
import io.rxmicro.annotation.processor.data.sql.model.inject.SupportedSelectVariables;
import io.rxmicro.common.util.Formats;
import io.rxmicro.data.sql.operation.Select;

import javax.lang.model.element.ExecutableElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static io.rxmicro.annotation.processor.data.sql.model.SQLKeywords.ALL;
import static io.rxmicro.annotation.processor.data.sql.model.SQLKeywords.DISTINCT;
import static io.rxmicro.annotation.processor.data.sql.model.SQLKeywords.FROM;
import static io.rxmicro.annotation.processor.data.sql.model.SQLKeywords.SELECT;
import static io.rxmicro.annotation.processor.data.sql.util.SQLs.joinTokensToSQL;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.data.sql.SupportedVariables.ALL_COLUMNS;
import static java.util.stream.Collectors.toList;

/**
 * @author nedis
 * @since 0.1
 */
public class PredefinedSelectSQLBuilder<DMF extends SQLDataModelField, DMC extends SQLDataObjectModelClass<DMF>> extends AbstractSQLBuilder {

    @Inject
    private SQLVariableValueResolver<Select, DMF, DMC> selectSQLVariableValueResolver;

    @Inject
    private SQLFieldsOrderValidator sqlFieldsOrderValidator;

    @Inject
    private SQLFieldsOrderExtractor sqlFieldsOrderExtractor;

    @Inject
    private VariableContext variableContext;

    @Inject
    @SupportedSelectVariables
    private Set<String> supportedVariables;


    @Override
    protected boolean isAsteriskShouldBeIgnored(final int index,
                                                final List<String> sqlTokens) {
        return index != 1;
    }

    public SQLStatement buildPredefinedSQL(final ClassHeader.Builder classHeaderBuilder,
                                           final ParsedSQL<Select> parsedSQL,
                                           final ExecutableElement method,
                                           final SQLMethodDescriptor<DMF, DMC> sqlMethodDescriptor) {
        final List<String> sqlTokens = new ArrayList<>(parsedSQL.getSqlTokens());
        final String originalSQL = joinTokensToSQL(sqlTokens);
        validateSelectStatement(method, sqlTokens);
        final boolean shouldExpandAsterisk = parsedSQL.getAnnotation().expandAsterisk() && sqlMethodDescriptor.getEntityResult().isPresent();
        final Set<String> vars = extractVariables(
                method,
                sqlTokens,
                shouldExpandAsterisk ? List.of("*") : List.of()
        );
        final VariableValuesMap variableValuesMap =
                selectSQLVariableValueResolver.resolveVariableValues(variableContext, parsedSQL, method, sqlMethodDescriptor);
        validateSupportedVars(Select.class, method, vars, supportedVariables, variableValuesMap.keySet(), "*");
        setVariableValues(method, sqlTokens, vars, variableValuesMap);
        validatePlaceholderCount(method, sqlTokens, sqlMethodDescriptor.getParams());
        return build(
                classHeaderBuilder,
                method,
                sqlTokens,
                sqlMethodDescriptor.getParams(),
                variableValuesMap,
                originalSQL,
                sqlMethodDescriptor
        );
    }

    private void validateSelectStatement(final ExecutableElement method,
                                         final List<String> sqlTokens) {
        if (sqlTokens.isEmpty() || !SELECT.equalsIgnoreCase(sqlTokens.get(0))) {
            throw new InterruptProcessingException(method, "Select SQL query must start with '?' keyword", SELECT);
        }
    }

    private SQLStatement build(final ClassHeader.Builder classHeaderBuilder,
                               final ExecutableElement method,
                               final List<String> sqlTokens,
                               final List<Variable> methodParams,
                               final VariableValuesMap variableValuesMap,
                               final String originalSQL,
                               final SQLMethodDescriptor<DMF, DMC> sqlMethodDescriptor) {
        final List<String> formatParams = new ArrayList<>();
        final List<String> bindParams = new ArrayList<>();
        splitParams(method, classHeaderBuilder, sqlTokens, methodParams, formatParams, bindParams);

        final SQLStatement.Builder builder = new SQLStatement.Builder()
                .setOriginalSql(originalSQL)
                .setBindParams(bindParams);
        final String sql = joinTokensToSQL(sqlTokens);
        if (formatParams.isEmpty()) {
            builder.setSqlExpression(format("\"?\"", sql));
        } else {
            classHeaderBuilder.addStaticImport(Formats.class, "format");
            builder.setSqlExpression(format("format(\"?\", ?)", sql, String.join(", ", formatParams)));
        }
        sqlMethodDescriptor.getEntityResult().ifPresent(modelClass -> {
            final List<SelectedColumn> selectedColumns = sqlFieldsOrderExtractor.getSelectedColumns(
                    sqlTokens,
                    getSelectedColumnFilter()
            );
            final List<String> defaultColumns = getDefaultColumns(variableValuesMap);
            sqlFieldsOrderValidator.validateSelectedColumn(method, modelClass, defaultColumns, selectedColumns);
            builder.setResultColumns(selectedColumns.stream().flatMap(c -> c.getCaption().stream()).collect(toList()))
                    .setDefaultColumnOrder(isDefaultColumnOrder(selectedColumns, defaultColumns));
        });
        return builder.build();
    }

    protected SelectedColumnFilter getSelectedColumnFilter() {
        return new SelectedColumnFilter.Builder()
                .setStartIndex(1)
                .setBreakTokens(Set.of(FROM))
                .setIgnoredTokens(Set.of(ALL, DISTINCT))
                .build();
    }

    private List<String> getDefaultColumns(final VariableValuesMap variableValuesMap) {
        return Optional.ofNullable(variableValuesMap.getSqlVariableValue(ALL_COLUMNS))
                .map(SQLVariableValue::getColumns)
                .orElse(List.of());
    }

    private boolean isDefaultColumnOrder(final List<SelectedColumn> selectedColumns,
                                         final List<String> defaultColumns) {
        return defaultColumns.equals(selectedColumns.stream().flatMap(c -> c.getCaption().stream()).collect(toList()));
    }
}
