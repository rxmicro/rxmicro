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

package io.rxmicro.examples.documentation.asciidoctor.full.model.nested;

import io.rxmicro.validation.constraint.Enumeration;
import io.rxmicro.validation.constraint.MaxInt;
import io.rxmicro.validation.constraint.MinInt;
import io.rxmicro.validation.constraint.Size;
import io.rxmicro.validation.constraint.UniqueItems;

import java.util.List;

public final class Nested {

    @MaxInt(20)
    @MinInt(5)
    Integer level;

    @Enumeration({"new", "old", "undefined"})
    String status;

    @Size(10)
    @MinInt(0)
    @MaxInt(255)
    @UniqueItems
    List<Integer> mask;
}
