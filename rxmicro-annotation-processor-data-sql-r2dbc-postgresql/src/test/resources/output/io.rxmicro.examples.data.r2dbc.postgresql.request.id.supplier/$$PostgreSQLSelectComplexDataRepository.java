package io.rxmicro.examples.data.r2dbc.postgresql.request.id.supplier;

import io.r2dbc.pool.ConnectionPool;
import io.rxmicro.data.SortOrder;
import io.rxmicro.data.sql.r2dbc.postgresql.detail.AbstractPostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.request.id.supplier.model.$$AccountEntityFromR2DBCSQLDBConverter;
import io.rxmicro.examples.data.r2dbc.postgresql.request.id.supplier.model.Account;
import io.rxmicro.examples.data.r2dbc.postgresql.request.id.supplier.model.Role;
import io.rxmicro.logger.RequestIdSupplier;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.data.sql.detail.SQLParams.joinEnumParams;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$PostgreSQLSelectComplexDataRepository extends AbstractPostgreSQLRepository implements SelectComplexDataRepository {

    private final $$AccountEntityFromR2DBCSQLDBConverter accountEntityFromR2DBCSQLDBConverter =
            new $$AccountEntityFromR2DBCSQLDBConverter();

    public $$PostgreSQLSelectComplexDataRepository(final ConnectionPool pool) {
        super(SelectComplexDataRepository.class, pool);
    }

    @Override
    public CompletableFuture<List<Account>> find01(final RequestIdSupplier requestIdSupplier, final String firstNameTemplate, final List<Role> roles, final BigDecimal balance, final SortOrder sortOrder, final int limit, final int offset) {
        // Original SQL statement:  'SELECT * FROM ${table} WHERE first_name ILIKE ? AND role IN (?) AND balance < ? ORDER BY(id ?, email ?) LIMIT ? OFFSET ?'
        final String generatedSQL = format("SELECT id, email, first_name, last_name, balance, role FROM account WHERE first_name ILIKE $1 AND role IN (?) AND balance < $2 ORDER BY(id ?, email ?) LIMIT $3 OFFSET $4", joinEnumParams(roles), sortOrder.sql(), sortOrder.sql());
        return this.connectionFactory.create(requestIdSupplier)
                .flatMap(c -> executeStatement(c, generatedSQL, firstNameTemplate, balance, limit, offset)
                        .flatMap(r -> Flux.from(r.map(accountEntityFromR2DBCSQLDBConverter::fromDB))
                                .collectList()
                        )
                        .delayUntil(s -> close(c))
                        .onErrorResume(createCloseThenReturnErrorFallback(c))
                )
                .toFuture();
    }
}
