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
import io.rxmicro.annotation.processor.common.util.UsedByFreemarker;
import io.rxmicro.common.RxMicroModule;
import io.rxmicro.common.meta.BuilderMethod;
import io.rxmicro.rest.model.UrlSegments;
import io.rxmicro.rest.server.detail.component.AbstractRestController;
import io.rxmicro.rest.server.detail.component.BadHttpRequestRestController;
import io.rxmicro.rest.server.detail.component.CrossOriginResourceSharingPreflightRestController;
import io.rxmicro.rest.server.detail.component.CustomExceptionServerModelWriters;
import io.rxmicro.rest.server.detail.component.HttpHealthCheckRestController;
import io.rxmicro.rest.server.detail.component.RestControllerAggregator;
import io.rxmicro.rest.server.detail.component.StaticResourceRestController;
import io.rxmicro.rest.server.detail.model.CrossOriginResourceSharingResource;
import io.rxmicro.rest.server.detail.model.HttpHealthCheckRegistration;
import io.rxmicro.rest.server.detail.model.mapping.ExactUrlRequestMappingRule;
import io.rxmicro.rest.server.detail.model.mapping.resource.UrlPathMatchTemplate;
import io.rxmicro.runtime.detail.ChildrenInitHelper;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import javax.lang.model.element.ModuleElement;

import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.ENVIRONMENT_CUSTOMIZER_SIMPLE_CLASS_NAME;
import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getEntryPointFullClassName;
import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getEntryPointPackage;
import static io.rxmicro.annotation.processor.rest.server.model.RestControllerAggregatorClassStructure.RestControllerModelType.BAD_REQUEST_NETTY;
import static io.rxmicro.annotation.processor.rest.server.model.RestControllerAggregatorClassStructure.RestControllerModelType.CORS;
import static io.rxmicro.annotation.processor.rest.server.model.RestControllerAggregatorClassStructure.RestControllerModelType.CUSTOM;
import static io.rxmicro.annotation.processor.rest.server.model.RestControllerAggregatorClassStructure.RestControllerModelType.HEATH_CHECK;
import static io.rxmicro.annotation.processor.rest.server.model.RestControllerAggregatorClassStructure.RestControllerModelType.STATIC_RESOURCES_STANDARD_CONTROLLER;
import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.rest.server.detail.component.RestControllerAggregator.REST_CONTROLLER_AGGREGATOR_IMPL_CLASS_NAME;

/**
 * @author nedis
 * @since 0.1
 */
public final class RestControllerAggregatorClassStructure extends ClassStructure {

    private final ModuleElement moduleElement;

    private final Collection<RestControllerClassStructure> classStructures;

    private final Set<CrossOriginResourceSharingResource> crossOriginResourceSharingResources;

    private final Set<HttpHealthCheck> httpHealthChecks;

    private final DeclaredStaticResources declaredStaticResources;

    private final boolean isRestServerNetty;

    private RestControllerAggregatorClassStructure(final EnvironmentContext environmentContext,
                                                   final Collection<RestControllerClassStructure> classStructures,
                                                   final Set<CrossOriginResourceSharingResource> resources,
                                                   final Set<HttpHealthCheck> httpHealthChecks,
                                                   final DeclaredStaticResources declaredStaticResources) {
        this.moduleElement = environmentContext.getCurrentModule();
        this.crossOriginResourceSharingResources = require(resources);
        this.httpHealthChecks = require(httpHealthChecks);
        this.declaredStaticResources = declaredStaticResources;
        this.classStructures = new TreeSet<>(Comparator.comparing(RestControllerClassStructure::getTargetFullClassName));
        this.classStructures.addAll(require(classStructures));
        this.isRestServerNetty = environmentContext.isRxMicroModuleEnabled(RxMicroModule.RX_MICRO_REST_SERVER_NETTY_MODULE);
    }

    @Override
    public String getTargetFullClassName() {
        return getEntryPointFullClassName(moduleElement, REST_CONTROLLER_AGGREGATOR_IMPL_CLASS_NAME);
    }

    @Override
    public String getTemplateName() {
        return "rest/server/$$RestControllerAggregatorTemplate.javaftl";
    }

    @Override
    protected boolean shouldSourceCodeBeGenerated(final boolean isLibraryModule) {
        return !classStructures.isEmpty() || declaredStaticResources.exist();
    }

    @Override
    public Map<String, Object> getTemplateVariables() {
        final Map<String, Object> map = new HashMap<>();
        final List<RestControllerModel> restControllerModels = classStructures.stream()
                .map(s -> new RestControllerModel(CUSTOM, s.getTargetFullClassName()))
                .collect(Collectors.toList());
        map.put("CORS_RESOURCES", crossOriginResourceSharingResources);
        if (!crossOriginResourceSharingResources.isEmpty()) {
            restControllerModels.add(new RestControllerModel(CORS));
        }
        map.put("HTTP_HEALTH_CHECKS", httpHealthChecks);
        if (!httpHealthChecks.isEmpty()) {
            restControllerModels.add(new RestControllerModel(HEATH_CHECK));
        }
        if (isRestServerNetty) {
            restControllerModels.add(new RestControllerModel(BAD_REQUEST_NETTY));
        }
        map.put("IMPL_CLASS_NAME", REST_CONTROLLER_AGGREGATOR_IMPL_CLASS_NAME);
        map.put("JAVA_REST_CONTROLLER_CLASSES", restControllerModels);
        map.put("ENVIRONMENT_CUSTOMIZER_CLASS", ENVIRONMENT_CUSTOMIZER_SIMPLE_CLASS_NAME);
        if (declaredStaticResources.exist()) {
            restControllerModels.add(new RestControllerModel(STATIC_RESOURCES_STANDARD_CONTROLLER));
            map.put("DECLARED_STATIC_RESOURCES", declaredStaticResources);
        }
        return map;
    }

