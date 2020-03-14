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

package io.rxmicro.annotation.processor.data.sql.component.impl.builder.select.operator;

import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.data.model.Var;
import io.rxmicro.annotation.processor.data.sql.component.impl.builder.select.SelectSQLOperatorReader;

import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import static io.rxmicro.annotation.processor.data.sql.model.SQLKeywords.ORDER;
import static io.rxmicro.common.util.Formats.FORMAT_PLACEHOLDER_TOKEN;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public class OrderBySelectSQLOperatorReader implements SelectSQLOperatorReader {

    private final Set<String> afterOrderKeywords;

    public OrderBySelectSQLOperatorReader(final Set<String> afterOrderKeywords) {
        this.afterOrderKeywords = afterOrderKeywords;
    }

    @Override
    public boolean canRead(final String token) {
        return ORDER.equalsIgnoreCase(token);
    }

    @Override
    public void read(final ClassHeader.Builder classHeaderBuilder,
                     final ListIterator<String> iterator,
                     final List<Var> methodParams,
                     final List<String> formatParams) {
        int nested = 0;
        while (iterator.hasNext()) {
            final String token = iterator.next();
            if (afterOrderKeywords.contains(token.toUpperCase())) {
                return;
            } else if (FORMAT_PLACEHOLDER_TOKEN.equals(token)) {
                formatParams.add(methodParams.remove(0).getGetter());
            } else if ("(".equals(token)) {
                nested++;
            } else if (")".equals(token)) {
                if (nested == 0) {
                    return;
                }
                nested--;
            }
        }
    }
}
