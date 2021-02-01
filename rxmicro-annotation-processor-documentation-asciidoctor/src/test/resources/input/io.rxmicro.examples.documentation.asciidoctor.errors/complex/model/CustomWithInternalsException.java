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

package io.rxmicro.examples.documentation.asciidoctor.errors.complex.model;

import io.rxmicro.documentation.Description;
import io.rxmicro.http.HttpHeaders;
import io.rxmicro.http.HttpVersion;
import io.rxmicro.http.error.HttpErrorException;
import io.rxmicro.rest.ResponseBody;

@Description("CustomWithInternalsException description!")
public final class CustomWithInternalsException extends HttpErrorException {

    public static final int STATUS_CODE = 499;

    final HttpVersion version;

    final HttpHeaders headers;

    @ResponseBody
    final byte[] body;

    public CustomWithInternalsException(final HttpVersion version,
                                        final HttpHeaders headers,
                                        final byte[] body) {
        super(STATUS_CODE);
        this.version = version;
        this.headers = headers;
        this.body = body;
    }
}

