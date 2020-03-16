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

package io.rxmicro.examples.rest.controller.model.fields.headers.direct;

import io.rxmicro.examples.rest.controller.model.fields.Status;
import io.rxmicro.rest.Header;
import io.rxmicro.rest.HeaderMappingStrategy;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.List;

@HeaderMappingStrategy
abstract class Abstract {

    @Header
    Boolean booleanParameter;

    @Header
    Byte byteParameter;

    @Header
    Short shortParameter;

    @Header
    Integer intParameter;

    @Header
    Long longParameter;

    @Header
    BigInteger bigIntParameter;

    @Header
    Float floatParameter;

    @Header
    Double doubleParameter;

    @Header
    BigDecimal decimalParameter;

    @Header
    Character charParameter;

    @Header
    String stringParameter;

    @Header
    Instant instantParameter;

    @Header
    Status status;

    @Header
    List<Boolean> booleanParameters;

    @Header
    List<Byte> byteParameters;

    @Header
    List<Short> shortParameters;

    @Header
    List<Integer> intParameters;

    @Header
    List<Long> longParameters;

    @Header
    List<BigInteger> bigIntParameters;

    @Header
    List<Float> floatParameters;

    @Header
    List<Double> doubleParameters;

    @Header
    List<BigDecimal> decimalParameters;

    @Header
    List<Character> charParameters;

    @Header
    List<String> stringParameters;

    @Header
    List<Instant> instantParameters;

    @Header
    List<Status> statuses;

}
