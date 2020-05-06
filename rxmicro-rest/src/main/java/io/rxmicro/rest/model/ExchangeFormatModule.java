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

import java.util.Collection;

/**
 * Base interface for supported exchange format modules
 *
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public interface ExchangeFormatModule {

    /**
     * Returns the current {@link ExchangeFormat}
     *
     * @return the current {@link ExchangeFormat}
     */
    ExchangeFormat getExchangeFormat();

    /**
     * Returns the {@link RxMicroModule} that supports current exchange format
     *
     * @return the {@link RxMicroModule} that supports current exchange format
     */
    RxMicroModule getRxMicroModule();

    /**
     * Returns all supported exchange formats
     *
     * @return all supported exchange formats
     */
    Collection<? extends ExchangeFormatModule> allExchangeFormatModules();
}
