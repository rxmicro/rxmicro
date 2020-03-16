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

package io.rxmicro.examples.documentation.asciidoctor.full.model;

import io.rxmicro.examples.documentation.asciidoctor.full.model.nested.Nested;
import io.rxmicro.validation.constraint.CountryCode;
import io.rxmicro.validation.constraint.MaxNumber;
import io.rxmicro.validation.constraint.MaxSize;
import io.rxmicro.validation.constraint.MinNumber;
import io.rxmicro.validation.constraint.MinSize;
import io.rxmicro.validation.constraint.NullableArrayItem;
import io.rxmicro.validation.constraint.Numeric;

import java.math.BigDecimal;
import java.util.List;

public final class Response {

    @Numeric(scale = 2)
    @MinNumber("0")
    @MaxNumber("3000.00")
    BigDecimal price;

    @CountryCode
    String country;

    @MinSize(1)
    @MaxSize(10)
    @NullableArrayItem
    List<Nested> nestedList;
}
