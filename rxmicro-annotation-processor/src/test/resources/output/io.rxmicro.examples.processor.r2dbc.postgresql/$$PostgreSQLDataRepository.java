package io.rxmicro.examples.processor.r2dbc.postgresql;

import io.r2dbc.pool.ConnectionPool;
import io.rxmicro.data.sql.r2dbc.postgresql.detail.AbstractPostgreSQLRepository;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$PostgreSQLDataRepository extends AbstractPostgreSQLRepository implements DataRepository {

    public $$PostgreSQLDataRepository(final ConnectionPool pool) {
        super(DataRepository.class, pool);
    }
}
