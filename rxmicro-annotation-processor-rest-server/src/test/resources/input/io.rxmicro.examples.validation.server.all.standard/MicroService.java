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

package io.rxmicro.examples.validation.server.all.standard;

import io.rxmicro.examples.validation.server.all.standard.model.Color;
import io.rxmicro.examples.validation.server.all.standard.model.Model;
import io.rxmicro.rest.method.PUT;
import io.rxmicro.validation.constraint.AssertFalse;
import io.rxmicro.validation.constraint.AssertTrue;
import io.rxmicro.validation.constraint.Base64URLEncoded;
import io.rxmicro.validation.constraint.CountryCode;
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
import io.rxmicro.validation.constraint.Max;
import io.rxmicro.validation.constraint.MaxSize;
import io.rxmicro.validation.constraint.MinDouble;
import io.rxmicro.validation.constraint.MinInt;
import io.rxmicro.validation.constraint.MinLength;
import io.rxmicro.validation.constraint.Min;
import io.rxmicro.validation.constraint.MinSize;
import io.rxmicro.validation.constraint.Nullable;
import io.rxmicro.validation.constraint.NullableArrayItem;
import io.rxmicro.validation.constraint.Numeric;
import io.rxmicro.validation.constraint.Past;
import io.rxmicro.validation.constraint.PastOrPresent;
import io.rxmicro.validation.constraint.Pattern;
import io.rxmicro.validation.constraint.Phone;
import io.rxmicro.validation.constraint.Port;
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
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletionStage;

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
import static java.util.concurrent.CompletableFuture.completedStage;

final class MicroService {

    @PUT("/put1")
    CompletionStage<Model> put1(final Model model) {
        return completedStage(model);
    }

