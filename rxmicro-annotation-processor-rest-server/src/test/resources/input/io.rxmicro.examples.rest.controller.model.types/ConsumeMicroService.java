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

import io.rxmicro.examples.rest.controller.model.types.model.request.http_body.HttpBodyRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.http_body.HttpBodyWithHeadersRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.http_body.HttpBodyWithInternalsAndHeadersRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.http_body.HttpBodyWithInternalsRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.http_body.HttpBodyWithPathVarAndHeadersRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.http_body.HttpBodyWithPathVarAndInternalsAndHeadersRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.http_body.HttpBodyWithPathVarAndInternalsRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.http_body.HttpBodyWithPathVarRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_or_http_body.QueryOrHttpBodyRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_or_http_body.QueryOrHttpBodyWithHeadersRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_or_http_body.QueryOrHttpBodyWithInternalsAndHeadersRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_or_http_body.QueryOrHttpBodyWithInternalsRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_or_http_body.QueryOrHttpBodyWithPathVarAndHeadersRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_or_http_body.QueryOrHttpBodyWithPathVarAndInternalsAndHeadersRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_or_http_body.QueryOrHttpBodyWithPathVarAndInternalsRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_or_http_body.QueryOrHttpBodyWithPathVarRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_string.QueryStringRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_string.QueryStringWithHeadersRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_string.QueryStringWithInternalsAndHeadersRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_string.QueryStringWithInternalsRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_string.QueryStringWithPathVarAndHeadersRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_string.QueryStringWithPathVarAndInternalsAndHeadersRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_string.QueryStringWithPathVarAndInternalsRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_string.QueryStringWithPathVarRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.without_any_fields.WithoutAnyFieldsRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.without_body.HeadersOnlyRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.without_body.InternalsAndHeadersRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.without_body.InternalsOnlyRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.without_body.PathVarAndHeadersRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.without_body.PathVarAndInternalsRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.without_body.PathVarOnlyRequest;
import io.rxmicro.rest.method.GET;
import io.rxmicro.rest.method.POST;

public class ConsumeMicroService {

    @POST("/consume01")
    void consume(final HttpBodyRequest request) {
        // do something
    }

    @POST("/consume02")
    void consume(final HttpBodyWithHeadersRequest request) {
        // do something
    }

    @POST("/consume03/${a}/${b}/${c}/${d}/${e}/${f}/${g}/${j}/${h}/${i}/${j}/${k}/${l}/${m}")
    void consume(final HttpBodyWithPathVarRequest request) {
        // do something
    }

    @POST("/consume04")
    void consume(final HttpBodyWithInternalsRequest request) {
        // do something
    }

    @POST("/consume05")
    void consume(final HttpBodyWithInternalsAndHeadersRequest request) {
        // do something
    }

    @POST("/consume06/${a}/${b}/${c}/${d}/${e}/${f}/${g}/${j}/${h}/${i}/${j}/${k}/${l}/${m}")
    void consume(final HttpBodyWithPathVarAndHeadersRequest request) {
        // do something
    }

    @POST("/consume07/${a}/${b}/${c}/${d}/${e}/${f}/${g}/${j}/${h}/${i}/${j}/${k}/${l}/${m}")
    void consume(final HttpBodyWithPathVarAndInternalsRequest request) {
        // do something
    }

    @POST("/consume08/${a}/${b}/${c}/${d}/${e}/${f}/${g}/${j}/${h}/${i}/${j}/${k}/${l}/${m}")
    void consume(final HttpBodyWithPathVarAndInternalsAndHeadersRequest request) {
        // do something
    }

    // -------------------------------------------------------------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------------------------------------------------------------

    @GET("/consume11")
    @POST("/consume11")
    void consume(final InternalsOnlyRequest request) {
        // do something
    }

    @GET("/consume12/${a}/${b}/${c}/${d}/${e}/${f}/${g}/${j}/${h}/${i}/${j}/${k}/${l}/${m}")
    @POST("/consume12/${a}/${b}/${c}/${d}/${e}/${f}/${g}/${j}/${h}/${i}/${j}/${k}/${l}/${m}")
    void consume(final PathVarOnlyRequest request) {
        // do something
    }

    @GET("/consume13")
    @POST("/consume13")
    void consume(final HeadersOnlyRequest request) {
        // do something
    }

    @GET("/consume14")
    @POST("/consume14")
    void consume(final InternalsAndHeadersRequest request) {
        // do something
    }

    @GET("/consume15/${a}/${b}/${c}/${d}/${e}/${f}/${g}/${j}/${h}/${i}/${j}/${k}/${l}/${m}")
    @POST("/consume15/${a}/${b}/${c}/${d}/${e}/${f}/${g}/${j}/${h}/${i}/${j}/${k}/${l}/${m}")
    void consume(final PathVarAndInternalsRequest request) {
        // do something
    }

