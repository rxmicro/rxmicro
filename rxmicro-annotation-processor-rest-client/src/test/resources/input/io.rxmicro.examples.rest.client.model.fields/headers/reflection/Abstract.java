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

package io.rxmicro.examples.rest.client.model.fields.headers.reflection;

import io.rxmicro.examples.rest.client.model.fields.Status;
import io.rxmicro.rest.Header;
import io.rxmicro.rest.HeaderMappingStrategy;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.List;

@SuppressWarnings("unused")
@HeaderMappingStrategy
abstract class Abstract {

    @Header
    private Boolean booleanParameter;

    @Header
    private Byte byteParameter;

    @Header
    private Short shortParameter;

    @Header
    private Integer intParameter;

    @Header
    private Long longParameter;

    @Header
    private BigInteger bigIntParameter;

    @Header
    private Float floatParameter;

    @Header
    private Double doubleParameter;

    @Header
    private BigDecimal decimalParameter;

    @Header
    private Character charParameter;

    @Header
    private String stringParameter;

    @Header
    private Instant instantParameter;

    @Header
    private Status status;

    @Header
    private List<Boolean> booleanParameters;

    @Header
    private List<Byte> byteParameters;

    @Header
    private List<Short> shortParameters;

    @Header
    private List<Integer> intParameters;

    @Header
    private List<Long> longParameters;

    @Header
    private List<BigInteger> bigIntParameters;

    @Header
    private List<Float> floatParameters;

    @Header
    private List<Double> doubleParameters;

    @Header
    private List<BigDecimal> decimalParameters;

    @Header
    private List<Character> charParameters;

    @Header
    private List<String> stringParameters;

    @Header
    private List<Instant> instantParameters;

    @Header
    private List<Status> statuses;
}
