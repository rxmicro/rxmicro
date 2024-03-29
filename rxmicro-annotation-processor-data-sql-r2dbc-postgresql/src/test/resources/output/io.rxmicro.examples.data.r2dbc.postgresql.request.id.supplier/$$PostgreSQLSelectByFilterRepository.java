package io.rxmicro.examples.data.r2dbc.postgresql.request.id.supplier;

import io.r2dbc.pool.ConnectionPool;
import io.rxmicro.data.sql.r2dbc.postgresql.detail.AbstractPostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.request.id.supplier.model.$$AccountEntityFromR2DBCSQLDBConverter;
import io.rxmicro.examples.data.r2dbc.postgresql.request.id.supplier.model.Account;
import io.rxmicro.examples.data.r2dbc.postgresql.request.id.supplier.model.Role;
import io.rxmicro.logger.RequestIdSupplier;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$PostgreSQLSelectByFilterRepository extends AbstractPostgreSQLRepository implements SelectByFilterRepository {

    private final $$AccountEntityFromR2DBCSQLDBConverter accountEntityFromR2DBCSQLDBConverter =
            new $$AccountEntityFromR2DBCSQLDBConverter();

    public $$PostgreSQLSelectByFilterRepository(final ConnectionPool pool) {
        super(SelectByFilterRepository.class, pool);
    }

    @Override
    public CompletableFuture<List<Account>> findByRole(final RequestIdSupplier requestIdSupplier, final Role role) {
        // Original SQL statement:  'SELECT * FROM ${table} WHERE role = ?'
        final String generatedSQL = "SELECT id, email, first_name, last_name, balance, role FROM account WHERE role = $1";
        return this.connectionFactory.create(requestIdSupplier)
                .flatMap(c -> executeStatement(c, generatedSQL, role)
                        .flatMap(r -> Flux.from(r.map(accountEntityFromR2DBCSQLDBConverter::fromDB))
                                .collectList()
                        )
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                )
                .toFuture();
    }

    @Override
    public CompletableFuture<List<Account>> findByFirstName(final RequestIdSupplier requestIdSupplier, final String firstName1, final String firstName2, final String firstName3) {
        // Original SQL statement:  'SELECT * FROM ${table} WHERE first_name = ? OR first_name = ? OR first_name = ?'
        final String generatedSQL = "SELECT id, email, first_name, last_name, balance, role FROM account WHERE first_name = $1 OR first_name = $2 OR first_name = $3";
        return this.connectionFactory.create(requestIdSupplier)
                .flatMap(c -> executeStatement(c, generatedSQL, firstName1, firstName2, firstName3)
                        .flatMap(r -> Flux.from(r.map(accountEntityFromR2DBCSQLDBConverter::fromDB))
                                .collectList()
                        )
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                )
                .toFuture();
    }

    @Override
    public CompletableFuture<List<Account>> findByBalance(final RequestIdSupplier requestIdSupplier, final BigDecimal minBalance, final BigDecimal maxBalance) {
        // Original SQL statement:  'SELECT * FROM ${table} WHERE balance BETWEEN ? AND ?'
        final String generatedSQL = "SELECT id, email, first_name, last_name, balance, role FROM account WHERE balance BETWEEN $1 AND $2";
        return this.connectionFactory.create(requestIdSupplier)
                .flatMap(c -> executeStatement(c, generatedSQL, minBalance, maxBalance)
                        .flatMap(r -> Flux.from(r.map(accountEntityFromR2DBCSQLDBConverter::fromDB))
                                .collectList()
                        )
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                )
                .toFuture();
    }

    @Override
    public CompletableFuture<List<Account>> findByFirstOrLastName(final RequestIdSupplier requestIdSupplier, final String name1, final String name2) {
        // Original SQL statement:  'SELECT * FROM ${table} WHERE first_name = ? OR last_name = ?'
        final String generatedSQL = "SELECT id, email, first_name, last_name, balance, role FROM account WHERE first_name = $1 OR last_name = $2";
        return this.connectionFactory.create(requestIdSupplier)
                .flatMap(c -> executeStatement(c, generatedSQL, name1, name2)
                        .flatMap(r -> Flux.from(r.map(accountEntityFromR2DBCSQLDBConverter::fromDB))
                                .collectList()
                        )
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                )
                .toFuture();
    }

    @Override
    public CompletableFuture<List<Account>> findByFirstOrLastName(final RequestIdSupplier requestIdSupplier, final String name) {
        // Original SQL statement:  'SELECT * FROM ${table} WHERE first_name ILIKE ? OR last_name ILIKE ?'
        final String generatedSQL = "SELECT id, email, first_name, last_name, balance, role FROM account WHERE first_name ILIKE $1 OR last_name ILIKE $2";
        return this.connectionFactory.create(requestIdSupplier)
                .flatMap(c -> executeStatement(c, generatedSQL, name, name)
                        .flatMap(r -> Flux.from(r.map(accountEntityFromR2DBCSQLDBConverter::fromDB))
                                .collectList()
                        )
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                )
                .toFuture();
    }
}
