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

import io.rxmicro.config.Config;
import io.rxmicro.config.ConfigException;
import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import static io.rxmicro.common.local.RxMicroEnvironment.isRuntimeStrictModeEnabled;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Strings.capitalize;
import static io.rxmicro.common.util.Strings.unCapitalize;
import static io.rxmicro.config.internal.model.AbstractDefaultConfigValueBuildHelper.getCurrentDefaultConfigValueStorage;
import static io.rxmicro.reflection.JavaBeans.findPublicSetters;
import static java.util.Map.entry;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

/**
 * @author nedis
 * @since 0.1
 */
public final class JavaBeanConfigProperties extends ConfigProperties {

    private static final int GETTER_PREFIX_LENGTH = "get".length();

    private static final Logger LOGGER = LoggerFactory.getLogger(JavaBeanConfigProperties.class);

    private final Collection<ConfigProperty> properties;

    public JavaBeanConfigProperties(final String namespace,
                                    final Config config) {
        super(namespace);
        this.properties = findPublicSetters(config.getClass()).stream()
                .map(method -> new ConfigProperty(namespace, upperNamespace, getPropertyName(method), method, config))
                .collect(toList());
    }

    private String getPropertyName(final Method method) {
        return unCapitalize(method.getName().substring(GETTER_PREFIX_LENGTH));
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
            if (isRuntimeStrictModeEnabled()) {
                validateRedundantProperties(storage.getDefaultStringValuesStorage(), "default config storage", true, false);
            }
            properties.forEach(p -> p.resolve(storage.getDefaultStringValuesStorage(), true, false).ifPresent(resolvedEntries::add));
            debugMessageBuilder.append(messageTemplate, storage.getDefaultStringValuesStorage());
        }
        if (storage.hasDefaultSupplierValuesStorage()) {
            if (isRuntimeStrictModeEnabled()) {
                validateRedundantProperties(storage.getDefaultSupplierValuesStorage(), "default config storage", true, false);
            }
            properties.forEach(p -> p.resolve(storage.getDefaultSupplierValuesStorage(), true, false)
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
            if (isRuntimeStrictModeEnabled()) {
                validateRedundantProperties(resourceOptional.get(), format("'?' ?", resourceName, resourceType), useFullName, false);
            }
            final Set<Map.Entry<String, String>> resolvedEntries = new LinkedHashSet<>();
            properties.forEach(p -> p.resolve(resourceOptional.get(), useFullName, false).ifPresent(resolvedEntries::add));
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
        if (isRuntimeStrictModeEnabled()) {
            validateRedundantProperties(
                    SYSTEM_PROPERTIES_SUPPLIER.get().entrySet().stream()
                            .map(e -> entry(e.getKey().toString(), e.getValue().toString()))
                            .collect(toMap(Map.Entry::getKey, Map.Entry::getValue)),
                    "Java system properties",
                    true,
                    false
            );
        }
        final Set<Map.Entry<String, String>> resolvedEntries = new LinkedHashSet<>();
        properties.forEach(p -> p.resolve(SYSTEM_PROPERTIES_SUPPLIER.get(), true).ifPresent(resolvedEntries::add));
        if (!resolvedEntries.isEmpty()) {
            debugMessageBuilder.addResolvedEntries(resolvedEntries);
            debugMessageBuilder.append("Discovered properties from Java system properties: ?", resolvedEntries);
        }
    }

    @Override
    protected void loadFromMap(final Map<String, String> map,
                               final String sourceName,
                               final DebugMessageBuilder debugMessageBuilder,
                               final boolean isEnvironmentVariable) {
        if (isRuntimeStrictModeEnabled()) {
            validateRedundantProperties(map, sourceName, true, isEnvironmentVariable);
        }
        final Set<Map.Entry<String, String>> resolvedEntries = new LinkedHashSet<>();
        properties.forEach(p -> p.resolve(map, true, isEnvironmentVariable).ifPresent(resolvedEntries::add));
        if (!resolvedEntries.isEmpty()) {
            debugMessageBuilder.addResolvedEntries(resolvedEntries);
            debugMessageBuilder.append("Discovered properties from ?: ?", sourceName, resolvedEntries);
        }
    }

    private void validateRedundantProperties(final Map<String, ?> map,
                                             final String sourceName,
                                             final boolean useFullName,
                                             final boolean isEnvironmentVariable) {
        final String prefix1 = namespace + '.';
        final String prefix2 = isEnvironmentVariable ? upperNamespace + '_' : null;
        final Map<String, ?> mapCopy;
        if (useFullName) {
            mapCopy = map.entrySet().stream()
                    .filter(e -> {
                        boolean result = e.getKey().startsWith(prefix1);
                        if (!result && isEnvironmentVariable) {
                            result = e.getKey().startsWith(prefix2);
                        }
                        return result;
                    })
                    .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
        } else {
            mapCopy = new HashMap<>(map);
        }
        properties.forEach(p -> mapCopy.remove(p.getPropertyName(useFullName)));
        if (isEnvironmentVariable) {
            properties.forEach(p -> mapCopy.remove(p.getSystemVariablePropertyName()));
        }
        if (!mapCopy.isEmpty()) {
            throw new ConfigException(
                    "Detected redundant property(ies) defined at ? for the namespace: '?': ?",
                    sourceName, namespace, mapCopy
            );
        }
    }

    @Override
    public void setProperties() {
        properties.forEach(ConfigProperty::setProperty);
    }
}
