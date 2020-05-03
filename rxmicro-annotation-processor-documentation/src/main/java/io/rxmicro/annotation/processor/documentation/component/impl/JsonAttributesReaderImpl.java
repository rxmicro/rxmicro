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

package io.rxmicro.annotation.processor.documentation.component.impl;

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.common.model.ModelField;
import io.rxmicro.annotation.processor.common.model.type.EnumModelClass;
import io.rxmicro.annotation.processor.documentation.component.JsonAttributesReader;
import io.rxmicro.json.JsonObjectBuilder;
import io.rxmicro.validation.constraint.Email;
import io.rxmicro.validation.constraint.Enumeration;
import io.rxmicro.validation.constraint.HostName;
import io.rxmicro.validation.constraint.IP;
import io.rxmicro.validation.constraint.Lat;
import io.rxmicro.validation.constraint.Length;
import io.rxmicro.validation.constraint.Lng;
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
import io.rxmicro.validation.constraint.Pattern;
import io.rxmicro.validation.constraint.Phone;
import io.rxmicro.validation.constraint.Size;
import io.rxmicro.validation.constraint.Skype;
import io.rxmicro.validation.constraint.SubEnum;
import io.rxmicro.validation.constraint.Telegram;
import io.rxmicro.validation.constraint.URI;
import io.rxmicro.validation.constraint.URLEncoded;
import io.rxmicro.validation.constraint.UniqueItems;
import io.rxmicro.validation.constraint.Viber;
import io.rxmicro.validation.constraint.WhatsApp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static io.rxmicro.annotation.processor.common.util.Elements.getAllowedEnumConstants;
import static io.rxmicro.annotation.processor.common.util.Numbers.NUMBER_FORMATS;
import static io.rxmicro.annotation.processor.common.util.Numbers.removeUnderscoresIfPresent;
import static java.util.stream.Collectors.joining;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@Singleton
public final class JsonAttributesReaderImpl implements JsonAttributesReader {

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

    private static final String DATE_TIME = "date-time";

    private static final String MINIMUM = "minimum";

    private static final String EXCLUSIVE_MINIMUM = "exclusiveMinimum";

    private static final String MAXIMUM = "maximum";

    private static final String EXCLUSIVE_MAXIMUM = "exclusiveMaximum";

    private static final String MIN_ITEMS = "minItems";

    private static final String MAX_ITEMS = "maxItems";

    private static final String UNIQUE_ITEMS = "uniqueItems";

    @Override
    public void readStringPrimitiveAttributes(final EnvironmentContext environmentContext,
                                              final JsonObjectBuilder builder,
                                              final ModelField modelField) {
        final Pattern pattern = modelField.getAnnotation(Pattern.class);
        if (pattern != null && !pattern.off()) {
            builder.put(PATTERN, pattern.regexp());
        }
        final Length length = modelField.getAnnotation(Length.class);
        if (length != null && !length.off()) {
            builder.put(MIN_LENGTH, length.value());
            builder.put(MAX_LENGTH, length.value());
        }
        final MinLength minLength = modelField.getAnnotation(MinLength.class);
        if (minLength != null && !minLength.off()) {
            builder.put(MIN_LENGTH, minLength.value());
        }
        final MaxLength maxLength = modelField.getAnnotation(MaxLength.class);
        if (maxLength != null && !maxLength.off()) {
            builder.put(MAX_LENGTH, maxLength.value());
        }

        final Enumeration enumeration = modelField.getAnnotation(Enumeration.class);
        if (enumeration != null && !enumeration.off()) {
            builder.put(ENUM, List.of(enumeration.value()));
            return;
        }
        final Email email = modelField.getAnnotation(Email.class);
        if (email != null) {
            builder.put(FORMAT, EMAIL);
            return;
        }
        final IP ip = modelField.getAnnotation(IP.class);
        if (ip != null) {
            if (ip.value().length == 1) {
                builder.put(FORMAT, ip.value()[0].getJsonFormat());
            } else {
                builder.put(FORMAT, Arrays.stream(ip.value()).map(IP.Version::getJsonFormat).collect(joining(", ")));
            }
            return;
        }
        final Phone phone = modelField.getAnnotation(Phone.class);
        if (phone != null) {
            builder.put(FORMAT, PHONE);
            return;
        }
        final Telegram telegram = modelField.getAnnotation(Telegram.class);
        if (telegram != null) {
            builder.put(FORMAT, TELEGRAM);
            return;
        }
        final Viber viber = modelField.getAnnotation(Viber.class);
        if (viber != null) {
            builder.put(FORMAT, VIBER);
            return;
        }
        final WhatsApp whatsApp = modelField.getAnnotation(WhatsApp.class);
        if (whatsApp != null) {
            builder.put(FORMAT, WHATSAPP);
            return;
        }
        final Skype skype = modelField.getAnnotation(Skype.class);
        if (skype != null) {
            builder.put(FORMAT, SKYPE);
            return;
        }
        final URI uri = modelField.getAnnotation(URI.class);
        if (uri != null) {
            builder.put(FORMAT, URI);
            return;
        }
        final URLEncoded urlEncoded = modelField.getAnnotation(URLEncoded.class);
        if (urlEncoded != null) {
            builder.put(FORMAT, URL_ENCODED);
            return;
        }
        final HostName hostName = modelField.getAnnotation(HostName.class);
        if (hostName != null) {
            builder.put(FORMAT, HOSTNAME);
        }
    }

