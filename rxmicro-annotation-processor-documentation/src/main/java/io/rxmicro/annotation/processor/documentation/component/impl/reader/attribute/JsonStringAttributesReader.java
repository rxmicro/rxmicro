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

package io.rxmicro.annotation.processor.documentation.component.impl.reader.attribute;

import io.rxmicro.annotation.processor.common.model.ModelField;
import io.rxmicro.json.JsonObjectBuilder;
import io.rxmicro.validation.constraint.DomainName;
import io.rxmicro.validation.constraint.Email;
import io.rxmicro.validation.constraint.EndsWith;
import io.rxmicro.validation.constraint.Enumeration;
import io.rxmicro.validation.constraint.HostName;
import io.rxmicro.validation.constraint.IP;
import io.rxmicro.validation.constraint.Length;
import io.rxmicro.validation.constraint.MaxLength;
import io.rxmicro.validation.constraint.MinLength;
import io.rxmicro.validation.constraint.Pattern;
import io.rxmicro.validation.constraint.Phone;
import io.rxmicro.validation.constraint.Skype;
import io.rxmicro.validation.constraint.StartsWith;
import io.rxmicro.validation.constraint.Telegram;
import io.rxmicro.validation.constraint.URI;
import io.rxmicro.validation.constraint.URLEncoded;
import io.rxmicro.validation.constraint.Viber;
import io.rxmicro.validation.constraint.WhatsApp;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

import static java.util.stream.Collectors.joining;

/**
 * @author nedis
 * @since 0.4
 */
public final class JsonStringAttributesReader {

    private static final String FORMAT = "format";

    private static final String PATTERN = "pattern";

    private static final String MIN_LENGTH = "minLength";

    private static final String MAX_LENGTH = "maxLength";

    private static final String ENUM = "enum";

    private static final String EMAIL = "email";

    private static final String PHONE = "phone";

    private static final String TELEGRAM = "telegram";

    private static final String VIBER = "viber";

    private static final String WHATSAPP = "whatsapp";

    private static final String SKYPE = "skype";

    private static final String URI = "uri";

    private static final String URL_ENCODED = "url-encoded";

    private static final String HOSTNAME = "hostname";

    private static final String DOMAIN_NAME = "domainName";

    private static final String STARTS_WITH = "startsWith";

    private static final String ENDS_WITH = "endsWith";

    private final List<BiConsumer<ModelField, JsonObjectBuilder>> biConsumers = List.of(

            (modelField, builder) -> {
                final Pattern pattern = modelField.getAnnotation(Pattern.class);
                if (pattern != null && !pattern.off()) {
                    builder.put(PATTERN, pattern.regexp());
                }
            },

            (modelField, builder) -> {
                final Length length = modelField.getAnnotation(Length.class);
                if (length != null && !length.off()) {
                    builder.put(MIN_LENGTH, length.value());
                    builder.put(MAX_LENGTH, length.value());
                }
            },

            (modelField, builder) -> {
                final MinLength minLength = modelField.getAnnotation(MinLength.class);
                if (minLength != null && !minLength.off()) {
                    builder.put(MIN_LENGTH, minLength.value());
                }
            },

            (modelField, builder) -> {
                final MaxLength maxLength = modelField.getAnnotation(MaxLength.class);
                if (maxLength != null && !maxLength.off()) {
                    builder.put(MAX_LENGTH, maxLength.value());
                }
            },

            (modelField, builder) -> {
                final Enumeration enumeration = modelField.getAnnotation(Enumeration.class);
                if (enumeration != null && !enumeration.off()) {
                    builder.put(ENUM, List.of(enumeration.value()));
                }
            },

            (modelField, builder) -> {
                final Email email = modelField.getAnnotation(Email.class);
                if (email != null && !email.off()) {
                    builder.put(FORMAT, EMAIL);
                }
            },

            (modelField, builder) -> {
                final IP ip = modelField.getAnnotation(IP.class);
                if (ip != null && !ip.off()) {
                    if (ip.value().length == 1) {
                        builder.put(FORMAT, ip.value()[0].getJsonFormat());
                    } else {
                        builder.put(FORMAT, Arrays.stream(ip.value()).map(IP.Version::getJsonFormat).collect(joining(", ")));
                    }
                }
            },

            (modelField, builder) -> {
                final Phone phone = modelField.getAnnotation(Phone.class);
                if (phone != null && !phone.off()) {
                    builder.put(FORMAT, PHONE);
                }
            },

            (modelField, builder) -> {
                final Telegram telegram = modelField.getAnnotation(Telegram.class);
                if (telegram != null && !telegram.off()) {
                    builder.put(FORMAT, TELEGRAM);
                }
            },

            (modelField, builder) -> {
                final Viber viber = modelField.getAnnotation(Viber.class);
                if (viber != null && !viber.off()) {
                    builder.put(FORMAT, VIBER);
                }
            },

            (modelField, builder) -> {
                final WhatsApp whatsApp = modelField.getAnnotation(WhatsApp.class);
                if (whatsApp != null && !whatsApp.off()) {
                    builder.put(FORMAT, WHATSAPP);
                }
            },

            (modelField, builder) -> {
                final Skype skype = modelField.getAnnotation(Skype.class);
                if (skype != null && !skype.off()) {
                    builder.put(FORMAT, SKYPE);
                }
            },

            (modelField, builder) -> {
                final URI uri = modelField.getAnnotation(URI.class);
                if (uri != null && !uri.off()) {
                    builder.put(FORMAT, URI);
                }
            },

            (modelField, builder) -> {
                final URLEncoded urlEncoded = modelField.getAnnotation(URLEncoded.class);
                if (urlEncoded != null && !urlEncoded.off()) {
                    builder.put(FORMAT, URL_ENCODED);
                }
            },

            (modelField, builder) -> {
                final HostName hostName = modelField.getAnnotation(HostName.class);
                if (hostName != null && !hostName.off()) {
                    builder.put(FORMAT, HOSTNAME);
                }
            },

            (modelField, builder) -> {
                final DomainName domainName = modelField.getAnnotation(DomainName.class);
                if (domainName != null && !domainName.off()) {
                    builder.put(FORMAT, DOMAIN_NAME);
                }
            },

            (modelField, builder) -> {
                final StartsWith startsWith = modelField.getAnnotation(StartsWith.class);
                if (startsWith != null && !startsWith.off()) {
                    builder.put(STARTS_WITH, startsWith.value());
                }
            },

            (modelField, builder) -> {
                final EndsWith endsWith = modelField.getAnnotation(EndsWith.class);
                if (endsWith != null && !endsWith.off()) {
                    builder.put(ENDS_WITH, endsWith.value());
                }
            }
    );

    public void read(final JsonObjectBuilder builder,
                     final ModelField modelField) {
        biConsumers.forEach(biConsumer -> biConsumer.accept(modelField, builder));
    }
}
