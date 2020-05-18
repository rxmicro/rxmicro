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

package io.rxmicro.exchange.json.detail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Used by generated code that was created by {@code RxMicro Annotation Processor}
 *
 * @author nedis
 * @since 0.1
 */
@SuppressWarnings("ForLoopReplaceableByForEach")
public abstract class ModelToJsonConverter<T> {

    @SuppressWarnings("unchecked")
    public final Object toJson(final Object model) {
        if (model instanceof List) {
            return toJsonObjectArray((List<T>) model);
        } else {
            return toJsonObject((T) model);
        }
    }

    public abstract Map<String, Object> toJsonObject(T model);

    public final List<Object> toJsonObjectArray(final List<T> list) {
        if (list != null) {
            final List<Object> objects = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                final T model = list.get(i);
                if (model != null) {
                    objects.add(toJsonObject(model));
                } else {
                    objects.add(null);
                }
            }
            if (!objects.isEmpty()) {
                return objects;
            }
        }
        return null;
    }

    protected final <E> Map<String, Object> convertIfNotNull(final ModelToJsonConverter<E> converter,
                                                             final E model) {
        return model != null ? converter.toJsonObject(model) : null;
    }

    protected final <E> List<Object> convertIfNotNull(final ModelToJsonConverter<E> converter,
                                                      final List<E> list) {
        return list != null ? converter.toJsonObjectArray(list) : null;
    }
}
