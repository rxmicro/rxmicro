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

package io.rxmicro.examples.rest.controller.model.fields.pathvariables.gettersetter;

import io.rxmicro.examples.rest.controller.model.fields.Status;
import io.rxmicro.rest.PathVariable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;

public final class Request {

    @PathVariable("a")
    private Boolean booleanParameter;

    @PathVariable("b")
    private Byte byteParameter;

    @PathVariable("c")
    private Short shortParameter;

    @PathVariable("d")
    private Integer intParameter;

    @PathVariable("e")
    private Long longParameter;

    @PathVariable("f")
    private BigInteger bigIntParameter;

    @PathVariable("g")
    private Float floatParameter;

    @PathVariable("h")
    private Double doubleParameter;

    @PathVariable("i")
    private BigDecimal decimalParameter;

    @PathVariable("j")
    private Character charParameter;

    @PathVariable("k")
    private String stringParameter;

    @PathVariable("l")
    private Instant instantParameter;

    @PathVariable("m")
    private Status status;

    public Boolean getBooleanParameter() {
        return booleanParameter;
    }

    public void setBooleanParameter(final Boolean booleanParameter) {
        this.booleanParameter = booleanParameter;
    }

    public Byte getByteParameter() {
        return byteParameter;
    }

    public void setByteParameter(final Byte byteParameter) {
        this.byteParameter = byteParameter;
    }

    public Short getShortParameter() {
        return shortParameter;
    }

    public void setShortParameter(final Short shortParameter) {
        this.shortParameter = shortParameter;
    }

    public Integer getIntParameter() {
        return intParameter;
    }

    public void setIntParameter(final Integer intParameter) {
        this.intParameter = intParameter;
    }

    public Long getLongParameter() {
        return longParameter;
    }

    public void setLongParameter(final Long longParameter) {
        this.longParameter = longParameter;
    }

    public BigInteger getBigIntParameter() {
        return bigIntParameter;
    }

    public void setBigIntParameter(final BigInteger bigIntParameter) {
        this.bigIntParameter = bigIntParameter;
    }

    public Float getFloatParameter() {
        return floatParameter;
    }

    public void setFloatParameter(final Float floatParameter) {
        this.floatParameter = floatParameter;
    }

    public Double getDoubleParameter() {
        return doubleParameter;
    }

    public void setDoubleParameter(final Double doubleParameter) {
        this.doubleParameter = doubleParameter;
    }

    public BigDecimal getDecimalParameter() {
        return decimalParameter;
    }

    public void setDecimalParameter(final BigDecimal decimalParameter) {
        this.decimalParameter = decimalParameter;
    }

    public Character getCharParameter() {
        return charParameter;
    }

    public void setCharParameter(final Character charParameter) {
        this.charParameter = charParameter;
    }

    public String getStringParameter() {
        return stringParameter;
    }

    public void setStringParameter(final String stringParameter) {
        this.stringParameter = stringParameter;
    }

    public Instant getInstantParameter() {
        return instantParameter;
    }

    public void setInstantParameter(final Instant instantParameter) {
        this.instantParameter = instantParameter;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(final Status status) {
        this.status = status;
    }
}
