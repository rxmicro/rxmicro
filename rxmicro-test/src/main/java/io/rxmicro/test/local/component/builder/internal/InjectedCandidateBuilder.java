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

package io.rxmicro.test.local.component.builder.internal;

import io.rxmicro.test.local.model.internal.InjectedCandidate;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.rxmicro.common.local.DeniedPackages.isDeniedPackage;
import static io.rxmicro.common.util.ExCollections.unmodifiableMap;
import static io.rxmicro.common.util.GeneratedClassRules.isGeneratedClass;
import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.reflection.Reflections.allFields;
import static io.rxmicro.reflection.Reflections.getFieldValue;
import static io.rxmicro.test.local.util.Mocks.isMock;
import static io.rxmicro.test.local.util.UserComponents.isUserComponentField;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

/**
 * @author nedis
 * @since 0.1
 */
public final class InjectedCandidateBuilder {

    private final Set<Class<?>> types;

    public InjectedCandidateBuilder(final Set<Class<?>> types) {
        this.types = require(types);
    }

    public Map<Class<?>, List<InjectedCandidate>> build(final Object destinationInstance) {
        final Map<Class<?>, List<InjectedCandidate>> map = types.stream().collect(toMap(identity(), e -> new ArrayList<>()));
        extract(map, destinationInstance);
        return unmodifiableMap(map);
    }

    private void extract(final Map<Class<?>, List<InjectedCandidate>> map,
                         final Object destinationInstance) {
        if (destinationInstance == null ||
                isMock(destinationInstance) ||
                isGeneratedClass(destinationInstance.getClass()) ||
                isDeniedPackage(destinationInstance.getClass().getPackageName())) {
            return;
        }
        for (final Field field : allFields(destinationInstance.getClass(), f -> true)) {
            if (types.contains(field.getType())) {
                map.get(field.getType()).add(new InjectedCandidate(field, destinationInstance));
            } else if (isUserComponentField(field)) {
                final Object childInstance = getFieldValue(destinationInstance, field);
                extract(map, childInstance);
            }
        }
    }
}
