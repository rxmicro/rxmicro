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
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Allows to customize an injection point by specifying a string value or custom annotation.
 * This annotation can also be used to specify a component name.
 * <p>
 * Is a synonym of the {@link Named} annotation, and is recommended for developers who have used Spring DI as CDI
 * implementation in their previous projects.
 *
 * @author nedis
 * @since 0.1
 * @see Named
 * @see Autowired
 */
@Documented
@Retention(SOURCE)
@Target({FIELD, TYPE, METHOD, CONSTRUCTOR, PARAMETER})
public @interface Qualifier {

    /**
     * Returns the customized name of component or injection point
     *
     * @return the customized name of component or injection point
     */
    String value();
}
