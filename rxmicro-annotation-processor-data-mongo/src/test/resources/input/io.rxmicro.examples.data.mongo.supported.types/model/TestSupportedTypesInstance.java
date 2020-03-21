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
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import static org.bson.BsonBinarySubType.USER_DEFINED;

public final class TestSupportedTypesInstance {

    public static final SupportedTypesEntity SUPPORTED_TYPES = newSupportedTypes(SupportedTypesEntity.class);

    public static final SupportedTypesEntity SUPPORTED_TYPES_WITH_BIG_DECIMAL_PROJECTION =
            new SupportedTypesEntity()
                    .setId(SUPPORTED_TYPES.getId())
                    .setBigDecimal(SUPPORTED_TYPES.getBigDecimal());

    @SuppressWarnings("unchecked")
    public static <T extends SupportedTypesDocument> T newSupportedTypes(final Class<T> supportedTypesClass) {
        final SupportedTypesDocument supportedTypes;
        if (supportedTypesClass == SupportedTypesEntity.class) {
            supportedTypes = new SupportedTypesEntity();
            ((SupportedTypesEntity) supportedTypes).id = new ObjectId("507f1f77bcf86cd799439011");
        } else {
            supportedTypes = new SupportedTypesDocument();
        }
        supportedTypes.status = Status.created;
        supportedTypes.statusList = List.of(supportedTypes.status);

        supportedTypes.aBoolean = true;
        supportedTypes.booleanList = List.of(supportedTypes.aBoolean);

        supportedTypes.aByte = 127;
        supportedTypes.byteList = List.of(supportedTypes.aByte);

        supportedTypes.aShort = 500;
        supportedTypes.shortList = List.of(supportedTypes.aShort);

        supportedTypes.aInteger = 1000;
        supportedTypes.integerList = List.of(supportedTypes.aInteger);

        supportedTypes.aLong = 999_999_999_999L;
        supportedTypes.longList = List.of(supportedTypes.aLong);

        supportedTypes.aFloat = 3.14f;
        supportedTypes.floatList = List.of(supportedTypes.aFloat);

        supportedTypes.aDouble = 3.14;
        supportedTypes.doubleList = List.of(supportedTypes.aDouble);

        supportedTypes.bigDecimal = new BigDecimal("3.14");
        supportedTypes.bigDecimalList = List.of(supportedTypes.bigDecimal);

        supportedTypes.character = 'y';
        supportedTypes.characterList = List.of(supportedTypes.character);

        supportedTypes.string = "string";
        supportedTypes.stringList = List.of(supportedTypes.string);

        supportedTypes.pattern = Pattern.compile("rxmicro");
        supportedTypes.patternList = List.of(supportedTypes.pattern);

        supportedTypes.instant = Instant.parse("2020-02-02T02:20:00.00Z");
        supportedTypes.instantList = List.of(supportedTypes.instant);

        supportedTypes.localDate = supportedTypes.instant.atZone(ZoneId.of("UTC")).toLocalDate();
        supportedTypes.localDateList = List.of(supportedTypes.localDate);

        supportedTypes.localDateTime = supportedTypes.instant.atZone(ZoneId.of("UTC")).toLocalDateTime();
        supportedTypes.localDateTimeList = List.of(supportedTypes.localDateTime);

        supportedTypes.localTime = supportedTypes.instant.atZone(ZoneId.of("UTC")).toLocalTime();
        supportedTypes.localTimeList = List.of(supportedTypes.localTime);

        supportedTypes.uuid = UUID.fromString("d3129be8-6745-3e12-0000-4442665556a4");
        supportedTypes.uuidList = List.of(supportedTypes.uuid);

        supportedTypes.code = new Code("function test(){}");
        supportedTypes.codeList = List.of(supportedTypes.code);

        supportedTypes.binary = new Binary(USER_DEFINED, new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9});
        supportedTypes.binaryList = List.of(supportedTypes.binary);
        return (T) supportedTypes;
    }

    private TestSupportedTypesInstance() {
    }
}
