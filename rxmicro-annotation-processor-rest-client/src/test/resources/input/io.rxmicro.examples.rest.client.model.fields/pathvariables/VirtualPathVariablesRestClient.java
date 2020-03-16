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

package io.rxmicro.examples.rest.client.model.fields.pathvariables;

import io.rxmicro.examples.rest.client.model.fields.Status;
import io.rxmicro.rest.PathVariable;
import io.rxmicro.rest.client.RestClient;
import io.rxmicro.rest.method.PUT;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.concurrent.CompletionStage;

@RestClient
public interface VirtualPathVariablesRestClient {

    @PUT("/path-variables/virtual/${a}/${b}/${c}/${d}/${e}/${f}/${g}/${j}/${h}/${i}/${j}/${k}/${l}/${m}")
    CompletionStage<Void> put(final @PathVariable("a") Boolean booleanParameter,
                              final @PathVariable("b") Byte byteParameter,
                              final @PathVariable("c") Short shortParameter,
                              final @PathVariable("d") Integer intParameter,
                              final @PathVariable("e") Long longParameter,
                              final @PathVariable("f") BigInteger bigIntParameter,
                              final @PathVariable("g") Float floatParameter,
                              final @PathVariable("h") Double doubleParameter,
                              final @PathVariable("i") BigDecimal decimalParameter,
                              final @PathVariable("j") Character charParameter,
                              final @PathVariable("k") String stringParameter,
                              final @PathVariable("l") Instant instantParameter,
                              final @PathVariable("m") Status status);
}
