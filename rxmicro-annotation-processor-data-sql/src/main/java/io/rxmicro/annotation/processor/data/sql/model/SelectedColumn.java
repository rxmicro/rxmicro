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

package io.rxmicro.annotation.processor.data.sql.model;

import java.util.Optional;

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class SelectedColumn {

    private final String caption;

    private final String expression;

    public static SelectedColumn buildColumn(final String columnName) {
        return new SelectedColumn(require(columnName), columnName);
    }

    public static SelectedColumn buildExpression(final String expression) {
        return new SelectedColumn(null, require(expression));
    }

    public static SelectedColumn buildExpressionWithAlias(final String expression, final String alias) {
        return new SelectedColumn(require(alias), require(expression));
    }

    private SelectedColumn(final String caption,
                           final String expression) {
        this.caption = caption;
        this.expression = expression;
    }

    public Optional<String> getCaption() {
        return Optional.ofNullable(caption);
    }

    public String getExpression() {
        return expression;
    }

    @Override
    public String toString() {
        if (expression.equals(caption)) {
            return caption;
        } else if (caption == null) {
            return expression;
        } else {
            return expression + " AS " + caption;
        }
    }
}
