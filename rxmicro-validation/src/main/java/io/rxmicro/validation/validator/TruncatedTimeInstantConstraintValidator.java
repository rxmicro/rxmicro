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

package io.rxmicro.validation.validator;

import io.rxmicro.common.ImpossibleException;
import io.rxmicro.http.error.ValidationException;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.validation.ConstraintValidator;
import io.rxmicro.validation.base.AbstractDateTimeEqualsConstraintValidator;
import io.rxmicro.validation.constraint.TruncatedTime;

import java.time.Instant;

import static io.rxmicro.validation.constraint.TruncatedTime.Truncated.HOURS;
import static io.rxmicro.validation.constraint.TruncatedTime.Truncated.MILLIS;
import static io.rxmicro.validation.constraint.TruncatedTime.Truncated.MINUTES;
import static io.rxmicro.validation.constraint.TruncatedTime.Truncated.SECONDS;

/**
 * Validator for the {@link io.rxmicro.validation.constraint.TruncatedTime} constraint.
 *
 * @author nedis
 * @see io.rxmicro.validation.constraint.TruncatedTime
 * @since 0.1
 */
public class TruncatedTimeInstantConstraintValidator extends AbstractDateTimeEqualsConstraintValidator
        implements ConstraintValidator<Instant> {

    private static final String ERROR_MESSAGE_TEMPLATE =
            "Invalid ? \"?\": Expected a time without ?, but actual is '?'!";

    private final TruncatedTime.Truncated truncated;

    /**
     * Creates the default instance of {@link TruncatedTimeInstantConstraintValidator} with the specified truncated mode.
     *
     * @param truncated the specified truncated mode
     */
    public TruncatedTimeInstantConstraintValidator(final TruncatedTime.Truncated truncated) {
        this.truncated = truncated;
    }

    @Override
    public void validate(final Instant actual,
                         final HttpModelType httpModelType,
                         final String modelName) {
        if (actual != null) {
            final long instantMillis = actual.toEpochMilli();
            if (truncated == MILLIS) {
                if (!isTruncatedToMillis(instantMillis)) {
                    throw new ValidationException(ERROR_MESSAGE_TEMPLATE, httpModelType, modelName, "milli seconds", actual);
                }
            } else if (truncated == SECONDS) {
                if (!isTruncatedToSeconds(instantMillis)) {
                    throw new ValidationException(ERROR_MESSAGE_TEMPLATE, httpModelType, modelName, "seconds", actual);
                }
            } else if (truncated == MINUTES) {
                if (!isTruncatedToMinutes(instantMillis)) {
                    throw new ValidationException(ERROR_MESSAGE_TEMPLATE, httpModelType, modelName, "minutes", actual);
                }
            } else if (truncated == HOURS) {
                if (!isTruncatedToHour(instantMillis)) {
                    throw new ValidationException(ERROR_MESSAGE_TEMPLATE, httpModelType, modelName, "hours", actual);
                }
            } else {
                throw new ImpossibleException("Unsupported truncated: ?", truncated);
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
