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

package io.rxmicro.examples.rest.controller.model.fields.headers;

import io.rxmicro.examples.rest.controller.model.fields.headers.reflection.Request;
import io.rxmicro.examples.rest.controller.model.fields.headers.reflection.Response;
import io.rxmicro.rest.method.PUT;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

final class ReflectionHeadersMicroService {

    @PUT("/headers/reflection")
    CompletionStage<Optional<Response>> put(final Request request) {
        return CompletableFuture.completedStage(Optional.empty());
    }

}
