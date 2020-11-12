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

import io.rxmicro.test.junit.internal.RxMicroRestBasedMicroServiceTestExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Declares the test class as a REST-based microservice test.
 *
 * <p>
 * The REST-based microservice test is a standard Unit test that tests only the source code of a microservice.
 * If the current microservice depends on external services (e.g. database, other REST-based microservices, etc.),
 * then it is allowed to use mocks for these external services during its testing.
 * If several REST-based microservices need to be tested, it is recommended to use the {@link RxMicroIntegrationTest} annotation instead.
 *
 * <p>
 * <strong>For easy writing of microservice tests, the RxMicro framework provides:</strong>
 * <ul>
 *     <li>
 *          The additional {@code @}{@link RxMicroRestBasedMicroServiceTest} annotation, that informs the RxMicro framework
 *          about the need to start the tested REST-based microservice and prepare the environment to execute test HTTP requests.
 *     </li>
 *     <li>
 *         A special blocking HTTP client to execute HTTP requests during testing: {@link io.rxmicro.test.BlockingHttpClient}.
 *     </li>
 *     <li>
 *         The {@link io.rxmicro.test.SystemOut} interface for easy console access.
 *     </li>
 * </ul>
 *
 * <p>
 * <strong>For each microservice test, the RxMicro framework performs the following actions:</strong>
 * <ul>
 *     <li>
 *         Before starting all the test methods in the class:
 *         <ul>
 *             <li>
 *                 checks the test class for compliance with the rules of REST-based microservice testing defined by the RxMicro framework;
 *             </li>
 *             <li>starts an HTTP server on a random free port;</li>
 *             <li>creates an instance of the {@link io.rxmicro.test.BlockingHttpClient} type;</li>
 *             <li>connects the created {@link io.rxmicro.test.BlockingHttpClient} to the running HTTP server.</li>
 *         </ul>
 *     </li>
 *     <li>
 *         Before starting each test method:
 *         <ul>
 *             <li>
 *                 if necessary, invokes the methods defined using the {@code @}{@link BeforeThisTest} or
 *                 {@code @}{@link BeforeIterationMethodSource} annotations;
 *             </li>
 *             <li>if necessary, registers the RxMicro component alternatives in the RxMicro container;</li>
 *             <li>registers the tested REST-based microservice on the running HTTP server;</li>
 *             <li>if necessary, injects the custom component alternatives to the REST-based microservice;</li>
 *             <li>injects a reference to the {@link io.rxmicro.test.BlockingHttpClient} instance into the test class;</li>
 *             <li>if necessary, creates the System.out mock, and injects it into the test class.</li>
 *         </ul>
 *     </li>
 *     <li>
 *         After performing each test method:
 *         <ul>
 *             <li>deletes all registered components from the RxMicro container;</li>
 *             <li>deletes all registered REST-based microservices on the running HTTP server;</li>
 *             <li>if necessary, restores the {@link System#out}.</li>
 *         </ul>
 *     </li>
 *     <li>
 *         After performing all the tests in the class:
 *         <ul>
 *             <li>clears the resources of the {@link io.rxmicro.test.BlockingHttpClient} component;</li>
 *             <li>stops the HTTP server and releases the selected resources.</li>
 *         </ul>
 *     </li>
 * </ul>
 *
 * <p>
 * <strong>For efficient writing of Rest-based microservice tests,
 * it is necessary to know the execution order of user and RxMicro code.</strong>
 *
 * <p>
 * When testing a Rest-based microservice, using the following test:
 *
 * <p>
 * <pre><code>
 * {@code @io.rxmicro.test.mockito.junit.InitMocks}
 * {@code @RxMicroRestBasedMicroServiceTest(MicroService.class)}
 * final class MicroServiceTest {
 *
 *     {@code @org.junit.jupiter.api.BeforeAll}
 *     static void beforeAll() {
 *     }
 *
 *     private BlockingHttpClient blockingHttpClient;
 *
 *     private SystemOut systemOut;
 *
 *     {@code @org.mockito.Mock}
 *     {@code @io.rxmicro.test.Alternative}
 *     private BusinessService businessService;
 *
 *     public MicroServiceTest() {
 *     }
 *
 *     {@code @org.junit.jupiter.api.BeforeEach}
 *     void beforeEach() {
 *     }
 *
 *     void beforeTest1UserMethod() {
 *     }
 *
 *     {@code @Test}
 *     {@code @io.rxmicro.test.junit.BeforeTest(method = "beforeTest1UserMethod")}
 *     void test1() {
 *     }
 *
 *     void beforeTest2UserMethod() {
 *     }
 *
 *     {@code @org.junit.jupiter.api.Test}
 *     {@code @io.rxmicro.test.junit.BeforeTest(method = "beforeTest2UserMethod")}
 *     void test2() {
 *     }
 *
 *     {@code @org.junit.jupiter.api.AfterEach}
 *     void afterEach() {
 *     }
 *
 *     {@code @org.junit.jupiter.api.AfterAll}
 *     static void afterAll() {
 *     }
 * }
 * </code></pre>
 * the execution order will be as follows:
 *
 * <p>
 * <pre><code>
 * RX-MICRO: Test class validated.
 * RX-MICRO: HTTP server started without any REST-based microservices using random free port.
 * RX-MICRO: Blocking HTTP client created and connected to the started HTTP server.
 * USER-TEST: '@org.junit.jupiter.api.BeforeAll' invoked.
 *
 * USER-TEST: new instance of the REST-based microservice test class created.
 * MOCKITO: All mocks created and injected.
 * RX-MICRO: Alternatives of the RxMicro components registered in the RxMicro runtime containers.
 * RX-MICRO: Blocking HTTP client injected to the instance of the test class.
 * RX-MICRO: SystemOut instance created and injected to the instance of the test class.
 * USER-TEST: '@org.junit.jupiter.api.BeforeEach' invoked.
 * USER-TEST: 'beforeTest1UserMethod' invoked.
 * RX-MICRO: Current REST-based microservice instance created and registered in the HTTP server.
 * RX-MICRO: Alternatives of the user components injected to the REST-based microservice instance.
 * USER-TEST: 'test1()' invoked.
 * USER-TEST: '@org.junit.jupiter.api.AfterEach' invoked.
 * RX-MICRO: All registered alternatives removed from the RxMicro runtime containers.
 * RX-MICRO: Current REST-based microservice instance unregistered from the HTTP server.
 * RX-MICRO: System.out reset.
 * MOCKITO: All mocks destroyed.
 *
 * USER-TEST: new instance of the REST-based microservice test class created.
 * MOCKITO: All mocks created and injected.
 * RX-MICRO: Alternatives of the RxMicro components registered in the RxMicro runtime containers.
 * RX-MICRO: Blocking HTTP client injected to the instance of the test class.
 * RX-MICRO: SystemOut instance created and injected to the instance of the test class.
 * USER-TEST: '@org.junit.jupiter.api.BeforeEach' invoked.
 * USER-TEST: 'beforeTest2UserMethod' invoked.
 * RX-MICRO: Current REST-based microservice instance created and registered in the HTTP server.
 * RX-MICRO: Alternatives of the user components injected to the REST-based microservice instance.
 * USER-TEST: 'test2()' invoked.
 * USER-TEST: '@org.junit.jupiter.api.AfterEach' invoked.
 * RX-MICRO: All registered alternatives removed from the RxMicro runtime containers.
 * RX-MICRO: Current REST-based microservice instance unregistered from the HTTP server.
 * RX-MICRO: System.out reset.
 * MOCKITO: All mocks destroyed.
 *
 * USER-TEST: '@org.junit.jupiter.api.AfterAll' invoked.
 * RX-MICRO: Blocking HTTP client released.
 * RX-MICRO: HTTP server stopped.
 * </code></pre>
 * In the above execution order of user and RxMicro code the following clarifications are implied:
 *
 * <p>
 * <ul>
 *     <li>The {@code MOCKITO} prefix means that the action is activated by the {@code @InitMocks} annotation.</li>
 *     <li>The {@code RX-MICRO} prefix means that the action is activated by the
 *              {@code @}{@link RxMicroRestBasedMicroServiceTest} annotation.</li>
 *     <li>The {@code USER-TEST} prefix means that at this stage a custom method from the {@code MicroServiceTest class} is invoked.</li>
 * </ul>
 *
 * @author nedis
 * @see RxMicroComponentTest
 * @see RxMicroIntegrationTest
 * @since 0.1
 */
@Target(TYPE)
@Retention(RUNTIME)
@ExtendWith(RxMicroRestBasedMicroServiceTestExtension.class)
@Inherited
public @interface RxMicroRestBasedMicroServiceTest {

    /**
     * Returns the REST controller classes of REST-based micro services, that must be tested.
     *
     * @return the REST controller classes of REST-based micro services, that must be tested
     */
    Class<?>[] value();

    /**
     * Returns  {@code true} if the RxMicro framework must use {@link io.rxmicro.rest.server.ServerInstance#shutdownAndWait()} method
     *                      to stop test HTTP server or
     *          {@code false} if the RxMicro framework must use {@link io.rxmicro.rest.server.ServerInstance#shutdown()} method instead.
     *
     * @return  {@code true} if the RxMicro framework must use {@link io.rxmicro.rest.server.ServerInstance#shutdownAndWait()} method
     *                       to stop test HTTP server or
     *          {@code false} if the RxMicro framework must use {@link io.rxmicro.rest.server.ServerInstance#shutdown()} method instead.
     * @see io.rxmicro.rest.server.ServerInstance#shutdown()
     * @see io.rxmicro.rest.server.ServerInstance#shutdownAndWait()
     */
    boolean waitForTestHttpServerStopped() default false;
}
