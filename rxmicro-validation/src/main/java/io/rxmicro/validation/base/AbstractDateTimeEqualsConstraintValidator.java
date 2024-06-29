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

package io.rxmicro.validation.base;

/**
 * Base validator class for date and time constraints.
 *
 * @author nedis
 * @since 0.1
 */
public abstract class AbstractDateTimeEqualsConstraintValidator {

    /**
     * Millis in 1 second.
     */
    protected static final int TRUNCATED_MILLISECONDS = 1_000;

    /**
     * Millis in 1 minute.
     */
    protected static final int TRUNCATED_SECONDS = 60 * TRUNCATED_MILLISECONDS;

    /**
     * Millis in 1 hours.
     */
    protected static final int TRUNCATED_MINUTES = 60 * TRUNCATED_SECONDS;

    /**
     * Millis in 24 hours.
     */
    protected static final int TRUNCATED_HOURS = 24 * TRUNCATED_MINUTES;

    private static final int[] TRUNCATED_CHARACTERISTICS = {
            TRUNCATED_MILLISECONDS,
            TRUNCATED_SECONDS,
            TRUNCATED_MINUTES,
            TRUNCATED_HOURS
    };

    /**
     * Returns {@code true} if values are not equals after truncation.
     *
     * @param expectedDirtyTimeInMillis expected time in millis without truncation
     * @param actualClearTimeInMillis   actual time in millis after truncation
     * @return {@code true} if values are not equals after truncation
     */
    protected boolean isNotEqualsAfterTruncation(final long expectedDirtyTimeInMillis,
                                                 final long actualClearTimeInMillis) {
        long expectedClearTimeInMillis = expectedDirtyTimeInMillis;
        for (final int characteristic : TRUNCATED_CHARACTERISTICS) {
            if (actualClearTimeInMillis % characteristic == 0) {
                expectedClearTimeInMillis = (expectedDirtyTimeInMillis / characteristic) * characteristic;
            } else {
                break;
            }
        }
        return expectedClearTimeInMillis != actualClearTimeInMillis;
    }
}
