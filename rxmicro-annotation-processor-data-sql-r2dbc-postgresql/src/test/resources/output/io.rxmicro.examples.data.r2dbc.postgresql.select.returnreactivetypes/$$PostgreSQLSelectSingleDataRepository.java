package io.rxmicro.examples.data.r2dbc.postgresql.select.returnreactivetypes;

import io.r2dbc.pool.ConnectionPool;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import io.rxmicro.data.sql.r2dbc.detail.RepositoryConnectionFactory;
import io.rxmicro.data.sql.r2dbc.detail.RepositoryConnectionPool;
import io.rxmicro.data.sql.r2dbc.postgresql.detail.AbstractPostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.select.returnreactivetypes.model.$$AccountEntityFromR2DBCSQLDBConverter;
import io.rxmicro.examples.data.r2dbc.postgresql.select.returnreactivetypes.model.Account;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$PostgreSQLSelectSingleDataRepository extends AbstractPostgreSQLRepository implements SelectSingleDataRepository {

    private final $$AccountEntityFromR2DBCSQLDBConverter accountEntityFromR2DBCSQLDBConverter =
            new $$AccountEntityFromR2DBCSQLDBConverter();

    private final RepositoryConnectionFactory connectionFactory;

    public $$PostgreSQLSelectSingleDataRepository(final ConnectionPool pool) {
        super(SelectSingleDataRepository.class);
        this.connectionFactory = new RepositoryConnectionPool(SelectSingleDataRepository.class, pool);
    }

    @Override
    public Mono<Account> findByEmail1(final String email) {
        // Original SQL statement:  'SELECT * FROM ${table} WHERE email = ?'
        final String generatedSQL = "SELECT first_name, last_name FROM account WHERE email = $1";
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL, email)
                        .flatMap(r -> Mono.from(r.map(accountEntityFromR2DBCSQLDBConverter::fromDB)))
                        .switchIfEmpty(close(c)
                                .then(Mono.empty())
                        )
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                );
    }

    @Override
    public CompletableFuture<Account> findByEmail2(final String email) {
        // Original SQL statement:  'SELECT * FROM ${table} WHERE email = ?'
        final String generatedSQL = "SELECT first_name, last_name FROM account WHERE email = $1";
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL, email)
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
    public CompletionStage<Account> findByEmail3(final String email) {
        // Original SQL statement:  'SELECT * FROM ${table} WHERE email = ?'
        final String generatedSQL = "SELECT first_name, last_name FROM account WHERE email = $1";
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL, email)
                        .flatMap(r -> Mono.from(r.map(accountEntityFromR2DBCSQLDBConverter::fromDB)))
                        .switchIfEmpty(close(c)
                                .then(Mono.empty())
                        )
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                )
                .switchIfEmpty(Mono.error(useOptionalExceptionSupplier(CompletionStage.class, Account.class)))
                .toFuture();
    }

    @Override
    public CompletableFuture<Optional<Account>> findByEmail4(final String email) {
        // Original SQL statement:  'SELECT * FROM ${table} WHERE email = ?'
        final String generatedSQL = "SELECT first_name, last_name FROM account WHERE email = $1";
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL, email)
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
    public CompletionStage<Optional<Account>> findByEmail5(final String email) {
        // Original SQL statement:  'SELECT * FROM ${table} WHERE email = ?'
        final String generatedSQL = "SELECT first_name, last_name FROM account WHERE email = $1";
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL, email)
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
    public Single<Account> findByEmail6(final String email) {
        // Original SQL statement:  'SELECT * FROM ${table} WHERE email = ?'
        final String generatedSQL = "SELECT first_name, last_name FROM account WHERE email = $1";
        return Single.fromPublisher(
                this.connectionFactory.create()
                        .flatMap(c -> executeStatement(c, generatedSQL, email)
                                .flatMap(r -> Mono.from(r.map(accountEntityFromR2DBCSQLDBConverter::fromDB)))
                                .switchIfEmpty(close(c)
                                        .then(Mono.empty())
                                )
                                .delayUntil(s -> close(c))
                                .onErrorResume(createCloseThenReturnErrorFallback(c))
                        )
        );
    }

    @Override
    public Maybe<Account> findByEmail7(final String email) {
        // Original SQL statement:  'SELECT * FROM ${table} WHERE email = ?'
        final String generatedSQL = "SELECT first_name, last_name FROM account WHERE email = $1";
        return Flowable.fromPublisher(
                this.connectionFactory.create()
                        .flatMap(c -> executeStatement(c, generatedSQL, email)
                                .flatMap(r -> Mono.from(r.map(accountEntityFromR2DBCSQLDBConverter::fromDB)))
                                .switchIfEmpty(close(c)
                                        .then(Mono.empty())
                                )
                                .delayUntil(s -> close(c))
                                .onErrorResume(createCloseThenReturnErrorFallback(c))
                        )
        ).firstElement();
    }
}
