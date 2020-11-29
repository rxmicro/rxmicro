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

package io.rxmicro.examples.rest.controller.model.field.access.headers.gettersetter;

import io.rxmicro.examples.rest.controller.model.field.access.Status;
import io.rxmicro.rest.Header;
import io.rxmicro.rest.RequestId;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.List;
import java.util.Set;

public class Request {

    @Header
    private Boolean booleanHeader;

    @Header
    private Byte byteHeader;

    @Header
    private Short shortHeader;

    @Header
    private Integer intHeader;

    @Header
    private Long longHeader;

    @Header
    private BigInteger bigIntHeader;

    @Header
    private Float floatHeader;

    @Header
    private Double doubleHeader;

    @Header
    private BigDecimal decimalHeader;

    @Header
    private Character charHeader;

    @Header
    private String stringHeader;

    @Header
    private Instant instantHeader;

    @Header
    private Status enumHeader;

    @Header
    private List<Boolean> booleanHeaderList;

    @Header
    private List<Byte> byteHeaderList;

    @Header
    private List<Short> shortHeaderList;

    @Header
    private List<Integer> intHeaderList;

    @Header
    private List<Long> longHeaderList;

    @Header
    private List<BigInteger> bigIntHeaderList;

    @Header
    private List<Float> floatHeaderList;

    @Header
    private List<Double> doubleHeaderList;

    @Header
    private List<BigDecimal> decimalHeaderList;

    @Header
    private List<Character> charHeaderList;

    @Header
    private List<String> stringHeaderList;

    @Header
    private List<Instant> instantHeaderList;

    @Header
    private List<Status> enumHeaderList;

    @Header
    private Set<Boolean> booleanHeaderSet;

    @Header
    private Set<Byte> byteHeaderSet;

    @Header
    private Set<Short> shortHeaderSet;

    @Header
    private Set<Integer> intHeaderSet;

    @Header
    private Set<Long> longHeaderSet;

    @Header
    private Set<BigInteger> bigIntHeaderSet;

    @Header
    private Set<Float> floatHeaderSet;

    @Header
    private Set<Double> doubleHeaderSet;

    @Header
    private Set<BigDecimal> decimalHeaderSet;

    @Header
    private Set<Character> charHeaderSet;

    @Header
    private Set<String> stringHeaderSet;

    @Header
    private Set<Instant> instantHeaderSet;

    @Header
    private Set<Status> enumHeaderSet;

    @RequestId
    private String requestIdHeader;

    public Boolean getBooleanHeader() {
        return booleanHeader;
    }

    public void setBooleanHeader(final Boolean booleanHeader) {
        this.booleanHeader = booleanHeader;
    }

    public Byte getByteHeader() {
        return byteHeader;
    }

    public void setByteHeader(final Byte byteHeader) {
        this.byteHeader = byteHeader;
    }

    public Short getShortHeader() {
        return shortHeader;
    }

    public void setShortHeader(final Short shortHeader) {
        this.shortHeader = shortHeader;
    }

    public Integer getIntHeader() {
        return intHeader;
    }

    public void setIntHeader(final Integer intHeader) {
        this.intHeader = intHeader;
    }

    public Long getLongHeader() {
        return longHeader;
    }

    public void setLongHeader(final Long longHeader) {
        this.longHeader = longHeader;
    }

    public BigInteger getBigIntHeader() {
        return bigIntHeader;
    }

    public void setBigIntHeader(final BigInteger bigIntHeader) {
        this.bigIntHeader = bigIntHeader;
    }

    public Float getFloatHeader() {
        return floatHeader;
    }

    public void setFloatHeader(final Float floatHeader) {
        this.floatHeader = floatHeader;
    }

    public Double getDoubleHeader() {
        return doubleHeader;
    }

    public void setDoubleHeader(final Double doubleHeader) {
        this.doubleHeader = doubleHeader;
    }

    public BigDecimal getDecimalHeader() {
        return decimalHeader;
    }

    public void setDecimalHeader(final BigDecimal decimalHeader) {
        this.decimalHeader = decimalHeader;
    }

    public Character getCharHeader() {
        return charHeader;
    }

    public void setCharHeader(final Character charHeader) {
        this.charHeader = charHeader;
    }

    public String getStringHeader() {
        return stringHeader;
    }

    public void setStringHeader(final String stringHeader) {
        this.stringHeader = stringHeader;
    }

    public Instant getInstantHeader() {
        return instantHeader;
    }

    public void setInstantHeader(final Instant instantHeader) {
        this.instantHeader = instantHeader;
    }

    public Status getEnumHeader() {
        return enumHeader;
    }

    public void setEnumHeader(final Status enumHeader) {
        this.enumHeader = enumHeader;
    }

    public List<Boolean> getBooleanHeaderList() {
        return booleanHeaderList;
    }

    public void setBooleanHeaderList(final List<Boolean> booleanHeaderList) {
        this.booleanHeaderList = booleanHeaderList;
    }

    public List<Byte> getByteHeaderList() {
        return byteHeaderList;
    }

    public void setByteHeaderList(final List<Byte> byteHeaderList) {
        this.byteHeaderList = byteHeaderList;
    }

    public List<Short> getShortHeaderList() {
        return shortHeaderList;
    }

    public void setShortHeaderList(final List<Short> shortHeaderList) {
        this.shortHeaderList = shortHeaderList;
    }

    public List<Integer> getIntHeaderList() {
        return intHeaderList;
    }

