package io.rxmicro.examples.data.r2dbc.postgresql.select.returnmodeltypes;

import io.r2dbc.pool.ConnectionPool;
import io.rxmicro.data.sql.model.EntityFieldList;
import io.rxmicro.data.sql.model.EntityFieldMap;
import io.rxmicro.data.sql.r2dbc.postgresql.detail.AbstractPostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.select.returnmodeltypes.model.$$AccountEntityFromR2DBCSQLDBConverter;
import io.rxmicro.examples.data.r2dbc.postgresql.select.returnmodeltypes.model.Account;
import io.rxmicro.examples.data.r2dbc.postgresql.select.returnmodeltypes.model.Role;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$PostgreSQLSelectManyDataRepository extends AbstractPostgreSQLRepository implements SelectManyDataRepository {

    private final $$AccountEntityFromR2DBCSQLDBConverter accountEntityFromR2DBCSQLDBConverter =
            new $$AccountEntityFromR2DBCSQLDBConverter();

    private final ConnectionPool pool;

    public $$PostgreSQLSelectManyDataRepository(final ConnectionPool pool) {
        super(SelectManyDataRepository.class);
        this.pool = pool;
    }

    @Override
    public CompletableFuture<List<Account>> findAllAccounts() {
        // Original SQL statement:  'SELECT first_name, last_name FROM ${table} ORDER BY id'
        final String generatedSQL = "SELECT first_name, last_name FROM account ORDER BY id";
        return pool.create()
                .flatMap(c -> executeStatement(c, generatedSQL)
                        .flatMap(r -> Flux.from(r.map(accountEntityFromR2DBCSQLDBConverter::fromDB))
                                .collectList()
                        )
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                )
                .toFuture();
    }

    @Override
    public CompletableFuture<List<EntityFieldMap>> findAllEntityFieldMapList() {
        // Original SQL statement:  'SELECT first_name, last_name FROM ${table} ORDER BY id'
        final String generatedSQL = "SELECT first_name, last_name FROM account ORDER BY id";
        return pool.create()
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
    public CompletableFuture<List<EntityFieldList>> findAllEntityFieldList() {
        // Original SQL statement:  'SELECT first_name, last_name FROM ${table} ORDER BY id'
        final String generatedSQL = "SELECT first_name, last_name FROM account ORDER BY id";
        return pool.create()
                .flatMap(c -> executeStatement(c, generatedSQL)
                        .flatMap(r -> Flux.from(r.map(toEntityFieldList()))
                                .collectList()
                        )
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                )
                .toFuture();
    }

    @Override
    public CompletableFuture<List<String>> findAllEmails() {
        // Original SQL statement:  'SELECT email FROM ${table} ORDER BY id'
        final String generatedSQL = "SELECT email FROM account ORDER BY id";
        return pool.create()
                .flatMap(c -> executeStatement(c, generatedSQL)
                        .flatMap(r -> Flux.from(r.map((row, meta) -> row.get(0, String.class)))
                                .collectList()
                        )
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                )
                .toFuture();
    }

    @Override
    public CompletableFuture<List<Role>> findAllRoles() {
        // Original SQL statement:  'SELECT DISTINCT role::text FROM ${table} ORDER BY role'
        final String generatedSQL = "SELECT DISTINCT role::text FROM account ORDER BY role";
        return pool.create()
                .flatMap(c -> executeStatement(c, generatedSQL)
                        .flatMap(r -> Flux.from(r.map((row, meta) -> row.get(0, Role.class)))
                                .collectList()
                        )
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                )
                .toFuture();
    }

    @Override
    public CompletableFuture<List<BigDecimal>> findAllBalances() {
        // Original SQL statement:  'SELECT DISTINCT balance FROM ${table} ORDER BY balance'
        final String generatedSQL = "SELECT DISTINCT balance FROM account ORDER BY balance";
        return pool.create()
                .flatMap(c -> executeStatement(c, generatedSQL)
                        .flatMap(r -> Flux.from(r.map((row, meta) -> row.get(0, BigDecimal.class)))
                                .collectList()
                        )
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                )
                .toFuture();
    }
}
