package io.rxmicro.examples.data.r2dbc.postgresql.returntypes.update;

import io.r2dbc.pool.ConnectionPool;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.rxmicro.data.sql.model.EntityFieldList;
import io.rxmicro.data.sql.model.EntityFieldMap;
import io.rxmicro.data.sql.r2dbc.detail.RepositoryConnectionFactory;
import io.rxmicro.data.sql.r2dbc.detail.RepositoryConnectionPool;
import io.rxmicro.data.sql.r2dbc.postgresql.detail.AbstractPostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.$$AccountEntityFromR2DBCSQLDBConverter;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.$$AccountEntityToR2DBCSQLDBConverter;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.Account;
import reactor.core.publisher.Mono;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$PostgreSQLUpdateOneEntityUsingMaybeRepository extends AbstractPostgreSQLRepository implements UpdateOneEntityUsingMaybeRepository {

    private final $$AccountEntityFromR2DBCSQLDBConverter accountEntityFromR2DBCSQLDBConverter =
            new $$AccountEntityFromR2DBCSQLDBConverter();

    private final $$AccountEntityToR2DBCSQLDBConverter accountEntityToR2DBCSQLDBConverter =
            new $$AccountEntityToR2DBCSQLDBConverter();

    private final RepositoryConnectionFactory connectionFactory;

    public $$PostgreSQLUpdateOneEntityUsingMaybeRepository(final ConnectionPool pool) {
        super(UpdateOneEntityUsingMaybeRepository.class);
        this.connectionFactory = new RepositoryConnectionPool(UpdateOneEntityUsingMaybeRepository.class, pool);
    }

    @Override
    public Maybe<Account> update01(final Account account) {
        // Original SQL statement:  'UPDATE ${table} SET ${updated-columns} WHERE ${by-id-filter} RETURNING *'
        final String generatedSQL = "UPDATE account SET first_name = $1, last_name = $2 WHERE id = $3 RETURNING id, first_name, last_name";
        final Object[] updateParams = accountEntityToR2DBCSQLDBConverter.getUpdateParams(account);
        final Class<?>[] updateParamTypes = accountEntityToR2DBCSQLDBConverter.getUpdateParamTypes();
        return Flowable.fromPublisher(
                this.connectionFactory.create()
                        .flatMap(c -> executeStatement(c, generatedSQL, updateParams, updateParamTypes)
                                .flatMap(r -> Mono.from(r.map((row, meta) -> accountEntityFromR2DBCSQLDBConverter.setIdFirst_nameLast_name(account, row, meta))))
                                .delayUntil(s -> close(c))
                                .onErrorResume(createCloseThenReturnErrorFallback(c))
                                .switchIfEmpty(close(c)
                                        .then(Mono.empty())
                                )
                        )
        ).firstElement();
    }

    @Override
    public Maybe<EntityFieldMap> update02(final Account account) {
        // Original SQL statement:  'UPDATE ${table} SET ${updated-columns} WHERE ${by-id-filter} RETURNING *'
        final String generatedSQL = "UPDATE account SET first_name = $1, last_name = $2 WHERE id = $3 RETURNING id, first_name, last_name";
        final Object[] updateParams = accountEntityToR2DBCSQLDBConverter.getUpdateParams(account);
        final Class<?>[] updateParamTypes = accountEntityToR2DBCSQLDBConverter.getUpdateParamTypes();
        return Flowable.fromPublisher(
                this.connectionFactory.create()
                        .flatMap(c -> executeStatement(c, generatedSQL, updateParams, updateParamTypes)
                                .flatMap(r -> Mono.from(r.map(toEntityFieldMap())))
                                .delayUntil(s -> close(c))
                                .onErrorResume(createCloseThenReturnErrorFallback(c))
                                .switchIfEmpty(close(c)
                                        .then(Mono.empty())
                                )
                        )
        ).firstElement();
    }

    @Override
    public Maybe<EntityFieldList> update03(final Account account) {
        // Original SQL statement:  'UPDATE ${table} SET ${updated-columns} WHERE ${by-id-filter} RETURNING *'
        final String generatedSQL = "UPDATE account SET first_name = $1, last_name = $2 WHERE id = $3 RETURNING id, first_name, last_name";
        final Object[] updateParams = accountEntityToR2DBCSQLDBConverter.getUpdateParams(account);
        final Class<?>[] updateParamTypes = accountEntityToR2DBCSQLDBConverter.getUpdateParamTypes();
        return Flowable.fromPublisher(
                this.connectionFactory.create()
                        .flatMap(c -> executeStatement(c, generatedSQL, updateParams, updateParamTypes)
                                .flatMap(r -> Mono.from(r.map(toEntityFieldList())))
                                .delayUntil(s -> close(c))
                                .onErrorResume(createCloseThenReturnErrorFallback(c))
                                .switchIfEmpty(close(c)
                                        .then(Mono.empty())
                                )
                        )
        ).firstElement();
    }
}
