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

package io.rxmicro.http.local;

import java.util.Set;

/**
 * @author nedis
 * @since 0.3
 */
public final class PredefinedUrls {

    /**
     * The predefined URL path for http health check
     */
    public static final String HTTP_HEALTH_CHECK_ENDPOINT = "/";

    /**
     * All supported health check urls.
     *
     * FIXME: This set must be configured at compile time with all @EnableHttpHealthCheck.value()s
     */
    public static final Set<String> HEALTH_CHECK_URLS = Set.of(HTTP_HEALTH_CHECK_ENDPOINT);

    private PredefinedUrls() {
    }
}
