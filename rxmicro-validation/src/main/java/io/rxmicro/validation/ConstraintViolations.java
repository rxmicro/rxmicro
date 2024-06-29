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

package io.rxmicro.validation;

import io.rxmicro.common.util.Formats;
import io.rxmicro.validation.local.ConstraintViolationReportHelper;

/**
 * Contains util methods that make decision about how the occurred violation should be handled.
 *
 * @author nedis
 * @since 0.12
 */
public final class ConstraintViolations {

    /**
     * Reports the occurred violation.
     * <p>{@code RxMicro} framework will handle this violation based on its configuration:
     * <ul>
     *     <li>A violation can be thrown as an {@link Exception};</li>
     *     <li>A violation can be logged into {@code stderr};</li>
     *     <li>A violation can be added to some batch, and then this batch will handled as single violation;</li>
     *     <li>etc.</li>
     * </ul>
     *
     * <p>
     * <i>(FYI: If {@code args} parameter is defined, then the {@link Formats#format(String, Object...)} method will be
     * used to format a final error message.)</i>
     *
     * @param message the error message template
     * @param args    the error message template argument
     */
    public static void reportViolation(final String message,
                                       final Object... args) {
        ConstraintViolationReportHelper.reportViolation(message, args);
    }

    private ConstraintViolations() {
    }
}
