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

package io.rxmicro.test.local.util;

/**
 * @author nedis
 * @since 0.1
 */
public final class Modules {

    public static boolean isRequiredModule(final Module projectModule,
                                           final Module requiredModule) {
        return requiredModule.isNamed() && projectModule.canRead(requiredModule);
    }

    private Modules() {
    }
}
