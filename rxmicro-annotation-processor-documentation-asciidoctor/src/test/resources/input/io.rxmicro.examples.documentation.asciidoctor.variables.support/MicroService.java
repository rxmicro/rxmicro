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

package io.rxmicro.examples.documentation.asciidoctor.variables.support;

import io.rxmicro.documentation.Description;
import io.rxmicro.documentation.Example;
import io.rxmicro.documentation.IncludeDescription;
import io.rxmicro.documentation.SimpleErrorResponse;
import io.rxmicro.rest.method.PUT;

public final class MicroService {

    @PUT("/")
    @SimpleErrorResponse(
            status = 485,
            description = "${var}",
            paramNames = "${var}",
            paramValueExamples = "${var}",
            paramDescriptions = "${var}",
            headerNames = "${var}",
            headerValueExamples = "${var}",
            headerDescriptions = "${var}",
            variables = {
                    "${var}", "test"
            }
    )
    @IncludeDescription(
            resource = "/${var}",
            variables = {
                    "${var}", "absolute/path/to/included/description"
            }
    )
    void consume(final
                 @Example(
                         value = "${var}",
                         variables = {
                                 "${var}", "test"
                         })
                 @Description(
                         value = "${var}",
                         variables = {
                                 "${var}", "test"
                         })
                         String data) {
        // do something
    }
}

