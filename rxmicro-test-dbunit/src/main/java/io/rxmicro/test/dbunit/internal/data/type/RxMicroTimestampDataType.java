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

package io.rxmicro.test.dbunit.internal.data.type;

import io.rxmicro.test.GlobalTestConfig;
import io.rxmicro.test.dbunit.internal.data.value.InstantIntervalValue;
import org.dbunit.dataset.datatype.AbstractDataType;
import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.datatype.TypeCastException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import static io.rxmicro.config.Configs.getConfig;
import static io.rxmicro.test.dbunit.internal.ExpressionValueResolvers.isExpressionValue;
import static io.rxmicro.test.dbunit.internal.ExpressionValueResolvers.resolveExpressionValue;
import static java.time.temporal.ChronoField.NANO_OF_SECOND;

/**
 * Unfortunately org.dbunit.dataset.datatype.TimestampDataType class has default constructor :(
 * So it is necessary to use composition instead of inheritance!
 *
 * @author nedis
 * @since 0.7
 */
public class RxMicroTimestampDataType extends AbstractDataType {

    private final DataType defaultTimestampDataType = DataType.TIMESTAMP;

    private TimeZone prevTimeZone;

    private Calendar calendar;

    public RxMicroTimestampDataType() {
        super("TIMESTAMP", Types.TIMESTAMP, Timestamp.class, false);
    }

    @Override
    public Object typeCast(final Object value) throws TypeCastException {
        if (isExpressionValue(value)) {
            return resolveExpressionValue(value);
        } else if (value instanceof Instant) {
            return Timestamp.from((Instant) value);
        } else if (value instanceof String) {
            return parseString((String) value);
        } else {
            return defaultTimestampDataType.typeCast(value);
        }
    }

    protected Object parseString(final String value) throws TypeCastException {
        try {
            // Try to parse as Instant
            return Timestamp.from(Instant.parse(value));
        } catch (final DateTimeParseException ignored) {
            return parseUsingConfiguredTimeZone(value);
        }
    }

    private Object parseUsingConfiguredTimeZone(final String value) throws TypeCastException {
        final DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd HH:mm:ss")
                .parseDefaulting(NANO_OF_SECOND, 0)
                .toFormatter()
                .withZone(getTimeZone().toZoneId());
        try {
            return Timestamp.from(dateTimeFormatter.parse(value, Instant::from));
        } catch (final DateTimeParseException ignored) {
            return defaultTimestampDataType.typeCast(value);
        }
    }

    @Override
    public boolean isDateTime() {
        return true;
    }

    @Override
    public Object getSqlValue(final int column,
                              final ResultSet resultSet) throws SQLException {
        final Timestamp value = resultSet.getTimestamp(column, getCalendar());
        if (value == null || resultSet.wasNull()) {
            return null;
        } else {
            return value;
        }
    }

    @Override
    public void setSqlValue(final Object value,
                            final int column,
                            final PreparedStatement statement) throws SQLException, TypeCastException {
        statement.setTimestamp(column, (Timestamp) typeCast(value), getCalendar());
    }

    private Calendar getCalendar() {
        final TimeZone oldPrevTimeZone = prevTimeZone;
        if (!getTimeZone().equals(oldPrevTimeZone)) {
            calendar = new GregorianCalendar(prevTimeZone);
        }
        return calendar;
    }

    private TimeZone getTimeZone() {
        final TimeZone timeZone = getConfig(GlobalTestConfig.class).getTimestampTimeZone();
        if (!timeZone.equals(prevTimeZone)) {
            prevTimeZone = timeZone;
        }
        return timeZone;
    }

    @Override
    protected int compareNonNulls(final Object value1,
                                  final Object value2) throws TypeCastException {
        if (value1 instanceof InstantIntervalValue) {
            return ((InstantIntervalValue) value1).compareTo(value2);
        } else if (value2 instanceof InstantIntervalValue) {
            return Math.negateExact(((InstantIntervalValue) value2).compareTo(value1));
        } else {
            return super.compareNonNulls(value1, value2);
        }
    }
}
