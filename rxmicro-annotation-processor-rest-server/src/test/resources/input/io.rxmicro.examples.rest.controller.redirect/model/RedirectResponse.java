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

package io.rxmicro.examples.rest.controller.redirect.model;

import io.rxmicro.rest.Header;
import io.rxmicro.rest.ResponseStatusCode;

import static io.rxmicro.http.HttpHeaders.LOCATION;
import static java.util.Objects.requireNonNull;

// tag::content[]
public final class RedirectResponse {

    @ResponseStatusCode
    final Integer status = 307;

    @Header(LOCATION)
    final String location;

    public RedirectResponse(final String location) {
        this.location = requireNonNull(location);
    }
}
// end::content[]
