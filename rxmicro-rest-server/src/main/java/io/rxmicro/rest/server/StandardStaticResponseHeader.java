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

package io.rxmicro.rest.server;

import io.rxmicro.http.HttpStandardHeaderNames;

import java.util.function.Supplier;

import static io.rxmicro.rest.server.internal.StandardResponseHeaderValueSupplierFactory.dateResponseHeaderValueSupplier;
import static io.rxmicro.rest.server.internal.StandardResponseHeaderValueSupplierFactory.serverResponseHeaderValueSupplier;

/**
 * Standard static HTTP headers
 *
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public enum StandardStaticResponseHeader implements StaticResponseHeader {

    /**
     * {@code `Server`} header that returns a server name
     */
    SERVER(HttpStandardHeaderNames.SERVER, serverResponseHeaderValueSupplier()),

    /**
     * {@code `Date`} header that returns a date with time of a HTTP response generation
     */
    DATE(HttpStandardHeaderNames.DATE, dateResponseHeaderValueSupplier());

    private final String headerName;

    private final Supplier<String> headerValueSupplier;

    StandardStaticResponseHeader(final String headerName,
                                 final Supplier<String> headerValueSupplier) {
        this.headerName = headerName;
        this.headerValueSupplier = headerValueSupplier;
    }

    @Override
    public String getName() {
        return headerName;
    }

    @Override
    public String getValue() {
        return headerValueSupplier.get();
    }
}
