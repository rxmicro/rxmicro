package io.rxmicro.examples.data.r2dbc.postgresql.returntypes.update;

import io.r2dbc.pool.ConnectionPool;
import io.reactivex.rxjava3.core.Completable;
import io.rxmicro.data.sql.r2dbc.postgresql.detail.AbstractPostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.$$AccountEntityToR2DBCSQLDBConverter;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.Account;
import reactor.core.publisher.Mono;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$PostgreSQLUpdateOneEntityUsingCompletableRepository extends AbstractPostgreSQLRepository implements UpdateOneEntityUsingCompletableRepository {

    private final $$AccountEntityToR2DBCSQLDBConverter accountEntityToR2DBCSQLDBConverter =
            new $$AccountEntityToR2DBCSQLDBConverter();

    public $$PostgreSQLUpdateOneEntityUsingCompletableRepository(final ConnectionPool pool) {
        super(UpdateOneEntityUsingCompletableRepository.class, pool);
    }

    @Override
    public Completable update01(final Account account) {
        // Original SQL statement:  'UPDATE ${table} SET ${updated-columns} WHERE ${by-id-filter}'
        final String generatedSQL = "UPDATE account SET first_name = $1, last_name = $2 WHERE id = $3";
        final Object[] updateParams = accountEntityToR2DBCSQLDBConverter.getUpdateParams(account);
        final Class<?>[] updateParamTypes = accountEntityToR2DBCSQLDBConverter.getUpdateParamTypes();
        return Completable.fromPublisher(
                this.connectionFactory.create()
                        .flatMap(c -> executeStatement(c, generatedSQL, updateParams, updateParamTypes)
                                .flatMap(r -> Mono.from(r.getRowsUpdated()))
                                .delayUntil(s -> close(c))
                                .onErrorResume(createCloseThenReturnErrorFallback(c))
                        )
        );
    }
}
