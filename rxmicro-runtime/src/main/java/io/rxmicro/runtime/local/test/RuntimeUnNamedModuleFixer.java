/*
 * Copyright 2019 https://rxmicro.io
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

package io.rxmicro.runtime.local.test;

import io.rxmicro.common.model.UnNamedModuleFixer;

/**
 * @author nedis
 * @since 0.1
 */
public final class RuntimeUnNamedModuleFixer extends UnNamedModuleFixer {

    @Override
    public void fix(final Module unNamedModule) {
        addOpens(
                getClass().getModule(),
                (currentModule, packageName) -> currentModule.addOpens(packageName, unNamedModule),
                "io.rxmicro.runtime.local",
                "io.rxmicro.runtime.local.error",
                "io.rxmicro.runtime.local.provider"
        );
    }
}
