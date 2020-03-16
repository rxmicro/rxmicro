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

import io.rxmicro.http.client.HttpClientContentConverter;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@SuppressWarnings("JavaRequiresAutoModule")
module rxmicro.test.mockito {
    requires transitive rxmicro.test;
    requires transitive org.mockito;

    exports io.rxmicro.test.mockito;
    exports io.rxmicro.test.mockito.httpclient;
    exports io.rxmicro.test.mockito.r2dbc;
    exports io.rxmicro.test.mockito.mongo;

    uses HttpClientContentConverter;
}