    @Override
    public ClassHeader getClassHeader() {
        final ClassHeader.Builder classHeaderBuilder = ClassHeader.newClassHeaderBuilder(getEntryPointPackage(moduleElement))
                .addImports(AbstractRestController.class, RestControllerAggregator.class, List.class)
                .addStaticImport(ChildrenInitHelper.class, "invokeAllStaticSections")
                .addStaticImport(CustomExceptionServerModelWriters.class, "CUSTOM_EXCEPTION_MODEL_WRITERS_CLASS_NAME");
        if (!crossOriginResourceSharingResources.isEmpty()) {
            classHeaderBuilder.addImports(
                    Set.class,
                    CrossOriginResourceSharingPreflightRestController.class,
                    CrossOriginResourceSharingResource.class,
                    UrlSegments.class
            );
        }
        if (!httpHealthChecks.isEmpty()) {
            classHeaderBuilder.addImports(HttpHealthCheckRestController.class, HttpHealthCheckRegistration.class);
        }
        if (isRestServerNetty) {
            classHeaderBuilder.addImports(ExactUrlRequestMappingRule.class, BadHttpRequestRestController.class);
        }
        if (declaredStaticResources.exist()) {
            customizeClassHeaderBuilderForDeclaredStaticResources(classHeaderBuilder);
        }
        return classHeaderBuilder.build();
    }

    private void customizeClassHeaderBuilderForDeclaredStaticResources(final ClassHeader.Builder classHeaderBuilder) {
        classHeaderBuilder.addImports(StaticResourceRestController.class, Map.class, List.class);
        for (final UrlPathMatchTemplate urlPathMatchTemplate : declaredStaticResources.getResourcePathTemplates()) {
            classHeaderBuilder.addImports(urlPathMatchTemplate.getClass());
        }
        for (final Map.Entry<UrlPathMatchTemplate, String> entry : declaredStaticResources.getCustomTemplateResourceMapping()) {
            classHeaderBuilder.addImports(entry.getKey().getClass());
        }
        if (!declaredStaticResources.getCustomTemplateResourceMapping().isEmpty() ||
                !declaredStaticResources.getCustomExactResourceMapping().isEmpty()) {
            classHeaderBuilder.addStaticImport(Map.class, "entry");
        }
    }

    /**
     * @author nedis
     * @since 0.7
     */
    public static final class RestControllerModel {

        private final RestControllerModelType type;

        private final String customClassName;

        public RestControllerModel(final RestControllerModelType type,
                                   final String customClassName) {
            this.type = require(type);
            this.customClassName = require(customClassName);
        }

        public RestControllerModel(final RestControllerModelType type) {
            this.type = require(type);
            this.customClassName = null;
        }

        @UsedByFreemarker("$$RestControllerAggregatorTemplate.javaftl")
        public RestControllerModelType getType() {
            return type;
        }

        @UsedByFreemarker("$$RestControllerAggregatorTemplate.javaftl")
        public String getCustomClassName() {
            return customClassName;
        }
    }

    /**
     * @author nedis
     * @since 0.7
     */
    public enum RestControllerModelType {

        CUSTOM,

        @UsedByFreemarker("$$RestControllerAggregatorTemplate.javaftl")
        CORS,

        @UsedByFreemarker("$$RestControllerAggregatorTemplate.javaftl")
        HEATH_CHECK,

        @UsedByFreemarker("$$RestControllerAggregatorTemplate.javaftl")
        BAD_REQUEST_NETTY,

        @UsedByFreemarker("$$RestControllerAggregatorTemplate.javaftl")
        STATIC_RESOURCES_STANDARD_CONTROLLER
    }

    /**
     * @author nedis
     * @since 0.10
     */
    public static final class Builder {

        private EnvironmentContext environmentContext;

        private Collection<RestControllerClassStructure> classStructures = Set.of();

        private Set<CrossOriginResourceSharingResource> crossOriginResourceSharingResources = Set.of();

        private Set<HttpHealthCheck> httpHealthChecks = Set.of();

        private DeclaredStaticResources declaredStaticResources;

        @BuilderMethod
        public Builder setEnvironmentContext(final EnvironmentContext environmentContext) {
            this.environmentContext = require(environmentContext);
            return this;
        }

        @BuilderMethod
        public Builder setClassStructures(final Collection<RestControllerClassStructure> classStructures) {
            this.classStructures = require(classStructures);
            return this;
        }

        @BuilderMethod
        public Builder setCrossOriginResourceSharingResources(
                final Set<CrossOriginResourceSharingResource> crossOriginResourceSharingResources) {
            this.crossOriginResourceSharingResources = require(crossOriginResourceSharingResources);
            return this;
        }

        @BuilderMethod
        public Builder setHttpHealthChecks(final Set<HttpHealthCheck> httpHealthChecks) {
            this.httpHealthChecks = require(httpHealthChecks);
            return this;
        }

        @BuilderMethod
        public Builder setDeclaredStaticResources(final DeclaredStaticResources declaredStaticResources) {
            this.declaredStaticResources = require(declaredStaticResources);
            return this;
        }

        public RestControllerAggregatorClassStructure build() {
            return new RestControllerAggregatorClassStructure(
                    environmentContext,
                    classStructures,
                    crossOriginResourceSharingResources,
                    httpHealthChecks,
                    declaredStaticResources
            );
        }
    }
}
