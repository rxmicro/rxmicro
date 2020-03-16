package io.rxmicro.examples.data.r2dbc.postgresql.select.custom.model;

import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import io.rxmicro.data.sql.r2dbc.detail.EntityFromR2DBCSQLDBConverter;

import java.math.BigDecimal;

/**
 * Generated by rxmicro annotation processor
 *
 * @link https://rxmicro.io
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
        return model;
    }

    public Account fromDBFirst_nameLast_name(final Row dbRow,
                                             final RowMetadata metadata) {
        final Account model = new Account();
        model.firstName = dbRow.get(0, String.class);
        model.lastName = dbRow.get(1, String.class);
        return model;
    }

    public Account fromDBLast_nameFirst_name(final Row dbRow,
                                             final RowMetadata metadata) {
        final Account model = new Account();
        model.lastName = dbRow.get(0, String.class);
        model.firstName = dbRow.get(1, String.class);
        return model;
    }
}
