package io.rxmicro.examples.data.r2dbc.postgresql.returntypes.update;

import io.r2dbc.pool.ConnectionPool;
import io.rxmicro.data.sql.model.EntityFieldList;
import io.rxmicro.data.sql.model.EntityFieldMap;
import io.rxmicro.data.sql.r2dbc.postgresql.detail.AbstractPostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.$$AccountEntityFromR2DBCSQLDBConverter;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.Account;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.concurrent.CompletionStage;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$PostgreSQLUpdateManyUsingCompletionStageListRepository extends AbstractPostgreSQLRepository implements UpdateManyUsingCompletionStageListRepository {

    private final $$AccountEntityFromR2DBCSQLDBConverter accountEntityFromR2DBCSQLDBConverter =
            new $$AccountEntityFromR2DBCSQLDBConverter();

    public $$PostgreSQLUpdateManyUsingCompletionStageListRepository(final ConnectionPool pool) {
        super(UpdateManyUsingCompletionStageListRepository.class, pool);
    }

    @Override
    public CompletionStage<List<Account>> updateAll01() {
        // Original SQL statement:  'UPDATE ${table} SET first_name = 'Russ', last_name = 'Hanneman' RETURNING *'
        final String generatedSQL = "UPDATE account SET first_name = 'Russ', last_name = 'Hanneman' RETURNING id, first_name, last_name";
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
    public CompletionStage<List<EntityFieldMap>> updateAll02() {
        // Original SQL statement:  'UPDATE ${table} SET first_name = 'Russ', last_name = 'Hanneman' RETURNING first_name, last_name'
        final String generatedSQL = "UPDATE account SET first_name = 'Russ', last_name = 'Hanneman' RETURNING first_name, last_name";
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL)
                        .flatMap(r -> Flux.from(r.map(toEntityFieldMap())).collectList())
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                )
                .toFuture();
    }

    @Override
    public CompletionStage<List<EntityFieldList>> updateAll03() {
        // Original SQL statement:  'UPDATE ${table} SET first_name = 'Russ', last_name = 'Hanneman' RETURNING first_name, last_name'
        final String generatedSQL = "UPDATE account SET first_name = 'Russ', last_name = 'Hanneman' RETURNING first_name, last_name";
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL)
                        .flatMap(r -> Flux.from(r.map(toEntityFieldList())).collectList())
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                )
                .toFuture();
    }
}
