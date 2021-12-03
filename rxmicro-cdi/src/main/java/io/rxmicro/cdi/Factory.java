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
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Denotes a factory method or a factory, that creates instances of the specified class.
 *
 * <p>
 * When using the dependency injection mechanisms, the RxMicro framework creates instances of the specified classes and injects links
 * to them to injection points. For successful implementation of this behavior, each class, the instance of which should be injected,
 * must contain an accessible constructor without parameters or a constructor annotated by the
 * {@link Inject} or {@link Autowired} annotation.
 *
 * <p>
 * In other words, the RxMicro framework determines the instance of which class should be created and creates this instance
 * automatically at the start of the CDI container. If it is necessary to get more control over creation of the implementation
 * instance, it is necessary to use the Factory Method template.
 * If the RxMicro framework detects a method in the class, annotated by the {@link Factory} annotation, then this method is used instead
 * of the constructor when creating the instance of this class.
 *
 * <p>
 * <strong>The factory method must meet the following requirements:</strong>
 * <ul>
 *     <li>The method must be {@code static}.</li>
 *     <li>The method must be non-{@code native}.</li>
 *     <li>The method must not be {@code synchronized}.</li>
 *     <li>The method must return the class instance in which it is declared.</li>
 *     <li>The method must not contain parameters.</li>
 * </ul>
 *
 * <p>
 * Besides factory method the RxMicro framework supports creation of factory classes, that can be used to create instances of other types.
 *
 * <p>
 * <strong>By using factory classes, it is possible to get the following benefits:</strong>
 * <ul>
 *     <li>Create dynamic classes. (For example, using the {@link java.lang.reflect.Proxy} class.)</li>
 *     <li>Implement a {@code prototype} scope.</li>
 * </ul>
 *
 * <p>
 * <strong>To create a factory class, it is necessary:</strong>
 * <ul>
 *     <li>Create a class implementing the {@link java.util.function.Supplier} interface.</li>
 *     <li>Annotate this class by the {@link Factory} annotation.</li>
 *     <li>Implement the {@link java.util.function.Supplier#get()} method, which should return the instance of the created class.</li>
 * </ul>
 *
 * @author nedis
 * @see java.util.function.Supplier
 * @see java.lang.reflect.Proxy
 * @see Inject
 * @see Autowired
 * @since 0.1
 */
@Documented
@Retention(CLASS)
@Target({METHOD, TYPE})
public @interface Factory {
}
