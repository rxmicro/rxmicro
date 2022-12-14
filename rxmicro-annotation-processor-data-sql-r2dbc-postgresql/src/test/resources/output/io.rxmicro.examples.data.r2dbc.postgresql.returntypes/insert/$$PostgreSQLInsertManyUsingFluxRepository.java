package io.rxmicro.examples.data.r2dbc.postgresql.returntypes.insert;

import io.r2dbc.pool.ConnectionPool;
import io.rxmicro.data.sql.model.EntityFieldList;
import io.rxmicro.data.sql.model.EntityFieldMap;
import io.rxmicro.data.sql.r2dbc.postgresql.detail.AbstractPostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.$$AccountEntityFromR2DBCSQLDBConverter;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$PostgreSQLInsertManyUsingFluxRepository extends AbstractPostgreSQLRepository implements InsertManyUsingFluxRepository {

    private final $$AccountEntityFromR2DBCSQLDBConverter accountEntityFromR2DBCSQLDBConverter =
            new $$AccountEntityFromR2DBCSQLDBConverter();

    public $$PostgreSQLInsertManyUsingFluxRepository(final ConnectionPool pool) {
        super(InsertManyUsingFluxRepository.class, pool);
    }

    @Override
    public Flux<Account> insertAll01() {
        // Original SQL statement:  'INSERT INTO ${table} SELECT * FROM dump RETURNING *'
        final String generatedSQL = "INSERT INTO account SELECT * FROM dump RETURNING id, first_name, last_name";
        final Account resultEntity = new Account();
        return this.connectionFactory.create()
                .flatMapMany(c -> executeStatement(c, generatedSQL)
                        .flatMapMany(r -> Flux.from(r.map((row, meta) -> accountEntityFromR2DBCSQLDBConverter.setIdFirst_nameLast_name(resultEntity, row, meta))))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                        .concatWith(close(c)
                                .then(Mono.empty())
                        )
                );
    }

    @Override
    public Flux<EntityFieldMap> insertAll02() {
        // Original SQL statement:  'INSERT INTO ${table} SELECT * FROM dump RETURNING first_name, last_name'
        final String generatedSQL = "INSERT INTO account SELECT * FROM dump RETURNING first_name, last_name";
        return this.connectionFactory.create()
                .flatMapMany(c -> executeStatement(c, generatedSQL)
                        .flatMapMany(r -> Flux.from(r.map(toEntityFieldMap())))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                        .concatWith(close(c)
                                .then(Mono.empty())
                        )
                );
    }

    @Override
    public Flux<EntityFieldList> insertAll03() {
        // Original SQL statement:  'INSERT INTO ${table} SELECT * FROM dump RETURNING first_name, last_name'
        final String generatedSQL = "INSERT INTO account SELECT * FROM dump RETURNING first_name, last_name";
        return this.connectionFactory.create()
                .flatMapMany(c -> executeStatement(c, generatedSQL)
                        .flatMapMany(r -> Flux.from(r.map(toEntityFieldList())))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                        .concatWith(close(c)
                                .then(Mono.empty())
                        )
                );
    }
}
