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

package io.rxmicro.data.mongo.detail;

import io.rxmicro.data.local.EntityToDBConverter;
import io.rxmicro.data.mongo.internal.AbstractEntityMongoDBConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@SuppressWarnings("ForLoopReplaceableByForEach")
public abstract class EntityToMongoDBConverter<E, DB> extends AbstractEntityMongoDBConverter implements EntityToDBConverter {

    public abstract DB toDB(E entity,
                            boolean withId);

    public Object getId(E entity) {
        throw new UnsupportedOperationException("Entity does not contain document id: " + entity);
    }

    @SuppressWarnings({"EmptyMethod", "unused"})
    public void setId(final DB dbInstance,
                      final E entity) {
        // do nothing
    }

    public final List<DB> toDB(final List<E> list) {
        if (list == null || list.isEmpty()) {
            return null;
        } else {
            final List<DB> result = new ArrayList<>(list.size());
            for (int i = 0; i < list.size(); i++) {
                result.add(toDB(list.get(i), false));
            }
            return result;
        }
    }

    protected final <T> DB convertIfNotNull(final EntityToMongoDBConverter<T, DB> converter,
                                            final T model) {
        return model != null ? converter.toDB(model, false) : null;
    }

    protected final <T> List<DB> convertIfNotNull(final EntityToMongoDBConverter<T, DB> converter,
                                                  final List<T> list) {
        return list != null ? converter.toDB(list) : null;
    }
}
