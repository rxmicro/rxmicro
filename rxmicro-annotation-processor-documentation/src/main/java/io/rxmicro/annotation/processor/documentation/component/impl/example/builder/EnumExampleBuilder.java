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
import io.rxmicro.annotation.processor.common.model.ModelField;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.documentation.component.impl.example.TypeExampleBuilder;
import io.rxmicro.annotation.processor.rest.model.RestModelField;
import io.rxmicro.validation.constraint.Enumeration;
import io.rxmicro.validation.constraint.SubEnum;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static io.rxmicro.annotation.processor.common.util.Elements.asEnumElement;
import static io.rxmicro.annotation.processor.common.util.Elements.getAllowedEnumConstants;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class EnumExampleBuilder implements TypeExampleBuilder {

    @Override
    public boolean isSupported(final RestModelField restModelField,
                               final TypeMirror typeMirror) {
        final Optional<TypeElement> typeElementOptional = asEnumElement(typeMirror);
        if (typeElementOptional.isPresent()) {
            return true;
        }
        final Enumeration enumeration = restModelField.getAnnotation(Enumeration.class);
        return enumeration != null && !enumeration.off();
    }

    @Override
    public String getExample(final RestModelField restModelField,
                             final TypeMirror typeMirror) {
        final Optional<TypeElement> typeElementOptional = asEnumElement(typeMirror);
        if (typeElementOptional.isPresent()) {
            return getEnumExample(restModelField, typeElementOptional.get());
        } else {
            final Enumeration enumeration = restModelField.getAnnotation(Enumeration.class);
            if (enumeration.value().length > 0) {
                return enumeration.value()[0];
            } else {
                throw new InterruptProcessingException(
                        restModelField.getElementAnnotatedBy(Enumeration.class),
                        "Missing enumeration values! Add at least one item!"
                );
            }
        }
    }

    private String getEnumExample(final ModelField modelField,
                                  final TypeElement typeElement) {
        final Set<String> enumConstants = getAllowedEnumConstants(typeElement);
        if (enumConstants.isEmpty()) {
            throw new InterruptProcessingException(
                    modelField.getFieldElement(),
                    "Missing enum constants! Add at least one constant to '?' class!",
                    typeElement.getQualifiedName()
            );
        }
        final SubEnum subEnum = modelField.getAnnotation(SubEnum.class);
        if (subEnum != null && !subEnum.off()) {
            if (subEnum.include().length > 0) {
                return subEnum.include()[0];
            } else {
                final Set<String> excludes = new HashSet<>(Arrays.asList(subEnum.exclude()));
                return enumConstants.stream()
                        .filter(c -> !excludes.contains(c))
                        .findFirst()
                        .orElseThrow(() -> {
                            throw new InterruptProcessingException(
                                    modelField.getElementAnnotatedBy(SubEnum.class),
                                    "All enum constants are excluded!"
                            );
                        });
            }
        } else {
            return enumConstants.iterator().next();
        }
    }
}
