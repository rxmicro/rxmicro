/*
 * Copyright 2019 https://rxmicro.io
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
import io.rxmicro.annotation.processor.rest.model.HttpMethodMapping;
import io.rxmicro.annotation.processor.rest.model.MappedRestObjectModelClass;
import io.rxmicro.annotation.processor.rest.model.RestGenerationContext;
import io.rxmicro.annotation.processor.rest.model.RestObjectModelClass;
import io.rxmicro.annotation.processor.rest.server.component.CrossOriginResourceSharingResourceBuilder;
import io.rxmicro.annotation.processor.rest.server.model.CrossOriginResourceSharingResourceProxy;
import io.rxmicro.annotation.processor.rest.server.model.RestControllerClassStructure;
import io.rxmicro.annotation.processor.rest.server.model.RestControllerMethod;
import io.rxmicro.rest.model.UrlSegments;
import io.rxmicro.rest.server.detail.model.CrossOriginResourceSharingResource;
import io.rxmicro.rest.server.feature.EnableCrossOriginResourceSharing;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@Singleton
public final class CrossOriginResourceSharingResourceBuilderImpl
        implements CrossOriginResourceSharingResourceBuilder {

    @Override
    public Set<CrossOriginResourceSharingResource> build(final Collection<RestControllerClassStructure> classStructures,
                                                         final RestGenerationContext context) {
        final Map<String, CrossOriginResourceSharingResourceProxy.Builder> exactUrlMap = new LinkedHashMap<>();
        final Map<UrlSegments, CrossOriginResourceSharingResourceProxy.Builder> urlTemplateMap = new LinkedHashMap<>();
        Map<String, RestObjectModelClass> fromHttpDataModelMap = null;
        for (final RestControllerClassStructure classStructure : classStructures) {
            final EnableCrossOriginResourceSharing enableCORS =
                    classStructure.getOwnerClass().getAnnotation(EnableCrossOriginResourceSharing.class);
            if (enableCORS != null && enableCORS.allowOrigins().length > 0) {
                if (fromHttpDataModelMap == null) {
                    fromHttpDataModelMap = buildHttpDataModelMap(context.getFromHttpDataModelClasses());
                }
                addCORSResources(exactUrlMap, urlTemplateMap, classStructure, enableCORS, fromHttpDataModelMap);
            }
        }
        if (exactUrlMap.isEmpty() && urlTemplateMap.isEmpty()) {
            return Set.of();
        } else {
            return Stream.concat(
                    exactUrlMap.entrySet().stream()
                            .map(e -> e.getValue().build(e.getKey())),
                    urlTemplateMap.entrySet().stream()
                            .map(e -> e.getValue().build(e.getKey()))
            ).collect(Collectors.toSet());
        }
    }

    private Map<String, RestObjectModelClass> buildHttpDataModelMap(
            final List<MappedRestObjectModelClass> httpDataModelClasses) {
        return httpDataModelClasses.stream()
                .map(MappedRestObjectModelClass::getModelClass)
                .collect(toMap(
                        c -> c.getModelTypeMirror().toString(),
                        identity(),
                        (o1, o2) -> o1
                ));
    }

    private void addCORSResources(final Map<String, CrossOriginResourceSharingResourceProxy.Builder> exactUrlMap,
                                  final Map<UrlSegments, CrossOriginResourceSharingResourceProxy.Builder> urlTemplateMap,
                                  final RestControllerClassStructure classStructure,
                                  final EnableCrossOriginResourceSharing enableCORS,
                                  final Map<String, RestObjectModelClass> fromHttpDataModelMap) {
        for (final RestControllerMethod method : classStructure.getMethods()) {
            for (final HttpMethodMapping httpMethodMapping : method.getHttpMethodMappings()) {
                final CrossOriginResourceSharingResourceProxy.Builder builder =
                        newBuilder(exactUrlMap, urlTemplateMap, enableCORS, httpMethodMapping);
                builder.addMethod(httpMethodMapping.getMethod());
                method.getFromHttpDataType().ifPresent(t ->
                        fromHttpDataModelMap.get(t.getQualifiedName().toString()).getHeaderEntries().stream()
                                .map(e -> e.getKey().getModelName()).forEach(builder::addHeader));
            }
        }
    }

    private CrossOriginResourceSharingResourceProxy.Builder newBuilder(
            final Map<String, CrossOriginResourceSharingResourceProxy.Builder> exactUrlMap,
            final Map<UrlSegments, CrossOriginResourceSharingResourceProxy.Builder> urlTemplateMap,
            final EnableCrossOriginResourceSharing enableCORS,
            final HttpMethodMapping httpMethodMapping) {
        return httpMethodMapping.isUrlSegmentsPresent() ?
                urlTemplateMap.computeIfAbsent(httpMethodMapping.getUrlSegments(), u ->
                        new CrossOriginResourceSharingResourceProxy.Builder(enableCORS)) :
                exactUrlMap.computeIfAbsent(httpMethodMapping.getUri(), u ->
                        new CrossOriginResourceSharingResourceProxy.Builder(enableCORS));
    }
}
