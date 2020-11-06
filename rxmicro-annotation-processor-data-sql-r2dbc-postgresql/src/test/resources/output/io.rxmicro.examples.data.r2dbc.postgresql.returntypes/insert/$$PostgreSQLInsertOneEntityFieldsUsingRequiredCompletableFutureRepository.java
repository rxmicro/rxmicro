package io.rxmicro.examples.data.r2dbc.postgresql.returntypes.insert;

import io.r2dbc.pool.ConnectionPool;
import io.rxmicro.data.sql.model.EntityFieldList;
import io.rxmicro.data.sql.model.EntityFieldMap;
import io.rxmicro.data.sql.r2dbc.postgresql.detail.AbstractPostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.$$AccountEntityFromR2DBCSQLDBConverter;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.Account;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$PostgreSQLInsertOneEntityFieldsUsingRequiredCompletableFutureRepository extends AbstractPostgreSQLRepository implements InsertOneEntityFieldsUsingRequiredCompletableFutureRepository {

    private final $$AccountEntityFromR2DBCSQLDBConverter accountEntityFromR2DBCSQLDBConverter =
            new $$AccountEntityFromR2DBCSQLDBConverter();

    private final ConnectionPool pool;

    public $$PostgreSQLInsertOneEntityFieldsUsingRequiredCompletableFutureRepository(final ConnectionPool pool) {
        super(InsertOneEntityFieldsUsingRequiredCompletableFutureRepository.class);
        this.pool = pool;
    }

    @Override
    public CompletableFuture<Void> insert01(final String firstName, final String lastName) {
        // Original SQL statement:  'INSERT INTO ${table}(first_name, last_name) VALUES(?, ?)'
        final String generatedSQL = "INSERT INTO account(first_name, last_name) VALUES($1, $2)";
        final Object[] insertParams = {firstName, lastName};
        final Class<?>[] insertParamTypes = {String.class, String.class};
        return pool.create()
                .flatMap(c -> executeStatement(c, generatedSQL, insertParams, insertParamTypes)
                        .flatMap(r -> Mono.from(r.getRowsUpdated()))
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                )
                .switchIfEmpty(Mono.defer(() -> Mono.error(useOptionalExceptionSupplier(CompletableFuture.class, Void.class))))
                .toFuture()
                .thenApply(r -> null);
    }

    @Override
    public CompletableFuture<Integer> insert02(final String firstName, final String lastName) {
        // Original SQL statement:  'INSERT INTO ${table}(first_name, last_name) VALUES(?, ?)'
        final String generatedSQL = "INSERT INTO account(first_name, last_name) VALUES($1, $2)";
        final Object[] insertParams = {firstName, lastName};
        final Class<?>[] insertParamTypes = {String.class, String.class};
        return pool.create()
                .flatMap(c -> executeStatement(c, generatedSQL, insertParams, insertParamTypes)
                        .flatMap(r -> Mono.from(r.getRowsUpdated()))
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                )
                .switchIfEmpty(Mono.defer(() -> Mono.error(useOptionalExceptionSupplier(CompletableFuture.class, Integer.class))))
                .toFuture();
    }

    @Override
    public CompletableFuture<Boolean> insert03(final String firstName, final String lastName) {
        // Original SQL statement:  'INSERT INTO ${table}(first_name, last_name) VALUES(?, ?)'
        final String generatedSQL = "INSERT INTO account(first_name, last_name) VALUES($1, $2)";
        final Object[] insertParams = {firstName, lastName};
        final Class<?>[] insertParamTypes = {String.class, String.class};
        return pool.create()
                .flatMap(c -> executeStatement(c, generatedSQL, insertParams, insertParamTypes)
                        .flatMap(r -> Mono.from(r.getRowsUpdated()))
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                )
                .switchIfEmpty(Mono.defer(() -> Mono.error(useOptionalExceptionSupplier(CompletableFuture.class, Boolean.class))))
                .map(r -> r > 0)
                .toFuture();
    }

    @Override
    public CompletableFuture<Account> insert04(final String firstName, final String lastName) {
        // Original SQL statement:  'INSERT INTO ${table}(first_name, last_name) VALUES(?, ?) RETURNING *'
        final String generatedSQL = "INSERT INTO account(first_name, last_name) VALUES($1, $2) RETURNING id, first_name, last_name";
        final Object[] insertParams = {firstName, lastName};
        final Class<?>[] insertParamTypes = {String.class, String.class};
        final Account entity = new Account();
        return pool.create()
                .flatMap(c -> executeStatement(c, generatedSQL, insertParams, insertParamTypes)
                        .flatMap(r -> Mono.from(r.map((row, meta) -> accountEntityFromR2DBCSQLDBConverter.setIdFirst_nameLast_name(entity, row, meta))))
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
    public CompletableFuture<EntityFieldMap> insert05(final String firstName, final String lastName) {
        // Original SQL statement:  'INSERT INTO ${table}(first_name, last_name) VALUES(?, ?) RETURNING first_name, last_name'
        final String generatedSQL = "INSERT INTO account(first_name, last_name) VALUES($1, $2) RETURNING first_name, last_name";
        final Object[] insertParams = {firstName, lastName};
        final Class<?>[] insertParamTypes = {String.class, String.class};
        return pool.create()
                .flatMap(c -> executeStatement(c, generatedSQL, insertParams, insertParamTypes)
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
    public CompletableFuture<EntityFieldList> insert06(final String firstName, final String lastName) {
        // Original SQL statement:  'INSERT INTO ${table}(first_name, last_name) VALUES(?, ?) RETURNING first_name, last_name'
        final String generatedSQL = "INSERT INTO account(first_name, last_name) VALUES($1, $2) RETURNING first_name, last_name";
        final Object[] insertParams = {firstName, lastName};
        final Class<?>[] insertParamTypes = {String.class, String.class};
        return pool.create()
                .flatMap(c -> executeStatement(c, generatedSQL, insertParams, insertParamTypes)
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
