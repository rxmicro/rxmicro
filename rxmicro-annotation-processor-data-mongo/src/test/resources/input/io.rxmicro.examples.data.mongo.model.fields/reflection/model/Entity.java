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

package io.rxmicro.examples.data.mongo.model.fields.reflection.model;

import io.rxmicro.data.mongo.DocumentId;
import io.rxmicro.examples.data.mongo.model.fields.Status;
import io.rxmicro.examples.data.mongo.model.fields.reflection.model.nested.Nested;
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
    private ObjectId id;

    private Status status;

    private List<Status> statusList;

    private Boolean aBoolean;

    private List<Boolean> booleanList;

    private Byte aByte;

    private List<Byte> byteList;

    private Short aShort;

    private List<Short> shortList;

    private Integer aInteger;

    private List<Integer> integerList;

    private Long aLong;

    private List<Long> longList;

    private Float aFloat;

    private List<Float> floatList;

    private Double aDouble;

    private List<Double> doubleList;

    private BigDecimal bigDecimal;

    private List<BigDecimal> bigDecimalList;

    private Character character;

    private List<Character> characterList;

    private String string;

    private List<String> stringList;

    private Pattern pattern;

    private List<Pattern> patternList;

    private Instant instant;

    private List<Instant> instantList;

    private LocalDate localDate;

    private List<LocalDate> localDateList;

    private LocalDateTime localDateTime;

    private List<LocalDateTime> localDateTimeList;

    private LocalTime localTime;

    private List<LocalTime> localTimeList;

    private UUID uuid;

    private List<UUID> uuidList;

    private Code code;

    private List<Code> codeList;

    private Binary binary;

    private List<Binary> binaryList;

    private Nested nested;

    private List<Nested> nestedList;
}
