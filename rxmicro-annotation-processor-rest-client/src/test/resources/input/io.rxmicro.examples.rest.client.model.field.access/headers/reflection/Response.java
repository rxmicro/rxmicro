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

package io.rxmicro.examples.rest.client.model.field.access.headers.reflection;

import io.rxmicro.examples.rest.client.model.field.access.Status;
import io.rxmicro.rest.Header;
import io.rxmicro.rest.RepeatHeader;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.List;
import java.util.Set;

public class Response {

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
}
