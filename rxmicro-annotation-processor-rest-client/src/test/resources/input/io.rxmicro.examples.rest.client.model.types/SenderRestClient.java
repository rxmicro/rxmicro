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

package io.rxmicro.examples.rest.client.model.types;

import io.rxmicro.examples.rest.client.model.types.model.request.http_body.HttpBodyRequest;
import io.rxmicro.examples.rest.client.model.types.model.request.http_body.HttpBodyWithHeadersRequest;
import io.rxmicro.examples.rest.client.model.types.model.request.http_body.HttpBodyWithPathVarAndHeadersRequest;
import io.rxmicro.examples.rest.client.model.types.model.request.http_body.HttpBodyWithPathVarRequest;
import io.rxmicro.examples.rest.client.model.types.model.request.query_string.QueryStringRequest;
import io.rxmicro.examples.rest.client.model.types.model.request.query_string.QueryStringWithHeadersRequest;
import io.rxmicro.examples.rest.client.model.types.model.request.query_string.QueryStringWithPathVarAndHeadersRequest;
import io.rxmicro.examples.rest.client.model.types.model.request.query_string.QueryStringWithPathVarRequest;
import io.rxmicro.examples.rest.client.model.types.model.request.without_any_fields.WithoutAnyFieldsRequest;
import io.rxmicro.examples.rest.client.model.types.model.request.without_body.HeadersOnlyRequest;
import io.rxmicro.examples.rest.client.model.types.model.request.without_body.PathVarAndHeadersRequest;
import io.rxmicro.examples.rest.client.model.types.model.request.without_body.PathVarOnlyRequest;
import io.rxmicro.rest.client.RestClient;
import io.rxmicro.rest.method.GET;
import io.rxmicro.rest.method.POST;

import java.util.concurrent.CompletionStage;

@RestClient
public interface SenderRestClient {

    @POST("/consume01")
    CompletionStage<Void> consume(final HttpBodyRequest request);

    @POST("/consume02")
    CompletionStage<Void> consume(final HttpBodyWithHeadersRequest request);

    @POST("/consume03/${a}/${b}/${c}/${d}/${e}/${f}/${g}/${j}/${h}/${i}/${j}/${k}/${l}/${m}")
    CompletionStage<Void> consume(final HttpBodyWithPathVarRequest request);

    @POST("/consume06/${a}/${b}/${c}/${d}/${e}/${f}/${g}/${j}/${h}/${i}/${j}/${k}/${l}/${m}")
    CompletionStage<Void> consume(final HttpBodyWithPathVarAndHeadersRequest request);

    // -------------------------------------------------------------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------------------------------------------------------------

    @GET("/consume12/${a}/${b}/${c}/${d}/${e}/${f}/${g}/${j}/${h}/${i}/${j}/${k}/${l}/${m}")
    CompletionStage<Void> get(final PathVarOnlyRequest request);

    @POST("/consume12/${a}/${b}/${c}/${d}/${e}/${f}/${g}/${j}/${h}/${i}/${j}/${k}/${l}/${m}")
    CompletionStage<Void> post(final PathVarOnlyRequest request);

    @GET("/consume13")
    CompletionStage<Void> get(final HeadersOnlyRequest request);

    @POST("/consume13")
    CompletionStage<Void> post(final HeadersOnlyRequest request);

    @GET("/consume16/${a}/${b}/${c}/${d}/${e}/${f}/${g}/${j}/${h}/${i}/${j}/${k}/${l}/${m}")
    CompletionStage<Void> get(final PathVarAndHeadersRequest request);

    @POST("/consume16/${a}/${b}/${c}/${d}/${e}/${f}/${g}/${j}/${h}/${i}/${j}/${k}/${l}/${m}")
    CompletionStage<Void> post(final PathVarAndHeadersRequest request);

    // -------------------------------------------------------------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------------------------------------------------------------

    @GET("/consume41")
    CompletionStage<Void> consume(final QueryStringRequest request);

    @GET("/consume42")
    CompletionStage<Void> consume(final QueryStringWithHeadersRequest request);

    @GET("/consume43/${a}/${b}/${c}/${d}/${e}/${f}/${g}/${j}/${h}/${i}/${j}/${k}/${l}/${m}")
    CompletionStage<Void> consume(final QueryStringWithPathVarRequest request);

    @GET("/consume46/${a}/${b}/${c}/${d}/${e}/${f}/${g}/${j}/${h}/${i}/${j}/${k}/${l}/${m}")
    CompletionStage<Void> consume(final QueryStringWithPathVarAndHeadersRequest request);

    // -------------------------------------------------------------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------------------------------------------------------------

    @GET("/consume51")
    CompletionStage<Void> get(final WithoutAnyFieldsRequest request);

    @POST("/consume51")
    CompletionStage<Void> post(final WithoutAnyFieldsRequest request);
}
