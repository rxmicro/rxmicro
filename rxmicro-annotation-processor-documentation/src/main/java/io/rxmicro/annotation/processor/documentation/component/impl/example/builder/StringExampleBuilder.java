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

package io.rxmicro.annotation.processor.documentation.component.impl.example.builder;

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.documentation.component.impl.example.TypeExampleBuilder;
import io.rxmicro.annotation.processor.rest.model.RestModelField;
import io.rxmicro.common.ImpossibleException;
import io.rxmicro.rest.RequestId;
import io.rxmicro.validation.constraint.Base64URLEncoded;
import io.rxmicro.validation.constraint.CountryCode;
import io.rxmicro.validation.constraint.DigitsOnly;
import io.rxmicro.validation.constraint.DomainName;
import io.rxmicro.validation.constraint.Email;
import io.rxmicro.validation.constraint.HostName;
import io.rxmicro.validation.constraint.IP;
import io.rxmicro.validation.constraint.LatinAlphabetOnly;
import io.rxmicro.validation.constraint.Pattern;
import io.rxmicro.validation.constraint.Phone;
import io.rxmicro.validation.constraint.Skype;
import io.rxmicro.validation.constraint.Telegram;
import io.rxmicro.validation.constraint.URI;
import io.rxmicro.validation.constraint.Uppercase;
import io.rxmicro.validation.constraint.Viber;
import io.rxmicro.validation.constraint.WhatsApp;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.lang.model.type.TypeMirror;

import static io.rxmicro.http.HttpStandardHeaderNames.REQUEST_ID;
import static io.rxmicro.rest.RequestId.REQUEST_ID_EXAMPLE;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class StringExampleBuilder implements TypeExampleBuilder {

    private final List<StringValueExampleBuilder> stringValueExampleBuilders = List.of(

            restModelField -> Optional.ofNullable(restModelField.getAnnotation(Base64URLEncoded.class))
                    .filter(a -> !a.off())
                    .map(a -> "SGVsbG8gd29ybGQh"),

            restModelField -> Optional.ofNullable(restModelField.getAnnotation(CountryCode.class))
                    .filter(a -> !a.off())
                    .map(this::getCountryCodeExample),

            restModelField -> Optional.ofNullable(restModelField.getAnnotation(DigitsOnly.class))
                    .filter(a -> !a.off())
                    .map(a -> "9876"),

            restModelField -> Optional.ofNullable(restModelField.getAnnotation(Email.class))
                    .filter(a -> !a.off())
                    .map(a -> "welcome@rxmicro.io"),

            restModelField -> Optional.ofNullable(restModelField.getAnnotation(HostName.class))
                    .filter(a -> !a.off())
                    .map(a -> "rxmicro.io"),

            restModelField -> Optional.ofNullable(restModelField.getAnnotation(DomainName.class))
                    .filter(a -> !a.off())
                    .map(a -> "rxmicro.io"),

            restModelField -> Optional.ofNullable(restModelField.getAnnotation(IP.class))
                    .filter(a -> !a.off())
                    .map(this::getIPExample),

            restModelField -> Optional.ofNullable(restModelField.getAnnotation(LatinAlphabetOnly.class))
                    .filter(a -> !a.off())
                    .map(a -> "Latin alphabet only!"),

            restModelField -> Optional.ofNullable(restModelField.getAnnotation(Phone.class))
                    .filter(a -> !a.off())
                    .map(a -> getPhoneExample(a.withoutPlus())),

            restModelField -> Optional.ofNullable(restModelField.getAnnotation(Skype.class))
                    .filter(a -> !a.off())
                    .map(a -> "rxmicro.io"),

            restModelField -> Optional.ofNullable(restModelField.getAnnotation(Telegram.class))
                    .filter(a -> !a.off())
                    .map(a -> getTelegramExample(a.withoutPlus())),

            restModelField -> Optional.ofNullable(restModelField.getAnnotation(Uppercase.class))
                    .filter(a -> !a.off())
                    .map(a -> "UPPERCASE"),

            restModelField -> Optional.ofNullable(restModelField.getAnnotation(URI.class))
                    .filter(a -> !a.off())
                    .map(a -> "https://rxmicro.io"),

            restModelField -> Optional.ofNullable(restModelField.getAnnotation(URI.class))
                    .filter(a -> !a.off())
                    .map(a -> "Hello%20world%21"),

            restModelField -> Optional.ofNullable(restModelField.getAnnotation(Viber.class))
                    .filter(a -> !a.off())
                    .map(a -> getViberExample(a.withoutPlus())),

            restModelField -> Optional.ofNullable(restModelField.getAnnotation(WhatsApp.class))
                    .filter(a -> !a.off())
                    .map(a -> getWhatsappExample(a.withoutPlus())),

            restModelField -> Optional.ofNullable(restModelField.getAnnotation(Pattern.class))
                    .filter(a -> !a.off())
                    .map(Pattern::regexp)
    );

    @Override
    public boolean isSupported(final RestModelField restModelField,
                               final TypeMirror typeMirror) {
        return String.class.getName().equals(typeMirror.toString());
    }

    @Override
    public String getExample(final RestModelField restModelField,
                             final TypeMirror typeMirror) {
        if (restModelField.getAnnotation(RequestId.class) != null ||
                restModelField.isHttpHeader() && REQUEST_ID.equalsIgnoreCase(restModelField.getModelName())) {
            return REQUEST_ID_EXAMPLE;
        } else {
            return stringValueExampleBuilders.stream()
                    .flatMap(builder -> builder.build(restModelField).stream())
                    .findFirst()
                    .orElse("string");
        }
    }

    private String getPhoneExample(final boolean withoutPlus) {
        if (withoutPlus) {
            return "12254359430";
        } else {
            return "+12254359430";
        }
    }

    private String getWhatsappExample(final boolean withoutPlus) {
        if (withoutPlus) {
            return "12257117934";
        } else {
            return "+12257117934";
        }
    }

    private String getTelegramExample(final boolean withoutPlus) {
        if (withoutPlus) {
            return "12256782613";
        } else {
            return "+12256782613";
        }
    }

    private String getViberExample(final boolean withoutPlus) {
        if (withoutPlus) {
            return "12257111123";
        } else {
            return "+12257111123";
        }
    }

    private String getIPExample(final IP ip) {
        final Set<IP.Version> versions = new HashSet<>(Arrays.asList(ip.value()));
        if (versions.contains(IP.Version.IP_V4)) {
            return "8.8.8.8";
        } else {
            return "2001:db8:85a3::8a2e:370:7334";
        }
    }

    private String getCountryCodeExample(final CountryCode countryCode) {
        if (countryCode.format() == CountryCode.Format.ISO_3166_1_ALPHA_2) {
            return "US";
        } else if (countryCode.format() == CountryCode.Format.ISO_3166_1_ALPHA_3) {
            return "USA";
        } else if (countryCode.format() == CountryCode.Format.ISO_3166_1_NUMERIC) {
            return "840";
        } else {
            throw new ImpossibleException("Unsupported CountryCode format: ?", countryCode.format());
        }
    }

    /**
     * @author nedis
     * @since 0.4
     */
    private interface StringValueExampleBuilder {

        Optional<String> build(RestModelField restModelField);
    }
}
