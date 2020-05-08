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

package io.rxmicro.annotation.processor.common.component.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.component.SourceCodeFormatter;
import io.rxmicro.annotation.processor.common.component.TokenParser;
import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.JavaTokenParserRule;
import io.rxmicro.annotation.processor.common.model.TokenParserResult;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static java.lang.System.lineSeparator;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class SourceCodeFormatterImpl implements SourceCodeFormatter {

    @Inject
    private TokenParser tokenParser;

    @Inject
    private JavaTokenParserRule javaTokenParserRule;

    @Override
    public String format(final ClassHeader classHeader,
                         final String content) {
        final Set<String> contentTokens = getContentTokens(content);
        // Remove unused class imports
        removeUnusedImports(
                classHeader.getEditableImports().entrySet().iterator(),
                contentTokens
        );
        // Remove unused static method imports
        removeUnusedImports(
                classHeader.getEditableStaticImports().entrySet().iterator(),
                contentTokens
        );
        final String header = classHeader.buildHeader(true);
        final String resultContent = header + lineSeparator() + content;
        return resultContent.endsWith(lineSeparator()) ?
                resultContent :
                resultContent + lineSeparator();
    }

    private void removeUnusedImports(final Iterator<Map.Entry<String, String>> iterator,
                                     final Set<String> contentTokens) {
        while (iterator.hasNext()) {
            final Map.Entry<String, String> entry = iterator.next();
            if (!contentTokens.contains(entry.getKey())) {
                iterator.remove();
            }
        }
    }

    private Set<String> getContentTokens(final String content) {
        final Set<String> tokens = new HashSet<>(100);
        final StringBuilder stringBuilder = new StringBuilder(120);
        boolean multiLineCommentStarted = false;
        for (int i = 0; i < content.length(); i++) {
            final char ch = content.charAt(i);
            if (ch != '\r') { // Ignore Windows lineSeparator if present
                if (ch == '\n') {
                    final TokenParserResult tokenParserResult = tokenParser.parse(
                            stringBuilder.toString(),
                            javaTokenParserRule,
                            multiLineCommentStarted
                    );
                    stringBuilder.delete(0, stringBuilder.length());
                    if (tokenParserResult.isNotEmpty()) {
                        tokens.addAll(tokenParserResult.getTokens());
                    }
                    multiLineCommentStarted = tokenParserResult.isMultiLineCommentStarted();
                } else {
                    stringBuilder.append(ch);
                }
            }
        }
        return tokens;
    }
}
