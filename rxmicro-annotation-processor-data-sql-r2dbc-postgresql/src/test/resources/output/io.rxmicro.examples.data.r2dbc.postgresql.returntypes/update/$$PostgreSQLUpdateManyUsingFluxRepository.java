package io.rxmicro.examples.data.r2dbc.postgresql.returntypes.update;

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
public final class $$PostgreSQLUpdateManyUsingFluxRepository extends AbstractPostgreSQLRepository implements UpdateManyUsingFluxRepository {

    private final $$AccountEntityFromR2DBCSQLDBConverter accountEntityFromR2DBCSQLDBConverter =
            new $$AccountEntityFromR2DBCSQLDBConverter();

    public $$PostgreSQLUpdateManyUsingFluxRepository(final ConnectionPool pool) {
        super(UpdateManyUsingFluxRepository.class, pool);
    }

    @Override
    public Flux<Account> updateAll01() {
        // Original SQL statement:  'UPDATE ${table} SET first_name = 'Russ', last_name = 'Hanneman' RETURNING *'
        final String generatedSQL = "UPDATE account SET first_name = 'Russ', last_name = 'Hanneman' RETURNING id, first_name, last_name";
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
    public Flux<EntityFieldMap> updateAll02() {
        // Original SQL statement:  'UPDATE ${table} SET first_name = 'Russ', last_name = 'Hanneman' RETURNING first_name, last_name'
        final String generatedSQL = "UPDATE account SET first_name = 'Russ', last_name = 'Hanneman' RETURNING first_name, last_name";
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
    public Flux<EntityFieldList> updateAll03() {
        // Original SQL statement:  'UPDATE ${table} SET first_name = 'Russ', last_name = 'Hanneman' RETURNING first_name, last_name'
        final String generatedSQL = "UPDATE account SET first_name = 'Russ', last_name = 'Hanneman' RETURNING first_name, last_name";
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
