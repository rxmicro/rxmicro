package io.rxmicro.examples.data.r2dbc.postgresql.returntypes.update;

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
public final class $$PostgreSQLUpdateOneEntityUsingOptionalCompletionStageRepository extends AbstractPostgreSQLRepository implements UpdateOneEntityUsingOptionalCompletionStageRepository {

    private final $$AccountEntityFromR2DBCSQLDBConverter accountEntityFromR2DBCSQLDBConverter =
            new $$AccountEntityFromR2DBCSQLDBConverter();

    private final $$AccountEntityToR2DBCSQLDBConverter accountEntityToR2DBCSQLDBConverter =
            new $$AccountEntityToR2DBCSQLDBConverter();

    public $$PostgreSQLUpdateOneEntityUsingOptionalCompletionStageRepository(final ConnectionPool pool) {
        super(UpdateOneEntityUsingOptionalCompletionStageRepository.class, pool);
    }

    @Override
    public CompletionStage<Optional<Account>> update01(final Account account) {
        // Original SQL statement:  'UPDATE ${table} SET ${updated-columns} WHERE ${by-id-filter} RETURNING *'
        final String generatedSQL = "UPDATE account SET first_name = $1, last_name = $2 WHERE id = $3 RETURNING id, first_name, last_name";
        final Object[] updateParams = accountEntityToR2DBCSQLDBConverter.getUpdateParams(account);
        final Class<?>[] updateParamTypes = accountEntityToR2DBCSQLDBConverter.getUpdateParamTypes();
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL, updateParams, updateParamTypes)
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
    public CompletionStage<Optional<EntityFieldMap>> update02(final Account account) {
        // Original SQL statement:  'UPDATE ${table} SET ${updated-columns} WHERE ${by-id-filter} RETURNING *'
        final String generatedSQL = "UPDATE account SET first_name = $1, last_name = $2 WHERE id = $3 RETURNING id, first_name, last_name";
        final Object[] updateParams = accountEntityToR2DBCSQLDBConverter.getUpdateParams(account);
        final Class<?>[] updateParamTypes = accountEntityToR2DBCSQLDBConverter.getUpdateParamTypes();
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL, updateParams, updateParamTypes)
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
    public CompletionStage<Optional<EntityFieldList>> update03(final Account account) {
        // Original SQL statement:  'UPDATE ${table} SET ${updated-columns} WHERE ${by-id-filter} RETURNING *'
        final String generatedSQL = "UPDATE account SET first_name = $1, last_name = $2 WHERE id = $3 RETURNING id, first_name, last_name";
        final Object[] updateParams = accountEntityToR2DBCSQLDBConverter.getUpdateParams(account);
        final Class<?>[] updateParamTypes = accountEntityToR2DBCSQLDBConverter.getUpdateParamTypes();
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL, updateParams, updateParamTypes)
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
