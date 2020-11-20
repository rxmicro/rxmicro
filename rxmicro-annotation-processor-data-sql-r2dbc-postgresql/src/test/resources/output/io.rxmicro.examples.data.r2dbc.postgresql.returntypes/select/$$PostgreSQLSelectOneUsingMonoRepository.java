package io.rxmicro.examples.data.r2dbc.postgresql.returntypes.select;

import io.r2dbc.pool.ConnectionPool;
import io.rxmicro.data.sql.model.EntityFieldList;
import io.rxmicro.data.sql.model.EntityFieldMap;
import io.rxmicro.data.sql.r2dbc.detail.RepositoryConnectionFactory;
import io.rxmicro.data.sql.r2dbc.detail.RepositoryConnectionPool;
import io.rxmicro.data.sql.r2dbc.postgresql.detail.AbstractPostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.$$AccountEntityFromR2DBCSQLDBConverter;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.Account;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.Role;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$PostgreSQLSelectOneUsingMonoRepository extends AbstractPostgreSQLRepository implements SelectOneUsingMonoRepository {

    private final $$AccountEntityFromR2DBCSQLDBConverter accountEntityFromR2DBCSQLDBConverter =
            new $$AccountEntityFromR2DBCSQLDBConverter();

    private final RepositoryConnectionFactory connectionFactory;

    public $$PostgreSQLSelectOneUsingMonoRepository(final ConnectionPool pool) {
        super(SelectOneUsingMonoRepository.class);
        this.connectionFactory = new RepositoryConnectionPool(SelectOneUsingMonoRepository.class, pool);
    }

    @Override
    public Mono<Account> find01() {
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
                );
    }

    @Override
    public Mono<EntityFieldMap> find02() {
        // Original SQL statement:  'SELECT first_name, last_name FROM ${table} WHERE email = 'richard.hendricks@piedpiper.com''
        final String generatedSQL = "SELECT first_name, last_name FROM account WHERE email = 'richard.hendricks@piedpiper.com'";
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL)
                        .flatMap(r -> Mono.from(r.map(toEntityFieldMap())))
                        .switchIfEmpty(close(c)
                                .then(Mono.empty())
                        )
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                );
    }

    @Override
    public Mono<EntityFieldList> find03() {
        // Original SQL statement:  'SELECT first_name, last_name FROM ${table} WHERE email = 'richard.hendricks@piedpiper.com''
        final String generatedSQL = "SELECT first_name, last_name FROM account WHERE email = 'richard.hendricks@piedpiper.com'";
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL)
                        .flatMap(r -> Mono.from(r.map(toEntityFieldList())))
                        .switchIfEmpty(close(c)
                                .then(Mono.empty())
                        )
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                );
    }

    @Override
    public Mono<String> find04() {
        // Original SQL statement:  'SELECT email FROM ${table} WHERE email = 'richard.hendricks@piedpiper.com''
        final String generatedSQL = "SELECT email FROM account WHERE email = 'richard.hendricks@piedpiper.com'";
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL)
                        .flatMap(r -> Mono.from(r.map((row, meta) -> row.get(0, String.class))))
                        .switchIfEmpty(close(c)
                                .then(Mono.empty())
                        )
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                );
    }

    @Override
    public Mono<Role> find05() {
        // Original SQL statement:  'SELECT role FROM ${table} WHERE email = 'richard.hendricks@piedpiper.com''
        final String generatedSQL = "SELECT role FROM account WHERE email = 'richard.hendricks@piedpiper.com'";
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL)
                        .flatMap(r -> Mono.from(r.map((row, meta) -> row.get(0, Role.class))))
                        .switchIfEmpty(close(c)
                                .then(Mono.empty())
                        )
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                );
    }

    @Override
    public Mono<BigDecimal> find06() {
        // Original SQL statement:  'SELECT balance FROM ${table} WHERE email = 'richard.hendricks@piedpiper.com''
        final String generatedSQL = "SELECT balance FROM account WHERE email = 'richard.hendricks@piedpiper.com'";
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL)
                        .flatMap(r -> Mono.from(r.map((row, meta) -> row.get(0, BigDecimal.class))))
                        .switchIfEmpty(close(c)
                                .then(Mono.empty())
                        )
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                );
    }
}
