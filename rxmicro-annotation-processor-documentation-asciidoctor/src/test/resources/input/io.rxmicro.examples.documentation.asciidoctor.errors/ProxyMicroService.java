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

package io.rxmicro.examples.documentation.asciidoctor.errors;

import io.rxmicro.documentation.ModelExceptionErrorResponse;
import io.rxmicro.documentation.SimpleErrorResponse;
import io.rxmicro.examples.documentation.asciidoctor.errors.model.CustomErrorModelException;
import io.rxmicro.examples.documentation.asciidoctor.errors.model.NotAcceptableException;
import io.rxmicro.examples.documentation.asciidoctor.errors.model.Response;
import io.rxmicro.rest.client.HttpClientTimeoutException;
import io.rxmicro.rest.method.GET;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static io.rxmicro.rest.client.RestClientFactory.getRestClient;

// tag::content[]
final class ProxyMicroService {

    private final ExternalMicroService externalMicroService =
            getRestClient(ExternalMicroService.class);

    @GET("/")
    // <1>
    @SimpleErrorResponse(
            status = 404,                               // <2>
            description = "If data not found",          // <3>
            exampleErrorMessage = "Data not found!"     // <4>
    )
    @ModelExceptionErrorResponse(HttpClientTimeoutException.class)  // <5>
    @ModelExceptionErrorResponse(NotAcceptableException.class)      // <6>
    @ModelExceptionErrorResponse(CustomErrorModelException.class)   // <6>
    CompletableFuture<Optional<Response>> get() {
        return externalMicroService.get().thenApply(Optional::of);
    }
}
// end::content[]
