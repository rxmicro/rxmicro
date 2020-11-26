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

package io.rxmicro.examples.documentation.asciidoctor.errors.model;

import io.rxmicro.documentation.Description;
import io.rxmicro.documentation.Example;
import io.rxmicro.http.error.HttpErrorException;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

// tag::content[]
// <1>
@Description("Custom description")
public class CustomErrorModelException extends HttpErrorException {

    public static final int STATUS_CODE = 410; // <2>

    // <3>
    @Example("2222-01-01T10:00:00Z")
    @Description("The client must repeat some action after this instant")
    private final Instant nextAttemptAfter;

    // <4>
    public CustomErrorModelException(final Instant nextAttemptAfter) {
        super(STATUS_CODE);
        this.nextAttemptAfter = nextAttemptAfter;
    }

    // <5>
    @Override
    public Optional<Map<String, Object>> getResponseData() {
        // <6>
        return Optional.of(Map.of("nextAttemptAfter", nextAttemptAfter));
    }
}
// end::content[]
