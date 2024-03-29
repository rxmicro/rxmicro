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

package io.rxmicro.test;

import io.rxmicro.rest.client.detail.HttpClientFactory;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static io.rxmicro.common.CommonConstants.EMPTY_STRING;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Declares the test class field as an alternative.
 *
 * <p>
 * (<i>The RxMicro framework supports alternatives only for REST-based microservice tests and component unit tests.</i>)
 *
 * <p>
 * For efficient unit testing, the RxMicro framework supports the mechanism of alternatives.
 * Alternatives are test components, usually being mocks with predefined behaviors,
 * that are injected by the RxMicro framework into the tested classes.
 * Alternatives are a powerful mechanism for writing unit tests.
 *
 * <p>
 * <strong>When developing a microservice project, two types of components are distinguished:</strong>
 * <ul>
 *     <li>
 *         <i>RxMicro component</i> - a class that is part of the RxMicro framework
 *         (for example, {@link HttpClientFactory}) or a class generated by the
 *         {@code RxMicro Annotation Processor} (Data Repository, Rest client, etc).
 *     </li>
 *     <li><i>Custom component</i> - a developer-written class that is part of a microservice project.</li>
 * </ul>
 *
 * <p>
 * These two types of components have different life cycles:
 * <ul>
 *     <li>
 *         The instances of the RxMicro components are created in the classes generated by the {@code RxMicro Annotation Processor},
 *         and are registered in the runtime container. When a reference to the RxMicro component is required,
 *         the custom class requests it in the runtime container.
 *     </li>
 *     <li>
 *         The instances of custom components are created independently by the developer in the code.
 *     </li>
 * </ul>
 *
 * <p>
 * Due to the difference in life cycles between the two types of the RxMicro components,
 * the RxMicro framework also supports two types of alternatives:
 * <ul>
 *     <li>alternatives of the RxMicro components;</li>
 *     <li>alternatives of custom components.</li>
 * </ul>
 *
 * <p>
 * These types of alternatives differ in the algorithms of injection into the tested class.
 *
 * <p>
 * <strong>Injection Algorithm for the Alternative of the RxMicro Component:</strong>
 *
 * <p>
 * To inject the alternative of the RxMicro component, the RxMicro framework uses the following algorithm:
 * <ul>
 *     <li>
 *         The alternative instance is created by the developer in the test code or by the testing framework automatically.
 *     </li>
 *     <li>
 *         Once all alternatives have been created, they are registered in the runtime container.
 *     </li>
 *     <li>
 *         Once all alternatives have been registered, the RxMicro framework creates an instance of the tested class.
 *     </li>
 *     <li>
 *         In the constructor or static section of the tested class, a request to the runtime container to get a reference to the
 *         RxMicro component is executed.
 *     </li>
 *     <li>
 *         Since the runtime container already contains an alternative instead of the real component, the alternative is injected
 *         into the instance of the tested class.
 *     </li>
 *     <li>
 *         After initialization, the instance of the tested class contains references to alternatives instead of the real
 *         RxMicro components.
 *     </li>
 * </ul>
 *
 * <p>
 * <strong>Injection Algorithm for the Alternative of the Custom Component:</strong>
 *
 * <p>
 * To inject the alternative of the custom component, the RxMicro framework uses the following algorithm:
 * <ul>
 *     <li>
 *         The alternative instance is created by the developer in the test code or by the testing framework automatically.
 *     </li>
 *     <li>
 *         The RxMicro framework creates an instance of the tested class.
 *     </li>
 *     <li>
 *         In the constructor or static section of the tested class, instances of the real custom components are created.
 *     </li>
 *     <li>
 *         After initialization, the instance of the tested class contains references to the real custom components.
 *     </li>
 *     <li>
 *         After creating an instance of the tested class, the RxMicro framework injects the custom component alternatives using the
 *         reflection mechanism.<br>
 *         <i>(I.e. the alternatives replace the real instances already after creating an instance of the tested class.)</i>
 *     </li>
 *     <li>
 *         After alternative injection, the instance of the tested class contains references to the alternatives of the RxMicro components
 *         instead of the real RxMicro components. <br>
 *         <i>(The real component instances will be removed by the garbage collector later.)</i>
 *     </li>
 * </ul>
 *
 * <p>
 * Thus, the main difference of the injection algorithm for the custom component alternatives is that during the injection process,
 * the real component instances are always created.
 *
 * <p>
 * <strong>Ambiguity Resolving Algorithm for Alternatives</strong>
 * To resolve ambiguities, the RxMicro framework uses the following algorithm:
 * <ul>
 *     <li>
 *         For each tested component, a search for injection candidates is performed.
 *     </li>
 *     <li>
 *         As a result, a map is formed with a user type as its key and a list of candidates for injection as its value. <br>
 *         <i>(The RxMicro framework does not support polymorphism rules when injecting alternatives.
 *         Thus, the alternative of the A type can only be injected in the field with the A type).</i>
 *     </li>
 *     <li>
 *         After receiving a map with candidates for injection, the RxMicro framework passes through this map.
 *     </li>
 *     <li>
 *         For each user type, a list of candidates and a list of alternatives is requested.
 *     </li>
 *     <li>
 *         If there is only one alternative and only one candidate for the user type, the RxMicro framework injects this alternative
 *         into the candidate field;
 *     </li>
 *     <li>
 *         If more than one alternative and only one candidate is found, the RxMicro framework will throw out an error;
 *     </li>
 *     <li>
 *         If there is more than one candidate and only one alternative, then:
 *         <ul>
 *             <li>
 *                 The RxMicro framework analyzes the injection candidate field name:
 *                 <ul>
 *                     <li>
 *                         if the candidate field name matches the alternative field name, the RxMicro framework injects this alternative;
 *                     </li>
 *                     <li>
 *                         if the candidate field name matches the value of the name parameter of the
 *                         {@code @}{@link Alternative} annotation, the RxMicro framework injects this alternative;
 *                     </li>
 *                     <li>
 *                         otherwise this candidate will be skipped;
 *                     </li>
 *                 </ul>
 *             </li>
 *             <li>
 *                 if no alternative has been injected, the RxMicro framework injects this alternative in all candidate fields.
 *             </li>
 *         </ul>
 *     </li>
 *     <li>
 *         If there is more than one candidate and more than one alternative, then:
 *         <ul>
 *             <li>
 *                 The RxMicro framework analyzes the injection candidate field name:
 *                 <ul>
 *                     <li>
 *                         if the candidate field name matches the alternative field name, the RxMicro framework injects this alternative;
 *                     </li>
 *                     <li>
 *                         if the candidate field name matches the value of the name parameter of the {@code @}{@link Alternative}
 *                         annotation, the RxMicro framework injects this alternative;
 *                     </li>
 *                     <li>
 *                         otherwise this candidate will be skipped;
 *                     </li>
 *                 </ul>
 *             </li>
 *             <li>
 *                 <i>(When more than one candidate and more than one alternative is found,
 *                 it is possible that none of the alternatives will be injected.)</i>
 *             </li>
 *         </ul>
 *     </li>
 * </ul>
 *
 * @author nedis
 * @since 0.1
 */
@Documented
@Retention(RUNTIME)
@Target(FIELD)
public @interface Alternative {

    /**
     * Returns {@code true} if the alternative can be {@code null}.
     *
     * @return {@code true} if the alternative can be {@code null}
     */
    boolean expectNull() default false;

    /**
     * Returns the alternative name that is used during injection algorithms.
     *
     * <p>
     * <i>(See above.)</i>
     *
     * @return the alternative name
     */
    String name() default EMPTY_STRING;
}
