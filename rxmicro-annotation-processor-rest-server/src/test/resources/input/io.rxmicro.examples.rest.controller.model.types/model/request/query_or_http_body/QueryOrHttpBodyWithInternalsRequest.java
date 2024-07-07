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

package io.rxmicro.examples.rest.controller.model.types.model.request.query_or_http_body;

import io.rxmicro.examples.rest.controller.model.types.model.Status;
import io.rxmicro.http.HttpHeaders;
import io.rxmicro.http.HttpVersion;
import io.rxmicro.rest.RemoteAddress;
import io.rxmicro.rest.RequestBody;
import io.rxmicro.rest.RequestMethod;
import io.rxmicro.rest.RequestUrlPath;
import io.rxmicro.rest.server.detail.model.HttpRequest;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.SocketAddress;
import java.time.Instant;
import java.util.List;
import java.util.Set;

public class QueryOrHttpBodyWithInternalsRequest {

    Boolean booleanParameter;

    Byte byteParameter;

    Short shortParameter;

    Integer intParameter;

    Long longParameter;

    BigInteger bigIntParameter;

    Float floatParameter;

    Double doubleParameter;

    BigDecimal decimalParameter;

    Character charParameter;

    String stringParameter;

    Instant instantParameter;

    Status enumParameter;

    List<Boolean> booleanParameterList;

    List<Byte> byteParameterList;

    List<Short> shortParameterList;

    List<Integer> intParameterList;

    List<Long> longParameterList;

    List<BigInteger> bigIntParameterList;

    List<Float> floatParameterList;

    List<Double> doubleParameterList;

    List<BigDecimal> decimalParameterList;

    List<Character> charParameterList;

    List<String> stringParameterList;

    List<Instant> instantParameterList;

    List<Status> enumParameterList;

    Set<Boolean> booleanParameterSet;

    Set<Byte> byteParameterSet;

    Set<Short> shortParameterSet;

    Set<Integer> intParameterSet;

    Set<Long> longParameterSet;

    Set<BigInteger> bigIntParameterSet;

    Set<Float> floatParameterSet;

    Set<Double> doubleParameterSet;

    Set<BigDecimal> decimalParameterSet;

    Set<Character> charParameterSet;

    Set<String> stringParameterSet;

    Set<Instant> instantParameterSet;

    Set<Status> enumParameterSet;

    // -------------------------------------------------------------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------------------------------------------------------------

    @RemoteAddress
    String internalRemoteAddress1;

    @RemoteAddress
    SocketAddress internalRemoteAddress2;

    @RequestUrlPath
    String internalUrlPath;

    @RequestMethod
    String internalRequestMethod;

    HttpVersion internalHttpVersion;

    HttpHeaders internalRequestHeaders;

    @RequestBody
    byte[] internalRequestBody;

    HttpRequest internalRequest;
}
