/*
 * Copyright (c) 2020. http://rxmicro.io
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

package io.rxmicro.examples.rest.client.model.field.access.pathvariables.reflection;

import io.rxmicro.examples.rest.client.model.field.access.Status;
import io.rxmicro.rest.PathVariable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;

public class Request {

    @PathVariable("a")
    private Boolean booleanPathVariable;

    @PathVariable("b")
    private Byte bytePathVariable;

    @PathVariable("c")
    private Short shortPathVariable;

    @PathVariable("d")
    private Integer intPathVariable;

    @PathVariable("e")
    private Long longPathVariable;

    @PathVariable("f")
    private BigInteger bigIntegerPathVariable;

    @PathVariable("g")
    private Float floatPathVariable;

    @PathVariable("h")
    private Double doublePathVariable;

    @PathVariable("i")
    private BigDecimal decimalPathVariable;

    @PathVariable("j")
    private Character charPathVariable;

    @PathVariable("k")
    private String stringPathVariable;

    @PathVariable("l")
    private Instant instantPathVariable;

    @PathVariable("m")
    private Status enumPathVariable;
}
