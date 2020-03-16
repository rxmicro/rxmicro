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

package io.rxmicro.examples.rest.controller.path.variables;

import io.rxmicro.examples.rest.controller.path.variables.model.Request;
import io.rxmicro.rest.PathVariable;
import io.rxmicro.rest.method.GET;

import static io.rxmicro.common.util.Formats.format;

// tag::content[]
final class MicroService {

    // <1>
    @GET("/1/${category}/${class}/${subType}")
    @GET("/1/${category}/${class}_${subType}")
    @GET("/1/${category}-${class}-${subType}")
    @GET("/1-${category}-${class}-${subType}")
    void consume(final Request request) {
        System.out.println(format(
                "?-?-?",
                request.getCategory(), request.getType(), request.getSubType()
        ));
    }

    // <1>
    @GET("/2/${category}/${class}/${subType}")
    @GET("/2/${category}/${class}_${subType}")
    @GET("/2/${category}-${class}-${subType}")
    @GET("/2-${category}-${class}-${subType}")
    void consume(final @PathVariable String category,       // <2>
                 final @PathVariable("class") String type,  // <3>
                 final @PathVariable String subType) {
        System.out.println(format(
                "?-?-?",
                category, type, subType
        ));
    }
}
// end::content[]
