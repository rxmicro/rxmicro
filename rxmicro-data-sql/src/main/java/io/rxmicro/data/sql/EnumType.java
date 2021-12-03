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

package io.rxmicro.data.sql;

import io.rxmicro.model.MappingStrategy;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static io.rxmicro.common.CommonConstants.EMPTY_STRING;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Denotes a db type for enum.
 *
 * @author nedis
 * @see Schema
 * @see MappingStrategy
 * @since 0.1
 */
@Documented
@Retention(CLASS)
@Target(TYPE)
public @interface EnumType {

    /**
     * Returns the db type for enum.
     *
     * @return the db type for enum
     */
    String name() default EMPTY_STRING;

    /**
     * Returns the mapping strategy which is used to generate db type name using the simple class name automatically.
     *
     * @return the mapping strategy which is used to generate db type name using the simple class name automatically
     */
    MappingStrategy mappingStrategy() default MappingStrategy.LOWERCASE_WITH_UNDERSCORED;

    /**
     * Returns the schema name.
     *
     * @return the schema name
     */
    String schema() default EMPTY_STRING;
}
