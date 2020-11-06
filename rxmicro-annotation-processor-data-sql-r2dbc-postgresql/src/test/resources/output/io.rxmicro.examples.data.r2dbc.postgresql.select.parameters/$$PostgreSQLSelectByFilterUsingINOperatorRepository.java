package io.rxmicro.examples.data.r2dbc.postgresql.select.parameters;

import io.r2dbc.pool.ConnectionPool;
import io.rxmicro.data.sql.r2dbc.postgresql.detail.AbstractPostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.select.parameters.model.$$AccountEntityFromR2DBCSQLDBConverter;
import io.rxmicro.examples.data.r2dbc.postgresql.select.parameters.model.Account;
import io.rxmicro.examples.data.r2dbc.postgresql.select.parameters.model.Role;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.data.sql.detail.SQLParams.joinEnumParams;
import static io.rxmicro.data.sql.detail.SQLParams.joinParams;
import static io.rxmicro.data.sql.detail.SQLParams.joinStringParams;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$PostgreSQLSelectByFilterUsingINOperatorRepository extends AbstractPostgreSQLRepository implements SelectByFilterUsingINOperatorRepository {

    private final $$AccountEntityFromR2DBCSQLDBConverter accountEntityFromR2DBCSQLDBConverter =
            new $$AccountEntityFromR2DBCSQLDBConverter();

    private final ConnectionPool pool;

    public $$PostgreSQLSelectByFilterUsingINOperatorRepository(final ConnectionPool pool) {
        super(SelectByFilterUsingINOperatorRepository.class);
        this.pool = pool;
    }

    @Override
    public CompletableFuture<List<Account>> findByRole() {
        // Original SQL statement:  'SELECT * FROM ${table} WHERE role IN ('CEO' ::role, 'Systems_Architect' ::role)'
        final String generatedSQL = "SELECT first_name, last_name FROM account WHERE role IN ('CEO' ::role, 'Systems_Architect' ::role)";
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
    public CompletableFuture<List<Account>> findNotBlockedAccount() {
        // Original SQL statement:  'SELECT * FROM ${table} WHERE email NOT IN (SELECT email FROM blocked_accounts)'
        final String generatedSQL = "SELECT first_name, last_name FROM account WHERE email NOT IN (SELECT email FROM blocked_accounts)";
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
    public CompletableFuture<Optional<Account>> findByEmail(final String email) {
        // Original SQL statement:  'SELECT * FROM ${table} WHERE email in ?'
        final String generatedSQL = format("SELECT first_name, last_name FROM account WHERE email in (?)", "'" + email + "'");
        return pool.create()
                .flatMap(c -> executeStatement(c, generatedSQL)
                        .flatMap(r -> Mono.from(r.map(accountEntityFromR2DBCSQLDBConverter::fromDB)))
                        .switchIfEmpty(close(c)
                                .then(Mono.empty())
                        )
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                )
                .toFuture()
                .thenApply(a -> Optional.ofNullable(a));
    }

    @Override
    public CompletableFuture<List<Account>> findByEmail(final List<String> emails) {
        // Original SQL statement:  'SELECT * FROM ${table} WHERE email in ?'
        final String generatedSQL = format("SELECT first_name, last_name FROM account WHERE email in (?)", joinStringParams(emails));
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
    public CompletableFuture<List<Account>> findByRole(final Role role) {
        // Original SQL statement:  'SELECT * FROM ${table} WHERE role in (?)'
        final String generatedSQL = format("SELECT first_name, last_name FROM account WHERE role in (?)", "'" + role.name() + "'");
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
    public CompletableFuture<List<Account>> findByRole(final List<Role> roles) {
        // Original SQL statement:  'SELECT * FROM ${table} WHERE role in (?)'
        final String generatedSQL = format("SELECT first_name, last_name FROM account WHERE role in (?)", joinEnumParams(roles));
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
    public CompletableFuture<List<Account>> findByBalance(final BigDecimal balance) {
        // Original SQL statement:  'SELECT * FROM ${table} WHERE balance in (?)'
        final String generatedSQL = format("SELECT first_name, last_name FROM account WHERE balance in (?)", balance);
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
    public CompletableFuture<List<Account>> findByBalance(final List<BigDecimal> balances) {
        // Original SQL statement:  'SELECT * FROM ${table} WHERE balance in ?'
        final String generatedSQL = format("SELECT first_name, last_name FROM account WHERE balance in (?)", joinParams(balances));
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
}
