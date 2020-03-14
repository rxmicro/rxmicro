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

package io.rxmicro.annotation.processor.common.model;

import java.util.List;

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class TokenParserResult {

    private final List<String> tokens;

    private final boolean multiLineCommentStarted;

    public TokenParserResult(final List<String> tokens,
                             final boolean multiLineCommentStarted) {
        this.tokens = require(tokens);
        this.multiLineCommentStarted = multiLineCommentStarted;
    }

    public TokenParserResult(final boolean multiLineCommentStarted) {
        this(List.of(), multiLineCommentStarted);
    }

    public boolean isNotEmpty() {
        return !tokens.isEmpty();
    }

    public List<String> getTokens() {
        return tokens;
    }

    public boolean isMultiLineCommentStarted() {
        return multiLineCommentStarted;
    }
}
