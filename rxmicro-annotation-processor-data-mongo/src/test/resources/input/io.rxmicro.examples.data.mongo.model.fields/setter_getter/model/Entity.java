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

package io.rxmicro.examples.data.mongo.model.fields.setter_getter.model;

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

    private io.rxmicro.examples.data.mongo.model.fields.reflection.model.nested.Nested nested;

    private List<Nested> nestedList;

    public ObjectId getId() {
        return id;
    }

    public void setId(final ObjectId id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(final Status status) {
        this.status = status;
    }

    public List<Status> getStatusList() {
        return statusList;
    }

    public void setStatusList(final List<Status> statusList) {
        this.statusList = statusList;
    }

    public Boolean getaBoolean() {
        return aBoolean;
    }

    public void setaBoolean(final Boolean aBoolean) {
        this.aBoolean = aBoolean;
    }

    public List<Boolean> getBooleanList() {
        return booleanList;
    }

    public void setBooleanList(final List<Boolean> booleanList) {
        this.booleanList = booleanList;
    }

    public Byte getaByte() {
        return aByte;
    }

    public void setaByte(final Byte aByte) {
        this.aByte = aByte;
    }

    public List<Byte> getByteList() {
        return byteList;
    }

    public void setByteList(final List<Byte> byteList) {
        this.byteList = byteList;
    }

    public Short getaShort() {
        return aShort;
    }

    public void setaShort(final Short aShort) {
        this.aShort = aShort;
    }

    public List<Short> getShortList() {
        return shortList;
    }

    public void setShortList(final List<Short> shortList) {
        this.shortList = shortList;
    }

    public Integer getaInteger() {
        return aInteger;
    }

    public void setaInteger(final Integer aInteger) {
        this.aInteger = aInteger;
    }

    public List<Integer> getIntegerList() {
        return integerList;
    }

    public void setIntegerList(final List<Integer> integerList) {
        this.integerList = integerList;
    }

    public Long getaLong() {
        return aLong;
    }

    public void setaLong(final Long aLong) {
        this.aLong = aLong;
    }

    public List<Long> getLongList() {
        return longList;
    }

    public void setLongList(final List<Long> longList) {
        this.longList = longList;
    }

    public Float getaFloat() {
        return aFloat;
    }

    public void setaFloat(final Float aFloat) {
        this.aFloat = aFloat;
    }

    public List<Float> getFloatList() {
        return floatList;
    }

    public void setFloatList(final List<Float> floatList) {
        this.floatList = floatList;
    }

    public Double getaDouble() {
        return aDouble;
    }

    public void setaDouble(final Double aDouble) {
        this.aDouble = aDouble;
    }

    public List<Double> getDoubleList() {
        return doubleList;
    }

    public void setDoubleList(final List<Double> doubleList) {
        this.doubleList = doubleList;
    }

    public BigDecimal getBigDecimal() {
        return bigDecimal;
    }

    public void setBigDecimal(final BigDecimal bigDecimal) {
        this.bigDecimal = bigDecimal;
    }

    public List<BigDecimal> getBigDecimalList() {
        return bigDecimalList;
    }

    public void setBigDecimalList(final List<BigDecimal> bigDecimalList) {
        this.bigDecimalList = bigDecimalList;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(final Character character) {
        this.character = character;
    }

    public List<Character> getCharacterList() {
        return characterList;
    }

    public void setCharacterList(final List<Character> characterList) {
        this.characterList = characterList;
    }

    public String getString() {
        return string;
    }

    public void setString(final String string) {
        this.string = string;
    }

    public List<String> getStringList() {
        return stringList;
    }

    public void setStringList(final List<String> stringList) {
        this.stringList = stringList;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(final Pattern pattern) {
        this.pattern = pattern;
    }

    public List<Pattern> getPatternList() {
        return patternList;
    }

    public void setPatternList(final List<Pattern> patternList) {
        this.patternList = patternList;
    }

    public Instant getInstant() {
        return instant;
    }

    public void setInstant(final Instant instant) {
        this.instant = instant;
    }

    public List<Instant> getInstantList() {
        return instantList;
    }

    public void setInstantList(final List<Instant> instantList) {
        this.instantList = instantList;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(final LocalDate localDate) {
        this.localDate = localDate;
    }

    public List<LocalDate> getLocalDateList() {
        return localDateList;
    }

    public void setLocalDateList(final List<LocalDate> localDateList) {
        this.localDateList = localDateList;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(final LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public List<LocalDateTime> getLocalDateTimeList() {
        return localDateTimeList;
    }

    public void setLocalDateTimeList(final List<LocalDateTime> localDateTimeList) {
        this.localDateTimeList = localDateTimeList;
    }

    public LocalTime getLocalTime() {
        return localTime;
    }

    public void setLocalTime(final LocalTime localTime) {
        this.localTime = localTime;
    }

    public List<LocalTime> getLocalTimeList() {
        return localTimeList;
    }

    public void setLocalTimeList(final List<LocalTime> localTimeList) {
        this.localTimeList = localTimeList;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(final UUID uuid) {
        this.uuid = uuid;
    }

    public List<UUID> getUuidList() {
        return uuidList;
    }

    public void setUuidList(final List<UUID> uuidList) {
        this.uuidList = uuidList;
    }

    public Code getCode() {
        return code;
    }

    public void setCode(final Code code) {
        this.code = code;
    }

    public List<Code> getCodeList() {
        return codeList;
    }

    public void setCodeList(final List<Code> codeList) {
        this.codeList = codeList;
    }

    public Binary getBinary() {
        return binary;
    }

    public void setBinary(final Binary binary) {
        this.binary = binary;
    }

    public List<Binary> getBinaryList() {
        return binaryList;
    }

    public void setBinaryList(final List<Binary> binaryList) {
        this.binaryList = binaryList;
    }

    public Nested getNested() {
        return nested;
    }

    public void setNested(final Nested nested) {
        this.nested = nested;
    }

    public List<Nested> getNestedList() {
        return nestedList;
    }

    public void setNestedList(final List<Nested> nestedList) {
        this.nestedList = nestedList;
    }
}
