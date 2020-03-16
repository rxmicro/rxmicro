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

package io.rxmicro.examples.documentation.asciidoctor.full.model;

import io.rxmicro.validation.constraint.AssertFalse;
import io.rxmicro.validation.constraint.AssertTrue;
import io.rxmicro.validation.constraint.Base64URLEncoded;
import io.rxmicro.validation.constraint.CountryCode;
import io.rxmicro.validation.constraint.DigitsOnly;
import io.rxmicro.validation.constraint.Email;
import io.rxmicro.validation.constraint.Enumeration;
import io.rxmicro.validation.constraint.Future;
import io.rxmicro.validation.constraint.FutureOrPresent;
import io.rxmicro.validation.constraint.HostName;
import io.rxmicro.validation.constraint.IP;
import io.rxmicro.validation.constraint.Lat;
import io.rxmicro.validation.constraint.LatinAlphabetOnly;
import io.rxmicro.validation.constraint.Length;
import io.rxmicro.validation.constraint.Lng;
import io.rxmicro.validation.constraint.Lowercase;
import io.rxmicro.validation.constraint.MaxDouble;
import io.rxmicro.validation.constraint.MaxInt;
import io.rxmicro.validation.constraint.MaxLength;
import io.rxmicro.validation.constraint.MaxNumber;
import io.rxmicro.validation.constraint.MaxSize;
import io.rxmicro.validation.constraint.MinDouble;
import io.rxmicro.validation.constraint.MinInt;
import io.rxmicro.validation.constraint.MinLength;
import io.rxmicro.validation.constraint.MinNumber;
import io.rxmicro.validation.constraint.MinSize;
import io.rxmicro.validation.constraint.NullableArrayItem;
import io.rxmicro.validation.constraint.Numeric;
import io.rxmicro.validation.constraint.Past;
import io.rxmicro.validation.constraint.PastOrPresent;
import io.rxmicro.validation.constraint.Pattern;
import io.rxmicro.validation.constraint.Phone;
import io.rxmicro.validation.constraint.Size;
import io.rxmicro.validation.constraint.Skype;
import io.rxmicro.validation.constraint.Telegram;
import io.rxmicro.validation.constraint.TruncatedTime;
import io.rxmicro.validation.constraint.URI;
import io.rxmicro.validation.constraint.URLEncoded;
import io.rxmicro.validation.constraint.UniqueItems;
import io.rxmicro.validation.constraint.Uppercase;
import io.rxmicro.validation.constraint.Viber;
import io.rxmicro.validation.constraint.WhatsApp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public final class ValidatedRequest {

    @AssertTrue
    @AssertFalse
    Boolean validBoolean;

    @AssertTrue(off = true)
    @AssertFalse(off = true)
    Boolean inValidBoolean;

    @Future
    @FutureOrPresent
    @Past
    @PastOrPresent
    @TruncatedTime
    Instant validInstant;

    @Future(off = true)
    @FutureOrPresent(off = true)
    @Past(off = true)
    @PastOrPresent(off = true)
    @TruncatedTime(off = true)
    Instant inValidInstant;

    Status status;

    @Enumeration({"new", "old"})
    String validEnum;

    @Enumeration(value = {"new", "old"}, off = true)
    String inValidEnum;

    @Lat
    @Lng
    @MaxNumber("10")
    @MinNumber("0")
    @Numeric(scale = 2)
    BigDecimal validBigDecimal;

    @Numeric(scale = -1, precision = 4)
    BigDecimal bigDecimal;

    @Lat(off = true)
    @Lng(off = true)
    @MaxNumber(value = "10", off = true)
    @MinNumber(value = "0", off = true)
    @Numeric(scale = 2, off = true)
    BigDecimal inValidBigDecimal;

    @MinInt(10)
    @MaxInt(100)
    Integer validInteger;

    @MinInt(value = 10, off = true)
    @MaxInt(value = 100, off = true)
    Integer inValidInteger;

    @MinDouble(10.)
    @MaxDouble(100.)
    Double validDouble;

    @MinDouble(value = 10., off = true)
    @MaxDouble(value = 100., off = true)
    Double inValidDouble;

    @Base64URLEncoded
    @CountryCode
    @DigitsOnly
    @Email
    @HostName
    @IP
    @LatinAlphabetOnly
    @Length(value = 10)
    @MinLength(value = 10)
    @MaxLength(value = 10)
    @Lowercase
    @Pattern(regexp = "hello")
    @Phone
    @Skype
    @Telegram
    @Uppercase
    @URI
    @URLEncoded
    @Viber
    @WhatsApp
    String validString;

    @Base64URLEncoded(off = true)
    @CountryCode(off = true)
    @DigitsOnly(off = true)
    @Email(off = true)
    @HostName(off = true)
    @IP(off = true)
    @LatinAlphabetOnly(off = true)
    @Length(value = 10, off = true)
    @MinLength(value = 10, off = true)
    @MaxLength(value = 10, off = true)
    @Lowercase(off = true)
    @Pattern(regexp = "hello", off = true)
    @Phone(off = true)
    @Skype(off = true)
    @Telegram(off = true)
    @Uppercase(off = true)
    @URI(off = true)
    @URLEncoded(off = true)
    @Viber(off = true)
    @WhatsApp(off = true)
    String inValidString;

    @Size(value = 10)
    @MinSize(value = 10)
    @MaxSize(value = 10)
    @UniqueItems
    @NullableArrayItem
    List<String> validItems;

    @Size(value = 10, off = true)
    @MinSize(value = 10, off = true)
    @MaxSize(value = 10, off = true)
    @UniqueItems(off = true)
    @NullableArrayItem(off = true)
    List<String> inValidItems;

    @IP(IP.Version.IP_V4)
    String ip;

    Character character;
}
