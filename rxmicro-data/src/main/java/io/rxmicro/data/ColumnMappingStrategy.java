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

package io.rxmicro.data;

import io.rxmicro.model.MappingStrategy;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Sets the strategy of column name formation in the DB table (document), based on the analysis of the Java model class field names.
 *
 * <p>
 * If this annotation annotates the Java model class, then the set strategy will be used for all fields in this class.
 *
 * <p>
 * For example, if You set the default {@link MappingStrategy#LOWERCASE_WITH_UNDERSCORED} strategy, then the {@code parentId}
 * field in the Java class will correspond to the {@code parent_id} column in the DB table (or document).)
 *
 * <p>
 * <strong>The RxMicro framework uses the following algorithm to define the column name for the specified model field:</strong>
 * <ol>
 *     <li>
 *          If the field is annotated by the {@link Column} annotation with an explicit indication of the column name,
 *          the specified name is used.
 *     </li>
 *     <li>
 *          If no column name is specified in the {@link Column} annotation, the RxMicro framework checks for the
 *          {@link ColumnMappingStrategy} annotation above the model class.
 *     </li>
 *     <li>
 *          If the model class is annotated by the {@link ColumnMappingStrategy} annotation, then the specified naming strategy is used.
 *          (The field name is used as the basic name, and then, following the rules of the specified strategy,
 *          the column name is generated.)
 *     </li>
 *     <li>
 *          If the {@link ColumnMappingStrategy} annotation is missing, the model class field name is used as the column name.
 *     </li>
 * </ol>
 *
 * @author nedis
 * @see Column
 * @see MappingStrategy
 * @since 0.1
 */
@Documented
@Retention(SOURCE)
@Target(TYPE)
public @interface ColumnMappingStrategy {

    /**
     * Returns the mapping strategy which must be used for ORM.
     *
     * @return the mapping strategy which must be used for ORM
     */
    MappingStrategy value() default MappingStrategy.LOWERCASE_WITH_UNDERSCORED;
}
