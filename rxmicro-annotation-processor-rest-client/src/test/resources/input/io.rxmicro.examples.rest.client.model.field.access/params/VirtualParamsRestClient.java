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

package io.rxmicro.examples.rest.client.model.field.access.params;

import io.rxmicro.examples.rest.client.model.field.access.Status;
import io.rxmicro.examples.rest.client.model.field.access.params.direct.nested.Nested;
import io.rxmicro.rest.HeaderMappingStrategy;
import io.rxmicro.rest.RepeatQueryParameter;
import io.rxmicro.rest.client.RestClient;
import io.rxmicro.rest.method.GET;
import io.rxmicro.rest.method.PUT;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletionStage;

@RestClient
public interface VirtualParamsRestClient {

    @HeaderMappingStrategy
    @GET("/params/virtual")
    CompletionStage<Void> get(Boolean booleanParameter,
                              Byte byteParameter,
                              Short shortParameter,
                              Integer intParameter,
                              Long longParameter,
                              BigInteger bigIntParameter,
                              Float floatParameter,
                              Double doubleParameter,
                              BigDecimal decimalParameter,
                              Character charParameter,
                              String stringParameter,
                              Instant instantParameter,
                              Status enumParameter,
                              List<Boolean> booleanParameterList,
                              List<Byte> byteParameterList,
                              List<Short> shortParameterList,
                              List<Integer> intParameterList,
                              List<Long> longParameterList,
                              List<BigInteger> bigIntParameterList,
                              List<Float> floatParameterList,
                              List<Double> doubleParameterList,
                              List<BigDecimal> decimalParameterList,
                              List<Character> charParameterList,
                              List<String> stringParameterList,
                              List<Instant> instantParameterList,
                              List<Status> enumParameterList,
                              Set<Boolean> booleanParameterSet,
                              Set<Byte> byteParameterSet,
                              Set<Short> shortParameterSet,
                              Set<Integer> intParameterSet,
                              Set<Long> longParameterSet,
                              Set<BigInteger> bigIntParameterSet,
                              Set<Float> floatParameterSet,
                              Set<Double> doubleParameterSet,
                              Set<BigDecimal> decimalParameterSet,
                              Set<Character> charParameterSet,
                              Set<String> stringParameterSet,
                              Set<Instant> instantParameterSet,
                              Set<Status> enumParameterSet,
                              @RepeatQueryParameter List<Status> repeatingStatuses,
                              @RepeatQueryParameter Set<Status> repeatingStatusSet);

    @HeaderMappingStrategy
    @PUT("/params/virtual")
    CompletionStage<Void> put(Boolean booleanParameter,
                              Byte byteParameter,
                              Short shortParameter,
                              Integer intParameter,
                              Long longParameter,
                              BigInteger bigIntParameter,
                              Float floatParameter,
                              Double doubleParameter,
                              BigDecimal decimalParameter,
                              Character charParameter,
                              String stringParameter,
                              Instant instantParameter,
                              Status enumParameter,
                              List<Boolean> booleanParameterList,
                              List<Byte> byteParameterList,
                              List<Short> shortParameterList,
                              List<Integer> intParameterList,
                              List<Long> longParameterList,
                              List<BigInteger> bigIntParameterList,
                              List<Float> floatParameterList,
                              List<Double> doubleParameterList,
                              List<BigDecimal> decimalParameterList,
                              List<Character> charParameterList,
                              List<String> stringParameterList,
                              List<Instant> instantParameterList,
                              List<Status> enumParameterList,
                              Set<Boolean> booleanParameterSet,
                              Set<Byte> byteParameterSet,
                              Set<Short> shortParameterSet,
                              Set<Integer> intParameterSet,
                              Set<Long> longParameterSet,
                              Set<BigInteger> bigIntParameterSet,
                              Set<Float> floatParameterSet,
                              Set<Double> doubleParameterSet,
                              Set<BigDecimal> decimalParameterSet,
                              Set<Character> charParameterSet,
                              Set<String> stringParameterSet,
                              Set<Instant> instantParameterSet,
                              Set<Status> enumParameterSet,
                              Map<String, Boolean> booleanParameterMap,
                              Map<String, Byte> byteParameterMap,
                              Map<String, Short> shortParameterMap,
                              Map<String, Integer> integerParameterMap,
                              Map<String, Long> longParameterMap,
                              Map<String, BigInteger> bigIntegerParameterMap,
                              Map<String, Float> floatParameterMap,
                              Map<String, Double> doubleParameterMap,
                              Map<String, BigDecimal> bigDecimalParameterMap,
                              Map<String, Character> characterParameterMap,
                              Map<String, String> stringParameterMap,
                              Map<String, Instant> instantParameterMap,
                              Map<String, Status> enumParameterMap,
                              Nested nested,
                              List<Nested> nestedList,
                              Set<Nested> nestedSet,
                              Map<String, Nested> nestedParameterMap);
}
