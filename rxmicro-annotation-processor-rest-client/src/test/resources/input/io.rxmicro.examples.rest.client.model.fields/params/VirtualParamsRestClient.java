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

package io.rxmicro.examples.rest.client.model.fields.params;

import io.rxmicro.examples.rest.client.model.fields.Status;
import io.rxmicro.examples.rest.client.model.fields.params.direct.nested.Nested;
import io.rxmicro.rest.HeaderMappingStrategy;
import io.rxmicro.rest.RepeatQueryParameter;
import io.rxmicro.rest.client.RestClient;
import io.rxmicro.rest.method.PUT;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletionStage;

@RestClient
public interface VirtualParamsRestClient {

    @HeaderMappingStrategy
    @PUT("/params/virtual")
    CompletionStage<Void> put(final Boolean booleanParameter,
                              final Byte byteParameter,
                              final Short shortParameter,
                              final Integer intParameter,
                              final Long longParameter,
                              final BigInteger bigIntParameter,
                              final Float floatParameter,
                              final Double doubleParameter,
                              final BigDecimal decimalParameter,
                              final Character charParameter,
                              final String stringParameter,
                              final Instant instantParameter,
                              final Status status,
                              final Nested nested,
                              final List<Boolean> booleanParameters,
                              final List<Byte> byteParameters,
                              final List<Short> shortParameters,
                              final List<Integer> intParameters,
                              final List<Long> longParameters,
                              final List<BigInteger> bigIntParameters,
                              final List<Float> floatParameters,
                              final List<Double> doubleParameters,
                              final List<BigDecimal> decimalParameters,
                              final List<Character> charParameters,
                              final List<String> stringParameters,
                              final List<Instant> instantParameters,
                              final List<Status> statuses,
                              final List<Nested> nestedList,
                              final @RepeatQueryParameter List<Status> repeatingStatuses,
                              final Set<Boolean> booleanParameterSet,
                              final Set<Byte> byteParameterSet,
                              final Set<Short> shortParameterSet,
                              final Set<Integer> intParameterSet,
                              final Set<Long> longParameterSet,
                              final Set<BigInteger> bigIntParameterSet,
                              final Set<Float> floatParameterSet,
                              final Set<Double> doubleParameterSet,
                              final Set<BigDecimal> decimalParameterSet,
                              final Set<Character> charParameterSet,
                              final Set<String> stringParameterSet,
                              final Set<Instant> instantParameterSet,
                              final Set<Status> statusSet,
                              final Set<Nested> nestedSet,
                              final @RepeatQueryParameter Set<Status> repeatingStatusSet);
}
