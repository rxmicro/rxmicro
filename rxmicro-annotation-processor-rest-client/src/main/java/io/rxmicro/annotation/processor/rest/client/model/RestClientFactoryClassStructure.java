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

import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.ClassStructure;
import io.rxmicro.annotation.processor.common.model.DefaultConfigProxyValue;
import io.rxmicro.rest.client.RestClientFactory;
import io.rxmicro.rest.client.detail.RestClientImplFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import static io.rxmicro.annotation.processor.common.model.ClassHeader.newClassHeaderBuilder;
import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.ENVIRONMENT_CUSTOMIZER_SIMPLE_CLASS_NAME;
import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getEntryPointFullClassName;
import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.rest.client.RestClientFactory.REST_CLIENT_FACTORY_IMPL_CLASS_NAME;
import static io.rxmicro.runtime.detail.RxMicroRuntime.ENTRY_POINT_PACKAGE;

/**
 * @author nedis
 * @since 0.1
 */
public final class RestClientFactoryClassStructure extends ClassStructure {

    private final Set<RestClientClassStructure> classStructures;

    private final List<Map.Entry<String, DefaultConfigProxyValue>> defaultConfigValues;

    public RestClientFactoryClassStructure(final Set<RestClientClassStructure> classStructures,
                                           final List<Map.Entry<String, DefaultConfigProxyValue>> defaultConfigValues) {
        this.classStructures = new TreeSet<>(require(classStructures));
        this.defaultConfigValues = require(defaultConfigValues);
    }

    @Override
    public String getTargetFullClassName() {
        return getEntryPointFullClassName(REST_CLIENT_FACTORY_IMPL_CLASS_NAME);
    }

    @Override
    public String getTemplateName() {
        return "rest/client/$$RestClientFactoryImplTemplate.javaftl";
    }

    @Override
    public Map<String, Object> getTemplateVariables() {
        final Map<String, Object> map = new HashMap<>();
        map.put("IMPL_CLASS_NAME", REST_CLIENT_FACTORY_IMPL_CLASS_NAME);
        map.put("REST_CLIENTS", classStructures);
        map.put("ENVIRONMENT_CUSTOMIZER_CLASS", ENVIRONMENT_CUSTOMIZER_SIMPLE_CLASS_NAME);
        return map;
    }

    @Override
    public ClassHeader getClassHeader() {
        final ClassHeader.Builder classHeaderBuilder = newClassHeaderBuilder(ENTRY_POINT_PACKAGE)
                .addImports(RestClientFactory.class)
                .addStaticImport(RestClientImplFactory.class, "createRestClient");
        for (final RestClientClassStructure classStructure : classStructures) {
            classHeaderBuilder.addImports(classStructure.getFullInterfaceName(), classStructure.getTargetFullClassName());
            classHeaderBuilder.addImports(classStructure.getHttpClientConfigFullClassName());
        }
        return classHeaderBuilder.build();
    }

    @Override
    public List<Map.Entry<String, DefaultConfigProxyValue>> getDefaultConfigProxyValues() {
        return defaultConfigValues;
    }
}
