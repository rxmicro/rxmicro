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

package io.rxmicro.examples.rest.client.model.fields.params.direct;

import io.rxmicro.examples.rest.client.model.fields.Status;
import io.rxmicro.examples.rest.client.model.fields.params.direct.nested.Nested;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class BodyRequest extends Abstract {

    Nested nested;

    List<Nested> nestedList;

    Set<Nested> nestedSet;

    Map<String, Boolean> booleanData;

    Map<String, Byte> byteData;

    Map<String, Short> shortData;

    Map<String, Integer> integerData;

    Map<String, Long> longData;

    Map<String, BigInteger> bigIntegerData;

    Map<String, Float> floatData;

    Map<String, Double> doubleData;

    Map<String, BigDecimal> bigDecimalData;

    Map<String, Character> characterData;

    Map<String, String> stringData;

    Map<String, Status> enumData;

    Map<String, Instant> instantData;

    Map<String, Nested> nestedMap;
}
