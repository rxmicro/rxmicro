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

import io.rxmicro.data.local.EntityFromDBConverter;
import io.rxmicro.data.mongo.internal.AbstractEntityMongoDBConverter;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

/**
 * Used by generated code that created by the {@code RxMicro Annotation Processor}.
 *
 * @author nedis
 * @hidden
 * @since 0.1
 */
@SuppressWarnings("ForLoopReplaceableByForEach")
public abstract class EntityFromMongoDBConverter<DB, E> extends AbstractEntityMongoDBConverter implements EntityFromDBConverter {

    public abstract E fromDB(DB dbRow);

    public final List<E> fromDB(final List<DB> list) {
        if (list == null || list.isEmpty()) {
            return null;
        } else {
            final List<E> result = new ArrayList<>(list.size());
            for (int i = 0; i < list.size(); i++) {
                result.add(fromDB(list.get(i)));
            }
            return unmodifiableList(result);
        }
    }

    protected final <T> T convertIfNotNull(final EntityFromMongoDBConverter<DB, T> converter,
                                           final DB dbRow) {
        return dbRow != null ? converter.fromDB(dbRow) : null;
    }

    protected final <T> List<T> convertIfNotNull(final EntityFromMongoDBConverter<DB, T> converter,
                                                 final List<DB> list) {
        return list != null ? converter.fromDB(list) : null;
    }
}
