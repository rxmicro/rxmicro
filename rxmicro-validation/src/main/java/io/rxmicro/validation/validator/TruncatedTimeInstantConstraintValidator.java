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

package io.rxmicro.validation.validator;

import io.rxmicro.http.error.ValidationException;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.validation.ConstraintValidator;
import io.rxmicro.validation.base.AbstractDateTimeEqualsConstraintValidator;
import io.rxmicro.validation.constraint.TruncatedTime;

import java.time.Instant;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @see io.rxmicro.validation.constraint.TruncatedTime
 * @since 0.1
 */
public class TruncatedTimeInstantConstraintValidator extends AbstractDateTimeEqualsConstraintValidator
        implements ConstraintValidator<Instant> {

    private final TruncatedTime.Truncated truncated;

    public TruncatedTimeInstantConstraintValidator(final TruncatedTime.Truncated truncated) {
        this.truncated = truncated;
    }

    @Override
    public void validate(final Instant actual,
                         final HttpModelType httpModelType,
                         final String modelName) throws ValidationException {
        if (actual != null) {
            final long instantMillis = actual.toEpochMilli();
            switch (truncated) {
                case MILLIS: {
                    if (!isTruncatedToMillis(instantMillis)) {
                        throw new ValidationException(
                                "Invalid ? \"?\": Expected a time without milli seconds, but actual is '?'!",
                                httpModelType, modelName, actual);
                    }
                }
                case SECONDS: {
                    if (!isTruncatedToSeconds(instantMillis)) {
                        throw new ValidationException(
                                "Invalid ? \"?\": Expected a time without milli seconds, but actual is '?'!",
                                httpModelType, modelName, actual);
                    }
                }
                case MINUTES: {
                    if (!isTruncatedToMinutes(instantMillis)) {
                        throw new ValidationException(
                                "Invalid ? \"?\": Expected a time without milli seconds, but actual is '?'!",
                                httpModelType, modelName, actual);
                    }
                }
                case HOURS: {
                    if (!isTruncatedToHour(instantMillis)) {
                        throw new ValidationException(
                                "Invalid ? \"?\": Expected a time without milli seconds, but actual is '?'!",
                                httpModelType, modelName, actual);
                    }
                }
            }
        }
    }

    private boolean isTruncatedToMillis(final long instant) {
        return instant % TRUNCATED_MILLISECONDS == 0;
    }

    private boolean isTruncatedToSeconds(final long instant) {
        return instant % TRUNCATED_SECONDS == 0;
    }

    private boolean isTruncatedToMinutes(final long instant) {
        return instant % TRUNCATED_MINUTES == 0;
    }

    private boolean isTruncatedToHour(final long instant) {
        return instant % TRUNCATED_HOURS == 0;
    }
}
