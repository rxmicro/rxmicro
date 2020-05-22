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

package io.rxmicro.data.sql.model;

import java.util.Map;

/**
 * The base interface of virtual model that represents a database result set row as a {@link Map},
 * where {@code key} is a column name and {@code value} is a column value.
 *
 * <p>
 * This interface can be used instead of custom entity class.
 *
 * @author nedis
 * @see EntityFieldList
 * @since 0.1
 */
public interface EntityFieldMap extends Map<String, Object> {
}
