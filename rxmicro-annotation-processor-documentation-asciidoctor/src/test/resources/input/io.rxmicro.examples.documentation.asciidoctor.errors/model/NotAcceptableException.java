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

package io.rxmicro.examples.documentation.asciidoctor.errors.model;

import io.rxmicro.documentation.Description;
import io.rxmicro.documentation.Example;
import io.rxmicro.http.error.HttpErrorException;

// tag::content[]
@Description("This error response indicates that the API " +
        "is not able to generate any of the client's preferred media types, " +
        "as indicated by the Accept request header.") // <2>
public final class NotAcceptableException extends HttpErrorException {

    public static final int STATUS_CODE = 406; // <1>

    public NotAcceptableException(@Example("Not-Acceptable") final String message) { // <3>
        super(STATUS_CODE, message);
    }
}
// end::content[]