    @GET("/consume16/${a}/${b}/${c}/${d}/${e}/${f}/${g}/${j}/${h}/${i}/${j}/${k}/${l}/${m}")
    @POST("/consume16/${a}/${b}/${c}/${d}/${e}/${f}/${g}/${j}/${h}/${i}/${j}/${k}/${l}/${m}")
    void consume(final PathVarAndHeadersRequest request) {
        // do something
    }

    // -------------------------------------------------------------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------------------------------------------------------------

    @GET("/consume31")
    @POST("/consume31")
    void consume(final QueryOrHttpBodyRequest request) {
        // do something
    }

    @GET("/consume32")
    @POST("/consume32")
    void consume(final QueryOrHttpBodyWithHeadersRequest request) {
        // do something
    }

    @GET("/consume33/${a}/${b}/${c}/${d}/${e}/${f}/${g}/${j}/${h}/${i}/${j}/${k}/${l}/${m}")
    @POST("/consume33/${a}/${b}/${c}/${d}/${e}/${f}/${g}/${j}/${h}/${i}/${j}/${k}/${l}/${m}")
    void consume(final QueryOrHttpBodyWithPathVarRequest request) {
        // do something
    }

    @GET("/consume34")
    @POST("/consume34")
    void consume(final QueryOrHttpBodyWithInternalsRequest request) {
        // do something
    }

    @GET("/consume35")
    @POST("/consume35")
    void consume(final QueryOrHttpBodyWithInternalsAndHeadersRequest request) {
        // do something
    }

    @GET("/consume36/${a}/${b}/${c}/${d}/${e}/${f}/${g}/${j}/${h}/${i}/${j}/${k}/${l}/${m}")
    @POST("/consume36/${a}/${b}/${c}/${d}/${e}/${f}/${g}/${j}/${h}/${i}/${j}/${k}/${l}/${m}")
    void consume(final QueryOrHttpBodyWithPathVarAndHeadersRequest request) {
        // do something
    }

    @GET("/consume37/${a}/${b}/${c}/${d}/${e}/${f}/${g}/${j}/${h}/${i}/${j}/${k}/${l}/${m}")
    @POST("/consume37/${a}/${b}/${c}/${d}/${e}/${f}/${g}/${j}/${h}/${i}/${j}/${k}/${l}/${m}")
    void consume(final QueryOrHttpBodyWithPathVarAndInternalsRequest request) {
        // do something
    }

    @GET("/consume38/${a}/${b}/${c}/${d}/${e}/${f}/${g}/${j}/${h}/${i}/${j}/${k}/${l}/${m}")
    @POST("/consume38/${a}/${b}/${c}/${d}/${e}/${f}/${g}/${j}/${h}/${i}/${j}/${k}/${l}/${m}")
    void consume(final QueryOrHttpBodyWithPathVarAndInternalsAndHeadersRequest request) {
        // do something
    }

    // -------------------------------------------------------------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------------------------------------------------------------

    @GET("/consume41")
    void consume(final QueryStringRequest request) {
        // do something
    }

    @GET("/consume42")
    void consume(final QueryStringWithHeadersRequest request) {
        // do something
    }

    @GET("/consume43/${a}/${b}/${c}/${d}/${e}/${f}/${g}/${j}/${h}/${i}/${j}/${k}/${l}/${m}")
    void consume(final QueryStringWithPathVarRequest request) {
        // do something
    }

    @GET("/consume44")
    void consume(final QueryStringWithInternalsRequest request) {
        // do something
    }

    @GET("/consume45")
    void consume(final QueryStringWithInternalsAndHeadersRequest request) {
        // do something
    }

    @GET("/consume46/${a}/${b}/${c}/${d}/${e}/${f}/${g}/${j}/${h}/${i}/${j}/${k}/${l}/${m}")
    void consume(final QueryStringWithPathVarAndHeadersRequest request) {
        // do something
    }

    @GET("/consume47/${a}/${b}/${c}/${d}/${e}/${f}/${g}/${j}/${h}/${i}/${j}/${k}/${l}/${m}")
    void consume(final QueryStringWithPathVarAndInternalsRequest request) {
        // do something
    }

    @GET("/consume48/${a}/${b}/${c}/${d}/${e}/${f}/${g}/${j}/${h}/${i}/${j}/${k}/${l}/${m}")
    void consume(final QueryStringWithPathVarAndInternalsAndHeadersRequest request) {
        // do something
    }

    // -------------------------------------------------------------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------------------------------------------------------------

    @GET("/consume51")
    @POST("/consume51")
    void consume(final WithoutAnyFieldsRequest request) {
        // do something
    }
}
