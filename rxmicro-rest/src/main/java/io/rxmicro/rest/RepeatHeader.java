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

import io.rxmicro.common.meta.SupportedTypes;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Informs the {@code RxMicro Annotation Processor}, that a header model field with array type
 * must be converted to the repeating HTTP header.
 *
 * <p>
 * By default, the RxMicro framework converts header model value as string, separated by the
 * {@value io.rxmicro.http.HttpValues#STRING_ARRAY_DELIMITER}) symbol. For example:
 *
 * <p>
 * {@code HeaderName: value1|value2|value3.}
 *
 * <p>
 * If this annotation is present, result will be:
 *
 * <p>
 * {@code HeaderName: value1}
 * {@code HeaderName: value2}
 * {@code HeaderName: value3}
 *
 * <p>
 * Supported for REST client HTTP request and REST controller handler HTTP response models only!
 *
 * @author nedis
 * @see io.rxmicro.http.HttpHeaders
 * @see io.rxmicro.http.HttpValues
 * @see AddHeader
 * @see SetHeader
 * @see Header
 * @see HeaderMappingStrategy
 * @since 0.1
 */
@Documented
@Retention(SOURCE)
@Target({FIELD, METHOD, PARAMETER})
@SupportedTypes(
        String.class
)
public @interface RepeatHeader {

}
