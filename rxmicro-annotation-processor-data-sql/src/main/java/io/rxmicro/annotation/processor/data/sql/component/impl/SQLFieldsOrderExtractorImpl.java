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

package io.rxmicro.annotation.processor.data.sql.component.impl;

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.data.sql.component.SQLFieldsOrderExtractor;
import io.rxmicro.annotation.processor.data.sql.model.SelectedColumn;
import io.rxmicro.annotation.processor.data.sql.model.SelectedColumnFilter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static io.rxmicro.annotation.processor.data.sql.model.SQLKeywords.AS;
import static io.rxmicro.annotation.processor.data.sql.util.SQLs.joinTokensToSQL;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@Singleton
public final class SQLFieldsOrderExtractorImpl implements SQLFieldsOrderExtractor {

    @Override
    public List<SelectedColumn> getSelectedColumns(final List<String> sqlTokens,
                                                   final SelectedColumnFilter selectedColumnFilter) {
        final List<SelectedColumn> result = new ArrayList<>();
        final Iterator<String> iterator = sqlTokens.listIterator(selectedColumnFilter.getStartIndex());
        final List<String> columnTokens = new ArrayList<>();
        int parenthesesCount = 0;
        int bracesCount = 0;
        int bracketsCount = 0;
        while (iterator.hasNext()) {
            final String token = iterator.next();
            if (!selectedColumnFilter.getIgnoredTokens().contains(token.toUpperCase())) {
                if (parenthesesCount == 0 &&
                        bracesCount == 0 &&
                        bracketsCount == 0 &&
                        selectedColumnFilter.getBreakTokens().contains(token.toUpperCase())) {
                    break;
                } else if ("(".equals(token)) {
                    parenthesesCount++;
                } else if (")".equals(token)) {
                    parenthesesCount--;
                } else if ("{".equals(token)) {
                    bracesCount++;
                } else if ("}".equals(token)) {
                    bracesCount--;
                } else if ("[".equals(token)) {
                    bracketsCount++;
                } else if ("]".equals(token)) {
                    bracketsCount--;
                } else if (",".equals(token) && parenthesesCount == 0 && bracesCount == 0 && bracketsCount == 0) {
                    if (!columnTokens.isEmpty()) {
                        result.add(build(columnTokens));
                        columnTokens.clear();
                    }
                    continue;
                }
                columnTokens.add(token);
            }
        }
        if (!columnTokens.isEmpty()) {
            result.add(build(columnTokens));
        }
        return result;
    }

    private SelectedColumn build(final List<String> columnTokens) {
        if (columnTokens.size() == 1) {
            return SelectedColumn.buildColumn(columnTokens.get(0));
        } else {
            if (AS.equalsIgnoreCase(columnTokens.get(columnTokens.size() - 2))) {
                final String alias = columnTokens.get(columnTokens.size() - 1);
                final List<String> expressions = columnTokens.size() > 2 ?
                        columnTokens.subList(0, columnTokens.size() - 2) :
                        List.of();
                return SelectedColumn.buildExpressionWithAlias(joinTokensToSQL(expressions), alias);
            } else {
                return SelectedColumn.buildExpression(joinTokensToSQL(columnTokens));
            }
        }
    }
}
