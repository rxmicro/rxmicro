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

package io.rxmicro.annotation.processor.data.sql.util;

import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * @author nedis
 * @since 0.1
 */
public final class SQLs {

    private static final Set<String> ADD_SPACE_AFTER_TOKENS_BEFORE_PARENTHESES = Set.of("IN", "WHERE", ",");

    public static String joinTokensToSQL(final List<String> sqlTokens) {
        final StringBuilder sql = new StringBuilder();
        String prevToken = null;
        for (final String token : sqlTokens) {
            if (sql.length() == 0) {
                sql.append(escape(token));
            } else if (".".equals(token) || ",".equals(token) || ")".equals(token) || "]".equals(token) || "}".equals(token)) {
                sql.append(token);
            } else if ("(".equals(token)) {
                if (ADD_SPACE_AFTER_TOKENS_BEFORE_PARENTHESES.contains(prevToken)) {
                    sql.append(' ');
                }
                sql.append(escape(token));
            } else {
                final char lastChar = sql.charAt(sql.length() - 1);
                if (lastChar != '(' && lastChar != '[' && lastChar != '{' && lastChar != '.') {
                    sql.append(' ');
                }
                sql.append(escape(token));
            }
            prevToken = token.toUpperCase(Locale.ENGLISH);
        }
        return sql.toString();
    }

    private static String escape(final String token) {
        return token.replace("\"", "\\\"");
    }

    private SQLs() {
    }
}
