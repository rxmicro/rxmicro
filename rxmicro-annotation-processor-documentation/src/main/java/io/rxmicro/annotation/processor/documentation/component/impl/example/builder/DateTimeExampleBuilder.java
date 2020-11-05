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
import io.rxmicro.validation.constraint.Future;
import io.rxmicro.validation.constraint.FutureOrPresent;
import io.rxmicro.validation.constraint.Past;
import io.rxmicro.validation.constraint.PastOrPresent;
import io.rxmicro.validation.constraint.TruncatedTime;

import java.time.Instant;
import java.time.ZoneOffset;
import javax.lang.model.type.TypeMirror;

import static io.rxmicro.annotation.processor.common.util.Types.SUPPORTED_DATE_TIME_CLASSES;
import static io.rxmicro.common.local.Examples.INSTANT_EXAMPLE;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class DateTimeExampleBuilder implements TypeExampleBuilder {

    private static final int TRUNCATED_MILLISECONDS = 1_000;

    private static final int TRUNCATED_SECONDS = 60 * TRUNCATED_MILLISECONDS;

    private static final int TRUNCATED_MINUTES = 60 * TRUNCATED_SECONDS;

    private static final int TRUNCATED_HOURS = 24 * TRUNCATED_MINUTES;

    private static final String NOT_IMPL_YET = "Not impl yet";

    private static final int YEARS_TO_ADD_IN_FUTURE = 200;

    @Override
    public boolean isSupported(final RestModelField restModelField,
                               final TypeMirror typeMirror) {
        return SUPPORTED_DATE_TIME_CLASSES.contains(typeMirror.toString());
    }

    @Override
    public String getExample(final RestModelField restModelField,
                             final TypeMirror typeMirror) {
        final Future future = restModelField.getAnnotation(Future.class);
        final FutureOrPresent futureOrPresent = restModelField.getAnnotation(FutureOrPresent.class);
        if ((future != null && !future.off()) || (futureOrPresent != null && !futureOrPresent.off())) {
            return getFuture(restModelField.getFieldClass());
        }
        final Past past = restModelField.getAnnotation(Past.class);
        final PastOrPresent pastOrPresent = restModelField.getAnnotation(PastOrPresent.class);
        if ((past != null && !past.off()) || (pastOrPresent != null && !pastOrPresent.off())) {
            return getPast(restModelField.getFieldClass());
        }
        final TruncatedTime truncatedTime = restModelField.getAnnotation(TruncatedTime.class);
        if (truncatedTime != null && !truncatedTime.off()) {
            return getTruncatedTime(restModelField.getFieldClass(), truncatedTime);
        }
        return getDefaultDateTime(restModelField.getFieldClass());
    }

    private String getTruncatedTime(final TypeMirror typeMirror,
                                    final TruncatedTime truncatedTime) {
        if (typeMirror.toString().equals(Instant.class.getName())) {
            long epochMilli = Instant.parse(INSTANT_EXAMPLE).toEpochMilli();
            if (truncatedTime.value() == TruncatedTime.Truncated.MILLIS) {
                epochMilli = (epochMilli / TRUNCATED_MILLISECONDS) * TRUNCATED_MILLISECONDS;
            } else if (truncatedTime.value() == TruncatedTime.Truncated.SECONDS) {
                epochMilli = (epochMilli / TRUNCATED_SECONDS) * TRUNCATED_SECONDS;
            } else if (truncatedTime.value() == TruncatedTime.Truncated.MINUTES) {
                epochMilli = (epochMilli / TRUNCATED_MINUTES) * TRUNCATED_MINUTES;
            } else {
                epochMilli = (epochMilli / TRUNCATED_HOURS) * TRUNCATED_HOURS;
            }
            return Instant.ofEpochMilli(epochMilli).toString();
        }
        throw new UnsupportedOperationException(NOT_IMPL_YET);
    }

    @SuppressWarnings("SameReturnValue")
    private String getFuture(final TypeMirror typeMirror) {
        if (typeMirror.toString().equals(Instant.class.getName())) {
            return Instant.parse(INSTANT_EXAMPLE)
                    .atOffset(ZoneOffset.UTC)
                    .plusYears(YEARS_TO_ADD_IN_FUTURE)
                    .toInstant()
                    .toString();
        }
        throw new UnsupportedOperationException(NOT_IMPL_YET);
    }

    @SuppressWarnings("SameReturnValue")
    private String getPast(final TypeMirror typeMirror) {
        if (typeMirror.toString().equals(Instant.class.getName())) {
            return INSTANT_EXAMPLE;
        }
        throw new UnsupportedOperationException(NOT_IMPL_YET);
    }

    @SuppressWarnings("SameReturnValue")
    private String getDefaultDateTime(final TypeMirror typeMirror) {
        if (typeMirror.toString().equals(Instant.class.getName())) {
            return INSTANT_EXAMPLE;
        }
        throw new UnsupportedOperationException(NOT_IMPL_YET);
    }
}
