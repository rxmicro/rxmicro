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

package io.rxmicro.examples.rest.controller.set.status.code;

import io.rxmicro.rest.method.GET;
import io.rxmicro.rest.server.SetStatusCode;

// tag::content[]
final class MicroService {

    @GET("/200")
    void success() {
    }

    @GET("/307")
    @SetStatusCode(307)
    void permanentRedirect() {
    }

    @GET("/404")
    @SetStatusCode(404)
    void notFound() {
    }

    @GET("/500")
    @SetStatusCode(500)
    void internalError() {
    }
}
// end::content[]
