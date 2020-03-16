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

package io.rxmicro.annotation.processor.common.util;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * This annotation marks a method or field, which is used by Freemarker template(s)
 *
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@Documented
@Retention(SOURCE)
@Target({METHOD, FIELD})
public @interface UsedByFreemarker {

    /**
     * @return freemarker template names which are used method or field
     */
    String[] value() default {};
}
