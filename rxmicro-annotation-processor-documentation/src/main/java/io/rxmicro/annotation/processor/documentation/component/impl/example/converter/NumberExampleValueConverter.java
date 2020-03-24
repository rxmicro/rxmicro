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

package io.rxmicro.annotation.processor.documentation.component.impl.example.converter;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.component.NumberValidators;
import io.rxmicro.annotation.processor.documentation.component.impl.example.ExampleValueConverter;
import io.rxmicro.annotation.processor.rest.model.RestModelField;
import io.rxmicro.documentation.Example;
import io.rxmicro.json.JsonNumber;

import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

import static io.rxmicro.annotation.processor.common.util.Numbers.removeUnderscoresIfPresent;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@Singleton
public final class NumberExampleValueConverter extends ExampleValueConverter {

    private final Map<String, RangeValidator> validatorTypeMap;

    @Inject
    public NumberExampleValueConverter(final NumberValidators numberValidators) {
        validatorTypeMap = Map.of(
                Byte.class.getName(), numberValidators::validateByte,
                Short.class.getName(), numberValidators::validateShort,
                Integer.class.getName(), numberValidators::validateInteger,
                Long.class.getName(), numberValidators::validateLong,
                Float.class.getName(), numberValidators::validateFloat,
                Double.class.getName(), numberValidators::validateDouble,
                BigInteger.class.getName(), numberValidators::validateBigInteger,
                BigDecimal.class.getName(), numberValidators::validateBigDecimal
        );
    }

    @Override
    public boolean isSupported(final RestModelField restModelField) {
        return validatorTypeMap.containsKey(restModelField.getFieldClass().toString());
    }

    @Override
    public Object convert(final RestModelField restModelField,
                          final String value) {
        final String v = removeUnderscoresIfPresent(value);
        if (validatorTypeMap.get(restModelField.getFieldClass().toString()).validate(restModelField, value, Example.class)) {
            return new JsonNumber(v);
        } else {
            return ERROR_DETECTED;
        }
    }

    /**
     * @author nedis
     * @link https://rxmicro.io
     * @since 0.1
     */
    private interface RangeValidator {

        boolean validate(RestModelField restModelField,
                         String value,
                         Class<? extends Annotation> annotationClass);
    }
}
