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

package io.rxmicro.examples.rest.client.path.variables.complex;

import io.rxmicro.examples.rest.client.path.variables.complex.model.Request1;
import io.rxmicro.examples.rest.client.path.variables.complex.model.Request2;
import io.rxmicro.rest.PathVariable;
import io.rxmicro.rest.client.RestClient;
import io.rxmicro.rest.method.GET;
import io.rxmicro.rest.method.POST;
import io.rxmicro.rest.method.PUT;

import java.util.concurrent.CompletableFuture;

@RestClient
public interface OrderedPathVariablesRestClient {

    @GET("/${a}/${b}")
    CompletableFuture<Void> consumeAB(@PathVariable String a,
                                      @PathVariable String b);

    @GET("/${b}/${a}")
    CompletableFuture<Void> consumeBA(@PathVariable String a,
                                      @PathVariable String b);

    @GET("/${a}/${b}")
    CompletableFuture<Void> consumeAB(Request1 request1);

    @GET("/${b}/${a}")
    CompletableFuture<Void> consumeBA(Request1 request1);

    @GET("/${a}/${b}")
    CompletableFuture<Void> getAB(Request2 request2);

    @PUT("/${a}/${b}")
    CompletableFuture<Void> putAB(Request2 request2);

    @POST("/${a}/${b}")
    CompletableFuture<Void> postAB(Request2 request2);
}
