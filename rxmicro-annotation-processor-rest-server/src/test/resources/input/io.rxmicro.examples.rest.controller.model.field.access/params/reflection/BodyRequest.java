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

package io.rxmicro.examples.rest.controller.model.field.access.params.reflection;

import io.rxmicro.examples.rest.controller.model.field.access.Status;
import io.rxmicro.examples.rest.controller.model.field.access.params.reflection.nested.Nested;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BodyRequest {

    private Boolean booleanParameter;

    private Byte byteParameter;

    private Short shortParameter;

    private Integer intParameter;

    private Long longParameter;

    private BigInteger bigIntParameter;

    private Float floatParameter;

    private Double doubleParameter;

    private BigDecimal decimalParameter;

    private Character charParameter;

    private String stringParameter;

    private Instant instantParameter;

    private Status enumParameter;

    private List<Boolean> booleanParameterList;

    private List<Byte> byteParameterList;

    private List<Short> shortParameterList;

    private List<Integer> intParameterList;

    private  List<Long> longParameterList;

    private List<BigInteger> bigIntParameterList;

    private List<Float> floatParameterList;

    private List<Double> doubleParameterList;

    private List<BigDecimal> decimalParameterList;

    private List<Character> charParameterList;

    private List<String> stringParameterList;

    private List<Instant> instantParameterList;

    private List<Status> enumParameterList;

    private Set<Boolean> booleanParameterSet;

    private Set<Byte> byteParameterSet;

    private Set<Short> shortParameterSet;

    private Set<Integer> intParameterSet;

    private Set<Long> longParameterSet;

    private Set<BigInteger> bigIntParameterSet;

    private Set<Float> floatParameterSet;

    private Set<Double> doubleParameterSet;

    private Set<BigDecimal> decimalParameterSet;

    private Set<Character> charParameterSet;

    private Set<String> stringParameterSet;

    private Set<Instant> instantParameterSet;

    private Set<Status> enumParameterSet;

    private Map<String, Boolean> booleanParameterMap;

    private Map<String, Byte> byteParameterMap;

    private Map<String, Short> shortParameterMap;

    private Map<String, Integer> integerParameterMap;

    private Map<String, Long> longParameterMap;

    private Map<String, BigInteger> bigIntegerParameterMap;

    private Map<String, Float> floatParameterMap;

    private Map<String, Double> doubleParameterMap;

    private Map<String, BigDecimal> bigDecimalParameterMap;

    private Map<String, Character> characterParameterMap;

    private Map<String, String> stringParameterMap;

    private Map<String, Instant> instantParameterMap;

    private Map<String, Status> enumParameterMap;

    private Nested nested;

    private List<Nested> nestedList;

    private Set<Nested> nestedSet;

    private Map<String, Nested> nestedParameterMap;
}
