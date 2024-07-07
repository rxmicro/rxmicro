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

package io.rxmicro.examples.rest.client.params;

import io.rxmicro.rest.AddQueryParameter;
import io.rxmicro.rest.SetQueryParameter;
import io.rxmicro.rest.client.RestClient;
import io.rxmicro.rest.method.GET;

import java.util.concurrent.CompletableFuture;

// tag::content[]
@RestClient
@SetQueryParameter(name = "parent-param1", value = "old-parent-param1-value")
@SetQueryParameter(name = "parent-param1", value = "new-parent-param1-value")
@AddQueryParameter(name = "parent-param2", value = "new-parent-param2-value")
@AddQueryParameter(name = "parent-param2", value = "new-parent-param2-value")
public interface ComplexStaticQueryParametersRestClient {

    @GET("/get1")
    CompletableFuture<Void> get1();

    @GET("/get2")
    @SetQueryParameter(name = "child-param1", value = "old-child-param1-value")
    @SetQueryParameter(name = "child-param1", value = "new-child-param1-value")
    @AddQueryParameter(name = "child-param2", value = "new-child-param2-value")
    @AddQueryParameter(name = "child-param2", value = "new-child-param2-value")
    CompletableFuture<Void> get2();

    @GET("/get3")
    @SetQueryParameter(name = "parent-param1", value = "new-child-parent-param1-value")
    @SetQueryParameter(name = "parent-param2", value = "new-child-parent-param2-value")
    CompletableFuture<Void> get3();

    @GET("/get4")
    @AddQueryParameter(name = "parent-param1", value = "new-child-parent-param1-value")
    @AddQueryParameter(name = "parent-param2", value = "new-child-parent-param2-value")
    CompletableFuture<Void> get4();
}
// end::content[]
