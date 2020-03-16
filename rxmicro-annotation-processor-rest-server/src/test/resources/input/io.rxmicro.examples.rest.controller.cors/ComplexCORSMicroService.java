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

package io.rxmicro.examples.rest.controller.cors;

import io.rxmicro.rest.PathVariable;
import io.rxmicro.rest.method.GET;
import io.rxmicro.rest.method.HEAD;
import io.rxmicro.rest.method.POST;
import io.rxmicro.rest.method.PUT;
import io.rxmicro.rest.server.feature.EnableCrossOriginResourceSharing;

@EnableCrossOriginResourceSharing(
        allowOrigins = {
                "http://localhost:8080",
                "https://localhost:8443"
        },
        accessControlAllowCredentials = true,
        accessControlMaxAge = 600,
        exposedHeaders = "Ex-Mode"
)
final class ComplexCORSMicroService {

    @GET("/handler1")
    @HEAD("/handler1")
    @POST("/handler1")
    @PUT("/handler1")
    void handler1() {
    }

    @GET("/handler2")
    @POST("/handler2")
    void handler2() {
    }

    @GET("/${path}")
    @HEAD("/${path}")
    @POST("/${path}")
    void handler3(final @PathVariable String path) {
    }
}
