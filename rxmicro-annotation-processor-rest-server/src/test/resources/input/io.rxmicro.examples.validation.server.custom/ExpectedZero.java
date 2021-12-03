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

package io.rxmicro.examples.validation.server.custom;

import io.rxmicro.validation.base.ConstraintRule;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.math.BigDecimal;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.CLASS;

// tag::content[]
// <1>
@Retention(CLASS)
// <2>
@Target({FIELD, METHOD, PARAMETER})
// <3>
@ConstraintRule(
        supportedTypes = {
                BigDecimal.class                        // <4>
        },
        validatorClass = {
                ExpectedZeroConstraintValidator.class   // <5>
        }
)
public @interface ExpectedZero {

    boolean off() default false; // <6>

}
// end::content[]
