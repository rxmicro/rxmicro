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

package io.rxmicro.examples.data.r2dbc.postgresql.select.projection.model;

import io.rxmicro.data.Column;
import io.rxmicro.data.ColumnMappingStrategy;
import io.rxmicro.data.sql.Cast;
import io.rxmicro.data.sql.Table;

import java.math.BigDecimal;
import java.util.Objects;

@Table
@ColumnMappingStrategy
public final class Account {

    Long id;

    @Column(length = Column.UNLIMITED_LENGTH)
    String email;

    @Column(length = Column.UNLIMITED_LENGTH)
    String firstName;

    @Column(length = Column.UNLIMITED_LENGTH)
    String lastName;

    BigDecimal balance;

    @Cast("role::text")
    Role role;

    public Account(final Long id,
                   final String email,
                   final String firstName,
                   final String lastName,
                   final BigDecimal balance,
                   final Role role) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = balance;
        this.role = role;
    }

    public Account(final Long id,
                   final String email,
                   final String firstName,
                   final String lastName,
                   final BigDecimal balance) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = balance;
    }

    public Account(final String firstName,
                   final String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Account() {
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, firstName, lastName, balance, role);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Account account = (Account) o;
        return Objects.equals(id, account.id) &&
                Objects.equals(email, account.email) &&
                Objects.equals(firstName, account.firstName) &&
                Objects.equals(lastName, account.lastName) &&
                Objects.equals(balance, account.balance) &&
                role == account.role;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", balance=" + balance +
                ", role=" + role +
                '}';
    }
}
