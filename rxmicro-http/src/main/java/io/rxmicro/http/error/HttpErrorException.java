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

package io.rxmicro.http.error;

import io.rxmicro.common.RxMicroException;
import io.rxmicro.common.util.Formats;

/**
 * Base exception for all not success HTTP response statuses.
 *
 * <p>
 * Each reactive type of returned request handler result supports two states:
 * <ul>
 *     <li>Successful completion signal.</li>
 *     <li>Completion signal with an error.</li>
 * </ul>
 *
 * <p>
 * The feature of a successful completion signal consists in its uniqueness, i.e. if such a signal has occurred, it ensures successful
 * completion of the business task. The feature of a completion signal with an error is that different types of errors may occur
 * during the execution of the business task:
 * <ul>
 *     <li>validation error;</li>
 *     <li>data source connection error;</li>
 *     <li>computational algorithm error, etc.</li>
 * </ul>
 *
 * <p>
 * It means that each request handler can return only one successful result and several results with errors.
 *
 * <p>
 * So the RxMicro framework introduces the error concept. An error means any unsuccessful result.
 * For simplified error handling, the RxMicro framework recommends using HTTP status codes for each error category!
 * In case the HTTP code status is not sufficient, the RxMicro framework recommends using an additional text description.
 *
 * <p>
 * For this purpose, the RxMicro framework defines a standard JSON model which is returned in case of any error:
 * <pre>
 * {
 *    "message": "Not Found"
 * }
 * </pre>
 *
 * <p>
 * Thus, in case of an error, the client determines the error category basing on HTTP status code analysis. For more information,
 * the client should analyze a text message.
 *
 * <p>
 * For all child classes which extend the HttpErrorException class, when creating an exception instance the stack trace is not filled,
 * as this information is redundant.
 * (This behavior is achieved by using the {@link RuntimeException#RuntimeException(String, Throwable, boolean, boolean)}.)
 *
 * @author nedis
 * @since 0.1
 */
public class HttpErrorException extends RxMicroException {

    private static final int MIN_SUPPORTED_INFORMATIONAL_CODE = 100;

    private static final int MAX_SUPPORTED_INFORMATIONAL_CODE = 199;

    private static final int MIN_SUPPORTED_SUCCESS_CODE = 200;

    private static final int MAX_SUPPORTED_SUCCESS_CODE = 299;

    private static final int MIN_SUPPORTED_REDIRECT_CODE = 300;

    private static final int MAX_SUPPORTED_REDIRECT_CODE = 399;

    private static final int MIN_SUPPORTED_CLIENT_ERROR_CODE = 400;

    private static final int MAX_SUPPORTED_CLIENT_ERROR_CODE = 499;

    private static final int MIN_SUPPORTED_SERVER_ERROR_CODE = 500;

    private static final int MAX_SUPPORTED_SERVER_ERROR_CODE = 599;

    private final int statusCode;

    /**
     * Creates a HTTP error with status code and error message.
     *
     * <p>
     * For all child classes which extend the HttpErrorException class, when creating an exception instance the stack trace is not filled,
     * as this information is redundant.
     *
     * <p>
     * (<i>This behavior is achieved by using the {@link RuntimeException#RuntimeException(String, Throwable, boolean, boolean)}.</i>)
     *
     * @param statusCode the status code
     * @param message the error message
     * @throws NullPointerException if the error message is {@code null}
     */
    public HttpErrorException(final int statusCode,
                              final String message) {
        super(false, false, message);
        this.statusCode = statusCode;
    }

    /**
     * Creates a HTTP error with status code and error message.
     *
     * <p>
     * For all child classes which extend the HttpErrorException class, when creating an exception instance the stack trace is not filled,
     * as this information is redundant.
     *
     * <p>
     * (<i>This behavior is achieved by using the {@link RuntimeException#RuntimeException(String, Throwable, boolean, boolean)}.</i>)
     *
     * <p>
     * <i>(FYI: This constructor uses {@link Formats#format(String, Object...)} method to format error message.)</i>
     *
     * @param statusCode the status code
     * @param message the error message template
     * @param args the error message template argument
     * @throws NullPointerException if the error message template is {@code null}
     * @throws IllegalArgumentException if detected a redundant placeholder or missing argument
     */
    public HttpErrorException(final int statusCode,
                              final String message,
                              final Object... args) {
        super(false, false, message, args);
        this.statusCode = statusCode;
    }

    /**
     * Creates a HTTP error with status code only.
     *
     * <p>
     * For all child classes which extend the HttpErrorException class, when creating an exception instance the stack trace is not filled,
     * as this information is redundant.
     *
     * <p>
     * (<i>This behavior is achieved by using the {@link RuntimeException#RuntimeException(String, Throwable, boolean, boolean)}.</i>)
     *
     * @param statusCode the status code
     */
    public HttpErrorException(final int statusCode) {
        super(false, false);
        this.statusCode = statusCode;
    }

    /**
     * Returns the HTTP status code for current instance of exception.
     *
     * @return the HTTP status code for current instance of exception
     */
    public final int getStatusCode() {
        return statusCode;
    }

    /**
     * Returns {@code true} if the current instance of exception has a informational status code.
     *
     * @return {@code true} if the current instance of exception has a informational status code
     */
    public final boolean isInformationalCode() {
        return statusCode >= MIN_SUPPORTED_INFORMATIONAL_CODE && statusCode < MAX_SUPPORTED_INFORMATIONAL_CODE;
    }

    /**
     * Returns {@code true} if the current instance of exception has a success status code.
     *
     * @return {@code true} if the current instance of exception has a success status code
     */
    public final boolean isSuccessCode() {
        return statusCode >= MIN_SUPPORTED_SUCCESS_CODE && statusCode < MAX_SUPPORTED_SUCCESS_CODE;
    }

    /**
     * Returns {@code true} if the current instance of exception has a redirect status code.
     *
     * @return {@code true} if the current instance of exception has a redirect status code
     */
    public final boolean isRedirectCode() {
        return statusCode >= MIN_SUPPORTED_REDIRECT_CODE && statusCode < MAX_SUPPORTED_REDIRECT_CODE;
    }

    /**
     * Returns {@code true} if the current instance of exception has a client error status code.
     *
     * @return {@code true} if the current instance of exception has a client error status code
     */
    public final boolean isClientErrorCode() {
        return statusCode >= MIN_SUPPORTED_CLIENT_ERROR_CODE && statusCode < MAX_SUPPORTED_CLIENT_ERROR_CODE;
    }

    /**
     * Returns {@code true} if the current instance of exception has a server error status code.
     *
     * @return {@code true} if the current instance of exception has a server error status code
     */
    public final boolean isServerErrorCode() {
        return statusCode >= MIN_SUPPORTED_SERVER_ERROR_CODE && statusCode < MAX_SUPPORTED_SERVER_ERROR_CODE;
    }
}
