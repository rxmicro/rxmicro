package io.rxmicro.examples.data.r2dbc.postgresql.transactional;

import io.r2dbc.pool.ConnectionPool;
import io.reactivex.rxjava3.core.Single;
import io.rxmicro.data.sql.model.IsolationLevel;
import io.rxmicro.data.sql.model.rxjava3.Transaction;
import io.rxmicro.data.sql.r2dbc.postgresql.detail.AbstractPostgreSQLRepository;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$PostgreSQLBeginRxJava3TransactionRepository extends AbstractPostgreSQLRepository implements BeginRxJava3TransactionRepository {

    public $$PostgreSQLBeginRxJava3TransactionRepository(final ConnectionPool pool) {
        super(BeginRxJava3TransactionRepository.class, pool);
    }

    @Override
    public Single<Transaction> beginTransaction() {
        return Single.fromPublisher(
                this.connectionFactory.create()
                        .flatMap(c -> beginRxJava3Transaction(c))
        );
    }

    @Override
    public Single<Transaction> beginTransaction(final IsolationLevel isolationLevel) {
        return Single.fromPublisher(this.connectionFactory.create().flatMap(c -> beginRxJava3Transaction(c)))
                        .flatMap(t -> t.setIsolationLevel(isolationLevel)
                                .andThen(Single.just(t)));
    }
}
