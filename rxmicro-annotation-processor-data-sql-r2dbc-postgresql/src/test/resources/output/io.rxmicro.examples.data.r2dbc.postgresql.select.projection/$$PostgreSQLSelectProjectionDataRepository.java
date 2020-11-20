package io.rxmicro.examples.data.r2dbc.postgresql.select.projection;

import io.r2dbc.pool.ConnectionPool;
import io.rxmicro.data.sql.r2dbc.detail.RepositoryConnectionFactory;
import io.rxmicro.data.sql.r2dbc.detail.RepositoryConnectionPool;
import io.rxmicro.data.sql.r2dbc.postgresql.detail.AbstractPostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.select.projection.model.$$AccountEntityFromR2DBCSQLDBConverter;
import io.rxmicro.examples.data.r2dbc.postgresql.select.projection.model.Account;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$PostgreSQLSelectProjectionDataRepository extends AbstractPostgreSQLRepository implements SelectProjectionDataRepository {

    private final $$AccountEntityFromR2DBCSQLDBConverter accountEntityFromR2DBCSQLDBConverter =
            new $$AccountEntityFromR2DBCSQLDBConverter();

    private final RepositoryConnectionFactory connectionFactory;

    public $$PostgreSQLSelectProjectionDataRepository(final ConnectionPool pool) {
        super(SelectProjectionDataRepository.class);
        this.connectionFactory = new RepositoryConnectionPool(SelectProjectionDataRepository.class, pool);
    }

    @Override
    public CompletableFuture<Account> findAllColumns() {
        // Original SQL statement:  'SELECT * FROM ${table} WHERE email = 'richard.hendricks@piedpiper.com''
        final String generatedSQL = "SELECT id, email, first_name, last_name, balance, role FROM account WHERE email = 'richard.hendricks@piedpiper.com'";
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL)
                        .flatMap(r -> Mono.from(r.map(accountEntityFromR2DBCSQLDBConverter::fromDB)))
                        .switchIfEmpty(close(c)
                                .then(Mono.empty())
                        )
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                )
                .switchIfEmpty(Mono.error(useOptionalExceptionSupplier(CompletableFuture.class, Account.class)))
                .toFuture();
    }

    @Override
    public CompletableFuture<Account> findAllColumnsExceptRole1() {
        // Original SQL statement:  'SELECT id, email, first_name, last_name, balance FROM ${table} WHERE email = 'richard.hendricks@piedpiper.com''
        final String generatedSQL = "SELECT id, email, first_name, last_name, balance FROM account WHERE email = 'richard.hendricks@piedpiper.com'";
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL)
                        .flatMap(r -> Mono.from(r.map(accountEntityFromR2DBCSQLDBConverter::fromDBIdEmailFirst_nameLast_nameBalance)))
                        .switchIfEmpty(close(c)
                                .then(Mono.empty())
                        )
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                )
                .switchIfEmpty(Mono.error(useOptionalExceptionSupplier(CompletableFuture.class, Account.class)))
                .toFuture();
    }

    @Override
    public CompletableFuture<Account> findAllColumnsExceptRole2() {
        // Original SQL statement:  'SELECT id, email, last_name, first_name, balance FROM ${table} WHERE email = 'richard.hendricks@piedpiper.com''
        final String generatedSQL = "SELECT id, email, last_name, first_name, balance FROM account WHERE email = 'richard.hendricks@piedpiper.com'";
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL)
                        .flatMap(r -> Mono.from(r.map(accountEntityFromR2DBCSQLDBConverter::fromDBIdEmailLast_nameFirst_nameBalance)))
                        .switchIfEmpty(close(c)
                                .then(Mono.empty())
                        )
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                )
                .switchIfEmpty(Mono.error(useOptionalExceptionSupplier(CompletableFuture.class, Account.class)))
                .toFuture();
    }

    @Override
    public CompletableFuture<Account> findAllColumnsExceptRole3() {
        // Original SQL statement:  'SELECT 1 as id, 'richard.hendricks@piedpiper.com' as email, 'Hendricks' as last_name, 'Richard' as first_name, 70000.00 as balance'
        final String generatedSQL = "SELECT 1 as id, 'richard.hendricks@piedpiper.com' as email, 'Hendricks' as last_name, 'Richard' as first_name, 70000.00 as balance";
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL)
                        .flatMap(r -> Mono.from(r.map(accountEntityFromR2DBCSQLDBConverter::fromDBIdEmailLast_nameFirst_nameBalance)))
                        .switchIfEmpty(close(c)
                                .then(Mono.empty())
                        )
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                )
                .switchIfEmpty(Mono.error(useOptionalExceptionSupplier(CompletableFuture.class, Account.class)))
                .toFuture();
    }

    @Override
    public CompletableFuture<Account> findFirstAndLastName() {
        // Original SQL statement:  'SELECT first_name, last_name FROM ${table} WHERE email = 'richard.hendricks@piedpiper.com''
        final String generatedSQL = "SELECT first_name, last_name FROM account WHERE email = 'richard.hendricks@piedpiper.com'";
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL)
                        .flatMap(r -> Mono.from(r.map(accountEntityFromR2DBCSQLDBConverter::fromDBFirst_nameLast_name)))
                        .switchIfEmpty(close(c)
                                .then(Mono.empty())
                        )
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                )
                .switchIfEmpty(Mono.error(useOptionalExceptionSupplier(CompletableFuture.class, Account.class)))
                .toFuture();
    }

    @Override
    public CompletableFuture<Account> findModifiedColumns() {
        // Original SQL statement:  'SELECT id, '***@***' as email, upper(last_name) as last_name, first_name, (20000 + 50000.00) as balance FROM ${table} WHERE email = 'richard.hendricks@piedpiper.com''
        final String generatedSQL = "SELECT id, '***@***' as email, upper(last_name) as last_name, first_name, (20000 + 50000.00) as balance FROM account WHERE email = 'richard.hendricks@piedpiper.com'";
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL)
                        .flatMap(r -> Mono.from(r.map(accountEntityFromR2DBCSQLDBConverter::fromDBIdEmailLast_nameFirst_nameBalance)))
                        .switchIfEmpty(close(c)
                                .then(Mono.empty())
                        )
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                )
                .switchIfEmpty(Mono.error(useOptionalExceptionSupplier(CompletableFuture.class, Account.class)))
                .toFuture();
    }
}
