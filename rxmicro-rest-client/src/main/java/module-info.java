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

import io.rxmicro.http.client.HttpClientContentConverter;
import io.rxmicro.http.client.HttpClientFactory;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
module rxmicro.rest.client {
    requires transitive rxmicro.rest;
    requires transitive rxmicro.http.client;
    requires transitive rxmicro.logger;

    exports io.rxmicro.rest.client;

    exports io.rxmicro.rest.client.detail;

    opens io.rxmicro.rest.client to
            rxmicro.config;

    uses HttpClientFactory;
    uses HttpClientContentConverter;
}
