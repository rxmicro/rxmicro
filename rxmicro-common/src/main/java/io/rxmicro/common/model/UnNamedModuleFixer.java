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

package io.rxmicro.common.model;

import java.util.Arrays;
import java.util.function.BiConsumer;

import static io.rxmicro.common.util.TestLoggers.logInfoTestMessage;

/**
 * Java 9 has introduced the <a href="https://www.oracle.com/corporate/features/understanding-java-9-modules.html">JPMS</a>.
 *
 * <p>
 * This system requires that a developer defines the {@code module-info.java} descriptor for each project.
 * In this descriptor, the developer must describe all the dependencies of the current project.
 * In the context of the unit module system, the tests required for each project should be configured as a separate module,
 * since they depend on libraries that should not be available in the runtime.
 * Usually such libraries are unit testing libraries (e.g. {@code JUnit 5}), mock creation libraries (e.g. {@code Mockito}), etc.
 *
 * <p>
 * When trying to create a separate module-info.java descriptor available only for unit tests, many modern IDEs report an error.
 *
 * <p>
 * Therefore, the simplest and most common solution to this problem is to organize unit tests in the form of automatic module.
 * This solution allows correcting compilation errors, but when starting tests, there will be runtime errors.
 * To fix runtime errors, when starting the Java virtual machine, the RxMicro framework provide {@link UnNamedModuleFixer} basic class.
 *
 * <p>
 * <i>(This class can be used by internal RxMicro modules only!)</i>
 *
 * @author nedis
 * @see Module
 * @see ClassLoader#getUnnamedModule()
 * @since 0.1
 */
public abstract class UnNamedModuleFixer {

    /**
     * Adds missing exports or opens for the current {@link Module}.
     *
     * @param unNamedModule the reference to the unnamed {@link Module}.
     */
    public abstract void fix(Module unNamedModule);

    /**
     * Adds opens or exports instructions.
     *
     * @param currentModule the current module.
     * @param consumer the action consumer.
     * @param packageNames the package names.
     */
    protected final void addOpensOrExports(final Module currentModule,
                                           final BiConsumer<Module, String> consumer,
                                           final String... packageNames) {
        if (currentModule.isNamed()) {
            Arrays.stream(packageNames).forEach(packageName -> {
                logInfoTestMessage("opens ?/? to ALL-UNNAMED", currentModule.getName(), packageName);
                consumer.accept(currentModule, packageName);
            });
        }
    }
}
