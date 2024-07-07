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

package io.rxmicro.examples.rest.controller.routing;

import io.rxmicro.rest.method.DELETE;
import io.rxmicro.rest.method.GET;
import io.rxmicro.rest.method.HEAD;
import io.rxmicro.rest.method.OPTIONS;
import io.rxmicro.rest.method.PATCH;
import io.rxmicro.rest.method.POST;
import io.rxmicro.rest.method.PUT;

// tag::content[]
final class RoutingUsingHttpBody {

    @GET("/")
    @HEAD("/")
    @OPTIONS("/")
    @DELETE("/")
    @PATCH("/")
    @POST(value = "/", httpBody = false)
    @PUT(value = "/", httpBody = false)
    void handleRequestsWithoutBody(final String parameter) { // <1>
        System.out.println("without body");
    }

    @PATCH(value = "/", httpBody = true)
    @POST("/")
    @PUT("/")
    void handleRequestsWithBody(final String parameter) { // <2>
        System.out.println("with body");
    }
}
// end::content[]
