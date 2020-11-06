package io.rxmicro.examples.data.r2dbc.postgresql.returntypes.select;

import io.r2dbc.pool.ConnectionPool;
import io.rxmicro.data.sql.model.EntityFieldList;
import io.rxmicro.data.sql.model.EntityFieldMap;
import io.rxmicro.data.sql.r2dbc.postgresql.detail.AbstractPostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.$$AccountEntityFromR2DBCSQLDBConverter;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.Account;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.Role;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$PostgreSQLSelectManyUsingMonoListRepository extends AbstractPostgreSQLRepository implements SelectManyUsingMonoListRepository {

    private final $$AccountEntityFromR2DBCSQLDBConverter accountEntityFromR2DBCSQLDBConverter =
            new $$AccountEntityFromR2DBCSQLDBConverter();

    private final ConnectionPool pool;

    public $$PostgreSQLSelectManyUsingMonoListRepository(final ConnectionPool pool) {
        super(SelectManyUsingMonoListRepository.class);
        this.pool = pool;
    }

    @Override
    public Mono<List<Account>> findAll01() {
        // Original SQL statement:  'SELECT first_name, last_name FROM ${table} ORDER BY id'
        final String generatedSQL = "SELECT first_name, last_name FROM account ORDER BY id";
        return pool.create()
                .flatMap(c -> executeStatement(c, generatedSQL)
                        .flatMap(r -> Flux.from(r.map(accountEntityFromR2DBCSQLDBConverter::fromDBFirst_nameLast_name))
                                .collectList()
                        )
                        .delayUntil(s -> close(c))
                        .onErrorResume(e -> close(c)
                                .then(Mono.error(e))
                        )
                );
    }

    @Override
    public Mono<List<EntityFieldMap>> findAll02() {
        // Original SQL statement:  'SELECT first_name, last_name FROM ${table} ORDER BY id'
        final String generatedSQL = "SELECT first_name, last_name FROM account ORDER BY id";
        return pool.create()
                .flatMap(c -> executeStatement(c, generatedSQL)
                        .flatMap(r -> Flux.from(r.map(toEntityFieldMap()))
                                .collectList()
                        )
                        .delayUntil(s -> close(c))
                        .onErrorResume(e -> close(c)
                                .then(Mono.error(e))
                        )
                );
    }

    @Override
    public Mono<List<EntityFieldList>> findAll03() {
        // Original SQL statement:  'SELECT first_name, last_name FROM ${table} ORDER BY id'
        final String generatedSQL = "SELECT first_name, last_name FROM account ORDER BY id";
        return pool.create()
                .flatMap(c -> executeStatement(c, generatedSQL)
                        .flatMap(r -> Flux.from(r.map(toEntityFieldList()))
                                .collectList()
                        )
                        .delayUntil(s -> close(c))
                        .onErrorResume(e -> close(c)
                                .then(Mono.error(e))
                        )
                );
    }

    @Override
    public Mono<List<String>> findAll04() {
        // Original SQL statement:  'SELECT email FROM ${table} ORDER BY id'
        final String generatedSQL = "SELECT email FROM account ORDER BY id";
        return pool.create()
                .flatMap(c -> executeStatement(c, generatedSQL)
                        .flatMap(r -> Flux.from(r.map((row, meta) -> row.get(0, String.class)))
                                .collectList()
                        )
                        .delayUntil(s -> close(c))
                        .onErrorResume(e -> close(c)
                                .then(Mono.error(e))
                        )
                );
    }

    @Override
    public Mono<List<Role>> findAll05() {
        // Original SQL statement:  'SELECT DISTINCT role::text FROM ${table} ORDER BY role'
        final String generatedSQL = "SELECT DISTINCT role::text FROM account ORDER BY role";
        return pool.create()
                .flatMap(c -> executeStatement(c, generatedSQL)
                        .flatMap(r -> Flux.from(r.map((row, meta) -> row.get(0, Role.class)))
                                .collectList()
                        )
                        .delayUntil(s -> close(c))
                        .onErrorResume(e -> close(c)
                                .then(Mono.error(e))
                        )
                );
    }

    @Override
    public Mono<List<BigDecimal>> findAll06() {
        // Original SQL statement:  'SELECT DISTINCT balance FROM ${table}'
        final String generatedSQL = "SELECT DISTINCT balance FROM account";
        return pool.create()
                .flatMap(c -> executeStatement(c, generatedSQL)
                        .flatMap(r -> Flux.from(r.map((row, meta) -> row.get(0, BigDecimal.class)))
                                .collectList()
                        )
                        .delayUntil(s -> close(c))
                        .onErrorResume(e -> close(c)
                                .then(Mono.error(e))
                        )
                );
    }
}
