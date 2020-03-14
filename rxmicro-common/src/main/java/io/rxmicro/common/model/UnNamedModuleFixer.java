/*
 * Copyright (c) 2020. http://rxmicro.io
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

import static io.rxmicro.common.util.Formats.format;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public abstract class UnNamedModuleFixer {

    public abstract void fix(Module unNamedModule);

    protected final void addOpens(final Module currentModule,
                                  final BiConsumer<Module, String> addOpenConsumer,
                                  final String... packageNames) {
        if (currentModule.isNamed()) {
            Arrays.stream(packageNames).forEach(p -> {
                System.out.println(format("opens ?/? to ALL-UNNAMED", currentModule.getName(), p));
                addOpenConsumer.accept(currentModule, p);
            });
        }
    }
}
