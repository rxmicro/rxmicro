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

package io.rxmicro.annotation.processor.rest.server.component.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.rest.component.BaseUrlBuilder;
import io.rxmicro.annotation.processor.rest.server.component.DeclaredStaticResourcesResolver;
import io.rxmicro.annotation.processor.rest.server.component.UrlPathMatchTemplateClassResolver;
import io.rxmicro.annotation.processor.rest.server.model.DeclaredStaticResources;
import io.rxmicro.rest.server.StaticResources;
import io.rxmicro.rest.server.detail.model.mapping.resource.UrlPathMatchTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Strings.startsWith;
import static io.rxmicro.common.util.UrlPaths.normalizeUrlPath;
import static java.util.Map.entry;
import static java.util.stream.Collectors.toList;

/**
 * @author nedis
 * @since 0.8
 */
@Singleton
public final class DeclaredStaticResourcesResolverImpl extends BaseUrlBuilder implements DeclaredStaticResourcesResolver {

    @Inject
    private UrlPathMatchTemplateClassResolver urlPathMatchTemplateClassResolver;

    @Override
    public DeclaredStaticResources resolve(final EnvironmentContext environmentContext,
                                           final Collection<? extends TypeElement> annotations,
                                           final RoundEnvironment roundEnv) {
        final DeclaredStaticResourcesHolder holder = new DeclaredStaticResourcesHolder();

        final Set<String> processedTypes = new HashSet<>();
        for (final TypeElement annotation : annotations) {
            for (final Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
                if (element instanceof TypeElement) {
                    final TypeElement restControllerClass = (TypeElement) element;
                    if (environmentContext.isRxMicroClassShouldBeProcessed(restControllerClass) &&
                            processedTypes.add(restControllerClass.asType().toString())) {
                        addStaticResources(restControllerClass, holder);
                    }
                }
            }
        }
        // Resolve static resources from ModuleElement if exists
        addStaticResources(environmentContext.getCurrentModule(), holder);
        return buildDeclaredStaticResources(holder);
    }

    private void addStaticResources(final Element owner,
                                    final DeclaredStaticResourcesHolder holder) {
        for (final StaticResources staticResources : owner.getAnnotationsByType(StaticResources.class)) {
            validateStaticResourcesParameters(owner, staticResources);
            final Map.Entry<List<String>, List<String>> resources = getStaticResources(owner, staticResources);
            addStaticResource(owner, resources, holder);
        }
    }

    private void validateStaticResourcesParameters(final Element owner,
                                                   final StaticResources staticResources) {
        if (staticResources.value().length == 0 && staticResources.urls().length == 0) {
            throw new InterruptProcessingException(
                    owner,
                    "'@?' annotation must contain not empty array of static resources! " +
                            "Add missing resources using 'value' or 'urls' annotation parameters or remove this annotation!",
                    StaticResources.class.getSimpleName()
            );
        }
        if (staticResources.value().length != 0 && staticResources.urls().length != 0) {
            throw new InterruptProcessingException(
                    owner,
                    "'value' and 'urls' annotation parameters of '@?' annotation are aliases and can't be used together! " +
                            "Remove 'value' or 'urls' annotation parameter!",
                    StaticResources.class.getSimpleName()
            );
        }
        final int filePathsLength = staticResources.filePaths().length;
        if (filePathsLength != 0 && filePathsLength != staticResources.value().length && filePathsLength != staticResources.urls().length) {
            throw new InterruptProcessingException(
                    owner,
                    "Invalid parameters for '@?' annotation: filePaths().length must be equal to urls().length or value().length!",
                    StaticResources.class.getSimpleName()
            );
        }
    }

    private Map.Entry<List<String>, List<String>> getStaticResources(final Element owner,
                                                                     final StaticResources staticResources) {
        final int filePathsLength = staticResources.filePaths().length;
        if (staticResources.value().length != 0) {
            final List<String> urls = normalize(owner, staticResources.value(), "value");
            if (filePathsLength != 0) {
                return entry(urls, List.of(staticResources.filePaths()));
            } else {
                return entry(urls, List.of());
            }
        } else {
            final List<String> urls = normalize(owner, staticResources.urls(), "url");
            if (filePathsLength != 0) {
                return entry(urls, List.of(staticResources.filePaths()));
            } else {
                return entry(urls, List.of());
            }
        }
    }

