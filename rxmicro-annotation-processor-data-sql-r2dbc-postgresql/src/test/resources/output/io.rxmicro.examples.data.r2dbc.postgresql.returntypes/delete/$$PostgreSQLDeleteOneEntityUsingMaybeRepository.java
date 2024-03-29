package io.rxmicro.examples.data.r2dbc.postgresql.returntypes.delete;

import io.r2dbc.pool.ConnectionPool;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.rxmicro.data.sql.model.EntityFieldList;
import io.rxmicro.data.sql.model.EntityFieldMap;
import io.rxmicro.data.sql.r2dbc.postgresql.detail.AbstractPostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.$$AccountEntityFromR2DBCSQLDBConverter;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.$$AccountEntityToR2DBCSQLDBConverter;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.Account;
import reactor.core.publisher.Mono;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$PostgreSQLDeleteOneEntityUsingMaybeRepository extends AbstractPostgreSQLRepository implements DeleteOneEntityUsingMaybeRepository {

    private final $$AccountEntityFromR2DBCSQLDBConverter accountEntityFromR2DBCSQLDBConverter =
            new $$AccountEntityFromR2DBCSQLDBConverter();

    private final $$AccountEntityToR2DBCSQLDBConverter accountEntityToR2DBCSQLDBConverter =
            new $$AccountEntityToR2DBCSQLDBConverter();

    public $$PostgreSQLDeleteOneEntityUsingMaybeRepository(final ConnectionPool pool) {
        super(DeleteOneEntityUsingMaybeRepository.class, pool);
    }

    @Override
    public Maybe<Account> delete01(final Account account) {
        // Original SQL statement:  'DELETE FROM ${table} WHERE ${by-id-filter} RETURNING *'
        final String generatedSQL = "DELETE FROM account WHERE id = $1 RETURNING id, first_name, last_name";
        final Object primaryKey = accountEntityToR2DBCSQLDBConverter.getPrimaryKey(account);
        return Flowable.fromPublisher(
                this.connectionFactory.create()
                        .flatMap(c -> executeStatement(c, generatedSQL, primaryKey)
                                .flatMap(r -> Mono.from(r.map((row, meta) -> accountEntityFromR2DBCSQLDBConverter.setIdFirst_nameLast_name(account, row, meta))))
                                .delayUntil(s -> close(c))
                                .onErrorResume(createCloseThenReturnErrorFallback(c))
                                .switchIfEmpty(close(c)
                                        .then(Mono.empty())
                                )
                        )
        ).firstElement();
    }

    @Override
    public Maybe<EntityFieldMap> delete02(final Account account) {
        // Original SQL statement:  'DELETE FROM ${table} WHERE ${by-id-filter} RETURNING *'
        final String generatedSQL = "DELETE FROM account WHERE id = $1 RETURNING id, first_name, last_name";
        final Object primaryKey = accountEntityToR2DBCSQLDBConverter.getPrimaryKey(account);
        return Flowable.fromPublisher(
                this.connectionFactory.create()
                        .flatMap(c -> executeStatement(c, generatedSQL, primaryKey)
                                .flatMap(r -> Mono.from(r.map(toEntityFieldMap())))
                                .delayUntil(s -> close(c))
                                .onErrorResume(createCloseThenReturnErrorFallback(c))
                                .switchIfEmpty(close(c)
                                        .then(Mono.empty())
                                )
                        )
        ).firstElement();
    }

    @Override
    public Maybe<EntityFieldList> delete03(final Account account) {
        // Original SQL statement:  'DELETE FROM ${table} WHERE ${by-id-filter} RETURNING *'
        final String generatedSQL = "DELETE FROM account WHERE id = $1 RETURNING id, first_name, last_name";
        final Object primaryKey = accountEntityToR2DBCSQLDBConverter.getPrimaryKey(account);
        return Flowable.fromPublisher(
                this.connectionFactory.create()
                        .flatMap(c -> executeStatement(c, generatedSQL, primaryKey)
                                .flatMap(r -> Mono.from(r.map(toEntityFieldList())))
                                .delayUntil(s -> close(c))
                                .onErrorResume(createCloseThenReturnErrorFallback(c))
                                .switchIfEmpty(close(c)
                                        .then(Mono.empty())
                                )
                        )
        ).firstElement();
    }
}
