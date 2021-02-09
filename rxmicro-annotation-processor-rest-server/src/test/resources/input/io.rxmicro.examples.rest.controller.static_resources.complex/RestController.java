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

package io.rxmicro.examples.rest.controller.static_resources.complex;

import io.rxmicro.rest.method.GET;
import io.rxmicro.rest.server.StaticResources;

import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.completedStage;

@StaticResources("/static*")
@StaticResources("*.txt")
@StaticResources("/static/**")
@StaticResources("**.log")
@StaticResources("/*")
@StaticResources("/**")
@StaticResources(urls = "/custom1", filePaths = "custom.txt")
@StaticResources(urls = "/custom2", filePaths = "custom.txt")
@StaticResources("/parent/child/link.txt")
public final class RestController {

    @GET("/say-hello-world")
    CompletionStage<Response> getHelloWorld() {
        return completedStage(new Response("Hello world!"));
    }
}

