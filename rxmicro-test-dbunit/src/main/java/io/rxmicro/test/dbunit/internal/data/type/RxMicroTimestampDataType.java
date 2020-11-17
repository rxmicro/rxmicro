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

import org.dbunit.dataset.datatype.AbstractDataType;
import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.datatype.TypeCastException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.Instant;

import static io.rxmicro.test.dbunit.internal.ExpressionValueResolvers.isExpressionValue;
import static io.rxmicro.test.dbunit.internal.ExpressionValueResolvers.resolveExpressionValue;

/**
 * Unfortunately org.dbunit.dataset.datatype.TimestampDataType class has default constructor :(
 * So it is necessary to use composition instead of inheritance!
 *
 * @author nedis
 * @since 0.7
 */
public class RxMicroTimestampDataType extends AbstractDataType {

    private final DataType defaultTimestampDataType = DataType.TIMESTAMP;

    public RxMicroTimestampDataType() {
        super("TIMESTAMP", Types.TIMESTAMP, Timestamp.class, false);
    }

    @Override
    public Object typeCast(final Object value) throws TypeCastException {
        if (isExpressionValue(value)) {
            return resolveExpressionValue(value);
        } else if (value instanceof Instant) {
            return Timestamp.from((Instant) value);
        } else {
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
        final Timestamp value = resultSet.getTimestamp(column);
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
        statement.setTimestamp(column, (java.sql.Timestamp) typeCast(value));
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
