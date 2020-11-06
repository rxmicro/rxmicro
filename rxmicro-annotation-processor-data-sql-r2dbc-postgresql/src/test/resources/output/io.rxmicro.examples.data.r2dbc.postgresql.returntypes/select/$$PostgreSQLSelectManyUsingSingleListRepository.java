package io.rxmicro.examples.data.r2dbc.postgresql.returntypes.select;

import io.r2dbc.pool.ConnectionPool;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.rxmicro.data.sql.model.EntityFieldList;
import io.rxmicro.data.sql.model.EntityFieldMap;
import io.rxmicro.data.sql.r2dbc.postgresql.detail.AbstractPostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.$$AccountEntityFromR2DBCSQLDBConverter;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.Account;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.Role;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$PostgreSQLSelectManyUsingSingleListRepository extends AbstractPostgreSQLRepository implements SelectManyUsingSingleListRepository {

    private final $$AccountEntityFromR2DBCSQLDBConverter accountEntityFromR2DBCSQLDBConverter =
            new $$AccountEntityFromR2DBCSQLDBConverter();

    private final ConnectionPool pool;

    public $$PostgreSQLSelectManyUsingSingleListRepository(final ConnectionPool pool) {
        super(SelectManyUsingSingleListRepository.class);
        this.pool = pool;
    }

    @Override
    public Single<List<Account>> findAll01() {
        // Original SQL statement:  'SELECT first_name, last_name FROM ${table} ORDER BY id'
        final String generatedSQL = "SELECT first_name, last_name FROM account ORDER BY id";
        return Flowable.fromPublisher(
                pool.create()
                        .flatMapMany(c -> executeStatement(c, generatedSQL)
                                .flatMapMany(r -> Flux.from(r.map(accountEntityFromR2DBCSQLDBConverter::fromDBFirst_nameLast_name)))
                                .onErrorResume(createCloseThenReturnErrorFallback(c))
                                .concatWith(close(c)
                                        .then(Mono.empty())
                                )
                        )
        ).collect(ArrayList::new, (l, e) -> l.add(e));
    }

    @Override
    public Single<List<EntityFieldMap>> findAll02() {
        // Original SQL statement:  'SELECT first_name, last_name FROM ${table} ORDER BY id'
        final String generatedSQL = "SELECT first_name, last_name FROM account ORDER BY id";
        return Flowable.fromPublisher(
                pool.create()
                        .flatMapMany(c -> executeStatement(c, generatedSQL)
                                .flatMapMany(r -> Flux.from(r.map(toEntityFieldMap())))
                                .onErrorResume(createCloseThenReturnErrorFallback(c))
                                .concatWith(close(c)
                                        .then(Mono.empty())
                                )
                        )
        ).collect(ArrayList::new, (l, e) -> l.add(e));
    }

    @Override
    public Single<List<EntityFieldList>> findAll03() {
        // Original SQL statement:  'SELECT first_name, last_name FROM ${table} ORDER BY id'
        final String generatedSQL = "SELECT first_name, last_name FROM account ORDER BY id";
        return Flowable.fromPublisher(
                pool.create()
                        .flatMapMany(c -> executeStatement(c, generatedSQL)
                                .flatMapMany(r -> Flux.from(r.map(toEntityFieldList())))
                                .onErrorResume(createCloseThenReturnErrorFallback(c))
                                .concatWith(close(c)
                                        .then(Mono.empty())
                                )
                        )
        ).collect(ArrayList::new, (l, e) -> l.add(e));
    }

    @Override
    public Single<List<String>> findAll04() {
        // Original SQL statement:  'SELECT email FROM ${table} ORDER BY id'
        final String generatedSQL = "SELECT email FROM account ORDER BY id";
        return Flowable.fromPublisher(
                pool.create()
                        .flatMapMany(c -> executeStatement(c, generatedSQL)
                                .flatMapMany(r -> Flux.from(r.map((row, meta) -> row.get(0, String.class))))
                                .onErrorResume(createCloseThenReturnErrorFallback(c))
                                .concatWith(close(c)
                                        .then(Mono.empty())
                                )
                        )
        ).collect(ArrayList::new, (l, e) -> l.add(e));
    }

    @Override
    public Single<List<Role>> findAll05() {
        // Original SQL statement:  'SELECT DISTINCT role::text FROM ${table} ORDER BY role'
        final String generatedSQL = "SELECT DISTINCT role::text FROM account ORDER BY role";
        return Flowable.fromPublisher(
                pool.create()
                        .flatMapMany(c -> executeStatement(c, generatedSQL)
                                .flatMapMany(r -> Flux.from(r.map((row, meta) -> row.get(0, Role.class))))
                                .onErrorResume(createCloseThenReturnErrorFallback(c))
                                .concatWith(close(c)
                                        .then(Mono.empty())
                                )
                        )
        ).collect(ArrayList::new, (l, e) -> l.add(e));
    }

    @Override
    public Single<List<BigDecimal>> findAll06() {
        // Original SQL statement:  'SELECT DISTINCT balance FROM ${table}'
        final String generatedSQL = "SELECT DISTINCT balance FROM account";
        return Flowable.fromPublisher(
                pool.create()
                        .flatMapMany(c -> executeStatement(c, generatedSQL)
                                .flatMapMany(r -> Flux.from(r.map((row, meta) -> row.get(0, BigDecimal.class))))
                                .onErrorResume(createCloseThenReturnErrorFallback(c))
                                .concatWith(close(c)
                                        .then(Mono.empty())
                                )
                        )
        ).collect(ArrayList::new, (l, e) -> l.add(e));
    }
}
