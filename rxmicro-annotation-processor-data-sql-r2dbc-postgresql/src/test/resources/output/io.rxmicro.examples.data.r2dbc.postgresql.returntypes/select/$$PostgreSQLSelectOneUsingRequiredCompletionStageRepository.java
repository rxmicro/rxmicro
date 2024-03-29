package io.rxmicro.examples.data.r2dbc.postgresql.returntypes.select;

import io.r2dbc.pool.ConnectionPool;
import io.rxmicro.data.sql.model.EntityFieldList;
import io.rxmicro.data.sql.model.EntityFieldMap;
import io.rxmicro.data.sql.r2dbc.postgresql.detail.AbstractPostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.$$AccountEntityFromR2DBCSQLDBConverter;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.Account;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.Role;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.concurrent.CompletionStage;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$PostgreSQLSelectOneUsingRequiredCompletionStageRepository extends AbstractPostgreSQLRepository implements SelectOneUsingRequiredCompletionStageRepository {

    private final $$AccountEntityFromR2DBCSQLDBConverter accountEntityFromR2DBCSQLDBConverter =
            new $$AccountEntityFromR2DBCSQLDBConverter();

    public $$PostgreSQLSelectOneUsingRequiredCompletionStageRepository(final ConnectionPool pool) {
        super(SelectOneUsingRequiredCompletionStageRepository.class, pool);
    }

    @Override
    public CompletionStage<Account> find01() {
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
                .switchIfEmpty(Mono.error(useOptionalExceptionSupplier(CompletionStage.class, Account.class)))
                .toFuture();
    }

    @Override
    public CompletionStage<EntityFieldMap> find02() {
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
                )
                .switchIfEmpty(Mono.error(useOptionalExceptionSupplier(CompletionStage.class, EntityFieldMap.class)))
                .toFuture();
    }

    @Override
    public CompletionStage<EntityFieldList> find03() {
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
                )
                .switchIfEmpty(Mono.error(useOptionalExceptionSupplier(CompletionStage.class, EntityFieldList.class)))
                .toFuture();
    }

    @Override
    public CompletionStage<String> find04() {
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
                )
                .switchIfEmpty(Mono.error(useOptionalExceptionSupplier(CompletionStage.class, String.class)))
                .toFuture();
    }

    @Override
    public CompletionStage<Role> find05() {
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
                )
                .switchIfEmpty(Mono.error(useOptionalExceptionSupplier(CompletionStage.class, Role.class)))
                .toFuture();
    }

    @Override
    public CompletionStage<BigDecimal> find06() {
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
                )
                .switchIfEmpty(Mono.error(useOptionalExceptionSupplier(CompletionStage.class, BigDecimal.class)))
                .toFuture();
    }
}
