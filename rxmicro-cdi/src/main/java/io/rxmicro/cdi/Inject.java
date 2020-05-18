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

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Indicates the need to inject the component implementation into the annotated class field or method parameter.
 * <p>
 * Is a synonym of the {@link Autowired} annotation, and is recommended for developers who have used
 * JEE or Google Guice as CDI implementation in their previous projects.
 * <p>
 * The RxMicro framework focuses on creating microservice projects.
 * One of the key features of microservices is their simplicity.
 * That’s why singleton scope was chosen as the main and only one.
 * <p>
 * Thus, <b>all CDI components are singletons!</b>
 * <p>
 * By default, all injection points are required. Thus, if during the process of dependencies injection,
 * the RxMicro framework does not find a suitable instance, an error will occur.
 * If the current project allows the situation when a suitable instance may be missing, then the optional injection mode should be used.
 * <p>
 * If the optional injection mode is enabled, the RxMicro framework uses the following injection algorithm:
 * <ol>
 *     <li>
 *         If the dependency is found, it will be successfully injected.
 *     </li>
 *     <li>
 *         If there’s no dependency, nothing happens.
 *          (In this case, the behaviour appears to be as if the field is not annotated by any annotation!)
 *     </li>
 * </ol>
 *
 * @author nedis
 * @since 0.1
 * @see Autowired
 */
@Documented
@Retention(SOURCE)
@Target({FIELD, CONSTRUCTOR, METHOD, PARAMETER})
public @interface Inject {

    /**
     * If true, and the appropriate binding is not found, the RxMicro framework will skip injection of this
     * method or field rather than produce an error.
     * <p>
     * When applied to a field, any default value already assigned to the field will remain for optional injection
     * (The RxMicro framework will not actively null out the field).
     *
     * @return {@code true} if current injection point is optional
     */
    boolean optional() default false;
}
