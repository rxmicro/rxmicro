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

package io.rxmicro.examples.rest.client.model.fields.headers.gettersetter;

import io.rxmicro.examples.rest.client.model.fields.Status;
import io.rxmicro.rest.Header;
import io.rxmicro.rest.HeaderMappingStrategy;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.List;
import java.util.Set;

@HeaderMappingStrategy
abstract class Abstract {

    @Header
    private Boolean booleanParameter;

    @Header
    private Byte byteParameter;

    @Header
    private Short shortParameter;

    @Header
    private Integer intParameter;

    @Header
    private Long longParameter;

    @Header
    private BigInteger bigIntParameter;

    @Header
    private Float floatParameter;

    @Header
    private Double doubleParameter;

    @Header
    private BigDecimal decimalParameter;

    @Header
    private Character charParameter;

    @Header
    private String stringParameter;

    @Header
    private Instant instantParameter;

    @Header
    private Status status;

    @Header
    private List<Boolean> booleanParameters;

    @Header
    private List<Byte> byteParameters;

    @Header
    private List<Short> shortParameters;

    @Header
    private List<Integer> intParameters;

    @Header
    private List<Long> longParameters;

    @Header
    private List<BigInteger> bigIntParameters;

    @Header
    private List<Float> floatParameters;

    @Header
    private List<Double> doubleParameters;

    @Header
    private List<BigDecimal> decimalParameters;

    @Header
    private List<Character> charParameters;

    @Header
    private List<String> stringParameters;

    @Header
    private List<Instant> instantParameters;

    @Header
    private List<Status> statuses;

    @Header
    private Set<Boolean> booleanParameterSet;

    @Header
    private Set<Byte> byteParameterSet;

    @Header
    private Set<Short> shortParameterSet;

    @Header
    private Set<Integer> intParameterSet;

    @Header
    private Set<Long> longParameterSet;

    @Header
    private Set<BigInteger> bigIntParameterSet;

    @Header
    private Set<Float> floatParameterSet;

    @Header
    private Set<Double> doubleParameterSet;

    @Header
    private Set<BigDecimal> decimalParameterSet;

    @Header
    private Set<Character> charParameterSet;

    @Header
    private Set<String> stringParameterSet;

    @Header
    private Set<Instant> instantParameterSet;

    @Header
    private Set<Status> statusSet;

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

    public List<Boolean> getBooleanParameters() {
        return booleanParameters;
    }

    public void setBooleanParameters(final List<Boolean> booleanParameters) {
        this.booleanParameters = booleanParameters;
    }

    public List<Byte> getByteParameters() {
        return byteParameters;
    }

    public void setByteParameters(final List<Byte> byteParameters) {
        this.byteParameters = byteParameters;
    }

    public List<Short> getShortParameters() {
        return shortParameters;
    }

    public void setShortParameters(final List<Short> shortParameters) {
        this.shortParameters = shortParameters;
    }

    public List<Integer> getIntParameters() {
        return intParameters;
    }

    public void setIntParameters(final List<Integer> intParameters) {
        this.intParameters = intParameters;
    }

    public List<Long> getLongParameters() {
        return longParameters;
    }

    public void setLongParameters(final List<Long> longParameters) {
        this.longParameters = longParameters;
    }

    public List<BigInteger> getBigIntParameters() {
        return bigIntParameters;
    }

    public void setBigIntParameters(final List<BigInteger> bigIntParameters) {
        this.bigIntParameters = bigIntParameters;
    }

    public List<Float> getFloatParameters() {
        return floatParameters;
    }

    public void setFloatParameters(final List<Float> floatParameters) {
        this.floatParameters = floatParameters;
    }

    public List<Double> getDoubleParameters() {
        return doubleParameters;
    }

    public void setDoubleParameters(final List<Double> doubleParameters) {
        this.doubleParameters = doubleParameters;
    }

    public List<BigDecimal> getDecimalParameters() {
        return decimalParameters;
    }

    public void setDecimalParameters(final List<BigDecimal> decimalParameters) {
        this.decimalParameters = decimalParameters;
    }

    public List<Character> getCharParameters() {
        return charParameters;
    }

    public void setCharParameters(final List<Character> charParameters) {
        this.charParameters = charParameters;
    }

    public List<String> getStringParameters() {
        return stringParameters;
    }

    public void setStringParameters(final List<String> stringParameters) {
        this.stringParameters = stringParameters;
    }

    public List<Instant> getInstantParameters() {
        return instantParameters;
    }

    public void setInstantParameters(final List<Instant> instantParameters) {
        this.instantParameters = instantParameters;
    }

    public List<Status> getStatuses() {
        return statuses;
    }

    public void setStatuses(final List<Status> statuses) {
        this.statuses = statuses;
    }

    public Set<Boolean> getBooleanParameterSet() {
        return booleanParameterSet;
    }

    public void setBooleanParameterSet(final Set<Boolean> booleanParameterSet) {
        this.booleanParameterSet = booleanParameterSet;
    }

    public Set<Byte> getByteParameterSet() {
        return byteParameterSet;
    }

    public void setByteParameterSet(final Set<Byte> byteParameterSet) {
        this.byteParameterSet = byteParameterSet;
    }

    public Set<Short> getShortParameterSet() {
        return shortParameterSet;
    }

    public void setShortParameterSet(final Set<Short> shortParameterSet) {
        this.shortParameterSet = shortParameterSet;
    }

    public Set<Integer> getIntParameterSet() {
        return intParameterSet;
    }

    public void setIntParameterSet(final Set<Integer> intParameterSet) {
        this.intParameterSet = intParameterSet;
    }

    public Set<Long> getLongParameterSet() {
        return longParameterSet;
    }

    public void setLongParameterSet(final Set<Long> longParameterSet) {
        this.longParameterSet = longParameterSet;
    }

    public Set<BigInteger> getBigIntParameterSet() {
        return bigIntParameterSet;
    }

    public void setBigIntParameterSet(final Set<BigInteger> bigIntParameterSet) {
        this.bigIntParameterSet = bigIntParameterSet;
    }

    public Set<Float> getFloatParameterSet() {
        return floatParameterSet;
    }

    public void setFloatParameterSet(final Set<Float> floatParameterSet) {
        this.floatParameterSet = floatParameterSet;
    }

    public Set<Double> getDoubleParameterSet() {
        return doubleParameterSet;
    }

    public void setDoubleParameterSet(final Set<Double> doubleParameterSet) {
        this.doubleParameterSet = doubleParameterSet;
    }

    public Set<BigDecimal> getDecimalParameterSet() {
        return decimalParameterSet;
    }

    public void setDecimalParameterSet(final Set<BigDecimal> decimalParameterSet) {
        this.decimalParameterSet = decimalParameterSet;
    }

    public Set<Character> getCharParameterSet() {
        return charParameterSet;
    }

    public void setCharParameterSet(final Set<Character> charParameterSet) {
        this.charParameterSet = charParameterSet;
    }

    public Set<String> getStringParameterSet() {
        return stringParameterSet;
    }

    public void setStringParameterSet(final Set<String> stringParameterSet) {
        this.stringParameterSet = stringParameterSet;
    }

    public Set<Instant> getInstantParameterSet() {
        return instantParameterSet;
    }

    public void setInstantParameterSet(final Set<Instant> instantParameterSet) {
        this.instantParameterSet = instantParameterSet;
    }

    public Set<Status> getStatusSet() {
        return statusSet;
    }

    public void setStatusSet(final Set<Status> statusSet) {
        this.statusSet = statusSet;
    }
}
