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

package io.rxmicro.examples.rest.controller.internals.model;

import io.rxmicro.http.HttpHeaders;
import io.rxmicro.http.HttpVersion;
import io.rxmicro.rest.RemoteAddress;
import io.rxmicro.rest.RequestBody;
import io.rxmicro.rest.RequestMethod;
import io.rxmicro.rest.RequestUrlPath;

// tag::content[]
public final class Request {

    // <1>
    @RemoteAddress
    String remoteAddress1;

    // <2>
    @RequestUrlPath
    String urlPath;

    // <3>
    @RequestMethod
    String method;

    // <4>
    HttpVersion httpVersion;

    // <5>
    HttpHeaders headers;

    // <6>
    @RequestBody
    byte[] bodyBytes;

    public String getRemoteAddress1() {
        return remoteAddress1;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public String getMethod() {
        return method;
    }

    public HttpVersion getHttpVersion() {
        return httpVersion;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public byte[] getBodyBytes() {
        return bodyBytes;
    }
}
// end::content[]
