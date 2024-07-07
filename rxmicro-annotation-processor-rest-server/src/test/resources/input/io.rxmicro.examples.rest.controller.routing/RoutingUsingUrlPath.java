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

package io.rxmicro.examples.rest.controller.routing;

import io.rxmicro.rest.PathVariable;
import io.rxmicro.rest.method.GET;

// tag::content[]
final class RoutingUsingUrlPath {

    @GET("/urlPath1")
    void get1() {
        System.out.println("/urlPath1");
    }

    @GET("/urlPath2")
    void get2() {
        System.out.println("/urlPath2");
    }

    @GET("/${type}")
    void get3(final @PathVariable String type) { // <1>
        System.out.println("/${type}: " + type);
    }
}
// end::content[]
