package io.rxmicro.examples.data.r2dbc.postgresql.variables.model;

import io.r2dbc.spi.Row;
import io.rxmicro.data.sql.r2dbc.detail.EntityToR2DBCSQLDBConverter;

import static io.rxmicro.common.util.Requires.require;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$EntityEntityToR2DBCSQLDBConverter extends EntityToR2DBCSQLDBConverter<Entity, Row> {

    private static final Class<?>[] INSERT_PARAM_TYPES = {
            String.class
    };

    private static final Class<?>[] UPDATE_PARAM_TYPES = {
            String.class
    };

    public Class<?>[] getInsertParamTypes() {
        return INSERT_PARAM_TYPES;
    }

    public Object[] getInsertParams(final Entity entity) {
        return new Object[]{
                entity.value
        };
    }

    public Class<?>[] getUpdateParamTypes() {
        return UPDATE_PARAM_TYPES;
    }

    public Object[] getUpdateParams(final Entity entity) {
        return new Object[]{
                entity.value,
                // primary key(s):
                require(entity.id, "Primary key must be not null!")
        };
    }

    public Object getPrimaryKey(final Entity entity) {
        return require(entity.id, "Primary key must be not null!");
    }
}