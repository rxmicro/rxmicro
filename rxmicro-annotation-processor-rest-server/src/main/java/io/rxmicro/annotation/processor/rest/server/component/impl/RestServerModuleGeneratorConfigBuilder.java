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

package io.rxmicro.annotation.processor.rest.server.component.impl;

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.component.ModuleGeneratorConfigBuilder;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.rest.server.model.RestServerModuleGeneratorConfig;
import io.rxmicro.rest.model.ServerExchangeFormatModule;
import io.rxmicro.rest.server.RestServerGeneratorConfig;

import static io.rxmicro.annotation.processor.common.util.Annotations.getPresentOrDefaultAnnotation;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
@Singleton
public final class RestServerModuleGeneratorConfigBuilder implements ModuleGeneratorConfigBuilder<RestServerModuleGeneratorConfig> {

    @Override
    public RestServerModuleGeneratorConfig build(final EnvironmentContext environmentContext) {
        final RestServerGeneratorConfig config =
                getPresentOrDefaultAnnotation(environmentContext.getCurrentModule(), RestServerGeneratorConfig.class);
        final ServerExchangeFormatModule exchangeFormat = getExchangeFormat(environmentContext, config);
        return new RestServerModuleGeneratorConfig(environmentContext, exchangeFormat, config);
    }

    private ServerExchangeFormatModule getExchangeFormat(final EnvironmentContext environmentContext,
                                                         final RestServerGeneratorConfig config) {
        final ServerExchangeFormatModule exchangeFormat = config.exchangeFormat();
        if (exchangeFormat == ServerExchangeFormatModule.AUTO_DETECT) {
            ServerExchangeFormatModule serverExchangeFormatModule = null;
            for (final ServerExchangeFormatModule exchangeFormatModule : exchangeFormat.allExchangeFormatModules()) {
                if (environmentContext.getRxMicroModules().contains(exchangeFormatModule.getRxMicroModule())) {
                    if (serverExchangeFormatModule != null) {
                        throw new InterruptProcessingException(
                                environmentContext.getCurrentModule(),
                                "Specify exchangeFormat for all REST controllers!"
                        );
                    }
                    serverExchangeFormatModule = exchangeFormatModule;
                }
            }
            if (serverExchangeFormatModule == null) {
                throw new InterruptProcessingException(
                        environmentContext.getCurrentModule(),
                        "Missing exchange format rxmicro module. Add 'requires rxmicro.rest.server.exchange.json;' to 'module-info.java'"
                );
            }
            return serverExchangeFormatModule;
        }
        return exchangeFormat;
    }
}
