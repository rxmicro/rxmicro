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

package io.rxmicro.annotation.processor.data.sql.model;

import io.rxmicro.data.sql.model.IsolationLevel;
import io.rxmicro.data.sql.operation.CustomSelect;

import java.util.function.Predicate;
import javax.lang.model.element.VariableElement;

import static io.rxmicro.annotation.processor.data.model.CommonDataGroupRules.expectedType;
import static io.rxmicro.data.sql.model.TransactionType.SUPPORTED_TRANSACTION_TYPES;

/**
 * @author nedis
 * @since 0.7
 */
public final class CommonSQLGroupRules {

    public static final String TRANSACTION_GROUP = "TRANSACTION_GROUP";

    public static final Predicate<VariableElement> TRANSACTION_PREDICATE = v ->
            SUPPORTED_TRANSACTION_TYPES.stream().anyMatch(supportedType -> supportedType.equals(v.asType().toString()));

    public static final String CUSTOM_SELECT_GROUP = "CUSTOM_SELECT_GROUP";

    public static final Predicate<VariableElement> CUSTOM_SELECT_PREDICATE = v ->
            v.getAnnotation(CustomSelect.class) != null;

    public static final String ISOLATION_LEVEL_GROUP = "ISOLATION_LEVEL_GROUP";

    public static final Predicate<VariableElement> ISOLATION_LEVEL_PREDICATE = expectedType(IsolationLevel.class);

    private CommonSQLGroupRules(){
    }
}
