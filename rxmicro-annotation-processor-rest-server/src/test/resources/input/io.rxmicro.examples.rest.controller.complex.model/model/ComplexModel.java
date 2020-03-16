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

package io.rxmicro.examples.rest.controller.complex.model.model;

import io.rxmicro.examples.rest.controller.complex.model.model.nested.NestedModel;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.List;

public final class ComplexModel {

    Status status;

    List<Status> statusList;

    Boolean aBoolean;

    List<Boolean> booleanList;

    Byte aByte;

    List<Byte> byteList;

    Short aShort;

    List<Short> shortList;

    Integer aInteger;

    List<Integer> integerList;

    Long aLong;

    List<Long> longList;

    BigInteger bigInteger;

    List<BigInteger> bigIntegerList;

    Float aFloat;

    List<Float> floatList;

    Double aDouble;

    List<Double> doubleList;

    BigDecimal bigDecimal;

    List<BigDecimal> bigDecimalList;

    Character character;

    List<Character> characterList;

    String string;

    List<String> stringList;

    Instant instant;

    List<Instant> instantList;

    NestedModel nested;

    List<NestedModel> nestedList;
}
