package io.rxmicro.examples.data.r2dbc.postgresql.model.fields.direct;

import io.r2dbc.pool.ConnectionPool;
import io.rxmicro.data.sql.r2dbc.detail.RepositoryConnectionFactory;
import io.rxmicro.data.sql.r2dbc.detail.RepositoryConnectionPool;
import io.rxmicro.data.sql.r2dbc.postgresql.detail.AbstractPostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.model.fields.direct.model.$$EntityEntityFromR2DBCSQLDBConverter;
import io.rxmicro.examples.data.r2dbc.postgresql.model.fields.direct.model.$$EntityEntityToR2DBCSQLDBConverter;
import io.rxmicro.examples.data.r2dbc.postgresql.model.fields.direct.model.Entity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$PostgreSQLDirectDataRepository extends AbstractPostgreSQLRepository implements DirectDataRepository {

    private final $$EntityEntityFromR2DBCSQLDBConverter entityEntityFromR2DBCSQLDBConverter =
            new $$EntityEntityFromR2DBCSQLDBConverter();

    private final $$EntityEntityToR2DBCSQLDBConverter entityEntityToR2DBCSQLDBConverter =
            new $$EntityEntityToR2DBCSQLDBConverter();

    private final RepositoryConnectionFactory connectionFactory;

    public $$PostgreSQLDirectDataRepository(final ConnectionPool pool) {
        super(DirectDataRepository.class);
        this.connectionFactory = new RepositoryConnectionPool(DirectDataRepository.class, pool);
    }

    @Override
    public CompletableFuture<List<Entity>> findAll() {
        // Original SQL statement:  'SELECT * FROM ${table}'
        final String generatedSQL = "SELECT id, status, aBoolean, aByte, aShort, aInteger, aLong, bigInteger, aFloat, aDouble, bigDecimal, character, string, instant, localTime, localDate, localDateTime, offsetDateTime, zonedDateTime, inetAddress, uuid FROM direct.entity";
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL)
                        .flatMap(r -> Flux.from(r.map(entityEntityFromR2DBCSQLDBConverter::fromDB))
                                .collectList()
                        )
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                )
                .toFuture();
    }

    @Override
    public CompletableFuture<Void> insert(final Entity entity) {
        // Original SQL statement:  'INSERT INTO ${table}(${inserted-columns}) VALUES(${values})'
        final String generatedSQL = "INSERT INTO direct.entity(status, aBoolean, aByte, aShort, aInteger, aLong, bigInteger, aFloat, aDouble, bigDecimal, character, string, instant, localTime, localDate, localDateTime, offsetDateTime, zonedDateTime, inetAddress, uuid) VALUES($1, $2, $3, $4, $5, $6, $7, $8, $9, $10, $11, $12, $13, $14, $15, $16, $17, $18, $19, $20)";
        final Object[] insertParams = entityEntityToR2DBCSQLDBConverter.getInsertParams(entity);
        final Class<?>[] insertParamTypes = entityEntityToR2DBCSQLDBConverter.getInsertParamTypes();
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL, insertParams, insertParamTypes)
                        .flatMap(r -> Mono.from(r.getRowsUpdated()))
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                )
                .switchIfEmpty(Mono.defer(() -> Mono.error(useOptionalExceptionSupplier(CompletableFuture.class, Void.class))))
                .toFuture()
                .thenApply(r -> null);
    }

    @Override
    public CompletableFuture<Void> update(final Entity entity) {
        // Original SQL statement:  'UPDATE ${table} SET ${updated-columns} WHERE ${by-id-filter}'
        final String generatedSQL = "UPDATE direct.entity SET status = $1, aBoolean = $2, aByte = $3, aShort = $4, aInteger = $5, aLong = $6, bigInteger = $7, aFloat = $8, aDouble = $9, bigDecimal = $10, character = $11, string = $12, instant = $13, localTime = $14, localDate = $15, localDateTime = $16, offsetDateTime = $17, zonedDateTime = $18, inetAddress = $19, uuid = $20 WHERE id = $21";
        final Object[] updateParams = entityEntityToR2DBCSQLDBConverter.getUpdateParams(entity);
        final Class<?>[] updateParamTypes = entityEntityToR2DBCSQLDBConverter.getUpdateParamTypes();
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL, updateParams, updateParamTypes)
                        .flatMap(r -> Mono.from(r.getRowsUpdated()))
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                )
                .switchIfEmpty(Mono.defer(() -> Mono.error(useOptionalExceptionSupplier(CompletableFuture.class, Void.class))))
                .toFuture()
                .thenApply(r -> null);
    }

    @Override
    public CompletableFuture<Void> delete(final Entity entity) {
        // Original SQL statement:  'DELETE FROM ${table} WHERE ${by-id-filter}'
        final String generatedSQL = "DELETE FROM direct.entity WHERE id = $1";
        final Object primaryKey = entityEntityToR2DBCSQLDBConverter.getPrimaryKey(entity);
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL, primaryKey)
                        .flatMap(r -> Mono.from(r.getRowsUpdated()))
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                )
                .switchIfEmpty(Mono.defer(() -> Mono.error(useOptionalExceptionSupplier(CompletableFuture.class, Void.class))))
                .toFuture()
                .thenApply(r -> null);
    }
}
