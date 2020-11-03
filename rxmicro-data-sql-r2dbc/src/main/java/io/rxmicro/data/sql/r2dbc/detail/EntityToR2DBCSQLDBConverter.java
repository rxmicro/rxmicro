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

package io.rxmicro.data.sql.r2dbc.detail;

import io.rxmicro.data.local.EntityToDBConverter;

/**
 * Used by generated code that created by the {@code RxMicro Annotation Processor}.
 *
 * @author nedis
 * @hidden
 * @since 0.1
 */
public abstract class EntityToR2DBCSQLDBConverter<E, DB> implements EntityToDBConverter {

    protected final String trimNotNull(final String value,
                                       final int length) {
        return value.length() > length ? value.substring(0, length) : value;
    }

    protected final String trimNullable(final String value,
                                        final int length) {
        return value != null && value.length() > length ? value.substring(0, length) : value;
    }
}
