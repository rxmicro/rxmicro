package io.rxmicro.examples.data.r2dbc.postgresql.returntypes.insert;

import io.r2dbc.pool.ConnectionPool;
import io.rxmicro.data.sql.model.EntityFieldList;
import io.rxmicro.data.sql.model.EntityFieldMap;
import io.rxmicro.data.sql.r2dbc.postgresql.detail.AbstractPostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.$$AccountEntityFromR2DBCSQLDBConverter;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletionStage;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$PostgreSQLInsertManyUsingCompletionStageListRepository extends AbstractPostgreSQLRepository implements InsertManyUsingCompletionStageListRepository {

    private final $$AccountEntityFromR2DBCSQLDBConverter accountEntityFromR2DBCSQLDBConverter =
            new $$AccountEntityFromR2DBCSQLDBConverter();

    private final ConnectionPool pool;

    public $$PostgreSQLInsertManyUsingCompletionStageListRepository(final ConnectionPool pool) {
        super(InsertManyUsingCompletionStageListRepository.class);
        this.pool = pool;
    }

    @Override
    public CompletionStage<List<Account>> insertAll01() {
        // Original SQL statement:  'INSERT INTO ${table} SELECT * FROM dump RETURNING *'
        final String generatedSQL = "INSERT INTO account SELECT * FROM dump RETURNING id, first_name, last_name";
        final Account entity = new Account();
        return pool.create()
                .flatMap(c -> executeStatement(c, generatedSQL)
                        .flatMap(r -> Flux.from(r.map((row, meta) -> accountEntityFromR2DBCSQLDBConverter.setIdFirst_nameLast_name(entity, row, meta))).collectList())
                        .delayUntil(s -> close(c))
                        .onErrorResume(e -> close(c)
                                .then(Mono.error(e))
                        )
                )
                .toFuture();
    }

    @Override
    public CompletionStage<List<EntityFieldMap>> insertAll02() {
        // Original SQL statement:  'INSERT INTO ${table} SELECT * FROM dump RETURNING first_name, last_name'
        final String generatedSQL = "INSERT INTO account SELECT * FROM dump RETURNING first_name, last_name";
        return pool.create()
                .flatMap(c -> executeStatement(c, generatedSQL)
                        .flatMap(r -> Flux.from(r.map(toEntityFieldMap())).collectList())
                        .delayUntil(s -> close(c))
                        .onErrorResume(e -> close(c)
                                .then(Mono.error(e))
                        )
                )
                .toFuture();
    }

    @Override
    public CompletionStage<List<EntityFieldList>> insertAll03() {
        // Original SQL statement:  'INSERT INTO ${table} SELECT * FROM dump RETURNING first_name, last_name'
        final String generatedSQL = "INSERT INTO account SELECT * FROM dump RETURNING first_name, last_name";
        return pool.create()
                .flatMap(c -> executeStatement(c, generatedSQL)
                        .flatMap(r -> Flux.from(r.map(toEntityFieldList())).collectList())
                        .delayUntil(s -> close(c))
                        .onErrorResume(e -> close(c)
                                .then(Mono.error(e))
                        )
                )
                .toFuture();
    }
}
