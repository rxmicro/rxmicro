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

package io.rxmicro.examples.documentation.asciidoctor.echo;

import io.rxmicro.rest.method.GET;
import io.rxmicro.rest.method.POST;

import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.CompletableFuture.completedFuture;

// tag::content[]
final class MicroService {

    @GET("/")
    @POST("/")
    @POST(value = "/", httpBody = false)
    CompletableFuture<Echo> echo(final Echo echo) {
        return completedFuture(echo);
    }
}
// end::content[]
