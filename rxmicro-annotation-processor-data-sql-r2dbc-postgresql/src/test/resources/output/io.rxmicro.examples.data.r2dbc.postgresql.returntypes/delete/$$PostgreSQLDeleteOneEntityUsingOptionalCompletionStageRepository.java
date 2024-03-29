package io.rxmicro.examples.data.r2dbc.postgresql.returntypes.delete;

import io.r2dbc.pool.ConnectionPool;
import io.rxmicro.data.sql.model.EntityFieldList;
import io.rxmicro.data.sql.model.EntityFieldMap;
import io.rxmicro.data.sql.r2dbc.postgresql.detail.AbstractPostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.$$AccountEntityFromR2DBCSQLDBConverter;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.$$AccountEntityToR2DBCSQLDBConverter;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.Account;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$PostgreSQLDeleteOneEntityUsingOptionalCompletionStageRepository extends AbstractPostgreSQLRepository implements DeleteOneEntityUsingOptionalCompletionStageRepository {

    private final $$AccountEntityFromR2DBCSQLDBConverter accountEntityFromR2DBCSQLDBConverter =
            new $$AccountEntityFromR2DBCSQLDBConverter();

    private final $$AccountEntityToR2DBCSQLDBConverter accountEntityToR2DBCSQLDBConverter =
            new $$AccountEntityToR2DBCSQLDBConverter();

    public $$PostgreSQLDeleteOneEntityUsingOptionalCompletionStageRepository(final ConnectionPool pool) {
        super(DeleteOneEntityUsingOptionalCompletionStageRepository.class, pool);
    }

    @Override
    public CompletionStage<Optional<Account>> delete01(final Account account) {
        // Original SQL statement:  'DELETE FROM ${table} WHERE ${by-id-filter} RETURNING *'
        final String generatedSQL = "DELETE FROM account WHERE id = $1 RETURNING id, first_name, last_name";
        final Object primaryKey = accountEntityToR2DBCSQLDBConverter.getPrimaryKey(account);
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL, primaryKey)
                        .flatMap(r -> Mono.from(r.map((row, meta) -> accountEntityFromR2DBCSQLDBConverter.setIdFirst_nameLast_name(account, row, meta))))
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                        .switchIfEmpty(close(c)
                                .then(Mono.empty())
                        )
                )
                .toFuture()
                .thenApply(a -> Optional.ofNullable(a));
    }

    @Override
    public CompletionStage<Optional<EntityFieldMap>> delete02(final Account account) {
        // Original SQL statement:  'DELETE FROM ${table} WHERE ${by-id-filter} RETURNING *'
        final String generatedSQL = "DELETE FROM account WHERE id = $1 RETURNING id, first_name, last_name";
        final Object primaryKey = accountEntityToR2DBCSQLDBConverter.getPrimaryKey(account);
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL, primaryKey)
                        .flatMap(r -> Mono.from(r.map(toEntityFieldMap())))
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                        .switchIfEmpty(close(c)
                                .then(Mono.empty())
                        )
                )
                .toFuture()
                .thenApply(a -> Optional.ofNullable(a));
    }

    @Override
    public CompletionStage<Optional<EntityFieldList>> delete03(final Account account) {
        // Original SQL statement:  'DELETE FROM ${table} WHERE ${by-id-filter} RETURNING *'
        final String generatedSQL = "DELETE FROM account WHERE id = $1 RETURNING id, first_name, last_name";
        final Object primaryKey = accountEntityToR2DBCSQLDBConverter.getPrimaryKey(account);
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL, primaryKey)
                        .flatMap(r -> Mono.from(r.map(toEntityFieldList())))
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                        .switchIfEmpty(close(c)
                                .then(Mono.empty())
                        )
                )
                .toFuture()
                .thenApply(a -> Optional.ofNullable(a));
    }
}
