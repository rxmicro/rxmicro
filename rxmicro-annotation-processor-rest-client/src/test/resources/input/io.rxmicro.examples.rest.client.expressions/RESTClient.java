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

package io.rxmicro.examples.rest.client.expressions;

import io.rxmicro.rest.AddHeader;
import io.rxmicro.rest.client.RestClient;
import io.rxmicro.rest.method.PUT;

import java.util.concurrent.CompletableFuture;

// tag::content[]
@RestClient(
        configClass = CustomRestClientConfig.class,    // <1>
        configNameSpace = "custom"                     // <2>
)
@AddHeader(name = "Use-Proxy", value = "${useProxy}")  // <3>
public interface RESTClient {

    @PUT("/")
    @AddHeader(name = "Debug", value = "Use-Proxy=${useProxy}, Mode=${mode}")             // <3>
    @AddHeader(name = "Endpoint", value = "Schema=${schema}, Host=${host}, Port=${port}") // <3>
    CompletableFuture<Void> put();
}
// end::content[]
