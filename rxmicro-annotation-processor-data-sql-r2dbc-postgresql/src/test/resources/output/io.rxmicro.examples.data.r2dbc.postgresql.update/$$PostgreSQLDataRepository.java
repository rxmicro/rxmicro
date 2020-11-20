package io.rxmicro.examples.data.r2dbc.postgresql.update;

import io.r2dbc.pool.ConnectionPool;
import io.rxmicro.data.sql.model.EntityFieldMap;
import io.rxmicro.data.sql.r2dbc.detail.RepositoryConnectionFactory;
import io.rxmicro.data.sql.r2dbc.detail.RepositoryConnectionPool;
import io.rxmicro.data.sql.r2dbc.postgresql.detail.AbstractPostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.update.model.$$AccountEntityFromR2DBCSQLDBConverter;
import io.rxmicro.examples.data.r2dbc.postgresql.update.model.$$AccountEntityToR2DBCSQLDBConverter;
import io.rxmicro.examples.data.r2dbc.postgresql.update.model.Account;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$PostgreSQLDataRepository extends AbstractPostgreSQLRepository implements DataRepository {

    private final $$AccountEntityFromR2DBCSQLDBConverter accountEntityFromR2DBCSQLDBConverter =
            new $$AccountEntityFromR2DBCSQLDBConverter();

    private final $$AccountEntityToR2DBCSQLDBConverter accountEntityToR2DBCSQLDBConverter =
            new $$AccountEntityToR2DBCSQLDBConverter();

    private final RepositoryConnectionFactory connectionFactory;

    public $$PostgreSQLDataRepository(final ConnectionPool pool) {
        super(DataRepository.class);
        this.connectionFactory = new RepositoryConnectionPool(DataRepository.class, pool);
    }

    @Override
    public CompletableFuture<Boolean> update1(final Account account) {
        // Original SQL statement:  'UPDATE ${table} SET ${updated-columns} WHERE ${by-id-filter}'
        final String generatedSQL = "UPDATE account SET first_name = $1, last_name = $2, balance = $3, role = $4 WHERE id = $5";
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
    public CompletableFuture<Integer> update2(final String firstName, final String lastName, final Long id) {
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
    public CompletableFuture<Account> update3(final String firstName, final String lastName, final Long id) {
        // Original SQL statement:  'UPDATE ${table} SET first_name = ?, last_name = ? WHERE ${by-id-filter} RETURNING *'
        final String generatedSQL = "UPDATE account SET first_name = $1, last_name = $2 WHERE id = $3 RETURNING id, email, first_name, last_name, balance, role";
        final Object[] updateParams = {firstName, lastName, id};
        final Class<?>[] updateParamTypes = {String.class, String.class, Long.class};
        final Account resultEntity = new Account();
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL, updateParams, updateParamTypes)
                        .flatMap(r -> Mono.from(r.map((row, meta) -> accountEntityFromR2DBCSQLDBConverter.setIdEmailFirst_nameLast_nameBalanceRole(resultEntity, row, meta))))
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
    public CompletableFuture<Integer> update4(final String firstName, final String lastName, final Long id) {
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
    public CompletableFuture<EntityFieldMap> update5(final String firstName, final String lastName, final Long id) {
        // Original SQL statement:  'UPDATE ${table} SET first_name = ?, last_name = ? WHERE ${by-id-filter} RETURNING *'
        final String generatedSQL = "UPDATE account SET first_name = $1, last_name = $2 WHERE id = $3 RETURNING id, email, first_name, last_name, balance, role";
        final Object[] updateParams = {firstName, lastName, id};
        final Class<?>[] updateParamTypes = {String.class, String.class, Long.class};
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
}
