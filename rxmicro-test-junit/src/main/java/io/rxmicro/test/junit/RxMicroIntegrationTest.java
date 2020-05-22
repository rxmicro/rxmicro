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

package io.rxmicro.test.junit;

import io.rxmicro.test.junit.internal.RxMicroIntegrationTestExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Declares the test class as a REST-based microservice integration test.
 *
 * <p>
 * The REST-based microservice integration testing allows testing a complete system, which can consist of several REST-based microservices.
 *
 * <p>
 * <strong>For easy writing of the REST-based microservices integration tests, the RxMicro framework provides:</strong>
 * <ul>
 *     <li>
 *         The additional {@code @}{@link RxMicroIntegrationTest} annotation, that informs the RxMicro framework about the need
 *         to create an HTTP client and inject it into the tested class.
 *     </li>
 *     <li>A special blocking HTTP client to execute HTTP requests during testing: {@link io.rxmicro.test.BlockingHttpClient}.</li>
 *     <li>The {@link io.rxmicro.test.SystemOut} interface for easy console access.</li>
 * </ul>
 *
 * <p>
 * <strong>The main differences between integration testing and REST-based microservice testing:</strong>
 * <ul>
 *     <li>for integration tests, the RxMicro framework does not run an HTTP server;</li>
 *     <li>the developer has to start and stop the system consisting of REST-based microservices;</li>
 *     <li>the RxMicro framework does not support alternatives and additional configuration for integration tests;</li>
 * </ul>
 *
 * @author nedis
 * @see RxMicroComponentTest
 * @see RxMicroRestBasedMicroServiceTest
 * @since 0.1
 */
@Target(TYPE)
@Retention(RUNTIME)
@ExtendWith(RxMicroIntegrationTestExtension.class)
@Inherited
public @interface RxMicroIntegrationTest {
}
