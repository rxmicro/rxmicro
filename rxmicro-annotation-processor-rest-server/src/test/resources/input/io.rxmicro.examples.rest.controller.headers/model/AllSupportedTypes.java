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

package io.rxmicro.examples.rest.controller.headers.model;

import io.rxmicro.rest.Header;
import io.rxmicro.rest.HeaderMappingStrategy;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.List;

@HeaderMappingStrategy
public final class AllSupportedTypes {

    @Header
    Status status;

    @Header
    List<Status> statusList;

    @Header
    Boolean aBoolean;

    @Header
    List<Boolean> booleanList;

    @Header
    Byte aByte;

    @Header
    List<Byte> byteList;

    @Header
    Short aShort;

    @Header
    List<Short> shortList;

    @Header
    Integer aInteger;

    @Header
    List<Integer> integerList;

    @Header
    Long aLong;

    @Header
    List<Long> longList;

    @Header
    BigInteger bigInteger;

    @Header
    List<BigInteger> bigIntegerList;

    @Header
    Float aFloat;

    @Header
    List<Float> floatList;

    @Header
    Double aDouble;

    @Header
    List<Double> doubleList;

    @Header
    BigDecimal bigDecimal;

    @Header
    List<BigDecimal> bigDecimalList;

    @Header
    Character character;

    @Header
    List<Character> characterList;

    @Header
    String string;

    @Header
    List<String> stringList;

    @Header
    Instant instant;

    @Header
    List<Instant> instantList;
}
