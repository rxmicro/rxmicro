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

package io.rxmicro.runtime.local;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.reflect.Modifier.isPublic;

/**
 * @author nedis
 * @since 0.1
 */
public final class Beans {

    public static List<Method> findPublicSetters(final Class<?> classInstance) {
        final Set<String> methodNames = new HashSet<>();
        final List<Method> methods = new ArrayList<>();
        Class<?> current = classInstance;
        while (current != null && current != Object.class) {
            for (final Method method : current.getDeclaredMethods()) {
                if (isPublicSetter(method) &&
                        methodNames.add(method.getName() + method.getParameterTypes()[0].getName())) {
                    methods.add(method);
                }
            }
            current = current.getSuperclass();
        }
        return methods;
    }

    private static boolean isPublicSetter(final Method method) {
        return method.getName().startsWith("set") &&
                isPublic(method.getModifiers()) &&
                method.getParameterCount() == 1;
    }

    private Beans() {
    }
}