    public void setIntHeaderList(final List<Integer> intHeaderList) {
        this.intHeaderList = intHeaderList;
    }

    public List<Long> getLongHeaderList() {
        return longHeaderList;
    }

    public void setLongHeaderList(final List<Long> longHeaderList) {
        this.longHeaderList = longHeaderList;
    }

    public List<BigInteger> getBigIntHeaderList() {
        return bigIntHeaderList;
    }

    public void setBigIntHeaderList(final List<BigInteger> bigIntHeaderList) {
        this.bigIntHeaderList = bigIntHeaderList;
    }

    public List<Float> getFloatHeaderList() {
        return floatHeaderList;
    }

    public void setFloatHeaderList(final List<Float> floatHeaderList) {
        this.floatHeaderList = floatHeaderList;
    }

    public List<Double> getDoubleHeaderList() {
        return doubleHeaderList;
    }

    public void setDoubleHeaderList(final List<Double> doubleHeaderList) {
        this.doubleHeaderList = doubleHeaderList;
    }

    public List<BigDecimal> getDecimalHeaderList() {
        return decimalHeaderList;
    }

    public void setDecimalHeaderList(final List<BigDecimal> decimalHeaderList) {
        this.decimalHeaderList = decimalHeaderList;
    }

    public List<Character> getCharHeaderList() {
        return charHeaderList;
    }

    public void setCharHeaderList(final List<Character> charHeaderList) {
        this.charHeaderList = charHeaderList;
    }

    public List<String> getStringHeaderList() {
        return stringHeaderList;
    }

    public void setStringHeaderList(final List<String> stringHeaderList) {
        this.stringHeaderList = stringHeaderList;
    }

    public List<Instant> getInstantHeaderList() {
        return instantHeaderList;
    }

    public void setInstantHeaderList(final List<Instant> instantHeaderList) {
        this.instantHeaderList = instantHeaderList;
    }

    public List<Status> getEnumHeaderList() {
        return enumHeaderList;
    }

    public void setEnumHeaderList(final List<Status> enumHeaderList) {
        this.enumHeaderList = enumHeaderList;
    }

    public Set<Boolean> getBooleanHeaderSet() {
        return booleanHeaderSet;
    }

    public void setBooleanHeaderSet(final Set<Boolean> booleanHeaderSet) {
        this.booleanHeaderSet = booleanHeaderSet;
    }

    public Set<Byte> getByteHeaderSet() {
        return byteHeaderSet;
    }

    public void setByteHeaderSet(final Set<Byte> byteHeaderSet) {
        this.byteHeaderSet = byteHeaderSet;
    }

    public Set<Short> getShortHeaderSet() {
        return shortHeaderSet;
    }

    public void setShortHeaderSet(final Set<Short> shortHeaderSet) {
        this.shortHeaderSet = shortHeaderSet;
    }

    public Set<Integer> getIntHeaderSet() {
        return intHeaderSet;
    }

    public void setIntHeaderSet(final Set<Integer> intHeaderSet) {
        this.intHeaderSet = intHeaderSet;
    }

    public Set<Long> getLongHeaderSet() {
        return longHeaderSet;
    }

    public void setLongHeaderSet(final Set<Long> longHeaderSet) {
        this.longHeaderSet = longHeaderSet;
    }

    public Set<BigInteger> getBigIntHeaderSet() {
        return bigIntHeaderSet;
    }

    public void setBigIntHeaderSet(final Set<BigInteger> bigIntHeaderSet) {
        this.bigIntHeaderSet = bigIntHeaderSet;
    }

    public Set<Float> getFloatHeaderSet() {
        return floatHeaderSet;
    }

    public void setFloatHeaderSet(final Set<Float> floatHeaderSet) {
        this.floatHeaderSet = floatHeaderSet;
    }

    public Set<Double> getDoubleHeaderSet() {
        return doubleHeaderSet;
    }

    public void setDoubleHeaderSet(final Set<Double> doubleHeaderSet) {
        this.doubleHeaderSet = doubleHeaderSet;
    }

    public Set<BigDecimal> getDecimalHeaderSet() {
        return decimalHeaderSet;
    }

    public void setDecimalHeaderSet(final Set<BigDecimal> decimalHeaderSet) {
        this.decimalHeaderSet = decimalHeaderSet;
    }

    public Set<Character> getCharHeaderSet() {
        return charHeaderSet;
    }

    public void setCharHeaderSet(final Set<Character> charHeaderSet) {
        this.charHeaderSet = charHeaderSet;
    }

    public Set<String> getStringHeaderSet() {
        return stringHeaderSet;
    }

    public void setStringHeaderSet(final Set<String> stringHeaderSet) {
        this.stringHeaderSet = stringHeaderSet;
    }

    public Set<Instant> getInstantHeaderSet() {
        return instantHeaderSet;
    }

    public void setInstantHeaderSet(final Set<Instant> instantHeaderSet) {
        this.instantHeaderSet = instantHeaderSet;
    }

    public Set<Status> getEnumHeaderSet() {
        return enumHeaderSet;
    }

    public void setEnumHeaderSet(final Set<Status> enumHeaderSet) {
        this.enumHeaderSet = enumHeaderSet;
    }

    public String getRequestIdHeader() {
        return requestIdHeader;
    }

    public void setRequestIdHeader(final String requestIdHeader) {
        this.requestIdHeader = requestIdHeader;
    }
}
