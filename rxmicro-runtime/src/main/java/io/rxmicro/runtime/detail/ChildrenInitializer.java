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

package io.rxmicro.runtime.detail;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import static io.rxmicro.common.local.InternalModules.isInternalModule;
import static io.rxmicro.common.util.ExCollectors.toOrderedSet;
import static io.rxmicro.runtime.detail.RxMicroRuntime.getEntryPointFullClassName;

/**
 * @author nedis
 * @since 0.10
 */
public final class ChildrenInitializer {

    private static final Set<String> INITIALIZED_CLASSES = new HashSet<>();

    public static void invokeAllStaticSections(final Module currentModule,
                                               final String simpleClassName) {
        if (INITIALIZED_CLASSES.add(simpleClassName)) {
            for (final Module module : getAllNotInternalModulesExceptCurrent(currentModule)) {
                try {
                    Class.forName(getEntryPointFullClassName(module, simpleClassName));
                } catch (final ClassNotFoundException ignored) {
                    // do nothing
                }
            }
        }
    }

    private static Set<Module> getAllNotInternalModulesExceptCurrent(final Module excludedModule) {
        final Predicate<Module> predicate = m -> {
            if (m.isNamed()) {
                if (isInternalModule(m.getName())) {
                    return false;
                } else if (excludedModule != null) {
                    return !m.getName().equals(excludedModule.getName());
                } else {
                    return true;
                }
            } else {
                return true;
            }
        };
        return ModuleLayer.boot().modules().stream()
                .filter(predicate)
                .collect(toOrderedSet());
    }

    public static void resetChildrenInitializer() {
        INITIALIZED_CLASSES.clear();
    }

    private ChildrenInitializer() {
    }
}
