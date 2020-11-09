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

package io.rxmicro.annotation.processor.common.component.impl;

import io.rxmicro.annotation.processor.common.component.IterableContainerElementExtractor;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;

import java.util.Map;
import javax.lang.model.element.Element;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

import static io.rxmicro.annotation.processor.common.util.ProcessingEnvironmentHelper.getTypes;

/**
 * @author nedis
 * @since 0.7
 */
public final class MapWithStringKeysIterableContainerElementExtractor implements IterableContainerElementExtractor {

    @Override
    public boolean isSupported(final TypeMirror type) {
        return Map.class.getName().equals(getTypes().erasure(type).toString());
    }

    @Override
    public TypeMirror getItemType(final Element owner,
                                  final DeclaredType type) {
        final TypeMirror actualKeyType = type.getTypeArguments().get(0);
        final TypeMirror valueType = type.getTypeArguments().get(1);
        if (!String.class.getName().equals(actualKeyType.toString())) {
            throw new InterruptProcessingException(
                    owner,
                    "Use Map<java.lang.String, ?> instead of Map<?, ?>!",
                    valueType,
                    actualKeyType,
                    valueType
            );
        }

        return valueType;
    }
}
