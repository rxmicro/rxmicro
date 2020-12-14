package io.rxmicro.examples.data.r2dbc.postgresql.returntypes.update;

import io.r2dbc.pool.ConnectionPool;
import io.rxmicro.data.sql.model.EntityFieldList;
import io.rxmicro.data.sql.model.EntityFieldMap;
import io.rxmicro.data.sql.r2dbc.detail.RepositoryConnectionFactory;
import io.rxmicro.data.sql.r2dbc.detail.RepositoryConnectionPool;
import io.rxmicro.data.sql.r2dbc.postgresql.detail.AbstractPostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.$$AccountEntityFromR2DBCSQLDBConverter;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.Account;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$PostgreSQLUpdateOneEntityFieldsUsingRequiredCompletableFutureRepository extends AbstractPostgreSQLRepository implements UpdateOneEntityFieldsUsingRequiredCompletableFutureRepository {

    private final $$AccountEntityFromR2DBCSQLDBConverter accountEntityFromR2DBCSQLDBConverter =
            new $$AccountEntityFromR2DBCSQLDBConverter();

    private final RepositoryConnectionFactory connectionFactory;

    public $$PostgreSQLUpdateOneEntityFieldsUsingRequiredCompletableFutureRepository(final ConnectionPool pool) {
        super(UpdateOneEntityFieldsUsingRequiredCompletableFutureRepository.class);
        this.connectionFactory = new RepositoryConnectionPool(UpdateOneEntityFieldsUsingRequiredCompletableFutureRepository.class, pool);
    }

    @Override
    public CompletableFuture<Void> update01(final String firstName, final String lastName, final Long id) {
        // Original SQL statement:  'UPDATE ${table} SET first_name = ?, last_name = ? WHERE id = ?'
        final String generatedSQL = "UPDATE account SET first_name = $1, last_name = $2 WHERE id = $3";
        final Object[] updateParams = {firstName, lastName, id};
        final Class<?>[] updateParamTypes = {String.class, String.class, Long.class};
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
    public CompletableFuture<Integer> update02(final String firstName, final String lastName, final Long id) {
        // Original SQL statement:  'UPDATE ${table} SET first_name = ?, last_name = ? WHERE id = ?'
        final String generatedSQL = "UPDATE account SET first_name = $1, last_name = $2 WHERE id = $3";
        final Object[] updateParams = {firstName, lastName, id};
        final Class<?>[] updateParamTypes = {String.class, String.class, Long.class};
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
    public CompletableFuture<Boolean> update03(final String firstName, final String lastName, final Long id) {
        // Original SQL statement:  'UPDATE ${table} SET first_name = ?, last_name = ? WHERE id = ?'
        final String generatedSQL = "UPDATE account SET first_name = $1, last_name = $2 WHERE id = $3";
        final Object[] updateParams = {firstName, lastName, id};
        final Class<?>[] updateParamTypes = {String.class, String.class, Long.class};
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
    public CompletableFuture<Account> update04(final String firstName, final String lastName, final Long id) {
        // Original SQL statement:  'UPDATE ${table} SET first_name = ?, last_name = ? WHERE id = ? RETURNING *'
        final String generatedSQL = "UPDATE account SET first_name = $1, last_name = $2 WHERE id = $3 RETURNING id, first_name, last_name";
        final Object[] updateParams = {firstName, lastName, id};
        final Class<?>[] updateParamTypes = {String.class, String.class, Long.class};
        final Account resultEntity = new Account();
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL, updateParams, updateParamTypes)
                        .flatMap(r -> Mono.from(r.map((row, meta) -> accountEntityFromR2DBCSQLDBConverter.setIdFirst_nameLast_name(resultEntity, row, meta))))
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                        .switchIfEmpty(close(c)
                                .then(Mono.empty())
                        )
                )
                .switchIfEmpty(Mono.defer(() -> Mono.error(useOptionalExceptionSupplier(CompletableFuture.class, Account.class))))
                .toFuture();
    }

    @Override
    public CompletableFuture<EntityFieldMap> update05(final String firstName, final String lastName, final Long id) {
        // Original SQL statement:  'UPDATE ${table} SET first_name = ?, last_name = ? WHERE id = ? RETURNING first_name, last_name'
        final String generatedSQL = "UPDATE account SET first_name = $1, last_name = $2 WHERE id = $3 RETURNING first_name, last_name";
        final Object[] updateParams = {firstName, lastName, id};
        final Class<?>[] updateParamTypes = {String.class, String.class, Long.class};
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL, updateParams, updateParamTypes)
                        .flatMap(r -> Mono.from(r.map(toEntityFieldMap())))
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                        .switchIfEmpty(close(c)
                                .then(Mono.empty())
                        )
                )
                .switchIfEmpty(Mono.defer(() -> Mono.error(useOptionalExceptionSupplier(CompletableFuture.class, EntityFieldMap.class))))
                .toFuture();
    }

    @Override
    public CompletableFuture<EntityFieldList> update06(final String firstName, final String lastName, final Long id) {
        // Original SQL statement:  'UPDATE ${table} SET first_name = ?, last_name = ? WHERE id = ? RETURNING first_name, last_name'
        final String generatedSQL = "UPDATE account SET first_name = $1, last_name = $2 WHERE id = $3 RETURNING first_name, last_name";
        final Object[] updateParams = {firstName, lastName, id};
        final Class<?>[] updateParamTypes = {String.class, String.class, Long.class};
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL, updateParams, updateParamTypes)
                        .flatMap(r -> Mono.from(r.map(toEntityFieldList())))
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                        .switchIfEmpty(close(c)
                                .then(Mono.empty())
                        )
                )
                .switchIfEmpty(Mono.defer(() -> Mono.error(useOptionalExceptionSupplier(CompletableFuture.class, EntityFieldList.class))))
                .toFuture();
    }
}
