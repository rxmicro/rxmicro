package io.rxmicro.examples.data.r2dbc.postgresql.returntypes.update;

import io.r2dbc.pool.ConnectionPool;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.rxmicro.data.sql.model.EntityFieldList;
import io.rxmicro.data.sql.model.EntityFieldMap;
import io.rxmicro.data.sql.r2dbc.postgresql.detail.AbstractPostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.$$AccountEntityFromR2DBCSQLDBConverter;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.Account;
import reactor.core.publisher.Mono;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$PostgreSQLUpdateOneEntityFieldsUsingMaybeRepository extends AbstractPostgreSQLRepository implements UpdateOneEntityFieldsUsingMaybeRepository {

    private final $$AccountEntityFromR2DBCSQLDBConverter accountEntityFromR2DBCSQLDBConverter =
            new $$AccountEntityFromR2DBCSQLDBConverter();

    public $$PostgreSQLUpdateOneEntityFieldsUsingMaybeRepository(final ConnectionPool pool) {
        super(UpdateOneEntityFieldsUsingMaybeRepository.class, pool);
    }

    @Override
    public Maybe<Account> update01(final String firstName, final String lastName, final Long id) {
        // Original SQL statement:  'UPDATE ${table} SET first_name = ?, last_name = ? WHERE id = ? RETURNING *'
        final String generatedSQL = "UPDATE account SET first_name = $1, last_name = $2 WHERE id = $3 RETURNING id, first_name, last_name";
        final Object[] updateParams = {firstName, lastName, id};
        final Class<?>[] updateParamTypes = {String.class, String.class, Long.class};
        final Account resultEntity = new Account();
        return Flowable.fromPublisher(
                this.connectionFactory.create()
                        .flatMap(c -> executeStatement(c, generatedSQL, updateParams, updateParamTypes)
                                .flatMap(r -> Mono.from(r.map((row, meta) -> accountEntityFromR2DBCSQLDBConverter.setIdFirst_nameLast_name(resultEntity, row, meta))))
                                .delayUntil(s -> close(c))
                                .onErrorResume(createCloseThenReturnErrorFallback(c))
                                .switchIfEmpty(close(c)
                                        .then(Mono.empty())
                                )
                        )
        ).firstElement();
    }

    @Override
    public Maybe<EntityFieldMap> update02(final String firstName, final String lastName, final Long id) {
        // Original SQL statement:  'UPDATE ${table} SET first_name = ?, last_name = ? WHERE id = ? RETURNING first_name, last_name'
        final String generatedSQL = "UPDATE account SET first_name = $1, last_name = $2 WHERE id = $3 RETURNING first_name, last_name";
        final Object[] updateParams = {firstName, lastName, id};
        final Class<?>[] updateParamTypes = {String.class, String.class, Long.class};
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
    public Maybe<EntityFieldList> update03(final String firstName, final String lastName, final Long id) {
        // Original SQL statement:  'UPDATE ${table} SET first_name = ?, last_name = ? WHERE id = ? RETURNING first_name, last_name'
        final String generatedSQL = "UPDATE account SET first_name = $1, last_name = $2 WHERE id = $3 RETURNING first_name, last_name";
        final Object[] updateParams = {firstName, lastName, id};
        final Class<?>[] updateParamTypes = {String.class, String.class, Long.class};
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
