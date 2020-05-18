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
import io.rxmicro.validation.constraint.AssertFalse;
import io.rxmicro.validation.constraint.AssertTrue;

import java.util.List;
import java.util.Map;

import static io.rxmicro.annotation.processor.common.util.Annotations.getReadMore;

/**
 * @author nedis
 * @since 0.1
 */
public final class BooleanPrimitiveConstraintReader extends ConstraintReader {

    private final List<AnnotationConstraintReader> annotationConstraintReaders = List.of(

            (annotated, restrictions, readMores, descriptionBuilder) -> {
                final AssertTrue assertTrue = annotated.getAnnotation(AssertTrue.class);
                if (assertTrue != null && !assertTrue.off()) {
                    restrictions.add("expected: true");
                    descriptionBuilder.append("Must be `true`.");
                    getReadMore(AssertTrue.class).ifPresent(readMores::add);
                }
            },

            (annotated, restrictions, readMores, descriptionBuilder) -> {
                final AssertFalse assertFalse = annotated.getAnnotation(AssertFalse.class);
                if (assertFalse != null && !assertFalse.off()) {
                    restrictions.add("expected: false");
                    descriptionBuilder.append("Must be `false`.");
                    getReadMore(AssertFalse.class).ifPresent(readMores::add);
                }
            }
    );

    @Override
    public void readIfConstraintEnabled(final Map.Entry<RestModelField, ModelClass> entry,
                                        final List<String> restrictions,
                                        final List<ReadMore> readMores,
                                        final StringBuilder descriptionBuilder) {
        final RestModelField annotated = entry.getKey();
        readUsingAnnotationConstraintReader(annotationConstraintReaders, annotated, restrictions, readMores, descriptionBuilder);
    }
}
