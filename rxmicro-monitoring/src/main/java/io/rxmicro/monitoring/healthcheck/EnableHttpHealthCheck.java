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

package io.rxmicro.monitoring.healthcheck;

import io.rxmicro.http.local.PredefinedUrls;
import io.rxmicro.rest.model.HttpMethod;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.MODULE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Enables a <code>`${schema}://${host}:${port}/http-health-check`</code> endpoint.
 *
 * <p>
 * This endpoint can be used that the RxMicro HTTP server is up.
 *
 * @author nedis
 * @see HttpMethod
 * @since 0.1
 */
@Documented
@Retention(SOURCE)
@Target({TYPE, MODULE, ANNOTATION_TYPE})
public @interface EnableHttpHealthCheck {

    /**
     * The predefined URL path.
     */
    String HTTP_HEALTH_CHECK_ENDPOINT = PredefinedUrls.HTTP_HEALTH_CHECK_ENDPOINT;

    /**
     * Returns the HTTP method that must be used to send a verification HTTP request.
     *
     * @return the HTTP method that must be used to send a verification HTTP request
     */
    HttpMethod method() default HttpMethod.GET;
}
