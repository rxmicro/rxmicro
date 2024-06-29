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

package io.rxmicro.validation.local;

import io.rxmicro.common.meta.BuilderMethod;
import io.rxmicro.model.BaseModel;

/**
 * @author nedis
 * @since 0.12
 */
public final class ValidationOptions extends BaseModel {

    /**
     * @implNote The {@link String} type is used instead of {@link Class} one, because sometimes we need to enable
     * translation to exception class that is not available at compilation time.
     */
    private String translateConstraintViolationExceptionTo;

    public String getTranslateConstraintViolationExceptionTo() {
        return translateConstraintViolationExceptionTo;
    }

    @BuilderMethod
    public ValidationOptions setTranslateConstraintViolationExceptionTo(final String translateConstraintViolationExceptionTo) {
        this.translateConstraintViolationExceptionTo = translateConstraintViolationExceptionTo;
        return this;
    }
}
