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

import java.util.concurrent.CompletionStage;

/**
 * @author nedis
 * @since 0.7.2
 */
@SuppressWarnings("rawtypes")
public final class Method_cant_return_CompletionStage_without_parametrization<T> {

    @GET("/1")
    CompletionStage test1(){
        return null;
    }

    @GET("/2")
    CompletionStage<?> test2(){
        return null;
    }

    @GET("/3")
    <E> CompletionStage<E> test3(){
        return null;
    }

    @GET("/4")
    CompletionStage<T> test4(){
        return null;
    }
}
// Line: 31
// Error: Invalid return type: Expected generic type with 1 parameter(s): java.util.concurrent.CompletionStage

// Line: 36
// Error: Invalid return type: Wildcard is not allowed: java.util.concurrent.CompletionStage<?>

// Line: 41
// Error: Invalid return type: Type variable is not allowed: java.util.concurrent.CompletionStage<E>

// Line: 46
// Error: Invalid return type: Type variable is not allowed: java.util.concurrent.CompletionStage<T>
