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

import io.rxmicro.test.dbunit.internal.data.value.LongIntervalValue;
import org.dbunit.dataset.datatype.AbstractDataType;
import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.datatype.TypeCastException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static io.rxmicro.test.dbunit.internal.ExpressionValueResolvers.isExpressionValue;
import static io.rxmicro.test.dbunit.internal.ExpressionValueResolvers.resolveExpressionValue;

/**
 * Unfortunately org.dbunit.dataset.datatype.IntegerDataType class has default constructor :(
 * So it is necessary to use composition instead of inheritance!
 *
 * @author nedis
 * @since 0.7
 */
public class RxMicroIntegerDataType extends AbstractDataType {

    private final DataType defaultIntegerDataType = DataType.INTEGER;

    public RxMicroIntegerDataType(final String name,
                                  final int sqlType) {
        super(name, sqlType, Integer.class, true);
    }

    @Override
    public Object typeCast(final Object value) throws TypeCastException {
        if (isExpressionValue(value)) {
            return resolveExpressionValue(value);
        } else {
            return defaultIntegerDataType.typeCast(value);
        }
    }

    public Object getSqlValue(final int column,
                              final ResultSet resultSet) throws SQLException {
        final int value = resultSet.getInt(column);
        if (resultSet.wasNull()) {
            return null;
        } else {
            return value;
        }
    }

    public void setSqlValue(final Object value,
                            final int column,
                            final PreparedStatement statement) throws SQLException, TypeCastException {
        statement.setInt(column, ((Integer) typeCast(value)));
    }

    @Override
    protected int compareNonNulls(final Object value1,
                                  final Object value2) throws TypeCastException {
        if (value1 instanceof LongIntervalValue) {
            return ((LongIntervalValue) value1).compareTo(value2);
        } else if (value2 instanceof LongIntervalValue) {
            return Math.negateExact(((LongIntervalValue) value2).compareTo(value1));
        } else {
            return super.compareNonNulls(value1, value2);
        }
    }
}
