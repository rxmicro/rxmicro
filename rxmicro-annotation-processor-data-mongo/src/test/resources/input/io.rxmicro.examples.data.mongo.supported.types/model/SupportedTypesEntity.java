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

import io.rxmicro.data.mongo.DocumentId;
import org.bson.types.Binary;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

import static io.rxmicro.common.util.Formats.format;
import static java.util.stream.Collectors.toList;

public final class SupportedTypesEntity extends SupportedTypesDocument {

    public static final String COLLECTION_NAME = "supportedTypes";

    @DocumentId
    ObjectId id;

    public ObjectId getId() {
        return id;
    }

    public SupportedTypesEntity setId(final ObjectId id) {
        this.id = id;
        return this;
    }

    public SupportedTypesEntity setBigDecimal(final BigDecimal bigDecimal) {
        this.bigDecimal = bigDecimal;
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id, status, statusList, aBoolean, booleanList,
                aByte, byteList, aShort, shortList, aInteger, integerList, aLong, longList,
                aFloat, floatList, aDouble, doubleList, bigDecimal, bigDecimalList,
                character, characterList, string, stringList, String.valueOf(pattern),
                Optional.ofNullable(patternList).map(list -> list.stream().map(Objects::toString).collect(toList())).orElse(List.of()),
                instant, instantList, localDate, localDateList, localDateTime, localDateTimeList, localTime, localTimeList,
                uuid, uuidList, code, codeList, binary, binaryList
        );
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final SupportedTypesEntity that = (SupportedTypesEntity) o;
        return Objects.equals(id, that.id) &&
                status == that.status &&
                Objects.equals(statusList, that.statusList) &&
                Objects.equals(aBoolean, that.aBoolean) &&
                Objects.equals(booleanList, that.booleanList) &&
                Objects.equals(aByte, that.aByte) &&
                Objects.equals(byteList, that.byteList) &&
                Objects.equals(aShort, that.aShort) &&
                Objects.equals(shortList, that.shortList) &&
                Objects.equals(aInteger, that.aInteger) &&
                Objects.equals(integerList, that.integerList) &&
                Objects.equals(aLong, that.aLong) &&
                Objects.equals(longList, that.longList) &&
                Objects.equals(aFloat, that.aFloat) &&
                Objects.equals(floatList, that.floatList) &&
                Objects.equals(aDouble, that.aDouble) &&
                Objects.equals(doubleList, that.doubleList) &&
                Objects.equals(bigDecimal, that.bigDecimal) &&
                Objects.equals(bigDecimalList, that.bigDecimalList) &&
                Objects.equals(character, that.character) &&
                Objects.equals(characterList, that.characterList) &&
                Objects.equals(string, that.string) &&
                Objects.equals(stringList, that.stringList) &&
                equals(pattern, that.pattern) &&
                equals(patternList, that.patternList) &&
                Objects.equals(instant, that.instant) &&
                Objects.equals(instantList, that.instantList) &&
                Objects.equals(localDate, that.localDate) &&
                Objects.equals(localDateList, that.localDateList) &&
                Objects.equals(localDateTime, that.localDateTime) &&
                Objects.equals(localDateTimeList, that.localDateTimeList) &&
                Objects.equals(localTime, that.localTime) &&
                Objects.equals(localTimeList, that.localTimeList) &&
                Objects.equals(uuid, that.uuid) &&
                Objects.equals(uuidList, that.uuidList) &&
                Objects.equals(code, that.code) &&
                Objects.equals(codeList, that.codeList) &&
                Objects.equals(binary, that.binary) &&
                Objects.equals(binaryList, that.binaryList);
    }

    @Override
    public String toString() {
        return "SupportedTypesEntity{" +
                "id=" + id +
                ", status=" + status +
                ", statusList=" + statusList +
                ", aBoolean=" + aBoolean +
                ", booleanList=" + booleanList +
                ", aByte=" + aByte +
                ", byteList=" + byteList +
                ", aShort=" + aShort +
                ", shortList=" + shortList +
                ", aInteger=" + aInteger +
                ", integerList=" + integerList +
                ", aLong=" + aLong +
                ", longList=" + longList +
                ", aFloat=" + aFloat +
                ", floatList=" + floatList +
                ", aDouble=" + aDouble +
                ", doubleList=" + doubleList +
                ", bigDecimal=" + bigDecimal +
                ", bigDecimalList=" + bigDecimalList +
                ", character=" + character +
                ", characterList=" + characterList +
                ", string='" + string + '\'' +
                ", stringList=" + stringList +
                ", pattern=" + pattern +
                ", patternList=" + patternList +
                ", instant=" + instant +
                ", instantList=" + instantList +
                ", localDate=" + localDate +
                ", localDateList=" + localDateList +
                ", localDateTime=" + localDateTime +
                ", localDateTimeList=" + localDateTimeList +
                ", localTime=" + localTime +
                ", localTimeList=" + localTimeList +
                ", uuid=" + uuid +
                ", uuidList=" + uuidList +
                ", code=" + code +
                ", codeList=" + codeList +
                ", binary=" + (binary != null ? toString(binary) : "null") +
                ", binaryList=" + (binaryList != null ? binaryList.stream().map(this::toString).collect(toList()) : "null") +
                '}';
    }

    private String toString(final Binary binary) {
        return format(
                "Binary{type=?, data=?}",
                Integer.toHexString(binary.getType()),
                Arrays.toString(binary.getData())
        );
    }

    private boolean equals(final List<Pattern> patternList1,
                           final List<Pattern> patternList2) {
        if (patternList1 != null && patternList2 != null) {
            return patternList1.stream().map(Objects::toString).collect(toList())
                    .equals(patternList2.stream().map(Objects::toString).collect(toList()));
        } else {
            return Objects.equals(patternList1, patternList2);
        }
    }

    private boolean equals(final Pattern pattern1,
                           final Pattern pattern2) {
        if (pattern1 != null && pattern2 != null) {
            return pattern1.toString().equals(pattern2.toString());
        } else {
            return Objects.equals(pattern1, pattern2);
        }
    }
}
