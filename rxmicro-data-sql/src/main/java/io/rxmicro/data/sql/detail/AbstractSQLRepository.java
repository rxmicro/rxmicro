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

package io.rxmicro.data.sql.detail;

import io.rxmicro.data.detail.AbstractDataRepository;
import io.rxmicro.data.sql.model.InvalidDatabaseStateException;
import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.Arrays;

import static io.rxmicro.common.CommonConstants.EMPTY_STRING;
import static io.rxmicro.common.util.Formats.format;

/**
 * Used by generated code that created by the {@code RxMicro Annotation Processor}.
 *
 * @author nedis
 * @hidden
 * @since 0.1
 */
public abstract class AbstractSQLRepository extends AbstractDataRepository {

    protected final Logger logger;

    protected AbstractSQLRepository(final Class<?> repositoryInterface) {
        this.logger = LoggerFactory.getLogger(repositoryInterface);
    }

    protected final long validateRowsUpdated(final long expected,
                                             final long actual,
                                             final String sql,
                                             final Object... params) {
        if (expected != actual) {
            throw new InvalidDatabaseStateException(
                    "Last DML operation failed: Expected ? updated row count, but actual is ? for the SQL: '?'?!",
                    expected, actual, sql, getWithParamsPhrase(params)
            );
        }
        return actual;
    }

    protected final <T> Mono<T> throwExceptionIfEmptyResult(final String sql,
                                                            final Object... params) {
        return Mono.error(() -> new InvalidDatabaseStateException(
                "Last DML operation failed: Expected 1 updated row count, but actual is 0 for the SQL: '?'?!",
                sql, getWithParamsPhrase(params)
        ));
    }

    protected final <T> T throwExceptionIfNotEmptyResult(final String sql,
                                                         final Object... params) {
        throw new InvalidDatabaseStateException(
                "Last DML operation failed: Expected 0 updated row count, but actual is 1 for the SQL: '?'?!",
                sql, getWithParamsPhrase(params)
        );
    }

    private String getWithParamsPhrase(final Object... params) {
        if (params.length == 0) {
            return EMPTY_STRING;
        } else {
            return format(" with params ?", paramsToString(params));
        }
    }

    private String paramsToString(final Object... params) {
        if (params[0].getClass().isArray()) {
            return Arrays.toString((Object[]) params[0]);
        } else {
            return Arrays.toString(params);
        }
    }
}
