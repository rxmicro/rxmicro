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

package io.rxmicro.examples.rest.controller.notfound;

import io.reactivex.rxjava3.core.Maybe;
import io.rxmicro.rest.method.GET;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.CompletableFuture.completedFuture;

// tag::content[]
final class NotFoundMicroService {

    @GET("/get1")
    CompletableFuture<Optional<Response>> getOptional1(final Boolean found) { // <1>
        return completedFuture(found ? Optional.of(new Response()) : Optional.empty());
    }

    @GET("/get2")
    Mono<Response> getOptional2(final Boolean found) { // <2>
        return found ? Mono.just(new Response()) : Mono.empty();
    }

    @GET("/get3")
    Maybe<Response> getOptional3(final Boolean found) { // <3>
        return found ? Maybe.just(new Response()) : Maybe.empty();
    }
}
// end::content[]
