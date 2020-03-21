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

package io.rxmicro.annotation.processor.rest.client.model;

import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.common.model.ModelFieldType;
import io.rxmicro.annotation.processor.rest.model.RestModuleGeneratorConfig;
import io.rxmicro.rest.client.RestClientGeneratorConfig;
import io.rxmicro.rest.model.ClientExchangeFormatModule;

import javax.lang.model.element.PackageElement;
import java.util.Set;

import static io.rxmicro.common.RxMicroModule.RX_MICRO_VALIDATION_MODULE;
import static java.util.stream.Collectors.toSet;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class RestClientModuleGeneratorConfig extends RestModuleGeneratorConfig {

    private final ClientExchangeFormatModule exchangeFormat;

    private final boolean generateRequestValidators;

    private final RestClientGeneratorConfig.RequestValidationMode requestValidationMode;

    private final boolean generateResponseValidators;

    private final boolean generateRequiredModuleDirectives;

    private final Set<String> allModulePackages;

    public RestClientModuleGeneratorConfig(final EnvironmentContext environmentContext,
                                           final ClientExchangeFormatModule exchangeFormat,
                                           final RestClientGeneratorConfig restClientGeneratorConfig) {
        final boolean autoValue = environmentContext.isRxMicroModuleEnabled(RX_MICRO_VALIDATION_MODULE);
        this.exchangeFormat = exchangeFormat;
        this.requestValidationMode = restClientGeneratorConfig.requestValidationMode();
        this.generateRequestValidators = getOption(restClientGeneratorConfig.generateRequestValidators(), autoValue);
        this.generateResponseValidators = getOption(restClientGeneratorConfig.generateResponseValidators(), autoValue);
        this.generateRequiredModuleDirectives = restClientGeneratorConfig.generateRequiredModuleDirectives();
        this.allModulePackages = this.generateRequiredModuleDirectives && !environmentContext.getCurrentModule().isUnnamed() ?
                environmentContext.getCurrentModule().getEnclosedElements().stream()
                        .map(e -> (PackageElement) e)
                        .map(pe -> pe.getQualifiedName().toString())
                        .collect(toSet()) :
                Set.of();
    }

    public RestClientGeneratorConfig.RequestValidationMode getRequestValidationMode() {
        return requestValidationMode;
    }

    @Override
    public ClientExchangeFormatModule getExchangeFormatModule() {
        return exchangeFormat;
    }

    @Override
    public ModelFieldType getFromHttpDataModelFieldType() {
        return ModelFieldType.REST_CLIENT_RESPONSE;
    }

    @Override
    public ModelFieldType getToHttpDataModelFieldType() {
        return ModelFieldType.REST_CLIENT_REQUEST;
    }

    public boolean isGenerateRequestValidators() {
        return generateRequestValidators;
    }

    public boolean isGenerateResponseValidators() {
        return generateResponseValidators;
    }

    public boolean isGenerateRequiredModuleDirectives() {
        return generateRequiredModuleDirectives;
    }

    public Set<String> getAllModulePackages() {
        return allModulePackages;
    }
}
