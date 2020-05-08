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

package io.rxmicro.rest.server.feature;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Activates the Cross Origin Resource Sharing for all request handlers in the REST controller.
 * <p>
 * When activating this feature, the RxMicro framework automatically adds a standard handler that handles preflighted requests.
 *
 * @author nedis
 * @since 0.1
 */
@Documented
@Retention(SOURCE)
@Target(TYPE)
public @interface EnableCrossOriginResourceSharing {

    /**
     * <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Allow-Origin">
     *     https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Allow-Origin
     * </a>
     *
     * @return array of allowed origins, that must be supported.
     *         If Origin is not valid server returns the first origin as default expected Origin
     */
    String[] allowOrigins() default {"*"};

    /**
     * <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Allow-Credentials#Directives">
     *     https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Allow-Credentials#Directives
     * </a>
     *
     * @return {@code true} if access control allows the credentials
     */
    boolean accessControlAllowCredentials() default false;

    /**
     * <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Max-Age#Directives">
     *     https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Max-Age#Directives
     * </a>
     * <p>
     * Default value is {@code 86400} sec =  {@code 24} hours
     *
     * @return the maximum number of seconds the results can be cached or {@code -1} if cache must be disabled
     */
    int accessControlMaxAge() default 86_400;

    /**
     * Allows adding HTTP headers to ALL CORS requests.
     *
     * @return the exposed HTTP headers
     */
    String[] exposedHeaders() default {};
}
