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

package io.rxmicro.examples.rest.controller.model.types.model.request.without_body;

import io.rxmicro.http.HttpHeaders;
import io.rxmicro.http.HttpVersion;
import io.rxmicro.rest.RemoteAddress;
import io.rxmicro.rest.RequestBody;
import io.rxmicro.rest.RequestId;
import io.rxmicro.rest.RequestMethod;
import io.rxmicro.rest.RequestUrlPath;
import io.rxmicro.rest.server.detail.model.HttpRequest;

import java.net.SocketAddress;

public class InternalsOnlyRequest {

    @RemoteAddress
    String internalRemoteAddress1;

    @RemoteAddress
    SocketAddress internalRemoteAddress2;

    @RequestUrlPath
    String internalUrlPath;

    @RequestMethod
    String internalRequestMethod;

    HttpVersion internalHttpVersion;

    HttpHeaders internalRequestHeaders;

    @RequestBody
    byte[] internalRequestBody;

    HttpRequest internalRequest;
}
