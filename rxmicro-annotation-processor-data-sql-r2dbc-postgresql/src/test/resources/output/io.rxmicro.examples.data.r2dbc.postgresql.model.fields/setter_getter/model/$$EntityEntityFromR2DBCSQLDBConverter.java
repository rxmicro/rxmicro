package io.rxmicro.examples.data.r2dbc.postgresql.model.fields.setter_getter.model;

import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import io.rxmicro.data.sql.r2dbc.detail.EntityFromR2DBCSQLDBConverter;
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

/**
 * Generated by rxmicro annotation processor
 *
 * @link https://rxmicro.io
 */
public final class $$EntityEntityFromR2DBCSQLDBConverter extends EntityFromR2DBCSQLDBConverter<Row, RowMetadata, Entity> {

    public Entity fromDB(final Row dbRow,
                         final RowMetadata metadata) {
        final Entity model = new Entity();
        model.setId(dbRow.get(0, Long.class));
        model.setStatus(toEnum(Status.class, dbRow.get(1, String.class), "status"));
        model.setaBoolean(dbRow.get(2, Boolean.class));
        model.setaByte(dbRow.get(3, Byte.class));
        model.setaShort(dbRow.get(4, Short.class));
        model.setaInteger(dbRow.get(5, Integer.class));
        model.setaLong(dbRow.get(6, Long.class));
        model.setBigInteger(dbRow.get(7, BigInteger.class));
        model.setaFloat(dbRow.get(8, Float.class));
        model.setaDouble(dbRow.get(9, Double.class));
        model.setBigDecimal(dbRow.get(10, BigDecimal.class));
        model.setCharacter(dbRow.get(11, Character.class));
        model.setString(dbRow.get(12, String.class));
        model.setInstant(dbRow.get(13, Instant.class));
        model.setLocalTime(dbRow.get(14, LocalTime.class));
        model.setLocalDate(dbRow.get(15, LocalDate.class));
        model.setLocalDateTime(dbRow.get(16, LocalDateTime.class));
        model.setOffsetDateTime(dbRow.get(17, OffsetDateTime.class));
        model.setZonedDateTime(dbRow.get(18, ZonedDateTime.class));
        model.setInetAddress(dbRow.get(19, InetAddress.class));
        model.setUuid(dbRow.get(20, UUID.class));
        return model;
    }
}
