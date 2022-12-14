package io.rxmicro.examples.data.r2dbc.postgresql.partial.implementation;

import io.r2dbc.pool.ConnectionPool;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$PostgreSQLDataRepository extends AbstractDataRepository implements DataRepository {

    public $$PostgreSQLDataRepository(final ConnectionPool pool) {
        super(DataRepository.class, pool);
    }

    @Override
    public CompletableFuture<Long> generatedMethod() {
        // Original SQL statement:  'SELECT 1 + 1'
        final String generatedSQL = "SELECT 1 + 1";
        return this.connectionFactory.create()
                .flatMap(c -> executeStatement(c, generatedSQL)
                        .flatMap(r -> Mono.from(r.map((row, meta) -> row.get(0, Long.class))))
                        .switchIfEmpty(close(c)
                                .then(Mono.empty())
                        )
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                )
                .switchIfEmpty(Mono.error(useOptionalExceptionSupplier(CompletableFuture.class, Long.class)))
                .toFuture();
    }
}
