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

package io.rxmicro.examples.validation.client.all.types;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.rxmicro.examples.validation.client.all.types.model.Request;
import io.rxmicro.examples.validation.client.all.types.model.Response;
import io.rxmicro.rest.client.RestClient;
import io.rxmicro.rest.method.PUT;
import io.rxmicro.validation.constraint.Email;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("UnusedReturnValue")
@RestClient
public interface RESTClient1 {

    @PUT("/put1")
    CompletableFuture<Void> put1(@Email String email);

    @PUT("/put2")
    CompletableFuture<Void> put2(Request request);

    @PUT("/put3")
    CompletableFuture<Void> put2();

    @PUT("/put4")
    CompletableFuture<Response> put4(@Email String email);

    @PUT("/put5")
    CompletableFuture<Response> put5(Request request);

    @PUT("/put6")
    CompletableFuture<Response> put6();

    @PUT("/put7")
    Mono<Void> put7(@Email String email);

    @PUT("/put8")
    Mono<Void> put8(Request request);

    @PUT("/put9")
    Mono<Void> put9();

    @PUT("/put10")
    Mono<Response> put10(@Email String email);

    @PUT("/put11")
    Mono<Response> put11(Request request);

    @PUT("/put12")
    Mono<Response> put12();

    @PUT("/put13")
    Completable put13(@Email String email);

    @PUT("/put14")
    Completable put14(Request request);

    @PUT("/put15")
    Completable put15();

    @PUT("/put16")
    Single<Response> put16(@Email String email);

    @PUT("/put17")
    Single<Response> put17(Request request);

    @PUT("/put18")
    Single<Response> put18();

}
