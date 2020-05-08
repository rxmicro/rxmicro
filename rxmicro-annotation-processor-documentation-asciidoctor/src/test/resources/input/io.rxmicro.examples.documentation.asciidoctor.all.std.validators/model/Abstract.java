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

package io.rxmicro.examples.documentation.asciidoctor.all.std.validators.model;

import io.rxmicro.documentation.Example;
import io.rxmicro.examples.documentation.asciidoctor.all.std.validators.model.nested.Nested;
import io.rxmicro.rest.Header;
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
import io.rxmicro.validation.constraint.Nullable;
import io.rxmicro.validation.constraint.NullableArrayItem;
import io.rxmicro.validation.constraint.Numeric;
import io.rxmicro.validation.constraint.Past;
import io.rxmicro.validation.constraint.PastOrPresent;
import io.rxmicro.validation.constraint.Pattern;
import io.rxmicro.validation.constraint.Phone;
import io.rxmicro.validation.constraint.Size;
import io.rxmicro.validation.constraint.Skype;
import io.rxmicro.validation.constraint.SubEnum;
import io.rxmicro.validation.constraint.Telegram;
import io.rxmicro.validation.constraint.TruncatedTime;
import io.rxmicro.validation.constraint.URI;
import io.rxmicro.validation.constraint.URLEncoded;
import io.rxmicro.validation.constraint.UniqueItems;
import io.rxmicro.validation.constraint.Uppercase;
import io.rxmicro.validation.constraint.Viber;
import io.rxmicro.validation.constraint.WhatsApp;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.List;

import static io.rxmicro.http.HttpStandardHeaderNames.REQUEST_ID;
import static io.rxmicro.validation.base.LocationAccuracy.ACCURACY_111_KILOMETERS;
import static io.rxmicro.validation.base.LocationAccuracy.ACCURACY_111_METERS;
import static io.rxmicro.validation.base.LocationAccuracy.ACCURACY_11_CENTIMETERS;
import static io.rxmicro.validation.base.LocationAccuracy.ACCURACY_11_KILOMETERS;
import static io.rxmicro.validation.base.LocationAccuracy.ACCURACY_11_METERS;
import static io.rxmicro.validation.base.LocationAccuracy.ACCURACY_1_CENTIMETER;
import static io.rxmicro.validation.base.LocationAccuracy.ACCURACY_1_KILOMETER;
import static io.rxmicro.validation.base.LocationAccuracy.ACCURACY_1_METER;
import static io.rxmicro.validation.constraint.Base64URLEncoded.Alphabet.BASE;
import static io.rxmicro.validation.constraint.Base64URLEncoded.Alphabet.URL;
import static io.rxmicro.validation.constraint.CountryCode.Format.ISO_3166_1_ALPHA_2;
import static io.rxmicro.validation.constraint.CountryCode.Format.ISO_3166_1_ALPHA_3;
import static io.rxmicro.validation.constraint.CountryCode.Format.ISO_3166_1_NUMERIC;
import static io.rxmicro.validation.constraint.IP.Version.IP_V4;
import static io.rxmicro.validation.constraint.IP.Version.IP_V6;

@SuppressWarnings("unused")
abstract class Abstract {

    @Nullable
    private String optional;

    // -----------------------------------------------------------------------------------------------------------------

    private Boolean aBoolean;

    @Example("true")
    private Boolean exampleBoolean;

    @AssertFalse
    private Boolean assertFalse;

    @AssertTrue
    private Boolean assertTrue;

    // -----------------------------------------------------------------------------------------------------------------

    private Byte aByte;

    @Example("7")
    private Byte exampleByte;

    @MinInt(5)
    private Byte minIntByte;

    @MaxInt(15)
    private Byte maxIntByte;

    @MinNumber("5")
    private Byte minNumberByte;

    @MaxNumber("15")
    private Byte maxNumberByte;

    // -----------------------------------------------------------------------------------------------------------------

    private Short aShort;

    @Example("7_000")
    private Short exampleShort;

    @MinInt(5_000)
    private Short minIntShort;

    @MaxInt(15_000)
    private Short maxIntShort;

    @MinNumber("5_000")
    private Short minNumberShort;

    @MaxNumber("15_000")
    private Short maxNumberShort;

    // -----------------------------------------------------------------------------------------------------------------

    private Integer anInteger;

    @Example("700_000_000")
    private Integer exampleInteger;

