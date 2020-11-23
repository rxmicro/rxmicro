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

package io.rxmicro.annotation.processor.data.model;

import io.rxmicro.logger.RequestIdSupplier;

import java.util.function.Predicate;
import javax.lang.model.element.VariableElement;

/**
 * @author nedis
 * @since 0.7
 */
public final class CommonDataGroupRules {

    public static final String REQUEST_ID_SUPPLIER_GROUP = "REQUEST_ID_SUPPLIER_GROUP";

    public static final Predicate<VariableElement> REQUEST_ID_SUPPLIER_PREDICATE = expectedType(RequestIdSupplier.class);

    public static Predicate<VariableElement> expectedType(final Class<?> type) {
        return v -> type.getName().equals(v.asType().toString());
    }

    private CommonDataGroupRules() {
    }
}
