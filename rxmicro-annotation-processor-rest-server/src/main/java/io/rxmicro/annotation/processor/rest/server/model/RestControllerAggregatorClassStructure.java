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

import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.ClassStructure;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.common.RxMicroModule;
import io.rxmicro.rest.model.UrlSegments;
import io.rxmicro.rest.server.detail.component.AbstractRestController;
import io.rxmicro.rest.server.detail.component.BadHttpRequestRestController;
import io.rxmicro.rest.server.detail.component.CrossOriginResourceSharingPreflightRestController;
import io.rxmicro.rest.server.detail.component.HttpHealthCheckRestController;
import io.rxmicro.rest.server.detail.component.RestControllerAggregator;
import io.rxmicro.rest.server.detail.model.CrossOriginResourceSharingResource;
import io.rxmicro.rest.server.detail.model.HttpHealthCheckRegistration;
import io.rxmicro.rest.server.detail.model.mapping.ExactUrlRequestMappingRule;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.ENVIRONMENT_CUSTOMIZER_SIMPLE_CLASS_NAME;
import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getEntryPointFullClassName;
import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.rest.server.detail.component.RestControllerAggregator.REST_CONTROLLER_AGGREGATOR_IMPL_CLASS_NAME;
import static io.rxmicro.runtime.detail.Runtimes.ENTRY_POINT_PACKAGE;
import static java.util.stream.Collectors.toList;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class RestControllerAggregatorClassStructure extends ClassStructure {

    private final Collection<RestControllerClassStructure> classStructures;

    private final Set<CrossOriginResourceSharingResource> crossOriginResourceSharingResources;

    private final Set<HttpHealthCheck> httpHealthChecks;

    private final boolean isRestServerNetty;

    public RestControllerAggregatorClassStructure(final EnvironmentContext environmentContext,
                                                  final Collection<RestControllerClassStructure> classStructures,
                                                  final Set<CrossOriginResourceSharingResource> resources,
                                                  final Set<HttpHealthCheck> httpHealthChecks) {
        this.crossOriginResourceSharingResources = require(resources);
        this.httpHealthChecks = require(httpHealthChecks);
        this.classStructures = new TreeSet<>(Comparator.comparing(RestControllerClassStructure::getTargetFullClassName));
        this.classStructures.addAll(require(classStructures));
        this.isRestServerNetty = environmentContext.isRxMicroModuleEnabled(RxMicroModule.RX_MICRO_REST_SERVER_NETTY_MODULE);
    }

    @Override
    public String getTargetFullClassName() {
        return getEntryPointFullClassName(REST_CONTROLLER_AGGREGATOR_IMPL_CLASS_NAME);
    }

    @Override
    public String getTemplateName() {
        return "rest/server/$$RestControllerAggregatorTemplate.javaftl";
    }

    @Override
    public Map<String, Object> getTemplateVariables() {
        final Map<String, Object> map = new HashMap<>();
        map.put("IMPL_CLASS_NAME", REST_CONTROLLER_AGGREGATOR_IMPL_CLASS_NAME);
        map.put("JAVA_REST_CONTROLLER_CLASSES", classStructures.stream()
                .map(RestControllerClassStructure::getTargetFullClassName)
                .collect(toList()));
        map.put("CORS_RESOURCES", crossOriginResourceSharingResources);
        map.put("HTTP_HEALTH_CHECKS", httpHealthChecks);
        map.put("ENVIRONMENT_CUSTOMIZER_CLASS", ENVIRONMENT_CUSTOMIZER_SIMPLE_CLASS_NAME);
        map.put("IS_NETTY_REST_SERVER", isRestServerNetty);
        return map;
    }

    @Override
    public ClassHeader getClassHeader() {
        final ClassHeader.Builder classHeaderBuilder = ClassHeader.newClassHeaderBuilder(ENTRY_POINT_PACKAGE);
        if (!crossOriginResourceSharingResources.isEmpty()) {
            classHeaderBuilder.addImports(
                    Set.class,
                    CrossOriginResourceSharingPreflightRestController.class,
                    CrossOriginResourceSharingResource.class,
                    UrlSegments.class
            );
        }
        if (!httpHealthChecks.isEmpty()) {
            classHeaderBuilder.addImports(
                    HttpHealthCheckRestController.class,
                    HttpHealthCheckRegistration.class
            );
        }
        if (isRestServerNetty) {
            classHeaderBuilder.addImports(ExactUrlRequestMappingRule.class, BadHttpRequestRestController.class);
        }
        return classHeaderBuilder
                .addImports(
                        AbstractRestController.class,
                        RestControllerAggregator.class,
                        List.class
                ).build();
    }
}
