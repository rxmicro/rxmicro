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
import java.util.Collection;
import java.util.List;

/**
 * Used by generated code that created by the {@code RxMicro Annotation Processor}.
 *
 * @author nedis
 * @hidden
 * @since 0.1
 */
public abstract class EntityToMongoDBConverter<E, DB> extends AbstractEntityMongoDBConverter implements EntityToDBConverter {

    public abstract DB toDB(E entity,
                            boolean withId);

    public final List<DB> toDB(final Collection<E> list) {
        if (list == null || list.isEmpty()) {
            return null;
        } else {
            final List<DB> result = new ArrayList<>(list.size());
            for (final E e : list) {
                result.add(toDB(e, false));
            }
            return result;
        }
    }

    public Object getId(final E entity) {
        throw new UnsupportedOperationException("Entity does not contain document id: " + entity);
    }

    @SuppressWarnings({"EmptyMethod", "unused"})
    public void setId(final DB dbInstance,
                      final E entity) {
        // do nothing
    }

    protected final <T> DB convertToObjectIfNotNull(final EntityToMongoDBConverter<T, DB> converter,
                                                    final T model) {
        return model != null ? converter.toDB(model, false) : null;
    }

    protected final <T> List<DB> convertToListIfNotNull(final EntityToMongoDBConverter<T, DB> converter,
                                                        final Collection<T> list) {
        return list != null ? converter.toDB(list) : null;
    }
}
