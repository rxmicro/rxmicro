package io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model;

import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import io.rxmicro.data.sql.r2dbc.detail.EntityFromR2DBCSQLDBConverter;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$AccountEntityFromR2DBCSQLDBConverter extends EntityFromR2DBCSQLDBConverter<Row, RowMetadata, Account> {

    public Account fromDBFirst_nameLast_name(final Row dbRow,
                                             final RowMetadata metadata) {
        final Account model = new Account();
        model.firstName = dbRow.get(0, String.class);
        model.lastName = dbRow.get(1, String.class);
        return model;
    }

    public Account setId(final Account model,
                         final Row dbRow,
                         final RowMetadata metadata) {
        model.id = dbRow.get(0, Long.class);
        return model;
    }

    public Account setIdFirst_nameLast_name(final Account model,
                                            final Row dbRow,
                                            final RowMetadata metadata) {
        model.id = dbRow.get(0, Long.class);
        model.firstName = dbRow.get(1, String.class);
        model.lastName = dbRow.get(2, String.class);
        return model;
    }
}
