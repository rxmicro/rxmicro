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

package io.rxmicro.examples.rest.controller.params.model;

import io.rxmicro.rest.Parameter;
import io.rxmicro.rest.ParameterMappingStrategy;

// tag::content[]
// <1>
@ParameterMappingStrategy
public final class Response {

    // <2>
    final String endpointVersion;

    // <3>
    @Parameter("use-Proxy")
    final Boolean useProxy;

    public Response(final Request request) {
        this.endpointVersion = request.getEndpointVersion();
        this.useProxy = request.getUseProxy();
    }

    public Response(final String endpointVersion,
                    final Boolean useProxy) {
        this.endpointVersion = endpointVersion;
        this.useProxy = useProxy;
    }
}
// end::content[]
