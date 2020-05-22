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

package io.rxmicro.rest.model;

import io.rxmicro.common.RxMicroModule;

import java.util.Arrays;
import java.util.Collection;

import static io.rxmicro.common.RxMicroModule.RX_MICRO_REST_CLIENT_EXCHANGE_JSON_MODULE;
import static io.rxmicro.rest.model.ExchangeFormat.JSON_EXCHANGE_FORMAT;
import static java.util.stream.Collectors.toSet;

/**
 * Supported exchange formats.
 *
 * @author nedis
 * @see ServerExchangeFormatModule
 * @since 0.1
 */
public enum ClientExchangeFormatModule implements ExchangeFormatModule {

    /**
     * Exchange format must be resolved automatically.
     */
    AUTO_DETECT(null, null),

    /**
     * JSON exchange format.
     */
    JSON(RX_MICRO_REST_CLIENT_EXCHANGE_JSON_MODULE, JSON_EXCHANGE_FORMAT);

    private final RxMicroModule rxMicroModule;

    private final ExchangeFormat exchangeFormat;

    ClientExchangeFormatModule(final RxMicroModule rxMicroModule,
                               final ExchangeFormat exchangeFormat) {
        this.rxMicroModule = rxMicroModule;
        this.exchangeFormat = exchangeFormat;
    }

    @Override
    public ExchangeFormat getExchangeFormat() {
        return exchangeFormat;
    }

    @Override
    public RxMicroModule getRxMicroModule() {
        return rxMicroModule;
    }

    @Override
    public Collection<ClientExchangeFormatModule> allExchangeFormatModules() {
        return Arrays.stream(values()).filter(m -> m != AUTO_DETECT).collect(toSet());
    }
}
