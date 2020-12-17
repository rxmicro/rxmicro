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

package io.rxmicro.examples.rest.controller.model.field.access.internals.gettersetter;

import io.rxmicro.http.HttpHeaders;
import io.rxmicro.http.HttpVersion;
import io.rxmicro.rest.RemoteAddress;
import io.rxmicro.rest.RequestBody;
import io.rxmicro.rest.RequestMethod;
import io.rxmicro.rest.RequestUrlPath;
import io.rxmicro.rest.server.detail.model.HttpRequest;

import java.net.SocketAddress;

public class Request {

    @RemoteAddress
    private String internalRemoteAddress1;

    private SocketAddress internalRemoteAddress2;

    @RequestUrlPath
    private String internalUrlPath;

    @RequestMethod
    private String internalRequestMethod;

    private HttpVersion internalHttpVersion;

    private HttpHeaders internalRequestHeaders;

    @RequestBody
    private byte[] internalRequestBody;

    private HttpRequest internalRequest;

    public String getInternalRemoteAddress1() {
        return internalRemoteAddress1;
    }

    public void setInternalRemoteAddress1(final String internalRemoteAddress1) {
        this.internalRemoteAddress1 = internalRemoteAddress1;
    }

    public SocketAddress getInternalRemoteAddress2() {
        return internalRemoteAddress2;
    }

    public void setInternalRemoteAddress2(final SocketAddress internalRemoteAddress2) {
        this.internalRemoteAddress2 = internalRemoteAddress2;
    }

    public String getInternalUrlPath() {
        return internalUrlPath;
    }

    public void setInternalUrlPath(final String internalUrlPath) {
        this.internalUrlPath = internalUrlPath;
    }

    public String getInternalRequestMethod() {
        return internalRequestMethod;
    }

    public void setInternalRequestMethod(final String internalRequestMethod) {
        this.internalRequestMethod = internalRequestMethod;
    }

    public HttpVersion getInternalHttpVersion() {
        return internalHttpVersion;
    }

    public void setInternalHttpVersion(final HttpVersion internalHttpVersion) {
        this.internalHttpVersion = internalHttpVersion;
    }

    public HttpHeaders getInternalRequestHeaders() {
        return internalRequestHeaders;
    }

    public void setInternalRequestHeaders(final HttpHeaders internalRequestHeaders) {
        this.internalRequestHeaders = internalRequestHeaders;
    }

    public byte[] getInternalRequestBody() {
        return internalRequestBody;
    }

    public void setInternalRequestBody(final byte[] internalRequestBody) {
        this.internalRequestBody = internalRequestBody;
    }

    public HttpRequest getInternalRequest() {
        return internalRequest;
    }

    public void setInternalRequest(final HttpRequest internalRequest) {
        this.internalRequest = internalRequest;
    }
}
