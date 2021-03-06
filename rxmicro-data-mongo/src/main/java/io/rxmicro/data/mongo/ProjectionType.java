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

package io.rxmicro.data.mongo;

import io.rxmicro.model.NotStandardSerializableEnum;

/**
 * Mongo DB projection type.
 *
 * @author nedis
 * @see io.rxmicro.data.SortOrder
 * @see io.rxmicro.data.Pageable
 * @see io.rxmicro.data.RepeatParameter
 * @see IndexUsage
 * @since 0.1
 */
public enum ProjectionType implements NotStandardSerializableEnum {

    /**
     * Includes a projection for Mongo DB query.
     */
    INCLUDE,

    /**
     * Excludes a projection for Mongo DB query.
     */
    EXCLUDE;

    @Override
    public int mongo() {
        return this == INCLUDE ? 1 : 0;
    }
}
