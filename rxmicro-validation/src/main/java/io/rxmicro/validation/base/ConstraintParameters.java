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
 * Describes annotation parameter names that will be passed to a constructor of correspond validator.
 *
 * <p><b>IMPORTANT!</b>
 * <p>An order of these parameters is important and should be the same as an order of parameters for a constructor of correspond validator.
 * <p>For example, if there is a constraint annotation with the following definition:
 * <pre><code>
 * &#064;ConstraintParameters({"string1", "string2", "integer1", "boolean1"})
 * public{@literal @}interface SomeConstraintAnnotation {
 *    String string1();
 *    String string1();
 *    int integer1();
 *    boolean boolean1();
 * }
 * </code></pre>
 * then, it is assumed that correspond validator that contains a validation logic for this annotation has the following constructor:
 * <pre><code>
 * public class SomeConstraintAnnotationValidator
 *                       implements ConstraintValidator&lt;SomeType&gt; {
 *     public SomeConstraintAnnotation(final String string1,
 *                                     final String string2,
 *                                     final int integer1,
 *                                     final boolean boolean1){
 *        ...
 *     }
 *
 *     ...
 * }
 * </code></pre>
 *
 * <p>
 * <br>{@value ConstraintRule#OFF} is a specific parameter that defines common behaviour for any constraint annotation. That is why,
 * it is not necessary to add this parameter to the array of constraint annotation parameters, because it will be ignored.
 *
 * @author nedis
 * @see io.rxmicro.validation.constraint.LatinAlphabetOnly
 * @see io.rxmicro.validation.constraint.Numeric
 * @see io.rxmicro.validation.constraint.Pattern
 * @see io.rxmicro.validation.constraint.Phone
 * @see io.rxmicro.validation.constraint.SubEnum
 * @since 0.10
 * @implNote
 * <p>
 * If a constraint annotation is not annotated by {@link ConstraintParameters} one, it means that this constraint annotation does not have
 * any annotation parameters <i>(FYI: The {@value ConstraintRule#OFF} is a mandatory (not annotation) parameter)</i> and it means that
 * correspond validator is a stateless validator, i.e. a single instance of this validator can be used for validations of any amount of
 * models with this annotation.
 */
@Documented
@Retention(RUNTIME)
@Target(ANNOTATION_TYPE)
public @interface ConstraintParameters {

    /**
     * Returns the ordered annotation parameter names.
     *
     * @return the ordered annotation parameter names.
     */
    String[] value();
}
