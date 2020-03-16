package io.rxmicro.examples.data.r2dbc.postgresql.returntypes.update;

import io.r2dbc.pool.ConnectionPool;
import io.reactivex.rxjava3.core.Completable;
import io.rxmicro.data.sql.r2dbc.postgresql.detail.AbstractPostgreSQLRepository;
import reactor.core.publisher.Mono;

/**
 * Generated by rxmicro annotation processor
 *
 * @link https://rxmicro.io
 */
public final class $$PostgreSQLUpdateOneEntityFieldsUsingCompletableRepository extends AbstractPostgreSQLRepository implements UpdateOneEntityFieldsUsingCompletableRepository {

    private final ConnectionPool pool;

    public $$PostgreSQLUpdateOneEntityFieldsUsingCompletableRepository(final ConnectionPool pool) {
        super(UpdateOneEntityFieldsUsingCompletableRepository.class);
        this.pool = pool;
    }

    @Override
    public Completable update01(final String firstName, final String lastName, final Long id) {
        // Original SQL statement:  'UPDATE ${table} SET first_name = ?, last_name = ? WHERE id = ?'
        final String generatedSQL = "UPDATE account SET first_name = $1, last_name = $2 WHERE id = $3";
        return Completable.fromPublisher(
                pool.create()
                        .flatMap(c -> executeStatement(c, generatedSQL, firstName, lastName, id)
                                .flatMap(r -> Mono.from(r.getRowsUpdated()))
                                
                                .delayUntil(s -> close(c))
                                .onErrorResume(e -> close(c)
                                        .then(Mono.error(e)))
                                
                        )
        );
    }
}
