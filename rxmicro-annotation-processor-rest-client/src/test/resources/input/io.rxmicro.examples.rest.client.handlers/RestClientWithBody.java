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

package io.rxmicro.examples.rest.client.handlers;

import io.reactivex.rxjava3.core.Single;
import io.rxmicro.examples.rest.client.handlers.model.Request;
import io.rxmicro.examples.rest.client.handlers.model.Response;
import io.rxmicro.rest.client.RestClient;
import io.rxmicro.rest.method.GET;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

// tag::content[]
@RestClient
public interface RestClientWithBody {

    // <1>

    @GET("/jse/completedFuture1")
    CompletableFuture<Response> completedFuture1();

    @GET("/jse/completedFuture2")
    CompletableFuture<Response> completedFuture2(final Request request);

    @GET("/jse/completedFuture3")
    CompletableFuture<Response> completedFuture3(final String requestParameter);

    // <2>

    @GET("/jse/completionStage1")
    CompletionStage<Response> completionStage1();

    @GET("/jse/completionStage2")
    CompletionStage<Response> completionStage2(final Request request);

    @GET("/jse/completionStage3")
    CompletionStage<Response> completionStage3(final String requestParameter);

    // <3>

    @GET("/spring-reactor/mono1")
    Mono<Response> mono1();

    @GET("/spring-reactor/mono2")
    Mono<Response> mono2(final Request request);

    @GET("/spring-reactor/mono3")
    Mono<Response> mono3(final String requestParameter);

    // <4>

    @GET("/rxjava3/single1")
    Single<Response> single1();

    @GET("/rxjava3/single2")
    Single<Response> single2(final Request request);

    @GET("/rxjava3/single3")
    Single<Response> single3(final String requestParameter);
}
// end::content[]