    @Override
    public void readDateTimePrimitiveAttributes(final EnvironmentContext environmentContext,
                                                final JsonObjectBuilder builder,
                                                final ModelField modelField) {
        builder.put(FORMAT, DATE_TIME);
    }

    @Override
    public void readNumberPrimitiveAttributes(final EnvironmentContext environmentContext,
                                              final JsonObjectBuilder builder,
                                              final ModelField annotated) {
        Optional.ofNullable(NUMBER_FORMATS.get(annotated.getFieldClass().toString())).ifPresent(t ->
                builder.put(FORMAT, t));
        final Lat lat = annotated.getAnnotation(Lat.class);
        if (lat != null && !lat.off()) {
            builder.put(MINIMUM, -90);
            builder.put(EXCLUSIVE_MINIMUM, false);
            builder.put(MAXIMUM, 90);
            builder.put(EXCLUSIVE_MAXIMUM, false);
        }
        final Lng lng = annotated.getAnnotation(Lng.class);
        if (lng != null && !lng.off()) {
            builder.put(MINIMUM, -180);
            builder.put(EXCLUSIVE_MINIMUM, false);
            builder.put(MAXIMUM, 180);
            builder.put(EXCLUSIVE_MAXIMUM, false);
        }
        final MinDouble minDouble = annotated.getAnnotation(MinDouble.class);
        if (minDouble != null && !minDouble.off()) {
            builder.put(MINIMUM, minDouble.value());
            builder.put(EXCLUSIVE_MINIMUM, false);
        }
        final MinInt minInt = annotated.getAnnotation(MinInt.class);
        if (minInt != null && !minInt.off()) {
            builder.put(MINIMUM, minInt.value());
            builder.put(EXCLUSIVE_MINIMUM, !minInt.inclusive());
        }
        final MinNumber minNumber = annotated.getAnnotation(MinNumber.class);
        if (minNumber != null && !minNumber.off()) {
            builder.put(MINIMUM, new BigDecimal(removeUnderscoresIfPresent(minNumber.value())));
            builder.put(EXCLUSIVE_MINIMUM, !minNumber.inclusive());
        }
        final MaxDouble maxDouble = annotated.getAnnotation(MaxDouble.class);
        if (maxDouble != null && !maxDouble.off()) {
            builder.put(MAXIMUM, maxDouble.value());
            builder.put(EXCLUSIVE_MAXIMUM, false);
        }
        final MaxInt maxInt = annotated.getAnnotation(MaxInt.class);
        if (maxInt != null && !maxInt.off()) {
            builder.put(MAXIMUM, maxInt.value());
            builder.put(EXCLUSIVE_MAXIMUM, !maxInt.inclusive());
        }
        final MaxNumber maxNumber = annotated.getAnnotation(MaxNumber.class);
        if (maxNumber != null && !maxNumber.off()) {
            builder.put(MAXIMUM, new BigDecimal(removeUnderscoresIfPresent(maxNumber.value())));
            builder.put(EXCLUSIVE_MAXIMUM, !maxNumber.inclusive());
        }
    }

    @Override
    public void readEnumAttributes(final EnvironmentContext environmentContext,
                                   final JsonObjectBuilder builder,
                                   final ModelField modelField,
                                   final EnumModelClass enumModelClass) {
        final Set<String> enumConstants = getAllowedEnumConstants(enumModelClass.getTypeMirror());
        final SubEnum subEnum = modelField.getAnnotation(SubEnum.class);
        if (subEnum != null && !subEnum.off()) {
            if (subEnum.include().length > 0) {
                builder.put(ENUM, Arrays.asList(subEnum.include()));
            } else {
                final Set<String> enums = new LinkedHashSet<>(enumConstants);
                enums.removeAll(Arrays.asList(subEnum.exclude()));
                builder.put(ENUM, new ArrayList<>(enums));
            }
        } else {
            builder.put(ENUM, new ArrayList<>(enumConstants));
        }
    }

    @Override
    public void readArrayAttributes(final EnvironmentContext environmentContext,
                                    final JsonObjectBuilder builder,
                                    final ModelField modelField) {
        final Size size = modelField.getAnnotation(Size.class);
        if (size != null && !size.off()) {
            builder.put(MIN_ITEMS, size.value());
            builder.put(MAX_ITEMS, size.value());
        }
        final MinSize minSize = modelField.getAnnotation(MinSize.class);
        if (minSize != null && !minSize.off()) {
            builder.put(MIN_ITEMS, minSize.value());
        }
        final MaxSize maxSize = modelField.getAnnotation(MaxSize.class);
        if (maxSize != null && !maxSize.off()) {
            builder.put(MAX_ITEMS, maxSize.value());
        }
        final UniqueItems uniqueItems = modelField.getAnnotation(UniqueItems.class);
        if (uniqueItems != null && !uniqueItems.off()) {
            builder.put(UNIQUE_ITEMS, true);
        }
    }
}
