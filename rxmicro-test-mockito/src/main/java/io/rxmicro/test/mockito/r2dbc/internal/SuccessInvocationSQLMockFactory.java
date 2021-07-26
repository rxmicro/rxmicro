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

package io.rxmicro.test.mockito.r2dbc.internal;

import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.spi.Result;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import io.rxmicro.test.local.InvalidTestConfigException;
import io.rxmicro.test.mockito.r2dbc.SQLQueryWithParamsMock;
import org.mockito.stubbing.Answer;
import org.mockito.stubbing.OngoingStubbing;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.BiFunction;

import static io.rxmicro.test.mockito.internal.CommonMatchers.any;
import static io.rxmicro.test.mockito.r2dbc.internal.AnyValues.ANY_MAP_RESULT_FUNCTION;
import static java.util.stream.Collectors.toList;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author nedis
 * @since 0.1
 */
@SuppressWarnings("unchecked")
public final class SuccessInvocationSQLMockFactory extends AbstractInvocationSQLMockFactory {

    public void prepare(final ConnectionPool connectionPool,
                        final SQLQueryWithParamsMock sqlQueryWithParamsMock,
                        final int rowsUpdated) {
        getResult(connectionPool, sqlQueryWithParamsMock)
                .ifPresent(result -> {
                    when(result.getRowsUpdated()).thenReturn(Mono.just(rowsUpdated));
                    lenient().when(result.map(any(BiFunction.class, ANY_MAP_RESULT_FUNCTION))).thenAnswer(invocation -> {
                        throw new InvalidTestConfigException(
                                "Use prepareSQLOperationMocks(ConnectionPool,SQLParamsMock,List<List<Object>>) method instead!"
                        );
                    });
                });
    }

    public void prepare(final ConnectionPool connectionPool,
                        final SQLQueryWithParamsMock sqlQueryWithParamsMock,
                        final List<List<Object>> resultSet) {
        getResult(connectionPool, sqlQueryWithParamsMock)
                .ifPresent(result -> {
                    final Row row = newRowMock(resultSet);
                    final RowMetadata rowMetadata = mock(RowMetadata.class);
                    when(result.map(any(BiFunction.class, ANY_MAP_RESULT_FUNCTION))).thenAnswer((Answer<Publisher<?>>) invocation -> {
                        final BiFunction<Row, RowMetadata, ?> biFunction = invocation.getArgument(0);
                        if (resultSet.isEmpty()) {
                            return Mono.empty();
                        } else if (resultSet.size() == 1) {
                            return Mono.just(biFunction.apply(row, rowMetadata));
                        } else {
                            return Flux.fromIterable(resultSet.stream().map(d -> biFunction.apply(row, rowMetadata)).collect(toList()));
                        }
                    });
                    lenient().when(result.getRowsUpdated()).thenAnswer(invocation -> {
                        throw new InvalidTestConfigException(
                                "Use prepareSQLOperationMocks(ConnectionPool,SQLParamsMock,int) method instead!"
                        );
                    });
                });
    }

    private Optional<Result> getResult(final ConnectionPool connectionPool,
                                       final SQLQueryWithParamsMock sqlQueryWithParamsMock) {
        validate(connectionPool);
        return newConnectionMock(connectionPool, sqlQueryWithParamsMock, null, null)
                .flatMap(connection -> newStatementMock(connection, sqlQueryWithParamsMock, null, null))
                .flatMap(statement -> newResultMock(statement, null, null));
    }

    private Row newRowMock(final List<List<Object>> resultSet) {
        final Row row = mock(Row.class);
        final List<List<Object>> convertedResultSet = convert(resultSet);
        for (int i = 0; i < convertedResultSet.size(); i++) {
            final List<Object> values = convertedResultSet.get(i);
            OngoingStubbing<?> stubbing = when(row.get(i, values.get(0).getClass()));
            for (final Object value : values) {
                stubbing = stubbing.thenAnswer((Answer<?>) invocation -> value);
            }
        }
        return row;
    }

    private List<List<Object>> convert(final List<List<Object>> resultSet) {
        final Map<Integer, List<Object>> values = new TreeMap<>();
        for (final List<Object> objects : resultSet) {
            for (int i = 0; i < objects.size(); i++) {
                values.computeIfAbsent(i, v -> new ArrayList<>())
                        .add(objects.get(i));
            }
        }
        return new ArrayList<>(values.values());
    }
}
