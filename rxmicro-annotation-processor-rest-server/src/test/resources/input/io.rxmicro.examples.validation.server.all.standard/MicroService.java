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
                                @MinInt(3)
                                @MaxInt(10)
                                @MinNumber("3")
                                @MaxNumber("10") final Byte byteParameter,
                                @MinInt(3)
                                @MaxInt(10)
                                @MinNumber("3")
                                @MaxNumber("10") final Short shortParameter,
                                @MinInt(3)
                                @MaxInt(10)
                                @MinNumber("3")
                                @MaxNumber("10") final Integer intParameter,
                                @MinInt(3)
                                @MaxInt(10)
                                @MinNumber("3")
                                @MaxNumber("10") final Long longParameter,
                                @MinNumber("3")
                                @MaxNumber("10") final BigInteger bigIntParameter,
                                @MinDouble(3.1)
                                @MaxDouble(10.9)
                                @MinNumber("3")
                                @MaxNumber("10") final Float floatParameter,
                                @MinDouble(3.1)
                                @MaxDouble(10.9)
                                @MinNumber("3")
                                @MaxNumber("10") final Double doubleParameter,
                                @MinNumber("3.1")
                                @MaxNumber("10.9")
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
                                @MinNumber("3.1")
                                @MaxNumber("10.9")
                                @Lat
                                @Lng
                                @Numeric(scale = 5, precision = 2) final List<BigDecimal> decimals,
                                @UniqueItems
                                @Size(12)
                                @MinSize(2)
                                @MaxSize(50)
                                @MinNumber("3")
                                @MaxNumber("10") final List<BigInteger> bigIntegers,
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
                                @Lng(ACCURACY_1_CENTIMETER) final BigDecimal lng1cm) {
        return completedStage(new Model());
    }

}
