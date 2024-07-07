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

package io.rxmicro.examples.rest.controller.errors;

import io.rxmicro.rest.method.PUT;

import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.CompletableFuture.failedFuture;

// tag::content[]
final class MicroService {

    @PUT(value = "/business-object-1", httpBody = false)
    CompletableFuture<Void> updateObject1(final Integer id) {
        return failedFuture(new CustomNotFoundException("Object not found by id=" + id)); // <1>
    }

    @PUT(value = "/business-object-2", httpBody = false)
    CompletableFuture<Void> updateObject2(final Integer id) {
        throw new CustomNotFoundException("Object not found by id=" + id); // <2>
    }
}
// end::content[]
