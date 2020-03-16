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

import io.rxmicro.documentation.Example;
import io.rxmicro.rest.Header;
import io.rxmicro.rest.HeaderMappingStrategy;
import io.rxmicro.rest.ParameterMappingStrategy;
import io.rxmicro.rest.PathVariable;
import io.rxmicro.rest.RequestId;
import io.rxmicro.validation.constraint.AssertTrue;
import io.rxmicro.validation.constraint.Lat;
import io.rxmicro.validation.constraint.Lng;
import io.rxmicro.validation.constraint.Nullable;
import io.rxmicro.validation.constraint.Past;
import io.rxmicro.validation.constraint.Phone;
import io.rxmicro.validation.constraint.TruncatedTime;
import io.rxmicro.validation.constraint.Uppercase;

import java.math.BigDecimal;
import java.time.Instant;

@HeaderMappingStrategy
@ParameterMappingStrategy
public final class Request {

    @RequestId
    String requestId;

    @PathVariable
    @Example("category")
    String category;

    @PathVariable
    @Example("type")
    String type;

    @Header
    @Uppercase
    @Example("Production")
    String mode;

    @Header
    @Nullable
    @AssertTrue
    Boolean useProxy;

    @Phone
    String phone;

    @Lat
    BigDecimal lat;

    @Lng
    BigDecimal lng;

    @Past
    @TruncatedTime
    Instant created;
}