    @PUT("/put2")
    CompletionStage<Model> put2(@Nullable final String optionalParameter,
                                @AssertFalse
                                @AssertTrue final Boolean booleanParameter,
                                /*@Min("PT0S")
                                @Max("PT5S")
                                Duration durationParameter,*/
                                @MinInt(3)
                                @MaxInt(10)
                                @Min("3")
                                @Max("10") final Byte byteParameter,
                                @MinInt(3)
                                @MaxInt(10)
                                @Min("3")
                                @Max("10") final Short shortParameter,
                                @MinInt(3)
                                @MaxInt(10)
                                @Min("3")
                                @Max("10") final Integer intParameter,
                                @MinInt(3)
                                @MaxInt(10)
                                @Min("3")
                                @Max("10") final Long longParameter,
                                @Min("3")
                                @Max("10") final BigInteger bigIntParameter,
                                @MinDouble(3.1)
                                @MaxDouble(10.9)
                                @Min("3")
                                @Max("10") final Float floatParameter,
                                @MinDouble(3.1)
                                @MaxDouble(10.9)
                                @Min("3")
                                @Max("10") final Double doubleParameter,
                                @Min("3.1")
                                @Max("10.9")
                                @Lat
                                @Lng
                                @Numeric(scale = 5, precision = 2) final BigDecimal decimalParameter,
                                @Enumeration({"y", "n"}) final Character charParameter,
                                @Lowercase
                                @Uppercase
                                @Length(2)
                                @MinLength(2)
                                @MaxLength(2)
                                @Base64URLEncoded
                                @Email
                                @Phone
                                @Enumeration({"3", "2", "3"})
                                @IP
                                @Pattern(regexp = "hello")
                                @URI
                                @URLEncoded
                                @Viber
                                @WhatsApp
                                @Telegram
                                @Skype
                                @CountryCode
                                @HostName
                                @LatinAlphabetOnly final String stringParameter,
                                @Future
                                @FutureOrPresent
                                @Past
                                @PastOrPresent
                                @TruncatedTime final Instant instantParameter,
                                @SubEnum(include = {"RED", "BLUE"}) final Color colorParameter,
                                // ----------------------------------------------------------------------------------
                                @Nullable
                                @NullableArrayItem final List<String> optionalList,
                                @UniqueItems
                                @Size(12)
                                @MinSize(2)
                                @MaxSize(50)
                                @AssertFalse
                                @AssertTrue final List<Boolean> booleanValues,
                                @UniqueItems
                                @Size(12)
                                @MinSize(2)
                                @MaxSize(50)
                                @MinInt(3)
                                @MaxInt(10) final List<Byte> byteValues,
                                @UniqueItems
                                @Size(12)
                                @MinSize(2)
                                @MaxSize(50)
                                @MinInt(3)
                                @MaxInt(10) final List<Short> shortValues,
                                @UniqueItems
                                @Size(12)
                                @MinSize(2)
                                @MaxSize(50)
                                @MinInt(3)
                                @MaxInt(10) final List<Integer> intValues,
                                @UniqueItems
                                @Size(12)
                                @MinSize(2)
                                @MaxSize(50)
                                @MinInt(3)
                                @MaxInt(10) final List<Long> longValues,
                                @UniqueItems
                                @Size(12)
                                @MinSize(2)
                                @MaxSize(50)
                                @Enumeration({"y", "n"}) final List<Character> charValues,
                                @UniqueItems
                                @Size(12)
                                @MinSize(2)
                                @MaxSize(50)
                                @MinDouble(3.1)
                                @MaxDouble(10.9) final List<Float> floatValues,
                                @UniqueItems
                                @Size(12)
                                @MinSize(2)
                                @MaxSize(50)
                                @MinDouble(3.1)
                                @MaxDouble(10.9) final List<Double> doubleValues,
                                @UniqueItems
                                @Size(12)
                                @MinSize(2)
                                @MaxSize(50)
                                @Min("3.1")
                                @Max("10.9")
                                @Lat
                                @Lng
                                @Numeric(scale = 5, precision = 2) final List<BigDecimal> decimals,
                                @UniqueItems
                                @Size(12)
                                @MinSize(2)
                                @MaxSize(50)
                                @Min("3")
                                @Max("10") final List<BigInteger> bigIntegers,
                                @UniqueItems
                                @Size(12)
                                @MinSize(2)
                                @MaxSize(50)
                                @Lowercase
                                @Uppercase
                                @Length(2)
                                @MinLength(2)
                                @MaxLength(2)
                                @Base64URLEncoded
                                @Email
                                @Phone
                                @Enumeration({"3", "2", "3"})
                                @IP
                                @Pattern(regexp = "hello")
                                @URI
                                @URLEncoded
                                @Viber
                                @WhatsApp
                                @Telegram
                                @Skype
                                @CountryCode
                                @HostName
                                @LatinAlphabetOnly final List<String> strings,
                                @UniqueItems
                                @Size(12)
                                @MinSize(2)
                                @MaxSize(50)
                                @Future
                                @FutureOrPresent
                                @Past
                                @PastOrPresent
                                @TruncatedTime final List<Instant> instants,
                                @UniqueItems
                                @Size(12)
                                @MinSize(2)
                                @MaxSize(50)
                                @SubEnum(include = {"RED", "BLUE"}) final List<Color> colors,
                                @Nullable
                                @NullableArrayItem final Set<String> optionalSet,
                                @Size(12)
                                @MinSize(2)
                                @MaxSize(50)
                                @AssertFalse
                                @AssertTrue final Set<Boolean> booleanSet,
                                @Size(12)
                                @MinSize(2)
                                @MaxSize(50)
                                @MinInt(3)
                                @MaxInt(10) final Set<Byte> byteSet,
                                @Size(12)
                                @MinSize(2)
                                @MaxSize(50)
                                @MinInt(3)
                                @MaxInt(10) final Set<Short> shortSet,
                                @Size(12)
                                @MinSize(2)
                                @MaxSize(50)
                                @MinInt(3)
                                @MaxInt(10) final Set<Integer> intSet,
                                @Size(12)
                                @MinSize(2)
                                @MaxSize(50)
                                @MinInt(3)
                                @MaxInt(10) final Set<Long> longSet,
                                @Size(12)
                                @MinSize(2)
                                @MaxSize(50)
                                @Enumeration({"y", "n"}) final Set<Character> charSet,
                                @Size(12)
                                @MinSize(2)
                                @MaxSize(50)
                                @MinDouble(3.1)
                                @MaxDouble(10.9) final Set<Float> floatSet,
                                @Size(12)
                                @MinSize(2)
                                @MaxSize(50)
                                @MinDouble(3.1)
                                @MaxDouble(10.9) final Set<Double> doubleSet,
                                @Size(12)
                                @MinSize(2)
                                @MaxSize(50)
                                @Min("3.1")
                                @Max("10.9")
                                @Lat
                                @Lng
                                @Numeric(scale = 5, precision = 2) final Set<BigDecimal> decimalSet,
                                @Size(12)
                                @MinSize(2)
                                @MaxSize(50)
                                @Min("3")
                                @Max("10") final Set<BigInteger> bigIntegerSet,
                                @Size(12)
                                @MinSize(2)
                                @MaxSize(50)
                                @Lowercase
                                @Uppercase
                                @Length(2)
                                @MinLength(2)
                                @MaxLength(2)
                                @Base64URLEncoded
                                @Email
                                @Phone
                                @Enumeration({"3", "2", "3"})
                                @IP
                                @Pattern(regexp = "hello")
                                @URI
                                @URLEncoded
                                @Viber
                                @WhatsApp
                                @Telegram
                                @Skype
                                @CountryCode
                                @HostName
                                @LatinAlphabetOnly final Set<String> stringSet,
                                @Size(12)
                                @MinSize(2)
                                @MaxSize(50)
                                @Future
                                @FutureOrPresent
                                @Past
                                @PastOrPresent
                                @TruncatedTime final Set<Instant> instantSet,
                                @Size(12)
                                @MinSize(2)
                                @MaxSize(50)
                                @SubEnum(include = {"RED", "BLUE"}) final Set<Color> colorSet,
                                // -------------------------------------------------------------------------------------------------
                                @Nullable
                                @NullableArrayItem final Map<String, String> optionalMap,
                                @Size(12)
                                @MinSize(2)
                                @MaxSize(50)
                                @AssertFalse
                                @AssertTrue final Map<String, Boolean> booleanMap,
                                @Size(12)
                                @MinSize(2)
                                @MaxSize(50)
                                @MinInt(3)
                                @MaxInt(10) final Map<String, Byte> byteMap,
                                @Size(12)
                                @MinSize(2)
                                @MaxSize(50)
                                @MinInt(3)
                                @MaxInt(10) final Map<String, Short> shortMap,
                                @Size(12)
                                @MinSize(2)
                                @MaxSize(50)
                                @MinInt(3)
                                @MaxInt(10) final Map<String, Integer> intMap,
                                @Size(12)
                                @MinSize(2)
                                @MaxSize(50)
                                @MinInt(3)
                                @MaxInt(10) final Map<String, Long> longMap,
                                @Size(12)
                                @MinSize(2)
                                @MaxSize(50)
                                @Enumeration({"y", "n"}) final Map<String, Character> charMap,
                                @Size(12)
                                @MinSize(2)
                                @MaxSize(50)
                                @MinDouble(3.1)
                                @MaxDouble(10.9) final Map<String, Float> floatMap,
                                @Size(12)
                                @MinSize(2)
                                @MaxSize(50)
                                @MinDouble(3.1)
                                @MaxDouble(10.9) final Map<String, Double> doubleMap,
                                @Size(12)
                                @MinSize(2)
                                @MaxSize(50)
                                @Min("3.1")
                                @Max("10.9")
                                @Lat
                                @Lng
                                @Numeric(scale = 5, precision = 2) final Map<String, BigDecimal> decimalMap,
                                @Size(12)
                                @MinSize(2)
                                @MaxSize(50)
                                @Min("3")
                                @Max("10") final Map<String, BigInteger> bigIntegerMap,
                                @Size(12)
                                @MinSize(2)
                                @MaxSize(50)
                                @Lowercase
                                @Uppercase
                                @Length(2)
                                @MinLength(2)
                                @MaxLength(2)
                                @Base64URLEncoded
                                @Email
                                @Phone
                                @Enumeration({"3", "2", "3"})
                                @IP
                                @Pattern(regexp = "hello")
                                @URI
                                @URLEncoded
                                @Viber
                                @WhatsApp
                                @Telegram
                                @Skype
                                @CountryCode
                                @HostName
                                @LatinAlphabetOnly final Map<String, String> stringMap,
                                @Size(12)
                                @MinSize(2)
                                @MaxSize(50)
                                @Future
                                @FutureOrPresent
                                @Past
                                @PastOrPresent
                                @TruncatedTime final Map<String, Instant> instantMap,
                                @Size(12)
                                @MinSize(2)
                                @MaxSize(50)
                                @SubEnum(include = {"RED", "BLUE"}) final Map<String, Color> colorMap,
                                // ----------------------------------------------------------------------------------
                                @CountryCode(format = ISO_3166_1_ALPHA_2) final String countryCodeAlpha2,
                                @CountryCode(format = ISO_3166_1_ALPHA_3) final String countryCodeAlpha3,
                                @CountryCode(format = ISO_3166_1_NUMERIC) final String countryCodeNumeric,
                                @Base64URLEncoded(alphabet = BASE) final String base64URLEncodedBase,
                                @Base64URLEncoded(alphabet = URL) final String base64URLEncodedUrl,
                                @IP({IP_V4, IP_V6}) final String ip,
                                @IP(IP_V4) final String ip4,
                                @IP(IP_V6) final String ip6,
                                @Lat(ACCURACY_111_KILOMETERS) final BigDecimal lat111km,
                                @Lng(ACCURACY_111_KILOMETERS) final BigDecimal lng111km,
                                @Lat(ACCURACY_11_KILOMETERS) final BigDecimal lat11km,
                                @Lng(ACCURACY_11_KILOMETERS) final BigDecimal lng11km,
                                @Lat(ACCURACY_1_KILOMETER) final BigDecimal lat1km,
                                @Lng(ACCURACY_1_KILOMETER) final BigDecimal lng1km,
                                @Lat(ACCURACY_111_METERS) final BigDecimal lat111m,
                                @Lng(ACCURACY_111_METERS) final BigDecimal lng111m,
                                @Lat(ACCURACY_11_METERS) final BigDecimal lat11m,
                                @Lng(ACCURACY_11_METERS) final BigDecimal lng11m,
                                @Lat(ACCURACY_1_METER) final BigDecimal lat1m,
                                @Lng(ACCURACY_1_METER) final BigDecimal lng1m,
                                @Lat(ACCURACY_11_CENTIMETERS) final BigDecimal lat11cm,
                                @Lng(ACCURACY_11_CENTIMETERS) final BigDecimal lng11cm,
                                @Lat(ACCURACY_1_CENTIMETER) final BigDecimal lat1cm,
                                @Lng(ACCURACY_1_CENTIMETER) final BigDecimal lng1cm,
                                /*@ExistingPath final Path anyPath,
                                @ExistingDirectory final Path dirPath,
                                @ExistingFile final Path filePath,*/
                                @Port final Integer port) {
        return completedStage(new Model());
    }

}
