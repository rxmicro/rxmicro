/*
 * Copyright 2019 https://rxmicro.io
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

package io.rxmicro.data;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Allows setting mapping between one method parameter marked with this annotation and several universal placeholders
 * that are used in the query to db.
 *
 * @author nedis
 * @since 0.1
 * @see SortOrder
 * @see Pageable
 */
@Documented
@Retention(SOURCE)
@Target(PARAMETER)
public @interface RepeatParameter {

    /**
     * Returns the repeat count
     *
     * @return the repeat count
     */
    int value();
}
