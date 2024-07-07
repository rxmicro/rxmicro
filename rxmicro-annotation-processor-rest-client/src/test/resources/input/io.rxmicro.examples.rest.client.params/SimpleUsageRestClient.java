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

package io.rxmicro.examples.rest.client.params;

import io.rxmicro.examples.rest.client.params.model.Request;
import io.rxmicro.examples.rest.client.params.model.Response;
import io.rxmicro.rest.Parameter;
import io.rxmicro.rest.ParameterMappingStrategy;
import io.rxmicro.rest.client.RestClient;
import io.rxmicro.rest.method.GET;

import java.util.concurrent.CompletableFuture;

// tag::content[]
@RestClient
public interface SimpleUsageRestClient {

    @GET("/get1")
    CompletableFuture<Response> get1(final Request request); // <1>

    // <2>
    @GET("/get2")
    // <3>
    @ParameterMappingStrategy
    CompletableFuture<Response> get2(final String endpointVersion,        // <4>
                                     final @Parameter("use-Proxy") Boolean useProxy); // <5>
}
// end::content[]
