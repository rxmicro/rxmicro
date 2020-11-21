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

package io.rxmicro.test.dbunit.internal.data.value;

import java.util.Objects;

import static io.rxmicro.common.util.Formats.format;

/**
 * @author nedis
 * @since 0.7
 */
public final class LongIntervalValue implements Comparable<Object> {

    private final long min;

    private final long max;

    public LongIntervalValue(final long min,
                             final long max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public String toString() {
        return "An integer belonging to the interval: [" + min + ", " + max + ']';
    }

    @Override
    public int compareTo(final Object o) {
        if (o instanceof Number) {
            return compareTo((Number) o);
        } else if (o instanceof LongIntervalValue) {
            return compareTo((LongIntervalValue) o);
        } else if (o == null) {
            return 1;
        } else {
            throw new ClassCastException(format("Can't compare ? and ?", LongIntervalValue.class, o.getClass()));
        }
    }

    public int compareTo(final Number number) {
        final long value = number.longValue();
        if (min > value) {
            return -1;
        } else if (max < value) {
            return 1;
        } else {
            return 0;
        }
    }

    public int compareTo(final LongIntervalValue value) {
        if (min > value.min) {
            return -1;
        } else if (max < value.max) {
            return 1;
        } else {
            return 0;
        }
    }
}
