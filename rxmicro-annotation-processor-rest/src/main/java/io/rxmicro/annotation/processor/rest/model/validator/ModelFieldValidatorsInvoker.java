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

package io.rxmicro.annotation.processor.rest.model.validator;

import io.rxmicro.annotation.processor.common.util.UsedByFreemarker;
import io.rxmicro.annotation.processor.rest.model.RestModelField;

import java.util.List;

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @since 0.1
 */
public final class ModelFieldValidatorsInvoker {

    private final RestModelField field;

    private final List<ModelValidator> validators;

    ModelFieldValidatorsInvoker(final RestModelField field,
                                final List<ModelValidator> validators) {
        this.field = require(field);
        this.validators = require(validators);
    }

    public RestModelField getField() {
        return field;
    }

    @UsedByFreemarker
    public List<ModelValidator> getValidators() {
        return validators;
    }
}
