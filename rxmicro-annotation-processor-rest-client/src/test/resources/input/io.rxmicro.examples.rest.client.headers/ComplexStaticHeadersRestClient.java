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

import io.rxmicro.rest.AddHeader;
import io.rxmicro.rest.SetHeader;
import io.rxmicro.rest.client.RestClient;
import io.rxmicro.rest.method.GET;

import java.util.concurrent.CompletableFuture;

// tag::content[]
@RestClient
@SetHeader(name = "Parent-Header1", value = "old-Parent-Header1-value")
@SetHeader(name = "Parent-Header1", value = "new-Parent-Header1-value")
@AddHeader(name = "Parent-Header2", value = "new-Parent-Header2-value")
@AddHeader(name = "Parent-Header2", value = "new-Parent-Header2-value")
public interface ComplexStaticHeadersRestClient {

    @GET("/get1")
    CompletableFuture<Void> get1();

    @GET("/get2")
    @SetHeader(name = "Child-Header1", value = "old-Child-Header1-value")
    @SetHeader(name = "Child-Header1", value = "new-Child-Header1-value")
    @AddHeader(name = "Child-Header2", value = "new-Child-Header2-value")
    @AddHeader(name = "Child-Header2", value = "new-Child-Header2-value")
    CompletableFuture<Void> get2();

    @GET("/get3")
    @SetHeader(name = "Parent-Header1", value = "new-Child-Parent-Header1-value")
    @SetHeader(name = "Parent-Header2", value = "new-Child-Parent-Header2-value")
    CompletableFuture<Void> get3();

    @GET("/get4")
    @AddHeader(name = "Parent-Header1", value = "new-Child-Parent-Header1-value")
    @AddHeader(name = "Parent-Header2", value = "new-Child-Parent-Header2-value")
    CompletableFuture<Void> get4();
}
// end::content[]
