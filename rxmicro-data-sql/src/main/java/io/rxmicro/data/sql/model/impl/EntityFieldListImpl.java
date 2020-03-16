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

package io.rxmicro.data.sql.model.impl;

import io.rxmicro.data.sql.model.EntityFieldList;

import java.util.AbstractList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class EntityFieldListImpl extends AbstractList<Object> implements EntityFieldList {

    private final List<Object> list;

    public EntityFieldListImpl(final List<Object> list) {
        this.list = unmodifiableList(list);
    }

    @Override
    public Object get(final int index) {
        return list.get(index);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof List)) {
            return false;
        }
        if (o instanceof EntityFieldListImpl) {
            return list.equals(((EntityFieldListImpl) o).list);
        }
        return list.equals(o);
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }

    @Override
    public int size() {
        return list.size();
    }
}
