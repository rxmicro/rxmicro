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
import io.rxmicro.validation.constraint.Enumeration;
import io.rxmicro.validation.constraint.SubEnum;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.rxmicro.annotation.processor.common.util.Annotations.getReadMore;
import static io.rxmicro.annotation.processor.common.util.Elements.getAllowedEnumConstants;
import static io.rxmicro.common.util.ExCollectors.toOrderedSet;
import static io.rxmicro.common.util.Formats.format;

/**
 * @author nedis
 * @since 0.1
 */
public final class EnumPrimitiveConstraintReader implements ConstraintReader {

    private static final String RESTRICTION_TEMPLATE = "enum: ?";

    @Override
    public void readIfConstraintEnabled(final List<String> restrictions,
                                        final List<ReadMore> readMores,
                                        final Map.Entry<RestModelField, ModelClass> entry,
                                        final StringBuilder descriptionBuilder) {
        final RestModelField annotated = entry.getKey();
        final Enumeration enumeration = annotated.getAnnotation(Enumeration.class);
        if (enumeration != null && !enumeration.off()) {
            restrictions.add(format(RESTRICTION_TEMPLATE, Arrays.toString(enumeration.value())));
            getReadMore(Enumeration.class).ifPresent(readMores::add);
        }
        if (entry.getValue().isEnum()) {
            final Set<String> enumConstants = getAllowedEnumConstants(entry.getValue().asEnum().getTypeMirror());
            final SubEnum subEnum = annotated.getAnnotation(SubEnum.class);
            if (subEnum != null && !subEnum.off()) {
                if (subEnum.include().length > 0) {
                    restrictions.add(format(RESTRICTION_TEMPLATE, Arrays.toString(subEnum.include())));
                } else {
                    final Set<String> excludes = Set.of(subEnum.exclude());
                    restrictions.add(format(
                            RESTRICTION_TEMPLATE,
                            enumConstants.stream()
                                    .filter(e -> !excludes.contains(e))
                                    .collect(toOrderedSet())
                    ));
                }
            } else {
                restrictions.add(format(RESTRICTION_TEMPLATE, enumConstants));
            }
        }
    }
}
