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

package io.rxmicro.examples.validation.server.nested.model;

import io.rxmicro.examples.validation.server.nested.model.model.ArrayRequest;
import io.rxmicro.examples.validation.server.nested.model.model.ObjectRequest;
import io.rxmicro.rest.method.POST;

public class MicroService {

    @POST("/consume1")
    void consume1(final ObjectRequest request){

    }

    @POST("/consume2")
    void consume2(final ArrayRequest request){

    }
}
