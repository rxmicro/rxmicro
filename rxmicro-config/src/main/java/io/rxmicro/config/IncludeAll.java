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

package io.rxmicro.config;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Activates all classes in package and all sub packages annotated by this annotation
 * for the {@code RxMicro Annotation Processor}. All other classes will be ignored.
 *
 * <p>
 * If this annotation applied to type (class, interface, enum, annotation),
 * that this type is available for the {@code RxMicro Annotation Processor} only.
 *
 * @author nedis
 * @see ExcludeAll
 * @since 0.1
 */
@Documented
@Retention(CLASS)
@Target({PACKAGE, TYPE})
public @interface IncludeAll {

}
