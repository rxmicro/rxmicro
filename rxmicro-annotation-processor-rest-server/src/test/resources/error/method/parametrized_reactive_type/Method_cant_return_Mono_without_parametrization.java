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

package error.method.parametrized_reactive_type;

import io.rxmicro.rest.method.GET;
import reactor.core.publisher.Mono;

/**
 * @author nedis
 * @since 0.7.2
 */
@SuppressWarnings("rawtypes")
public final class Method_cant_return_Mono_without_parametrization<T> {

    @GET("/1")
    Mono test1() {
        return null;
    }

    @GET("/2")
    Mono<?> test2() {
        return null;
    }

    @GET("/3")
    <E> Mono<E> test3() {
        return null;
    }

    @GET("/4")
    Mono<T> test4() {
        return null;
    }
}
// Line: 30
// Error: Invalid return type: Expected generic type with 1 parameter(s): reactor.core.publisher.Mono

// Line: 35
// Error: Invalid return type: Wildcard is not allowed: reactor.core.publisher.Mono<?>

// Line: 40
// Error: Invalid return type: Type variable is not allowed: reactor.core.publisher.Mono<E>

// Line: 45
// Error: Invalid return type: Type variable is not allowed: reactor.core.publisher.Mono<T>
