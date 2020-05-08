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

package io.rxmicro.annotation.processor.documentation.asciidoctor.component.impl.restrictions;

import io.rxmicro.annotation.processor.common.model.type.ModelClass;
import io.rxmicro.annotation.processor.rest.model.RestModelField;
import io.rxmicro.common.meta.ReadMore;
import io.rxmicro.validation.constraint.MaxSize;
import io.rxmicro.validation.constraint.MinSize;
import io.rxmicro.validation.constraint.NullableArrayItem;
import io.rxmicro.validation.constraint.Size;
import io.rxmicro.validation.constraint.UniqueItems;

import java.util.List;
import java.util.Map;

import static io.rxmicro.annotation.processor.common.util.Annotations.getReadMore;
import static io.rxmicro.common.util.Formats.format;

/**
 * @author nedis
 * @since 0.1
 */
public final class ArrayRestrictionReader {

    public void read(final List<String> restrictions,
                     final List<ReadMore> readMores,
                     final Map.Entry<RestModelField, ModelClass> entry) {
        final RestModelField annotated = entry.getKey();
        final MaxSize maxSize = annotated.getAnnotation(MaxSize.class);
        if (maxSize != null && !maxSize.off()) {
            restrictions.add(format("maxSize: ?", maxSize.value()));
            restrictions.add(format("exclusiveMaximum: ?", !maxSize.inclusive()));
            getReadMore(MaxSize.class).ifPresent(readMores::add);
        }
        final MinSize minSize = annotated.getAnnotation(MinSize.class);
        if (minSize != null && !minSize.off()) {
            restrictions.add(format("minSize: ?", minSize.value()));
            restrictions.add(format("exclusiveMinimum: ?", !minSize.inclusive()));
            getReadMore(MinSize.class).ifPresent(readMores::add);
        }
        final NullableArrayItem nonRequired = annotated.getAnnotation(NullableArrayItem.class);
        if (nonRequired != null && !nonRequired.off()) {
            restrictions.add("optionalItem: true");
            getReadMore(NullableArrayItem.class).ifPresent(readMores::add);
        }
        final Size size = annotated.getAnnotation(Size.class);
        if (size != null && !size.off()) {
            restrictions.add(format("size: ?", size.value()));
            getReadMore(Size.class).ifPresent(readMores::add);
        }
        final UniqueItems uniqueItems = annotated.getAnnotation(UniqueItems.class);
        if (uniqueItems != null && !uniqueItems.off()) {
            restrictions.add("uniqueItems: true");
            getReadMore(UniqueItems.class).ifPresent(readMores::add);
        }
    }
}
