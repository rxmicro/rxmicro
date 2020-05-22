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

package io.rxmicro.test.mockito.junit;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Informs the test framework about the need to create mocks and inject them into the test class fields,
 * annotated by the @{@link org.mockito.Mock} annotation.
 *
 * <p>
 * <i>(Using the @{@link InitMocks} annotation is preferable to the analogous
 * {@code @}{@link ExtendWith}{@code ({@link MockitoExtension}.class)} construction!)</i>
 *
 * <p>
 * <strong>Usage example:</strong>
 * <pre>
 * {@code @InitMocks}
 * final class Test {
 *
 *     {@code @Mock}
 *     private Component component;
 *
 *     {@code @Test}
 *     void Do_something() {
 *         when(component.getValue()).thenReturn("test");
 *         ...
 *     }
 * }
 * </pre>
 *
 * @author nedis
 * @see MockitoExtension
 * @see org.mockito.Mock
 * @since 0.1
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(MockitoExtension.class)
@Inherited
public @interface InitMocks {
}
