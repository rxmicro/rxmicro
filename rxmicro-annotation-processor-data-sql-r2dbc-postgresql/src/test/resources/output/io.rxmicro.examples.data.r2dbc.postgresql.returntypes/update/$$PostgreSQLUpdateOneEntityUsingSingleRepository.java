package io.rxmicro.examples.data.r2dbc.postgresql.returntypes.update;

import io.r2dbc.pool.ConnectionPool;
import io.reactivex.rxjava3.core.Single;
import io.rxmicro.data.sql.model.EntityFieldList;
import io.rxmicro.data.sql.model.EntityFieldMap;
import io.rxmicro.data.sql.r2dbc.postgresql.detail.AbstractPostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.$$AccountEntityToR2DBCSQLDBConverter;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.Account;
import reactor.core.publisher.Mono;

/**
 * Generated by rxmicro annotation processor
 *
 * @link https://rxmicro.io
 */
public final class $$PostgreSQLUpdateOneEntityUsingSingleRepository extends AbstractPostgreSQLRepository implements UpdateOneEntityUsingSingleRepository {

    private final $$AccountEntityToR2DBCSQLDBConverter accountEntityToR2DBCSQLDBConverter =
            new $$AccountEntityToR2DBCSQLDBConverter();

    private final ConnectionPool pool;

    public $$PostgreSQLUpdateOneEntityUsingSingleRepository(final ConnectionPool pool) {
        super(UpdateOneEntityUsingSingleRepository.class);
        this.pool = pool;
    }

    @Override
    public Single<Integer> update01(final Account account) {
        // Original SQL statement:  'UPDATE ${table} SET ${updated-columns} WHERE ${by-id-filter}'
        final String generatedSQL = "UPDATE account SET first_name = $1, last_name = $2 WHERE id = $3";
        final Object[] updateParams = accountEntityToR2DBCSQLDBConverter.getUpdateParams(account);
        return Single.fromPublisher(
                pool.create()
                        .flatMap(c -> executeStatement(c, generatedSQL, updateParams)
                                .flatMap(r -> Mono.from(r.getRowsUpdated()))
                                
                                .delayUntil(s -> close(c))
                                .onErrorResume(e -> close(c)
                                        .then(Mono.error(e)))
                                
                        )
        );
    }

    @Override
    public Single<Boolean> update02(final Account account) {
        // Original SQL statement:  'UPDATE ${table} SET ${updated-columns} WHERE ${by-id-filter}'
        final String generatedSQL = "UPDATE account SET first_name = $1, last_name = $2 WHERE id = $3";
        final Object[] updateParams = accountEntityToR2DBCSQLDBConverter.getUpdateParams(account);
        return Single.fromPublisher(
                pool.create()
                        .flatMap(c -> executeStatement(c, generatedSQL, updateParams)
                                .flatMap(r -> Mono.from(r.getRowsUpdated()))
                                
                                .delayUntil(s -> close(c))
                                .onErrorResume(e -> close(c)
                                        .then(Mono.error(e)))
                                
                        )
        ).map(r -> r > 0);
    }

    @Override
    public Single<Account> update03(final Account account) {
        // Original SQL statement:  'UPDATE ${table} SET ${updated-columns} WHERE ${by-id-filter} RETURNING *'
        final String generatedSQL = "UPDATE account SET first_name = $1, last_name = $2 WHERE id = $3 RETURNING id, first_name, last_name";
        final Object[] updateParams = accountEntityToR2DBCSQLDBConverter.getUpdateParams(account);
        return Single.fromPublisher(
                pool.create()
                        .flatMap(c -> executeStatement(c, generatedSQL, updateParams)
                                .flatMap(r -> Mono.from(r.map((row, meta) -> accountEntityToR2DBCSQLDBConverter.setIdFirst_nameLast_name(account, row, meta))))
                                .switchIfEmpty(close(c)
                                        .then(Mono.empty()))
                                .delayUntil(s -> close(c))
                                .onErrorResume(e -> close(c)
                                        .then(Mono.error(e)))
                        )
        );
    }

    @Override
    public Single<EntityFieldMap> update04(final Account account) {
        // Original SQL statement:  'UPDATE ${table} SET ${updated-columns} WHERE ${by-id-filter} RETURNING *'
        final String generatedSQL = "UPDATE account SET first_name = $1, last_name = $2 WHERE id = $3 RETURNING id, first_name, last_name";
        final Object[] updateParams = accountEntityToR2DBCSQLDBConverter.getUpdateParams(account);
        return Single.fromPublisher(
                pool.create()
                        .flatMap(c -> executeStatement(c, generatedSQL, updateParams)
                                .flatMap(r -> Mono.from(r.map(toEntityFieldMap())))
                                .switchIfEmpty(close(c)
                                        .then(Mono.empty()))
                                .delayUntil(s -> close(c))
                                .onErrorResume(e -> close(c)
                                        .then(Mono.error(e)))
                        )
        );
    }

    @Override
    public Single<EntityFieldList> update05(final Account account) {
        // Original SQL statement:  'UPDATE ${table} SET ${updated-columns} WHERE ${by-id-filter} RETURNING *'
        final String generatedSQL = "UPDATE account SET first_name = $1, last_name = $2 WHERE id = $3 RETURNING id, first_name, last_name";
        final Object[] updateParams = accountEntityToR2DBCSQLDBConverter.getUpdateParams(account);
        return Single.fromPublisher(
                pool.create()
                        .flatMap(c -> executeStatement(c, generatedSQL, updateParams)
                                .flatMap(r -> Mono.from(r.map(toEntityFieldList())))
                                .switchIfEmpty(close(c)
                                        .then(Mono.empty()))
                                .delayUntil(s -> close(c))
                                .onErrorResume(e -> close(c)
                                        .then(Mono.error(e)))
                        )
        );
    }
}
