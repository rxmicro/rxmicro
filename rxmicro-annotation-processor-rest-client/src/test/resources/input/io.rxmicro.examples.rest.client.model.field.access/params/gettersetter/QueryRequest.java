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

package io.rxmicro.examples.rest.client.model.field.access.params.gettersetter;

import io.rxmicro.examples.rest.client.model.field.access.Status;
import io.rxmicro.rest.RepeatQueryParameter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.List;
import java.util.Set;

public class QueryRequest {

    private Boolean booleanParameter;

    private Byte byteParameter;

    private Short shortParameter;

    private Integer intParameter;

    private Long longParameter;

    private BigInteger bigIntParameter;

    private Float floatParameter;

    private Double doubleParameter;

    private BigDecimal decimalParameter;

    private Character charParameter;

    private String stringParameter;

    private Instant instantParameter;

    private Status enumParameter;

    private List<Boolean> booleanParameterList;

    private List<Byte> byteParameterList;

    private List<Short> shortParameterList;

    private List<Integer> intParameterList;

    private List<Long> longParameterList;

    private List<BigInteger> bigIntParameterList;

    private List<Float> floatParameterList;

    private List<Double> doubleParameterList;

    private List<BigDecimal> decimalParameterList;

    private List<Character> charParameterList;

    private List<String> stringParameterList;

    private List<Instant> instantParameterList;

    private List<Status> enumParameterList;

    private Set<Boolean> booleanParameterSet;

    private Set<Byte> byteParameterSet;

    private Set<Short> shortParameterSet;

    private Set<Integer> intParameterSet;

    private Set<Long> longParameterSet;

    private Set<BigInteger> bigIntParameterSet;

    private Set<Float> floatParameterSet;

    private Set<Double> doubleParameterSet;

    private Set<BigDecimal> decimalParameterSet;

    private Set<Character> charParameterSet;

    private Set<String> stringParameterSet;

    private Set<Instant> instantParameterSet;

    private Set<Status> enumParameterSet;

    @RepeatQueryParameter
    private List<Status> repeatingEnumParameterList;

    @RepeatQueryParameter
    private Set<Status> repeatingEnumParameterSet;

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

    public Status getEnumParameter() {
        return enumParameter;
    }

    public void setEnumParameter(final Status enumParameter) {
        this.enumParameter = enumParameter;
    }

    public List<Boolean> getBooleanParameterList() {
        return booleanParameterList;
    }

    public void setBooleanParameterList(final List<Boolean> booleanParameterList) {
        this.booleanParameterList = booleanParameterList;
    }

    public List<Byte> getByteParameterList() {
        return byteParameterList;
    }

    public void setByteParameterList(final List<Byte> byteParameterList) {
        this.byteParameterList = byteParameterList;
    }

    public List<Short> getShortParameterList() {
        return shortParameterList;
    }

    public void setShortParameterList(final List<Short> shortParameterList) {
        this.shortParameterList = shortParameterList;
    }

    public List<Integer> getIntParameterList() {
        return intParameterList;
    }

    public void setIntParameterList(final List<Integer> intParameterList) {
        this.intParameterList = intParameterList;
    }

    public List<Long> getLongParameterList() {
        return longParameterList;
    }

    public void setLongParameterList(final List<Long> longParameterList) {
        this.longParameterList = longParameterList;
    }

    public List<BigInteger> getBigIntParameterList() {
        return bigIntParameterList;
    }

    public void setBigIntParameterList(final List<BigInteger> bigIntParameterList) {
        this.bigIntParameterList = bigIntParameterList;
    }

    public List<Float> getFloatParameterList() {
        return floatParameterList;
    }

    public void setFloatParameterList(final List<Float> floatParameterList) {
        this.floatParameterList = floatParameterList;
    }

    public List<Double> getDoubleParameterList() {
        return doubleParameterList;
    }

    public void setDoubleParameterList(final List<Double> doubleParameterList) {
        this.doubleParameterList = doubleParameterList;
    }

    public List<BigDecimal> getDecimalParameterList() {
        return decimalParameterList;
    }

    public void setDecimalParameterList(final List<BigDecimal> decimalParameterList) {
        this.decimalParameterList = decimalParameterList;
    }

    public List<Character> getCharParameterList() {
        return charParameterList;
    }

    public void setCharParameterList(final List<Character> charParameterList) {
        this.charParameterList = charParameterList;
    }

    public List<String> getStringParameterList() {
        return stringParameterList;
    }

    public void setStringParameterList(final List<String> stringParameterList) {
        this.stringParameterList = stringParameterList;
    }

    public List<Instant> getInstantParameterList() {
        return instantParameterList;
    }

    public void setInstantParameterList(final List<Instant> instantParameterList) {
        this.instantParameterList = instantParameterList;
    }

    public List<Status> getEnumParameterList() {
        return enumParameterList;
    }

    public void setEnumParameterList(final List<Status> enumParameterList) {
        this.enumParameterList = enumParameterList;
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

    public Set<Status> getEnumParameterSet() {
        return enumParameterSet;
    }

    public void setEnumParameterSet(final Set<Status> enumParameterSet) {
        this.enumParameterSet = enumParameterSet;
    }

    public List<Status> getRepeatingEnumParameterList() {
        return repeatingEnumParameterList;
    }

    public void setRepeatingEnumParameterList(final List<Status> repeatingEnumParameterList) {
        this.repeatingEnumParameterList = repeatingEnumParameterList;
    }

    public Set<Status> getRepeatingEnumParameterSet() {
        return repeatingEnumParameterSet;
    }

    public void setRepeatingEnumParameterSet(final Set<Status> repeatingEnumParameterSet) {
        this.repeatingEnumParameterSet = repeatingEnumParameterSet;
    }
}