    private List<String> normalize(final Element owner,
                                   final String[] values,
                                   final String name) {
        return Arrays.stream(values)
                .map(value -> {
                    validateNotNull(owner, value, format("? can't be null!", name));
                    validateNotEmpty(owner, value, format("? can't be empty string!", name));
                    validateThatPathIsTrimmedValue(owner, value, format("? must not contain space characters!", name));
                    final String normalizeUrlPath;
                    if (startsWith(value, '*')) {
                        // Remove first '/' character
                        normalizeUrlPath = normalizeUrlPath(value).substring(1);
                    } else {
                        normalizeUrlPath = normalizeUrlPath(value);
                    }
                    if (!normalizeUrlPath.equals(value) && isStrictModeEnabled()) {
                        throw new InterruptProcessingException(
                                owner,
                                "Invalid static resource ?: Expected '?', but actual is '?'!",
                                name, normalizeUrlPath, value
                        );
                    }
                    return normalizeUrlPath;
                })
                .collect(toList());
    }

    private void addStaticResource(final Element owner,
                                   final Map.Entry<List<String>, List<String>> resources,
                                   final DeclaredStaticResourcesHolder holder) {
        for (int i = 0; i < resources.getKey().size(); i++) {
            final String url = resources.getKey().get(i);
            final Optional<UrlPathMatchTemplate> urlPathMatchTemplate = urlPathMatchTemplateClassResolver.getIfExists(owner, url);
            if (resources.getValue().isEmpty()) {
                if (urlPathMatchTemplate.isPresent()) {
                    validateThatUrlIsUnique(owner, holder, urlPathMatchTemplate.get());
                    holder.resourcePathTemplates.add(urlPathMatchTemplate.get());
                } else {
                    validateThatUrlIsUnique(owner, holder, url);
                    holder.exactResourcePaths.add(url);
                }
            } else {
                final String filePath = resources.getValue().get(i);
                if (urlPathMatchTemplate.isPresent()) {
                    validateThatUrlIsUnique(owner, holder, urlPathMatchTemplate.get());
                    holder.customTemplateResourceMapping.put(urlPathMatchTemplate.get(), filePath);
                } else {
                    validateThatUrlIsUnique(owner, holder, url);
                    holder.customExactResourceMapping.put(url, filePath);
                }
            }
        }
    }

    private void validateThatUrlIsUnique(final Element owner,
                                         final DeclaredStaticResourcesHolder holder,
                                         final String url) {
        boolean throwError = false;
        if (holder.exactResourcePaths.contains(url)) {
            throwError = true;
        } else if (holder.customExactResourceMapping.containsKey(url)) {
            throwError = true;
        }
        if (throwError) {
            throw new InterruptProcessingException(
                    owner,
                    "The '?' static resource is not unique! Remove duplicate of the static resource definition!",
                    url
            );
        }
    }

    private void validateThatUrlIsUnique(final Element owner,
                                         final DeclaredStaticResourcesHolder holder,
                                         final UrlPathMatchTemplate urlPathMatchTemplate) {
        boolean throwError = false;
        if (holder.resourcePathTemplates.contains(urlPathMatchTemplate)) {
            throwError = true;
        } else if (holder.customTemplateResourceMapping.containsKey(urlPathMatchTemplate)) {
            throwError = true;
        }
        if (throwError) {
            throw new InterruptProcessingException(
                    owner,
                    "The '?' static resource is not unique! Remove duplicate of the static resource definition!",
                    urlPathMatchTemplate.getUrlTemplate()
            );
        }
    }

    private DeclaredStaticResources buildDeclaredStaticResources(final DeclaredStaticResourcesHolder holder) {
        final List<UrlPathMatchTemplate> resourcePathTemplates = new ArrayList<>(holder.resourcePathTemplates);
        resourcePathTemplates.addAll(holder.customTemplateResourceMapping.keySet());
        resourcePathTemplates.sort(UrlPathMatchTemplate::compareTo);

        final List<String> exactResourcePaths = new ArrayList<>(holder.exactResourcePaths);
        exactResourcePaths.addAll(holder.customExactResourceMapping.keySet());

        return new DeclaredStaticResources(
                resourcePathTemplates,
                holder.customTemplateResourceMapping,
                exactResourcePaths,
                holder.customExactResourceMapping
        );
    }

    /**
     * @author nedis
     * @since 0.8
     */
    private static final class DeclaredStaticResourcesHolder {

        private final Set<UrlPathMatchTemplate> resourcePathTemplates = new LinkedHashSet<>();

        private final Map<UrlPathMatchTemplate, String> customTemplateResourceMapping = new LinkedHashMap<>();

        private final Set<String> exactResourcePaths = new LinkedHashSet<>();

        private final Map<String, String> customExactResourceMapping = new LinkedHashMap<>();
    }
}
