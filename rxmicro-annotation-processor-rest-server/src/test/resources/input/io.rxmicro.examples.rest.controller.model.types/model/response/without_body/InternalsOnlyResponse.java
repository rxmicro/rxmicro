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

package io.rxmicro.examples.rest.controller.model.types.model.response.without_body;

import io.rxmicro.http.HttpHeaders;
import io.rxmicro.http.HttpVersion;
import io.rxmicro.rest.ResponseBody;
import io.rxmicro.rest.ResponseStatusCode;

public class InternalsOnlyResponse {

    @ResponseStatusCode
    Integer internalResponseStatusCode;

    HttpVersion internalHttpVersion;

    HttpHeaders internalHttpHeaders;

    @ResponseBody
    byte[] internalResponseBody;
}
