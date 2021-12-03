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

package io.rxmicro.rest.server;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static io.rxmicro.rest.server.ServerRequest.HttpBodySupport.WITHOUT_HTTP_BODY;
import static io.rxmicro.rest.server.ServerRequest.HttpBodySupport.WITH_HTTP_BODY;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Marks annotated class as a server request.
 *
 * <p>
 * This annotation is useful for library modules.
 * Because the RxMicro framework can resolve server requests for non-library modules successfully without this annotation.
 *
 * @author nedis
 * @since 0.10
 */
@Documented
@Retention(CLASS)
@Target(TYPE)
public @interface ServerRequest {

    /**
     * Returns {@code true} if the annotated class is a parent class for other server requests.
     *
     * @return {@code true} if the annotated class is a parent class for other server requests.
     */
    boolean parent() default false;

    /**
     * Returns the http body support modes.
     *
     * @return the http body support modes
     */
    HttpBodySupport[] value() default {WITH_HTTP_BODY, WITHOUT_HTTP_BODY};

    /**
     * Provides http body support.
     *
     * @author nedis
     * @since 0.10
     */
    enum HttpBodySupport {

        /**
         * This mode means that the server request must be used by http handlers that require http body.
         */
        WITH_HTTP_BODY,

        /**
         * This mode means that the server request must be used by http handlers that do not require http body.
         */
        WITHOUT_HTTP_BODY
    }
}
