package io.rxmicro.examples.data.r2dbc.postgresql.variables.model;

import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import io.rxmicro.data.sql.r2dbc.detail.EntityFromR2DBCSQLDBConverter;

/**
 * Generated by rxmicro annotation processor
 *
 * @link https://rxmicro.io
 */
public final class $$EntityEntityFromR2DBCSQLDBConverter extends EntityFromR2DBCSQLDBConverter<Row, RowMetadata, Entity> {

    public Entity fromDB(final Row dbRow,
                         final RowMetadata metadata) {
        final Entity model = new Entity();
        model.id = dbRow.get(0, Long.class);
        model.value = dbRow.get(1, String.class);
        return model;
    }

    public Entity setIdValue(final Entity model,
                             final Row dbRow,
                             final RowMetadata metadata) {
        model.id = dbRow.get(0, Long.class);
        model.value = dbRow.get(1, String.class);
        return model;
    }
}
