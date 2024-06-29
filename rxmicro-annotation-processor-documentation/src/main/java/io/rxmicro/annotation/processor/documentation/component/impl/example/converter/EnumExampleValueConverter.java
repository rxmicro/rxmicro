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

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.documentation.component.impl.example.ExampleValueConverter;
import io.rxmicro.annotation.processor.rest.model.RestModelField;
import io.rxmicro.validation.constraint.Enumeration;
import io.rxmicro.validation.constraint.SubEnum;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.lang.model.element.TypeElement;

import static io.rxmicro.annotation.processor.common.util.Elements.asEnumElement;
import static io.rxmicro.annotation.processor.common.util.Elements.getAllowedEnumConstants;
import static io.rxmicro.common.util.Formats.format;
import static java.util.Arrays.asList;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class EnumExampleValueConverter extends ExampleValueConverter {

    @Override
    public boolean isSupported(final RestModelField restModelField) {
        final Optional<TypeElement> typeElementOptional = asEnumElement(restModelField.getFieldClass());
        if (typeElementOptional.isPresent()) {
            return true;
        }
        final Enumeration enumeration = restModelField.getAnnotation(Enumeration.class);
        return enumeration != null && !enumeration.off();
    }

    @Override
    public Object convert(final RestModelField restModelField, final String value) {
        final Optional<TypeElement> typeElementOptional = asEnumElement(restModelField.getFieldClass());
        if (typeElementOptional.isPresent()) {
            return convertEnum(restModelField, typeElementOptional.get(), value);
        } else {
            final Enumeration enumeration = restModelField.getAnnotation(Enumeration.class);
            if (enumeration.value().length > 0) {
                if (Set.of(enumeration.value()).contains(value)) {
                    return value;
                } else {
                    showInvalidExampleValueError(
                            restModelField,
                            format("one from the set: ?", Arrays.toString(enumeration.value())),
                            value
                    );
                    return ERROR_DETECTED;
                }
            } else {
                throw new InterruptProcessingException(
                        restModelField.getElementAnnotatedBy(Enumeration.class),
                        "Missing enumeration values! Add at least one item!"
                );
            }
        }
    }

    private Object convertEnum(final RestModelField restModelField,
                               final TypeElement typeElement,
                               final String value) {
        final Set<String> enumConstants = getAllowedEnumConstants(typeElement.asType());
        if (enumConstants.isEmpty()) {
            throw new InterruptProcessingException(
                    restModelField.getFieldElement(),
                    "Missing enum constants! Add at least one constant to '?' class!",
                    typeElement.getQualifiedName()
            );
        }
        final Set<String> verifySet;
        final SubEnum subEnum = restModelField.getAnnotation(SubEnum.class);
        if (subEnum != null && !subEnum.off()) {
            if (subEnum.include().length > 0) {
                verifySet = Set.of(subEnum.include());
            } else {
                final Set<String> excludes = new HashSet<>(asList(subEnum.exclude()));
                verifySet = enumConstants.stream().filter(c -> !excludes.contains(c)).collect(Collectors.toSet());
                if (verifySet.isEmpty()) {
                    throw new InterruptProcessingException(
                            restModelField.getElementAnnotatedBy(SubEnum.class),
                            "All enum constants are excluded!"
                    );
                }
            }
        } else {
            verifySet = enumConstants;
        }
        if (verifySet.contains(value)) {
            return value;
        } else {
            showInvalidExampleValueError(
                    restModelField,
                    format("one from the set: ?", verifySet),
                    value
            );
            return ERROR_DETECTED;
        }
    }
}
