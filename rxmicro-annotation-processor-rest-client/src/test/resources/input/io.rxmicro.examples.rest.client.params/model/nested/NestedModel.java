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

package io.rxmicro.examples.rest.client.params.model.nested;

import io.rxmicro.rest.ParameterMappingStrategy;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

// tag::content[]
@ParameterMappingStrategy
public final class NestedModel {

    String stringParameter;

    BigDecimal bigDecimalParameter;

    Instant instantParameter;

    public NestedModel(final String stringParameter,
                       final BigDecimal bigDecimalParameter,
                       final Instant instantParameter) {
        this.stringParameter = stringParameter;
        this.bigDecimalParameter = bigDecimalParameter;
        this.instantParameter = instantParameter;
    }

    public NestedModel() {
    }

    @Override
    public int hashCode() {
        return Objects.hash(stringParameter, bigDecimalParameter, instantParameter);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final NestedModel that = (NestedModel) o;
        return stringParameter.equals(that.stringParameter) &&
                bigDecimalParameter.equals(that.bigDecimalParameter) &&
                instantParameter.equals(that.instantParameter);
    }
}
// end::content[]
