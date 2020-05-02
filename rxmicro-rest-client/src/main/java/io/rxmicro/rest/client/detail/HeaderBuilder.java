/*
 * Copyright 2019 https://rxmicro.io
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

package io.rxmicro.rest.client.detail;

import io.rxmicro.common.model.ListBuilder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static io.rxmicro.http.HttpValues.listToString;
import static java.util.Map.entry;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class HeaderBuilder extends ListBuilder<Map.Entry<String, String>> {

    public HeaderBuilder add(final String name,
                             final Object value) {
        if (value != null) {
            super.add(entry(name, value.toString()));
        }
        return this;
    }

    public HeaderBuilder add(final String name,
                             final BigDecimal value) {
        if (value != null) {
            super.add(entry(name, value.toPlainString()));
        }
        return this;
    }

    public HeaderBuilder add(final String name,
                             final List<?> list) {
        if (list != null && !list.isEmpty()) {
            super.add(entry(name, listToString(list)));
        }
        return this;
    }
}
