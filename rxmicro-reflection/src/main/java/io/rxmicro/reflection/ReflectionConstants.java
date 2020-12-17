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

package io.rxmicro.reflection;

/**
 * The constants for the {@code rxmicro.reflection} module.
 *
 * @author nedis
 * @since 0.7.4
 */
public final class ReflectionConstants {

    /**
     * The reference to the {@link Module} object of the {@code rxmicro.reflection} module.
     */
    public static final Module RX_MICRO_REFLECTION_MODULE = ReflectionConstants.class.getModule();

    private ReflectionConstants() {
    }
}
