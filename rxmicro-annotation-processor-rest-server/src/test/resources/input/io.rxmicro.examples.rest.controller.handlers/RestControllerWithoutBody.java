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

import io.reactivex.rxjava3.core.Completable;
import io.rxmicro.rest.method.GET;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@SuppressWarnings("EmptyMethod")
// tag::content[]
final class RestControllerWithoutBody {

    // <1>
    @GET("/void/void1")
    void void1() {
        //do something
    }

    @GET("/void/void2")
    void void2(final Request request) {
        //do something with request
    }

    @GET("/void/void3")
    void void3(final String requestParameter) {
        //do something with requestParameter
    }

    // <2>

    @GET("/jse/completedFuture1")
    CompletableFuture<Void> completedFuture1() {
        return CompletableFuture.completedFuture(null);
    }

    @GET("/jse/completedFuture2")
    CompletableFuture<Void> completedFuture1(final Request request) {
        return CompletableFuture.completedFuture(null);
    }

    @GET("/jse/completedFuture3")
    CompletableFuture<Void> completedFuture1(final String requestParameter) {
        return CompletableFuture.completedFuture(null);
    }

    // <3>

    @GET("/jse/completionStage1")
    CompletionStage<Void> completionStage1() {
        return CompletableFuture.completedStage(null);
    }

    @GET("/jse/completionStage2")
    CompletionStage<Void> completionStage2(final Request request) {
        return CompletableFuture.completedStage(null);
    }

    @GET("/jse/completionStage3")
    CompletionStage<Void> completionStage3(final String requestParameter) {
        return CompletableFuture.completedStage(null);
    }

    // <4>

    @GET("/spring-reactor/mono1")
    Mono<Void> mono1() {
        return Mono.just("").then();
    }

    @GET("/spring-reactor/mono2")
    Mono<Void> mono2(final String requestParameter) {
        return Mono.just("").then();
    }

    @GET("/spring-reactor/mono3")
    Mono<Void> mono4(final Request request) {
        return Mono.just("").then();
    }

    // <5>

    @GET("/rxjava3/completable1")
    Completable completable1() {
        return Completable.complete();
    }

    @GET("/rxjava3/completable2")
    Completable completable2(final Request request) {
        return Completable.complete();
    }

    @GET("/rxjava3/completable3")
    Completable completable3(final String requestParameter) {
        return Completable.complete();
    }
}
// end::content[]
