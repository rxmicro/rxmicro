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

package io.rxmicro.config.internal;

import io.rxmicro.config.ConfigException;
import io.rxmicro.validation.local.ValidationOptions;

/**
 * @author nedis
 * @since 0.12
 */
public final class Validations {

    public static final ValidationOptions VALIDATION_OPTIONS = ValidationOptions.builder()
            .setTranslateConstraintViolationExceptionTo(ConfigException.class.getName())
            .build();

    private Validations() {
    }
}
