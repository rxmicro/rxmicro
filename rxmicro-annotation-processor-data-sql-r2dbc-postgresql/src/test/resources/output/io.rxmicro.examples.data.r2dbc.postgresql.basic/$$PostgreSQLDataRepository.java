package io.rxmicro.examples.data.r2dbc.postgresql.basic;

import io.r2dbc.pool.ConnectionPool;
import io.rxmicro.data.sql.r2dbc.postgresql.detail.AbstractPostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.basic.entity.$$AccountEntityFromR2DBCSQLDBConverter;
import io.rxmicro.examples.data.r2dbc.postgresql.basic.entity.Account;
import reactor.core.publisher.Mono;

/**
 * Generated by rxmicro annotation processor
 *
 * @link https://rxmicro.io
 */
public final class $$PostgreSQLDataRepository extends AbstractPostgreSQLRepository implements DataRepository {

    private final $$AccountEntityFromR2DBCSQLDBConverter accountEntityFromR2DBCSQLDBConverter =
            new $$AccountEntityFromR2DBCSQLDBConverter();

    private final ConnectionPool pool;

    public $$PostgreSQLDataRepository(final ConnectionPool pool) {
        super(DataRepository.class);
        this.pool = pool;
    }

    @Override
    public Mono<Account> findByEmail(final String email) {
        // Original SQL statement:  'SELECT * FROM ${table} WHERE email = ?'
        final String generatedSQL = "SELECT first_name, last_name FROM account WHERE email = $1";
        return pool.create()
                .flatMap(c -> executeStatement(c, generatedSQL, email)
                        .flatMap(r -> Mono.from(r.map(accountEntityFromR2DBCSQLDBConverter::fromDB)))
                        .switchIfEmpty(close(c)
                                .then(Mono.empty()))
                        .delayUntil(s -> close(c))
                        .onErrorResume(e -> close(c)
                                .then(Mono.error(e)))
                );
    }
}
