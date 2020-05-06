/*
 * Copyright 2019 https://rxmicro.io
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

package io.rxmicro.http.error;

import io.rxmicro.common.RxMicroException;
import io.rxmicro.common.util.Formats;

/**
 * Base exception for all not success HTTP response statuses.
 * <p>
 * Each reactive type of returned request handler result supports two states:
 * <ul>
 *     <li>Successful completion signal.</li>
 *     <li>Completion signal with an error.</li>
 * </ul>
 * <p>
 * The feature of a successful completion signal consists in its uniqueness, i.e. if such a signal has occurred, it ensures successful
 * completion of the business task. The feature of a completion signal with an error is that different types of errors may occur
 * during the execution of the business task:
 * <ul>
 *     <li>validation error;</li>
 *     <li>data source connection error;</li>
 *     <li>computational algorithm error, etc.</li>
 * </ul>
 * <p>
 * It means that each request handler can return only one successful result and several results with errors.
 * <p>
 * So the RxMicro framework introduces the error concept. An error means any unsuccessful result.
 * For simplified error handling, the RxMicro framework recommends using HTTP status codes for each error category!
 * In case the HTTP code status is not sufficient, the RxMicro framework recommends using an additional text description.
 * <p>
 * For this purpose, the RxMicro framework defines a standard JSON model which is returned in case of any error:
 * <pre>
 * {
 *    "message": "Not Found"
 * }
 * </pre>
 * <p>
 * Thus, in case of an error, the client determines the error category basing on HTTP status code analysis. For more information,
 * the client should analyze a text message.
 * <p>
 * For all child classes which extend the HttpErrorException class, when creating an exception instance the stack trace is not filled,
 * as this information is redundant.
 * (This behavior is achieved by using the {@link RuntimeException#RuntimeException(String, Throwable, boolean, boolean)}.)
 *
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public class HttpErrorException extends RxMicroException {

    private final int statusCode;

    /**
     * Creates a HTTP error with status code and error message.
     * <p>
     * For all child classes which extend the HttpErrorException class, when creating an exception instance the stack trace is not filled,
     * as this information is redundant.
     * <p>
     * (<i>This behavior is achieved by using the {@link RuntimeException#RuntimeException(String, Throwable, boolean, boolean)}.</i>)
     *
     * @param statusCode the status code
     * @param message the error message
     * @throws NullPointerException if {@code message} is {@code null}
     */
    public HttpErrorException(final int statusCode,
                              final String message) {
        super(false, false, message);
        this.statusCode = statusCode;
    }

    /**
     * Creates a HTTP error with status code and error message.
     * <p>
     * For all child classes which extend the HttpErrorException class, when creating an exception instance the stack trace is not filled,
     * as this information is redundant.
     * <p>
     * (<i>This behavior is achieved by using the {@link RuntimeException#RuntimeException(String, Throwable, boolean, boolean)}.</i>)
     *
     * <i>(FYI: This constructor uses {@link Formats#format(String, Object...)} method to format error message.)</i>
     *
     * @param statusCode the status code
     * @param message the error message template
     * @param args the error message template argument
     * @throws NullPointerException if {@code message} is {@code null}
     */
    public HttpErrorException(final int statusCode,
                              final String message,
                              final Object... args) {
        super(false, false, message, args);
        this.statusCode = statusCode;
    }

    /**
     * Creates a HTTP error with status code only.
     * <p>
     * For all child classes which extend the HttpErrorException class, when creating an exception instance the stack trace is not filled,
     * as this information is redundant.
     * <p>
     * (<i>This behavior is achieved by using the {@link RuntimeException#RuntimeException(String, Throwable, boolean, boolean)}.</i>)
     *
     * @param statusCode the status code
     */
    public HttpErrorException(final int statusCode) {
        super(false, false);
        this.statusCode = statusCode;
    }

    public final int getStatusCode() {
        return statusCode;
    }

    public final boolean isInformationalCode() {
        return statusCode >= 100 && statusCode < 199;
    }

    public final boolean isSuccessCode() {
        return statusCode >= 200 && statusCode < 299;
    }

    public final boolean isRedirectCode() {
        return statusCode >= 300 && statusCode < 399;
    }

    public final boolean isClientErrorCode() {
        return statusCode >= 400 && statusCode < 499;
    }

    public final boolean isServerErrorCode() {
        return statusCode >= 500 && statusCode < 599;
    }
}
