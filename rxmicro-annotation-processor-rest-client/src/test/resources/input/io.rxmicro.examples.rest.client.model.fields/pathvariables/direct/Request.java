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

package io.rxmicro.examples.rest.client.model.fields.pathvariables.direct;

import io.rxmicro.examples.rest.client.model.fields.Status;
import io.rxmicro.rest.PathVariable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;

public final class Request {

    @PathVariable("a")
    Boolean booleanParameter;

    @PathVariable("b")
    Byte byteParameter;

    @PathVariable("c")
    Short shortParameter;

    @PathVariable("d")
    Integer intParameter;

    @PathVariable("e")
    Long longParameter;

    @PathVariable("f")
    BigInteger bigIntParameter;

    @PathVariable("g")
    Float floatParameter;

    @PathVariable("h")
    Double doubleParameter;

    @PathVariable("i")
    BigDecimal decimalParameter;

    @PathVariable("j")
    Character charParameter;

    @PathVariable("k")
    String stringParameter;

    @PathVariable("l")
    Instant instantParameter;

    @PathVariable("m")
    Status status;
}
