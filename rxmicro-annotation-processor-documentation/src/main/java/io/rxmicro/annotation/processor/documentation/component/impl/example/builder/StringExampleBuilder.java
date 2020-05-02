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
import io.rxmicro.rest.RequestId;
import io.rxmicro.validation.constraint.Base64URLEncoded;
import io.rxmicro.validation.constraint.CountryCode;
import io.rxmicro.validation.constraint.DigitsOnly;
import io.rxmicro.validation.constraint.Email;
import io.rxmicro.validation.constraint.HostName;
import io.rxmicro.validation.constraint.IP;
import io.rxmicro.validation.constraint.LatinAlphabetOnly;
import io.rxmicro.validation.constraint.Pattern;
import io.rxmicro.validation.constraint.Phone;
import io.rxmicro.validation.constraint.Skype;
import io.rxmicro.validation.constraint.Telegram;
import io.rxmicro.validation.constraint.URI;
import io.rxmicro.validation.constraint.URLEncoded;
import io.rxmicro.validation.constraint.Uppercase;
import io.rxmicro.validation.constraint.Viber;
import io.rxmicro.validation.constraint.WhatsApp;

import javax.lang.model.type.TypeMirror;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static io.rxmicro.http.HttpHeaders.REQUEST_ID;
import static io.rxmicro.rest.RequestId.REQUEST_ID_EXAMPLE;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@Singleton
public final class StringExampleBuilder implements TypeExampleBuilder {

    @Override
    public boolean isSupported(final RestModelField restModelField,
                               final TypeMirror typeMirror) {
        return String.class.getName().equals(typeMirror.toString());
    }

    @Override
    public String getExample(final RestModelField restModelField,
                             final TypeMirror typeMirror) {
        final Base64URLEncoded base64URLEncoded = restModelField.getAnnotation(Base64URLEncoded.class);
        if (base64URLEncoded != null && !base64URLEncoded.off()) {
            return "SGVsbG8gd29ybGQh";
        }
        final CountryCode countryCode = restModelField.getAnnotation(CountryCode.class);
        if (countryCode != null && !countryCode.off()) {
            return getCountryCodeExample(countryCode);
        }
        final DigitsOnly digitsOnly = restModelField.getAnnotation(DigitsOnly.class);
        if (digitsOnly != null && !digitsOnly.off()) {
            return "9876";
        }
        final Email email = restModelField.getAnnotation(Email.class);
        if (email != null && !email.off()) {
            return "welcome@rxmicro.io";
        }
        final HostName hostName = restModelField.getAnnotation(HostName.class);
        if (hostName != null && !hostName.off()) {
            return "rxmicro.io";
        }
        final IP ip = restModelField.getAnnotation(IP.class);
        if (ip != null && !ip.off()) {
            return getIPExample(ip);
        }
        final LatinAlphabetOnly latinAlphabetOnly = restModelField.getAnnotation(LatinAlphabetOnly.class);
        if (latinAlphabetOnly != null && !latinAlphabetOnly.off()) {
            return "Latin alphabet only!";
        }
        final Phone phone = restModelField.getAnnotation(Phone.class);
        if (phone != null && !phone.off()) {
            return getPhoneExample(phone.withoutPlus());
        }
        final Skype skype = restModelField.getAnnotation(Skype.class);
        if (skype != null && !skype.off()) {
            return "rxmicro.io";
        }
        final Telegram telegram = restModelField.getAnnotation(Telegram.class);
        if (telegram != null && !telegram.off()) {
            return getTelegramExample(telegram.withoutPlus());
        }
        final Uppercase uppercase = restModelField.getAnnotation(Uppercase.class);
        if (uppercase != null && !uppercase.off()) {
            return "STRING";
        }
        final URI uri = restModelField.getAnnotation(URI.class);
        if (uri != null && !uri.off()) {
            return "https://rxmicro.io";
        }
        final URLEncoded urlEncoded = restModelField.getAnnotation(URLEncoded.class);
        if (urlEncoded != null && !urlEncoded.off()) {
            return "Hello%20world%21";
        }
        final Viber viber = restModelField.getAnnotation(Viber.class);
        if (viber != null && !viber.off()) {
            return getViberExample(viber.withoutPlus());
        }
        final WhatsApp whatsApp = restModelField.getAnnotation(WhatsApp.class);
        if (whatsApp != null && !whatsApp.off()) {
            return getWhatsappExample(whatsApp.withoutPlus());
        }
        final Pattern pattern = restModelField.getAnnotation(Pattern.class);
        if (pattern != null && !pattern.off()) {
            return pattern.regexp();
        }
        if (restModelField.getAnnotation(RequestId.class) != null) {
            return REQUEST_ID_EXAMPLE;
        }
        if (restModelField.isHttpHeader() && REQUEST_ID.equalsIgnoreCase(restModelField.getModelName())) {
            return REQUEST_ID_EXAMPLE;
        }
        return "string";
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
            throw new UnsupportedOperationException(
                    "Unsupported CountryCode format: " + countryCode.format());
        }
    }
}
