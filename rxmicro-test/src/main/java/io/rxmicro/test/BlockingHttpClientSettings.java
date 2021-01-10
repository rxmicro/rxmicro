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

package io.rxmicro.test;

import io.rxmicro.common.model.Option;
import io.rxmicro.http.ProtocolSchema;
import io.rxmicro.rest.BaseUrlPath;
import io.rxmicro.rest.Version;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Allows configuring the following component: {@link BlockingHttpClient}, in order to execute HTTP requests in tests.
 *
 * <p>
 * <i>(This annotation applies only to the {@link BlockingHttpClient} type fields.)</i>
 *
 * <p>
 * The RxMicro framework supports the {@link BlockingHttpClient} component only for REST-based microservice tests
 * and REST-based microservice integration tests.
 *
 * @author nedis
 * @see BlockingHttpClient
 * @see io.rxmicro.rest.Version.Strategy
 * @since 0.1
 */
@SuppressWarnings("JavaDoc")
@Documented
@Retention(RUNTIME)
@Target(FIELD)
public @interface BlockingHttpClientSettings {

    /**
     * Returns the HTTP protocol schema.
     *
     * @return the HTTP protocol schema
     * @see ProtocolSchema
     */
    ProtocolSchema schema() default ProtocolSchema.HTTP;

    /**
     * Returns the HTTP server host.
     *
     * @return the HTTP server host
     */
    String host() default "localhost";

    /**
     * Returns the HTTP server port or -1, if port must detected automatically.
     *
     * <p>
     * <i>(The {@link #port()} and {@link #randomPortProvider()} parameters are mutually exclusive.)</i>
     *
     * @return the HTTP server port or -1, if port must detected automatically
     */
    int port() default -1;

    /**
     * Allows specifying the dynamic connection port.
     *
     * <p>
     * <i>(The port will be read from the static final variable of the current class with the specified name.)</i>
     *
     * <p>
     * <i>(The {@link #port()} and {@link #randomPortProvider()} parameters are mutually exclusive.)</i>
     *
     * @return field name, which contains port value.
     *          The field must be a final, a static and a member of test class.
     */
    String randomPortProvider() default "";

    /**
     * Returns the base url path.
     *
     * @return the base url path.
     * @see BaseUrlPath
     */
    String baseUrlPath() default "";

    /**
     * Returns the position of the base url according to {@link Version} if {@link Version.Strategy#URL_PATH} is used.
     *
     * @return the position of the base url according to {@link Version} if {@link Version.Strategy#URL_PATH} is used
     * @see BaseUrlPath
     * @see BaseUrlPath.Position
     */
    BaseUrlPath.Position baseUrlPosition() default BaseUrlPath.Position.AFTER_VERSION;

    /**
     * Returns the current API version or empty string if not defined.
     *
     * @return the current API version or empty string if not defined
     * @see io.rxmicro.rest.Version.Strategy
     */
    String versionValue() default "";

    /**
     * Returns the current API version strategy.
     *
     * @return the current API version strategy
     * @see io.rxmicro.rest.Version.Strategy
     */
    Version.Strategy versionStrategy() default Version.Strategy.URL_PATH;

    /**
     * Returns the request timeout in {@code SECONDS}.
     *
     * <p>
     * {@code 0} means infinite timeout.
     *
     * @return the request timeout in {@code SECONDS}
     */
    int requestTimeout() default 10;

    /**
     * Returns follow redirect option for the {@link BlockingHttpClient}.
     *
     * @return  {@link Option#AUTO} is {@link Option#ENABLED} for integration tests and
     *                      {@link Option#DISABLED} for REST-based micro service tests,
     *          {@link Option#ENABLED} if http client must support redirects automatically
     *          {@link Option#DISABLED} if http client must not support redirects automatically
     * @see Option
     */
    Option followRedirects() default Option.AUTO;
}
