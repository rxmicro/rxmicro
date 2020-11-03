package io.rxmicro.examples.data.r2dbc.postgresql.select.projection.model;

import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import io.rxmicro.data.sql.r2dbc.detail.EntityFromR2DBCSQLDBConverter;

import java.math.BigDecimal;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$AccountEntityFromR2DBCSQLDBConverter extends EntityFromR2DBCSQLDBConverter<Row, RowMetadata, Account> {

    public Account fromDB(final Row dbRow,
                          final RowMetadata metadata) {
        final Account model = new Account();
        model.id = dbRow.get(0, Long.class);
        model.email = dbRow.get(1, String.class);
        model.firstName = dbRow.get(2, String.class);
        model.lastName = dbRow.get(3, String.class);
        model.balance = dbRow.get(4, BigDecimal.class);
        model.role = dbRow.get(5, Role.class);
        return model;
    }

    public Account fromDBFirst_nameLast_name(final Row dbRow,
                                             final RowMetadata metadata) {
        final Account model = new Account();
        model.firstName = dbRow.get(0, String.class);
        model.lastName = dbRow.get(1, String.class);
        return model;
    }

    public Account fromDBIdEmailFirst_nameLast_nameBalance(final Row dbRow,
                                                           final RowMetadata metadata) {
        final Account model = new Account();
        model.id = dbRow.get(0, Long.class);
        model.email = dbRow.get(1, String.class);
        model.firstName = dbRow.get(2, String.class);
        model.lastName = dbRow.get(3, String.class);
        model.balance = dbRow.get(4, BigDecimal.class);
        return model;
    }

    public Account fromDBIdEmailLast_nameFirst_nameBalance(final Row dbRow,
                                                           final RowMetadata metadata) {
        final Account model = new Account();
        model.id = dbRow.get(0, Long.class);
        model.email = dbRow.get(1, String.class);
        model.lastName = dbRow.get(2, String.class);
        model.firstName = dbRow.get(3, String.class);
        model.balance = dbRow.get(4, BigDecimal.class);
        return model;
    }
}
