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

package io.rxmicro.annotation.processor.rest.server.model;

import io.rxmicro.annotation.processor.common.model.DocumentationType;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.common.model.ModelFieldType;
import io.rxmicro.annotation.processor.rest.model.RestModuleGeneratorConfig;
import io.rxmicro.common.RxMicroModule;
import io.rxmicro.rest.model.ServerExchangeFormatModule;
import io.rxmicro.rest.server.RestServerGeneratorConfig;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static io.rxmicro.annotation.processor.common.model.DocumentationType.Ascii_Doctor;
import static io.rxmicro.common.RxMicroModule.RX_MICRO_DOCUMENTATION_ASCIIDOCTOR_MODULE;
import static io.rxmicro.common.RxMicroModule.RX_MICRO_VALIDATION_MODULE;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class RestServerModuleGeneratorConfig extends RestModuleGeneratorConfig {

    private static final Map<RxMicroModule, DocumentationType> DOCUMENTATION_MAPPING = Map.of(
            RX_MICRO_DOCUMENTATION_ASCIIDOCTOR_MODULE, Ascii_Doctor
    );

    private final ServerExchangeFormatModule exchangeFormat;

    private final boolean generateRequestValidators;

    private final boolean generateResponseValidators;

    private final Set<DocumentationType> documentationTypes;

    public RestServerModuleGeneratorConfig(final EnvironmentContext environmentContext,
                                           final ServerExchangeFormatModule exchangeFormat,
                                           final RestServerGeneratorConfig restServerGeneratorConfig) {
        final boolean autoValue = environmentContext.isRxMicroModuleEnabled(RX_MICRO_VALIDATION_MODULE);
        this.exchangeFormat = exchangeFormat;
        this.generateRequestValidators = getOption(restServerGeneratorConfig.generateRequestValidators(), autoValue);
        this.generateResponseValidators = getOption(restServerGeneratorConfig.generateResponseValidators(), autoValue);
        this.documentationTypes = environmentContext.getRxMicroModules().stream()
                .flatMap(m -> Optional.ofNullable(DOCUMENTATION_MAPPING.get(m)).stream())
                .collect(Collectors.toSet());
    }

    @Override
    public ServerExchangeFormatModule getExchangeFormatModule() {
        return exchangeFormat;
    }

    @Override
    public ModelFieldType getFromHttpDataModelFieldType() {
        return ModelFieldType.REST_SERVER_REQUEST;
    }

    @Override
    public ModelFieldType getToHttpDataModelFieldType() {
        return ModelFieldType.REST_SERVER_RESPONSE;
    }

    public boolean isGenerateRequestValidators() {
        return generateRequestValidators;
    }

    public boolean isGenerateResponseValidators() {
        return generateResponseValidators;
    }

    public Set<DocumentationType> getDocumentationTypes() {
        return documentationTypes;
    }
}
