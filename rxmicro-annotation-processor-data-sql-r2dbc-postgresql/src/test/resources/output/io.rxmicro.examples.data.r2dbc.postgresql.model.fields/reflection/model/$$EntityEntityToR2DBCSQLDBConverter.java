package io.rxmicro.examples.data.r2dbc.postgresql.model.fields.reflection.model;

import io.r2dbc.spi.Row;
import io.rxmicro.data.sql.r2dbc.detail.EntityToR2DBCSQLDBConverter;
import io.rxmicro.examples.data.r2dbc.postgresql.model.fields.Status;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.UUID;

import static io.rxmicro.common.util.Requires.require;
import static rxmicro.examples.data.r2dbc.postgresql.model.fields.$$Reflections.getFieldValue;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$EntityEntityToR2DBCSQLDBConverter extends EntityToR2DBCSQLDBConverter<Entity, Row> {

    private static final Class<?>[] INSERT_PARAM_TYPES = {
            Status.class,
            Boolean.class,
            Byte.class,
            Short.class,
            Integer.class,
            Long.class,
            BigInteger.class,
            Float.class,
            Double.class,
            BigDecimal.class,
            Character.class,
            String.class,
            Instant.class,
            LocalTime.class,
            LocalDate.class,
            LocalDateTime.class,
            OffsetDateTime.class,
            ZonedDateTime.class,
            InetAddress.class,
            UUID.class
    };

    private static final Class<?>[] UPDATE_PARAM_TYPES = {
            Status.class,
            Boolean.class,
            Byte.class,
            Short.class,
            Integer.class,
            Long.class,
            BigInteger.class,
            Float.class,
            Double.class,
            BigDecimal.class,
            Character.class,
            String.class,
            Instant.class,
            LocalTime.class,
            LocalDate.class,
            LocalDateTime.class,
            OffsetDateTime.class,
            ZonedDateTime.class,
            InetAddress.class,
            UUID.class
    };

    public Class<?>[] getInsertParamTypes() {
        return INSERT_PARAM_TYPES;
    }

    public Object[] getInsertParams(final Entity entity) {
        return new Object[]{
                getFieldValue(entity, "status"),
                getFieldValue(entity, "aBoolean"),
                getFieldValue(entity, "aByte"),
                getFieldValue(entity, "aShort"),
                getFieldValue(entity, "aInteger"),
                getFieldValue(entity, "aLong"),
                getFieldValue(entity, "bigInteger"),
                getFieldValue(entity, "aFloat"),
                getFieldValue(entity, "aDouble"),
                getFieldValue(entity, "bigDecimal"),
                getFieldValue(entity, "character"),
                getFieldValue(entity, "string"),
                getFieldValue(entity, "instant"),
                getFieldValue(entity, "localTime"),
                getFieldValue(entity, "localDate"),
                getFieldValue(entity, "localDateTime"),
                getFieldValue(entity, "offsetDateTime"),
                getFieldValue(entity, "zonedDateTime"),
                getFieldValue(entity, "inetAddress"),
                getFieldValue(entity, "uuid")
        };
    }

    public Class<?>[] getUpdateParamTypes() {
        return UPDATE_PARAM_TYPES;
    }

    public Object[] getUpdateParams(final Entity entity) {
        return new Object[]{
                getFieldValue(entity, "status"),
                getFieldValue(entity, "aBoolean"),
                getFieldValue(entity, "aByte"),
                getFieldValue(entity, "aShort"),
                getFieldValue(entity, "aInteger"),
                getFieldValue(entity, "aLong"),
                getFieldValue(entity, "bigInteger"),
                getFieldValue(entity, "aFloat"),
                getFieldValue(entity, "aDouble"),
                getFieldValue(entity, "bigDecimal"),
                getFieldValue(entity, "character"),
                getFieldValue(entity, "string"),
                getFieldValue(entity, "instant"),
                getFieldValue(entity, "localTime"),
                getFieldValue(entity, "localDate"),
                getFieldValue(entity, "localDateTime"),
                getFieldValue(entity, "offsetDateTime"),
                getFieldValue(entity, "zonedDateTime"),
                getFieldValue(entity, "inetAddress"),
                getFieldValue(entity, "uuid"),
                // primary key(s):
                require(getFieldValue(entity, "id"), "Primary key must be not null!")
        };
    }

    public Object getPrimaryKey(final Entity entity) {
        return require(getFieldValue(entity, "id"), "Primary key must be not null!");
    }
}