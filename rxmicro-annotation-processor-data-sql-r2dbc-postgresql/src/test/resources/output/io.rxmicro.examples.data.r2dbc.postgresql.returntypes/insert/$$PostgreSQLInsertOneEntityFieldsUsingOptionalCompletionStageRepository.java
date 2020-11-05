package io.rxmicro.examples.data.r2dbc.postgresql.returntypes.insert;

import io.r2dbc.pool.ConnectionPool;
import io.rxmicro.data.sql.model.EntityFieldList;
import io.rxmicro.data.sql.model.EntityFieldMap;
import io.rxmicro.data.sql.r2dbc.postgresql.detail.AbstractPostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.$$AccountEntityFromR2DBCSQLDBConverter;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.Account;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$PostgreSQLInsertOneEntityFieldsUsingOptionalCompletionStageRepository extends AbstractPostgreSQLRepository implements InsertOneEntityFieldsUsingOptionalCompletionStageRepository {

    private final $$AccountEntityFromR2DBCSQLDBConverter accountEntityFromR2DBCSQLDBConverter =
            new $$AccountEntityFromR2DBCSQLDBConverter();

    private final ConnectionPool pool;

    public $$PostgreSQLInsertOneEntityFieldsUsingOptionalCompletionStageRepository(final ConnectionPool pool) {
        super(InsertOneEntityFieldsUsingOptionalCompletionStageRepository.class);
        this.pool = pool;
    }

    @Override
    public CompletionStage<Optional<Account>> insert01(final String firstName, final String lastName) {
        // Original SQL statement:  'INSERT INTO ${table}(first_name, last_name) VALUES(?, ?) ON CONFLICT DO NOTHING RETURNING *'
        final String generatedSQL = "INSERT INTO account(first_name, last_name) VALUES($1, $2) ON CONFLICT DO NOTHING RETURNING id, first_name, last_name";
        final Object[] insertParams = {firstName, lastName};
        final Class<?>[] insertParamTypes = {String.class, String.class};
        final Account entity = new Account();
        return pool.create()
                .flatMap(c -> executeStatement(c, generatedSQL, insertParams, insertParamTypes)
                        .flatMap(r -> Mono.from(r.map((row, meta) -> accountEntityFromR2DBCSQLDBConverter.setIdFirst_nameLast_name(entity, row, meta))))
                        .switchIfEmpty(close(c)
                                .then(Mono.empty())
                        )
                        .delayUntil(s -> close(c))
                        .onErrorResume(e -> close(c)
                                .then(Mono.error(e))
                        )
                )
                .toFuture()
                .thenApply(a -> Optional.ofNullable(a));
    }

    @Override
    public CompletionStage<Optional<EntityFieldMap>> insert02(final String firstName, final String lastName) {
        // Original SQL statement:  'INSERT INTO ${table}(first_name, last_name) VALUES(?, ?) ON CONFLICT DO NOTHING RETURNING first_name, last_name'
        final String generatedSQL = "INSERT INTO account(first_name, last_name) VALUES($1, $2) ON CONFLICT DO NOTHING RETURNING first_name, last_name";
        final Object[] insertParams = {firstName, lastName};
        final Class<?>[] insertParamTypes = {String.class, String.class};
        return pool.create()
                .flatMap(c -> executeStatement(c, generatedSQL, insertParams, insertParamTypes)
                        .flatMap(r -> Mono.from(r.map(toEntityFieldMap())))
                        .switchIfEmpty(close(c)
                                .then(Mono.empty())
                        )
                        .delayUntil(s -> close(c))
                        .onErrorResume(e -> close(c)
                                .then(Mono.error(e))
                        )
                )
                .toFuture()
                .thenApply(a -> Optional.ofNullable(a));
    }

    @Override
    public CompletionStage<Optional<EntityFieldList>> insert03(final String firstName, final String lastName) {
        // Original SQL statement:  'INSERT INTO ${table}(first_name, last_name) VALUES(?, ?) ON CONFLICT DO NOTHING RETURNING first_name, last_name'
        final String generatedSQL = "INSERT INTO account(first_name, last_name) VALUES($1, $2) ON CONFLICT DO NOTHING RETURNING first_name, last_name";
        final Object[] insertParams = {firstName, lastName};
        final Class<?>[] insertParamTypes = {String.class, String.class};
        return pool.create()
                .flatMap(c -> executeStatement(c, generatedSQL, insertParams, insertParamTypes)
                        .flatMap(r -> Mono.from(r.map(toEntityFieldList())))
                        .switchIfEmpty(close(c)
                                .then(Mono.empty())
                        )
                        .delayUntil(s -> close(c))
                        .onErrorResume(e -> close(c)
                                .then(Mono.error(e))
                        )
                )
                .toFuture()
                .thenApply(a -> Optional.ofNullable(a));
    }
}
