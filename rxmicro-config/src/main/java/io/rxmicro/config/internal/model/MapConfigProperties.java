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

import io.rxmicro.config.AsMapConfig;
import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import static io.rxmicro.common.util.ExCollectors.toOrderedSet;
import static io.rxmicro.common.util.ExCollectors.toUnmodifiableOrderedMap;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Strings.capitalize;
import static io.rxmicro.config.internal.Converters.convertWithoutTypeDefinition;
import static io.rxmicro.config.internal.model.AbstractDefaultConfigValueBuilder.getCurrentDefaultConfigValueStorage;
import static java.util.Map.entry;

/**
 * @author nedis
 * @since 0.7
 */
public final class MapConfigProperties extends ConfigProperties {

    private static final Logger LOGGER = LoggerFactory.getLogger(MapConfigProperties.class);

    private final AsMapConfig asMapConfig;

    private final Map<String, String> data = new LinkedHashMap<>();

    public MapConfigProperties(final String namespace,
                               final AsMapConfig asMapConfig) {
        super(namespace);
        this.asMapConfig = asMapConfig;
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    @Override
    protected void loadDefaultConfigValues(final DebugMessageBuilder debugMessageBuilder) {
        final String prefix = namespace + ".";
        final Set<Map.Entry<String, String>> resolvedEntries = new LinkedHashSet<>();
        final DefaultConfigValueStorage storage = getCurrentDefaultConfigValueStorage();
        if (storage.hasDefaultStringValuesStorage()) {
            for (final Map.Entry<String, String> entry : storage.getDefaultStringValuesStorage().entrySet()) {
                if (entry.getKey().startsWith(prefix)) {
                    resolvedEntries.add(entry(entry.getKey().substring(prefix.length()), entry.getValue()));
                }
            }
        }
        if (storage.hasDefaultSupplierValuesStorage()) {
            for (final Map.Entry<String, Supplier<?>> entry : storage.getDefaultSupplierValuesStorage().entrySet()) {
                if (entry.getKey().startsWith(prefix)) {
                    resolvedEntries.add(entry(entry.getKey().substring(prefix.length()), String.valueOf(entry.getValue().get())));
                }
            }
        }
        addResolvedEntries(resolvedEntries, "default config storage", debugMessageBuilder);
    }

    @Override
    protected void loadResource(final Supplier<Optional<Map<String, String>>> propertiesSupplier,
                                final String resourceType,
                                final String resourceName,
                                final boolean useFullName,
                                final DebugMessageBuilder debugMessageBuilder) {
        final Optional<Map<String, String>> resourceOptional = propertiesSupplier.get();
        if (resourceOptional.isPresent()) {
            if (useFullName) {
                loadFromMap(resourceOptional.get(), format("'?' ?", resourceName, resourceType), debugMessageBuilder, false);
            } else {
                final Set<Map.Entry<String, String>> resolvedEntries = resourceOptional.get().entrySet();
                addResolvedEntries(resolvedEntries, format("'?' ?", resourceName, resourceType), debugMessageBuilder);
            }
        } else {
            debugMessageBuilder.append("? not found: ?", capitalize(resourceType), resourceName);
        }
    }

    @Override
    protected void loadFromJavaSystemProperties(final DebugMessageBuilder debugMessageBuilder) {
        final String prefix = namespace + ".";
        final Set<Map.Entry<String, String>> resolvedEntries = SYSTEM_PROPERTIES.entrySet()
                .stream()
                .filter(e -> String.valueOf(e.getKey()).startsWith(prefix))
                .map(e -> entry(String.valueOf(e.getKey()).substring(prefix.length()), String.valueOf(e.getValue())))
                .collect(toOrderedSet());
        addResolvedEntries(resolvedEntries, "Java system properties", debugMessageBuilder);
    }

    @Override
    protected void loadFromMap(final Map<String, String> map,
                               final String sourceName,
                               final DebugMessageBuilder debugMessageBuilder,
                               final boolean isEnvironmentVariable) {
        final String prefix1 = namespace + '.';
        final String prefix2 = isEnvironmentVariable ? upperNamespace + '_' : null;
        final Set<Map.Entry<String, String>> resolvedEntries = map.entrySet()
                .stream()
                .filter(e -> {
                    boolean result = e.getKey().startsWith(prefix1);
                    if (!result && isEnvironmentVariable) {
                        result = e.getKey().startsWith(prefix2);
                    }
                    return result;
                })
                .map(e -> entry(e.getKey().substring(prefix1.length()), e.getValue()))
                .collect(toOrderedSet());
        addResolvedEntries(resolvedEntries, sourceName, debugMessageBuilder);
    }

    @Override
    public void setProperties() {
        asMapConfig.setMap(
                data.entrySet().stream()
                        .map(this::convertEntry)
                        .collect(toUnmodifiableOrderedMap(Map.Entry::getKey, Map.Entry::getValue))
        );
    }

    private Map.Entry<String, Object> convertEntry(final Map.Entry<String, String> entry) {
        final Object value = convertWithoutTypeDefinition(entry.getValue(), asMapConfig.supportsMap(), asMapConfig.supportsList());
        return entry(entry.getKey(), value);
    }

    private void addResolvedEntries(final Set<Map.Entry<String, String>> resolvedEntries,
                                    final String sourceName,
                                    final DebugMessageBuilder debugMessageBuilder) {
        if (!resolvedEntries.isEmpty()) {
            resolvedEntries.forEach(e -> data.put(e.getKey(), e.getValue()));
            debugMessageBuilder.addResolvedEntries(resolvedEntries);
            debugMessageBuilder.append("Discovered properties from ?: ?", sourceName, resolvedEntries);
        }
    }
}
