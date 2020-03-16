/*
 * Copyright (c) 2020. https://rxmicro.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.rxmicro.examples.data.r2dbc.postgresql.model.fields.setter_getter.model;

import io.rxmicro.data.sql.PrimaryKey;
import io.rxmicro.data.sql.Schema;
import io.rxmicro.data.sql.Table;
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

@Schema("setter_getter")
@Table(name = "entity")
public final class Entity {

    @PrimaryKey
    private Long id;

    private Status status;

    private Boolean aBoolean;

    private Byte aByte;

    private Short aShort;

    private Integer aInteger;

    private Long aLong;

    private BigInteger bigInteger;

    private Float aFloat;

    private Double aDouble;

    private BigDecimal bigDecimal;

    private Character character;

    private String string;

    private Instant instant;

    private LocalTime localTime;

    private LocalDate localDate;

    private LocalDateTime localDateTime;

    private OffsetDateTime offsetDateTime;

    private ZonedDateTime zonedDateTime;

    private InetAddress inetAddress;

    private UUID uuid;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(final Status status) {
        this.status = status;
    }

    public Boolean getaBoolean() {
        return aBoolean;
    }

    public void setaBoolean(final Boolean aBoolean) {
        this.aBoolean = aBoolean;
    }

    public Byte getaByte() {
        return aByte;
    }

    public void setaByte(final Byte aByte) {
        this.aByte = aByte;
    }

    public Short getaShort() {
        return aShort;
    }

    public void setaShort(final Short aShort) {
        this.aShort = aShort;
    }

    public Integer getaInteger() {
        return aInteger;
    }

    public void setaInteger(final Integer aInteger) {
        this.aInteger = aInteger;
    }

    public Long getaLong() {
        return aLong;
    }

    public void setaLong(final Long aLong) {
        this.aLong = aLong;
    }

    public BigInteger getBigInteger() {
        return bigInteger;
    }

    public void setBigInteger(final BigInteger bigInteger) {
        this.bigInteger = bigInteger;
    }

    public Float getaFloat() {
        return aFloat;
    }

    public void setaFloat(final Float aFloat) {
        this.aFloat = aFloat;
    }

    public Double getaDouble() {
        return aDouble;
    }

    public void setaDouble(final Double aDouble) {
        this.aDouble = aDouble;
    }

    public BigDecimal getBigDecimal() {
        return bigDecimal;
    }

    public void setBigDecimal(final BigDecimal bigDecimal) {
        this.bigDecimal = bigDecimal;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(final Character character) {
        this.character = character;
    }

    public String getString() {
        return string;
    }

    public void setString(final String string) {
        this.string = string;
    }

    public Instant getInstant() {
        return instant;
    }

    public void setInstant(final Instant instant) {
        this.instant = instant;
    }

    public LocalTime getLocalTime() {
        return localTime;
    }

    public void setLocalTime(final LocalTime localTime) {
        this.localTime = localTime;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(final LocalDate localDate) {
        this.localDate = localDate;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(final LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public OffsetDateTime getOffsetDateTime() {
        return offsetDateTime;
    }

    public void setOffsetDateTime(final OffsetDateTime offsetDateTime) {
        this.offsetDateTime = offsetDateTime;
    }

    public ZonedDateTime getZonedDateTime() {
        return zonedDateTime;
    }

    public void setZonedDateTime(final ZonedDateTime zonedDateTime) {
        this.zonedDateTime = zonedDateTime;
    }

    public InetAddress getInetAddress() {
        return inetAddress;
    }

    public void setInetAddress(final InetAddress inetAddress) {
        this.inetAddress = inetAddress;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(final UUID uuid) {
        this.uuid = uuid;
    }
}
