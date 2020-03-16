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

import io.rxmicro.validation.ConstraintValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Use this annotation to define custom validator
 *
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@Documented
@Retention(RUNTIME)
@Target({ANNOTATION_TYPE})
public @interface ConstraintRule {

    /**
     * @return all supported types, which can be validated by constraint annotation
     */
    Class<?>[] supportedTypes();

    /**
     * @return {@code ConstraintValidator} class per each supported type, i.e.
     * supportedTypes.length must be equal to validatorClass.length,
     * otherwise compile error will be throw
     */
    @SuppressWarnings("rawtypes")
    Class<? extends ConstraintValidator>[] validatorClass();
}
