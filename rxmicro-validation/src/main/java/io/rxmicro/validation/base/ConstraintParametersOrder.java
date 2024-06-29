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

package io.rxmicro.validation.base;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Defines an order of constraint annotation parameters.
 *
 * <p>
 * If a constraint validator depends on two or more parameters, then the constraint annotation must be annotated by the
 * {@link ConstraintParametersOrder} annotation, because the java reflection API does not allow reading method order.
 * <br>{@value ConstraintRule#OFF} is a specific parameter that defines common behaviour for any constraint annotation. That is why it is not
 * necessary to add this parameter to the array of constraint annotation parameters, because it will be ignored.
 *
 * @author nedis
 * @see io.rxmicro.validation.constraint.LatinAlphabetOnly
 * @see io.rxmicro.validation.constraint.Numeric
 * @see io.rxmicro.validation.constraint.Pattern
 * @see io.rxmicro.validation.constraint.Phone
 * @see io.rxmicro.validation.constraint.SubEnum
 * @since 0.10
 */
@Documented
@Retention(RUNTIME)
@Target(ANNOTATION_TYPE)
public @interface ConstraintParametersOrder {

    /**
     * Returns the order of constraint annotation parameters.
     *
     * @return the order of constraint annotation parameters.
     */
    String[] value();
}
