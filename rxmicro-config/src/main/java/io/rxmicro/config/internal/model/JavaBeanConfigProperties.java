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

package io.rxmicro.config.internal.model;

import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerFactory;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import static io.rxmicro.common.util.Strings.capitalize;
import static io.rxmicro.config.internal.model.AbstractDefaultConfigValueBuilder.getCurrentDefaultConfigValueStorage;
import static java.util.Map.entry;

/**
 * @author nedis
 * @since 0.1
 */
public final class JavaBeanConfigProperties extends ConfigProperties {

    private static final Logger LOGGER = LoggerFactory.getLogger(JavaBeanConfigProperties.class);

    private final Collection<ConfigProperty> properties;

    public JavaBeanConfigProperties(final String namespace,
                                    final Collection<ConfigProperty> properties) {
        super(namespace);
        this.properties = properties;
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    @Override
    protected void loadDefaultConfigValues(final DebugMessageBuilder debugMessageBuilder) {
        final Set<Map.Entry<String, String>> resolvedEntries = new LinkedHashSet<>();
        final DefaultConfigValueStorage storage = getCurrentDefaultConfigValueStorage();
        final String messageTemplate = "Discovered properties from default config storage: ?";
        if (storage.hasDefaultStringValuesStorage()) {
            properties.forEach(p -> p.resolve(storage.getDefaultStringValuesStorage(), true).ifPresent(resolvedEntries::add));
            debugMessageBuilder.append(messageTemplate, storage.getDefaultStringValuesStorage());
        }
        if (storage.hasDefaultSupplierValuesStorage()) {
            properties.forEach(p -> p.resolve(storage.getDefaultSupplierValuesStorage(), true)
                    .ifPresent(e -> resolvedEntries.add(entry(e.getKey(), String.valueOf(e.getValue())))));
            debugMessageBuilder.append(messageTemplate, storage.getDefaultSupplierValuesStorage());
        }
        if (!resolvedEntries.isEmpty()) {
            debugMessageBuilder.addResolvedEntries(resolvedEntries);
            debugMessageBuilder.append(messageTemplate, resolvedEntries);
        }
    }

    @Override
    protected void loadResource(final Supplier<Optional<Map<String, String>>> propertiesSupplier,
                                final String resourceType,
                                final String resourceName,
                                final boolean useFullName,
                                final DebugMessageBuilder debugMessageBuilder) {
        final Optional<Map<String, String>> resourceOptional = propertiesSupplier.get();
        if (resourceOptional.isPresent()) {
            final Set<Map.Entry<String, String>> resolvedEntries = new LinkedHashSet<>();
            properties.forEach(p -> p.resolve(resourceOptional.get(), useFullName).ifPresent(resolvedEntries::add));
            if (!resolvedEntries.isEmpty()) {
                debugMessageBuilder.addResolvedEntries(resolvedEntries);
                debugMessageBuilder.append("Discovered properties from '?' ?: ?", resourceName, resourceType, resolvedEntries);
            }
        } else {
            debugMessageBuilder.append("? not found: ?", capitalize(resourceType), resourceName);
        }
    }

    @Override
    protected void loadFromJavaSystemProperties(final DebugMessageBuilder debugMessageBuilder) {
        final Set<Map.Entry<String, String>> resolvedEntries = new LinkedHashSet<>();
        properties.forEach(p -> p.resolve(SYSTEM_PROPERTIES, true).ifPresent(resolvedEntries::add));
        if (!resolvedEntries.isEmpty()) {
            debugMessageBuilder.addResolvedEntries(resolvedEntries);
            debugMessageBuilder.append("Discovered properties from Java system properties: ?", resolvedEntries);
        }
    }

    @Override
    protected void loadFromMap(final Map<String, String> map,
                               final String sourceName,
                               final DebugMessageBuilder debugMessageBuilder) {
        final Set<Map.Entry<String, String>> resolvedEntries = new LinkedHashSet<>();
        properties.forEach(p -> p.resolve(map, true).ifPresent(resolvedEntries::add));
        if (!resolvedEntries.isEmpty()) {
            debugMessageBuilder.addResolvedEntries(resolvedEntries);
            debugMessageBuilder.append("Discovered properties from ?: ?", sourceName, resolvedEntries);
        }
    }

    @Override
    public void setProperties() {
        properties.forEach(ConfigProperty::setProperty);
    }
}
