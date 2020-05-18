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
import io.rxmicro.validation.constraint.Lat;
import io.rxmicro.validation.constraint.Lng;
import io.rxmicro.validation.constraint.MaxDouble;
import io.rxmicro.validation.constraint.MaxInt;
import io.rxmicro.validation.constraint.MaxNumber;
import io.rxmicro.validation.constraint.MinDouble;
import io.rxmicro.validation.constraint.MinInt;
import io.rxmicro.validation.constraint.MinNumber;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiConsumer;

import static io.rxmicro.annotation.processor.common.util.Numbers.removeUnderscoresIfPresent;

/**
 * @author nedis
 * @since 0.4
 */
public final class JsonNumberAttributesReader {

    private static final String MINIMUM = "minimum";

    private static final String EXCLUSIVE_MINIMUM = "exclusiveMinimum";

    private static final String MAXIMUM = "maximum";

    private static final String EXCLUSIVE_MAXIMUM = "exclusiveMaximum";

    private static final int MIN_LAT = -90;

    private static final int MAX_LAT = 90;

    private static final int MIN_LNG = -180;

    private static final int MAX_LNG = 180;

    private final List<BiConsumer<ModelField, JsonObjectBuilder>> biConsumers = List.of(

            (modelField, builder) -> {
                final Lat lat = modelField.getAnnotation(Lat.class);
                if (lat != null && !lat.off()) {
                    builder.put(MINIMUM, MIN_LAT);
                    builder.put(EXCLUSIVE_MINIMUM, false);
                    builder.put(MAXIMUM, MAX_LAT);
                    builder.put(EXCLUSIVE_MAXIMUM, false);
                }
            },

            (modelField, builder) -> {
                final Lng lng = modelField.getAnnotation(Lng.class);
                if (lng != null && !lng.off()) {
                    builder.put(MINIMUM, MIN_LNG);
                    builder.put(EXCLUSIVE_MINIMUM, false);
                    builder.put(MAXIMUM, MAX_LNG);
                    builder.put(EXCLUSIVE_MAXIMUM, false);
                }
            },

            (modelField, builder) -> {
                final MinDouble minDouble = modelField.getAnnotation(MinDouble.class);
                if (minDouble != null && !minDouble.off()) {
                    builder.put(MINIMUM, minDouble.value());
                    builder.put(EXCLUSIVE_MINIMUM, false);
                }
            },

            (modelField, builder) -> {
                final MinInt minInt = modelField.getAnnotation(MinInt.class);
                if (minInt != null && !minInt.off()) {
                    builder.put(MINIMUM, minInt.value());
                    builder.put(EXCLUSIVE_MINIMUM, !minInt.inclusive());
                }
            },

            (modelField, builder) -> {
                final MinNumber minNumber = modelField.getAnnotation(MinNumber.class);
                if (minNumber != null && !minNumber.off()) {
                    builder.put(MINIMUM, new BigDecimal(removeUnderscoresIfPresent(minNumber.value())));
                    builder.put(EXCLUSIVE_MINIMUM, !minNumber.inclusive());
                }
            },

            (modelField, builder) -> {
                final MaxDouble maxDouble = modelField.getAnnotation(MaxDouble.class);
                if (maxDouble != null && !maxDouble.off()) {
                    builder.put(MAXIMUM, maxDouble.value());
                    builder.put(EXCLUSIVE_MAXIMUM, false);
                }
            },

            (modelField, builder) -> {
                final MaxInt maxInt = modelField.getAnnotation(MaxInt.class);
                if (maxInt != null && !maxInt.off()) {
                    builder.put(MAXIMUM, maxInt.value());
                    builder.put(EXCLUSIVE_MAXIMUM, !maxInt.inclusive());
                }
            },

            (modelField, builder) -> {
                final MaxNumber maxNumber = modelField.getAnnotation(MaxNumber.class);
                if (maxNumber != null && !maxNumber.off()) {
                    builder.put(MAXIMUM, new BigDecimal(removeUnderscoresIfPresent(maxNumber.value())));
                    builder.put(EXCLUSIVE_MAXIMUM, !maxNumber.inclusive());
                }
            }
    );


    public void read(final JsonObjectBuilder builder,
                     final ModelField modelField) {
        biConsumers.forEach(biConsumer -> biConsumer.accept(modelField, builder));
    }
}
