package io.rxmicro.examples.data.r2dbc.postgresql.transactional.model;

import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import io.rxmicro.data.sql.r2dbc.detail.EntityFromR2DBCSQLDBConverter;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$OrderEntityFromR2DBCSQLDBConverter extends EntityFromR2DBCSQLDBConverter<Row, RowMetadata, Order> {

    public Order setId(final Order model,
                       final Row dbRow,
                       final RowMetadata metadata) {
        model.id = dbRow.get(0, Long.class);
        return model;
    }
}
