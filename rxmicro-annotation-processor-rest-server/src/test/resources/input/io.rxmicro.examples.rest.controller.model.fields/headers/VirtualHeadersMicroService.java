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

import io.rxmicro.examples.rest.controller.model.fields.Status;
import io.rxmicro.rest.Header;
import io.rxmicro.rest.HeaderMappingStrategy;
import io.rxmicro.rest.method.PUT;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.List;
import java.util.Set;

@SuppressWarnings("EmptyMethod")
final class VirtualHeadersMicroService {

    @HeaderMappingStrategy
    @PUT("/headers/virtual")
    void put(final @Header Boolean booleanParameter,
             final @Header Byte byteParameter,
             final @Header Short shortParameter,
             final @Header Integer intParameter,
             final @Header Long longParameter,
             final @Header BigInteger bigIntParameter,
             final @Header Float floatParameter,
             final @Header Double doubleParameter,
             final @Header BigDecimal decimalParameter,
             final @Header Character charParameter,
             final @Header String stringParameter,
             final @Header Instant instantParameter,
             final @Header Status status,
             final @Header List<Boolean> booleanParameters,
             final @Header List<Byte> byteParameters,
             final @Header List<Short> shortParameters,
             final @Header List<Integer> intParameters,
             final @Header List<Long> longParameters,
             final @Header List<BigInteger> bigIntParameters,
             final @Header List<Float> floatParameters,
             final @Header List<Double> doubleParameters,
             final @Header List<BigDecimal> decimalParameters,
             final @Header List<Character> charParameters,
             final @Header List<String> stringParameters,
             final @Header List<Instant> instantParameters,
             final @Header List<Status> statuses,
             final @Header Set<Boolean> booleanParameterSet,
             final @Header Set<Byte> byteParameterSet,
             final @Header Set<Short> shortParameterSet,
             final @Header Set<Integer> intParameterSet,
             final @Header Set<Long> longParameterSet,
             final @Header Set<BigInteger> bigIntParameterSet,
             final @Header Set<Float> floatParameterSet,
             final @Header Set<Double> doubleParameterSet,
             final @Header Set<BigDecimal> decimalParameterSet,
             final @Header Set<Character> charParameterSet,
             final @Header Set<String> stringParameterSet,
             final @Header Set<Instant> instantParameterSet,
             final @Header Set<Status> statusSet) {
        // do something
    }

}
