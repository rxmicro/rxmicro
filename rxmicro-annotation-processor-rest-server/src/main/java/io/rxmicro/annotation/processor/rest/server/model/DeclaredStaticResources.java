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

import io.rxmicro.rest.server.detail.model.mapping.resource.UrlPathMatchTemplate;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static io.rxmicro.common.util.ExCollections.unmodifiableList;
import static io.rxmicro.common.util.ExCollections.unmodifiableOrderedMap;
import static io.rxmicro.common.util.ExCollectors.toUnmodifiableOrderedSet;
import static java.util.function.Function.identity;

/**
 * @author nedis
 * @since 0.8
 */
public final class DeclaredStaticResources {

    private final Collection<UrlPathMatchTemplate> resourcePathTemplates;

    private final Map<UrlPathMatchTemplate, String> customTemplateResourceMapping;

    private final Collection<String> exactResourcePaths;

    private final Map<String, String> customExactResourceMapping;

    public DeclaredStaticResources(final Collection<UrlPathMatchTemplate> resourcePathTemplates,
                                   final Map<UrlPathMatchTemplate, String> customTemplateResourceMapping,
                                   final Collection<String> exactResourcePaths,
                                   final Map<String, String> customExactResourceMapping) {
        this.resourcePathTemplates = unmodifiableList(resourcePathTemplates);
        this.customTemplateResourceMapping = unmodifiableOrderedMap(customTemplateResourceMapping);
        this.exactResourcePaths = unmodifiableList(exactResourcePaths);
        this.customExactResourceMapping = unmodifiableOrderedMap(customExactResourceMapping);
    }

    public boolean exist() {
        return !resourcePathTemplates.isEmpty() || !customTemplateResourceMapping.isEmpty() ||
                !exactResourcePaths.isEmpty() || !customExactResourceMapping.isEmpty();
    }

    public Set<String> getStaticUrls() {
        return Stream
                .of(
                        resourcePathTemplates.stream().map(UrlPathMatchTemplate::getUrlTemplate),
                        exactResourcePaths.stream()
                )
                .flatMap(identity())
                .collect(toUnmodifiableOrderedSet());
    }

    public Collection<UrlPathMatchTemplate> getResourcePathTemplates() {
        return resourcePathTemplates;
    }

    public Set<Map.Entry<UrlPathMatchTemplate, String>> getCustomTemplateResourceMapping() {
        return customTemplateResourceMapping.entrySet();
    }

    public Collection<String> getExactResourcePaths() {
        return exactResourcePaths;
    }

    public Set<Map.Entry<String, String>> getCustomExactResourceMapping() {
        return customExactResourceMapping.entrySet();
    }
}
