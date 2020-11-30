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
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Collections.unmodifiableMap;

/**
 * Used by generated code that created by the {@code RxMicro Annotation Processor}.
 *
 * @author nedis
 * @hidden
 * @since 0.1
 */
public abstract class ModelToJsonConverter<T> {

    @SuppressWarnings("unchecked")
    public final Object toJson(final Object model) {
        if (model instanceof List) {
            return toJsonObjectArray((List<T>) model);
        } else {
            return toJsonObject((T) model);
        }
    }

    public Map<String, Object> toJsonObject(final T model) {
        throw new AbstractMethodError("The RxMicro Annotation Processor did not generate an implementation of this method!");
    }

    public final List<Object> toJsonObjectArray(final Collection<T> list) {
        if (list != null) {
            final List<Object> objects = new ArrayList<>();
            for (final T model : list) {
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

    protected final <E> Map<String, Object> convertFromObjectIfNotNull(final ModelToJsonConverter<E> converter,
                                                                       final E model) {
        return model != null ? converter.toJsonObject(model) : null;
    }

    protected final <E> List<Object> convertFromListIfNotNull(final ModelToJsonConverter<E> converter,
                                                              final List<E> list) {
        return list != null ? converter.toJsonObjectArray(list) : null;
    }

    protected final <E> List<Object> convertFromSetIfNotNull(final ModelToJsonConverter<E> converter,
                                                             final Set<E> list) {
        return list != null ? converter.toJsonObjectArray(list) : null;
    }

    protected final <E> Map<String, Object> convertFromMapIfNotNull(final ModelToJsonConverter<E> converter,
                                                                    final Map<String, E> model) {
        if (model != null) {
            final Map<String, Object> result = new LinkedHashMap<>();
            for (final Map.Entry<String, E> entry : model.entrySet()) {
                result.put(entry.getKey(), converter.toJsonObject(entry.getValue()));
            }
            return unmodifiableMap(result);
        } else {
            return null;
        }
    }
}
