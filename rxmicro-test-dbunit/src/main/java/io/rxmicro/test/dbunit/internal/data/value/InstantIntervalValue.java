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

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Objects;

import static io.rxmicro.common.util.Formats.format;

/**
 * @author nedis
 * @since 0.7
 */
public final class InstantIntervalValue implements Comparable<Object> {

    private final Instant minInstant;

    private final Instant maxInstant;

    public InstantIntervalValue(final Instant minInstant,
                                final Instant maxInstant) {
        this.minInstant = minInstant;
        this.maxInstant = maxInstant;
    }

    public InstantIntervalValue(final Instant instant,
                                final Duration delta) {
        this.minInstant = instant.minusMillis(delta.toMillis());
        this.maxInstant = instant.plusMillis(delta.toMillis());
    }

    @Override
    public String toString() {
        return "An instant belonging to the interval: [" + minInstant + ", " + maxInstant + ']';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || (getClass() != o.getClass() && !(o instanceof Timestamp))) {
            return false;
        }
        return compareTo(o) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(minInstant, maxInstant);
    }

    @Override
    public int compareTo(final Object o) {
        if (o instanceof Timestamp) {
            return compareTo((Timestamp) o);
        } else if (o instanceof InstantIntervalValue) {
            return compareTo((InstantIntervalValue) o);
        } else if (o == null) {
            return 1;
        } else {
            throw new ClassCastException(format("Can't compare ? and ?", InstantIntervalValue.class, o.getClass()));
        }
    }

    public int compareTo(final Timestamp timestamp) {
        final Instant other = timestamp.toLocalDateTime().toInstant(ZoneOffset.UTC);
        if (minInstant.isAfter(other)) {
            return -1;
        } else if (maxInstant.isBefore(other)) {
            return 1;
        } else {
            return 0;
        }
    }

    public int compareTo(final InstantIntervalValue instantIntervalValue) {
        if (minInstant.isBefore(instantIntervalValue.minInstant)) {
            return -1;
        } else if (maxInstant.isAfter(instantIntervalValue.maxInstant)) {
            return 1;
        } else {
            return 0;
        }
    }
}
