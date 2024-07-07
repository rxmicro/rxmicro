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

package io.rxmicro.examples.data.r2dbc.postgresql.model.fields.reflection.model;

import io.rxmicro.data.Column;
import io.rxmicro.data.sql.PrimaryKey;
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

@Table(name = "reflection.entity")
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

    @Column(length = Column.UNLIMITED_LENGTH)
    private String string;

    private Instant instant;

    private LocalTime localTime;

    private LocalDate localDate;

    private LocalDateTime localDateTime;

    private OffsetDateTime offsetDateTime;

    private ZonedDateTime zonedDateTime;

    private InetAddress inetAddress;

    private UUID uuid;
}
