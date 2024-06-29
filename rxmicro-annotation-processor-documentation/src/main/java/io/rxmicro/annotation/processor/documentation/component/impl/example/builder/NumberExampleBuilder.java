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
import io.rxmicro.common.ImpossibleException;
import io.rxmicro.json.JsonNumber;
import io.rxmicro.validation.base.LocationAccuracy;
import io.rxmicro.validation.constraint.Lat;
import io.rxmicro.validation.constraint.Lng;
import io.rxmicro.validation.constraint.MaxDouble;
import io.rxmicro.validation.constraint.MaxInt;
import io.rxmicro.validation.constraint.Max;
import io.rxmicro.validation.constraint.MinDouble;
import io.rxmicro.validation.constraint.MinInt;
import io.rxmicro.validation.constraint.Min;
import io.rxmicro.validation.constraint.Numeric;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import javax.lang.model.type.TypeMirror;

import static io.rxmicro.annotation.processor.common.util.Numbers.removeUnderscoresIfPresent;
import static java.math.RoundingMode.HALF_UP;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class NumberExampleBuilder implements TypeExampleBuilder {

    private final Map<String, Supplier<JsonNumber>> exampleByTypeMap = Map.of(
            Byte.class.getName(), () -> new JsonNumber("10"),
            Short.class.getName(), () -> new JsonNumber(removeUnderscoresIfPresent("10_000")),
            Integer.class.getName(), () -> new JsonNumber(removeUnderscoresIfPresent("1_000_000_000")),
            Long.class.getName(), () -> new JsonNumber(removeUnderscoresIfPresent("1_000_000_000_000")),
            BigInteger.class.getName(), () -> new JsonNumber(removeUnderscoresIfPresent("1_000_000_000_000_000_000_000")),
            Float.class.getName(), () -> new JsonNumber("3.14"),
            Double.class.getName(), () -> new JsonNumber("3.1415926535"),
            BigDecimal.class.getName(), () -> new JsonNumber("3.1415926535897932384626433832795028841971"),
            JsonNumber.class.getName(), () -> new JsonNumber("3.1415926535897932384626433832795028841971")
    );

    private final List<JsonNumberExampleBuilder> jsonNumberExampleBuilders = List.of(

            restModelField -> Optional.ofNullable(restModelField.getAnnotation(Lat.class))
                    .filter(a -> !a.off())
                    .map(lat -> withLocationAccuracy("34.063821", lat.value())),

            restModelField -> Optional.ofNullable(restModelField.getAnnotation(Lng.class))
                    .filter(a -> !a.off())
                    .map(lng -> withLocationAccuracy("-118.296339", lng.value())),

            restModelField -> Optional.ofNullable(restModelField.getAnnotation(MaxDouble.class))
                    .filter(a -> !a.off())
                    .map(maxDouble -> new JsonNumber(String.valueOf(maxDouble.value()))),

            restModelField -> Optional.ofNullable(restModelField.getAnnotation(MaxInt.class))
                    .filter(a -> !a.off())
                    .map(maxInt -> new JsonNumber(String.valueOf(maxInt.value()))),

            restModelField -> Optional.ofNullable(restModelField.getAnnotation(Max.class))
                    .filter(a -> !a.off())
                    .map(maxNumber -> new JsonNumber(removeUnderscoresIfPresent(maxNumber.value()))),

            restModelField -> Optional.ofNullable(restModelField.getAnnotation(MinDouble.class))
                    .filter(a -> !a.off())
                    .map(minDouble -> new JsonNumber(String.valueOf(minDouble.value()))),

            restModelField -> Optional.ofNullable(restModelField.getAnnotation(MinInt.class))
                    .filter(a -> !a.off())
                    .map(minInt -> new JsonNumber(String.valueOf(minInt.value()))),

            restModelField -> Optional.ofNullable(restModelField.getAnnotation(Min.class))
                    .filter(a -> !a.off())
                    .map(minNumber -> new JsonNumber(removeUnderscoresIfPresent(minNumber.value()))),

            restModelField -> Optional.ofNullable(restModelField.getAnnotation(Numeric.class))
                    .filter(a -> !a.off())
                    .map(numeric -> {
                        final BigDecimal bigDecimal = new BigDecimal("987654321.987654321");
                        if (numeric.scale() > -1) {
                            return new JsonNumber(bigDecimal.setScale(numeric.scale(), HALF_UP).toPlainString());
                        } else {
                            return new JsonNumber(bigDecimal.toPlainString());
                        }
                    })
    );

    @Override
    public boolean isSupported(final RestModelField restModelField,
                               final TypeMirror typeMirror) {
        return exampleByTypeMap.containsKey(typeMirror.toString());
    }

    @Override
    public JsonNumber getExample(final RestModelField restModelField,
                                 final TypeMirror typeMirror) {
        return jsonNumberExampleBuilders.stream()
                .flatMap(builder -> builder.build(restModelField).stream())
                .findFirst()
                .orElseGet(() -> getExampleByType(typeMirror));
    }

    private JsonNumber withLocationAccuracy(final String value,
                                            final LocationAccuracy locationAccuracy) {
        return new JsonNumber(
                new BigDecimal(value)
                        .setScale(locationAccuracy.getCoordinateScale(), HALF_UP)
                        .toPlainString()
        );
    }

    private JsonNumber getExampleByType(final TypeMirror typeMirror) {
        return Optional.ofNullable(exampleByTypeMap.get(typeMirror.toString()))
                .orElseThrow(() -> new ImpossibleException("Example by number type must be found!"))
                .get();
    }

    /**
     * @author nedis
     * @since 0.4
     */
    private interface JsonNumberExampleBuilder {

        Optional<JsonNumber> build(RestModelField restModelField);
    }
}
