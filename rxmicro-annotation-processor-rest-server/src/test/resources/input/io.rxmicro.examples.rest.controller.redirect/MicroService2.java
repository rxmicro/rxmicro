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

package io.rxmicro.examples.rest.controller.redirect;

import io.rxmicro.examples.rest.controller.redirect.model.RedirectResponse;
import io.rxmicro.rest.SetHeader;
import io.rxmicro.rest.method.PUT;
import io.rxmicro.rest.server.SetStatusCode;

import java.util.concurrent.CompletableFuture;

import static io.rxmicro.http.HttpHeaders.LOCATION;
import static java.util.concurrent.CompletableFuture.completedFuture;

@SuppressWarnings("EmptyMethod")
final class MicroService2 {

    // tag::content[]
    @PUT(value = "/old-url1")
    CompletableFuture<RedirectResponse> redirect1() {
        return completedFuture(new RedirectResponse("/new-url")); // <1>
    }

    @PUT(value = "/old-url2")
    // <2>
    @SetStatusCode(307)
    @SetHeader(name = LOCATION, value = "/new-url")
    void redirect2() {
    }
    // end::content[]

    @PUT(value = "/new-url")
    void put(final String parameter) {
        System.out.println(parameter);
    }
}

