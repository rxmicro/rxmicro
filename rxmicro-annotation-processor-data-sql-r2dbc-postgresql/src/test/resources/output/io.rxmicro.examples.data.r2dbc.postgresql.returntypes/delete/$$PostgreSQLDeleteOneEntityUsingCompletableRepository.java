package io.rxmicro.examples.data.r2dbc.postgresql.returntypes.delete;

import io.r2dbc.pool.ConnectionPool;
import io.reactivex.rxjava3.core.Completable;
import io.rxmicro.data.sql.r2dbc.postgresql.detail.AbstractPostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.$$AccountEntityToR2DBCSQLDBConverter;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.Account;
import reactor.core.publisher.Mono;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$PostgreSQLDeleteOneEntityUsingCompletableRepository extends AbstractPostgreSQLRepository implements DeleteOneEntityUsingCompletableRepository {

    private final $$AccountEntityToR2DBCSQLDBConverter accountEntityToR2DBCSQLDBConverter =
            new $$AccountEntityToR2DBCSQLDBConverter();

    private final ConnectionPool pool;

    public $$PostgreSQLDeleteOneEntityUsingCompletableRepository(final ConnectionPool pool) {
        super(DeleteOneEntityUsingCompletableRepository.class);
        this.pool = pool;
    }

    @Override
    public Completable delete01(final Account account) {
        // Original SQL statement:  'DELETE FROM ${table} WHERE ${by-id-filter}'
        final String generatedSQL = "DELETE FROM account WHERE id = $1";
        final Object primaryKey = accountEntityToR2DBCSQLDBConverter.getPrimaryKey(account);
        return Completable.fromPublisher(
                pool.create()
                        .flatMap(c -> executeStatement(c, generatedSQL, primaryKey)
                                .flatMap(r -> Mono.from(r.getRowsUpdated()))
                                .delayUntil(s -> close(c))
                                .onErrorResume(e -> close(c)
                                        .then(Mono.error(e))
                                )
                        )
        );
    }
}
