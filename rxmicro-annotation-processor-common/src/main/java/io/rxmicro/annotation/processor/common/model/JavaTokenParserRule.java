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

package io.rxmicro.annotation.processor.common.model;

import com.google.inject.Singleton;

import java.util.Set;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@Singleton
public final class JavaTokenParserRule extends TokenParserRule {

    @Override
    public boolean supportVariables() {
        return false;
    }

    @Override
    protected Set<String> getLineCommentStartedTokens() {
        return Set.of("//");
    }

    @Override
    protected Set<String> getMultiLineCommentStartedTokens() {
        return Set.of("/*");
    }

    @Override
    protected Set<String> getMultiLineCommentFinishedTokens() {
        return Set.of("*/");
    }

    @Override
    protected Set<String> getOperatorTokenDelimiters() {
        return Set.of(
                "+", "++", "+=", "-", "--", "-=", "*", "*=", "/", "/=", "%", "%=",
                ">", ">>", ">=", ">>>", ">>=", ">>>=", "<", "<<", "<=", "<<=",
                "!", "!=", "=", "==", "&", "&&", "&=", "|", "||", "|=", "^", "^=", "~"
        );
    }

    @Override
    protected Set<String> getNotOperatorTokenDelimiters() {
        return Set.of(
                "(", ")",
                "{", "}",
                "[", "]",
                ":",
                ",",
                ";",
                "."
        );
    }
}
