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

package io.rxmicro.model;

/**
 * Custom options for dynamic data repositories.
 *
 * @author nedis
 * @since 0.1
 */
public interface NotStandardSerializableEnum {

    /**
     * Converts enum value to the mongo specific format.
     *
     * @return the mongo specific format of the enum value
     * @throws UnsupportedOperationException if current instance if not Mongo enum
     */
    default int mongo() {
        throw new UnsupportedOperationException();
    }

    /**
     * Converts enum value to the SQL specific format.
     *
     * @return the SQL specific format of the enum value
     * @throws UnsupportedOperationException if current instance if not SQL enum
     */
    default String sql() {
        throw new UnsupportedOperationException();
    }
}
