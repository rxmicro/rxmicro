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

package io.rxmicro.annotation.processor.documentation.asciidoctor.component.impl.restrictions.primitive;

import io.rxmicro.annotation.processor.common.model.type.ModelClass;
import io.rxmicro.annotation.processor.documentation.asciidoctor.component.impl.restrictions.ConstraintReader;
import io.rxmicro.annotation.processor.rest.model.RestModelField;
import io.rxmicro.common.meta.ReadMore;
import io.rxmicro.validation.constraint.Base64URLEncoded;
import io.rxmicro.validation.constraint.CountryCode;
import io.rxmicro.validation.constraint.DigitsOnly;
import io.rxmicro.validation.constraint.Email;
import io.rxmicro.validation.constraint.HostName;
import io.rxmicro.validation.constraint.IP;
import io.rxmicro.validation.constraint.LatinAlphabetOnly;
import io.rxmicro.validation.constraint.Length;
import io.rxmicro.validation.constraint.Lowercase;
import io.rxmicro.validation.constraint.MaxLength;
import io.rxmicro.validation.constraint.MinLength;
import io.rxmicro.validation.constraint.Pattern;
import io.rxmicro.validation.constraint.Phone;
import io.rxmicro.validation.constraint.Skype;
import io.rxmicro.validation.constraint.Telegram;
import io.rxmicro.validation.constraint.URI;
import io.rxmicro.validation.constraint.URLEncoded;
import io.rxmicro.validation.constraint.Uppercase;
import io.rxmicro.validation.constraint.Viber;
import io.rxmicro.validation.constraint.WhatsApp;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static io.rxmicro.annotation.processor.common.util.Annotations.getReadMore;
import static io.rxmicro.common.util.Formats.format;
import static java.util.stream.Collectors.joining;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class StringPrimitiveConstraintReader implements ConstraintReader {

    @Override
    public void readIfConstraintEnabled(final List<String> restrictions,
                                        final List<ReadMore> readMores,
                                        final Map.Entry<RestModelField, ModelClass> entry,
                                        final StringBuilder descriptionBuilder) {
        final RestModelField annotated = entry.getKey();
        if (Character.class.getName().equals(annotated.getFieldClass().toString())) {
            restrictions.add("format: character");
        }
        final Base64URLEncoded base64URLEncoded = annotated.getAnnotation(Base64URLEncoded.class);
        if (base64URLEncoded != null && !base64URLEncoded.off()) {
            restrictions.add("format: base64Encoded");
            restrictions.add("alphabet: " + base64URLEncoded.alphabet());
            readMores.add(base64URLEncoded.alphabet().getReadMore());
        }
        final CountryCode countryCode = annotated.getAnnotation(CountryCode.class);
        if (countryCode != null && !countryCode.off()) {
            restrictions.add("format: countryCode");
            restrictions.add("ISO 3166-1: " + countryCode.format().getType());
            descriptionBuilder.append(countryCode.format().getDescription());
            readMores.add(countryCode.format().getReadMore());
        }
        final DigitsOnly digitsOnly = annotated.getAnnotation(DigitsOnly.class);
        if (digitsOnly != null && !digitsOnly.off()) {
            restrictions.add("format: digitsOnly");
            getReadMore(DigitsOnly.class).ifPresent(readMores::add);
        }
        final Email email = annotated.getAnnotation(Email.class);
        if (email != null && !email.off()) {
            restrictions.add("format: email");
            descriptionBuilder.append("Well-formed email address");
            getReadMore(Email.class).ifPresent(readMores::add);
        }
        final HostName hostName = annotated.getAnnotation(HostName.class);
        if (hostName != null && !hostName.off()) {
            restrictions.add("format: hostname");
            descriptionBuilder.append("Well-formed hostname");
            getReadMore(HostName.class).ifPresent(readMores::add);
        }
        final IP ip = annotated.getAnnotation(IP.class);
        if (ip != null && !ip.off()) {
            final IP.Version[] versions = ip.value();
            if (versions.length == 1) {
                restrictions.add("format: ip");
                restrictions.add("version: " + versions[0].getJsonFormat());
                descriptionBuilder.append("IP address of version ").append(versions[0].getVersion()).append('.');
                readMores.add(versions[0].getReadMore());
            } else {
                restrictions.add("format: ip");
                restrictions.add("version: [" + Arrays.stream(versions)
                        .map(IP.Version::getJsonFormat)
                        .collect(joining(", ")) + "]");
                descriptionBuilder.append("IP address of version 4 or 6.");
                Arrays.stream(versions).forEach(v -> readMores.add(v.getReadMore()));
            }
        }
        final LatinAlphabetOnly latinAlphabetOnly = annotated.getAnnotation(LatinAlphabetOnly.class);
        if (latinAlphabetOnly != null && !latinAlphabetOnly.off()) {
            restrictions.add("alphabet: latinOnly");
            restrictions.add("allowsUppercase: " + latinAlphabetOnly.allowsUppercase());
            restrictions.add("allowsLowercase: " + latinAlphabetOnly.allowsLowercase());
            restrictions.add("allowsDigits: " + latinAlphabetOnly.allowsDigits());
            restrictions.add("allowsPunctuations: " + (!latinAlphabetOnly.punctuations().isEmpty()));
            getReadMore(LatinAlphabetOnly.class).ifPresent(readMores::add);
        }
        final Length length = annotated.getAnnotation(Length.class);
        if (length != null && !length.off()) {
            restrictions.add(format("length: ?", length.value()));
            getReadMore(Length.class).ifPresent(readMores::add);
        }
        final Lowercase lowercase = annotated.getAnnotation(Lowercase.class);
        if (lowercase != null && !lowercase.off()) {
            restrictions.add("lowercase: true");
            getReadMore(Lowercase.class).ifPresent(readMores::add);
        }
        final MaxLength maxLength = annotated.getAnnotation(MaxLength.class);
        if (maxLength != null && !maxLength.off()) {
            restrictions.add(format("maxLength: ?", maxLength.value()));
            restrictions.add(format("exclusiveMaximum: ?", !maxLength.inclusive()));
            getReadMore(MaxLength.class).ifPresent(readMores::add);
        }
        final MinLength minLength = annotated.getAnnotation(MinLength.class);
        if (minLength != null && !minLength.off()) {
            restrictions.add(format("minLength: ?", minLength.value()));
            restrictions.add(format("exclusiveMinimum: ?", !minLength.inclusive()));
            getReadMore(MinLength.class).ifPresent(readMores::add);
        }
        final Pattern pattern = annotated.getAnnotation(Pattern.class);
        if (pattern != null && !pattern.off()) {
            restrictions.add("format: regex");
            restrictions.add(format("pattern: /?/", pattern.regexp()));
            restrictions.add(format("flags: ?", Arrays.toString(pattern.flags())));
            getReadMore(Pattern.class).ifPresent(readMores::add);
        }
        final Phone phone = annotated.getAnnotation(Phone.class);
        if (phone != null && !phone.off()) {
            restrictions.add("format: phone number");
            descriptionBuilder.append("Phone number in the international format.");
            getReadMore(Phone.class).ifPresent(readMores::add);
        }
        final Skype skype = annotated.getAnnotation(Skype.class);
        if (skype != null && !skype.off()) {
            restrictions.add("format: skype");
            descriptionBuilder.append("Skype name");
            getReadMore(Skype.class).ifPresent(readMores::add);
        }
        final Telegram telegram = annotated.getAnnotation(Telegram.class);
        if (telegram != null && !telegram.off()) {
            restrictions.add("format: telegram number");
            descriptionBuilder.append("Telegram number in the international format.");
            getReadMore(Telegram.class).ifPresent(readMores::add);
        }
        final Uppercase uppercase = annotated.getAnnotation(Uppercase.class);
        if (uppercase != null && !uppercase.off()) {
            restrictions.add("uppercase: true");
            getReadMore(Uppercase.class).ifPresent(readMores::add);
        }
        final URI uri = annotated.getAnnotation(URI.class);
        if (uri != null && !uri.off()) {
            restrictions.add("format: uri");
            getReadMore(URI.class).ifPresent(readMores::add);
        }
        final URLEncoded urlEncoded = annotated.getAnnotation(URLEncoded.class);
        if (urlEncoded != null && !urlEncoded.off()) {
            restrictions.add("format: urlEncoded");
            getReadMore(URLEncoded.class).ifPresent(readMores::add);
        }
        final Viber viber = annotated.getAnnotation(Viber.class);
        if (viber != null && !viber.off()) {
            restrictions.add("format: viber number");
            descriptionBuilder.append("Viber number in the international format.");
            getReadMore(Viber.class).ifPresent(readMores::add);
        }
        final WhatsApp whatsApp = annotated.getAnnotation(WhatsApp.class);
        if (whatsApp != null && !whatsApp.off()) {
            restrictions.add("format: whatsapp number");
            descriptionBuilder.append("Whatsapp number in the international format.");
            getReadMore(WhatsApp.class).ifPresent(readMores::add);
        }
    }
}
