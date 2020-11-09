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

import java.util.List;
import java.util.Set;
import javax.lang.model.element.Element;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

import static io.rxmicro.annotation.processor.common.util.ProcessingEnvironmentHelper.getTypes;

/**
 * @author nedis
 * @since 0.7
 */
public final class CollectionIterableContainerElementExtractor implements IterableContainerElementExtractor {

    private final Set<String> classNames = Set.of(List.class.getName(), Set.class.getName());

    @Override
    public boolean isSupported(final TypeMirror type) {
        return classNames.contains(getTypes().erasure(type).toString());
    }

    @Override
    public TypeMirror getItemType(final Element owner,
                                  final DeclaredType type) {
        return type.getTypeArguments().get(0);
    }
}
