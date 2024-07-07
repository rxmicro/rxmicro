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

package io.rxmicro.examples.rest.client.headers;

import io.rxmicro.rest.SetHeader;
import io.rxmicro.rest.client.RestClient;
import io.rxmicro.rest.method.GET;

import java.util.concurrent.CompletableFuture;

// tag::content[]
@RestClient
// <1>
@SetHeader(name = "Mode", value = "Demo")
public interface StaticHeadersRestClient {

    @GET("/get1")
    CompletableFuture<Void> get1();

    @GET("/get2")
    // <2>
    @SetHeader(name = "Debug", value = "true")
    CompletableFuture<Void> get2();
}
// end::content[]
