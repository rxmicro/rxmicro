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

package io.rxmicro.examples.data.mongo.model.fields.direct.model;

import io.rxmicro.data.mongo.DocumentId;
import io.rxmicro.examples.data.mongo.model.fields.Status;
import io.rxmicro.examples.data.mongo.model.fields.direct.model.nested.Nested;
import org.bson.types.Binary;
import org.bson.types.Code;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

public final class Entity {

    @DocumentId
    ObjectId id;

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

    Pattern pattern;

    List<Pattern> patternList;

    Instant instant;

    List<Instant> instantList;

    LocalDate localDate;

    List<LocalDate> localDateList;

    LocalDateTime localDateTime;

    List<LocalDateTime> localDateTimeList;

    LocalTime localTime;

    List<LocalTime> localTimeList;

    UUID uuid;

    List<UUID> uuidList;

    Code code;

    List<Code> codeList;

    Binary binary;

    List<Binary> binaryList;

    Nested nested;

    List<Nested> nestedList;
}
