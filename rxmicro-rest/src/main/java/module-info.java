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

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
module rxmicro.rest {
    requires transitive rxmicro.common;
    requires transitive rxmicro.http;
    requires transitive rxmicro.model;

    exports io.rxmicro.rest;
    exports io.rxmicro.rest.model;
    exports io.rxmicro.rest.method;
    exports io.rxmicro.rest.component;

    exports io.rxmicro.rest.local to
            rxmicro.rest.server,
            rxmicro.rest.client;

}
