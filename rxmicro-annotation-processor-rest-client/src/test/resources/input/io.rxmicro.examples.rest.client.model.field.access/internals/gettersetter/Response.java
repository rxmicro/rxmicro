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

package io.rxmicro.examples.rest.client.model.field.access.internals.gettersetter;

import io.rxmicro.http.HttpHeaders;
import io.rxmicro.http.HttpVersion;
import io.rxmicro.rest.ResponseBody;
import io.rxmicro.rest.ResponseStatusCode;

public class Response {

    @ResponseStatusCode
    private Integer internalResponseStatusCode;

    private HttpVersion internalHttpVersion;

    private HttpHeaders internalHttpHeaders;

    @ResponseBody
    private byte[] internalResponseBody;

    public Integer getInternalResponseStatusCode() {
        return internalResponseStatusCode;
    }

    public void setInternalResponseStatusCode(final Integer internalResponseStatusCode) {
        this.internalResponseStatusCode = internalResponseStatusCode;
    }

    public HttpVersion getInternalHttpVersion() {
        return internalHttpVersion;
    }

    public void setInternalHttpVersion(final HttpVersion internalHttpVersion) {
        this.internalHttpVersion = internalHttpVersion;
    }

    public HttpHeaders getInternalHttpHeaders() {
        return internalHttpHeaders;
    }

    public void setInternalHttpHeaders(final HttpHeaders internalHttpHeaders) {
        this.internalHttpHeaders = internalHttpHeaders;
    }

    public byte[] getInternalResponseBody() {
        return internalResponseBody;
    }

    public void setInternalResponseBody(final byte[] internalResponseBody) {
        this.internalResponseBody = internalResponseBody;
    }
}
