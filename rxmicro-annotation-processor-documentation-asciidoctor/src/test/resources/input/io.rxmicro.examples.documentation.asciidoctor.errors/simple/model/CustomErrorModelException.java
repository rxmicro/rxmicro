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

package io.rxmicro.examples.documentation.asciidoctor.errors.simple.model;

import io.rxmicro.documentation.Description;
import io.rxmicro.http.error.HttpErrorException;
import io.rxmicro.rest.Header;
import io.rxmicro.rest.HeaderMappingStrategy;
import io.rxmicro.rest.ParameterMappingStrategy;
import io.rxmicro.validation.constraint.Future;
import io.rxmicro.validation.constraint.Nullable;

import java.time.Instant;
import java.util.Map;

import static io.rxmicro.common.util.Requires.require;

// tag::content[]
// <1>
@Description("Custom description")
@HeaderMappingStrategy
@ParameterMappingStrategy
public class CustomErrorModelException extends HttpErrorException {

    public static final int STATUS_CODE = 410; // <2>

    // <3>
    @Header
    @Description("Debug header example description")
    @Nullable
    final Boolean debug;

    // <4>
    @Description("The client must repeat some action after this instant")
    @Future
    final Instant nextAttemptAfter;

    // <5>
    public CustomErrorModelException(final Instant nextAttemptAfter,
                                     final Boolean debug) {
        super(STATUS_CODE);
        this.nextAttemptAfter = nextAttemptAfter;
        this.debug = debug;
    }
}
// end::content[]
