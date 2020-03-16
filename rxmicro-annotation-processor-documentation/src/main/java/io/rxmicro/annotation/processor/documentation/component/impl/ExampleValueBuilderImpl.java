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

package io.rxmicro.annotation.processor.documentation.component.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.model.definition.SupportedTypesProvider;
import io.rxmicro.annotation.processor.documentation.component.ExampleValueBuilder;
import io.rxmicro.annotation.processor.documentation.component.impl.example.ExampleValueConverter;
import io.rxmicro.annotation.processor.documentation.component.impl.example.TypeExampleBuilder;
import io.rxmicro.annotation.processor.rest.model.RestModelField;
import io.rxmicro.documentation.Example;

import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toUnmodifiableList;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@Singleton
public final class ExampleValueBuilderImpl implements ExampleValueBuilder {

    private final Map<RestModelField, List<Object>> cache = new HashMap<>();

    @Inject
    private Set<TypeExampleBuilder> exampleBuilders;

    @Inject
    private Set<ExampleValueConverter> exampleValueConverters;

    @Inject
    private SupportedTypesProvider supportedTypesProvider;

    @Override
    public List<Object> getExamples(final RestModelField restModelField) {
        return cache.computeIfAbsent(restModelField, m -> extractExampleFromField(restModelField));
    }

    private List<Object> extractExampleFromField(final RestModelField restModelField) {
        final Example[] examples = restModelField.getAnnotationsByType(Example.class);
        if (examples.length > 0) {
            return Arrays.stream(examples)
                    .map(example -> exampleValueConverters.stream()
                            .filter(b -> b.isSupported(restModelField))
                            .map(b -> b.convert(restModelField, example.value())).findFirst()
                            .orElse(example.value())
                    )
                    .collect(toUnmodifiableList());
        } else {
            final TypeMirror fieldClass;
            if (supportedTypesProvider.primitiveContainers().contains(restModelField.getFieldClass())) {
                fieldClass = ((DeclaredType) restModelField.getFieldClass()).getTypeArguments().get(0);
            } else {
                fieldClass = restModelField.getFieldClass();
            }
            return singletonList(
                    exampleBuilders.stream()
                            .filter(b -> b.isSupported(restModelField, fieldClass))
                            .map(b -> b.getExample(restModelField, fieldClass)).findFirst()
                            .orElse(null)
            );
        }
    }
}
