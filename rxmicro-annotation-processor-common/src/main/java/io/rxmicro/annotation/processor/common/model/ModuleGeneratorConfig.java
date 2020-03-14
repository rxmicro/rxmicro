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

package io.rxmicro.annotation.processor.common.model;

import io.rxmicro.rest.model.GenerateOption;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public abstract class ModuleGeneratorConfig {

    protected final boolean getOption(final GenerateOption option,
                                      final boolean autoValue) {
        if (option == GenerateOption.DISABLED) {
            return false;
        } else {
            return autoValue;
        }
    }

    @Override
    public final int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public final boolean equals(final Object obj) {
        return obj != null && getClass() == obj.getClass();
    }
}
