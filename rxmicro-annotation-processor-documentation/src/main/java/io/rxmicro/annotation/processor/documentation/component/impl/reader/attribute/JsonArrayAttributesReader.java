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

package io.rxmicro.annotation.processor.documentation.component.impl.reader.attribute;

import io.rxmicro.annotation.processor.common.model.ModelField;
import io.rxmicro.json.JsonObjectBuilder;
import io.rxmicro.validation.constraint.MaxSize;
import io.rxmicro.validation.constraint.MinSize;
import io.rxmicro.validation.constraint.Size;
import io.rxmicro.validation.constraint.UniqueItems;

import java.util.List;
import java.util.function.BiConsumer;

/**
 * @author nedis
 * @since 0.4
 */
public final class JsonArrayAttributesReader {

    private static final String MIN_ITEMS = "minItems";

    private static final String MAX_ITEMS = "maxItems";

    private static final String UNIQUE_ITEMS = "uniqueItems";

    private final List<BiConsumer<ModelField, JsonObjectBuilder>> biConsumers = List.of(

            (modelField, builder) -> {
                final Size size = modelField.getAnnotation(Size.class);
                if (size != null && !size.off()) {
                    builder.put(MIN_ITEMS, size.value());
                    builder.put(MAX_ITEMS, size.value());
                }
            },

            (modelField, builder) -> {
                final MinSize minSize = modelField.getAnnotation(MinSize.class);
                if (minSize != null && !minSize.off()) {
                    builder.put(MIN_ITEMS, minSize.value());
                }
            },

            (modelField, builder) -> {
                final MaxSize maxSize = modelField.getAnnotation(MaxSize.class);
                if (maxSize != null && !maxSize.off()) {
                    builder.put(MAX_ITEMS, maxSize.value());
                }
            },

            (modelField, builder) -> {
                final UniqueItems uniqueItems = modelField.getAnnotation(UniqueItems.class);
                if (uniqueItems != null && !uniqueItems.off()) {
                    builder.put(UNIQUE_ITEMS, true);
                }
            }
    );

    public void read(final JsonObjectBuilder builder,
                     final ModelField modelField) {
        biConsumers.forEach(biConsumer -> biConsumer.accept(modelField, builder));
    }
}
