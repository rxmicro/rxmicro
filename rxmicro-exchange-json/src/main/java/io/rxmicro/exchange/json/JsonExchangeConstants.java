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

package io.rxmicro.exchange.json;

/**
 * Defines the JSON constants.
 *
 * @author nedis
 * @since 0.1
 */
public final class JsonExchangeConstants {

    /**
     * Mime content type for JSON format.
     */
    public static final String CONTENT_TYPE_APPLICATION_JSON = "application/json";

    /**
     * Default property name for error json objects.
     */
    public static final String MESSAGE = "message";

    private JsonExchangeConstants() {
    }
}
