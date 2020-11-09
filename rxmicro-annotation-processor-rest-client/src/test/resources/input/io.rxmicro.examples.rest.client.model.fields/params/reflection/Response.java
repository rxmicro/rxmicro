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

package io.rxmicro.examples.rest.client.model.fields.params.reflection;

import io.rxmicro.examples.rest.client.model.fields.Status;
import io.rxmicro.examples.rest.client.model.fields.params.reflection.nested.Nested;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class Response extends Abstract {

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
}