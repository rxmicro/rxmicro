package io.rxmicro.examples.data.r2dbc.postgresql.returntypes.insert;

import io.r2dbc.pool.ConnectionPool;
import io.reactivex.rxjava3.core.Single;
import io.rxmicro.data.sql.model.EntityFieldList;
import io.rxmicro.data.sql.model.EntityFieldMap;
import io.rxmicro.data.sql.r2dbc.postgresql.detail.AbstractPostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.$$AccountEntityFromR2DBCSQLDBConverter;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.Account;
import reactor.core.publisher.Mono;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$PostgreSQLInsertOneEntityFieldsUsingSingleRepository extends AbstractPostgreSQLRepository implements InsertOneEntityFieldsUsingSingleRepository {

    private final $$AccountEntityFromR2DBCSQLDBConverter accountEntityFromR2DBCSQLDBConverter =
            new $$AccountEntityFromR2DBCSQLDBConverter();

    public $$PostgreSQLInsertOneEntityFieldsUsingSingleRepository(final ConnectionPool pool) {
        super(InsertOneEntityFieldsUsingSingleRepository.class, pool);
    }

    @Override
    public Single<Long> insert01(final String firstName, final String lastName) {
        // Original SQL statement:  'INSERT INTO ${table}(first_name, last_name) VALUES(?, ?)'
        final String generatedSQL = "INSERT INTO account(first_name, last_name) VALUES($1, $2)";
        final Object[] insertParams = {firstName, lastName};
        final Class<?>[] insertParamTypes = {String.class, String.class};
        return Single.fromPublisher(
                this.connectionFactory.create()
                        .flatMap(c -> executeStatement(c, generatedSQL, insertParams, insertParamTypes)
                                .flatMap(r -> Mono.from(r.getRowsUpdated()))
                                .delayUntil(s -> close(c))
                                .onErrorResume(createCloseThenReturnErrorFallback(c))
                        )
        );
    }

    @Override
    public Single<Boolean> insert02(final String firstName, final String lastName) {
        // Original SQL statement:  'INSERT INTO ${table}(first_name, last_name) VALUES(?, ?)'
        final String generatedSQL = "INSERT INTO account(first_name, last_name) VALUES($1, $2)";
        final Object[] insertParams = {firstName, lastName};
        final Class<?>[] insertParamTypes = {String.class, String.class};
        return Single.fromPublisher(
                this.connectionFactory.create()
                        .flatMap(c -> executeStatement(c, generatedSQL, insertParams, insertParamTypes)
                                .flatMap(r -> Mono.from(r.getRowsUpdated()))
                                .delayUntil(s -> close(c))
                                .onErrorResume(createCloseThenReturnErrorFallback(c))
                        )
        ).map(r -> r > 0);
    }

    @Override
    public Single<Account> insert03(final String firstName, final String lastName) {
        // Original SQL statement:  'INSERT INTO ${table}(first_name, last_name) VALUES(?, ?) RETURNING *'
        final String generatedSQL = "INSERT INTO account(first_name, last_name) VALUES($1, $2) RETURNING id, first_name, last_name";
        final Object[] insertParams = {firstName, lastName};
        final Class<?>[] insertParamTypes = {String.class, String.class};
        final Account resultEntity = new Account();
        return Single.fromPublisher(
                this.connectionFactory.create()
                        .flatMap(c -> executeStatement(c, generatedSQL, insertParams, insertParamTypes)
                                .flatMap(r -> Mono.from(r.map((row, meta) -> accountEntityFromR2DBCSQLDBConverter.setIdFirst_nameLast_name(resultEntity, row, meta))))
                                .delayUntil(s -> close(c))
                                .onErrorResume(createCloseThenReturnErrorFallback(c))
                                .switchIfEmpty(close(c)
                                        .then(Mono.empty())
                                )
                        )
        );
    }

    @Override
    public Single<EntityFieldMap> insert04(final String firstName, final String lastName) {
        // Original SQL statement:  'INSERT INTO ${table}(first_name, last_name) VALUES(?, ?) RETURNING first_name, last_name'
        final String generatedSQL = "INSERT INTO account(first_name, last_name) VALUES($1, $2) RETURNING first_name, last_name";
        final Object[] insertParams = {firstName, lastName};
        final Class<?>[] insertParamTypes = {String.class, String.class};
        return Single.fromPublisher(
                this.connectionFactory.create()
                        .flatMap(c -> executeStatement(c, generatedSQL, insertParams, insertParamTypes)
                                .flatMap(r -> Mono.from(r.map(toEntityFieldMap())))
                                .delayUntil(s -> close(c))
                                .onErrorResume(createCloseThenReturnErrorFallback(c))
                                .switchIfEmpty(close(c)
                                        .then(Mono.empty())
                                )
                        )
        );
    }

    @Override
    public Single<EntityFieldList> insert05(final String firstName, final String lastName) {
        // Original SQL statement:  'INSERT INTO ${table}(first_name, last_name) VALUES(?, ?) RETURNING first_name, last_name'
        final String generatedSQL = "INSERT INTO account(first_name, last_name) VALUES($1, $2) RETURNING first_name, last_name";
        final Object[] insertParams = {firstName, lastName};
        final Class<?>[] insertParamTypes = {String.class, String.class};
        return Single.fromPublisher(
                this.connectionFactory.create()
                        .flatMap(c -> executeStatement(c, generatedSQL, insertParams, insertParamTypes)
                                .flatMap(r -> Mono.from(r.map(toEntityFieldList())))
                                .delayUntil(s -> close(c))
                                .onErrorResume(createCloseThenReturnErrorFallback(c))
                                .switchIfEmpty(close(c)
                                        .then(Mono.empty())
                                )
                        )
        );
    }
}
