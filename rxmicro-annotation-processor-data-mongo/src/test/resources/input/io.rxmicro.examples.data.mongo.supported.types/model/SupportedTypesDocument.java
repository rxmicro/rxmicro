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

package io.rxmicro.examples.data.mongo.supported.types.model;

import org.bson.types.Binary;
import org.bson.types.Code;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

public class SupportedTypesDocument {

    Status status;

    List<Status> statusList = List.of();

    Boolean aBoolean;

    List<Boolean> booleanList = List.of();

    Byte aByte;

    List<Byte> byteList = List.of();

    Short aShort;

    List<Short> shortList = List.of();

    Integer aInteger;

    List<Integer> integerList = List.of();

    Long aLong;

    List<Long> longList = List.of();

    Float aFloat;

    List<Float> floatList = List.of();

    Double aDouble;

    List<Double> doubleList = List.of();

    BigDecimal bigDecimal;

    List<BigDecimal> bigDecimalList = List.of();

    Character character;

    List<Character> characterList = List.of();

    String string;

    List<String> stringList = List.of();

    Pattern pattern;

    List<Pattern> patternList = List.of();

    Instant instant;

    List<Instant> instantList = List.of();

    LocalDate localDate;

    List<LocalDate> localDateList = List.of();

    LocalDateTime localDateTime;

    List<LocalDateTime> localDateTimeList = List.of();

    LocalTime localTime;

    List<LocalTime> localTimeList = List.of();

    UUID uuid;

    List<UUID> uuidList = List.of();

    Code code;

    List<Code> codeList = List.of();

    Binary binary;

    List<Binary> binaryList = List.of();

    public Status getStatus() {
        return status;
    }

    public Boolean getaBoolean() {
        return aBoolean;
    }

    public Byte getaByte() {
        return aByte;
    }

    public Short getaShort() {
        return aShort;
    }

    public Integer getaInteger() {
        return aInteger;
    }

    public Long getaLong() {
        return aLong;
    }

    public BigDecimal getBigDecimal() {
        return bigDecimal;
    }

    public SupportedTypesDocument setBigDecimal(final BigDecimal bigDecimal) {
        this.bigDecimal = bigDecimal;
        return this;
    }

    public Character getCharacter() {
        return character;
    }

    public String getString() {
        return string;
    }

    public Instant getInstant() {
        return instant;
    }

    public UUID getUuid() {
        return uuid;
    }
}
