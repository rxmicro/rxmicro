package io.rxmicro.examples.data.r2dbc.postgresql.primary.keys.model;

import io.r2dbc.spi.Row;
import io.rxmicro.data.sql.r2dbc.detail.EntityToR2DBCSQLDBConverter;

/**
 * Generated by rxmicro annotation processor
 *
 * @link https://rxmicro.io
 */
public final class $$ProductEntityToR2DBCSQLDBConverter extends EntityToR2DBCSQLDBConverter<Product, Row> {

    public Object[] getInsertParams(final Product entity) {
        return new Object[]{
                entity.id,
                entity.name,
                entity.price,
                entity.count
        };
    }

    public Object[] getUpdateParams(final Product entity) {
        return new Object[]{
                entity.name,
                entity.price,
                entity.count,
                // primary key(s):
                entity.id
        };
    }

    public Object getPrimaryKey(final Product entity) {
        return entity.id;
    }
}
