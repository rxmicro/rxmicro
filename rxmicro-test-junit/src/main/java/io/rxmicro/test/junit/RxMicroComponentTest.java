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

import io.rxmicro.test.junit.internal.RxMicroComponentTestExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Declares the test class as a component unit test.
 *
 * <p>
 * <strong>For efficient writing of component tests, it is necessary to know the execution order of user and RxMicro code.</strong>
 *
 * <p>
 * When testing a component, using the following test:
 *
 * <p>
 * <pre>
 * {@code @io.rxmicro.test.mockito.junit.InitMocks}
 * {@code @RxMicroComponentTest(BusinessService.class)}
 * final class BusinessServiceTest {
 *
 *     {@code @org.junit.jupiter.api.BeforeAll}
 *     static void beforeAll() {
 *     }
 *
 *     private BusinessService businessService;
 *
 *     private SystemOut systemOut;
 *
 *     {@code @org.mockito.Mock}
 *     {@code @io.rxmicro.test.Alternative}
 *     private BusinessService.ChildBusinessService childBusinessService;
 *
 *     public BusinessServiceTest() {
 *     }
 *
 *     {@code @org.junit.jupiter.api.BeforeEach}
 *     void beforeEach() {
 *     }
 *
 *     void beforeTest1UserMethod() {
 *     }
 *
 *     {@code @org.junit.jupiter.api.Test}
 *     {@code @io.rxmicro.test.junit.BeforeTest(method = "beforeTest1UserMethod")}
 *     void test1() {
 *     }
 *
 *     void beforeTest2UserMethod() {
 *     }
 *
 *     {@code @Test}
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
 * </pre>
 *
 * <p>
 * the execution order will be as follows:
 *
 * <p>
 * <pre>
 * RX-MICRO: Test class validated.
 * USER-TEST: '@org.junit.jupiter.api.BeforeAll' invoked.
 *
 * USER-TEST: new instance of the component test class created.
 * MOCKITO: All mocks created and injected.
 * RX-MICRO: Alternatives of the RxMicro components registered in the RxMicro runtime containers.
 * RX-MICRO: SystemOut instance created and injected to the instance of the test class.
 * USER-TEST: '@org.junit.jupiter.api.BeforeEach' invoked.
 * USER-TEST: 'beforeTest1UserMethod' invoked.
 * RX-MICRO: Tested component instance created, if it is not created by user.
 * RX-MICRO: Alternatives of the user components injected to the tested component instance.
 * USER-TEST: 'test1()' method invoked.
 * USER-TEST: '@org.junit.jupiter.api.AfterEach' invoked.
 * RX-MICRO: All registered alternatives removed from the RxMicro runtime containers.
 * RX-MICRO: System.out reset.
 * MOCKITO: All mocks destroyed.
 *
 * USER-TEST: new instance of the component test class created.
 * MOCKITO: All mocks created and injected.
 * RX-MICRO: Alternatives of the RxMicro components registered in the RxMicro runtime containers.
 * RX-MICRO: SystemOut instance created and injected to the instance of the test class.
 * USER-TEST: '@org.junit.jupiter.api.BeforeEach' invoked.
 * USER-TEST: 'beforeTest2UserMethod' invoked.
 * RX-MICRO: Tested component instance created, if it is not created by user.
 * RX-MICRO: Alternatives of the user components injected to the tested component instance.
 * USER-TEST: 'test2()' method invoked.
 * USER-TEST: '@org.junit.jupiter.api.AfterEach' invoked.
 * RX-MICRO: All registered alternatives removed from the RxMicro runtime containers.
 * RX-MICRO: System.out reset.
 * MOCKITO: All mocks destroyed.
 *
 * USER-TEST: '@org.junit.jupiter.api.AfterAll' invoked.
 * </pre>
 *
 * <p>
 * In the above execution order of user and RxMicro code the following clarifications are implied:
 *
 * <p>
 * <ul>
 *     <li>The {@code MOCKITO} prefix means that the action is activated by the {@code @InitMocks} annotation.</li>
 *     <li>The {@code RX-MICRO} prefix means that the action is activated by the {@code @}{@link RxMicroComponentTest} annotation.</li>
 *     <li>The {@code USER-TEST} prefix means that at this stage a custom method from the {@code BusinessServiceTest class} is invoked.</li>
 * </ul>
 *
 * @author nedis
 * @see RxMicroRestBasedMicroServiceTest
 * @see RxMicroIntegrationTest
 * @since 0.1
 */
@Target(TYPE)
@Retention(RUNTIME)
@ExtendWith(RxMicroComponentTestExtension.class)
@Inherited
public @interface RxMicroComponentTest {

    /**
     * Returns the component class, which must be tested.
     *
     * @return the component class, which must be tested
     */
    Class<?> value();
}
