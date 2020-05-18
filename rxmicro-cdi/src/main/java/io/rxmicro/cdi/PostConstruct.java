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
 * Denotes a method, that should be invoked automatically after all dependencies have been injected into the current component.
 * In its semantics, it completely corresponds to the {@code javax.annotation.PostConstruct} annotation.
 * <p>
 * <h4>The postConstruct method must meet the following requirements:</h4>
 * <ul>
 *     <li>This method should be a single method in the class.</li>
 *     <li>The method must not be {@code static}.</li>
 *     <li>The method must not be {@code abstract}.</li>
 *     <li>The method must be non-{@code native}.</li>
 *     <li>The method must not be {@code synchronized}.</li>
 *     <li>The method must not contain parameters.</li>
 *     <li>The method must return the void type.</li>
 * </ul>
 *
 * @author nedis
 * @since 0.1
 */
@Documented
@Retention(SOURCE)
@Target(METHOD)
@SuppressWarnings("JavaDoc")
public @interface PostConstruct {

    /**
     * Default name of method, which the RxMicro framework interprets as method annotated by this annotation,
     * i.e.
     * <pre>
     * void {@value #DEFAULT_POST_CONSTRUCT_METHOD_NAME}() {
     *
     * }
     * </pre>
     * is equivalent to
     *
     * <pre>
     * {@code @}{@link PostConstruct}
     * void ${any-method-name}() {
     *
     * }
     * </pre>
     */
    String DEFAULT_POST_CONSTRUCT_METHOD_NAME = "postConstruct";
}
