package io.rxmicro.examples.data.r2dbc.postgresql.returntypes.update;

import io.r2dbc.pool.ConnectionPool;
import io.rxmicro.data.sql.model.EntityFieldList;
import io.rxmicro.data.sql.model.EntityFieldMap;
import io.rxmicro.data.sql.r2dbc.detail.RepositoryConnectionFactory;
import io.rxmicro.data.sql.r2dbc.detail.RepositoryConnectionPool;
import io.rxmicro.data.sql.r2dbc.postgresql.detail.AbstractPostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.$$AccountEntityFromR2DBCSQLDBConverter;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.$$AccountEntityToR2DBCSQLDBConverter;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.Account;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$PostgreSQLUpdateOneEntityUsingRequiredCompletableFutureRepository extends AbstractPostgreSQLRepository implements UpdateOneEntityUsingRequiredCompletableFutureRepository {

    private final $$AccountEntityFromR2DBCSQLDBConverter accountEntityFromR2DBCSQLDBConverter =
            new $$AccountEntityFromR2DBCSQLDBConverter();

    private final $$AccountEntityToR2DBCSQLDBConverter accountEntityToR2DBCSQLDBConverter =
            new $$AccountEntityToR2DBCSQLDBConverter();

    private final RepositoryConnectionFactory connectionFactory;

    public $$PostgreSQLUpdateOneEntityUsingRequiredCompletableFutureRepository(final ConnectionPool pool) {
        super(UpdateOneEntityUsingRequiredCompletableFutureRepository.class);
        this.connectionFactory = new RepositoryConnectionPool(UpdateOneEntityUsingRequiredCompletableFutureRepository.class, pool);
    }

    @Override
    public CompletableFuture<Void> update01(final Account account) {
        // Original SQL statement:  'UPDATE ${table} SET ${updated-columns} WHERE ${by-id-filter}'
        final String generatedSQL = "UPDATE account SET first_name = $1, last_name = $2 WHERE id = $3";
        final Object[] updateParams = accountEntityToR2DBCSQLDBConverter.getUpdateParams(account);
        final Class<?>[] updateParamTypes = accountEntityToR2DBCSQLDBConverter.getUpdateParamTypes();
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL, updateParams, updateParamTypes)
                        .flatMap(r -> Mono.from(r.getRowsUpdated()))
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                )
                .switchIfEmpty(Mono.defer(() -> Mono.error(useOptionalExceptionSupplier(CompletableFuture.class, Void.class))))
                .toFuture()
                .thenApply(r -> null);
    }

    @Override
    public CompletableFuture<Integer> update02(final Account account) {
        // Original SQL statement:  'UPDATE ${table} SET ${updated-columns} WHERE ${by-id-filter}'
        final String generatedSQL = "UPDATE account SET first_name = $1, last_name = $2 WHERE id = $3";
        final Object[] updateParams = accountEntityToR2DBCSQLDBConverter.getUpdateParams(account);
        final Class<?>[] updateParamTypes = accountEntityToR2DBCSQLDBConverter.getUpdateParamTypes();
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL, updateParams, updateParamTypes)
                        .flatMap(r -> Mono.from(r.getRowsUpdated()))
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                )
                .switchIfEmpty(Mono.defer(() -> Mono.error(useOptionalExceptionSupplier(CompletableFuture.class, Integer.class))))
                .toFuture();
    }

    @Override
    public CompletableFuture<Boolean> update03(final Account account) {
        // Original SQL statement:  'UPDATE ${table} SET ${updated-columns} WHERE ${by-id-filter}'
        final String generatedSQL = "UPDATE account SET first_name = $1, last_name = $2 WHERE id = $3";
        final Object[] updateParams = accountEntityToR2DBCSQLDBConverter.getUpdateParams(account);
        final Class<?>[] updateParamTypes = accountEntityToR2DBCSQLDBConverter.getUpdateParamTypes();
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL, updateParams, updateParamTypes)
                        .flatMap(r -> Mono.from(r.getRowsUpdated()))
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                )
                .switchIfEmpty(Mono.defer(() -> Mono.error(useOptionalExceptionSupplier(CompletableFuture.class, Boolean.class))))
                .map(r -> r > 0)
                .toFuture();
    }

    @Override
    public CompletableFuture<Account> update04(final Account account) {
        // Original SQL statement:  'UPDATE ${table} SET ${updated-columns} WHERE ${by-id-filter} RETURNING *'
        final String generatedSQL = "UPDATE account SET first_name = $1, last_name = $2 WHERE id = $3 RETURNING id, first_name, last_name";
        final Object[] updateParams = accountEntityToR2DBCSQLDBConverter.getUpdateParams(account);
        final Class<?>[] updateParamTypes = accountEntityToR2DBCSQLDBConverter.getUpdateParamTypes();
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL, updateParams, updateParamTypes)
                        .flatMap(r -> Mono.from(r.map((row, meta) -> accountEntityFromR2DBCSQLDBConverter.setIdFirst_nameLast_name(account, row, meta))))
                        .switchIfEmpty(close(c)
                                .then(Mono.empty())
                        )
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                )
                .switchIfEmpty(Mono.defer(() -> Mono.error(useOptionalExceptionSupplier(CompletableFuture.class, Account.class))))
                .toFuture();
    }

    @Override
    public CompletableFuture<EntityFieldMap> update05(final Account account) {
        // Original SQL statement:  'UPDATE ${table} SET ${updated-columns} WHERE ${by-id-filter} RETURNING *'
        final String generatedSQL = "UPDATE account SET first_name = $1, last_name = $2 WHERE id = $3 RETURNING id, first_name, last_name";
        final Object[] updateParams = accountEntityToR2DBCSQLDBConverter.getUpdateParams(account);
        final Class<?>[] updateParamTypes = accountEntityToR2DBCSQLDBConverter.getUpdateParamTypes();
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL, updateParams, updateParamTypes)
                        .flatMap(r -> Mono.from(r.map(toEntityFieldMap())))
                        .switchIfEmpty(close(c)
                                .then(Mono.empty())
                        )
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                )
                .switchIfEmpty(Mono.defer(() -> Mono.error(useOptionalExceptionSupplier(CompletableFuture.class, EntityFieldMap.class))))
                .toFuture();
    }

    @Override
    public CompletableFuture<EntityFieldList> update06(final Account account) {
        // Original SQL statement:  'UPDATE ${table} SET ${updated-columns} WHERE ${by-id-filter} RETURNING *'
        final String generatedSQL = "UPDATE account SET first_name = $1, last_name = $2 WHERE id = $3 RETURNING id, first_name, last_name";
        final Object[] updateParams = accountEntityToR2DBCSQLDBConverter.getUpdateParams(account);
        final Class<?>[] updateParamTypes = accountEntityToR2DBCSQLDBConverter.getUpdateParamTypes();
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL, updateParams, updateParamTypes)
                        .flatMap(r -> Mono.from(r.map(toEntityFieldList())))
                        .switchIfEmpty(close(c)
                                .then(Mono.empty())
                        )
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                )
                .switchIfEmpty(Mono.defer(() -> Mono.error(useOptionalExceptionSupplier(CompletableFuture.class, EntityFieldList.class))))
                .toFuture();
    }
}