    @MinInt(500_000_000)
    private Integer minIntInteger;

    @MaxInt(2_000_000_000)
    private Integer maxIntInteger;

    @MinNumber("500_000_000")
    private Integer minNumberInteger;

    @MaxNumber("2_000_000_000")
    private Integer maxNumberInteger;

    // -----------------------------------------------------------------------------------------------------------------

    private Long aLong;

    @Example("700_000_000_000")
    private Long exampleLong;

    @MinInt(500_000_000_000L)
    private Long minIntLong;

    @MaxInt(2_000_000_000_000L)
    private Long maxIntLong;

    @MinNumber("500_000_000_000")
    private Long minNumberLong;

    @MaxNumber("2_000_000_000_000")
    private Long maxNumberLong;

    // -----------------------------------------------------------------------------------------------------------------

    private BigInteger aBigInteger;

    @Example("700_000_000_000_000_000_000")
    private BigInteger exampleBigInteger;

    @MinNumber("500_000_000_000_000_000_000")
    private BigInteger minNumberBigInteger;

    @MaxNumber("2_000_000_000_000_000_000_000")
    private BigInteger maxNumberBigInteger;

    // -----------------------------------------------------------------------------------------------------------------

    private Float aFloat;

    @Example("2.78")
    private Float exampleFloat;

    @MinDouble(1.1)
    private Float minIntFloat;

    @MaxDouble(6.78)
    private Float maxIntFloat;

    @MinNumber("1.1")
    private Float minNumberFloat;

    @MaxNumber("6.78")
    private Float maxNumberFloat;

    // -----------------------------------------------------------------------------------------------------------------

    private Double aDouble;

    @Example("2.789654321")
    private Double exampleDouble;

    @MinDouble(1.1234567890)
    private Double minIntDouble;

    @MaxDouble(6.7815926535)
    private Double maxIntDouble;

    @MinNumber("1.1234567890")
    private Double minNumberDouble;

    @MaxNumber("6.7815926535")
    private Double maxNumberDouble;

    // -----------------------------------------------------------------------------------------------------------------

    private BigDecimal aBigDecimal;

    @Example("2.9876543210987654321098765432109876543210")
    private BigDecimal exampleBigDecimal;

    @MinNumber("1.1234567890123456789012345678901234567890")
    private BigDecimal minNumberBigDecimal;

    @MaxNumber("6.7815926535897932384626433832795028841971")
    private BigDecimal maxNumberBigDecimal;

    @Numeric(scale = 5, precision = 2)
    private BigDecimal numericBigDecimal;

    @Lat(ACCURACY_111_KILOMETERS)
    private BigDecimal lat111kmBigDecimal;

    @Lng(ACCURACY_111_KILOMETERS)
    private BigDecimal lng111kmBigDecimal;

    @Lat(ACCURACY_11_KILOMETERS)
    private BigDecimal lat11kmBigDecimal;

    @Lng(ACCURACY_11_KILOMETERS)
    private BigDecimal lng11kmBigDecimal;

    @Lat(ACCURACY_1_KILOMETER)
    private BigDecimal lat1kmBigDecimal;

    @Lng(ACCURACY_1_KILOMETER)
    private BigDecimal lng1kmBigDecimal;

    @Lat(ACCURACY_111_METERS)
    private BigDecimal lat111mBigDecimal;

    @Lng(ACCURACY_111_METERS)
    private BigDecimal lng111mBigDecimal;

    @Lat(ACCURACY_11_METERS)
    private BigDecimal lat11mBigDecimal;

    @Lng(ACCURACY_11_METERS)
    private BigDecimal lng11mBigDecimal;

    @Lat(ACCURACY_1_METER)
    private BigDecimal lat1mBigDecimal;

    @Lng(ACCURACY_1_METER)
    private BigDecimal lng1mBigDecimal;

    @Lat(ACCURACY_11_CENTIMETERS)
    private BigDecimal lat11cmBigDecimal;

    @Lng(ACCURACY_11_CENTIMETERS)
    private BigDecimal lng11cmBigDecimal;

    @Lat(ACCURACY_1_CENTIMETER)
    private BigDecimal lat1cmBigDecimal;

    @Lng(ACCURACY_1_CENTIMETER)
    private BigDecimal lng1cmBigDecimal;

    // -----------------------------------------------------------------------------------------------------------------

    private Character aCharacter;

    @Example("h")
    private Character exampleCharacter;

