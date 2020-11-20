package io.rxmicro.examples.data.r2dbc.postgresql.returntypes.delete;

import io.r2dbc.pool.ConnectionPool;
import io.rxmicro.data.sql.model.EntityFieldList;
import io.rxmicro.data.sql.model.EntityFieldMap;
import io.rxmicro.data.sql.r2dbc.detail.RepositoryConnectionFactory;
import io.rxmicro.data.sql.r2dbc.detail.RepositoryConnectionPool;
import io.rxmicro.data.sql.r2dbc.postgresql.detail.AbstractPostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.$$AccountEntityFromR2DBCSQLDBConverter;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.Account;
import reactor.core.publisher.Mono;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$PostgreSQLDeleteOneEntityFieldsUsingMonoRepository extends AbstractPostgreSQLRepository implements DeleteOneEntityFieldsUsingMonoRepository {

    private final $$AccountEntityFromR2DBCSQLDBConverter accountEntityFromR2DBCSQLDBConverter =
            new $$AccountEntityFromR2DBCSQLDBConverter();

    private final RepositoryConnectionFactory connectionFactory;

    public $$PostgreSQLDeleteOneEntityFieldsUsingMonoRepository(final ConnectionPool pool) {
        super(DeleteOneEntityFieldsUsingMonoRepository.class);
        this.connectionFactory = new RepositoryConnectionPool(DeleteOneEntityFieldsUsingMonoRepository.class, pool);
    }

    @Override
    public Mono<Void> delete01(final Long id) {
        // Original SQL statement:  'DELETE FROM ${table} WHERE id = ?'
        final String generatedSQL = "DELETE FROM account WHERE id = $1";
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL, id)
                        .flatMap(r -> Mono.from(r.getRowsUpdated()))
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                )
                .then();
    }

    @Override
    public Mono<Integer> delete02(final Long id) {
        // Original SQL statement:  'DELETE FROM ${table} WHERE id = ?'
        final String generatedSQL = "DELETE FROM account WHERE id = $1";
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL, id)
                        .flatMap(r -> Mono.from(r.getRowsUpdated()))
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                );
    }

    @Override
    public Mono<Boolean> delete03(final Long id) {
        // Original SQL statement:  'DELETE FROM ${table} WHERE id = ?'
        final String generatedSQL = "DELETE FROM account WHERE id = $1";
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL, id)
                        .flatMap(r -> Mono.from(r.getRowsUpdated()))
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                )
                .map(r -> r > 0);
    }

    @Override
    public Mono<Account> delete04(final Long id) {
        // Original SQL statement:  'DELETE FROM ${table} WHERE id = ? RETURNING *'
        final String generatedSQL = "DELETE FROM account WHERE id = $1 RETURNING id, first_name, last_name";
        final Account resultEntity = new Account();
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL, id)
                        .flatMap(r -> Mono.from(r.map((row, meta) -> accountEntityFromR2DBCSQLDBConverter.setIdFirst_nameLast_name(resultEntity, row, meta))))
                        .switchIfEmpty(close(c)
                                .then(Mono.empty())
                        )
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                );
    }

    @Override
    public Mono<EntityFieldMap> delete05(final Long id) {
        // Original SQL statement:  'DELETE FROM ${table} WHERE id = ? RETURNING first_name, last_name'
        final String generatedSQL = "DELETE FROM account WHERE id = $1 RETURNING first_name, last_name";
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL, id)
                        .flatMap(r -> Mono.from(r.map(toEntityFieldMap())))
                        .switchIfEmpty(close(c)
                                .then(Mono.empty())
                        )
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                );
    }

    @Override
    public Mono<EntityFieldList> delete06(final Long id) {
        // Original SQL statement:  'DELETE FROM ${table} WHERE id = ? RETURNING first_name, last_name'
        final String generatedSQL = "DELETE FROM account WHERE id = $1 RETURNING first_name, last_name";
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL, id)
                        .flatMap(r -> Mono.from(r.map(toEntityFieldList())))
                        .switchIfEmpty(close(c)
                                .then(Mono.empty())
                        )
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                );
    }
}
