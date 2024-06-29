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

package io.rxmicro.config.internal.validator;

import io.rxmicro.config.ConfigException;
import io.rxmicro.validation.local.ConstraintViolationReporter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nedis
 * @since 0.12
 */
public final class DefaultConfigConstraintViolationReporter implements ConstraintViolationReporter {

    private final List<String> messages = new ArrayList<>();

    @Override
    public void reportViolation(final String message) {
        this.messages.add(message);
    }

    @Override
    public void onValidationCompleted() {
        if (messages.isEmpty()) {
            return;
        }
        throw new ConfigException(String.join("\n", messages));
    }
}
