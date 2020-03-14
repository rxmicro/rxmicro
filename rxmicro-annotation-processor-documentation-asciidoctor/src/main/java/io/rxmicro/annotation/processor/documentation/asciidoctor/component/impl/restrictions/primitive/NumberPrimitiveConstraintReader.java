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

package io.rxmicro.annotation.processor.documentation.asciidoctor.component.impl.restrictions.primitive;

import io.rxmicro.annotation.processor.common.model.type.ModelClass;
import io.rxmicro.annotation.processor.documentation.asciidoctor.component.impl.restrictions.ConstraintReader;
import io.rxmicro.annotation.processor.rest.model.RestModelField;
import io.rxmicro.common.meta.ReadMore;
import io.rxmicro.validation.constraint.Lat;
import io.rxmicro.validation.constraint.Lng;
import io.rxmicro.validation.constraint.MaxDouble;
import io.rxmicro.validation.constraint.MaxInt;
import io.rxmicro.validation.constraint.MaxNumber;
import io.rxmicro.validation.constraint.MinDouble;
import io.rxmicro.validation.constraint.MinInt;
import io.rxmicro.validation.constraint.MinNumber;
import io.rxmicro.validation.constraint.Numeric;

import javax.lang.model.type.TypeMirror;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import static io.rxmicro.annotation.processor.common.util.Annotations.getReadMore;
import static io.rxmicro.annotation.processor.common.util.Numbers.NUMBER_FORMATS;
import static io.rxmicro.annotation.processor.common.util.Numbers.convertIfNecessary;
import static io.rxmicro.common.util.Formats.format;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class NumberPrimitiveConstraintReader implements ConstraintReader {

    @Override
    public void readIfConstraintEnabled(final List<String> restrictions,
                                        final List<ReadMore> readMores,
                                        final Map.Entry<RestModelField, ModelClass> entry,
                                        final StringBuilder descriptionBuilder) {
        final RestModelField annotated = entry.getKey();
        getNumberType(entry.getKey().getFieldClass()).ifPresent(t ->
                restrictions.add(format("format: ?", t)));
        final Lat lat = annotated.getAnnotation(Lat.class);
        if (lat != null && !lat.off()) {
            descriptionBuilder.append("Latitude of GPS location point.");
            getReadMore(Lat.class).ifPresent(readMores::add);
        }
        final Lng lng = annotated.getAnnotation(Lng.class);
        if (lng != null && !lng.off()) {
            descriptionBuilder.append("Longitude of GPS location point.");
            getReadMore(Lng.class).ifPresent(readMores::add);
        }
        final MinDouble minDouble = annotated.getAnnotation(MinDouble.class);
        if (minDouble != null && !minDouble.off()) {
            restrictions.add(format("minimum: ?", formatNumber(minDouble.value())));
            restrictions.add(format("exclusiveMinimum: true"));
            getReadMore(MinDouble.class).ifPresent(readMores::add);
        }
        final MinInt minInt = annotated.getAnnotation(MinInt.class);
        if (minInt != null && !minInt.off()) {
            restrictions.add(format("minimum: ?", formatNumber(minInt.value())));
            restrictions.add(format("exclusiveMinimum: " + (!minInt.inclusive())));
            getReadMore(MinInt.class).ifPresent(readMores::add);
        }
        final MinNumber minNumber = annotated.getAnnotation(MinNumber.class);
        if (minNumber != null && !minNumber.off()) {
            restrictions.add(format("minimum: ?", formatNumber(minNumber.value())));
            restrictions.add(format("exclusiveMinimum: " + (!minNumber.inclusive())));
            getReadMore(MinNumber.class).ifPresent(readMores::add);
        }
        final MaxDouble maxDouble = annotated.getAnnotation(MaxDouble.class);
        if (maxDouble != null && !maxDouble.off()) {
            restrictions.add(format("maximum: ?", formatNumber(maxDouble.value())));
            restrictions.add(format("exclusiveMaximum: true"));
            getReadMore(MaxDouble.class).ifPresent(readMores::add);
        }
        final MaxInt maxInt = annotated.getAnnotation(MaxInt.class);
        if (maxInt != null && !maxInt.off()) {
            restrictions.add(format("maximum: ?", formatNumber(maxInt.value())));
            restrictions.add(format("exclusiveMaximum: " + (!maxInt.inclusive())));
            getReadMore(MaxInt.class).ifPresent(readMores::add);
        }
        final MaxNumber maxNumber = annotated.getAnnotation(MaxNumber.class);
        if (maxNumber != null && !maxNumber.off()) {
            restrictions.add(format("maximum: ?", formatNumber(maxNumber.value())));
            restrictions.add(format("exclusiveMaximum: " + (!maxNumber.inclusive())));
            getReadMore(MaxNumber.class).ifPresent(readMores::add);
        }
        final Numeric numeric = annotated.getAnnotation(Numeric.class);
        if (numeric != null && !numeric.off()) {
            if (numeric.precision() > -1) {
                restrictions.add("precision: " + numeric.precision());
            }
            if (numeric.scale() > -1) {
                restrictions.add("scale: " + numeric.scale());
            }
            getReadMore(Numeric.class).ifPresent(readMores::add);
        }
    }

    private Optional<String> getNumberType(final TypeMirror fieldClass) {
        return Optional.ofNullable(NUMBER_FORMATS.get(fieldClass.toString()));
    }

    private String formatNumber(final Number number) {
        return NumberFormat.getNumberInstance(Locale.ENGLISH).format(number);
    }

    private String formatNumber(final String number) {
        return formatNumber(new BigDecimal(convertIfNecessary(number)));
    }
}
