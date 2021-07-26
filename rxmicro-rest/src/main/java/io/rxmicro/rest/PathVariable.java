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
 * Maps HTTP path variable to annotated class field.
 *
 * <p>
 * <strong>The RxMicro framework supports the following Java types, which can be path variables of the request model:</strong>
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
 * @see Parameter
 * @see Header
 * @since 0.1
 */
@Documented
@Retention(SOURCE)
@Target({FIELD, METHOD, PARAMETER})
public @interface PathVariable {

    /**
     * By default, HTTP path variable name equals to field name.
     *
     * @return the HTTP path variable
     */
    String value() default EMPTY_STRING;
}
