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

package io.rxmicro.examples.data.mongo.aggregate.model;

import io.rxmicro.data.mongo.DocumentId;

import java.math.BigDecimal;
import java.util.Objects;

// tag::content[]
public final class Report {

    @DocumentId
    Role id;

    BigDecimal total;
    // end::content[]

    public Report() {
    }

    public Report(final Role id,
                  final BigDecimal total) {
        this.id = id;
        this.total = total;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, total);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Report report = (Report) o;
        return id == report.id &&
                Objects.equals(total, report.total);
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", total=" + total +
                '}';
    }
// tag::content[]
}
// end::content[]
