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

package io.rxmicro.examples.documentation.asciidoctor.full;

import io.rxmicro.documentation.ModelExceptionErrorResponse;
import io.rxmicro.documentation.SimpleErrorResponse;
import io.rxmicro.examples.documentation.asciidoctor.full.model.NotAcceptableException;
import io.rxmicro.examples.documentation.asciidoctor.full.model.Request;
import io.rxmicro.examples.documentation.asciidoctor.full.model.Response;
import io.rxmicro.rest.Version;
import io.rxmicro.rest.client.HttpClientTimeoutException;
import io.rxmicro.rest.method.GET;
import io.rxmicro.rest.server.feature.EnableCrossOriginResourceSharing;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@EnableCrossOriginResourceSharing
@Version(value = "latest", strategy = Version.Strategy.HEADER)
@SimpleErrorResponse(status = 404, description = "If object is not found", exampleErrorMessage = "Object not found")
@ModelExceptionErrorResponse(HttpClientTimeoutException.class)
@ModelExceptionErrorResponse(NotAcceptableException.class)
@SimpleErrorResponse(status = 406, description = "406", exampleErrorMessage = "Code: 406")
final class MicroService1 {

    @GET("/${category}/${type}")
    @ModelExceptionErrorResponse(NotAcceptableException.class)
    @SimpleErrorResponse(status = 504, description = "504", exampleErrorMessage = "Code: 504")
    CompletableFuture<Optional<Response>> handle1(final Request request) {
        return CompletableFuture.completedFuture(Optional.empty());
    }

    @GET("/${category}-${type}")
    @SimpleErrorResponse(status = 404, description = "If Handler is not found", exampleErrorMessage = "Handler not found")
    CompletableFuture<Optional<Response>> handle2(final Request request) {
        return CompletableFuture.completedFuture(Optional.empty());
    }
}
