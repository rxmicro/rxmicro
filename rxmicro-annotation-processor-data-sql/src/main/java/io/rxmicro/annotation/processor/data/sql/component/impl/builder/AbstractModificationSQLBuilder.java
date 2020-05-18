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

package io.rxmicro.annotation.processor.data.sql.component.impl.builder;

import com.google.inject.Inject;
import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.data.sql.component.SQLBuilder;
import io.rxmicro.annotation.processor.data.sql.component.SQLVariableValueResolver;
import io.rxmicro.annotation.processor.data.sql.model.ParsedSQL;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataModelField;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataObjectModelClass;
import io.rxmicro.annotation.processor.data.sql.model.SQLMethodDescriptor;
import io.rxmicro.annotation.processor.data.sql.model.SQLStatement;
import io.rxmicro.annotation.processor.data.sql.model.VariableContext;
import io.rxmicro.annotation.processor.data.sql.model.VariableValuesMap;
import io.rxmicro.common.util.Formats;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.ExecutableElement;

import static io.rxmicro.annotation.processor.data.sql.util.SQLs.joinTokensToSQL;
import static io.rxmicro.common.util.Formats.format;

/**
 * @author nedis
 * @since 0.1
 */
public abstract class AbstractModificationSQLBuilder
        <A extends Annotation, DMF extends SQLDataModelField, DMC extends SQLDataObjectModelClass<DMF>>
        extends AbstractSQLBuilder
        implements SQLBuilder<A, DMF, DMC> {

    @Inject
    private SQLVariableValueResolver<A, DMF, DMC> sqlVariableValueResolver;

    @Inject
    private VariableContext variableContext;

    @Override
    public final SQLStatement build(final ClassHeader.Builder classHeaderBuilder,
                                    final ParsedSQL<A> parsedSQL,
                                    final ExecutableElement method,
                                    final SQLMethodDescriptor<DMF, DMC> sqlMethodDescriptor) {
        final List<String> sqlTokens = new ArrayList<>(parsedSQL.getSqlTokens());
        final String originalSQL = joinTokensToSQL(sqlTokens);
        validateStatement(method, sqlTokens);
        final Set<String> vars = extractVariables(method, sqlTokens, List.of("*"));

        final VariableValuesMap variableValuesMap =
                sqlVariableValueResolver.resolveVariableValues(variableContext, parsedSQL, method, sqlMethodDescriptor);
        validateSupportedVars(
                parsedSQL.getAnnotation().annotationType(),
                method,
                vars,
                getSupportedVariables(),
                variableValuesMap.keySet(),
                "*"
        );
        setVariableValues(method, sqlTokens, vars, variableValuesMap);
        final SQLStatement.Builder builder = new SQLStatement.Builder()
                .setOriginalSql(originalSQL);
        setResultColumns(method, builder, sqlTokens, sqlMethodDescriptor);
        if (sqlMethodDescriptor.getEntityParam().isPresent()) {
            replaceAllPlaceholders(sqlTokens);
            return builder
                    .setSqlExpression(format("\"?\"", joinTokensToSQL(sqlTokens)))
                    .build();
        } else {
            return buildParametrized(method, classHeaderBuilder, sqlMethodDescriptor, builder, sqlTokens);
        }
    }

    protected abstract void setResultColumns(ExecutableElement method,
                                             SQLStatement.Builder builder,
                                             List<String> sqlTokens,
                                             SQLMethodDescriptor<DMF, DMC> sqlMethodDescriptor);

    protected abstract Set<String> getSupportedVariables();

    protected abstract void validateStatement(ExecutableElement method,
                                              List<String> sqlTokens);


    private SQLStatement buildParametrized(final ExecutableElement method,
                                           final ClassHeader.Builder classHeaderBuilder,
                                           final SQLMethodDescriptor<DMF, DMC> sqlMethodDescriptor,
                                           final SQLStatement.Builder builder,
                                           final List<String> sqlTokens) {
        final List<String> formatParams = new ArrayList<>();
        final List<String> bindParams = new ArrayList<>();
        splitParams(method, classHeaderBuilder, sqlTokens, sqlMethodDescriptor.getParams(), formatParams, bindParams);
        final String sql = joinTokensToSQL(sqlTokens);
        if (formatParams.isEmpty()) {
            builder.setSqlExpression(format("\"?\"", sql));
        } else {
            classHeaderBuilder.addStaticImport(Formats.class, "format");
            builder.setSqlExpression(format("format(\"?\", ?)", sql, String.join(", ", formatParams)));
        }
        return builder
                .setBindParams(bindParams)
                .build();
    }
}
