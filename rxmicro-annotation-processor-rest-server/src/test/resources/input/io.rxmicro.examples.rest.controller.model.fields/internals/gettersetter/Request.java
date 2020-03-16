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

package io.rxmicro.examples.rest.controller.model.fields.internals.gettersetter;

import io.rxmicro.rest.RemoteAddress;
import io.rxmicro.rest.RequestBody;
import io.rxmicro.rest.RequestId;
import io.rxmicro.rest.RequestMethod;
import io.rxmicro.rest.RequestUrlPath;
import io.rxmicro.rest.server.detail.model.HttpRequest;

import java.net.SocketAddress;

public final class Request extends Abstract {

    private HttpRequest request;

    private SocketAddress remoteAddress1;

    @RemoteAddress
    private String remoteAddress2;

    @RequestUrlPath
    private String urlPath;

    @RequestMethod
    private String method;

    @RequestBody
    private byte[] body;

    @RequestId
    private String id;

    public HttpRequest getRequest() {
        return request;
    }

    public void setRequest(final HttpRequest request) {
        this.request = request;
    }

    public SocketAddress getRemoteAddress1() {
        return remoteAddress1;
    }

    public void setRemoteAddress1(final SocketAddress remoteAddress1) {
        this.remoteAddress1 = remoteAddress1;
    }

    public String getRemoteAddress2() {
        return remoteAddress2;
    }

    public void setRemoteAddress2(final String remoteAddress2) {
        this.remoteAddress2 = remoteAddress2;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(final String urlPath) {
        this.urlPath = urlPath;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(final String method) {
        this.method = method;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(final byte[] body) {
        this.body = body;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

}
