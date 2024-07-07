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

package io.rxmicro.examples.documentation.asciidoctor.errors.complex;

import io.rxmicro.documentation.ModelExceptionErrorResponse;
import io.rxmicro.documentation.SimpleErrorResponse;
import io.rxmicro.examples.documentation.asciidoctor.errors.complex.model.CustomWithInternalsException;
import io.rxmicro.rest.method.GET;

final class ComplexMicroService {

    @SimpleErrorResponse(
            status = 429,
            description = "Too Many Requests",
            headerNames = "Retry-After",
            headerValueExamples = "3600",
            headerDescriptions = "Indicates how long to wait before making a new request.",
            headersRequired = false
    )
    @SimpleErrorResponse(
            status = 500,
            description = "Internal Server Error",
            headerNames = "Retry-After",
            headerValueExamples = "3600",
            headerDescriptions = "Indicates how long to wait before making a new request.",
            headersRequired = false,
            paramNames = {"message", "readMore"},
            paramValueExamples = {"NullPointerException", "https://example.com/read-more.html"},
            paramsRequired = {true, false},
            paramDescriptions = "The detailed cause of error"
    )
    @ModelExceptionErrorResponse(CustomWithInternalsException.class)
    @GET("/")
    void test() {

    }
}

