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

package io.rxmicro.annotation.processor.documentation.component.impl.example.builder;

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.documentation.component.impl.example.TypeExampleBuilder;
import io.rxmicro.annotation.processor.rest.model.RestModelField;
import io.rxmicro.json.JsonNumber;
import io.rxmicro.validation.base.LocationAccuracy;
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
import java.math.BigInteger;
import java.util.Set;

import static io.rxmicro.annotation.processor.common.util.Numbers.removeUnderscoresIfPresent;
import static java.math.RoundingMode.HALF_UP;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class NumberExampleBuilder implements TypeExampleBuilder {

    private static final Set<String> NUMBERS = Set.of(
            Byte.class.getName(),
            Short.class.getName(),
            Integer.class.getName(),
            Long.class.getName(),
            Float.class.getName(),
            Double.class.getName(),
            BigDecimal.class.getName(),
            BigInteger.class.getName()
    );

    @Override
    public boolean isSupported(final RestModelField restModelField,
                               final TypeMirror typeMirror) {
        return NUMBERS.contains(typeMirror.toString());
    }

    @Override
    public JsonNumber getExample(final RestModelField restModelField,
                                 final TypeMirror typeMirror) {
        final Lat lat = restModelField.getAnnotation(Lat.class);
        if (lat != null && !lat.off()) {
            return withLocationAccuracy("34.063821", lat.value());
        }
        final Lng lng = restModelField.getAnnotation(Lng.class);
        if (lng != null && !lng.off()) {
            return withLocationAccuracy("-118.296339", lng.value());
        }
        final MaxDouble maxDouble = restModelField.getAnnotation(MaxDouble.class);
        if (maxDouble != null && !maxDouble.off()) {
            return new JsonNumber(String.valueOf(maxDouble.value()));
        }
        final MaxInt maxInt = restModelField.getAnnotation(MaxInt.class);
        if (maxInt != null && !maxInt.off()) {
            return new JsonNumber(String.valueOf(maxInt.value()));
        }
        final MaxNumber maxNumber = restModelField.getAnnotation(MaxNumber.class);
        if (maxNumber != null && !maxNumber.off()) {
            return new JsonNumber(removeUnderscoresIfPresent(maxNumber.value()));
        }
        final MinDouble minDouble = restModelField.getAnnotation(MinDouble.class);
        if (minDouble != null && !minDouble.off()) {
            return new JsonNumber(String.valueOf(minDouble.value()));
        }
        final MinInt minInt = restModelField.getAnnotation(MinInt.class);
        if (minInt != null && !minInt.off()) {
            return new JsonNumber(String.valueOf(minInt.value()));
        }
        final MinNumber minNumber = restModelField.getAnnotation(MinNumber.class);
        if (minNumber != null && !minNumber.off()) {
            return new JsonNumber(removeUnderscoresIfPresent(minNumber.value()));
        }
        final Numeric numeric = restModelField.getAnnotation(Numeric.class);
        if (numeric != null && !numeric.off()) {
            final BigDecimal bigDecimal = new BigDecimal("987654321.987654321");
            if (numeric.scale() > -1) {
                return new JsonNumber(bigDecimal.setScale(numeric.scale(), HALF_UP).toPlainString());
            } else {
                return new JsonNumber(bigDecimal.toPlainString());
            }
        }
        // By type
        if (Byte.class.getName().equals(typeMirror.toString())) {
            return new JsonNumber("10");
        } else if (Short.class.getName().equals(typeMirror.toString())) {
            return new JsonNumber(removeUnderscoresIfPresent("10_000"));
        } else if (Integer.class.getName().equals(typeMirror.toString())) {
            return new JsonNumber(removeUnderscoresIfPresent("1_000_000_000"));
        } else if (Long.class.getName().equals(typeMirror.toString())) {
            return new JsonNumber(removeUnderscoresIfPresent("1_000_000_000_000"));
        } else if (BigInteger.class.getName().equals(typeMirror.toString())) {
            return new JsonNumber(removeUnderscoresIfPresent("1_000_000_000_000_000_000_000"));
        } else if (Float.class.getName().equals(typeMirror.toString())) {
            return new JsonNumber("3.14");
        } else if (Double.class.getName().equals(typeMirror.toString())) {
            return new JsonNumber("3.1415926535");
        } else /*BigDecimal*/ {
            return new JsonNumber("3.1415926535897932384626433832795028841971");
        }
    }

    private JsonNumber withLocationAccuracy(final String value,
                                            final LocationAccuracy locationAccuracy) {
        return new JsonNumber(
                new BigDecimal(value)
                        .setScale(locationAccuracy.getCoordinateScale(), HALF_UP)
                        .toPlainString()
        );
    }
}
