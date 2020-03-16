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

package io.rxmicro.examples.rest.controller.handlers;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import io.rxmicro.rest.method.GET;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

// tag::content[]
final class RestControllerWithBody {

    // <1>

    @GET("/jse/completedFuture1")
    CompletableFuture<Response> completedFuture1() {
        return CompletableFuture.completedFuture(new Response());
    }

    @GET("/jse/completedFuture2")
    CompletableFuture<Response> completedFuture2(final Request request) {
        return CompletableFuture.completedFuture(new Response());
    }

    @GET("/jse/completedFuture3")
    CompletableFuture<Response> completedFuture3(final String requestParameter) {
        return CompletableFuture.completedFuture(new Response());
    }

    @GET("/jse/completedFuture4")
    CompletableFuture<Optional<Response>> completedFuture4() {
        return CompletableFuture.completedFuture(Optional.of(new Response()));
    }

    @GET("/jse/completedFuture5")
    CompletableFuture<Optional<Response>> completedFuture5(final Request request) {
        return CompletableFuture.completedFuture(Optional.of(new Response()));
    }

    @GET("/jse/completedFuture6")
    CompletableFuture<Optional<Response>> completedFuture6(final String requestParameter) {
        return CompletableFuture.completedFuture(Optional.of(new Response()));
    }

    // <2>

    @GET("/jse/completionStage1")
    CompletionStage<Response> completionStage1() {
        return CompletableFuture.completedStage(new Response());
    }

    @GET("/jse/completionStage2")
    CompletionStage<Response> completionStage2(final Request request) {
        return CompletableFuture.completedStage(new Response());
    }

    @GET("/jse/completionStage3")
    CompletionStage<Response> completionStage3(final String requestParameter) {
        return CompletableFuture.completedStage(new Response());
    }

    @GET("/jse/completionStage4")
    CompletionStage<Optional<Response>> completionStage4() {
        return CompletableFuture.completedStage(Optional.of(new Response()));
    }

    @GET("/jse/completionStage5")
    CompletionStage<Optional<Response>> completionStage5(final Request request) {
        return CompletableFuture.completedStage(Optional.of(new Response()));
    }

    @GET("/jse/completionStage6")
    CompletionStage<Optional<Response>> completionStage6(final String requestParameter) {
        return CompletableFuture.completedStage(Optional.of(new Response()));
    }

    // <3>

    @GET("/spring-reactor/mono1")
    Mono<Response> mono1() {
        return Mono.just(new Response());
    }

    @GET("/spring-reactor/mono2")
    Mono<Response> mono2(final Request request) {
        return Mono.just(new Response());
    }

    @GET("/spring-reactor/mono3")
    Mono<Response> mono3(final String requestParameter) {
        return Mono.just(new Response());
    }

    // <4>

    @GET("/rxjava3/single1")
    Single<Response> single1() {
        return Single.just(new Response());
    }

    @GET("/rxjava3/single2")
    Single<Response> single2(final Request request) {
        return Single.just(new Response());
    }

    @GET("/rxjava3/single3")
    Single<Response> single3(final String requestParameter) {
        return Single.just(new Response());
    }

    @GET("/rxjava3/maybe1")
    Maybe<Response> maybe1() {
        return Maybe.just(new Response());
    }

    @GET("/rxjava3/maybe2")
    Maybe<Response> maybe2(final Request request) {
        return Maybe.just(new Response());
    }

    @GET("/rxjava3/maybe3")
    Maybe<Response> maybe3(final String requestParameter) {
        return Maybe.just(new Response());
    }
}
// end::content[]
