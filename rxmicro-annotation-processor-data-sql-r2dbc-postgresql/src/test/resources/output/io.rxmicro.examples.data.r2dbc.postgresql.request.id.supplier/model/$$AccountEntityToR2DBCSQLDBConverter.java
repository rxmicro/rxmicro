package io.rxmicro.examples.data.r2dbc.postgresql.request.id.supplier.model;

import io.r2dbc.spi.Row;
import io.rxmicro.data.sql.r2dbc.detail.EntityToR2DBCSQLDBConverter;

import java.math.BigDecimal;

import static io.rxmicro.common.util.Requires.require;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$AccountEntityToR2DBCSQLDBConverter extends EntityToR2DBCSQLDBConverter<Account, Row> {

    private static final Class<?>[] INSERT_PARAM_TYPES = {
            String.class,
            String.class,
            String.class,
            BigDecimal.class,
            Role.class
    };

    private static final Class<?>[] UPDATE_PARAM_TYPES = {
            String.class,
            String.class,
            BigDecimal.class,
            Role.class
    };

    public Class<?>[] getInsertParamTypes() {
        return INSERT_PARAM_TYPES;
    }

    public Object[] getInsertParams(final Account entity) {
        return new Object[]{
                entity.email,
                entity.firstName,
                entity.lastName,
                entity.balance,
                entity.role
        };
    }

    public Class<?>[] getUpdateParamTypes() {
        return UPDATE_PARAM_TYPES;
    }

    public Object[] getUpdateParams(final Account entity) {
        return new Object[]{
                entity.firstName,
                entity.lastName,
                entity.balance,
                entity.role,
                // primary key(s):
                require(entity.id, "Primary key must be not null!")
        };
    }

    public Object getPrimaryKey(final Account entity) {
        return require(entity.id, "Primary key must be not null!");
    }
}
