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

package error.method.return_type;

import io.rxmicro.rest.method.GET;

/**
 * @author nedis
 * @since 0.7.2
 */
public final class Method_must_return_void_or_reactive_type {

    @GET("/")
    String test(){
        return "";
    }
}
// Line: 28
// Error: Invalid return type. Expected one of the following: [java.util.concurrent.CompletionStage,
//          java.util.concurrent.CompletableFuture, reactor.core.publisher.Mono, io.reactivex.rxjava3.core.Maybe,
//          io.reactivex.rxjava3.core.Single, io.reactivex.rxjava3.core.Completable]