    @Enumeration({"y", "n"})
    private Character enumerationChar;

    @Enumeration({"y", "n"})
    @Example("y")
    private Character enumerationWithExampleChar;

    // -----------------------------------------------------------------------------------------------------------------

    private String aString;

    @Example("hello")
    private String exampleString;

    @Lowercase
    private String lowercaseString;

    @Uppercase
    private String uppercaseString;

    @Length(2)
    private String length2String;

    @MinLength(2)
    private String minLength2String;

    @MaxLength(2)
    private String maxLength2String;

    @Email
    private String emailString;

    @Phone
    private String phoneString;

    @Phone(withoutPlus = false)
    private String phoneWithPlusString;

    @Enumeration({"new", "old"})
    private String enumerationString;

    @Pattern(regexp = "hello")
    private String patternString;

    @URI
    private String uriString;

    @URLEncoded
    private String urlEncodedString;

    @Viber
    private String viberString;

    @Viber(withoutPlus = false)
    private String viberWithPlusString;

    @WhatsApp
    private String whatsAppString;

    @WhatsApp(withoutPlus = false)
    private String whatsAppWithPlusString;

    @Telegram
    private String telegramString;

    @Telegram(withoutPlus = false)
    private String telegramWithPlusString;

    @Skype
    private String skypeString;

    @CountryCode(format = ISO_3166_1_ALPHA_2)
    private String countryCodeAlpha2;

    @CountryCode(format = ISO_3166_1_ALPHA_3)
    private String countryCodeAlpha3;

    @CountryCode(format = ISO_3166_1_NUMERIC)
    private String countryCodeNumeric;

    @Base64URLEncoded(alphabet = BASE)
    private String base64URLEncodedBase;

    @Base64URLEncoded(alphabet = URL)
    private String base64URLEncodedUrl;

    @IP({IP_V4, IP_V6})
    private String ip;

    @IP(IP_V4)
    private String ip4;

    @IP(IP_V6)
    private String ip6;

    @HostName
    private String hostNameString;

    @LatinAlphabetOnly
    private String latinAlphabetOnlyString;

    @DigitsOnly
    private String digitsOnlyString;

    // -----------------------------------------------------------------------------------------------------------------

    private Instant anInstant;

    @Example("2020-04-10T23:40:15.789Z")
    private Instant exampleInstant;

    @Future
    private Instant instantFuture;

    @FutureOrPresent
    private Instant instantFutureOrPresent;

    @Past
    private Instant instantPast;

    @PastOrPresent
    private Instant instantPastOrPresent;

    @TruncatedTime(value = TruncatedTime.Truncated.MILLIS)
    private Instant instantTruncatedTimeMillis;

    @TruncatedTime(value = TruncatedTime.Truncated.SECONDS)
    private Instant instantTruncatedTimeSeconds;

    @TruncatedTime(value = TruncatedTime.Truncated.MINUTES)
    private Instant instantTruncatedTimeMinutes;

    @TruncatedTime(value = TruncatedTime.Truncated.HOURS)
    private Instant instantTruncatedTimeHours;

    // -----------------------------------------------------------------------------------------------------------------

    private Color colorEnum;

    @Example("BLUE")
    private Color exampleEnum;

    @SubEnum(include = {"GREEN", "BLUE"})
    private Color subEnumInclude;

    @SubEnum(exclude = {"GREEN", "BLUE"})
    private Color subEnumExclude;

    @SubEnum(include = {"GREEN", "BLUE"})
    @Example("BLUE")
    private Color subEnumWithExample;

    // -----------------------------------------------------------------------------------------------------------------

    @NullableArrayItem
    private List<String> optionalList;

    // -----------------------------------------------------------------------------------------------------------------

    private List<Integer> aList;

    @Example("item1")
    @Example("item2")
    private List<Integer> exampleList;

    @UniqueItems
    private List<Integer> uniqueItemsList;

    @Size(5)
    private List<Integer> size5List;

    @MinSize(5)
    private List<Integer> minSize5List;

    @MaxSize(5)
    private List<Integer> maxSize5List;

    // -----------------------------------------------------------------------------------------------------------------

    @Nullable
    private Nested optionalNested;

    private Nested requiredNested;

    @NullableArrayItem
    private List<Nested> optionalNestedList;

    private List<Nested> requiredNestedList;

    // -----------------------------------------------------------------------------------------------------------------

    @Header(REQUEST_ID)
    private String requestId;
}
