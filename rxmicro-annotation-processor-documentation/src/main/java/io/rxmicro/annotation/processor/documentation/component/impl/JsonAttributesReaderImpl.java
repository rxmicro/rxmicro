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
import io.rxmicro.annotation.processor.common.model.ModelField;
import io.rxmicro.annotation.processor.common.model.type.EnumModelClass;
import io.rxmicro.annotation.processor.documentation.component.JsonAttributesReader;
import io.rxmicro.annotation.processor.documentation.component.impl.reader.attribute.JsonArrayAttributesReader;
import io.rxmicro.annotation.processor.documentation.component.impl.reader.attribute.JsonNumberAttributesReader;
import io.rxmicro.annotation.processor.documentation.component.impl.reader.attribute.JsonStringAttributesReader;
import io.rxmicro.json.JsonObjectBuilder;
import io.rxmicro.validation.constraint.SubEnum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import static io.rxmicro.annotation.processor.common.util.Elements.getAllowedEnumConstants;
import static io.rxmicro.annotation.processor.common.util.Numbers.NUMBER_FORMATS;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class JsonAttributesReaderImpl implements JsonAttributesReader {

    private static final String FORMAT = "format";

    private static final String ENUM = "enum";

    private static final String DATE_TIME = "date-time";

    private final JsonStringAttributesReader jsonStringAttributesReader = new JsonStringAttributesReader();

    private final JsonNumberAttributesReader jsonNumberAttributesReader = new JsonNumberAttributesReader();

    private final JsonArrayAttributesReader jsonArrayAttributesReader = new JsonArrayAttributesReader();

    @Override
    public void readStringPrimitiveAttributes(final JsonObjectBuilder builder,
                                              final ModelField modelField) {
        jsonStringAttributesReader.read(builder, modelField);
    }

    @Override
    public void readDateTimePrimitiveAttributes(final JsonObjectBuilder builder,
                                                final ModelField modelField) {
        builder.put(FORMAT, DATE_TIME);
    }

    @Override
    public void readNumberPrimitiveAttributes(final JsonObjectBuilder builder,
                                              final ModelField modelField) {
        Optional.ofNullable(NUMBER_FORMATS.get(modelField.getFieldClass().toString())).ifPresent(t ->
                builder.put(FORMAT, t));
        jsonNumberAttributesReader.read(builder, modelField);
    }

    @Override
    public void readEnumAttributes(final JsonObjectBuilder builder,
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
    public void readArrayAttributes(final JsonObjectBuilder builder,
                                    final ModelField modelField) {
        jsonArrayAttributesReader.read(builder, modelField);
    }
}
