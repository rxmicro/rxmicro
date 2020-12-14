package io.rxmicro.examples.data.r2dbc.postgresql.returntypes.insert;

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

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$PostgreSQLInsertOneEntityUsingOptionalCompletableFutureRepository extends AbstractPostgreSQLRepository implements InsertOneEntityUsingOptionalCompletableFutureRepository {

    private final $$AccountEntityFromR2DBCSQLDBConverter accountEntityFromR2DBCSQLDBConverter =
            new $$AccountEntityFromR2DBCSQLDBConverter();

    private final $$AccountEntityToR2DBCSQLDBConverter accountEntityToR2DBCSQLDBConverter =
            new $$AccountEntityToR2DBCSQLDBConverter();

    private final RepositoryConnectionFactory connectionFactory;

    public $$PostgreSQLInsertOneEntityUsingOptionalCompletableFutureRepository(final ConnectionPool pool) {
        super(InsertOneEntityUsingOptionalCompletableFutureRepository.class);
        this.connectionFactory = new RepositoryConnectionPool(InsertOneEntityUsingOptionalCompletableFutureRepository.class, pool);
    }

    @Override
    public CompletableFuture<Optional<Account>> insert01(final Account account) {
        // Original SQL statement:  'INSERT INTO ${table}(${inserted-columns}) VALUES(${values}) ON CONFLICT DO NOTHING RETURNING ${id-columns}'
        final String generatedSQL = "INSERT INTO account(first_name, last_name) VALUES($1, $2) ON CONFLICT DO NOTHING RETURNING id";
        final Object[] insertParams = accountEntityToR2DBCSQLDBConverter.getInsertParams(account);
        final Class<?>[] insertParamTypes = accountEntityToR2DBCSQLDBConverter.getInsertParamTypes();
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL, insertParams, insertParamTypes)
                        .flatMap(r -> Mono.from(r.map((row, meta) -> accountEntityFromR2DBCSQLDBConverter.setId(account, row, meta))))
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
    public CompletableFuture<Optional<EntityFieldMap>> insert02(final Account account) {
        // Original SQL statement:  'INSERT INTO ${table}(${inserted-columns}) VALUES(${values}) ON CONFLICT DO NOTHING RETURNING ${id-columns}'
        final String generatedSQL = "INSERT INTO account(first_name, last_name) VALUES($1, $2) ON CONFLICT DO NOTHING RETURNING id";
        final Object[] insertParams = accountEntityToR2DBCSQLDBConverter.getInsertParams(account);
        final Class<?>[] insertParamTypes = accountEntityToR2DBCSQLDBConverter.getInsertParamTypes();
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL, insertParams, insertParamTypes)
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
    public CompletableFuture<Optional<EntityFieldList>> insert03(final Account account) {
        // Original SQL statement:  'INSERT INTO ${table}(${inserted-columns}) VALUES(${values}) ON CONFLICT DO NOTHING RETURNING ${id-columns}'
        final String generatedSQL = "INSERT INTO account(first_name, last_name) VALUES($1, $2) ON CONFLICT DO NOTHING RETURNING id";
        final Object[] insertParams = accountEntityToR2DBCSQLDBConverter.getInsertParams(account);
        final Class<?>[] insertParamTypes = accountEntityToR2DBCSQLDBConverter.getInsertParamTypes();
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL, insertParams, insertParamTypes)
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

    @Override
    public CompletableFuture<Optional<Account>> insert04(final Account account) {
        // Original SQL statement:  'INSERT INTO ${table}(${inserted-columns}) VALUES(${values}) ON CONFLICT DO NOTHING RETURNING *'
        final String generatedSQL = "INSERT INTO account(first_name, last_name) VALUES($1, $2) ON CONFLICT DO NOTHING RETURNING id, first_name, last_name";
        final Object[] insertParams = accountEntityToR2DBCSQLDBConverter.getInsertParams(account);
        final Class<?>[] insertParamTypes = accountEntityToR2DBCSQLDBConverter.getInsertParamTypes();
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL, insertParams, insertParamTypes)
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
    public CompletableFuture<Optional<EntityFieldMap>> insert05(final Account account) {
        // Original SQL statement:  'INSERT INTO ${table}(${inserted-columns}) VALUES(${values}) ON CONFLICT DO NOTHING RETURNING *'
        final String generatedSQL = "INSERT INTO account(first_name, last_name) VALUES($1, $2) ON CONFLICT DO NOTHING RETURNING id, first_name, last_name";
        final Object[] insertParams = accountEntityToR2DBCSQLDBConverter.getInsertParams(account);
        final Class<?>[] insertParamTypes = accountEntityToR2DBCSQLDBConverter.getInsertParamTypes();
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL, insertParams, insertParamTypes)
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
    public CompletableFuture<Optional<EntityFieldList>> insert06(final Account account) {
        // Original SQL statement:  'INSERT INTO ${table}(${inserted-columns}) VALUES(${values}) ON CONFLICT DO NOTHING RETURNING *'
        final String generatedSQL = "INSERT INTO account(first_name, last_name) VALUES($1, $2) ON CONFLICT DO NOTHING RETURNING id, first_name, last_name";
        final Object[] insertParams = accountEntityToR2DBCSQLDBConverter.getInsertParams(account);
        final Class<?>[] insertParamTypes = accountEntityToR2DBCSQLDBConverter.getInsertParamTypes();
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL, insertParams, insertParamTypes)
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
