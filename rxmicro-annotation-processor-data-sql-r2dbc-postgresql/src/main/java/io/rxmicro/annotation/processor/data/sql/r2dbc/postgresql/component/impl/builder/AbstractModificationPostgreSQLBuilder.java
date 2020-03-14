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

package io.rxmicro.annotation.processor.data.sql.r2dbc.postgresql.component.impl.builder;

import com.google.inject.Inject;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.data.sql.component.SQLFieldsOrderExtractor;
import io.rxmicro.annotation.processor.data.sql.component.impl.SQLFieldsOrderValidator;
import io.rxmicro.annotation.processor.data.sql.component.impl.builder.AbstractModificationSQLBuilder;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataModelField;
import io.rxmicro.annotation.processor.data.sql.model.SQLMethodDescriptor;
import io.rxmicro.annotation.processor.data.sql.model.SQLStatement;
import io.rxmicro.annotation.processor.data.sql.model.SelectedColumn;
import io.rxmicro.annotation.processor.data.sql.model.SelectedColumnFilter;
import io.rxmicro.annotation.processor.data.sql.r2dbc.postgresql.model.PostgreSQLDataObjectModelClass;
import io.rxmicro.annotation.processor.data.sql.r2dbc.postgresql.model.PostgreSQLKeywords;
import io.rxmicro.data.sql.model.EntityFieldList;
import io.rxmicro.data.sql.model.EntityFieldMap;

import javax.lang.model.element.ExecutableElement;
import java.lang.annotation.Annotation;
import java.util.List;

import static io.rxmicro.annotation.processor.data.sql.r2dbc.postgresql.model.PostgreSQLKeywords.RETURNING;
import static java.util.stream.Collectors.toList;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public abstract class AbstractModificationPostgreSQLBuilder<A extends Annotation>
        extends AbstractModificationSQLBuilder<A, SQLDataModelField, PostgreSQLDataObjectModelClass> {

    @Inject
    private SQLFieldsOrderExtractor sqlFieldsOrderExtractor;

    @Inject
    private SQLFieldsOrderValidator sqlFieldsOrderValidator;

    @Override
    protected final boolean isAsteriskShouldBeIgnored(final int index,
                                                      final List<String> sqlTokens) {
        // index == 0 - allows to avoid IndexOutOfBoundException:
        return index == 0 || !RETURNING.equalsIgnoreCase(sqlTokens.get(index - 1));
    }

    @Override
    protected void setResultColumns(final ExecutableElement method,
                                    final SQLStatement.Builder builder,
                                    final List<String> sqlTokens,
                                    final SQLMethodDescriptor<SQLDataModelField, PostgreSQLDataObjectModelClass> sqlMethodDescriptor) {
        final int startIndex = getReturningKeywordIndex(sqlTokens);
        if (startIndex != -1) {
            final List<SelectedColumn> selectedColumns =
                    sqlFieldsOrderExtractor.getSelectedColumns(
                            sqlTokens,
                            new SelectedColumnFilter.Builder()
                                    .setStartIndex(startIndex + 1)
                                    .build()
                    );
            if (selectedColumns.isEmpty()) {
                throw new InterruptProcessingException(method, "Missing columns after '?' keyword", PostgreSQLKeywords.RETURNING);
            }
            sqlFieldsOrderValidator.validateSelectedColumn(method, selectedColumns);
            if (sqlMethodDescriptor.getEntityParam().isEmpty() &&
                    sqlMethodDescriptor.getEntityResult().isEmpty() &&
                    !sqlMethodDescriptor.getResult().isResultType(EntityFieldList.class) &&
                    !sqlMethodDescriptor.getResult().isResultType(EntityFieldMap.class)) {
                throw new InterruptProcessingException(method, "Missing entity parameter or entity result for setting the returning column values");
            }
            builder.setDefaultColumnOrder(false)
                    .setResultColumns(selectedColumns.stream().flatMap(c -> c.getCaption().stream()).collect(toList()));
        }
    }

    private int getReturningKeywordIndex(final List<String> sqlTokens) {
        for (int i = 0; i < sqlTokens.size(); i++) {
            if (RETURNING.equalsIgnoreCase(sqlTokens.get(i))) {
                return i;
            }
        }
        return -1;
    }
}
