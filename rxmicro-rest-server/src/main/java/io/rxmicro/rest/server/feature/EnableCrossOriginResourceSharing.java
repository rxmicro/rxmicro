/*
 * Copyright (c) 2020. http://rxmicro.io
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
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
@Documented
@Retention(SOURCE)
@Target(TYPE)
public @interface EnableCrossOriginResourceSharing {

    /**
     * https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Allow-Origin
     *
     * @return array of allowed origins, that must be supported.
     * If Origin is not valid server returns the first origin as default expected Origin
     */
    String[] allowOrigins() default {"*"};

    /**
     * https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Allow-Credentials#Directives
     */
    boolean accessControlAllowCredentials() default false;

    /**
     * https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Max-Age#Directives
     * <p>
     * Default value is 86400 sec = 24 hours
     *
     * @return Maximum number of seconds the results can be cached or
     * <code>-1</code> if cache must be disabled
     */
    int accessControlMaxAge() default 86400;

    /**
     * Allows to add HTTP headers to ALL CORS requests
     */
    String[] exposedHeaders() default {};
}
