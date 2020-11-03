package io.rxmicro.examples.data.r2dbc.postgresql.primary.keys.model;

import io.r2dbc.spi.Row;
import io.rxmicro.data.sql.r2dbc.detail.EntityToR2DBCSQLDBConverter;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$AccountEntityToR2DBCSQLDBConverter extends EntityToR2DBCSQLDBConverter<Account, Row> {

    public Object[] getInsertParams(final Account entity) {
        return new Object[]{
                entity.email,
                entity.firstName,
                entity.lastName,
                entity.balance,
                entity.role
        };
    }

    public Object[] getUpdateParams(final Account entity) {
        return new Object[]{
                entity.firstName,
                entity.lastName,
                entity.balance,
                entity.role,
                // primary key(s):
                entity.id
        };
    }

    public Object getPrimaryKey(final Account entity) {
        return entity.id;
    }
}
