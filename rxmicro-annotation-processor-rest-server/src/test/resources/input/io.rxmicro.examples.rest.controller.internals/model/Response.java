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

package io.rxmicro.examples.rest.controller.internals.model;

import io.rxmicro.http.HttpHeaders;
import io.rxmicro.http.HttpVersion;
import io.rxmicro.rest.ResponseBody;
import io.rxmicro.rest.ResponseStatusCode;

// tag::content[]
public final class Response {

    //<1>
    @ResponseStatusCode
    final Integer status;

    // <2>
    final HttpVersion version;

    // <3>
    final HttpHeaders headers;

    // <4>
    @ResponseBody
    final byte[] body;

    public Response(final Integer status,
                    final HttpVersion version,
                    final HttpHeaders headers,
                    final byte[] body) {
        this.status = status;
        this.version = version;
        this.headers = headers;
        this.body = body;
    }
}
// end::content[]
