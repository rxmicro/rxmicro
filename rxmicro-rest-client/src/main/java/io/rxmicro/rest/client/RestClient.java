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

package io.rxmicro.rest.client;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static io.rxmicro.common.CommonConstants.EMPTY_STRING;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Denotes that an interface is a dynamic generated REST client.
 *
 * @author nedis
 * @see RestClientConfig
 * @see RestClientFactory
 * @since 0.1
 */
@Documented
@Retention(CLASS)
@Target(TYPE)
public @interface RestClient {

    /**
     * Allows setting a custom config name space.
     *
     * <p>
     * By default the RxMicro framework uses {@link io.rxmicro.config.Config#getDefaultNameSpace(Class)} method to define
     * the config name space.
     *
     * <p>
     * Using this parameter a developer can defined the custom config name space.
     *
     * @return custom config name space.
     * @see io.rxmicro.config.Config#getDefaultNameSpace(Class)
     */
    String configNameSpace() default EMPTY_STRING;

    /**
     * Allows extending the standard {@link RestClientConfig} class.
     *
     * @return the custom class with additional properties
     * @see RestClientConfig
     */
    Class<? extends RestClientConfig> configClass() default RestClientConfig.class;
}
