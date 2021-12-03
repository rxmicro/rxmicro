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

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * This annotation indicates that the RxMicro framework must find all created {@link RestClient}s that can be found at all library modules.
 *
 * <p>
 * All library modules must be defined at the {@code module-info.java} for the current module.
 *
 * @author nedis
 * @see RestClient
 * @see RestClientConfig
 * @see RestClientFactory
 * @since 0.10
 */
@Documented
@Retention(CLASS)
@Target(TYPE)
public @interface FindAllRestClients {

}
