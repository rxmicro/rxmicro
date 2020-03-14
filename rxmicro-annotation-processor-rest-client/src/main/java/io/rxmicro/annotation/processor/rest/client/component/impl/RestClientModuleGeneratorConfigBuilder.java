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

package io.rxmicro.annotation.processor.rest.client.component.impl;

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.component.ModuleGeneratorConfigBuilder;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.rest.client.model.RestClientModuleGeneratorConfig;
import io.rxmicro.rest.client.RestClientGeneratorConfig;
import io.rxmicro.rest.model.ClientExchangeFormatModule;

import static io.rxmicro.annotation.processor.common.util.Annotations.getPresentOrDefaultAnnotation;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
@Singleton
public final class RestClientModuleGeneratorConfigBuilder implements ModuleGeneratorConfigBuilder<RestClientModuleGeneratorConfig> {

    @Override
    public RestClientModuleGeneratorConfig build(final EnvironmentContext environmentContext) {
        final RestClientGeneratorConfig config =
                getPresentOrDefaultAnnotation(environmentContext.getCurrentModule(), RestClientGeneratorConfig.class);
        final ClientExchangeFormatModule exchangeFormat = getExchangeFormat(environmentContext, config);
        return new RestClientModuleGeneratorConfig(environmentContext, exchangeFormat, config);
    }

    private ClientExchangeFormatModule getExchangeFormat(final EnvironmentContext environmentContext,
                                                         final RestClientGeneratorConfig config) {
        final ClientExchangeFormatModule exchangeFormat = config.exchangeFormat();
        if (exchangeFormat == ClientExchangeFormatModule.AUTO_DETECT) {
            ClientExchangeFormatModule clientExchangeFormatModule = null;
            for (final ClientExchangeFormatModule exchangeFormatModule : exchangeFormat.allExchangeFormatModules()) {
                if (environmentContext.getRxMicroModules().contains(exchangeFormatModule.getRxMicroModule())) {
                    if (clientExchangeFormatModule != null) {
                        throw new InterruptProcessingException(
                                environmentContext.getCurrentModule(),
                                "Specify exchangeFormat for all REST clients!"
                        );
                    }
                    clientExchangeFormatModule = exchangeFormatModule;
                }
            }
            if (clientExchangeFormatModule == null) {
                throw new InterruptProcessingException(
                        environmentContext.getCurrentModule(),
                        "Missing exchange format rxmicro module. Add 'requires rxmicro.rest.client.exchange.json;' to 'module-info.java'"
                );
            }
            return clientExchangeFormatModule;
        }
        return exchangeFormat;
    }
}
