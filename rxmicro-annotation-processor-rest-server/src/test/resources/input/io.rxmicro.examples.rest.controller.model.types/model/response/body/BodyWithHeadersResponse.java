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

package io.rxmicro.examples.rest.controller.model.types.model.response.body;

import io.rxmicro.examples.rest.controller.model.types.model.Status;
import io.rxmicro.examples.rest.controller.model.types.model.nested.Nested;
import io.rxmicro.rest.Header;
import io.rxmicro.rest.RepeatHeader;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BodyWithHeadersResponse {

    Boolean booleanParameter;

    Byte byteParameter;

    Short shortParameter;

    Integer intParameter;

    Long longParameter;

    BigInteger bigIntParameter;

    Float floatParameter;

    Double doubleParameter;

    BigDecimal decimalParameter;

    Character charParameter;

    String stringParameter;

    Instant instantParameter;

    Status enumParameter;

    List<Boolean> booleanParameterList;

    List<Byte> byteParameterList;

    List<Short> shortParameterList;

    List<Integer> intParameterList;

    List<Long> longParameterList;

    List<BigInteger> bigIntParameterList;

    List<Float> floatParameterList;

    List<Double> doubleParameterList;

    List<BigDecimal> decimalParameterList;

    List<Character> charParameterList;

    List<String> stringParameterList;

    List<Instant> instantParameterList;

    List<Status> enumParameterList;

    Set<Boolean> booleanParameterSet;

    Set<Byte> byteParameterSet;

    Set<Short> shortParameterSet;

    Set<Integer> intParameterSet;

    Set<Long> longParameterSet;

    Set<BigInteger> bigIntParameterSet;

    Set<Float> floatParameterSet;

    Set<Double> doubleParameterSet;

    Set<BigDecimal> decimalParameterSet;

    Set<Character> charParameterSet;

    Set<String> stringParameterSet;

    Set<Instant> instantParameterSet;

    Set<Status> enumParameterSet;

    Map<String, Boolean> booleanParameterMap;

    Map<String, Byte> byteParameterMap;

    Map<String, Short> shortParameterMap;

    Map<String, Integer> integerParameterMap;

    Map<String, Long> longParameterMap;

    Map<String, BigInteger> bigIntegerParameterMap;

    Map<String, Float> floatParameterMap;

    Map<String, Double> doubleParameterMap;

    Map<String, BigDecimal> bigDecimalParameterMap;

    Map<String, Character> characterParameterMap;

    Map<String, String> stringParameterMap;

    Map<String, Instant> instantParameterMap;

    Map<String, Status> enumParameterMap;

    Nested nested;

    List<Nested> nestedList;

    Set<Nested> nestedSet;

    Map<String, Nested> nestedParameterMap;

    // -------------------------------------------------------------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------------------------------------------------------------

    @Header
    Boolean booleanHeader;

    @Header
    Byte byteHeader;

    @Header
    Short shortHeader;

    @Header
    Integer intHeader;

    @Header
    Long longHeader;

    @Header
    BigInteger bigIntHeader;

    @Header
    Float floatHeader;

    @Header
    Double doubleHeader;

    @Header
    BigDecimal decimalHeader;

    @Header
    Character charHeader;

    @Header
    String stringHeader;

    @Header
    Instant instantHeader;

    @Header
    Status enumHeader;

    @Header
    List<Boolean> booleanHeaderList;

    @Header
    List<Byte> byteHeaderList;

    @Header
    List<Short> shortHeaderList;

    @Header
    List<Integer> intHeaderList;

    @Header
    List<Long> longHeaderList;

    @Header
    List<BigInteger> bigIntHeaderList;

    @Header
    List<Float> floatHeaderList;

    @Header
    List<Double> doubleHeaderList;

    @Header
    List<BigDecimal> decimalHeaderList;

    @Header
    List<Character> charHeaderList;

    @Header
    List<String> stringHeaderList;

    @Header
    List<Instant> instantHeaderList;

    @Header
    List<Status> enumHeaderList;

    @Header
    Set<Boolean> booleanHeaderSet;

    @Header
    Set<Byte> byteHeaderSet;

    @Header
    Set<Short> shortHeaderSet;

    @Header
    Set<Integer> intHeaderSet;

    @Header
    Set<Long> longHeaderSet;

    @Header
    Set<BigInteger> bigIntHeaderSet;

    @Header
    Set<Float> floatHeaderSet;

    @Header
    Set<Double> doubleHeaderSet;

    @Header
    Set<BigDecimal> decimalHeaderSet;

    @Header
    Set<Character> charHeaderSet;

    @Header
    Set<String> stringHeaderSet;

    @Header
    Set<Instant> instantHeaderSet;

    @Header
    Set<Status> enumHeaderSet;

    @Header
    @RepeatHeader
    List<Status> repeatingEnumHeaderList;

    @Header
    @RepeatHeader
    Set<Status> repeatingEnumHeaderSet;

}
