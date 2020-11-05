package io.rxmicro.examples.data.r2dbc.postgresql.returntypes.delete;

import io.r2dbc.pool.ConnectionPool;
import io.reactivex.rxjava3.core.Single;
import io.rxmicro.data.sql.model.EntityFieldList;
import io.rxmicro.data.sql.model.EntityFieldMap;
import io.rxmicro.data.sql.r2dbc.postgresql.detail.AbstractPostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.$$AccountEntityFromR2DBCSQLDBConverter;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.$$AccountEntityToR2DBCSQLDBConverter;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.Account;
import reactor.core.publisher.Mono;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$PostgreSQLDeleteOneEntityUsingSingleRepository extends AbstractPostgreSQLRepository implements DeleteOneEntityUsingSingleRepository {

    private final $$AccountEntityFromR2DBCSQLDBConverter accountEntityFromR2DBCSQLDBConverter =
            new $$AccountEntityFromR2DBCSQLDBConverter();

    private final $$AccountEntityToR2DBCSQLDBConverter accountEntityToR2DBCSQLDBConverter =
            new $$AccountEntityToR2DBCSQLDBConverter();

    private final ConnectionPool pool;

    public $$PostgreSQLDeleteOneEntityUsingSingleRepository(final ConnectionPool pool) {
        super(DeleteOneEntityUsingSingleRepository.class);
        this.pool = pool;
    }

    @Override
    public Single<Integer> delete01(final Account account) {
        // Original SQL statement:  'DELETE FROM ${table} WHERE ${by-id-filter}'
        final String generatedSQL = "DELETE FROM account WHERE id = $1";
        final Object primaryKey = accountEntityToR2DBCSQLDBConverter.getPrimaryKey(account);
        return Single.fromPublisher(
                pool.create()
                        .flatMap(c -> executeStatement(c, generatedSQL, primaryKey)
                                .flatMap(r -> Mono.from(r.getRowsUpdated()))
                                .delayUntil(s -> close(c))
                                .onErrorResume(e -> close(c)
                                        .then(Mono.error(e))
                                )
                        )
        );
    }

    @Override
    public Single<Boolean> delete02(final Account account) {
        // Original SQL statement:  'DELETE FROM ${table} WHERE ${by-id-filter}'
        final String generatedSQL = "DELETE FROM account WHERE id = $1";
        final Object primaryKey = accountEntityToR2DBCSQLDBConverter.getPrimaryKey(account);
        return Single.fromPublisher(
                pool.create()
                        .flatMap(c -> executeStatement(c, generatedSQL, primaryKey)
                                .flatMap(r -> Mono.from(r.getRowsUpdated()))
                                .delayUntil(s -> close(c))
                                .onErrorResume(e -> close(c)
                                        .then(Mono.error(e))
                                )
                        )
        ).map(r -> r > 0);
    }

    @Override
    public Single<Account> delete03(final Account account) {
        // Original SQL statement:  'DELETE FROM ${table} WHERE ${by-id-filter} RETURNING *'
        final String generatedSQL = "DELETE FROM account WHERE id = $1 RETURNING id, first_name, last_name";
        final Object primaryKey = accountEntityToR2DBCSQLDBConverter.getPrimaryKey(account);
        return Single.fromPublisher(
                pool.create()
                        .flatMap(c -> executeStatement(c, generatedSQL, primaryKey)
                                .flatMap(r -> Mono.from(r.map((row, meta) -> accountEntityFromR2DBCSQLDBConverter.setIdFirst_nameLast_name(account, row, meta))))
                                .switchIfEmpty(close(c)
                                        .then(Mono.empty())
                                )
                                .delayUntil(s -> close(c))
                                .onErrorResume(e -> close(c)
                                        .then(Mono.error(e))
                                )
                        )
        );
    }

    @Override
    public Single<EntityFieldMap> delete04(final Account account) {
        // Original SQL statement:  'DELETE FROM ${table} WHERE ${by-id-filter} RETURNING *'
        final String generatedSQL = "DELETE FROM account WHERE id = $1 RETURNING id, first_name, last_name";
        final Object primaryKey = accountEntityToR2DBCSQLDBConverter.getPrimaryKey(account);
        return Single.fromPublisher(
                pool.create()
                        .flatMap(c -> executeStatement(c, generatedSQL, primaryKey)
                                .flatMap(r -> Mono.from(r.map(toEntityFieldMap())))
                                .switchIfEmpty(close(c)
                                        .then(Mono.empty())
                                )
                                .delayUntil(s -> close(c))
                                .onErrorResume(e -> close(c)
                                        .then(Mono.error(e))
                                )
                        )
        );
    }

    @Override
    public Single<EntityFieldList> delete05(final Account account) {
        // Original SQL statement:  'DELETE FROM ${table} WHERE ${by-id-filter} RETURNING *'
        final String generatedSQL = "DELETE FROM account WHERE id = $1 RETURNING id, first_name, last_name";
        final Object primaryKey = accountEntityToR2DBCSQLDBConverter.getPrimaryKey(account);
        return Single.fromPublisher(
                pool.create()
                        .flatMap(c -> executeStatement(c, generatedSQL, primaryKey)
                                .flatMap(r -> Mono.from(r.map(toEntityFieldList())))
                                .switchIfEmpty(close(c)
                                        .then(Mono.empty())
                                )
                                .delayUntil(s -> close(c))
                                .onErrorResume(e -> close(c)
                                        .then(Mono.error(e))
                                )
                        )
        );
    }
}
