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

package io.rxmicro.data.mongo;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Denotes a model field that must be used as document unique identifier.
 *
 * <p>
 * Read more:
 * <a href="https://docs.mongodb.com/manual/core/document/#field-names">
 * https://docs.mongodb.com/manual/core/document/#field-names
 * </a>
 *
 * @author nedis
 * @since 0.1
 */
@Documented
@Retention(CLASS)
@Target({FIELD, METHOD})
public @interface DocumentId {

}
