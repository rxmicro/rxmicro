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

package io.rxmicro.examples.rest.controller.model.fields.params.gettersetter;

import io.rxmicro.examples.rest.controller.model.fields.Status;
import io.rxmicro.examples.rest.controller.model.fields.params.gettersetter.nested.Nested;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class BodyRequest extends Abstract {

    private Nested nested;

    private List<Nested> nestedList;

    private Set<Nested> nestedSet;

    private Map<String, Boolean> booleanData;

    private Map<String, Byte> byteData;

    private Map<String, Short> shortData;

    private Map<String, Integer> integerData;

    private Map<String, Long> longData;

    private Map<String, BigInteger> bigIntegerData;

    private Map<String, Float> floatData;

    private Map<String, Double> doubleData;

    private Map<String, BigDecimal> bigDecimalData;

    private Map<String, Character> characterData;

    private Map<String, String> stringData;

    private Map<String, Status> enumData;

    private Map<String, Instant> instantData;

    private Map<String, Nested> nestedMap;

    public Nested getNested() {
        return nested;
    }

    public void setNested(final Nested nested) {
        this.nested = nested;
    }

    public List<Nested> getNestedList() {
        return nestedList;
    }

    public void setNestedList(final List<Nested> nestedList) {
        this.nestedList = nestedList;
    }

    public Set<Nested> getNestedSet() {
        return nestedSet;
    }

    public void setNestedSet(final Set<Nested> nestedSet) {
        this.nestedSet = nestedSet;
    }

    public Map<String, Boolean> getBooleanData() {
        return booleanData;
    }

    public void setBooleanData(final Map<String, Boolean> booleanData) {
        this.booleanData = booleanData;
    }

    public Map<String, Byte> getByteData() {
        return byteData;
    }

    public void setByteData(final Map<String, Byte> byteData) {
        this.byteData = byteData;
    }

    public Map<String, Short> getShortData() {
        return shortData;
    }

    public void setShortData(final Map<String, Short> shortData) {
        this.shortData = shortData;
    }

    public Map<String, Integer> getIntegerData() {
        return integerData;
    }

    public void setIntegerData(final Map<String, Integer> integerData) {
        this.integerData = integerData;
    }

    public Map<String, Long> getLongData() {
        return longData;
    }

    public void setLongData(final Map<String, Long> longData) {
        this.longData = longData;
    }

    public Map<String, BigInteger> getBigIntegerData() {
        return bigIntegerData;
    }

    public void setBigIntegerData(final Map<String, BigInteger> bigIntegerData) {
        this.bigIntegerData = bigIntegerData;
    }

    public Map<String, Float> getFloatData() {
        return floatData;
    }

    public void setFloatData(final Map<String, Float> floatData) {
        this.floatData = floatData;
    }

    public Map<String, Double> getDoubleData() {
        return doubleData;
    }

    public void setDoubleData(final Map<String, Double> doubleData) {
        this.doubleData = doubleData;
    }

    public Map<String, BigDecimal> getBigDecimalData() {
        return bigDecimalData;
    }

    public void setBigDecimalData(final Map<String, BigDecimal> bigDecimalData) {
        this.bigDecimalData = bigDecimalData;
    }

    public Map<String, Character> getCharacterData() {
        return characterData;
    }

    public void setCharacterData(final Map<String, Character> characterData) {
        this.characterData = characterData;
    }

    public Map<String, String> getStringData() {
        return stringData;
    }

    public void setStringData(final Map<String, String> stringData) {
        this.stringData = stringData;
    }

    public Map<String, Status> getEnumData() {
        return enumData;
    }

    public void setEnumData(final Map<String, Status> enumData) {
        this.enumData = enumData;
    }

    public Map<String, Instant> getInstantData() {
        return instantData;
    }

    public void setInstantData(final Map<String, Instant> instantData) {
        this.instantData = instantData;
    }

    public Map<String, Nested> getNestedMap() {
        return nestedMap;
    }

    public void setNestedMap(final Map<String, Nested> nestedMap) {
        this.nestedMap = nestedMap;
    }
}
