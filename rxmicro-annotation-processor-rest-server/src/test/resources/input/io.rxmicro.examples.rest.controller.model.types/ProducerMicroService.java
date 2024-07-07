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

package io.rxmicro.examples.rest.controller.model.types;

import io.rxmicro.examples.rest.controller.model.types.model.response.body.BodyOnlyResponse;
import io.rxmicro.examples.rest.controller.model.types.model.response.body.BodyWithHeadersResponse;
import io.rxmicro.examples.rest.controller.model.types.model.response.body.BodyWithInternalsAndHeadersResponse;
import io.rxmicro.examples.rest.controller.model.types.model.response.body.BodyWithInternalsResponse;
import io.rxmicro.examples.rest.controller.model.types.model.response.without_body.HeadersOnlyResponse;
import io.rxmicro.examples.rest.controller.model.types.model.response.without_body.InternalsAndHeadersResponse;
import io.rxmicro.examples.rest.controller.model.types.model.response.without_body.InternalsOnlyResponse;
import io.rxmicro.rest.method.GET;

import java.util.concurrent.CompletionStage;

public class ProducerMicroService {

    @GET("/produce01")
    CompletionStage<BodyOnlyResponse> produce01() {
        throw new UnsupportedOperationException();
    }

    @GET("/produce02")
    CompletionStage<BodyWithHeadersResponse> produce02() {
        throw new UnsupportedOperationException();
    }

    @GET("/produce03")
    CompletionStage<BodyWithInternalsResponse> produce03() {
        throw new UnsupportedOperationException();
    }

    @GET("/produce04")
    CompletionStage<BodyWithInternalsAndHeadersResponse> produce04() {
        throw new UnsupportedOperationException();
    }

    @GET("/produce11")
    CompletionStage<HeadersOnlyResponse> produce11() {
        throw new UnsupportedOperationException();
    }

    @GET("/produce12")
    CompletionStage<InternalsOnlyResponse> produce12() {
        throw new UnsupportedOperationException();
    }

    @GET("/produce13")
    CompletionStage<InternalsAndHeadersResponse> produce13() {
        throw new UnsupportedOperationException();
    }
}
