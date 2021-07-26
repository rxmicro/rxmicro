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

package io.rxmicro.rest;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static io.rxmicro.common.CommonConstants.EMPTY_STRING;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Maps HTTP header to annotated class field.
 *
 * <p>
 * <strong>The RxMicro framework uses the following algorithm to define the HTTP header name for the specified model field:</strong>
 * <ol>
 *     <li>
 *          If the field is annotated by the {@link Header} annotation with an explicit indication of the HTTP header name,
 *          the specified name is used.
 *     </li>
 *     <li>
 *          If no HTTP header name is specified in the {@link Header} annotation, the RxMicro framework checks for the
 *          {@link HeaderMappingStrategy} annotation above the model class.
 *     </li>
 *     <li>
 *          If the model class is annotated by the {@link HeaderMappingStrategy} annotation, then the specified naming strategy is used.
 *          (The field name is used as the basic name, and then, following the rules of the specified strategy,
 *          the HTTP header name is generated.)
 *     </li>
 *     <li>
 *          If the {@link HeaderMappingStrategy} annotation is missing, the model class field name is used as the HTTP header name.
 *     </li>
 * </ol>
 *
 * <p>
 * <strong>The RxMicro framework supports the following Java types, which can be HTTP request model headers:</strong>
 * <ul>
 *     <li>? extends {@link Enum}&lt;?&gt</li>
 *     <li>{@link Boolean}</li>
 *     <li>{@link Byte}</li>
 *     <li>{@link Short}</li>
 *     <li>{@link Integer}</li>
 *     <li>{@link Long}</li>
 *     <li>{@link java.math.BigInteger}</li>
 *     <li>{@link Float}</li>
 *     <li>{@link Double}</li>
 *     <li>{@link java.math.BigDecimal}</li>
 *     <li>{@link Character}</li>
 *     <li>{@link String}</li>
 *     <li>{@link java.time.Instant}</li>
 *     <li>{@link java.util.List}&lt;PRIMITIVE&gt</li>
 * </ul>
 *
 * @author nedis
 * @see AddHeader
 * @see SetHeader
 * @see HeaderMappingStrategy
 * @see RepeatHeader
 * @since 0.1
 */
@Documented
@Retention(SOURCE)
@Target({FIELD, METHOD, PARAMETER})
public @interface Header {

    /**
     * Returns the HTTP header name.
     *
     * @return the HTTP header name
     */
    String value() default EMPTY_STRING;
}
