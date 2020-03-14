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

package io.rxmicro.annotation.processor.data.sql.model;

import java.util.List;
import java.util.Objects;

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public abstract class EntityConverterMethod implements Comparable<EntityConverterMethod> {

    private final String name;

    private final List<String> selectedColumns;

    EntityConverterMethod(final String name, final List<String> selectedColumns) {
        this.name = require(name);
        this.selectedColumns = require(selectedColumns);
    }

    public String getName() {
        return name;
    }

    public List<String> getSelectedColumns() {
        return selectedColumns;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final EntityConverterMethod that = (EntityConverterMethod) o;
        return name.equals(that.name);
    }

    @Override
    public int compareTo(final EntityConverterMethod o) {
        return name.compareTo(o.name);
    }
}
