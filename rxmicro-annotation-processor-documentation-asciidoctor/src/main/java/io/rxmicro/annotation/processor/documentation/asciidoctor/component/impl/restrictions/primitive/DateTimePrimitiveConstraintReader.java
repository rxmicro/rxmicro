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
import io.rxmicro.common.ImpossibleException;
import io.rxmicro.common.meta.ReadMore;
import io.rxmicro.validation.constraint.Future;
import io.rxmicro.validation.constraint.FutureOrPresent;
import io.rxmicro.validation.constraint.Past;
import io.rxmicro.validation.constraint.PastOrPresent;
import io.rxmicro.validation.constraint.TruncatedTime;

import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static io.rxmicro.annotation.processor.common.util.Annotations.getReadMore;

/**
 * @author nedis
 * @since 0.1
 */
public final class DateTimePrimitiveConstraintReader extends ConstraintReader {

    private final List<AnnotationConstraintReader> annotationConstraintReaders = List.of(

            (annotated, restrictions, readMores, descriptionBuilder) -> {
                final Future future = annotated.getAnnotation(Future.class);
                if (future != null && !future.off()) {
                    restrictions.add("future: true");
                    getReadMore(Future.class).ifPresent(readMores::add);
                }
            },

            (annotated, restrictions, readMores, descriptionBuilder) -> {
                final FutureOrPresent futureOrPresent = annotated.getAnnotation(FutureOrPresent.class);
                if (futureOrPresent != null && !futureOrPresent.off()) {
                    restrictions.add("future: true");
                    restrictions.add("present: true");
                    getReadMore(FutureOrPresent.class).ifPresent(readMores::add);
                }
            },

            (annotated, restrictions, readMores, descriptionBuilder) -> {
                final Past past = annotated.getAnnotation(Past.class);
                if (past != null && !past.off()) {
                    restrictions.add("past: true");
                    getReadMore(Past.class).ifPresent(readMores::add);
                }
            },

            (annotated, restrictions, readMores, descriptionBuilder) -> {
                final PastOrPresent pastOrPresent = annotated.getAnnotation(PastOrPresent.class);
                if (pastOrPresent != null && !pastOrPresent.off()) {
                    restrictions.add("past: true");
                    restrictions.add("present: true");
                    getReadMore(PastOrPresent.class).ifPresent(readMores::add);
                }
            },

            (annotated, restrictions, readMores, descriptionBuilder) -> {
                final TruncatedTime truncatedTime = annotated.getAnnotation(TruncatedTime.class);
                if (truncatedTime != null && !truncatedTime.off()) {
                    restrictions.add("truncated: " + truncatedTime.value().name().toLowerCase(Locale.ENGLISH));
                    getReadMore(TruncatedTime.class).ifPresent(readMores::add);
                }
            }
    );

    @ReadMore(
            caption = "What is UTC?",
            link = "https://en.wikipedia.org/wiki/Coordinated_Universal_Time"
    )
    private Instant instant;

    @Override
    public void readIfConstraintEnabled(final Map.Entry<RestModelField, ModelClass> entry,
                                        final List<String> restrictions,
                                        final List<ReadMore> readMores,
                                        final StringBuilder descriptionBuilder) {
        final RestModelField annotated = entry.getKey();
        addTypeFormat(restrictions, readMores, annotated);
        readUsingAnnotationConstraintReader(annotationConstraintReaders, annotated, restrictions, readMores, descriptionBuilder);
    }

    private void addTypeFormat(final List<String> restrictions,
                               final List<ReadMore> readMores,
                               final RestModelField annotated) {
        if (Instant.class.getName().equals(annotated.getFieldClass().toString())) {
            restrictions.add("format: UTC");
            try {
                readMores.add(getClass().getDeclaredField("instant").getAnnotation(ReadMore.class));
            } catch (final NoSuchFieldException ex) {
                throw new ImpossibleException(ex, "Field `instant` must be declared!");
            }
        }
    }
}
