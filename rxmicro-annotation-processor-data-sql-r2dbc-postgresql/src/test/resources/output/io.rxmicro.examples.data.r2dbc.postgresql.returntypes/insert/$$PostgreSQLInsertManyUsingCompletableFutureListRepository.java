package io.rxmicro.examples.data.r2dbc.postgresql.returntypes.insert;

import io.r2dbc.pool.ConnectionPool;
import io.rxmicro.data.sql.model.EntityFieldList;
import io.rxmicro.data.sql.model.EntityFieldMap;
import io.rxmicro.data.sql.r2dbc.postgresql.detail.AbstractPostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.$$AccountEntityFromR2DBCSQLDBConverter;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.Account;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$PostgreSQLInsertManyUsingCompletableFutureListRepository extends AbstractPostgreSQLRepository implements InsertManyUsingCompletableFutureListRepository {

    private final $$AccountEntityFromR2DBCSQLDBConverter accountEntityFromR2DBCSQLDBConverter =
            new $$AccountEntityFromR2DBCSQLDBConverter();

    public $$PostgreSQLInsertManyUsingCompletableFutureListRepository(final ConnectionPool pool) {
        super(InsertManyUsingCompletableFutureListRepository.class, pool);
    }

    @Override
    public CompletableFuture<List<Account>> insertAll01() {
        // Original SQL statement:  'INSERT INTO ${table} SELECT * FROM dump RETURNING *'
        final String generatedSQL = "INSERT INTO account SELECT * FROM dump RETURNING id, first_name, last_name";
        final Account resultEntity = new Account();
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL)
                        .flatMap(r -> Flux.from(r.map((row, meta) -> accountEntityFromR2DBCSQLDBConverter.setIdFirst_nameLast_name(resultEntity, row, meta))).collectList())
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                )
                .toFuture();
    }

    @Override
    public CompletableFuture<List<EntityFieldMap>> insertAll02() {
        // Original SQL statement:  'INSERT INTO ${table} SELECT * FROM dump RETURNING first_name, last_name'
        final String generatedSQL = "INSERT INTO account SELECT * FROM dump RETURNING first_name, last_name";
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL)
                        .flatMap(r -> Flux.from(r.map(toEntityFieldMap())).collectList())
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                )
                .toFuture();
    }

    @Override
    public CompletableFuture<List<EntityFieldList>> insertAll03() {
        // Original SQL statement:  'INSERT INTO ${table} SELECT * FROM dump RETURNING first_name, last_name'
        final String generatedSQL = "INSERT INTO account SELECT * FROM dump RETURNING first_name, last_name";
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL)
                        .flatMap(r -> Flux.from(r.map(toEntityFieldList())).collectList())
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                )
                .toFuture();
    }
}
