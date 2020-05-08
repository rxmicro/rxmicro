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

package io.rxmicro.data.sql.local.impl;

import io.rxmicro.data.sql.model.EntityFieldList;

import java.util.AbstractList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

/**
 * @author nedis
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
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof List)) {
            return false;
        }
        if (other instanceof EntityFieldListImpl) {
            return list.equals(((EntityFieldListImpl) other).list);
        }
        return list.equals(other);
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
