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

package io.rxmicro.examples.rest.client.model.field.access.pathvariables.gettersetter;

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

    public Boolean getBooleanPathVariable() {
        return booleanPathVariable;
    }

    public void setBooleanPathVariable(final Boolean booleanPathVariable) {
        this.booleanPathVariable = booleanPathVariable;
    }

    public Byte getBytePathVariable() {
        return bytePathVariable;
    }

    public void setBytePathVariable(final Byte bytePathVariable) {
        this.bytePathVariable = bytePathVariable;
    }

    public Short getShortPathVariable() {
        return shortPathVariable;
    }

    public void setShortPathVariable(final Short shortPathVariable) {
        this.shortPathVariable = shortPathVariable;
    }

    public Integer getIntPathVariable() {
        return intPathVariable;
    }

    public void setIntPathVariable(final Integer intPathVariable) {
        this.intPathVariable = intPathVariable;
    }

    public Long getLongPathVariable() {
        return longPathVariable;
    }

    public void setLongPathVariable(final Long longPathVariable) {
        this.longPathVariable = longPathVariable;
    }

    public BigInteger getBigIntegerPathVariable() {
        return bigIntegerPathVariable;
    }

    public void setBigIntegerPathVariable(final BigInteger bigIntegerPathVariable) {
        this.bigIntegerPathVariable = bigIntegerPathVariable;
    }

    public Float getFloatPathVariable() {
        return floatPathVariable;
    }

    public void setFloatPathVariable(final Float floatPathVariable) {
        this.floatPathVariable = floatPathVariable;
    }

    public Double getDoublePathVariable() {
        return doublePathVariable;
    }

    public void setDoublePathVariable(final Double doublePathVariable) {
        this.doublePathVariable = doublePathVariable;
    }

    public BigDecimal getDecimalPathVariable() {
        return decimalPathVariable;
    }

    public void setDecimalPathVariable(final BigDecimal decimalPathVariable) {
        this.decimalPathVariable = decimalPathVariable;
    }

    public Character getCharPathVariable() {
        return charPathVariable;
    }

    public void setCharPathVariable(final Character charPathVariable) {
        this.charPathVariable = charPathVariable;
    }

    public String getStringPathVariable() {
        return stringPathVariable;
    }

    public void setStringPathVariable(final String stringPathVariable) {
        this.stringPathVariable = stringPathVariable;
    }

    public Instant getInstantPathVariable() {
        return instantPathVariable;
    }

    public void setInstantPathVariable(final Instant instantPathVariable) {
        this.instantPathVariable = instantPathVariable;
    }

    public Status getEnumPathVariable() {
        return enumPathVariable;
    }

    public void setEnumPathVariable(final Status enumPathVariable) {
        this.enumPathVariable = enumPathVariable;
    }
}
