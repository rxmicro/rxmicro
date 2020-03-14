/*
 * Copyright (c) 2020. http://rxmicro.io
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
import static io.rxmicro.annotation.processor.common.util.Numbers.convertIfNecessary;
import static java.util.stream.Collectors.joining;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
@Singleton
public final class JsonAttributesReaderImpl implements JsonAttributesReader {

    private static final String FORMAT = "format";

    @Override
    public void readStringPrimitiveAttributes(final EnvironmentContext environmentContext,
                                              final JsonObjectBuilder builder,
                                              final ModelField modelField) {
        final Pattern pattern = modelField.getAnnotation(Pattern.class);
        if (pattern != null && !pattern.off()) {
            builder.put("pattern", pattern.regexp());
        }
        final Length length = modelField.getAnnotation(Length.class);
        if (length != null && !length.off()) {
            builder.put("minLength", length.value());
            builder.put("maxLength", length.value());
        }
        final MinLength minLength = modelField.getAnnotation(MinLength.class);
        if (minLength != null && !minLength.off()) {
            builder.put("minLength", minLength.value());
        }
        final MaxLength maxLength = modelField.getAnnotation(MaxLength.class);
        if (maxLength != null && !maxLength.off()) {
            builder.put("maxLength", maxLength.value());
        }

        final Enumeration enumeration = modelField.getAnnotation(Enumeration.class);
        if (enumeration != null && !enumeration.off()) {
            builder.put("enum", List.of(enumeration.value()));
            return;
        }
        final Email email = modelField.getAnnotation(Email.class);
        if (email != null) {
            builder.put(FORMAT, "email");
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
            builder.put(FORMAT, "phone");
            return;
        }
        final Telegram telegram = modelField.getAnnotation(Telegram.class);
        if (telegram != null) {
            builder.put(FORMAT, "telegram");
            return;
        }
        final Viber viber = modelField.getAnnotation(Viber.class);
        if (viber != null) {
            builder.put(FORMAT, "viber");
            return;
        }
        final WhatsApp whatsApp = modelField.getAnnotation(WhatsApp.class);
        if (whatsApp != null) {
            builder.put(FORMAT, "whatsapp");
            return;
        }
        final Skype skype = modelField.getAnnotation(Skype.class);
        if (skype != null) {
            builder.put(FORMAT, "skype");
            return;
        }
        final URI uri = modelField.getAnnotation(URI.class);
        if (uri != null) {
            builder.put(FORMAT, "uri");
            return;
        }
        final URLEncoded urlEncoded = modelField.getAnnotation(URLEncoded.class);
        if (urlEncoded != null) {
            builder.put(FORMAT, "url-encoded");
            return;
        }
        final HostName hostName = modelField.getAnnotation(HostName.class);
        if (hostName != null) {
            builder.put(FORMAT, "hostname");
        }
    }

    @Override
    public void readDateTimePrimitiveAttributes(final EnvironmentContext environmentContext,
                                                final JsonObjectBuilder builder,
                                                final ModelField modelField) {
        builder.put(FORMAT, "date-time");
    }

    @Override
    public void readNumberPrimitiveAttributes(final EnvironmentContext environmentContext,
                                              final JsonObjectBuilder builder,
                                              final ModelField annotated) {
        Optional.ofNullable(NUMBER_FORMATS.get(annotated.getFieldClass().toString())).ifPresent(t ->
                builder.put(FORMAT, t));
        final Lat lat = annotated.getAnnotation(Lat.class);
        if (lat != null && !lat.off()) {
            builder.put("minimum", -90);
            builder.put("exclusiveMinimum", false);
            builder.put("maximum", 90);
            builder.put("exclusiveMaximum", false);
        }
        final Lng lng = annotated.getAnnotation(Lng.class);
        if (lng != null && !lng.off()) {
            builder.put("minimum", -180);
            builder.put("exclusiveMinimum", false);
            builder.put("maximum", 180);
            builder.put("exclusiveMaximum", false);
        }
        final MinDouble minDouble = annotated.getAnnotation(MinDouble.class);
        if (minDouble != null && !minDouble.off()) {
            builder.put("minimum", minDouble.value());
            builder.put("exclusiveMinimum", false);
        }
        final MinInt minInt = annotated.getAnnotation(MinInt.class);
        if (minInt != null && !minInt.off()) {
            builder.put("minimum", minInt.value());
            builder.put("exclusiveMinimum", !minInt.inclusive());
        }
        final MinNumber minNumber = annotated.getAnnotation(MinNumber.class);
        if (minNumber != null && !minNumber.off()) {
            builder.put("minimum", new BigDecimal(convertIfNecessary(minNumber.value())));
            builder.put("exclusiveMinimum", !minNumber.inclusive());
        }
        final MaxDouble maxDouble = annotated.getAnnotation(MaxDouble.class);
        if (maxDouble != null && !maxDouble.off()) {
            builder.put("maximum", maxDouble.value());
            builder.put("exclusiveMaximum", false);
        }
        final MaxInt maxInt = annotated.getAnnotation(MaxInt.class);
        if (maxInt != null && !maxInt.off()) {
            builder.put("maximum", maxInt.value());
            builder.put("exclusiveMaximum", !maxInt.inclusive());
        }
        final MaxNumber maxNumber = annotated.getAnnotation(MaxNumber.class);
        if (maxNumber != null && !maxNumber.off()) {
            builder.put("maximum", new BigDecimal(convertIfNecessary(maxNumber.value())));
            builder.put("exclusiveMaximum", !maxNumber.inclusive());
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
                builder.put("enum", Arrays.asList(subEnum.include()));
            } else {
                final Set<String> enums = new LinkedHashSet<>(enumConstants);
                enums.removeAll(Arrays.asList(subEnum.exclude()));
                builder.put("enum", new ArrayList<>(enums));
            }
        } else {
            builder.put("enum", new ArrayList<>(enumConstants));
        }
    }

    @Override
    public void readArrayAttributes(final EnvironmentContext environmentContext,
                                    final JsonObjectBuilder builder,
                                    final ModelField modelField) {
        final Size size = modelField.getAnnotation(Size.class);
        if (size != null && !size.off()) {
            builder.put("minItems", size.value());
            builder.put("maxItems", size.value());
        }
        final MinSize minSize = modelField.getAnnotation(MinSize.class);
        if (minSize != null && !minSize.off()) {
            builder.put("minItems", minSize.value());
        }
        final MaxSize maxSize = modelField.getAnnotation(MaxSize.class);
        if (maxSize != null && !maxSize.off()) {
            builder.put("maxItems", maxSize.value());
        }
        final UniqueItems uniqueItems = modelField.getAnnotation(UniqueItems.class);
        if (uniqueItems != null && !uniqueItems.off()) {
            builder.put("uniqueItems", true);
        }
    }
}
