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

package io.rxmicro.test.dbunit.internal.component;

import io.rxmicro.common.CheckedWrapperException;
import io.rxmicro.common.model.InputStreamResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author nedis
 * @since 0.7
 */
public final class SQLScriptReader {

    public List<String> readJdbcStatements(final InputStreamResource resource) {
        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getBufferedInputStream(), UTF_8))) {
            final List<String> statements = new ArrayList<>();
            final StringBuilder statementBuilder = new StringBuilder();
            boolean stringLiteral = false;
            while (true) {
                final int ch = reader.read();
                if (ch == -1) {
                    break;
                } else if (ch == ';') {
                    if (!stringLiteral) {
                        trimStatementBuilder(statementBuilder);
                        if (statementBuilder.length() > 0) {
                            statements.add(statementBuilder.toString());
                            statementBuilder.delete(0, statementBuilder.length());
                        }
                    } else {
                        statementBuilder.append((char) ch);
                    }
                } else if (ch == '\'') {
                    stringLiteral = !stringLiteral;
                    statementBuilder.append((char) ch);
                } else {
                    statementBuilder.append((char) ch);
                }
            }
            if (!stringLiteral) {
                trimStatementBuilder(statementBuilder);
                if (statementBuilder.length() > 0) {
                    statements.add(statementBuilder.toString());
                    statementBuilder.delete(0, statementBuilder.length());
                }
            }
            return statements;
        } catch (final IOException ex) {
            throw new CheckedWrapperException(
                    ex, "Can't read SQL statements from resource: '?': ?", resource.getResourcePath(), ex.getMessage()
            );
        }
    }

    private void trimStatementBuilder(final StringBuilder statementBuilder) {
        int index = 0;
        while (index < statementBuilder.length()) {
            final char ch = statementBuilder.charAt(index);
            if (ch == '\r') {
                statementBuilder.deleteCharAt(index);
            } else if (ch == '\n' || ch == '\t' || ch == ' ') {
                if (index == 0) {
                    statementBuilder.deleteCharAt(0);
                } else if (index < statementBuilder.length() - 1) {
                    final char nextCh = statementBuilder.charAt(index + 1);
                    if (nextCh == '\n' || nextCh == '\t' || nextCh == ' ') {
                        statementBuilder.deleteCharAt(index);
                    } else {
                        if (ch == '\n' || ch == '\t') {
                            statementBuilder.setCharAt(index, ' ');
                        }
                        index++;
                    }
                }
            } else {
                index++;
            }
        }
    }
}
