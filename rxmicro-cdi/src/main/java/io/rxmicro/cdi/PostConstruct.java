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
package io.rxmicro.cdi;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Method annotated by this annotation will be invoked by RxMicro after injection of all dependencies to bean instance.
 *
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@Documented
@Retention(SOURCE)
@Target(METHOD)
public @interface PostConstruct {

    /**
     * Default name of method, which RxMicro interprets as method annotated by this annotation,
     * i.e.
     * <p>
     * void postConstruct() {
     * <p>
     * }
     * <p>
     * is equivalent to
     *
     * <code>@PostConstruct</code>
     * void ${any-method-name}() {
     * <p>
     * }
     */
    String DEFAULT_POST_CONSTRUCT_METHOD_NAME = "postConstruct";
}
