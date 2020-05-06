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
import io.rxmicro.rest.Version;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static io.rxmicro.http.client.HttpClientConfig.DEFAULT_HTTP_CLIENT_TIMEOUT_VALUE_IN_SECONDS;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@Documented
@Retention(RUNTIME)
@Target(FIELD)
public @interface BlockingHttpClientSettings {

    /**
     * @return  {@link Option#AUTO} is {@link Option#ENABLED} for integration tests and
     *                      {@link Option#DISABLED} for REST-based micro service tests,
     *          {@link Option#ENABLED} if http client must support redirects automatically
     *          {@link Option#DISABLED} if http client must not support redirects automatically
     */
    Option followRedirects() default Option.AUTO;

    /**
     * Unit = SECONDS
     * <p>
     * 0 means infinite timeout
     *
     * @return timeout in SECONDS
     */
    int requestTimeout() default DEFAULT_HTTP_CLIENT_TIMEOUT_VALUE_IN_SECONDS;

    /**
     * @return HTTP protocol schema
     */
    ProtocolSchema schema() default ProtocolSchema.HTTP;

    /**
     * @return HTTP server host
     */
    String host() default "localhost";

    /**
     * @return HTTP server port or -1, if port must detected automatically
     */
    int port() default -1;

    /**
     * @return field name, which contains port value.
     * The field must be a final, a static and a member of test class.
     */
    String randomPortProvider() default "";

    /**
     * @return Current API version or empty string if not defined
     * @see io.rxmicro.rest.Version.Strategy
     */
    String versionValue() default "";

    /**
     * @return Current API version strategy
     * @see io.rxmicro.http.HttpHeaders
     * @see io.rxmicro.rest.Version.Strategy
     */
    Version.Strategy versionStrategy() default Version.Strategy.URL_PATH;
}
