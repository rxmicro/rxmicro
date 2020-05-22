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

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Declares a message returned by the HTTP request handler in case of no result.
 *
 * <p>
 * The RxMicro framework supports Not Found Logic for HTTP request handlers.
 *
 * <p>
 * To activate this feature it’s necessary to return a reactive type that supports optional result.
 *
 * <p>
 * <strong>When handling requests, the RxMicro framework checks the handler result:</strong>
 * <ul>
 *     <li>
 *          If the handler returns a response model, the RxMicro framework will convert it to an HTTP response with the 200 status and
 *          JSON representation of this model.
 *     </li>
 *     <li>
 *         If the handler returns an empty result, the RxMicro framework generates an HTTP response with the 404 and the custom
 *         error message that defined via this annotation. This this annotation is missing the RxMicro framework generates
 *         default "Not Found" error message.
 *     </li>
 * </ul>
 *
 * <p>
 * For more control over the HTTP response generated in case of an error, use exception instead of {@code Not Found} Logic feature!
 *
 * @author nedis
 * @see SetStatusCode
 * @see io.rxmicro.http.error.HttpErrorException
 * @since 0.1
 */
@Documented
@Retention(SOURCE)
@Target(METHOD)
public @interface NotFoundMessage {

    /**
     * Returns custom not found message.
     *
     * @return custom not found message
     */
    String value();
}
