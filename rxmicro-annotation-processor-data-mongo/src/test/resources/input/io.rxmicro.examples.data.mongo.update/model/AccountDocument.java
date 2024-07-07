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

package io.rxmicro.examples.data.mongo.update.model;

import java.math.BigDecimal;
import java.util.Objects;

public class AccountDocument {

    String email;

    String firstName;

    String lastName;

    BigDecimal balance;

    public AccountDocument() {
    }

    public AccountDocument(final String email,
                           final String firstName,
                           final String lastName,
                           final BigDecimal balance) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = balance;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, firstName, lastName, balance);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final AccountDocument that = (AccountDocument) o;
        return Objects.equals(email, that.email) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(balance, that.balance);
    }

    @Override
    public String toString() {
        return "AccountDocument{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", balance=" + balance +
                '}';
    }
}
