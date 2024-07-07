package io.rxmicro.examples.data.r2dbc.postgresql.variables;

import io.r2dbc.pool.ConnectionPool;
import io.rxmicro.data.sql.model.EntityFieldMap;
import io.rxmicro.data.sql.r2dbc.postgresql.detail.AbstractPostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.variables.model.$$EntityEntityFromR2DBCSQLDBConverter;
import io.rxmicro.examples.data.r2dbc.postgresql.variables.model.Entity;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$PostgreSQLSelectDataRepository extends AbstractPostgreSQLRepository implements SelectDataRepository {

    private final $$EntityEntityFromR2DBCSQLDBConverter entityEntityFromR2DBCSQLDBConverter =
            new $$EntityEntityFromR2DBCSQLDBConverter();

    public $$PostgreSQLSelectDataRepository(final ConnectionPool pool) {
        super(SelectDataRepository.class, pool);
    }

    @Override
    public CompletableFuture<List<Entity>> findFromEntityTable1() {
        // Original SQL statement:  'SELECT * FROM ${table}'
        final String generatedSQL = "SELECT id, value FROM entity_table";
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL)
                        .flatMap(r -> Flux.from(r.map(entityEntityFromR2DBCSQLDBConverter::fromDB))
                                .collectList()
                        )
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                )
                .toFuture();
    }

    @Override
    public CompletableFuture<List<EntityFieldMap>> findFromEntityTable2() {
        // Original SQL statement:  'SELECT * FROM ${table}'
        final String generatedSQL = "SELECT * FROM entity_table";
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL)
                        .flatMap(r -> Flux.from(r.map(toEntityFieldMap()))
                                .collectList()
                        )
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                )
                .toFuture();
    }

    @Override
    public CompletableFuture<List<EntityFieldMap>> findFromGlobalTable() {
        // Original SQL statement:  'SELECT * FROM ${table}'
        final String generatedSQL = "SELECT * FROM global_table";
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL)
                        .flatMap(r -> Flux.from(r.map(toEntityFieldMap()))
                                .collectList()
                        )
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                )
                .toFuture();
    }

    @Override
    public CompletableFuture<List<EntityFieldMap>> findFromLocalTable() {
        // Original SQL statement:  'SELECT * FROM ${table}'
        final String generatedSQL = "SELECT * FROM local_table";
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL)
                        .flatMap(r -> Flux.from(r.map(toEntityFieldMap()))
                                .collectList()
                        )
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                )
                .toFuture();
    }
}
